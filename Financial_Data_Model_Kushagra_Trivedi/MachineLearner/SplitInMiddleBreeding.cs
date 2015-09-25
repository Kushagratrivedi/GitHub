namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Implementation of a genetic breeding algorithm splitting the chromosomes in the middle.
    /// </summary>
    public class SplitInMiddleBreeding : IGeneticBreeding
    {
        /// <summary>
        /// Probability for breeding the chromosomes based on fitness.
        /// </summary>
        private double[] probability;

        /// <summary>
        /// Returns a new population based on the breeding algorithm.
        /// </summary>
        /// <param name="oldChromosomes">The chromosomes of a population.</param>
        /// <returns>A new population.</returns>
        public TradeSystemChromosome[] Breed(TradeSystemChromosome[] oldChromosomes)
        {
            TradeSystemChromosome[] newChromosomes = new TradeSystemChromosome[oldChromosomes.Length];
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
            return newChromosomes;
        }

        /// <summary>
        /// Cross the buy trade conditions.
        /// </summary>
        /// <param name="chromosome1">First chromosome to cross.</param>
        /// <param name="chromosome2">Second chromosome to cross.</param>
        /// <param name="whereToSplit">The place to split the chromosomes.</param>
        private void CrossBuyTradeConditions(TradeSystemChromosome chromosome1, TradeSystemChromosome chromosome2, int whereToSplit)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// ********************************************* 
        }

        /// <summary>
        /// Cross the sell trade conditions.
        /// </summary>
        /// <param name="chromosome1">First chromosome to cross.</param>
        /// <param name="chromosome2">Second chromosome to cross.</param>
        /// <param name="whereToSplit">The place to split the chromosomes.</param>
        private void CrossSellTradeConditions(TradeSystemChromosome chromosome1, TradeSystemChromosome chromosome2, int whereToSplit)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Gets a position at random based on the probability.
        /// </summary>
        /// <returns>The position to reference.</returns>
        private int GetRandomPosition()
        {
            int position = 0;
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
            return position;
        }
    }
}
