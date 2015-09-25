namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// A class for keeping track of a simple moving average.
    /// </summary>
    public class SimpleMovingAverage
    {
        /// <summary>
        /// The mfvvalues to be avearged.
        /// </summary>
        private Queue<double> values;

        /// <summary>
        /// Initializes a new instance of the SimpleMovingAverage class.
        /// </summary>
        /// <param name="period">The number of mfvvalues to keep.</param>
        public SimpleMovingAverage(int period)
        {
            this.Period = period;
            this.values = new Queue<double>();
        }

        /// <summary>
        /// Gets the number of mfvvalues to average.
        /// </summary>
        public int Period
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the number of mfvvalues currently held.
        /// </summary>
        public int ValueCount
        {
            get
            {
                return this.values.Count;
            }
        }

        /// <summary>
        /// Adds a value to be avearaged.
        /// </summary>
        /// <param name="newValue">The value to be added.</param>
        public void AddValue(double newValue)
        {
            this.values.Enqueue(newValue);
            if (this.values.Count > this.Period)
            {
                this.values.Dequeue();
            }
        }

        /// <summary>
        /// Returns the moving average over the period or valueCount if it is lower.
        /// </summary>
        /// <returns>A moving average of the contained points.</returns>
        public double MovingAverage()
        {
            return this.values.Average();
        }

        /// <summary>
        /// Calculates the standard deviation for the moving average.
        /// Uses standard deviation of the sample per Glossary of Technical Indicators.pdf
        /// </summary>
        /// <returns>The standard deviation of the moving average.</returns>
        public double StandardDeviation()
        {
            double sd = 0;
            foreach (double value in this.values)
            {
                sd += (double)Math.Pow((value - this.MovingAverage()), 2);
            }

            return (double)Math.Sqrt(sd / this.Period);
        }

        /// <summary>
        /// Calculates the mean deviation for the moving average.
        /// </summary>
        /// <returns>the mean deviation of the mfvvalues in the Moving Average</returns>
        public double MeanDeviation()
        {
            // a running total of the mean deviation
            double meanDeviation = 0;

            // this is a clone of all of the mfvvalues in the mfvvalues queue
            Queue<double> tempValues = new Queue<double>(this.values.ToArray());

            // get the absoulte value of SMA - value for each value in the queue
            // add that to the meanDeviation
            while (tempValues.Count > 0)
            {
                meanDeviation += Math.Abs(tempValues.Dequeue() - this.MovingAverage());
            }

            // divide meanDeviation by the number of mfvvalues looked at
            meanDeviation /= this.values.Count;

            return meanDeviation;
        }

        /// <summary>
        /// This method returns the array of mfvvalues which the SMA holds
        /// </summary>
        /// <returns>Inner array of the object</returns>
        public double[] ToArray()
        {
            return this.values.ToArray();
        }
    }
}
