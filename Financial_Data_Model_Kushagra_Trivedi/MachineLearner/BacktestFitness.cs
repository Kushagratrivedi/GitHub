namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;
    using FDMTermProject.Library;

    /// <summary>
    /// Implementation of a fitness algorithm using backtest results.
    /// </summary>
    public class BacktestFitness : IGeneticFitness
    {
        /// <summary>
        /// The indicator library adapter to run the backtest with.
        /// </summary>
        private IndicatorLibraryAdapter indicatorLibraryAdapter;
        
        /// <summary>
        /// Initializes a new instance of the BacktestFitness class.
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The indicator library adapter to run the backtest with.</param>
        public BacktestFitness(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            this.indicatorLibraryAdapter = indicatorLibraryAdapter;
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Sets the fitness of each chromosome based on backtest results.
        /// </summary>
        /// <param name="chromosomeList">Array of chromosomes to backtest.</param>
        public void SetFitness(TradeSystemChromosome[] chromosomeList)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }
    }
}
