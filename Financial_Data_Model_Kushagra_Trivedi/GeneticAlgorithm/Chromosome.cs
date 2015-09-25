using FDMTermProject.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FDMTermProject
{
    public class Chromosome
    {
        private static RandomSingleton RandomSingleton;

        public Chromosome ()
	    {
            RandomSingleton = RandomSingleton.Instance;
	    }
        public TradeCondition BuyCondition { get; set; }

        public TradeCondition SellCondition { get; set; }

        public static List<Chromosome> SwapChromosomes(Chromosome chromosome1, Chromosome chromosome2, int swapBuySell = 0)
        {
            Chromosome cloneChromosome1 = DeepClone(chromosome1);
            Chromosome cloneChromosome2 = DeepClone(chromosome2);
            List<Chromosome> crossovers = new List<Chromosome>() { cloneChromosome1, cloneChromosome2 };
            TradeRule tr;
            if (swapBuySell == 0 || swapBuySell == 1)
            {
                foreach(int index in GetBreedIndexes(Math.Min(cloneChromosome1.BuyCondition.TradeRules.Count, cloneChromosome2.BuyCondition.TradeRules.Count)))
                {
                    tr = cloneChromosome1.BuyCondition.TradeRules[index];
                    cloneChromosome1.BuyCondition.TradeRules[index] = cloneChromosome2.BuyCondition.TradeRules[index];
                    cloneChromosome2.BuyCondition.TradeRules[index] = tr;
                }
            }

            if (swapBuySell == 0 || swapBuySell == -1)
            {
                foreach (int index in GetBreedIndexes(Math.Min(cloneChromosome1.SellCondition.TradeRules.Count, cloneChromosome2.SellCondition.TradeRules.Count)))
                {
                    tr = cloneChromosome1.SellCondition.TradeRules[index];
                    cloneChromosome1.SellCondition.TradeRules[index] = cloneChromosome2.SellCondition.TradeRules[index];
                    cloneChromosome2.SellCondition.TradeRules[index] = tr;
                }
            }

            return crossovers;
        }

        private static List<int> GetBreedIndexes(int count)
        {
            int interval = 4;
            RandomPicker randomPicker = new RandomPicker(0, count);
            List<int> indexes = new List<int>();
            for (int i = 0; i < Math.Max(1, count); i = i + interval)
            {
                indexes.Add(randomPicker.GetNextPick());
            }
            return indexes;
        }

        public static Chromosome TweakRules(Chromosome chromosome, List<TradeRule> buyRules, List<TradeRule> sellRules, int tweakBuySell = 0)
        {
            Chromosome cloneChromosome = DeepClone(chromosome);

            Random rand = RandomSingleton.GetRandom();
            if (tweakBuySell == 0 || tweakBuySell == 1)
            {
                foreach (int index in GetBreedIndexes(cloneChromosome.BuyCondition.TradeRules.Count))
                {
                    bool loop = true;
                    while(loop)
                    {
                        TradeRule tradeRule = buyRules[rand.Next(0, buyRules.Count())];
                        if (!cloneChromosome.BuyCondition.TradeRules.Any(tr => tr.Name == tradeRule.Name))
                        {
                            cloneChromosome.BuyCondition.TradeRules[index] = tradeRule;
                            loop = false;
                        }
                    }
                }
            }
            if (tweakBuySell == 0 || tweakBuySell == -1)
            {
                foreach (int index in GetBreedIndexes(cloneChromosome.SellCondition.TradeRules.Count))
                {
                    bool loop = true;
                    while (loop)
                    {
                        TradeRule tradeRule = sellRules[rand.Next(0, sellRules.Count())];
                        if (!cloneChromosome.SellCondition.TradeRules.Any(tr => tr.Name == tradeRule.Name))
                        {
                            cloneChromosome.SellCondition.TradeRules[index] = tradeRule;
                            loop = false;
                        }
                    }
                }
            }

            return cloneChromosome;
        }

        public static Chromosome TweakJoinConditions(Chromosome chromosome, int tweakBuySell = 0)
        {
            Chromosome cloneChromosome = DeepClone(chromosome);
            if (tweakBuySell == 0 || tweakBuySell == 1)
            {
                foreach (int index in GetBreedIndexes(cloneChromosome.BuyCondition.RuleJoinTypes.Count - 1))
                {
                    cloneChromosome.BuyCondition.RuleJoinTypes[index] = cloneChromosome.BuyCondition.RuleJoinTypes[index] == RuleJoinType.and
                                                                                                                        ? RuleJoinType.or
                                                                                                                        : RuleJoinType.and;
                }
            }
            if (tweakBuySell == 0 || tweakBuySell == -1)
            {
                foreach (int index in GetBreedIndexes(cloneChromosome.SellCondition.RuleJoinTypes.Count - 1))
                {
                    cloneChromosome.SellCondition.RuleJoinTypes[index] = cloneChromosome.SellCondition.RuleJoinTypes[index] == RuleJoinType.and
                                                                                                                        ? RuleJoinType.or
                                                                                                                        : RuleJoinType.and;
                }
            }

            return cloneChromosome;
        }

        public static Chromosome DeepClone(Chromosome chromosome)
        {
            if (chromosome == null)
                return null;

            Chromosome clone = new Chromosome()
            {
                BuyCondition = chromosome.BuyCondition == null ? null : new TradeCondition()
                {
                    TradeRules = CloneTradeRules(chromosome.BuyCondition.TradeRules),
                    RuleJoinTypes = CloneRuleJoinType(chromosome.BuyCondition.RuleJoinTypes)
                },
                SellCondition = chromosome.SellCondition == null ? null : new TradeCondition()
                {
                    TradeRules = CloneTradeRules(chromosome.SellCondition.TradeRules),
                    RuleJoinTypes = CloneRuleJoinType(chromosome.SellCondition.RuleJoinTypes)
                }
            };       

            return clone;
        }

        private static List<TradeRule> CloneTradeRules(IEnumerable<TradeRule> tradeRules)
        {
            return tradeRules.Select(tr => (TradeRule)new SimpleCompareTradeRule(tr.IndicatorName1, tr.CompareType, tr.IndicatorName2)).ToList();
        }

        private static List<RuleJoinType> CloneRuleJoinType(IEnumerable<RuleJoinType> ruleJoinTypes)
        {
            return ruleJoinTypes.Select(rjt => rjt).ToList();
        }
        
    }

}
