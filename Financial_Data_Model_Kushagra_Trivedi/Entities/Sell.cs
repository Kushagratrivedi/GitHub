namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// Represents a sold contract.
    /// </summary>
    public class Sell
    {
        /// <summary>
        /// Initializes a new instance of the Sell class.
        /// </summary>
        /// <param name="stockPoint">The stock point to sell at.</param>
        public Sell(StockPoint stockPoint)
        {
            this.Price = stockPoint.Open;
            this.Date = stockPoint.Date;
            this.Time = stockPoint.PointDateTime.AddMinutes(-5).ToString("HH:mm");
            this.Stopped = false;
        }

        /// <summary>
        /// Initializes a new instance of the Sell class for a stop.
        /// </summary>
        /// <param name="stockPoint">The stock point the trade was stopped out at.</param>
        /// <param name="price">The price the trade was stopped out at.</param>
        //public Sell(StockPoint stockPoint, double price, int periodDecrement)
        //{
        //    this.Price = price;
        //    this.Date = stockPoint.Date;
        //    //this.Time = stockPoint.Time;
        //    this.Time = stockPoint.PointDateTime.AddMinutes(-periodDecrement).ToString("HH:mm");
        //    this.Stopped = true;
        //}
        public Sell(StockPoint stockPoint, int periodDecrement, bool stopped)
        {
            this.Price = stockPoint.Open;
            this.Date = stockPoint.Date;
            //this.Time = stockPoint.Time;
            this.Time = stockPoint.PointDateTime.AddMinutes(-periodDecrement).ToString("HH:mm");
            this.Stopped = stopped;
        }

        /// <summary>
        /// Gets the date of the sell.
        /// </summary>
        public string Date
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the time of the sell.
        /// </summary>
        public string Time
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets a value indicating whether a trade was stopped out.
        /// </summary>
        public bool Stopped
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the price of the sell.
        /// </summary>
        public double Price
        {
            get;
            private set;
        }
    }
}
