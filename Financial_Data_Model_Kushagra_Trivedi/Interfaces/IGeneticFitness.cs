namespace FDMTermProject.Interfaces
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.GeneticAlgorithmNS;

    /// <summary>
    /// An interface for an implementation of a fitness algorithm.
    /// </summary>
    public interface IGeneticFitness
    {
        /// <summary>
        /// Sets the fitness of each chromosome.
        /// </summary>
        /// <param name="chromosomeList">The chromosomes of a population.</param>
        void SetFitness(TradeSystemChromosome[] chromosomeList);
    }
}
