namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Entities.TradeRules;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Implementation of a genetic mutating algorithm for trade conditions.
    /// </summary>
    public class TradeConditionMutation : IGeneticMutating
    {
        /// <summary>
        /// Random number generator.
        /// </summary>
        private Random rand;

        /// <summary>
        /// The mutation rate.
        /// </summary>
        private double rate;

        /// <summary>
        /// The indicator helper to generate a new trade rule with.
        /// </summary>
        private IndicatorHelper indicatorHelper;

        /// <summary>
        /// Initializes a new instance of the TradeConditionMutation class.
        /// </summary> 
        /// <param name="rate">The mutation rate.</param>
        /// <param name="indicatorHelper">The indicator helper to mutate with.</param>
        public TradeConditionMutation(double rate, IndicatorHelper indicatorHelper)
        {
            this.indicatorHelper = indicatorHelper;
            this.rate = rate;
            this.rand = new Random();
        }

        /// <summary>
        /// Mutates a population based on the mutating algorithm.
        /// </summary>
        /// <param name="chromosomeList">The chromosomes to be mutated.</param>
        public void Mutate(ref TradeSystemChromosome[] chromosomeList)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Mutate the chrmosome.
        /// </summary>
        /// <param name="tradeSystemChromosome">The chromosome to mutate.</param>
        private void Mutate(TradeSystemChromosome tradeSystemChromosome)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }
    }
}
