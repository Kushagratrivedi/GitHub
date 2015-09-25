namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.Data;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Genetic Algorithm class
    /// </summary>
    public class GeneticAlgorithm
    {
        /// <summary>
        /// Number of generations to run.
        /// </summary>
        private int generations;

        /// <summary>
        /// The chromosomes making up the population.
        /// </summary>
        private TradeSystemChromosome[] chromosomes;

        /// <summary>
        /// The best chromosome
        /// </summary>
        private TradeSystemChromosome bestChromosome = null;
        
        /// <summary>
        /// The algorithm to breed with.
        /// </summary>
        private IGeneticBreeding breedingAlgorithm;

        /// <summary>
        /// The algorithm to mutuate with.
        /// </summary>
        private IGeneticMutating mutatingAlgorithm;

        /// <summary>
        /// The algorithm to set fitness with.
        /// </summary>
        private IGeneticFitness fitnessAlgorithm;

        /// <summary>
        /// The number of seeded chromosomes.
        /// </summary>
        private int seeded = 0;

        /// <summary>
        /// The progress of the genetic algorithm.
        /// </summary>
        private double progress;

        /// <summary>
        /// Initializes a new instance of the GeneticAlgorithm class.
        /// </summary>
        /// <param name="chromosomes">The number of chromosomes in the population.</param>
        /// <param name="generations">The number of generations to run.</param>
        /// <param name="chromosomeSize">The size of the chromosomes.</param>
        /// <param name="tradeSystemTemplate">A template to make TradeSystemChromosomes with</param>
        /// <param name="indicatorHelper">The indicator helper for generating new chromosomes.</param>
        /// <param name="breedingAlgorithm">The algorithm to breed with.</param>
        /// <param name="mutatingAlgorithm">The algorithm to mutate with.</param>
        /// <param name="fitnessAlgorithm">The algorithm to calculate fitness.</param>
        public GeneticAlgorithm(int chromosomes, int generations, int chromosomeSize, TradeSystem tradeSystemTemplate, IndicatorHelper indicatorHelper, IGeneticBreeding breedingAlgorithm, IGeneticMutating mutatingAlgorithm, IGeneticFitness fitnessAlgorithm)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Gets or sets the current best chromosome
        /// </summary>
        public TradeSystemChromosome BestChromosome
        {
            get
            {
                return this.bestChromosome;
            }

            set
            {
                if (this.bestChromosome != null && this.bestChromosome.Fitness > value.Fitness && (this.bestChromosome.FitnessWinners + this.bestChromosome.FitnessLosers) > 0)
                {
                    throw new Exception("BestChromosome fitness downgrade from" + this.bestChromosome.Fitness + "to " + value.Fitness);
                }

                this.bestChromosome = value;
            }
        }
        
        /// <summary>
        /// Run the genetic algorithm.
        /// </summary>
        /// <param name="worker">The background worker object.</param>
        /// <param name="e">Do work event args.</param>
        public void Run(BackgroundWorker worker, DoWorkEventArgs e)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Seed the population with some chromosomes.
        /// </summary>
        /// <param name="chromosomes">The seed chromosomes.</param>
        public void Seed(TradeSystemChromosome[] chromosomes)
        {
            /// *********************************************
            /// GDBCup - Code goes here
            /// *********************************************
        }

        /// <summary>
        /// Gets the chromosome with the besst fitness.
        /// </summary>
        /// <returns>The most fit chromosome.</returns>
        private TradeSystemChromosome GetBest()
        {
            return this.chromosomes.OrderByDescending(x => x.Fitness).First();
        }

        /// <summary>
        /// Generate a random population.
        /// </summary>
        private void GeneratePopulation()
        {
            foreach (TradeSystemChromosome chromosome in this.chromosomes.Skip(this.seeded))
            {
                chromosome.Randomize();
            }
        }
    }
}
