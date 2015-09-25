namespace FDMTermProject.Entities
{
    using System;
    using System.Collections;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Xml;
    using System.Xml.Serialization;

    /// <summary>
    /// A derived class of TradeRule which compares two indicators
    /// </summary>
    public class SimpleCompareTradeRule : TradeRule
    {
        /// <summary>
        /// Initializes a new instance of the SimpleCompareTradeRule class.
        /// </summary>
        /// <param name="indicator1">First indicator.</param>
        /// <param name="compareType">Type of comparison.</param>
        /// <param name="indicator2">Second indicator.</param>
        public SimpleCompareTradeRule(string indicator1, IndicatorCompareType compareType, string indicator2)
            : base(indicator1, compareType, indicator2)
        { 
        }

        /// <summary>
        /// Initializes a new instance of the SimpleCompareTradeRule class.
        /// Default constructor for XML Serialization.
        /// </summary>
        public SimpleCompareTradeRule()
        {
        }

        /// <summary>
        /// Evaluates the two indicators as passed
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The indicatorLibraryAdapter to use for the indicator evaluation.</param>
        /// <returns>A boolean value of the evaluation.</returns>
        public override bool Eval(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            double? indicator1 = indicatorLibraryAdapter.GetIndicator(this.IndicatorName1);
            double? indicator2 = indicatorLibraryAdapter.GetIndicator(this.IndicatorName2);

            double? previousIndicator1 = indicatorLibraryAdapter.GetPreviousIndicator(this.IndicatorName1);
            double? previousIndicator2 = indicatorLibraryAdapter.GetPreviousIndicator(this.IndicatorName2);

            if (indicator1 == null || indicator2 == null)
            {
                return false;
            }

            switch (this.CompareType)
            {
                case IndicatorCompareType.GT:
                    return indicator1 > indicator2;
                case IndicatorCompareType.LT:
                    return indicator1 < indicator2;
                case IndicatorCompareType.UP_XOVER:
                    return previousIndicator1 < previousIndicator2 && indicator1 > indicator2;
                case IndicatorCompareType.DOWN_XUNDER:
                    return previousIndicator1 > previousIndicator2 && indicator1 < indicator2;
                default:
                    throw new Exception("Compare Type not defined");
            }
        }
    }
}
