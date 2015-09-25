using FDMTermProject.Entities;
using FDMTermProject.Library;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace FDMTermProject
{
    /*
    public class GeneticAlgorithmHelper
    {
        public delegate void ProcessInitHandler(object sender, EventArgs e);
        public event ProcessInitHandler OnInitiated;

        public delegate void ChromosomeUpdateHandler(object sender, StatusEventArgs e);
        public event ChromosomeUpdateHandler OnChromosomeProcessed;

        public delegate void ProgressChangedHandler(object sender, ProgressEventArgs e);
        public event ProgressChangedHandler OnProgressUpdate;

        public delegate void ProcessCompleteHandler(object sender, EventArgs e);
        public event ProcessCompleteHandler OnComplete;

        private bool Proceed = true;
        private int CurrentIteration = 0;
        private int CurrentGeneration = 0;
        private Random rand;
        public bool DynamicSize { get; set; }
        public int PopulationSize { get; set; }
        public int ChromosomeSize { get; set; }
        public double MutationRate { get; set; }
        public int GenerationSize { get; set; }
        public int NumberOfIteration { get; set; }
        public Dictionary<int, GeneticData> GeneticData { get; private set; }
        public List<TradeRule> MixedTradeRules { get; private set; }
        public IndicatorLibraryAdapter IndicatorLibraryAdapter { get; set; }
        public TradeSystem TradeSystem { get; private set; }
        public bool Seed { get; set; }
        public List<TradeChromosome> SeededChromosomes;
        private MachineLearnerTradeRule MachineLearnerTradeRule;
        private List<RuleJoinType> joinTypes;
        private List<StockPoint> stockPoints;
        private List<int> stockDaySplit;
        private GeneticResult BestResult = null;
        private int workDone = 0;
        private int workToDo = 0;
        public GeneticAlgorithmHelper(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            this.rand = new Random();
            this.DynamicSize = false;
            this.PopulationSize = 10;
            this.ChromosomeSize = 3;
            this.MutationRate = 0.01;
            this.GenerationSize = 10;
            this.NumberOfIteration = 1;
            this.GeneticData = new Dictionary<int, GeneticData>();
            this.MixedTradeRules = null;
            this.IndicatorLibraryAdapter = indicatorLibraryAdapter;
            this.Seed = false;
            this.SeededChromosomes = new List<TradeChromosome>();
            this.joinTypes = Enum.GetValues(typeof(RuleJoinType)).Cast<RuleJoinType>().ToList();
            this.stockPoints = this.IndicatorLibraryAdapter.StockPoints;
            this.stockDaySplit = this.IndicatorLibraryAdapter.EndOfDayIndex;
            SetData();
        }

        #region SetUp Data

        private void SetData()
        {
            this.MachineLearnerTradeRule = new MachineLearnerTradeRule(this.IndicatorLibraryAdapter);
            List<TradeRule>[] crossOverRules = this.MachineLearnerTradeRule.GenerateCrossOverConditions();
            List<TradeRule> buyRules = crossOverRules[0];
            List<TradeRule> sellRules = crossOverRules[1];
            AddGeneticData(buyRules, GeneticDataType.Buy);
            AddGeneticData(sellRules, GeneticDataType.Sell);
        }

        #endregion SetUp Data

        #region Initialize Iteration

        public void Init(TradeSystem tradeSystem, bool seedSystem)
        {
            var dt = DateTime.Now;

            Initiated();
            this.TradeSystem = tradeSystem;
            this.Proceed = true;
            this.workToDo = this.GenerationSize * this.NumberOfIteration;
            this.workDone = 0;
            this.CurrentIteration = 0;
            this.CurrentGeneration = 0;
            this.BestResult = null;
            List<TradeChromosome> currentChromosomes;

            if (seedSystem)
            {
                AddGeneticData(tradeSystem.BuyCondition.TradeRules);
                AddGeneticData(tradeSystem.SellCondition.TradeRules);

                TradeHelper tradeHelper = new TradeHelper(tradeSystem);
                tradeHelper.RunBacktest(this.IndicatorLibraryAdapter);

                double profit = Math.Round((tradeHelper.Capital + tradeHelper.TradeTable.Select().Sum(r => (double)r["Profit"])), 2);
                int losers = tradeHelper.Losers;
                int winners = tradeHelper.Winners;
                int totalTrades = tradeHelper.TradeCount;

                this.BestResult = new GeneticResult(this, tradeSystem, profit, winners, losers, totalTrades, 0, 0);
            }

            if (this.MixedTradeRules == null)
            {
                this.MixedTradeRules = this.MachineLearnerTradeRule.GenerateTradeRuleForGroups();
            }

            TradeType? tradeType = null;
            if (tradeSystem.Longs && !tradeSystem.Shorts) // long only
            {
                tradeType = TradeType.longTrade;
            }
            else if (!tradeSystem.Longs && tradeSystem.Shorts) // short only
            {
                tradeType = TradeType.shortTrade;
            }
            for (int i = 1; i <= this.NumberOfIteration; i++)
            {
                if (!Proceed)
                    break;

                if (i == 1)
                {
                    if (!seedSystem)
                    {
                        this.BestResult = null;
                    }
                }
                else
                {
                    if (!this.Seed)
                    {
                        this.BestResult = null;
                    }
                }

                currentChromosomes = new List<TradeChromosome>();
                this.CurrentIteration = i;
                UpdateProgress(string.Format("Processing Iteration: {0}", i));
                GenerateRandomGeneticData(); // generate data

                if (!Proceed)
                    break;

                for (int g = 1; g <= this.GenerationSize; g++)
                {
                    if (!currentChromosomes.Any())
                    {
                        currentChromosomes = GeneratePopulation(this.PopulationSize);
                    }

                    if (!Proceed)
                        break;

                    currentChromosomes = EvaluateFitness(currentChromosomes, i, g, tradeType).Select(d => d.Chromosome).Cast<TradeChromosome>().ToList();

                    var tst = DateTime.Now.Subtract(dt);

                    if (this.BestResult != null)
                    {
                        ChromosomeProcessed(this.BestResult);
                        int breedTake = (currentChromosomes.Count + 1) / 2;
                        currentChromosomes = currentChromosomes.Take(breedTake)
                                                               .Concat(GeneratePopulation(this.PopulationSize - breedTake))
                                                               .ToList();
                    }

                    this.workDone++;
                    this.CurrentGeneration = g;
                    UpdateProgress(string.Format("Completed - Iteration {0}, Generation {1}", i, g));
                }
            }

            Completed();
        }

        public List<TradeChromosome> GeneratePopulation(int size)
        {
            List<TradeChromosome> tradeChromosomes = new List<TradeChromosome>();

            Dictionary<int, GeneticData> buyGeneticData = this.GeneticData.Where(gd => gd.Value.Type != GeneticDataType.Sell).ToDictionary(gd => gd.Key, gd => gd.Value);
            Dictionary<int, GeneticData> sellGeneticData = this.GeneticData.Where(gd => gd.Value.Type != GeneticDataType.Buy).ToDictionary(gd => gd.Key, gd => gd.Value);

            TradeChromosome chromosome;
            for (int popSize = 0; popSize < size; popSize++)
            {
                if (!Proceed)
                    return tradeChromosomes;

                chromosome = new TradeChromosome();

                int loopBuySize = this.DynamicSize ? rand.Next(1, this.ChromosomeSize + 1) : this.ChromosomeSize;
                int loopSellSize = this.DynamicSize ? rand.Next(1, this.ChromosomeSize + 1) : this.ChromosomeSize;
                int loopSize = Math.Max(loopBuySize, loopSellSize);
                for (int i = 0; i < loopSize; i++)
                {
                    if (i < loopBuySize)
                    {
                        chromosome.BuyIndexes.Add(buyGeneticData.ElementAt(rand.Next(0, buyGeneticData.Count)).Key);
                        chromosome.BuyJoinIndexes.Add(GetRuleJoinIndex(i == loopBuySize - 1));
                    }

                    if (i < loopSellSize)
                    {
                        chromosome.SellIndexes.Add(sellGeneticData.ElementAt(rand.Next(0, sellGeneticData.Count)).Key);
                        chromosome.SellJoinIndexes.Add(GetRuleJoinIndex(i == loopSellSize - 1));
                    }

                }
                tradeChromosomes.Add(chromosome);
            }

            return tradeChromosomes;
        }

        #endregion Initialize Iteration

        #region Evaluate Fitness

        public List<dynamic> EvaluateFitness(List<TradeChromosome> chromosomes, int iteration, int generation, TradeType? tradeType = null)
        {
            List<dynamic> list = new List<dynamic>();
            if (chromosomes == null)
                return list;
            
            TradeChromosome bestChromosome = null;
            double currentFitnessValue = 0D;
            double bestFitnessValue = double.MinValue;
            EvalulatePotential evp = new EvalulatePotential(this.IndicatorLibraryAdapter);
            TradeInfoReport tradeInfoReport = null;
            TradeCondition buyCond;
            TradeCondition sellCond;

            var dt = DateTime.Now;

            List<TradeCondition> tcs = new List<TradeCondition>();
            for (int i = 0; i < chromosomes.Count; i++)
            {
                var bc = GetTradeCondition(chromosomes[i].BuyIndexes, chromosomes[i].BuyJoinIndexes);
                var sc = GetTradeCondition(chromosomes[i].SellIndexes, chromosomes[i].SellJoinIndexes);
                if(!tcs.Any(tc=>tc.Name == bc.Name))
                {
                    tcs.Add(bc);
                }
                if(!tcs.Any(tc=>tc.Name == sc.Name))
                {
                    tcs.Add(sc);
                }
            }
            evp.TradeRules(tcs);

            var tst = DateTime.Now.Subtract(dt).TotalMilliseconds;

            for (int i = 0; i < chromosomes.Count; i++)
            {
                if (!Proceed)
                {
                    return list;
                }
                buyCond = GetTradeCondition(chromosomes.First().BuyIndexes, chromosomes.First().BuyJoinIndexes);
                sellCond = GetTradeCondition(chromosomes.First().SellIndexes, chromosomes.First().SellJoinIndexes);
                tradeInfoReport = evp.EvalTrades(buyCond, sellCond, tradeType);

                currentFitnessValue = tradeInfoReport.TradeInfos.Sum(t => t.TradeType == TradeType.longTrade 
                                                        ? t.ExitPrice - t.EntryPrice
                                                        : t.EntryPrice - t.ExitPrice);

                //currentFitnessValue = EvaluateResultValue(chromosomes[i], tradeType);
                if (bestFitnessValue < currentFitnessValue)
                {
                    bestChromosome = chromosomes[i];
                    bestFitnessValue = currentFitnessValue;
                }
                dynamic d = new System.Dynamic.ExpandoObject();
                d.FitnessValue = currentFitnessValue;
                d.Chromosome = chromosomes[i];
                list.Add(d);
            }

            TradeSystem ts = this.TradeSystem;
            ts.BuyCondition = GetTradeCondition(bestChromosome.BuyIndexes, bestChromosome.BuyJoinIndexes);
            ts.SellCondition = GetTradeCondition(bestChromosome.SellIndexes, bestChromosome.SellJoinIndexes);

            //TradeHelper tradeHelper = new TradeHelper(ts);
            //tradeHelper.RunBacktest(this.IndicatorLibraryAdapter);
            //double profit = (tradeHelper.Capital + tradeHelper.TradeTable.Select().Sum(r => (double)r["Profit"]));
            //profit = Math.Round(Math.Max(profit, 0D), 2);

            //int losers = tradeHelper.Losers;
            //int winners = tradeHelper.Winners;
            //int totalTrades = tradeHelper.TradeCount;

            //GeneticResult result = new GeneticResult(this, ts, profit, winners, losers, totalTrades, iteration, generation);

            var l = tradeInfoReport.TradeInfos.Where(r => r.IsLoser);
            var w = tradeInfoReport.TradeInfos.Where(r => r.IsWinner);
            double profit = tradeInfoReport.TradeInfos.Sum(ti=> ti.TradeType == TradeType.longTrade ? ti.ExitPrice - ti.EntryPrice : ti.EntryPrice - ti.ExitPrice);
            int losers = l.Count();
            int winners = w.Count();
            int totalTrades = tradeInfoReport.TotalTrades;

            GeneticResult result = new GeneticResult(this, ts, profit, winners, losers, totalTrades, iteration, generation);

            if (this.BestResult == null || this.BestResult.FitnessValue < result.FitnessValue)
            {
                this.BestResult = result;
            }
            this.BestResult.Iteration = iteration;
            this.BestResult.Generation = generation;

            return list.OrderByDescending(d => d.FitnessValue).ToList();
        }

        /*
        private double EvaluateResultValue(TradeChromosome tradeChromosome, TradeType? tradeType = null)
        {
            if (tradeChromosome == null)
                return 0D;

            List<GeneticData> buyGeneticData = tradeChromosome.BuyIndexes.Select(bi => this.GeneticData[bi]).ToList();
            List<RuleJoinType> buyJoinIndexes = tradeChromosome.BuyJoinIndexes.Select(bj => this.joinTypes[bj]).ToList();

            List<GeneticData> sellGeneticData = tradeChromosome.SellIndexes.Select(si => this.GeneticData[si]).ToList();
            List<RuleJoinType> sellJoinIndexes = tradeChromosome.SellJoinIndexes.Select(sj => this.joinTypes[sj]).ToList();

            List<int> PotentialBuyTradeIndexes = buyGeneticData.Select(gd => gd.PotentialIndexes).SelectMany(pi => pi).Distinct().OrderBy(pi => pi).ToList();
            List<int> PotentialSellTradeIndexes = sellGeneticData.Select(gd => gd.PotentialIndexes).SelectMany(pi => pi).Distinct().OrderBy(pi => pi).ToList();
            
            Dictionary<int, double> buyTrades = EvaluateCondition(buyGeneticData, buyJoinIndexes, PotentialBuyTradeIndexes);
            Dictionary<int, double> sellTrades = EvaluateCondition(sellGeneticData, sellJoinIndexes, PotentialSellTradeIndexes);

            Dictionary<int, double> combinedTrades = EvaluateBuySell(buyTrades, sellTrades);
            
            double tradeValues = 0D;

            StockPoint entryPoint;
            StockPoint exitPoint;
            double fitness = 0;
            bool bIsLongTrade = false;
            int tradeCount = 0;
            for (int i = 1; i <= combinedTrades.Count; i += 2)
            {
                if (!Proceed)
                    return tradeValues;

                KeyValuePair<int, double> entryTrade = combinedTrades.ElementAt(i - 1);
                KeyValuePair<int, double> exitTrade = combinedTrades.ElementAt(i);

                bIsLongTrade = entryTrade.Value == 1;

                if (this.stockDaySplit.Contains(entryTrade.Key))
                {
                    entryPoint = this.stockPoints[entryTrade.Key];
                }
                else
                {
                    entryPoint = this.stockPoints[entryTrade.Key + 1];
                }
                if (this.stockDaySplit.Contains(exitTrade.Key))
                {
                    exitPoint = this.stockPoints[exitTrade.Key];
                }
                else
                {
                    exitPoint = this.stockPoints[exitTrade.Key + 1];
                }

                if (tradeType == null || ((tradeType.Value == TradeType.longTrade && bIsLongTrade) || (tradeType.Value == TradeType.shortTrade && !bIsLongTrade)))
                {
                    fitness = (exitPoint.Open - entryPoint.Open) * (entryTrade.Value > 0 ? 1 : -1);
                    tradeValues += fitness;
                    tradeCount++;
                }
            }

            return tradeValues;
        }

        private Dictionary<int, double> EvaluateCondition(List<GeneticData> geneticDataList, List<RuleJoinType> joinTypes, List<int> PotentialIndexes)
        {
            Dictionary<int, double> results = new Dictionary<int, double>();
            foreach (int pidx in PotentialIndexes)
            {
                if (EvaluateResult(geneticDataList.Select(gd => gd.Values[pidx]).ToList(), joinTypes))
                {
                    results.Add(pidx, 1D);
                }
            }
            
            return results;
        }

        private bool EvaluateResult(List<double> values, List<RuleJoinType> joinTypes)
        {
            List<bool> andResults = new List<bool>();
            List<bool> orList = new List<bool>();
            List<int> andList = null;
                
            for(int idx = 0; idx < joinTypes.Count; idx++)
            {
                if (joinTypes[idx] == RuleJoinType.and)
                {
                    if (andList == null)
                    {
                        andList = new List<int>();
                    }
                    andList.Add(idx);
                }
                else
                {
                    if (andList != null && andList.Any())
                    {
                        bool tr1 = getBoolValue(values[0], values[1], RuleJoinType.and);
                        bool tr2;
                        for (int id = 1; id < andList.Count; id++)
                        {
                            tr2 = getBoolValue(values[andList[id]], values[andList[id] + 1], RuleJoinType.and);
                            tr1 = tr1 && tr2;
                        }
                        andResults.Add(tr1);
                    }
                    if (idx == 0 || joinTypes[idx - 1] == RuleJoinType.or)
                    {
                        orList.Add(values[idx] == 1D);
                    }
                    andList = null;
                }
            }
            List<bool> andOrResultList = andResults.Concat(orList).ToList();
            bool result = false;
            for (int idx = 0; idx < andOrResultList.Count; idx++)
            {
                if (idx == 0)
                {
                    result = andOrResultList[idx];
                }
                else
                {
                    result = result || andOrResultList[idx];
                }
            }
            return result;

            //old code
            //bool result = true;
            //for (int i = 0; i < values.Count - 1; i++)
            //{
            //    result = joinTypes[i] == RuleJoinType.and ? result && (getBoolValue(values[i], values[i + 1], RuleJoinType.or))
            //                                              : result || (getBoolValue(values[i], values[i + 1], RuleJoinType.and));
            //}
        }
        
        private bool getBoolValue(double value1, double value2, RuleJoinType ruleJoinType)
        {
            return ruleJoinType == RuleJoinType.and ? value1 == 1D && value2 == 1D
                                                    : value1 == 1D || value2 == 1D;
        }

        private Dictionary<int, double> EvaluateBuySell(Dictionary<int, double> buyTrades, Dictionary<int, double> sellTrades)
        {
            if (buyTrades == null && sellTrades == null)
                return null;

            List<int> loopKeys = buyTrades.Keys.Concat(sellTrades.Keys).Distinct().OrderBy(k => k).ToList();
            Dictionary<int, double> tradeData = new Dictionary<int, double>();

            if (!loopKeys.Any())
                return tradeData;

            double value;
            bool buyEntered = false;
            bool sellEntered = false;

            int previousKey = loopKeys.First();
            int currentPoint = 0;
            foreach (int key in loopKeys)
            {
                if (key == this.stockDaySplit.Last())
                    break;

                currentPoint = this.stockDaySplit.First(cp => cp > previousKey);
                if (previousKey < currentPoint && key >= currentPoint)
                {
                    if (buyEntered)
                    {
                        buyEntered = false;
                        value = -1D;
                        tradeData.Add(currentPoint, value);
                    }

                    if (sellEntered)
                    {
                        sellEntered = false;
                        value = 1D;
                        tradeData.Add(currentPoint, value);
                    }
                }

                if (!buyEntered && buyTrades.ContainsKey(key) && buyTrades[key] == 1D && !this.stockDaySplit.Contains(key) && !this.stockDaySplit.Contains(key + 1))
                {
                    if (!sellEntered)
                    {
                        buyEntered = true;
                    }
                    sellEntered = false;
                    value = 1D;
                    tradeData.Add(key, value);
                }
                else if (!sellEntered && sellTrades.ContainsKey(key) && sellTrades[key] == 1D && !this.stockDaySplit.Contains(key) && !this.stockDaySplit.Contains(key + 1))
                {
                    if (!buyEntered)
                    {
                        sellEntered = true;
                    }
                    buyEntered = false;
                    value = -1D;
                    tradeData.Add(key, value);
                }

                previousKey = key;
            }

            if (buyEntered)
            {
                currentPoint = this.stockDaySplit.First(cp => cp > previousKey);
                value = -1D;
                tradeData.Add(currentPoint, value);
            }

            if (sellEntered)
            {
                currentPoint = this.stockDaySplit.First(cp => cp > previousKey);
                value = 1D;
                tradeData.Add(currentPoint, value);
            }

            return tradeData;
        }

        /

        #endregion Evaluate Fitness

        #region Breeding Section

        private List<TradeChromosome> Breed(List<TradeChromosome> chromosomes)
        {
            // Breed
            List<TradeChromosome> chromosomeList = new List<TradeChromosome>(chromosomes);
            List<int> mutateSlectedIndexes = new List<int>();
            List<TradeChromosome> mutationList = new List<TradeChromosome>();
            List<TradeChromosome> crossoverList = new List<TradeChromosome>();
            List<TradeChromosome> breedChromosomes = new List<TradeChromosome>();
            int mutateSelectedIndex = 0;
            int mutationLoop = (int)Math.Floor(this.MutationRate * chromosomeList.Count);
            if (this.MutationRate < 0.1)
            {
                for (int m = 0; m < Math.Max(mutationLoop, 1); m++)
                {
                    mutateSelectedIndex = rand.Next(0, chromosomeList.Count);
                    if (mutateSlectedIndexes.Contains(mutateSelectedIndex))
                    {
                        m--;
                        continue;
                    }
                    mutateSlectedIndexes.Add(mutateSelectedIndex);
                    mutationList.Add(chromosomeList[mutateSelectedIndex]);
                }
            }
            else
            {
                mutateSelectedIndex = rand.Next(0, chromosomeList.Count - mutationLoop);
                for (int m = mutateSelectedIndex; m < mutateSelectedIndex + mutationLoop; m++)
                {
                    mutateSlectedIndexes.Add(m);
                    mutationList.Add(chromosomeList[m]);
                }
            }
            breedChromosomes = Mutate(mutationList);

            foreach (TradeChromosome chromosome in chromosomeList)
            {
                if (!mutationList.Contains(chromosome))
                {
                    crossoverList.Add(chromosome);
                }
            }
            return breedChromosomes.Concat(CrossOver(crossoverList)).ToList();
        }

        private List<TradeChromosome> CrossOver(List<TradeChromosome> chromosomes)
        {
            if (chromosomes == null || chromosomes.Count < 2)
                return chromosomes;

            List<TradeChromosome> crossedOverChromosomes = new List<TradeChromosome>();

            TradeChromosome chromosome1;
            TradeChromosome chromosome2;
            TradeChromosome[] crossedChromosomes;
            for (int i = 0; i < chromosomes.Count; i += 2)
            {
                if (!Proceed)
                    return null;

                chromosome1 = chromosomes[i];
                if (i != chromosomes.Count - 1)
                {
                    chromosome2 = chromosomes[i + 1];
                }
                else
                {
                    chromosome2 = chromosomes[i - 1];
                }
                crossedChromosomes = CrossOverConditions(chromosome1, chromosome2);
                crossedOverChromosomes.Add(crossedChromosomes[0]);
                crossedOverChromosomes.Add(crossedChromosomes[1]);
            }
            if (crossedOverChromosomes.Count > chromosomes.Count)
            {
                crossedOverChromosomes.Remove(crossedOverChromosomes.Last());
            }

            return crossedOverChromosomes;
        }

        private TradeChromosome[] CrossOverConditions(TradeChromosome chromosome1, TradeChromosome chromosome2)
        {
            if (chromosome1 == null || chromosome2 == null)
                return null;

            TradeChromosome[] crossedOverChromosomes = new TradeChromosome[2];
            crossedOverChromosomes[0] = chromosome1;
            crossedOverChromosomes[1] = chromosome2;
            List<int>[] buyIndexes = SwapChromosomesIndexes(chromosome1.BuyIndexes, chromosome2.BuyIndexes);
            List<int>[] sellIndexes = SwapChromosomesIndexes(chromosome1.SellIndexes, chromosome2.SellIndexes);

            crossedOverChromosomes[0].BuyIndexes = buyIndexes[0];
            crossedOverChromosomes[0].SellIndexes = sellIndexes[0];

            crossedOverChromosomes[1].BuyIndexes = buyIndexes[1];
            crossedOverChromosomes[1].SellIndexes = sellIndexes[1];

            return crossedOverChromosomes;
        }

        private List<int>[] SwapChromosomesIndexes(List<int> indexes1, List<int> indexes2)
        {
            if (indexes1 == null || indexes2 == null)
                return null;

            List<int> idx1 = new List<int>(indexes1);
            List<int> idx2 = new List<int>(indexes2);
            int loopVal = Math.Min(idx1.Count, idx2.Count);
            bool swapped = false;

            for (int i = 0; i < loopVal; i++)
            {
                if (!indexes1.Contains(idx2[i]) && !indexes2.Contains(idx1[i]))
                {
                    idx1[i] = indexes2[i];
                    idx2[i] = indexes1[i];

                    swapped = true;
                }

                if (swapped)
                    break;
            }

            return new List<int>[] { idx1, idx2 };
        }

        private List<TradeChromosome> Mutate(List<TradeChromosome> chromosomes)
        {
            List<TradeChromosome> mutatedTradeChromosomes = new List<TradeChromosome>();

            for (int i = 0; i < chromosomes.Count; i++)
            {
                if (!Proceed)
                    return null;

                TradeChromosome chromosome = chromosomes[i];

                if (rand.Next(0, 2) == 0)
                {
                    mutatedTradeChromosomes.Add(MutateCondition(chromosome));
                }
                else
                {
                    mutatedTradeChromosomes.Add(MutateRule(chromosome));
                }
            }

            return mutatedTradeChromosomes;
        }

        private TradeChromosome MutateCondition(TradeChromosome chromosome)
        {
            TradeChromosome conditionedChromosome = chromosome;

            conditionedChromosome.BuyJoinIndexes = ConditionTweak(chromosome.BuyJoinIndexes);
            conditionedChromosome.SellJoinIndexes = ConditionTweak(chromosome.SellJoinIndexes);

            return conditionedChromosome;
        }

        private List<int> ConditionTweak(List<int> indexes)
        {
            if (indexes.Count == 1)
                return indexes;

            List<int> returnval = new List<int>(indexes);

            int index = rand.Next(0, indexes.Count - 1);

            returnval[index] = returnval[index] == 1 ? 2 : 1;

            return returnval;
        }

        private TradeChromosome MutateRule(TradeChromosome chromosome)
        {
            if (chromosome == null)
                return null;

            TradeChromosome tweakedChromosome = chromosome;

            tweakedChromosome.BuyIndexes = RuleTweak(chromosome.BuyIndexes);
            tweakedChromosome.SellIndexes = RuleTweak(chromosome.SellIndexes);

            return tweakedChromosome;
        }

        private List<int> RuleTweak(List<int> indexes)
        {
            List<int> returnval = new List<int>(indexes);
            int addSubtract = rand.Next(0, 2) == 0 ? -1 : 1;
            int tweakIndex = rand.Next(0, indexes.Count);
            int tweakIndexVal = indexes[tweakIndex] + addSubtract;
            while (true)
            {
                if (tweakIndexVal <= 0)
                    addSubtract = 1;
                if (tweakIndexVal >= this.GeneticData.Count - 1)
                    addSubtract = -1;
                tweakIndexVal = tweakIndexVal + addSubtract;

                if (!indexes.Contains(tweakIndexVal))
                {
                    if (this.GeneticData[tweakIndexVal].Type == GeneticDataType.Both)
                    {
                        returnval[tweakIndex] = tweakIndexVal;
                        break;
                    }
                }
            }

            return returnval;
        }

        #endregion Breeding Section

        #region Helpers

        private int GetRuleJoinIndex(bool lastindex = false)
        {
            if (lastindex)
                return 0;

            return (rand.Next(0, 100) < 50 ? 1 : 2);
        }

        private void GenerateRandomGeneticData()
        {
            RandomPicker randPicker = new RandomPicker(0, this.MixedTradeRules.Count);
            List<TradeRule> tradeRules = new List<TradeRule>();
            TradeRule tradeRule;
            int counter = Math.Min(this.PopulationSize * 2, this.MixedTradeRules.Count);
            List<int> pickIndexes = new List<int>();
            for (int i = 0; i < counter; i++)
            {
                if (!Proceed)
                    return;

                tradeRule = this.MixedTradeRules[randPicker.GetNextPick()];
                tradeRules.Add(tradeRule);
            }

            AddGeneticData(tradeRules);
        }

        public void AddGeneticData(List<TradeRule> tradeRules, GeneticDataType geneticDataType = GeneticDataType.Both)
        {
            //PotentialTradeDataInfo[] geneticDataInfos;
            GeneticData geneticdata;
            List<TradeRule> filteredTradeRule = tradeRules.Where(tr => !this.GeneticData.Any(gd => gd.Value.TradeRule.Name == tr.Name)).ToList();
            if (filteredTradeRule.Any())
            {
                //geneticDataInfos = EvalAll(filteredTradeRule, null, null);
                for (int i = 0; i < filteredTradeRule.Count; i++)
                {
                    if (!Proceed)
                        return;

                    geneticdata = new GeneticData()
                    {
                        Type = geneticDataType,
                        TradeRule = filteredTradeRule[i],
                        //Values = geneticDataInfos[i].Values,
                        //PotentialIndexes = geneticDataInfos[i].PotentialIndexes
                    };
                    this.GeneticData.Add(this.GeneticData.Count, geneticdata);
                }
            }
        }

        public PotentialTradeDataInfo[] EvalAll(List<TradeRule> tradeRules, DateTime? startDate, DateTime? endDate)
        {
            DateTime sDate = startDate ?? DateTime.MinValue;
            DateTime eDate = endDate ?? DateTime.MaxValue;

            EvalulatePotential eval = new EvalulatePotential(this.IndicatorLibraryAdapter);
            return eval.Evaluate(tradeRules, sDate, eDate);
        }

        public TradeCondition GetTradeCondition(List<int> conditionindexes, List<int> joinindexes)
        {
            TradeCondition tradeCondition = new TradeCondition();
            List<TradeRule> tradeRules = new List<TradeRule>();
            List<RuleJoinType> joinTypes = new List<RuleJoinType>();

            TradeRule tradeRule;
            RuleJoinType joinType;
            for (int i = 0; i < Math.Min(conditionindexes.Count, joinindexes.Count); i++)
            {
                tradeRule = this.GeneticData[conditionindexes[i]].TradeRule;
                joinType = this.joinTypes[joinindexes[i]];
                tradeRules.Add(tradeRule);
                joinTypes.Add(joinType);
            }

            tradeCondition.TradeRules = tradeRules;
            tradeCondition.RuleJoinTypes = joinTypes;

            return tradeCondition;
        }

        #endregion Helpers

        #region Update/Cancel Function

        private void Initiated()
        {
            if (OnInitiated == null)
                return;

            OnInitiated(this, new EventArgs());
        }

        private void UpdateProgress(string message)
        {
            if (OnProgressUpdate == null)
                return;

            int progress = (int)((double)this.workDone / this.workToDo * 100);
            ProgressEventArgs args = new ProgressEventArgs(progress, message);
            OnProgressUpdate(this, args);
        }

        internal void Cancel()
        {
            this.Proceed = false;
        }

        private void ChromosomeProcessed(GeneticResult geneticResult)
        {
            if (OnChromosomeProcessed == null)
                return;
            StatusEventArgs args;

            args = new StatusEventArgs(geneticResult);
            OnChromosomeProcessed(this, args);
        }

        private void Completed()
        {
            if (OnComplete == null)
                return;

            OnComplete(this, new EventArgs());
        }

        #endregion Update/Cancel
    }
    */
}
