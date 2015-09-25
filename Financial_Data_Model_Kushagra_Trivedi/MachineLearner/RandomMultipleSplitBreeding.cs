namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Implementation of a genetic breeding algorithm splitting the chromosomes at random locations.
    /// </summary>
    public class RandomMultipleSplitBreeding : IGeneticBreeding
    {
        /// <summary>
        /// Probability for breeding the chromosomes based on fitness.
        /// </summary>
        private double[] probability;

        /// <summary>
        /// A random number generator.
        /// </summary>
        private Random rand;

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
        private void CrossBuyTradeConditions(TradeSystemChromosome chromosome1, TradeSystemChromosome chromosome2)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
            
        }

        /// <summary>
        /// Gets the split locations based on 
        /// </summary>
        /// <param name="numSplits">The number of splits locations.</param>
        /// <param name="length">The length of the chromosomes.</param>
        /// <returns>An array of split locations.</returns>
        private int[] GetSplitLocations(int numSplits, int length)
        {
            int[] splitLocations = new int[numSplits];
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************

            return splitLocations;
        }

        /// <summary>
        /// Cross the sell trade conditions.
        /// </summary>
        /// <param name="chromosome1">First chromosome to cross.</param>
        /// <param name="chromosome2">Second chromosome to cross.</param>
        private void CrossSellTradeConditions(TradeSystemChromosome chromosome1, TradeSystemChromosome chromosome2)
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
