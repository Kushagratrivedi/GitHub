using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject.Library
{
    public class ChaikinMoneyFlow
    {
        // money flow multiplier
        private Queue<double> mfvvalues;

        // money flow volumes
        private Queue<double> volumevalues;

        // Period for chaikin money flow
        public int Period { get; private set; }

        // total count of mfvvalues
        public int MFVValueCount { get { return mfvvalues.Count; } }

        // total count of volumevalues
        public int VolumeValueCount { get { return volumevalues.Count; } }

        // constructor
        public ChaikinMoneyFlow(int period)
        {
            this.Period = period;
            this.mfvvalues = new Queue<double>();
            this.volumevalues = new Queue<double>();
        }

        // add new value to the mfvvalues queue
        // deque the old value if needed
        public void AddMFVValue(double newValue)
        {
            this.mfvvalues.Enqueue(newValue);
            if (this.MFVValueCount > this.Period)
            {
                this.mfvvalues.Dequeue();
            }
        }

        // add new volume to the queue
        public void AddVolumeValue(double newValue)
        {
            this.volumevalues.Enqueue(newValue);
            if (this.VolumeValueCount > this.Period)
            {
                this.volumevalues.Dequeue();
            }
        }

        // calculate chaikin money flow
        public double CalculateChaikinMoneyFlow()
        {
            var sum = this.volumevalues.Sum();
            return this.mfvvalues.Sum()/ (sum == 0 ? 1 : sum);
        }
    }
}
