namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;

    /// <summary>
    /// Technical indicators are defined in this class.
    /// </summary>
    public static class IndicatorLib
    {
        // SMA
        /// <summary>
        /// Calculates the Simple Moving Average and fills indicators with the data needed to graph it.
        /// </summary>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="data">StockPoints to calculate the SMA with.</param>
        /// <param name="indicators">The mfvvalues needed to graph the SMA.</param>
        /// <param name="period">The number of days to average.</param>
        /// <param name="offset">Where to start the graph.</param>
        public static void CalculateSma(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = "SMA(" + period.ToString("00") + ")";
            SimpleMovingAverage sma = new SimpleMovingAverage(period);
            offset = period - 1;
            foreach (StockPoint point in data)
            {
                sma.AddValue(point.Close);
                if (sma.Period == sma.ValueCount)
                {
                    AddValue(point.PointDateTime, key, sma.MovingAverage(), indicators);
                }
            }
        }

        // EMA
        /// <summary>
        /// Calculates the Exponential Moving Average and fills indicators with the data needed to graph it.
        /// </summary>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="data">StockPoints to calculate the EMA with.</param>
        /// <param name="indicators">The mfvvalues needed to graph the EMA.</param>
        /// <param name="period">The number of days to average.</param>
        /// <param name="offset">Where to start the graph.</param>
        public static void CalculateEma(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = "EMA(" + period.ToString("00") + ")";
            ExponentialMovingAverage ema = new ExponentialMovingAverage(period);
            offset = period - 1;
            int i = 0;
            foreach (StockPoint point in data)
            {
                ema.AddValue(point.Close);
                if (i < offset)
                {
                    i++;
                }
                else
                {
                    AddValue(point.PointDateTime, key, ema.MovingAverage, indicators);
                }
            }
        }

        // calculate Bollinger Bands        
        public static void CalculateBollinger(out string key, out string key2, out string key3, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, int multiplier, out int offset)
        {
            key = string.Format("BB-Upper-({0}:{1})", period.ToString("00"), multiplier.ToString("00"));
            key2 = string.Format("BB-Mid-({0}:{1})", period.ToString("00"), multiplier.ToString("00"));
            key3 = string.Format("BB-Lower-({0}:{1})", period.ToString("00"), multiplier.ToString("00"));
            offset = period - 1;

            double upperBand;
            double midBand;
            double lowerBand;

            SimpleMovingAverage sma = new SimpleMovingAverage(period);
            SimpleMovingAverage smaPrice = new SimpleMovingAverage(period);

            int i = 0;
            foreach (StockPoint point in data)
            {
                sma.AddValue(point.Close);
                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    double smaValue = sma.MovingAverage();
                    double stdMultiplied = sma.StandardDeviation() * multiplier;

                    upperBand = smaValue + stdMultiplied;
                    midBand = smaValue;
                    lowerBand = smaValue - stdMultiplied;

                    AddValue(point.PointDateTime, key, upperBand, indicators);
                    AddValue(point.PointDateTime, key2, midBand, indicators);
                    AddValue(point.PointDateTime, key3, lowerBand, indicators);
                }
            }

        }

        // RSI
        /// <summary>
        /// Calculates the Relative Strength Index and fills indicators with the data needed to graph it.
        /// </summary>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="data">StockPoints to calculate the Relative Strength Index with.</param>
        /// <param name="indicators">The mfvvalues needed to graph the RSI.</param>
        /// <param name="period">The number of days gained/lost to consider.</param>
        /// <param name="offset">Where to start the graph.</param>
        public static void CalculateRsi(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = "RSI(" + period.ToString("00") + ")";
            ExponentialMovingAverage gains = new ExponentialMovingAverage(period);
            ExponentialMovingAverage losses = new ExponentialMovingAverage(period);
            offset = period;
            int i = 0;
            StockPoint previousPoint = data[0];
            foreach (StockPoint point in data.Skip(1))
            {
                if (previousPoint.Close > point.Close)
                {
                    losses.AddValue(previousPoint.Close - point.Close);
                    gains.AddValue(0);
                }
                else if (previousPoint.Close < point.Close)
                {
                    gains.AddValue(point.Close - previousPoint.Close);
                    losses.AddValue(0);
                }

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    double rsi = 100 - (100 / (1 + (gains.MovingAverage / losses.MovingAverage)));
                    AddValue(point.PointDateTime, key, rsi, indicators);
                }

                previousPoint = point;
            }
        }

        // Chaikin Oscillator
        public static void CalculateChaikinOscillator(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period1, int period2, out int offset, out int min, out int max)
        {
            key = string.Format("ChiOsc({0}:{1})", period1.ToString("00"), period2.ToString("00"));
            offset = (period2 > period1 ? period2 : period1) - 1;

            min = int.MaxValue;
            max = int.MinValue;

            // Money Flow Multiplier = [(Close  -  Low) - (High - Close)] /(High - Low)
            double mfm;

            // Money Flow Volume = Money Flow Multiplier x Volume for the Period
            double mfv;

            // ADL = Previous ADL + Current Period's Money Flow Volume
            double adl = 0D;

            // CHaikin Oscillator
            double co;

            ExponentialMovingAverage ema1 = new ExponentialMovingAverage(period1);
            ExponentialMovingAverage ema2 = new ExponentialMovingAverage(period2);
            int i = 0;
            foreach (StockPoint point in data)
            {
                mfm = ((point.Close - point.Low) - (point.High - point.Close)) / (point.High - point.Low == 0 ? 1 : point.High - point.Low);

                mfv = mfm * point.Volume;

                adl = adl + mfv;
                
                ema1.AddValue(adl);
                ema2.AddValue(adl);

                if (i < period2 - 1)
                {
                    i++;
                }
                else
                {
                    co = ema1.MovingAverage - ema2.MovingAverage;

                    if (min > co)
                        min = (int)Math.Floor(co);
                    if (max < co)
                        max = (int)Math.Ceiling(co);

                    AddValue(point.PointDateTime, key, co, indicators);
                }
            }
        }

        // CMF
        /// <summary>
        /// Calculates the Chaikin Money Flow Index and fills indicators with the data needed to graph it.
        /// </summary>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="data">StockPoints to calculate the Chaikin Money Flow with.</param>
        /// <param name="indicators">The mfvvalues needed to graph the Chaikin Money Flow.</param>
        /// <param name="period">The number of period to consider.</param>
        /// <param name="offset">Where to start the graph.</param>
        public static void CalculateCMF(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = string.Format("CMF({0})", period.ToString("00"));
            offset = period - 1;

            // Money Flow Multiplier = [(Close  -  Low) - (High - Close)] /(High - Low)
            double mfm;

            // Money Flow Volume = Money Flow Multiplier x Volume for the Period
            double mfv;

            // Chaikin money flow
            ChaikinMoneyFlow cmf = new ChaikinMoneyFlow(period);

            // Given Period Chaikin Money Flow value
            double cmfValue;
            
            int i = 0;
            foreach(StockPoint point in data)
            {

                mfm = ((point.Close - point.Low) - (point.High - point.Close)) / (point.High - point.Low == 0 ? 1 : point.High - point.Low);

                mfv = mfm * point.Volume;

                cmf.AddMFVValue(mfv);
                cmf.AddVolumeValue(point.Volume);

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    cmfValue = cmf.CalculateChaikinMoneyFlow();
                    AddValue(point.PointDateTime, key, cmfValue, indicators);
                }
            }
        }

        // MACD
        public static void CalculateMACD(out string key, out string key2, out double[] key3, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period1, int period2, int period3, out int offset, out int min, out int max)
        {
            min = int.MaxValue;
            max = int.MinValue;

            key = string.Format("MACD({0}-{1}-{2})", period1.ToString("00"), period2.ToString("00"), period3.ToString("00"));
            key2 = string.Format("MACD-SignalLine({0}-{1}-{2})", period1.ToString("00"), period2.ToString("00"), period3.ToString("00"));
            List<double> hist = new List<double>();

            offset = (new List<int>(){period1, period2}.Max()) - 1;

            ExponentialMovingAverage ema1 = new ExponentialMovingAverage(period1);
            ExponentialMovingAverage ema2 = new ExponentialMovingAverage(period2);
            ExponentialMovingAverage ema3 = new ExponentialMovingAverage(period3);
            
            int i = 0;
            foreach(StockPoint point in data)
            {
                ema1.AddValue(point.Close);
                ema2.AddValue(point.Close);

                double EMA1 = ema1.MovingAverage;
                double EMA2 = ema2.MovingAverage;

                double MACDLine = EMA1 - EMA2;

                ema3.AddValue(MACDLine);

                if (i < offset)
                {
                    i++;
                    hist.Add(0);
                }
                else
                {
                    double SignalLine = ema3.MovingAverage;

                    if (min > MACDLine || min > SignalLine)
                    {
                        min = (int)(Math.Floor(MACDLine < SignalLine ? MACDLine : SignalLine));
                    }

                    if (max < MACDLine || max < SignalLine)
                    {
                        max = (int)(Math.Ceiling(MACDLine > SignalLine ? MACDLine : SignalLine));
                    }

                    AddValue(point.PointDateTime, key, MACDLine, indicators);
                    AddValue(point.PointDateTime, key2, SignalLine, indicators);
                    
                    hist.Add(MACDLine - SignalLine); 
                }
            }

            key3 = hist.ToArray();
        }

        //cci
        public static void CalculateCCI(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset, out int min, out int max)
        {
            key = string.Format("CCI({0})", period.ToString("00"));
            offset = period - 1;

            min = int.MaxValue;
            max = int.MinValue;
            
            // cci = (Typical Price  -  20-period SMA of TP) / (.015 x Mean Deviation)
            double cci;

            // Typical Price (TP) = (High + Low + Close)/3
            double typicalPrice;

            double constant = 0.015;

            SimpleMovingAverage sma = new SimpleMovingAverage(period);
            int i = 0;

            foreach (StockPoint point in data)
            {
                typicalPrice = (point.High + point.Close + point.Low) / 3D;
                sma.AddValue(typicalPrice);

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    cci = (typicalPrice - sma.MovingAverage())/ (constant * sma.MeanDeviation());
                    AddValue(point.PointDateTime, key, cci, indicators);

                    if (min > cci)
                        min = (int)Math.Floor(cci);

                    if (max < cci)
                        max = (int)Math.Ceiling(cci);
                }
            }
        }
        
        //Aroon
        public static void CalculateAroon(out string key, out string key2, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = string.Format("Aroon-UP({0})", period.ToString("00"));
            key2 = string.Format("Aroon-Down({0})", period.ToString("00"));
            offset = period - 1;

            // Aroon-Up = ((period - Days Since period-day High)/period) x 100
            double up;

            // Aroon-Down = ((period - Days Since period-day Low)/period) x 100
            double down;

            // day since 25 day high
            int high;

            // day since 25 day low
            int low;

            Queue<double> highVals = new Queue<double>();
            Queue<double> lowVals = new Queue<double>();

            int i = 0;
            foreach(StockPoint point in data)
            {
                highVals.Enqueue(point.High);
                lowVals.Enqueue(point.Low);
                if(highVals.Count > period)
                {
                    highVals.Dequeue();
                    lowVals.Dequeue();
                }

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    high = period - highVals.ToList().IndexOf(highVals.Max());
                    low = period - highVals.ToList().IndexOf(highVals.Min());

                    up = ((period - high) / (double)period) * 100;
                    down = ((period - low) / (double)period) * 100;

                    AddValue(point.PointDateTime, key, up, indicators);
                    AddValue(point.PointDateTime, key2, down, indicators);
                }
            }            
        }

        // Aroon Oscillator
        public static void CalculateAroonOscillator(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = string.Format("AroonOSc({0})", period.ToString("00"));
            offset = period - 1;

            // Aroon Oscillator 
            double aroonOsc;

            // Aroon-Up = ((period - Days Since period-day High)/period) x 100
            double up;

            // Aroon-Down = ((period - Days Since period-day Low)/period) x 100
            double down;

            // day since look back period high
            int high;

            // day since look back period low
            int low;

            Queue<double> highVals = new Queue<double>();
            Queue<double> lowVals = new Queue<double>();

            int i = 0;
            foreach (StockPoint point in data)
            {
                highVals.Enqueue(point.High);
                lowVals.Enqueue(point.Low);
                if (highVals.Count > period)
                {
                    highVals.Dequeue();
                    lowVals.Dequeue();
                }

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    high = period - highVals.ToList().IndexOf(highVals.Max());
                    low = period - highVals.ToList().IndexOf(highVals.Min());

                    up = ((period - high) / (double)period) * 100;
                    down = ((period - low) / (double)period) * 100;

                    aroonOsc =  up - down;

                    AddValue(point.PointDateTime, key, aroonOsc, indicators);
                }
            }
        }

        // Stochastic RSI
        public static void CalculateStochasticRSI(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset, bool leverage = true)
        {
            key = string.Format("StocRSI({0})", period.ToString("00"));
            ExponentialMovingAverage gains = new ExponentialMovingAverage(period);
            ExponentialMovingAverage losses = new ExponentialMovingAverage(period);
            offset = period * 2;

            double rsi;
            double stochRSI;
            double highestRSI;
            double lowestRSI;

            Queue<double> rsiQueue = new Queue<double>();

            StockPoint previousPoint = data[0];
            int i = 0;
            foreach (StockPoint point in data.Skip(1))
            {
                if (previousPoint.Close > point.Close)
                {
                    losses.AddValue(previousPoint.Close - point.Close);
                    gains.AddValue(0);
                }
                else if (previousPoint.Close < point.Close)
                {
                    gains.AddValue(point.Close - previousPoint.Close);
                    losses.AddValue(0);
                }
                
                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    rsi = 100 - (100 / (1 + (gains.MovingAverage / losses.MovingAverage)));

                    rsiQueue.Enqueue(rsi);
                    if (rsiQueue.Count > period)
                    {
                        rsiQueue.Dequeue();
                    }

                    if (rsiQueue.Count == period)
                    {
                        highestRSI = rsiQueue.Max();
                        lowestRSI = rsiQueue.Min();

                        stochRSI = (rsi - lowestRSI) / (highestRSI - lowestRSI) * (leverage ? 100 : 1);

                        AddValue(point.PointDateTime, key, stochRSI, indicators);
                    }
                }

                previousPoint = point;
            }
        }

        
        // Money Flow Index
        public static void CalculateMFI(out string key, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, out int offset)
        {
            key = string.Format("MFI({0})", period.ToString("00"));
            offset = period;

            // Typical Price = (High + Low + Close)/3
            double typicalPrice;

            // Raw Money Flow = Typical Price x Volume
            double rawMoneyFlow;

            // Money Flow Ratio = (x-period Positive Money Flow)/(x-period Negative Money Flow)
            double moneyFlowRatio;

            // Money Flow Index = 100 - 100/(1 + Money Flow Ratio)
            double moneyFlowIndex;

            double positiveMoneyFlow = 0D;
            double negativeMoneyFLow = 0D;

            Queue<double> moneyFlow = new Queue<double>();
            double previousTipycalPrice = (data[0].Low + data[0].High + data[0].Close) / 3;
            
            int i = 0;
            foreach (StockPoint point in data.Skip(1))
            {
                typicalPrice = (point.Low + point.High + point.Close) / 3;
                rawMoneyFlow = typicalPrice * point.Volume;
                moneyFlow.Enqueue(rawMoneyFlow * (typicalPrice >= previousTipycalPrice ? 1 : -1));

                if(moneyFlow.Count > period)
                {
                    moneyFlow.Dequeue();
                }

                if (i < period - 1)
                {
                    i++;
                }
                else
                {
                    positiveMoneyFlow = moneyFlow.Where(v => v >= 0).Sum();
                    negativeMoneyFLow = Math.Abs(moneyFlow.Where(v => v < 0).Sum());

                    moneyFlowRatio = positiveMoneyFlow / negativeMoneyFLow;
                    moneyFlowIndex = 100 - (100 / (1 + moneyFlowRatio));

                    AddValue(point.PointDateTime, key, moneyFlowIndex, indicators);
                }

                previousTipycalPrice = typicalPrice;
            }
        }

        // TRIX
        public static void CalculateTRIX(out string key, out string key2, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period, int period2, out int offset, out int min, out int max, bool leverage = false)
        {
            min = int.MaxValue;
            max = int.MinValue;

            key = string.Format("TRIX({0}-{1})", period.ToString("00"), period2.ToString("00"));
            key2 = string.Format("TRIX-Smooth-({0}-{1})", period.ToString("00"), period2.ToString("00"));

            offset = 3 * (period > period2 ? period : period2);
            ExponentialMovingAverage ema1 = new ExponentialMovingAverage(period);
            ExponentialMovingAverage ema2 = new ExponentialMovingAverage(period);
            ExponentialMovingAverage ema3 = new ExponentialMovingAverage(period);
            ExponentialMovingAverage ema4 = new ExponentialMovingAverage(period2);
            double singleSmooth;
            double doubleSmooth;
            double tripleSmooth;
            double prevTriplesmooth = -1;
            double trix;

            int i = 0;
            foreach (StockPoint point in data)
            {
                ema1.AddValue(point.Close);
                if (i < period)
                {
                    i++;
                }
                else
                {
                    singleSmooth = ema1.MovingAverage;
                    ema2.AddValue(singleSmooth);
                    if (i < 2 * period)
                    {
                        i++;
                    }
                    else
                    {
                        doubleSmooth = ema2.MovingAverage;
                        ema3.AddValue(doubleSmooth);
                        if (i < 3 * period)
                        {
                            i++;
                        }
                        else
                        {
                            tripleSmooth = ema3.MovingAverage;
                            if (prevTriplesmooth != -1)
                            {
                                trix = (double)decimal.Round((((decimal)tripleSmooth - (decimal)prevTriplesmooth) / (decimal)tripleSmooth) * 100 * (leverage ? 100 : 1), 4);
                                ema4.AddValue(trix);

                                AddValue(point.PointDateTime, key, trix, indicators);
                                AddValue(point.PointDateTime, key2, ema4.MovingAverage, indicators);

                                if (min > trix)
                                    min = (int)Math.Floor(trix);

                                if (max < trix)
                                    max = (int)Math.Ceiling(trix);
                            }
                            prevTriplesmooth = tripleSmooth;
                        }
                    }
                }
            }
        }


        // Full Stochastic
        /// <summary>
        /// Calculates the Full Stochastic Oscillator and fills indicators with the data needed to graph it.
        /// </summary>
        /// <param name="key">Name of the first set to put in the graph key</param>
        /// <param name="key2">Name of the second set to put in the graph key</param>
        /// <param name="data">StockPoints to calculate the Full Stochastic with.</param>
        /// <param name="indicators">The mfvvalues needed to graph the Full Stochastic.</param>
        /// <param name="period1">The number of period to calculate Fast Stochastic.</param>
        /// <param name="period2">The number of period to calculate SMA of Fast Stochastic.</param>
        /// <param name="period3">The number of period to calculate SMA of calculated SMA of Fast Stochastic.</param>
        /// <param name="offset">Where to start the graph.</param>
        public static void CalculateStochastic(out string key, out string key2, StockPoints data, Dictionary<DateTime, Dictionary<string, double>> indicators, int period1, int period2, int period3, out int offset)
        {
            key = string.Format("Full STO %K({0}:{1})", period1.ToString("00"), period2.ToString("00"));
            key2 = string.Format("Full STO %D({0})", period3.ToString("00"));

            offset = period1 - 1;

            SimpleMovingAverage ksma = new SimpleMovingAverage(period2);
            SimpleMovingAverage dsma = new SimpleMovingAverage(period3);

            // lowest among the lowest for the chosen period
            double lowestLow;

            // highest among the highest for the chosen period
            double highestHigh;

            // Lowest Low = lowest low for the look-back period
            // Highest High = highest high for the look-back period
            // fastk = (Current Close - Lowest Low)/(Highest High - Lowest Low) * 100
            double fastk;

            // fullk smoothed with X-period SMA
            double fullk;

            // X-period SMA of Full fullk
            double fulld;

            int i = 0;
            // loop through each stockpoint skipping the 
            foreach (StockPoint point in data)
            {
                lowestLow = data.Skip(data.IndexOf(point) - (period1 - 1)).Take(period1).OrderBy(sp => sp.Low).FirstOrDefault().Low;
                highestHigh = data.Skip(data.IndexOf(point) - (period1 - 1)).Take(period1).OrderByDescending(sp => sp.High).FirstOrDefault().High;

                fastk = (point.Close - lowestLow) / (highestHigh - lowestLow == 0 ? 1 : highestHigh - lowestLow) * 100;
                ksma.AddValue(fastk);
                fullk = ksma.MovingAverage();

                dsma.AddValue(fullk);
                fulld = dsma.MovingAverage();

                if (i < period1 - 1)
                {
                    i++;
                }
                else
                {
                    AddValue(point.PointDateTime, key, fullk, indicators);
                    AddValue(point.PointDateTime, key2, fulld, indicators);
                }
            }
        }


        /// ********************************************************
        /// GDBCup - More indicator definitions will be added here
        /// ********************************************************


        /// <summary>
        /// Add a value to the dictionary for the graph.
        /// </summary>
        /// <param name="key1">The date and time of the value.</param>
        /// <param name="key2">The name of the line to graph.</param>
        /// <param name="value">The value to graph.</param>
        /// <param name="map">The dictonary to add the point to.</param>
        public static void AddValue(DateTime key1, string key2, double value, Dictionary<DateTime, Dictionary<string, double>> map)
        {
            if (map.ContainsKey(key1))
            {
                if (!map[key1].ContainsKey(key2))
                {
                    map[key1].Add(key2, value);
                }
            }
            else
            {
                Dictionary<string, double> temp = new Dictionary<string, double>();
                temp.Add(key2, value);
                map.Add(key1, temp);
            }
        }

        /// <summary>
        /// Add a horizontal line to the graph
        /// </summary>
        /// <param name="key">The name of the line to graph.</param>
        /// <param name="data">A list of stock points to get the date time range. (x-axis)</param>
        /// <param name="lineValue">The location of the line. (y-axis)</param>
        /// <param name="indicators">The dictionary to add the line to.</param>
        public static void GetHorizontalLine(string key, StockPoints data, double lineValue, Dictionary<DateTime, Dictionary<string, double>> indicators)
        {
            for (int i = 0; i < data.Count; i++)
            {
                AddValue(data[i].PointDateTime, key, lineValue, indicators);
            }
        }

        /// <summary>
        /// Returns the max of 3 mfvvalues.
        /// </summary>
        /// <param name="t1">First value to compare.</param>
        /// <param name="t2">Second value to compare.</param>
        /// <param name="t3">Third value to compare.</param>
        /// <returns>The highest of the 3 given mfvvalues.</returns>
        private static double Max(double t1, double t2, double t3)
        {
            if (t1 >= t2 && t1 >= t3)
            {
                return t1;
            }
            else if (t2 >= t3 && t2 >= t1)
            {
                return t2;
            }
            else
            {
                return t3;
            }
        }
    }
}
