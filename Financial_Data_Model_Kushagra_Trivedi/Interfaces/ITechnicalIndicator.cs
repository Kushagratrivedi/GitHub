namespace FDMTermProject.Interfaces
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;

    /// <summary>
    /// This interface defines the behavior of technical indicators.
    /// </summary>
    public interface ITechnicalIndicator
    {
        /// <summary>
        /// Gets the mfvvalues of the indicator for the most recent stockpoint
        /// </summary>
        double[] Values { get; }

        /// <summary>
        /// Gets a value indicating whether the indicator has been given
        /// enough StockPoints to produce mfvvalues
        /// </summary>
        bool IsReady { get; }

        /// <summary>
        /// Gets a list of the keys which correspond to the Values outputted
        /// </summary>
        string[] Keys { get; }

        /// <summary>
        /// Gets the number of keys the indicator produces
        /// </summary>
        int NumberOfKeys { get; }

        /// <summary>
        /// Add a stockpoint to the indicator, producing a new value
        /// </summary>
        /// <param name="point">Advance the indicator to the next StockPoint</param>
        void AddPoint(StockPoint point);
    }
}