namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// Represents a purchased contract.
    /// </summary>
    public class Buy
    {
        /// <summary>
        /// Initializes a new instance of the Buy class.
        /// </summary>
        /// <param name="stockPoint">The stock point for the purchase.</param>
        /// <param name="tradeTypes">The type of the buy: short or long.</param>
        /// <param name="shares">The number of shares purchased.</param>
        public Buy(StockPoint stockPoint, TradeType tradeType, int shares)
        {
            this.Date = stockPoint.Date;
            this.Time = stockPoint.PointDateTime.AddMinutes(-5).ToString("HH:mm");
            this.Price = stockPoint.Open;
            this.TradeType = tradeType;
            this.Shares = shares;
        }

        /// <summary>
        /// Gets the date of the purchase.
        /// </summary>
        public string Date
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the time of the purchase.
        /// </summary>
        public string Time
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the number of shares purchased.
        /// </summary>
        public int Shares
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the price at the time of the buy.
        /// </summary>
        public double Price
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the type of the buy: short or long.
        /// </summary>
        public TradeType TradeType
        {
            get;
            private set;
        }
    }
}
