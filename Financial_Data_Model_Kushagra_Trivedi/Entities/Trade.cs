namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// The class that handles a trade.
    /// </summary>
    public class Trade
    {
        /// <summary>
        /// The buy portion of the trade.
        /// </summary>
        private Buy buy = null;

        /// <summary>
        /// The sell portion of the trade.
        /// </summary>
        private Sell sell = null;

        /// <summary>
        /// The maximum points lost from the entry point before selling.
        /// </summary>
        private double maxDrawdown;

        /// <summary>
        /// The amount of money made/lost per point.
        /// </summary>
        private double profitPerPoint;

        /// <summary>
        /// Commisssion for a round trip buy and sell.
        /// </summary>
        private double commission;

        /// <summary>
        /// Initializes a new instance of the Trade class.
        /// </summary>
        /// <param name="maxDrawdown">The maximum drawdown for the trade.</param>
        /// <param name="profitPerPoint">The amount of money made/lost per point.</param>
        /// <param name="commission">The amount of round trip commission to be paid on this trade.</param>
        /// <param name="startingCapital">The starting capital for the trade</param>
        public Trade(double maxDrawdown, double profitPerPoint, double commission, double startingCapital)
        {

            this.maxDrawdown = maxDrawdown;
            this.profitPerPoint = profitPerPoint;
            this.commission = commission;
            this.StartingCapital = startingCapital;
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
        }

        #region Public Properties

        /// <summary>
        /// Gets the capital at the begining of the trade.
        /// </summary>
        public double StartingCapital
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the capital at the end of the trade.
        /// </summary>
        public double EndingCapital
        {
            get
            {
                return this.StartingCapital + this.Profit;
            }
        }

        /// <summary>
        /// Gets the status of the trade.
        /// </summary>
        public TradeStatus Status
        {
            get
            {
                if (this.buy == null)
                {
                    return TradeStatus.buying;
                }

                if (this.sell == null)
                {
                    return TradeStatus.selling;
                }

                return TradeStatus.complete;
            }
        }

        /// <summary>
        /// Gets the entry date of the trade.
        /// </summary>
        public string EntryDate
        {
            get
            {
                if (this.Status != TradeStatus.buying)
                {
                    return this.buy.Date;
                }
                else
                {
                    throw new Exception("Trade has not yet been entered.");
                }
            }
        }

        /// <summary>
        /// Gets the entry time of the trade.
        /// </summary>
        public string EntryTime
        {
            get
            {
                if (this.Status != TradeStatus.buying)
                {
                    return this.buy.Time;
                }
                else
                {
                    throw new Exception("Trade has not yet been entered.");
                }
            }
        }

        /// <summary>
        /// Gets the entry price of the trade.
        /// </summary>
        public double EntryPrice
        {
            get
            {
                if (this.Status != TradeStatus.buying)
                {
                    return this.buy.Price;
                }
                else
                {
                    throw new Exception("Trade has not yet been entered.");
                }
            }
        }

        /// <summary>
        /// Gets the number of contracts purchased.
        /// </summary>
        public int Quantity
        {
            get
            {
                if (this.Status != TradeStatus.buying)
                {
                    return this.buy.Shares;
                }
                else
                {
                    throw new Exception("Trade has not yet been entered.");
                }
            }
        }
        
        /// <summary>
        /// Gets the exit date of the trade.
        /// </summary>
        public string ExitDate
        {
            get
            {
                if (this.Status == TradeStatus.complete)
                {
                    return this.sell.Date;
                }
                else
                {
                    throw new Exception("Trade has not yet been exited.");
                }
            }
        }

        /// <summary>
        /// Gets the exit time of the trade.
        /// </summary>
        public string ExitTime
        {
            get
            {
                if (this.Status == TradeStatus.complete)
                {
                    return this.sell.Time;
                }
                else
                {
                    throw new Exception("Trade has not yet been exited.");
                }
            }
        }

        /// <summary>
        /// Gets the exit price of the trade.
        /// </summary>
        public double ExitPrice
        {
            get
            {
                if (this.Status == TradeStatus.complete)
                {
                    return this.sell.Price;
                }
                else
                {
                    throw new Exception("Trade has not yet been exited.");
                }
            }
        }

        /// <summary>
        /// Gets a value indicating whether the trade was stopped out.
        /// </summary>
        public bool StoppedOut
        {
            get
            {
                if (this.Status == TradeStatus.complete)
                {
                    return this.sell.Stopped;
                }
                else
                {
                    throw new Exception("Trade has not yet been exited.");
                }
            }
        }

        /// <summary>
        /// Gets the profit from the trade using banker's rounding.
        /// </summary>
        public double Profit
        {
            get
            {
                double buyPrice = 0D;
                double sellPrice = 0D;
                if (this.buy.TradeType == Entities.TradeType.shortTrade)
                {
                    buyPrice = this.buy.Price;
                    sellPrice = this.sell.Price;
                }
                else
                {
                    buyPrice = this.sell.Price;
                    sellPrice = this.buy.Price;
                }
                return Math.Round(((buyPrice - sellPrice) * this.Quantity * this.profitPerPoint) - (this.commission * this.Quantity), 2);

                /// *********************************************
                /// GDBCup - This needs to be coded
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets the type of the trade: long or short.
        /// </summary>
        public TradeType TradeType
        {
            get
            {
                return this.buy.TradeType;
            }
        }

        /// <summary>
        /// Gets the percent return on starting capital.
        /// </summary>
        public double PercentProfit
        {
            get
            {
                return (double)decimal.Round((decimal)((this.Profit / this.buy.Price) * 100), 2);
                /// *********************************************
                /// GDBCup - This needs to be coded
                /// *********************************************
            }
        }

        #endregion

        /// <summary>
        /// Checks to make sure the trade hasn't stopped out. If it has it sells.
        /// </summary>
        /// <param name="stockPoint">The stock point to check the trade against.</param>
        /// <returns>True if it stopped the trade.</returns>
        public bool CheckStop(StockPoint stockPoint)
        {
            return this.Status == TradeStatus.complete;
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
        }

        /// <summary>
        /// Manually enter the trade.
        /// </summary>
        /// <param name="stockPoint">The stock point to purchase the contract at.</param>
        /// <param name="tradeTypes">The type of trade: long or short.</param>
        /// <param name="shares">The number of shares to buy.</param>
        public void Enter(StockPoint stockPoint, TradeType tradeType, int shares)
        {
            this.buy = new Buy(stockPoint, tradeType, shares);
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
        }

        /// <summary>
        /// Manually exit the trade.
        /// </summary>
        /// <param name="stockPoint">The stock point to sell the contract at.</param>
        /// <param name="stopped">Is thw exit stopped out</param>
        public void Exit(StockPoint stockPoint, int periodDecrement, bool stopped = true)
        {
            this.sell = new Sell(stockPoint, periodDecrement, stopped);
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
        }
    }
}
