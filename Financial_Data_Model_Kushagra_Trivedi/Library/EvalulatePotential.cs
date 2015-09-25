using FDMTermProject.Entities;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FDMTermProject
{
    public class EvalulateTrade
    {
        private IndicatorLibraryAdapter IndicatorLibraryAdapter;
        private Trades Trades = new Trades();
        private bool Cancelled;
        public int StopOuts { get; set; }
        public int Winners { get; set; }
        public int Losers { get; set; }

        private new Dictionary<string, double[]> Data;
        private List<StockPoint> StockPoints;
        public DataTable LogTables { get; private set; }

        public double Capital { get; private set; }

        public double AverageDailyReturn
        {
            get
            {
                List<IGrouping<string, Trade>> group = this.Trades.GroupBy(t => t.ExitDate).ToList();
                List<double> values = new List<double>();
                double avg = Math.Round(this.Capital, 2);
                values.Add(avg);
                for (int i = 0; i < group.Count(); i++)
                {
                    avg = Math.Round(avg + group[i].Sum(t => t.Profit), 2);
                    values.Add(avg);
                }
                return (values.Last() - values.First()) / values.First() * 100;
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        public double AnnualizedSharpieRatio
        {
            get
            {
                if (this.DailyReturnStandardDeviation == 0)
                    return 0;
                double initAmt = this.Capital;
                double compoundRateOfReturn = this.AverageDailyReturn;

                return Math.Round((compoundRateOfReturn - 1D) / this.DailyReturnStandardDeviation, 2);
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        public double DailyReturnStandardDeviation
        {
            get
            {
                List<IGrouping<string, Trade>> group = this.Trades.GroupBy(t => t.ExitDate).ToList();
                List<double> values = new List<double>();
                double avg = Math.Round(this.Capital, 2);
                values.Add(avg);
                for (int i = 0; i < group.Count(); i++)
                {
                    avg = Math.Round(avg + group[i].Sum(t => t.Profit), 2);
                    values.Add(avg);
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

                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
            }
        }

        public EvalulateTrade(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            this.LogTables = null;
            this.Winners = 0;
            this.Losers = 0;
            this.StopOuts = 0;

            this.IndicatorLibraryAdapter = indicatorLibraryAdapter;
            this.Data = new Dictionary<string, double[]>();
            //this.StockPoints = Enumerable.Range(0, indicatorLibraryAdapter.DataCount)
            //                        .Select(e => new StockPoint(
            //                                GetDateTimeValue((int)indicatorLibraryAdapter.Data["Date"][e].Value,
            //                                                       (int)indicatorLibraryAdapter.Data["Time"][e].Value),
            //                                indicatorLibraryAdapter.Data["Open"][e].Value,
            //                                indicatorLibraryAdapter.Data["High"][e].Value,
            //                                indicatorLibraryAdapter.Data["Low"][e].Value,
            //                                indicatorLibraryAdapter.Data["Close"][e].Value,
            //                                (long)indicatorLibraryAdapter.Data["Volume"][e].Value)
            //                            ).ToArray();

            this.StockPoints = indicatorLibraryAdapter.StockPoints;
        }

        public void SetTradeRules(IEnumerable<TradeRule> tradeRules)
        {
            TradeRule[] tRules = tradeRules.GroupBy(tr => tr.Name)
                                    .Select(g => g.First())
                                    .Where(tr => !this.Data.ContainsKey(tr.Name))
                                    .ToArray();

            if (!tRules.Any())
            {
                return;
            }
            int dataCount = this.IndicatorLibraryAdapter.DataCount;
            double?[][] ind1Data = tRules.Select(tr => this.IndicatorLibraryAdapter.Data[tr.IndicatorName1].ToArray()).ToArray();


            if (this.Cancelled)
            {
                this.Cancelled = false;
                return;
            }

            double?[][] ind2Data = tRules.Select(tr => tr.IndicatorName2.StartsWith("#")
                                ? Enumerable.Range(0, dataCount).Select(e => (double?)getDoubleValue(tr.IndicatorName2)).ToArray()
                                : this.IndicatorLibraryAdapter.Data[tr.IndicatorName2].ToArray())
                .ToArray();

            if (this.Cancelled)
            {
                this.Cancelled = false;
                return;
            }

            this.Data = this.Data.Concat(Enumerable.Range(0, tRules.Count())
                                    .ToDictionary(
                                        e => tRules[e].Name,
                                        e => Enumerable.Range(0, dataCount)
                                             .Select(e1 => Eval(tRules[e].CompareType,
                                                         ind1Data[e][e1],
                                                         ind2Data[e][e1],
                                                         e1 == 0 ? new double?() : ind1Data[e][e1 - 1],
                                                         e1 == 0 ? new double?() : ind2Data[e][e1 - 1])).ToArray()
                                    ).ToArray()).ToDictionary(d => d.Key, d => d.Value);
        }
        
        private object[] GetEmptyArray(int count)
        {
            return Enumerable.Range(0, count).Select(e => "").ToArray();
        }

        public Trades EvalTrades(TradeSystem tradeSystem, TradeCondition buyCondition, TradeCondition sellCondition, double stopOutValue, bool log = false)
        {
            object[] logRowData = null;
            if (log)
            {
                int logIndex = -1;
                this.LogTables = new DataTable("LogTable");
                this.LogTables.Columns.Add("Date");
                this.LogTables.Columns.Add("Time");
                this.LogTables.Columns.Add("Open");
                this.LogTables.Columns.Add("High");
                this.LogTables.Columns.Add("Low");
                this.LogTables.Columns.Add("Close");
                this.LogTables.Columns.Add("Volume");
                this.LogTables.Columns.Add("StopOut");
                this.LogTables.Columns.Add("Buy Signal");
                this.LogTables.Columns.Add("Sell Signal");

                TradeRule tr;
                for (int i = 0; i < buyCondition.TradeRules.Count; i++)
                {
                    tr = buyCondition.TradeRules[i];
                    logIndex++;
                    this.LogTables.Columns.Add(string.Format("{0} {1} {2} -BR{3}", tr.IndicatorName1, tr.CompareType.ToString(), tr.IndicatorName2, logIndex));
                    this.LogTables.Columns.Add(string.Format("Result -BR{0}", logIndex));
                    this.LogTables.Columns.Add(string.Format("JoinType -BR{0}", logIndex));
                }

                logIndex = 0;
                for (int i = 0; i < sellCondition.TradeRules.Count; i++)
                {
                    tr = sellCondition.TradeRules[i];
                    logIndex++;
                    this.LogTables.Columns.Add(string.Format("{0} {1} {2} -SR{3}", tr.IndicatorName1, tr.CompareType.ToString(), tr.IndicatorName2, logIndex));
                    this.LogTables.Columns.Add(string.Format("Result -SR{0}", logIndex));
                    this.LogTables.Columns.Add(string.Format("JoinType -SR{0}", logIndex));
                }

                logRowData = GetEmptyArray(this.LogTables.Columns.Count);
            }

            bool stopout = stopOutValue != double.MaxValue;
            DateTime startDate = tradeSystem.StartDate;
            DateTime endDate = tradeSystem.EndDate;

            TradeType? tradeType = null;
            if(tradeSystem.Longs && ! tradeSystem.Shorts)
            {
                tradeType = TradeType.longTrade;
            }
            else if (!tradeSystem.Longs && tradeSystem.Shorts)
            {
                tradeType = TradeType.shortTrade;
            }

            this.Winners = 0;
            this.Losers = 0;
            this.StopOuts = 0;
            this.Trades = new Trades();
            this.Capital = tradeSystem.FinancialSettings.InitialCapital;

            bool[] buys = GetValues(buyCondition);
            bool[] sells = GetValues(sellCondition);            
            List<int> endOfDaysIndexes = this.IndicatorLibraryAdapter.EndOfDayIndex;
            
            bool buySignal;
            bool sellSignal;
            bool longEntered = false;
            bool shortEntered = false;

            double maxDrawDown = tradeSystem.FinancialSettings.MaxDrawdown;
            double dollarsPerPoint = tradeSystem.FinancialSettings.DollarsPerPoint;
            double roundTripCommision = tradeSystem.FinancialSettings.RoundTripCommission;
            double currentCapital = tradeSystem.FinancialSettings.InitialCapital;
            double equityPerContract = tradeSystem.FinancialSettings.EquityPerContract;
            int maxContracts = tradeSystem.FinancialSettings.MaxContracts;

            int counter = 0;
            Trade trade = null;
            StockPoint stockPoint = null;
            DateTime currentDateTime;

            #region Enter/Exit and Log Trade Func

            int stopOutCounter = 0;
            Action EnterLog = () =>
            {
                StockPoint point = StockPoints[counter];
                logRowData[0] = point.Date;
                logRowData[1] = point.Time;
                logRowData[2] = point.Open.ToString("0.00");
                logRowData[3] = point.High.ToString("0.00");
                logRowData[4] = point.Low.ToString("0.00");
                logRowData[5] = point.Close.ToString("0.00");
                logRowData[6] = point.Volume.ToString();
                if (stopOutCounter != this.StopOuts)
                {
                    stopOutCounter = this.StopOuts;
                    this.LogTables.Rows[this.LogTables.Rows.Count - 1][7] = "1";
                }
                logRowData[7] = "0";
                if (endOfDaysIndexes.Contains(counter))
                {
                    logRowData[8] = "end of day";
                    logRowData[9] = "end of day";
                }
                else
                {
                    logRowData[8] = buys[counter] ? "1" : "0";
                    logRowData[9] = sells[counter] ? "1" : "0";
                }
                int logIndex = 9;

                TradeRule tr;
                double[] evalData;
                for (int i = 0; i < buyCondition.TradeRules.Count; i++)
                {
                    tr = buyCondition.TradeRules[i];
                    evalData = this.Data[tr.Name];
                    logRowData[++logIndex] = string.Format("{0} {1} {2}",
                                                            this.IndicatorLibraryAdapter.Data[tr.IndicatorName1][counter].ToString(),
                                                            tr.CompareType.ToString(),
                                                            tr.IndicatorName2.Contains("#")
                                                                ? tr.IndicatorName2.Replace("#", "")
                                                                : Convert.ToString(this.IndicatorLibraryAdapter.Data[tr.IndicatorName2][counter]));
                    logRowData[++logIndex] = evalData[counter].ToString("0");
                    logRowData[++logIndex] = buyCondition.RuleJoinTypes[i].ToString();
                }

                for (int i = 0; i < sellCondition.TradeRules.Count; i++)
                {
                    tr = sellCondition.TradeRules[i];
                    evalData = this.Data[tr.Name];
                    logRowData[++logIndex] = string.Format("{0} {1} {2}",
                                                            this.IndicatorLibraryAdapter.Data[tr.IndicatorName1][counter],
                                                            tr.CompareType.ToString(),
                                                            tr.IndicatorName2.Contains("#")
                                                                ? tr.IndicatorName2.Replace("#", "")
                                                                : Convert.ToString(this.IndicatorLibraryAdapter.Data[tr.IndicatorName2][counter]));
                    logRowData[++logIndex] = evalData[counter].ToString("0");
                    logRowData[++logIndex] = sellCondition.RuleJoinTypes[i].ToString();
                }

                this.LogTables.Rows.Add(logRowData);

                logRowData = GetEmptyArray(this.LogTables.Columns.Count);
            };

            // returns if entry point is the last point
            Action<TradeType> EnterTrade = (entryTradeType) =>
            {
                longEntered = entryTradeType == TradeType.longTrade;
                shortEntered = entryTradeType == TradeType.shortTrade;

                trade = new Trade(maxDrawDown, dollarsPerPoint, roundTripCommision, currentCapital);
                trade.Enter(this.StockPoints[counter + 1], entryTradeType, Math.Min((int)(currentCapital / equityPerContract), maxContracts));
            };

            // returns if the needs to exit
            Func<bool, int, int, bool> ExitTrade = (isStopped, indexIncreamentvalue, decrement) =>
            {
                trade.Exit(this.StockPoints[counter + indexIncreamentvalue], decrement, isStopped);               

                currentCapital = Math.Round(Math.Max(currentCapital + trade.Profit, 0D), 2);
                if (trade.Profit > 0)
                {
                    this.Winners++;
                }
                else if (trade.Profit < 0)
                {
                    this.Losers++;
                }
                if(isStopped)
                {
                    this.StopOuts++;
                }
                Trades.Add(trade);

                longEntered = false;
                shortEntered = false;
                
                return currentCapital < equityPerContract;
            };

            #endregion Enter/Exit Trade Func

            for (counter = 0; counter < this.StockPoints.Count(); counter++)
            {
                if (this.Cancelled)
                {
                    this.Cancelled = false;
                    return Trades;
                }

                buySignal = buys[counter];
                sellSignal = sells[counter];
                stockPoint = StockPoints[counter];

                currentDateTime = stockPoint.PointDateTime;

                if (currentDateTime >= endDate)
                {
                    if (longEntered || shortEntered)
                    {
                        ExitTrade(false, 0, 0);
                    }
                    break;
                }

                if (currentDateTime < startDate)
                {
                    continue;
                }

                if (log) EnterLog();

                if (currentDateTime.AddMinutes(30).Hour >= 9)
                {
                    if (longEntered || shortEntered)
                    {
                        // exit end of day, if long/short entered or beyond specified date
                        if (endOfDaysIndexes.Contains(counter))
                        {
                            if (ExitTrade(false, 0, 0)) break;
                        }
                        // exit trade logic
                        else if ((longEntered && sellSignal) || (shortEntered && buySignal))
                        {
                            if (ExitTrade(false, 1, 5)) break;
                        }
                        //stopout
                        else if (stopout && ((trade.TradeType == TradeType.longTrade && trade.EntryPrice - stockPoint.Low >= stopOutValue) ||
                                            (trade.TradeType == TradeType.shortTrade && stockPoint.High - trade.EntryPrice >= stopOutValue)))
                        {
                            if (ExitTrade(true, 1, 5)) break;
                        }
                        continue;
                    }

                    // enter trade logic
                    if (!(longEntered || shortEntered || endOfDaysIndexes.Contains(counter)))
                    {
                        if (buySignal && (tradeType == null || tradeType.Value == TradeType.longTrade))
                        {
                            EnterTrade(TradeType.longTrade);
                        }
                        else if (sellSignal && (tradeType == null || tradeType.Value == TradeType.shortTrade))
                        {
                            EnterTrade(TradeType.shortTrade);
                        }
                    }
                }
            }

            return Trades;
        }

        private double Eval(IndicatorCompareType CompareType, double? indicator1, double? indicator2, double? previousIndicator1, double? previousIndicator2)
        {
            if (indicator1 == null || indicator2 == null)
                return 0D;

            switch (CompareType)
            {
                case IndicatorCompareType.GT:
                    return indicator1 > indicator2 ? 1D : 0D;
                case IndicatorCompareType.LT:
                    return indicator1 < indicator2 ? 1D : 0D;
                case IndicatorCompareType.UP_XOVER:
                    if (previousIndicator1 == null || previousIndicator2 == null)
                        return 0D;
                    return previousIndicator1 < previousIndicator2 && indicator1 > indicator2 ? 1D : 0D;
                case IndicatorCompareType.DOWN_XUNDER:
                    if (previousIndicator1 == null || previousIndicator2 == null)
                        return 0D;
                    return previousIndicator1 > previousIndicator2 && indicator1 < indicator2 ? 1D : 0D;
            }

            return 0D;
        }

        private bool[] GetValues(TradeCondition tradeCondition)
        {
            IEnumerable<TradeRule> tRules = tradeCondition.TradeRules.GroupBy(tr => tr.Name)
                                    .Select(g => g.First())
                                    .Where(tr => !this.Data.ContainsKey(tr.Name));
            if(tRules.Any())
            {
                SetTradeRules(tRules);
            }

            double[][] data = tradeCondition.TradeRules.Select(tr => this.Data[tr.Name]).ToArray();
            
            return Enumerable.Range(0, this.IndicatorLibraryAdapter.DataCount)
                      .Select(e => EvaluateResult(data.Select(d => d[e]).ToArray(), tradeCondition.RuleJoinTypes))
                      .ToArray();
        }

        private DateTime GetDateTimeValue(int date, int time)
        {
            string strDate = date.ToString();
            string strTime = time.ToString("0000");

            return DateTime.Parse(string.Format("{0}/{1}/{2} {3}:{4}:00",
                strDate.Substring(0, 4), 
                strDate.Substring(4, 2),
                strDate.Substring(6, 2),
                strTime.Substring(0, 2),
                strTime.Substring(2, 2)));
        }

        private double? getDoubleValue(string str)
        {
            if (string.IsNullOrEmpty(str))
            {
                return null;
            }
            else if (str.StartsWith("#"))
            {
                return double.Parse(str.Replace("#", ""));
            }
            else
            {
                return double.Parse(str);
            }
        }

        private bool EvaluateResult(double[] values, List<RuleJoinType> joinTypes)
        {
            if (values == null || !values.Any())
                return false;

            List<bool> andResults = new List<bool>();
            List<bool> orList = new List<bool>();
            List<int> andList = null;

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
                        bool tr1 = getBoolValue(values[0], values[1], RuleJoinType.and);
                        bool tr2;
                        for (int id = 1; id < andList.Count; id++)
                        {
                            tr2 = getBoolValue(values[andList[id]], values[andList[id] + 1], RuleJoinType.and);
                            tr1 = tr1 && tr2;
                        }
                        andResults.Add(tr1);
                    }
                    if (idx == 0 || joinTypes[idx - 1] == RuleJoinType.or)
                    {
                        orList.Add(values[idx] == 1D);
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
        }

        private bool getBoolValue(double value1, double value2, RuleJoinType ruleJoinType)
        {
            return ruleJoinType == RuleJoinType.and ? value1 == 1D && value2 == 1D
                                                    : value1 == 1D || value2 == 1D;
        }

        internal void Cancel()
        {
            this.Cancelled = true;
        }
    }
}
