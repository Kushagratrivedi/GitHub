namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// The trade system for backtesting. (Buy/Sell rules, financial settings, and backtest period/method)
    /// </summary>
    public class TradeSystem
    {
        /// <summary>
        /// Initializes a new instance of the TradeSystem class.
        /// Default constructor needed for serialization.
        /// </summary>
        public TradeSystem()
        {
            this.BuyCondition = null;
            this.SellCondition = null;
        }

        /// <summary>
        /// Initializes a new instance of the TradeSystem class.
        /// </summary>
        /// <param name="buyCondition">Buy conditions for the trade system</param>
        /// <param name="sellCondition">Sell conditions for the trade system</param>
        /// <param name="startDate">Date to start trading</param>
        /// <param name="endDate">Date to stop trading</param>
        /// <param name="financialSettings">Financial settings for the trade system</param>
        /// <param name="shorts">Short trades are allowed.</param>
        /// <param name="longs">Long trades are allowed.</param>
        public TradeSystem(
            TradeCondition buyCondition, 
            TradeCondition sellCondition, 
            DateTime startDate, 
            DateTime endDate,  
            FinancialSettings financialSettings,
            bool shorts, 
            bool longs)
        {
            this.BuyCondition = buyCondition;
            this.SellCondition = sellCondition;
            this.StartDate = startDate;
            this.EndDate = endDate;
            this.FinancialSettings = financialSettings;
            this.Shorts = shorts;
            this.Longs = longs;
        }

        /// <summary>
        /// Gets or sets the buy conditions for the trade system.
        /// </summary>
        public TradeCondition BuyCondition
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the sell conditions for the trade system.
        /// </summary>
        public TradeCondition SellCondition
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the date to start trading.
        /// </summary>
        public DateTime StartDate
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the date to stop trading.
        /// </summary>
        public DateTime EndDate
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets a value indicating whether long trades are allowed.
        /// </summary>
        public bool Longs
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets a value indicating whether short trades are allowed.
        /// </summary>
        public bool Shorts
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the financial settings for the trade system.
        /// </summary>
        public FinancialSettings FinancialSettings
        {
            get;
            set;
        }

        /// <summary>
        /// Creates a copy of this trade system.
        /// </summary>
        /// <returns>A copy of this trade system.</returns>
        public TradeSystem Clone(bool copyTradeConditions = true)
        {
            TradeSystem clone = new TradeSystem();
            if (copyTradeConditions)
            {
                Chromosome chromosome = Chromosome.DeepClone(new Chromosome()
                {
                    BuyCondition = this.BuyCondition,
                    SellCondition = this.SellCondition
                });

                clone.BuyCondition = chromosome.BuyCondition;
                clone.SellCondition = chromosome.SellCondition;
            }
            clone.StartDate = this.StartDate;
            clone.EndDate = this.EndDate;
            clone.FinancialSettings = new FinancialSettings(this.FinancialSettings.DollarsPerPoint, this.FinancialSettings.EquityPerContract, this.FinancialSettings.InitialCapital, this.FinancialSettings.MaxContracts, this.FinancialSettings.MaxDrawdown, this.FinancialSettings.RoundTripCommission);
            clone.Shorts = this.Shorts;
            clone.Longs = this.Longs;

            return clone;
        }

        /// <summary>
        /// Creates a copy of this trade system with new trade conditions.
        /// </summary>
        /// <param name="buyCondition">The buy condition.</param>
        /// <param name="sellCondition">The sell condition.</param>
        /// <returns>A copy of this trade system with new trade conditions.</returns>
        public TradeSystem CloneWithNewRules(TradeCondition buyCondition, TradeCondition sellCondition)
        {
            TradeSystem clone = this.Clone();
            clone.BuyCondition = buyCondition;
            clone.SellCondition = sellCondition;
            return clone;
        }

        public TradeSystem CloneWithOutConditions()
        {
            TradeSystem clone = this.Clone(false);
            return clone;
        }
    }
}
