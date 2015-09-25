namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// A class for keeping track of a Exponential moving average.
    /// </summary>
    public class ExponentialMovingAverage
    {
        /// <summary>
        /// Simple Moving Average to prime the Exponential Moving Average.
        /// </summary>
        private SimpleMovingAverage simpleMovingAverage;

        /// <summary>
        /// Initializes a new instance of the ExponentialMovingAverage class.
        /// </summary>
        /// <param name="period">The period for the average to cover.</param>
        public ExponentialMovingAverage(int period)
        {
            this.simpleMovingAverage = new SimpleMovingAverage(period);
            this.Period = period;
            this.MovingAverage = 0;
        }

        /// <summary>
        /// Gets the exponential moving average of the data given.
        /// </summary>
        public double MovingAverage
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the period the average to covers.
        /// </summary>
        public double Period
        {
            get;
            private set;
        }

        /// <summary>
        /// Add a value to the average
        /// </summary>
        /// <param name="value">The value to add to the average.</param>
        public void AddValue(double value)
        {
            if (this.simpleMovingAverage.Period > this.simpleMovingAverage.ValueCount)
            {
                this.simpleMovingAverage.AddValue(value);
                this.MovingAverage = this.simpleMovingAverage.MovingAverage();
            }
            else
            {
                this.MovingAverage = ((2 / (this.Period + 1)) * (value - this.MovingAverage)) + this.MovingAverage;
            }
        }
    }
}
