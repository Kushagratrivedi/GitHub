namespace FDMTermProject.Library.Indicators
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using FDMTermProject.Interfaces;

    /// <summary>
    /// Technical Indicator that calcualates RelativeStrengthIndex
    /// </summary>
    public class RelativeStrengthIndex : ITechnicalIndicator
    {
        /// <summary>
        /// The number of keys the indicator produces
        /// </summary>
        private const int NUMBEROFKEYS = 1;

        /// <summary>
        /// The number of stockpoints received
        /// </summary>
        private int valuesReceived;

        /// <summary>
        /// The number of points needed for the indicator to produce mfvvalues
        /// </summary>
        private int minimumPoints;

        /// <summary>
        /// A SMA to keep track of gains
        /// </summary>
        private FDMTermProject.Library.ExponentialMovingAverage gains;

        /// <summary>
        /// A SMA to keep track of losses
        /// </summary>
        private FDMTermProject.Library.ExponentialMovingAverage losses;

        /// <summary>
        /// Keep track of the previous point to compare to
        /// </summary>
        private StockPoint previousPoint;
  
        /// <summary>
        /// Initializes a new instance of the RelativeStrengthIndex class.
        /// </summary>
        /// <param name="period">The period to calculate the RelativeStrengthIndex for</param>
        public RelativeStrengthIndex(int period)
        {
            this.Values = null;
            this.minimumPoints = period + 1;
            this.valuesReceived = 0;
            this.Keys = new string[NUMBEROFKEYS];
            this.Values = new double[NUMBEROFKEYS];
            this.Keys[0] = "RSI(" + period + ")";

            this.gains = new FDMTermProject.Library.ExponentialMovingAverage(period);
            this.losses = new FDMTermProject.Library.ExponentialMovingAverage(period);
            this.previousPoint = null;
        }

        /// <summary>
        /// Gets the mfvvalues of RelativeStrengthIndex for the most recent StockPoint
        /// </summary>
        public double[] Values { get; private set; }

        /// <summary>
        /// Gets a value indicating whether RelativeStrengthIndex has been given enough StockPoints to produce mfvvalues
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
        /// Gets the number of keys which RelativeStrengthIndex produces
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

            if (this.previousPoint != null)
            {
                if (this.previousPoint.Close > point.Close)
                {
                    this.losses.AddValue(this.previousPoint.Close - point.Close);
                    this.gains.AddValue(0);
                }
                else if (this.previousPoint.Close < point.Close)
                {
                    this.gains.AddValue(point.Close - this.previousPoint.Close);
                    this.losses.AddValue(0);
                }

                if (this.IsReady)
                {
                    this.Values[0] = 100 - (100 / (1 + (this.gains.MovingAverage / this.losses.MovingAverage)));
                }
            }

            this.previousPoint = point;
        }
    }
}