using FDMTermProject.Entities;
using FDMTermProject.Entities.TradeRules;
using FDMTermProject.Library;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject
{
    public class MachineLearnerTradeRule
    {
        RandomSingleton randomGenerator;
        public string[] Headers { get; private set; }
        private List<List<string>> Groups;
        private Dictionary<string, List<double>> FixedValues;
        List<IndicatorCompareType> indicatorsTypes;
        public MachineLearnerTradeRule(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            this.indicatorsTypes = Enum.GetValues(typeof(IndicatorCompareType)).Cast<IndicatorCompareType>().ToList();
            this.randomGenerator = new RandomSingleton();
            this.Headers = indicatorLibraryAdapter.IndicatorLocations.Keys.Select(k => k.ToString()).ToArray();
            this.FixedValues = TechnicalIndicatorClass.GetIndicatorsDefaultCrossOvers(this.Headers);
            this.Groups = TechnicalIndicatorClass.GetGroupsOfIndicators(this.Headers);
        }

        public List<TradeRule>[] GenerateCrossOverConditions()
        {
            Dictionary<string, List<double>> crossovervalues = this.FixedValues;
            List<TradeRule>[] tradeRules = new List<TradeRule>[2];
            List<TradeRule> buyRules = new List<TradeRule>();
            List<TradeRule> sellRules = new List<TradeRule>();

            TradeRule buyRule;
            TradeRule sellRule;
            
            KeyValuePair<string, List<double>> kv;
            for (int i = 0; i < crossovervalues.Count; i++ )
            {
                kv = crossovervalues.ElementAt(i);
                buyRule = TradeRuleFactory.GenerateTradeRule(kv.Key, IndicatorCompareType.UP_XOVER, "#" + Math.Min(kv.Value[0], kv.Value[1]));
                buyRules.Add(buyRule);

                sellRule = TradeRuleFactory.GenerateTradeRule(kv.Key, IndicatorCompareType.DOWN_XUNDER, "#" + Math.Max(kv.Value[0], kv.Value[1]));
                sellRules.Add(sellRule);
            }

            tradeRules[0] = buyRules;
            tradeRules[1] = sellRules;

            return tradeRules;
        }

        public List<TradeRule> GenerateTradeRuleForGroups(int size)
        {
            var dt = DateTime.Now;

            int totalCount = 0;
            List<RandomPicker>  groupItemPicker = new List<RandomPicker>(); 
            foreach (List<string> group in this.Groups)
            {
                groupItemPicker.Add(new RandomPicker(0, group.Count));
                totalCount = totalCount + group.Count;
            }
            RandomPicker groupPicker = new RandomPicker(0, totalCount);

            List<TradeRule> tradeRules = new List<TradeRule>();

            int groupIndexer;
            int selectedGroupIndex;
            int indicator1Index;
            int indicator2Index;
            List<string> selectedGroup;
            TradeRule tradeRule;
            for (int i = 0; i < size; i++ )
            {
                groupIndexer = groupPicker.GetNextPick();
                selectedGroupIndex = GetGroupIndex(groupIndexer);
                selectedGroup = this.Groups[selectedGroupIndex];

                indicator1Index = groupItemPicker[selectedGroupIndex].GetNextPick();
                indicator2Index = groupItemPicker[selectedGroupIndex].GetNextPick();

                tradeRule = TradeRuleFactory.GenerateTradeRule(selectedGroup[indicator1Index], GetJoinType(), selectedGroup[indicator2Index]);

                tradeRules.Add(tradeRule);
            }

            var time = DateTime.Now.Subtract(dt).TotalMilliseconds;

            return tradeRules;
        }

        private int GetGroupIndex(int indexer)
        {
            bool loop = true;
            int index = -1;
            int sum = 0;
            while(loop)
            {
                sum = sum + this.Groups[++index].Count;
                if(sum > indexer)
                {
                    loop = false;
                }
            }

            return index;
        }


        private IndicatorCompareType GetJoinType()
        {
            return this.indicatorsTypes[this.randomGenerator.GetRandom().Next(0, 4)];
        }

        /*
        public List<TradeRule> GenerateTradeRuleForGroups()
        {
            List<List<string>> indicatorGroups = this.Groups;
            List<TradeRule> tradeRules = new List<TradeRule>();

            foreach(List<string> group in indicatorGroups)
            {
                foreach (List<string> g in GetCombinations(group))
                {
                    List<TradeRule> rules = GetRules(g);
                    if (rules != null && rules.Any())
                    {
                        foreach(TradeRule tr in rules)
                        {
                            tradeRules.Add(tr);
                        }
                    }
                }
            }

            return tradeRules;
        }

        private static List<List<string>> GetCombinations(List<string> group)
        {
            List<List<string>> listOfLists = new List<List<string>>();
            foreach (string str in group)
            {
                foreach (string st in group)
                {
                    if (str != st)
                    {
                        if (!listOfLists.Any(l => l.Contains(str) && l.Contains(st)))
                        {
                            listOfLists.Add(new List<string>() { str, st });
                        }
                    }
                }
            }

            return listOfLists;
        }

        private static List<TradeRule> GetRules(List<string> group)
        {
            if (group.Count != 2)
                return null;

            List<TradeRule> list = new List<TradeRule>();
            foreach (IndicatorCompareType indicator in Enum.GetValues(typeof(IndicatorCompareType)).Cast<IndicatorCompareType>())
            {
                list.Add(TradeRuleFactory.GenerateTradeRule(group[0], indicator, group[1]));
            }

            return list;
        }

        */
    }
}
