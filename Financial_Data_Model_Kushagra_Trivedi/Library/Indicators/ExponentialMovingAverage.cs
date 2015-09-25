namespace FDMTermProject.Library.Indicators
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Technical Indicator that calcualates a exponential moving average
    /// </summary>
    public class ExponentialMovingAverage : ITechnicalIndicator
    {
        /// <summary>
        /// An Exponential Moving Average
        /// </summary>
        private FDMTermProject.Library.ExponentialMovingAverage ema;

        /// <summary>
        /// The number of stockpoints received
        /// </summary>
        private int valuesReceived;

        /// <summary>
        /// The number of points needed for the indicator to produce mfvvalues
        /// </summary>
        private int minimumPoints;

        /// <summary>
        /// Initializes a new instance of the ExponentialMovingAverage class
        /// </summary>
        /// <param name="period">The period to calculate the ExponentialMovingAverage for</param>
        public ExponentialMovingAverage(int period)
        {
            this.Values = null;
            this.minimumPoints = period;
            this.valuesReceived = 0;
            this.ema = new Library.ExponentialMovingAverage(period);
            this.Keys = new string[1];
            this.Values = new double[1];
            this.Keys[0] = "EMA(" + period + ")";
        }

        /// <summary>
        /// Gets the mfvvalues of the indicator
        /// </summary>
        public double[] Values { get; private set; }

        /// <summary>
        /// Gets a value indicating whether ExponentialMovingAverage has been given enough StockPoints to produce mfvvalues
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
        /// Gets the number of keys which ExponentialMovingAverage produces
        /// </summary>
        public int NumberOfKeys
        {
            get { return 1; }
        }

        /// <summary>
        /// Advance the indicator with a new StockPoint
        /// </summary>
        /// <param name="point">The StockPoint to add</param>
        public void AddPoint(StockPoint point)
        {
            this.valuesReceived++;
            this.ema.AddValue(point.Close);

            if (this.IsReady)
            {
                this.Values[0] = this.ema.MovingAverage;
            }
        }
    }
}
