using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject.Library
{
    public enum IndicatorType
    {
        Overlay,
        Separate
    }
    public enum IndicatorValue
    {
        None = 1,
        SMA,
        EMA,
        Bollinger_Band,
        RSI,
        Full_Stochastic_Oscillator,
        MACD,
        Chaikin_Oscillator,
        Chaikin_Money_Flow,
        Aroon,
        Aroon_Oscillator,
        Stochastic_RSI,
        Money_Flow_Index,
        Commodity_Channel_Index,
        TRIX
    }

    public class TechnicalIndicatorClass
    {
        public string Name { get; private set; }
        public List<int> Defaultvalues { get; private set; }
        public string ShortName { get; set; }
        public IndicatorType Type { get; set; }
        public IndicatorValue Value { get; set; }
        public double UpperLimit { get; private set; }
        public double MaxVal { get; private set; }
        public double LowerLimit { get; private set; }
        public double MinVal { get; private set; }
        public TechnicalIndicatorClass(string name, string shortname, IndicatorValue value, List<int> values, IndicatorType type, double upperlimit = double.MaxValue, double lowerlimit = double.MinValue, double maxvalue = double.MaxValue, double minvalue = double.MinValue)
        {
            this.Name = name;
            this.Defaultvalues = values == null ? new List<int>() : values;
            this.ShortName = shortname;
            this.Value = value;
            this.Type = type;
            this.UpperLimit = upperlimit;
            this.LowerLimit = lowerlimit;
            this.MaxVal = maxvalue;
            this.MinVal = minvalue;
        }

        /// <summary>
        /// Gets the list of Indicators
        /// </summary>
        /// <returns>List of Separate Indicators</returns>
        public static List<TechnicalIndicatorClass> GetIndicators(bool needNullValue, string name = null)
        {
            List<TechnicalIndicatorClass> indicatorList = new List<TechnicalIndicatorClass>()
            {
                /*Separate*/
                new TechnicalIndicatorClass("Relative Strength Indicator", "RSI", IndicatorValue.RSI, new List<int>{ 14 }, IndicatorType.Separate, 70D, 30D, 100D, 0D),
                new TechnicalIndicatorClass("Full Stochastic Oscillator", "FullStoOsc", IndicatorValue.Full_Stochastic_Oscillator, new List<int>{ 14, 3, 3 }, IndicatorType.Separate, 80D, 20D, 100D, 0D),
                new TechnicalIndicatorClass("Moving Average Convergence Divergence", "MACD", IndicatorValue.MACD, new List<int>{ 12, 26, 9 }, IndicatorType.Separate),
                new TechnicalIndicatorClass("Chaikin Oscillator", "ChiOsc", IndicatorValue.Chaikin_Oscillator, new List<int>{ 3, 10 }, IndicatorType.Separate),
                new TechnicalIndicatorClass("Aroon Oscillator", "AroonOsc", IndicatorValue.Aroon_Oscillator, new List<int>{ 25 }, IndicatorType.Separate, 90D, -90D, 100D, -100D),
                new TechnicalIndicatorClass("Aroon", "Aroon", IndicatorValue.Aroon, new List<int>{ 25 }, IndicatorType.Separate, 70D, 30D, 100D, 0D),
                new TechnicalIndicatorClass("Stochastic RSI", "StocRSI", IndicatorValue.Stochastic_RSI, new List<int>{ 14 }, IndicatorType.Separate, 80D, 20D, 100D, 0D),
                new TechnicalIndicatorClass("Money Flow Index", "MFI", IndicatorValue.Money_Flow_Index, new List<int>{ 25 }, IndicatorType.Separate, 80D, 20D, 100D, 0D),
                new TechnicalIndicatorClass("Commodity Channel Index", "CCI", IndicatorValue.Commodity_Channel_Index, new List<int>{ 20 }, IndicatorType.Separate, double.MaxValue, double.MinValue, 100D, -100D),
                new TechnicalIndicatorClass("Chaikin Money Flow", "CMF", IndicatorValue.Chaikin_Money_Flow, new List<int>{ 20 }, IndicatorType.Separate),
                new TechnicalIndicatorClass("TRIX", "TRIX", IndicatorValue.TRIX, new List<int>{ 15, 9 }, IndicatorType.Separate),

                /*Overlay*/
                new TechnicalIndicatorClass("Simple Moving Average", "SMA", IndicatorValue.SMA, new List<int>{ 10 }, IndicatorType.Overlay),
                new TechnicalIndicatorClass("Exponential Moving Average", "EMA", IndicatorValue.EMA, new List<int>{ 10 }, IndicatorType.Overlay),
                new TechnicalIndicatorClass("Bollinger Band", "BollBnd", IndicatorValue.Bollinger_Band, new List<int>{ 20, 2 }, IndicatorType.Overlay)
            }
            .OrderBy(si => si.Name).ToList();

            if (needNullValue)
            {
                indicatorList.Insert(0, new TechnicalIndicatorClass(name == null ? "None" : name, "None", IndicatorValue.None, null, IndicatorType.Separate));
            }

            return indicatorList;
        }

        public static List<List<string>> GetGroupsOfIndicators(IEnumerable<string> indicators)
        {
            Dictionary<int, List<IndicatorValue>> groups = new Dictionary<int, List<IndicatorValue>>();
            
            List<TechnicalIndicatorClass> indicatorClasses = GetIndicators(false);
            var listOfOverlays = indicatorClasses.Where(i => i.Type == IndicatorType.Overlay).ToList();
            var listOfSeperates = indicatorClasses.Where(i => i.Type == IndicatorType.Separate).ToList();
            var grpSeperates = listOfSeperates.Where(id=>id.MaxVal != double.MaxValue && id.MinVal != double.MinValue).GroupBy(g => new { g.MinVal, g.MaxVal });
            var seperates = listOfSeperates.Where(id => id.MaxVal == double.MaxValue && id.MinVal == double.MinValue);

            groups.Add(1, listOfOverlays.Select(id=>id.Value).Concat(new List<IndicatorValue>() { IndicatorValue.None, IndicatorValue.None }).ToList()); // open, high, low, close
                        
            for (int i = 0; i < seperates.Count(); i++)
            {
                groups.Add(i + 2, new List<IndicatorValue>() { seperates.ElementAt(i).Value });
            }
            var count = groups.Count;
            for(int i = 0; i < grpSeperates.Count(); i++)
            {
                count++;
                groups.Add(count, grpSeperates.ElementAt(i).Select(id => id.Value).ToList());
            }

            List<List<string>> lists = new List<List<string>>();
            foreach (var grp in groups)
            {
                List<string> indicatorLists = indicators.Where(id => GetIndicator(id)!= null && grp.Value.Contains(GetIndicator(id).Value)).ToList();
                lists.Add(indicatorLists);
            }

            return lists;
        }

        private static IndicatorValue? GetIndicator(string indicator)
        {
            IndicatorValue? returnValue = null;
            if(indicator.StartsWith("SMA", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.SMA;
            }
            else if(indicator.StartsWith("EMA", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.EMA;
            }
            else if(indicator.StartsWith("AroonOsc", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Aroon_Oscillator;
            }
            else if(indicator.StartsWith("Aroon", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Aroon;
            }
            else if(indicator.StartsWith("BB", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Bollinger_Band;
            }
            else if(indicator.StartsWith("CMF", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Chaikin_Money_Flow;
            }
            else if(indicator.StartsWith("ChiOsc", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Chaikin_Oscillator;
            }
            else if(indicator.StartsWith("AroonOsc", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Aroon_Oscillator;
            }
            else if (indicator.StartsWith("CCI", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Commodity_Channel_Index;
            }
            else if (indicator.StartsWith("Full Sto", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Full_Stochastic_Oscillator;
            }
            else if (indicator.StartsWith("MFI", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Money_Flow_Index;
            }
            else if (indicator.StartsWith("MACD", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.MACD;
            }
            else if (indicator.StartsWith("RSI", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.RSI;
            }
            else if (indicator.StartsWith("StocRSI", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.Stochastic_RSI;
            }
            else if (indicator.StartsWith("TRIX", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.TRIX;
            }
            else if (indicator.StartsWith("Open", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.None;
            }
            else if (indicator.StartsWith("Close", StringComparison.OrdinalIgnoreCase))
            {
                returnValue = IndicatorValue.None;
            }

            return returnValue;
        }

        public static Dictionary<string, List<double>> GetIndicatorsDefaultCrossOvers(IEnumerable<string> indicators)
        {
            var retVal = new Dictionary<string, List<double>>();
            var inds = GetIndicators(false);
            foreach (var indicator in indicators)
            {
                var dbls = new List<double>();
                var indVal = GetIndicator(indicator);

                if (indVal != null && indVal.Value != IndicatorValue.None)
                {
                    dbls.Add(inds.First(i => i.Value == indVal.Value).LowerLimit);
                    dbls.Add(inds.First(i => i.Value == indVal.Value).UpperLimit);

                    if (dbls[0] != double.MinValue && dbls[1] != double.MaxValue)
                    {
                        if (!retVal.ContainsKey(indicator))
                        {
                            retVal.Add(indicator, dbls);
                        }
                    }
                }
            }

            return retVal;
        }
    }
}
