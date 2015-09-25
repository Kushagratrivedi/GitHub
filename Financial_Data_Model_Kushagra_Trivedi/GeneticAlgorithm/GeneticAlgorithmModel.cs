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
    public class ProgressEventArgs : EventArgs
    {
        public int Progress { get; private set; }
        public string Message { get; private set; }
        public ProgressEventArgs(int progress, string message)
        {
            this.Progress = progress;
            this.Message = message;
        }
    }

    public class StatusEventArgs : EventArgs
    {
        public GeneticResult GeneticResult { get; private set; }
        public StatusEventArgs(GeneticResult geneticResult)
        {
            this.GeneticResult = geneticResult;
        }
    }

    public class GeneticAlgorithmModel
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
        //public bool DynamicSize { get; set; }
        public int MinSize { get; set; }
        public int MaxSize { get; set; }
        public int PopulationSize { get; set; }
        //public int ChromosomeSize { get; set; }
        public double MutationRate { get; set; }
        public int GenerationSize { get; set; }
        public int NumberOfIteration { get; set; }
        //public Dictionary<int, GeneticData> GeneticData { get; private set; }
        //public List<TradeRule> MixedTradeRules { get; private set; }
        public List<TradeRule> BuyRules { get; set; }
        public List<TradeRule> BuyRulesCache;
        public List<TradeRule> SellRules { get; set; }
        public List<TradeRule> SellRulesCache;
        public IndicatorLibraryAdapter IndicatorLibraryAdapter { get; set; }
        public EvalulateTrade Evaluate { get; set; }
        public bool Seed { get; set; }
        public List<TradeChromosome> SeededChromosomes;
        private MachineLearnerTradeRule MachineLearnerTradeRule;
        private List<RuleJoinType> joinTypes;
        private List<StockPoint> stockPoints;
        private List<int> stockDaySplit;
        private GeneticResult BestResult = null;
        private int workDone = 0;
        private int workToDo = 0;
        public GeneticAlgorithmModel(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            this.rand = new Random();
            this.PopulationSize = 10;
            this.MinSize = 5;
            this.MaxSize = 5;
            this.MutationRate = 0.01;
            this.GenerationSize = 10;
            this.NumberOfIteration = 1;
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
            this.BuyRulesCache = crossOverRules[0];
            this.SellRulesCache = crossOverRules[1];
        }

        #endregion SetUp Data

        #region Initialize Iteration

        public void Init(TradeSystem tradeSystem, bool seedSystem, double stopOutValue)
        {
            Initiated();
            this.Proceed = true;
            this.workToDo = this.GenerationSize * this.NumberOfIteration;
            this.workDone = 0;
            this.CurrentIteration = 0;
            this.CurrentGeneration = 0;
            this.BestResult = null;
            List<Chromosome> currentChromosomes;
            List<TradeRule> mixedTradeRules;

            Chromosome seedChromosome = null;
            if (seedSystem)
            {
                seedChromosome = new Chromosome() { BuyCondition = tradeSystem.BuyCondition, SellCondition = tradeSystem.SellCondition };
            }

            //if (this.MixedTradeRules == null)
            //{
            //    this.MixedTradeRules = this.MachineLearnerTradeRule.GenerateTradeRuleForGroups(this.PopulationSize);
            //    this.BuyRules = this.BuyRules.Concat(this.MixedTradeRules).ToList();
            //    this.SellRules = this.SellRules.Concat(this.MixedTradeRules).ToList();
            //}

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

                mixedTradeRules = this.MachineLearnerTradeRule.GenerateTradeRuleForGroups(this.PopulationSize);
                this.BuyRules = this.BuyRulesCache.Concat(mixedTradeRules).ToList();
                this.SellRules = this.SellRulesCache.Concat(mixedTradeRules).ToList();

                if (i == 1 && !seedSystem)
                {
                    this.BestResult = null;
                }
                else if (!this.Seed)
                {
                    this.BestResult = null;
                }

                this.CurrentIteration = i;
                currentChromosomes = new List<Chromosome>();
                UpdateProgress(string.Format("Processing Iteration: {0}", i));

                if (!Proceed)
                    break;

                for (int g = 1; g <= this.GenerationSize; g++)
                {
                    if (!currentChromosomes.Any())
                    {
                        currentChromosomes = GeneratePopulation();
                        if(seedChromosome != null)
                        {
                            currentChromosomes[0] = seedChromosome;
                        }
                    }

                    if (!Proceed)
                        break;

                    currentChromosomes = EvaluateFitness(tradeSystem, currentChromosomes, i, g, stopOutValue, tradeType).Select(d => d.Chromosome).Cast<Chromosome>().ToList();
                    
                    if (this.BestResult != null)
                    {
                        ChromosomeProcessed(this.BestResult);
                        currentChromosomes = Breed(currentChromosomes);
                    }

                    this.workDone++;
                    this.CurrentGeneration = g;
                    UpdateProgress(string.Format("Completed - Iteration {0}, Generation {1}", i, g));
                }
            }

            Completed();
        }

        public List<Chromosome> GeneratePopulation()
        {
            List<Chromosome> tradeChromosomes = new List<Chromosome>();
            
            RandomPicker buyPicker = new RandomPicker(0, this.BuyRules.Count);
            RandomPicker sellPicker = new RandomPicker(0, this.SellRules.Count);
            Chromosome chromosome;
            for (int popSize = 0; popSize < this.PopulationSize; popSize++)
            {
                if (!Proceed)
                    return tradeChromosomes;

                chromosome = new Chromosome();
                chromosome.BuyCondition = new TradeCondition();
                chromosome.SellCondition = new TradeCondition();

                int loopBuySize = rand.Next(this.MinSize, this.MaxSize + 1);
                int loopSellSize = rand.Next(this.MinSize, this.MaxSize + 1);
                int loopSize = Math.Max(loopBuySize, loopSellSize);
                for (int i = 0; i < loopSize; i++)
                {
                    if (i < loopBuySize)
                    {
                        chromosome.BuyCondition.TradeRules.Add(this.BuyRules[buyPicker.GetNextPick()]);
                        chromosome.BuyCondition.RuleJoinTypes.Add(GetRuleJoinType(i == loopBuySize - 1));
                    }

                    if (i < loopSellSize)
                    {
                        chromosome.SellCondition.TradeRules.Add(this.SellRules[sellPicker.GetNextPick()]);
                        chromosome.SellCondition.RuleJoinTypes.Add(GetRuleJoinType(i == loopSellSize - 1));
                    }
                }
                tradeChromosomes.Add(chromosome);
            }

            return tradeChromosomes;
        }

        #endregion Initialize Iteration

        #region Evaluate Fitness

        public List<dynamic> EvaluateFitness(TradeSystem tradeSystem, List<Chromosome> chromosomes, int iteration, int generation, double stopOutValue, TradeType? tradeType = null)
        {
            List<dynamic> list = new List<dynamic>();
            if (chromosomes == null)
                return list;

            Chromosome bestChromosome = null;
            double currentFitnessValue = 0D;
            double bestFitnessValue = double.MinValue;

            this.Evaluate.SetTradeRules(chromosomes.SelectMany(c=>c.BuyCondition.TradeRules.Concat(c.SellCondition.TradeRules)));

            Trades trades = null;
            TradeCondition buyCond;
            TradeCondition sellCond;
            
            int totalTrades = 0;
            int winners = 0;
            int losers = 0;
            int stopout = 0;
            Chromosome chr = null;
            for (int i = 0; i < chromosomes.Count; i++)
            {
                chr = chromosomes[i];

                buyCond = chr.BuyCondition;
                sellCond = chr.SellCondition;
                trades = this.Evaluate.EvalTrades(tradeSystem, buyCond, sellCond, stopOutValue);
                double endingCapital = Math.Round((tradeSystem.FinancialSettings.InitialCapital + trades.TradeTable.Select().Sum(r => (double)r["Profit"])), 2);  
                currentFitnessValue = endingCapital > 0 ? endingCapital : 0;

                if (!Proceed)
                {
                    return list;
                }

                if (bestFitnessValue < currentFitnessValue)
                {
                    bestChromosome = chr;
                    bestFitnessValue = currentFitnessValue;
                    winners = this.Evaluate.Winners;
                    losers = this.Evaluate.Losers;
                    stopout = this.Evaluate.StopOuts;
                    totalTrades = trades.Count();
                }

                dynamic d = new System.Dynamic.ExpandoObject();
                d.FitnessValue = currentFitnessValue;
                d.Chromosome = chr;
                list.Add(d);
            }

            TradeSystem ts = tradeSystem.CloneWithOutConditions();
            ts.BuyCondition = bestChromosome.BuyCondition;
            ts.SellCondition = bestChromosome.SellCondition;

            GeneticResult result = new GeneticResult(ts, bestFitnessValue, winners, losers, totalTrades, stopout, iteration, generation);

            if (this.BestResult == null || this.BestResult.FitnessValue < result.FitnessValue)
            {
                this.BestResult = result;
            }
            this.BestResult.Iteration = iteration;
            this.BestResult.Generation = generation;

            return list.OrderByDescending(d => d.FitnessValue).ToList();
        }
        
        #endregion Evaluate Fitness

        #region Breeding Section

        private List<Chromosome> Breed(List<Chromosome> chromosomes)
        {
            if (chromosomes == null || !chromosomes.Any())
                return chromosomes;

            // Breed
            List<Chromosome> returnList = new List<Chromosome>();

            RandomPicker rp = new RandomPicker(0, chromosomes.Count);
            double mutationRate = this.MutationRate;
            int[] mutationIndex = Enumerable.Range(0, (int)this.MutationRate * chromosomes.Count).Select(val => rp.GetNextPick()).OrderBy(val => val).ToArray();
            List<Chromosome> mutationChromosomes = Enumerable.Range(0, mutationIndex.Count()).Select(idx => chromosomes[mutationIndex[idx]]).ToList();
            List<Chromosome> crossoverChromosomes = chromosomes.Where(chr => !mutationChromosomes.Contains(chr)).ToList();

            Chromosome chromosome1;
            Chromosome chromosome2;
            for (int i = 0; i < mutationChromosomes.Count; i++)
            {
                chromosome1 = mutationChromosomes[i];
                if (chromosome1.BuyCondition.TradeRules.Count > 1 && chromosome1.SellCondition.TradeRules.Count > 1 && i % 2 == 0)
                {
                    returnList.Add(Chromosome.TweakJoinConditions(chromosome1));
                }
                else
                {
                    returnList.Add(Chromosome.TweakRules(chromosome1, this.BuyRules, this.SellRules));
                }
            }

            rp = new RandomPicker(0, crossoverChromosomes.Count);
            for (int i = 0; i < crossoverChromosomes.Count; i = i + 2)
            {
                chromosome1 = crossoverChromosomes[rp.GetNextPick()];
                chromosome2 = crossoverChromosomes[rp.GetNextPick()];

                returnList = returnList.Concat(Chromosome.SwapChromosomes(chromosome1, chromosome2)).ToList();
            }

            return returnList;
        }
        
        #endregion Breeding Section

        #region Helpers

        private RuleJoinType GetRuleJoinType(bool lastindex = false)
        {
            if (lastindex)
                return RuleJoinType.none;

            return (rand.Next(0, 100) < 50 ? RuleJoinType.or : RuleJoinType.and);
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
            this.Evaluate.Cancel();
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
}