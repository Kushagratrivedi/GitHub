namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;

    /// <summary>
    /// Class contains buy/sell rule generator methods, populate trade base on input data and input buy/sell rule
    /// </summary>
    public class TradeHelper
    {        
        /// <summary>
        /// The trades collection.
        /// </summary>
        private Trades trades = new Trades();

        /// <summary>
        /// The trade system.
        /// </summary>
        private TradeSystem tradeSystem;

        /// <summary>
        /// The current trade the trade helper is in.
        /// </summary>
        private Trade currentTrade;

        /// <summary>
        /// Indicates a buy needs to be executed.
        /// </summary>
        private bool impendingBuy = false;

        /// <summary>
        /// The type of the impending buy (long or short).
        /// </summary>
        private TradeType impendingBuyType = TradeType.longTrade;

        /// <summary>
        /// Indicates a sell needs to be executed.
        /// </summary>
        private bool impendingSell = false;

        /// <summary>
        /// The previous stock point for end of day exits.
        /// </summary>
        private StockPoint point;

        /// <summary>
        /// Private value for DailyReturns cache.
        /// </summary>
        private List<DailyReturn> dailyReturns = null;
        
        /// <summary>
        /// Private value for AverageDailyReturn cache.
        /// </summary>
        private double? averageDailyReturn = null;
        
        /// <summary>
        /// Private value for DailyReturnStandardDeviation cache.
        /// </summary>
        private double? dailyReturnStandardDeviation = null;
        
        /// <summary>
        /// Initializes a new instance of the TradeHelper class.
        /// </summary>
        /// <param name="tradeSystem">The trading system to use.</param>
        public TradeHelper(TradeSystem tradeSystem)
        {
            this.tradeSystem = tradeSystem;
            this.Capital = tradeSystem.FinancialSettings.InitialCapital;
            this.currentTrade = new Trade(this.tradeSystem.FinancialSettings.MaxDrawdown, this.tradeSystem.FinancialSettings.DollarsPerPoint, this.tradeSystem.FinancialSettings.RoundTripCommission, this.Capital);
        }

        #region Public Properties
        /// <summary>
        /// Gets the number of trades.
        /// </summary>
        public int TradeCount
        {
            get
            {
                return this.trades.Count;
            }
        }

        /// <summary>
        /// Gets the number of winning trades.
        /// </summary>
        public int Winners
        {
            get
            {
                return this.trades.Count(t => t.Profit > 0);
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets the number of losing trades.
        /// </summary>
        public int Losers
        {
            get
            {
                return this.trades.Count(t => t.Profit < 0);
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets the number of losing trades.
        /// </summary>
        public int StopOuts
        {
            get
            {
                return this.trades.Count(t => t.StoppedOut);
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets the amount of money to start with.
        /// </summary>
        public double Capital
        {
            get;
            private set;
        }

        /// <summary>
        /// Gets the table of trades.
        /// </summary>
        public DataTable TradeTable
        {
            get
            {
                return this.trades.TradeTable;
            }
        }

        /// <summary>
        /// Gets the average daily return.
        /// </summary>
        public double AverageDailyReturn
        {
            get
            {
                List<IGrouping<string, Trade>> group = this.trades.GroupBy(t => t.ExitDate).ToList();
                List<double> values = new List<double>();
                double avg = Math.Round(this.Capital, 2);
                values.Add(avg);
                for (int i = 0; i < group.Count(); i++)
                {
                    avg = Math.Round(avg + group[i].Sum(t => t.Profit), 2);
                    values.Add(avg);

                    //double prevDay = group[i - 1].Sum(t => t.Profit);
                    //double nextDay = group[i].Sum(t => t.Profit);
                    //avg = avg + (nextDay / prevDay) - 1;
                }

                return (values.Last() - values.First()) / values.First() * 100;

                //List<double> profitPercent = new List<double>();
                //for(int i = 1; i < values.Count; i++ )
                //{
                //    profitPercent.Add((values[i] / values[i - 1]) - 1);
                //}

                //double avgReturn = profitPercent.Sum() / profitPercent.Count;
                //return avgReturn;

                //if (values.Last() <= 0)
                //{
                //    return 0;
                //}
                //double annualizedReturn = ((Math.Sqrt(values.First() / values.Last()) * values.Count) - 1) * 365;

                //return annualizedReturn;
                
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets the standard deviation of the daily returns.
        /// </summary>
        public double DailyReturnStandardDeviation
        {
            get
            {

                List<IGrouping<string, Trade>> group = this.trades.GroupBy(t => t.ExitDate).ToList();
                List<double> values = new List<double>();
                double avg = Math.Round(this.Capital, 2);
                values.Add(avg);
                for (int i = 0; i < group.Count(); i++)
                {
                    avg = Math.Round(avg + group[i].Sum(t => t.Profit), 2);
                    values.Add(avg);

                    //double prevDay = group[i - 1].Sum(t => t.Profit);
                    //double nextDay = group[i].Sum(t => t.Profit);
                    //avg = avg + (nextDay / prevDay) - 1;
                }

                List<double> profitPercent = new List<double>();
                for (int i = 1; i < values.Count; i++)
                {
                    profitPercent.Add((values[i] / values[i - 1]) - 1);
                }

                double avgProfit = profitPercent.Any() ? profitPercent.Average() : 0;
                double varience = 0;
                foreach (double val in profitPercent)
                {
                    varience = varience + Math.Pow(val - avgProfit, 2);
                }

                return Math.Sqrt(varience / values.Count) * 365;

                //List<IGrouping<string, Trade>> group = this.trades.GroupBy(t => t.ExitDate).ToList();

                //double sd = 0;
                //double avg = this.AverageDailyReturn;
                //for (int i = 1; i < group.Count(); i++)
                //{
                //    double nextDay = group[i].Sum(t => t.Profit);
                //    double prevDay = group[i - 1].Sum(t => t.Profit);
                //    double currentVal = (nextDay / prevDay) - 1;

                //    sd = Math.Pow(currentVal - avg, 2);
                //}


                //return Math.Sqrt(sd / (group.Any() ? group.Count() : 1));

                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        /// <summary>
        /// Gets an attempt to calculate a sharpe ratio using daily returns.
        /// 2.5 is used as the risk free rate.
        /// If there are no trades on a day it will not account for that day properly.
        /// </summary>
        public double AnnualizedSharpieRatio
        {
            get
            {
                if (this.DailyReturnStandardDeviation == 0)
                    return 0;

                double initAmt = this.Capital;
                //double compoundRateOfReturn = ((this.AverageDailyReturn * initAmt) - initAmt)/ initAmt;
                double compoundRateOfReturn = this.AverageDailyReturn;
                
                return Math.Round((compoundRateOfReturn - 1D) / this.DailyReturnStandardDeviation, 2);

                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }
        
        #endregion

        /// <summary>
        /// Gets the profit and percent return grouped per day.
        /// </summary>
        private List<DailyReturn> DailyReturns
        {
            get
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return this.dailyReturns;
            }
        }
        
        /* RunBacktest Different Approach
        public void RunBackTest1(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            double dProfit = 0D;
            double dEntryPrice = 0D;
            double maxDrawDown = this.tradeSystem.FinancialSettings.MaxDrawdown;
            double dollarsPerPoint = this.tradeSystem.FinancialSettings.DollarsPerPoint;
            double roundTripCommision = this.tradeSystem.FinancialSettings.RoundTripCommission;
            double currentCapital = this.tradeSystem.FinancialSettings.InitialCapital;
            double equityPerContract = this.tradeSystem.FinancialSettings.EquityPerContract;

            int maxContracts = this.tradeSystem.FinancialSettings.MaxContracts;
            int iShares;

            Trade trade = null;
            StockPoint entryPoint = null;
            StockPoint exitPoint = null;

            TradeCondition buyCondition = this.tradeSystem.BuyCondition;
            TradeCondition sellCondition = this.tradeSystem.SellCondition;

            PotentialTradeDataInfo[] buyPotentials = indicatorLibraryAdapter.EvalAll(buyCondition.TradeRules, this.tradeSystem.StartDate, this.tradeSystem.EndDate);
            PotentialTradeDataInfo[] sellPotentials = indicatorLibraryAdapter.EvalAll(sellCondition.TradeRules, this.tradeSystem.StartDate, this.tradeSystem.EndDate);

            List<int> PotentialBuyTradeIndexes = buyPotentials.Select(gd => gd.PotentialIndexes).SelectMany(pi => pi).Distinct().OrderBy(pi => pi).ToList();
            List<int> PotentialSellTradeIndexes = sellPotentials.Select(gd => gd.PotentialIndexes).SelectMany(pi => pi).Distinct().OrderBy(pi => pi).ToList();

            Dictionary<int, double> buyTrades = EvaluateCondition(buyPotentials, buyCondition.RuleJoinTypes, PotentialBuyTradeIndexes);
            Dictionary<int, double> sellTrades = EvaluateCondition(sellPotentials, sellCondition.RuleJoinTypes, PotentialSellTradeIndexes);

            Dictionary<int, double> combinedTrades = EvaluateBuySell(indicatorLibraryAdapter.EndOfDayIndex, buyTrades, sellTrades);

            while(true)
            {

            }
        }

        private Dictionary<int, double> EvaluateCondition(PotentialTradeDataInfo[] geneticInfoList, List<RuleJoinType> joinTypes, List<int> PotentialIndexes)
        {
            Dictionary<int, double> results = new Dictionary<int, double>();
            foreach (int pidx in PotentialIndexes)
            {
                if (EvaluateResult(geneticInfoList.Select(gd => gd.Values[pidx]).ToList(), joinTypes))
                {
                    results.Add(pidx, 1D);
                }
            }

            return results;
        }

        private bool EvaluateResult(List<double> values, List<RuleJoinType> joinTypes)
        {
            bool result = true;
            for (int i = 0; i < values.Count - 1; i++)
            {
                result = joinTypes[i] == RuleJoinType.and ? result && (getBoolValue(values[i], values[i + 1], RuleJoinType.or))
                                                          : result || (getBoolValue(values[i], values[i + 1], RuleJoinType.and));
            }

            return result;
        }

        private bool getBoolValue(double value1, double value2, RuleJoinType ruleJoinType)
        {
            return ruleJoinType == RuleJoinType.and ? value1 == 1D && value2 == 1D
                                                    : value1 == 1D && value2 == 1D;
        }

        private Dictionary<int, double> EvaluateBuySell(List<int> stockDaySplit, Dictionary<int, double> buyTrades, Dictionary<int, double> sellTrades)
        {
            if (buyTrades == null && sellTrades == null)
                return null;

            List<int> loopKeys = buyTrades.Keys.Concat(sellTrades.Keys).Distinct().OrderBy(k => k).ToList();
            Dictionary<int, double> tradeData = new Dictionary<int, double>();

            if (!loopKeys.Any())
                return tradeData;

            double value;
            bool buyEntered = false;
            bool sellEntered = false;

            int previousKey = loopKeys.First();
            int currentPoint = 0;
            foreach (int key in loopKeys)
            {
                if (key == stockDaySplit.Last())
                    break;

                currentPoint = stockDaySplit.First(cp => cp > previousKey);
                if (previousKey < currentPoint && key >= currentPoint)
                {
                    if (buyEntered)
                    {
                        buyEntered = false;
                        value = -1D;
                        tradeData.Add(currentPoint, value);
                    }

                    if (sellEntered)
                    {
                        sellEntered = false;
                        value = 1D;
                        tradeData.Add(currentPoint, value);
                    }
                }

                if (!buyEntered && buyTrades.ContainsKey(key) && buyTrades[key] == 1D && !stockDaySplit.Contains(key) && !stockDaySplit.Contains(key + 1))
                {
                    if (!sellEntered)
                    {
                        buyEntered = true;
                    }
                    sellEntered = false;
                    value = 1D;
                    tradeData.Add(key, value);
                }
                else if (!sellEntered && sellTrades.ContainsKey(key) && sellTrades[key] == 1D && !stockDaySplit.Contains(key) && !stockDaySplit.Contains(key + 1))
                {
                    if (!buyEntered)
                    {
                        sellEntered = true;
                    }
                    buyEntered = false;
                    value = -1D;
                    tradeData.Add(key, value);
                }

                previousKey = key;
            }

            if (buyEntered)
            {
                currentPoint = stockDaySplit.First(cp => cp > previousKey);
                value = -1D;
                tradeData.Add(currentPoint, value);
            }

            if (sellEntered)
            {
                currentPoint = stockDaySplit.First(cp => cp > previousKey);
                value = 1D;
                tradeData.Add(currentPoint, value);
            }

            return tradeData;
        }
        */

        /*
        
        /// <summary>
        /// Calculates all the buys and sells against the dataset.
        /// </summary>
        /// <param name="indicatorLibraryAdapter">Moves through each row of the indicatorLibraryAdapter to find the buy and sell points.</param>
        public void RunBacktest(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            bool isLongTradeChecked = this.tradeSystem.Longs;
            bool isShortTradeChecked = this.tradeSystem.Shorts;

            if (!isShortTradeChecked && !isLongTradeChecked)
            {
                return;
            }

            if(!ValidateTradeSystem())
            {
                return;
            }

            bool hasEnteredLong = false;
            bool hasEnteredShort = false;
            bool stockPointMoved = false;
            bool eqityWiped = false;

            TradeType? LongTradeSignal;
            TradeType? ShortTradeSignal;

            StockPoint currentPoint = null;
            StockPoint entryPoint = null;
            StockPoint exitPoint = null;
            StockPoint previousPoint = null;

            double dProfit = 0D;
            double dEntryPrice = 0D;
            double maxDrawDown = this.tradeSystem.FinancialSettings.MaxDrawdown;
            double dollarsPerPoint = this.tradeSystem.FinancialSettings.DollarsPerPoint;
            double roundTripCommision = this.tradeSystem.FinancialSettings.RoundTripCommission;
            double currentCapital = this.tradeSystem.FinancialSettings.InitialCapital;
            double equityPerContract = this.tradeSystem.FinancialSettings.EquityPerContract;

            int maxContracts = this.tradeSystem.FinancialSettings.MaxContracts;
            int iShares;

            Trade trade = null;
            
            // move the pointer to top of the data
            indicatorLibraryAdapter.Restart();

            // previous set to the current stock point
            previousPoint = indicatorLibraryAdapter.GetStockPoint();
            
            while (stockPointMoved || indicatorLibraryAdapter.MoveNext())
            {
                if(eqityWiped)
                {
                    break;
                }

                stockPointMoved = false;

                // current stock point set to the first
                currentPoint = indicatorLibraryAdapter.GetStockPoint();
                
                // if the current point is within the backtesting time period
                if (currentPoint.PointDateTime >= this.tradeSystem.StartDate && currentPoint.PointDateTime <= this.tradeSystem.EndDate)
                {
                    if (currentPoint.PointDateTime.AddMinutes(30).Hour < 9)
                    {
                        continue;
                    }

                    // logic to get out of the trade if the trade has entered
                    if (hasEnteredLong || hasEnteredShort)
                    {
                        bool exitConditionMet = false;
                        if(hasEnteredLong)
                        {
                            exitConditionMet = CheckShortCondition(indicatorLibraryAdapter) != null;
                        }
                        else
                        {
                            exitConditionMet = CheckLongCondition(indicatorLibraryAdapter) != null;
                        }

                        if (exitConditionMet)
                        {
                            if (indicatorLibraryAdapter.MoveNext())
                            {
                                stockPointMoved = true;
                                // exit stock point
                                exitPoint = indicatorLibraryAdapter.GetStockPoint();

                                //eqityWiped = ExitCurrentTrade(false);
                                if (trade.Status != TradeStatus.complete)
                                {
                                    trade.Exit(exitPoint,  false);
                                    dProfit = trade.Profit;

                                    trades.Add(trade);
                                }

                                hasEnteredLong = false;
                                hasEnteredShort = false;

                                continue;
                            }
                            else
                            {
                                break;
                            }
                        }
                    }

                    // stop condition met for long || short
                    if ((hasEnteredLong && (dEntryPrice - currentPoint.Low) >= this.tradeSystem.FinancialSettings.MaxDrawdown) ||
                        (hasEnteredShort && (currentPoint.High - dEntryPrice) >= this.tradeSystem.FinancialSettings.MaxDrawdown))
                    {
                        if (indicatorLibraryAdapter.MoveNext())
                        {
                            stockPointMoved = true;

                            // exit stock point
                            exitPoint = indicatorLibraryAdapter.GetStockPoint();
                            //eqityWiped = ExitCurrentTrade(true);
                            if (trade.Status != TradeStatus.complete)
                            {
                                trade.Exit(exitPoint, true);
                                dProfit = trade.Profit;

                                trades.Add(trade);
                            }

                            hasEnteredLong = false;
                            hasEnteredShort = false;

                            continue;
                        }
                        else
                        {
                            break;
                        }
                    }

                    // if end of the day exit
                    if ((hasEnteredLong || hasEnteredShort) && previousPoint.PointDateTime.Day != currentPoint.PointDateTime.Day)
                    {
                        if (indicatorLibraryAdapter.MoveNext())
                        {
                            stockPointMoved = true;

                            // exit stock point
                            exitPoint = previousPoint;
                            //eqityWiped = ExitCurrentTrade(false);
                            if (trade.Status != TradeStatus.complete)
                            {
                                trade.Exit(exitPoint, false);
                                dProfit = trade.Profit;

                                trades.Add(trade);
                            }

                            hasEnteredLong = false;
                            hasEnteredShort = false;
                        }
                        else
                        {
                            break;
                        }
                    }
                    previousPoint = currentPoint;
                    
                    // logic to get in the long trade
                    if (isLongTradeChecked && !hasEnteredLong && !hasEnteredShort)
                    {
                        // get the current trade signal
                        LongTradeSignal = CheckLongCondition(indicatorLibraryAdapter);

                        // if the tradetype triggered to go long and currently the trade is short or null
                        if (LongTradeSignal != null && LongTradeSignal.Value == TradeType.longTrade)
                        {
                            // move to the next record to enter
                            if (indicatorLibraryAdapter.MoveNext())
                            {
                                stockPointMoved = true;

                                // dont enter the trade if is last
                                if (indicatorLibraryAdapter.IsLast())
                                {
                                    break;
                                }

                                // enter stock point
                                entryPoint = indicatorLibraryAdapter.GetStockPoint();
                                dEntryPrice = currentPoint.Open;
                                currentCapital = currentCapital + dProfit;
                                // calculate share quantity
                                iShares = Math.Min(maxContracts, (int)Math.Floor(currentCapital / equityPerContract));

                                if (currentCapital > 0 && iShares > 0)
                                {
                                    trade = new Trade(maxDrawDown, dollarsPerPoint, roundTripCommision, currentCapital);
                                    trade.Enter(entryPoint, TradeType.longTrade, iShares);

                                    hasEnteredLong = true;
                                  
                                    hasEnteredShort = false;
                                }
                                else
                                {
                                    eqityWiped = true;
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                    }

                    // logic to get in the short trade
                    if (isShortTradeChecked && !hasEnteredLong && !hasEnteredShort)
                    {
                        // get the current trade signal
                        ShortTradeSignal = CheckShortCondition(indicatorLibraryAdapter);

                        // if the tradetype triggered to go short and currently the trade is long or null
                        if (ShortTradeSignal != null && ShortTradeSignal.Value == TradeType.shortTrade)
                        {
                            if (indicatorLibraryAdapter.MoveNext())
                            {
                                stockPointMoved = true;

                                // dont enter the trade if is  last
                                if (indicatorLibraryAdapter.IsLast())
                                {
                                    break;
                                }

                                // enter stock point
                                entryPoint = indicatorLibraryAdapter.GetStockPoint();
                                dEntryPrice = currentPoint.Open;
                                currentCapital = currentCapital + dProfit;

                                // calculate share quantity
                                iShares = Math.Min(maxContracts, (int)Math.Floor(currentCapital / equityPerContract));

                                if (currentCapital > 0 && iShares > 0)
                                {
                                    trade = new Trade(maxDrawDown, dollarsPerPoint, roundTripCommision, currentCapital);
                                    trade.Enter(entryPoint, TradeType.shortTrade, iShares);

                                    hasEnteredLong = false;
                                    hasEnteredShort = true;
                                }
                                else
                                {
                                    eqityWiped = true;
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                }
                if (currentPoint.PointDateTime < this.tradeSystem.StartDate)
                {
                    indicatorLibraryAdapter.MoveToDate(this.tradeSystem.StartDate);
                }
                else if(currentPoint.PointDateTime > this.tradeSystem.EndDate)
                {
                    break;
                }
            }

            // if trade has entered and not exitted exit at the current point
            if (hasEnteredLong || hasEnteredShort)
            {
                // exit stock point
                trade.Exit(currentPoint, false);
            }
        }
        
        */
        private bool ValidateTradeSystem()
        {
            TradeCondition BuyCondition = this.tradeSystem.BuyCondition;
            TradeCondition SellCondition = this.tradeSystem.SellCondition;

            if (BuyCondition == null || SellCondition == null)
            {
                return false;
            }

            if(BuyCondition.RuleJoinTypes.Count(bc=>bc == RuleJoinType.none) > 1)
            {
                return false;
            }

            if(BuyCondition.TradeRules.Any(tr=>string.IsNullOrWhiteSpace(tr.IndicatorName1) || string.IsNullOrWhiteSpace(tr.IndicatorName2)))
            {
                return false;
            }

            if (SellCondition.RuleJoinTypes.Count(bc => bc == RuleJoinType.none) > 1)
            {
                return false;
            }

            if (SellCondition.TradeRules.Any(tr => string.IsNullOrWhiteSpace(tr.IndicatorName1) || string.IsNullOrWhiteSpace(tr.IndicatorName2)))
            {
                return false;
            }

            return true;
        }

        /// <summary>
        /// Returns TradeType triggered at the current row
        /// </summary>
        /// <returns>int 0 : no triggered, 1 - long triggered , 2 - short triggered, 3- both triggered</returns>
        private int GetTradeCondition(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            TradeCondition buyTradeCondition = this.tradeSystem.BuyCondition;
            TradeCondition sellTradeCondition = this.tradeSystem.SellCondition;

            bool bLongTriggered = false;
            bool bShortTriggered = false;
            int iTrigger = 0;

            //check for buy condition update tradeTypes
            if (buyTradeCondition != null)
            {
                if (EvalBuySellSignal(indicatorLibraryAdapter, buyTradeCondition.TradeRules, buyTradeCondition.RuleJoinTypes))
                {
                    bLongTriggered = true;
                }
            }

            // check for sell condition update tradeTypes
            if (sellTradeCondition != null)
            {
                if (EvalBuySellSignal(indicatorLibraryAdapter, sellTradeCondition.TradeRules, sellTradeCondition.RuleJoinTypes))
                {
                    bShortTriggered = true;
                }
            }

            if (bLongTriggered && bShortTriggered)
            {
                iTrigger = 3;
            }
            else if (!bLongTriggered && bShortTriggered)
            {
                iTrigger = 2;
            }
            else if (bLongTriggered && !bShortTriggered)
            {
                iTrigger = 1;
            }

            return iTrigger;
        }


        private TradeType? CheckLongCondition(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            TradeCondition buyTradeCondition = this.tradeSystem.BuyCondition;
            TradeType? triggerValue = null;
            //check for buy condition update tradeTypes
            if (buyTradeCondition != null)
            {
                if (EvalBuySellSignal(indicatorLibraryAdapter, buyTradeCondition.TradeRules, buyTradeCondition.RuleJoinTypes))
                {
                    triggerValue = TradeType.longTrade;
                }
            }

            return triggerValue;
        }

        private TradeType? CheckShortCondition(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            TradeCondition sellTradeCondition = this.tradeSystem.SellCondition;
            TradeType? triggerValue = null;
            //check for buy condition update tradeTypes
            if (sellTradeCondition != null)
            {
                if (EvalBuySellSignal(indicatorLibraryAdapter, sellTradeCondition.TradeRules, sellTradeCondition.RuleJoinTypes))
                {
                    triggerValue = TradeType.shortTrade;
                }
            }

            return triggerValue;
        }

        /// <summary>
        /// Evaluates the join conditions and returns the signal as boolean
        /// </summary>
        /// <param name="tradeRules">List of traderules</param>
        /// <param name="joinTypes">list of join types</param>
        /// <returns>boolean signal</returns>
        private bool EvalBuySellSignal(IndicatorLibraryAdapter indicatorLibraryAdapter, List<TradeRule> tradeRules, List<RuleJoinType> joinTypes)
        {
            List<bool> andResults = new List<bool>();
            List<int> andList = null;
            List<bool> orList = new List<bool>();
            for (int idx = 0; idx < joinTypes.Count; idx++)
            {
                if (joinTypes[idx] == RuleJoinType.and)
                {
                    if (andList == null)
                    {
                        andList = new List<int>();
                    }
                    andList.Add(idx);
                }
                else
                {
                    if (andList != null && andList.Any())
                    {
                        bool tr1 = tradeRules[andList[0]].Eval(indicatorLibraryAdapter);
                        bool tr2;
                        for (int id = 1; id < andList.Count; id++)
                        {
                            tr2 = tradeRules[andList[id]].Eval(indicatorLibraryAdapter);
                            tr1 = tr1 && tr2;
                        }
                        andResults.Add(tr1);
                    }
                    if (idx == 0 || joinTypes[idx - 1] == RuleJoinType.or)
                    {
                        orList.Add(tradeRules[idx].Eval(indicatorLibraryAdapter));
                    }
                    andList = null;
                }
            }

            List<bool> andOrResultList = andResults.Concat(orList).ToList();
            bool result = false;
            for (int idx = 0; idx < andOrResultList.Count; idx++)
            {
                if (idx == 0)
                {
                    result = andOrResultList[idx];
                }
                else
                {
                    result = result || andOrResultList[idx];
                }
            }

            return result;

            /*
            int i = 0;
            RuleJoinType rj = joinTypes[i];
            TradeRule tr = tradeRules[i];
            bool bSignal = tr.Eval(indicatorLibraryAdapter);

            while (rj != RuleJoinType.none)
            {
                tr = tradeRules[i];
                bSignal = rj == RuleJoinType.or ? bSignal || tr.Eval(indicatorLibraryAdapter)
                                                : bSignal && tr.Eval(indicatorLibraryAdapter);
                rj = joinTypes[i];
                ++i;
            }

            return bSignal;
            */
        }

        /// <summary>
        /// Closes any open trades if there are some.
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The indicatorLibraryAdapter to get the final stock point from.</param>
        public void CloseOpenTrades(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            /// *********************************************
            /// GDBCup - Code is needed here
            /// *********************************************
        }
        
        /// <summary>
        /// Runs the backtest for one row. Must manually MoveNext on the indicator library if using this method.
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The indicator Library Adapter to use for the backtest row.</param>
        public void RunBacktestRow(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            /// *********************************************
            /// GDBCup - Code is needed here
            /// *********************************************
        }

        /// <summary>
        /// Calculates the number of contracts that can be bought.
        /// </summary>
        /// <returns>The number of contracts that can be bought.</returns>
        private int NumberOfContracts()
        {
            int contracts = (int)Math.Floor(this.Capital / this.tradeSystem.FinancialSettings.EquityPerContract);
            int maxContracts = this.tradeSystem.FinancialSettings.MaxContracts;
            return contracts > maxContracts ? maxContracts : contracts;
            /// *********************************************
            /// GDBCup - Code is needed here
            /// *********************************************
        }

        /// <summary>
        /// A structure for the daily return mfvvalues.
        /// </summary>
        private struct DailyReturn
        {
            /// <summary>
            /// The date of the returns.
            /// </summary>
            public string Date;

            /// <summary>
            /// The amount of profit on that day.
            /// </summary>
            public double Profit;

            /// <summary>
            /// The percent return on the inital capital for that day.
            /// </summary>
            public double PercentReturn;
        }
    }
}
