namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Entities.TradeRules;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// A chromosome based on a trade system.
    /// </summary>
    public class TradeSystemChromosome
    {
        /// <summary>
        /// Chromosome size.
        /// </summary>
        private int size;

        /// <summary>
        /// The indicator helper for randomizing the chromosome.
        /// </summary>
        private IndicatorHelper indicatorHelper;

        /// <summary>
        /// Initializes a new instance of the TradeSystemChromosome class.
        /// </summary>
        /// <param name="tradeSystem">The trade system the chromosome represents.</param>
        /// <param name="indicatorHelper">The indicator helper for randomizing the chromosome.</param>
        /// <param name="size">Chromosome size.</param>
        public TradeSystemChromosome(TradeSystem tradeSystem, IndicatorHelper indicatorHelper, int size)
        {
            this.size = size;
            this.ChromosomeValue = tradeSystem;
            this.indicatorHelper = indicatorHelper;
        }

        /// <summary>
        /// Gets or sets the trade system the chromosome represents.
        /// </summary>
        public TradeSystem ChromosomeValue
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the value of the chromosome's fitness.
        /// </summary>
        public double Fitness 
        { 
            get; 
            set; 
        }

        /// <summary>
        /// Gets or sets the ending capital balance.
        /// </summary>
        public double Capital
        {
            get;
            set;
        }

        /// <summary>
        /// Gets the value of the fitness statistics.
        /// </summary>
        public string FitnessStats
        {
            get
            {
                string stats;
                /// *********************************************
                /// GDBCup - Code goes here
                /// *********************************************

                return "somestring"; /// Revise once coded up
            }
        }

        /// <summary>
        /// Gets or sets a unique identifier for the chromosome.
        /// </summary>
        public string UniqueID
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the fitness value for number of winning trades.
        /// </summary>
        public int FitnessWinners
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the fitness value for number of losing trades.
        /// </summary>
        public int FitnessLosers
        {
            get;
            set;
        }

        /// <summary>
        /// Randomizes the chromosome.
        /// </summary>
        public void Randomize()
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Creates a new instance of the chromosome with the same mfvvalues.
        /// </summary>
        /// <returns>A new instance of the chromosome with the same mfvvalues.</returns>
        internal TradeSystemChromosome Clone()
        {
            TradeSystemChromosome tc = new TradeSystemChromosome(
                new TradeSystem(
                    this.ChromosomeValue.BuyCondition.Clone(), 
                    this.ChromosomeValue.SellCondition.Clone(),
                    this.ChromosomeValue.StartDate,
                    this.ChromosomeValue.EndDate,
                    this.ChromosomeValue.FinancialSettings,
                    this.ChromosomeValue.Shorts,
                    this.ChromosomeValue.Longs), 
                this.indicatorHelper, 
                this.size);
            tc.FitnessLosers = this.FitnessLosers;
            tc.FitnessWinners = this.FitnessWinners;
            tc.Fitness = this.Fitness;
            return tc;
        }

        /// <summary>
        /// Creates a new instance of the chromosome with the same mfvvalues but blank fitness.
        /// </summary>
        /// <returns>A new instance of the chromosome with the same mfvvalues but blank fitness.</returns>
        internal TradeSystemChromosome CloneWithoutFitness()
        {
            return new TradeSystemChromosome(
                new TradeSystem(
                    this.ChromosomeValue.BuyCondition.Clone(), 
                    this.ChromosomeValue.SellCondition.Clone(),
                    this.ChromosomeValue.StartDate,
                    this.ChromosomeValue.EndDate,
                    this.ChromosomeValue.FinancialSettings,
                    this.ChromosomeValue.Shorts,
                    this.ChromosomeValue.Longs), 
                this.indicatorHelper, 
                this.size);
        }
    }
}
