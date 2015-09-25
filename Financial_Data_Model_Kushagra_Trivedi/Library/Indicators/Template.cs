/*
namespace FDMTermProject.Library.Indicators
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Technical Indicator that calcualates *IndicatorName*
    /// </summary>
    public class *IndicatorName* : ITechnicalIndicator
    {
        /// <summary>
        /// The number of keys the indicator produces
        /// </summary>
        private const int NUMBEROFKEYS = //Set the number of keys;

        /// <summary>
        /// The number of stockpoints received
        /// </summary>
        private int valuesReceived;

        /// <summary>
        /// The number of points needed for the indicator to produce mfvvalues
        /// </summary>
        private int minimumPoints;

        /// <summary>
        /// A SMA to keep track of mfvvalues
        /// </summary>
        private FDMTermProject.Library.SimpleMovingAverage sma; // Not needed for every indicator
  
        /// <summary>
        /// Initializes a new instance of the *IndicatorName* class.
        /// </summary>
        /// <param name="period">The period to calculate the *IndicatorName* for</param>
        public *IndicatorName*(int period)
        {
            this.Values = null;
            this.minimumPoints = // minimumPoints is one more than offset in IndicatorLib;
            this.valuesReceived = 0;
            this.Keys = new string[NUMBEROFKEYS];
            this.Values = new double[NUMBEROFKEYS];
            this.Keys[0] = "*IndicatorName*(" + period + ")";
  
            this.sma = new Library.SimpleMovingAverage(period);
        }

        /// <summary>
        /// Gets the mfvvalues of *IndicatorName* for the most recent StockPoint
        /// </summary>
        public double[] Values { get; private set; }
 
        /// <summary>
        /// Gets a value indicating whether *IndicatorName* has been given enough StockPoints to produce mfvvalues
        /// </summary>
        public bool IsReady
        {
            get { return this.valuesReceived >= this.minimumPoints; }
        }
  
        /// <summary>
        /// Gets the names corresponding to each value in Values
        /// </summary>
        public string[] Keys
        {
            get;
            private set;
        }
  
        /// <summary>
        /// Gets the number of keys which *IndicatorName* produces
        /// </summary>
        public int NumberOfKeys
        {
            get { return NUMBEROFKEYS; }
        }

        /// <summary>
        /// Advance the indicator with a new StockPoint
        /// </summary>
        /// <param name="point">The StockPoint to add</param>
        public void AddPoint(StockPoint point)
        {
            this.valuesReceived++;
            this.sma.AddValue(point.Close);

            if (this.IsReady)
            {
                // Put calculations here and assign to Values
            }
        }
    }
}
*/