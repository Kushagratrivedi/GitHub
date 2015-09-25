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
    /// Trade rule class (xml serializable)
    /// </summary>
    [XmlRoot("TradeRule")]
    public abstract class TradeRule
    {
        /// <summary>
        /// Initializes a new instance of the TradeRule class.
        /// </summary>
        /// <param name="indicator1">First indicator.</param>
        /// <param name="compareType">Type of comparison.</param>
        /// <param name="indicator2">Second indicator.</param>
        public TradeRule(string indicator1, IndicatorCompareType compareType, string indicator2)
        {
            this.IndicatorName1 = indicator1;
            this.CompareType = compareType;
            this.IndicatorName2 = indicator2;
        }

        /// <summary>
        /// Initializes a new instance of the TradeRule class.
        /// Default constructor for serialization.
        /// </summary>
        public TradeRule()
        {
        }

        /// <summary>
        /// Gets or sets the first indicator
        /// </summary>
        [XmlElement("IndicatorName1")]
        public string IndicatorName1 { get; set; }

        /// <summary>
        /// Gets or sets the second indicator
        /// </summary>
        [XmlElement("IndicatorName2")]
        public string IndicatorName2 { get; set; }

        /// <summary>
        /// Gets or sets the comparison type
        /// </summary>
        [XmlElement("CompareType")]
        public IndicatorCompareType CompareType { get; set; }

        /// <summary>
        /// Gets the name of the trade rule
        /// </summary>
        [XmlIgnore()]
        public string Name 
        {
            get
            {
                return this.IndicatorName1 + " " + this.CompareType.ToString() + " " + this.IndicatorName2;
            }
        }

        /// <summary>
        /// Evaluates the two indicators as passed
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The indicatorLibraryAdapter to use for the indicator evaluation.</param>
        /// <returns>A boolean value of the evaluation.</returns>
        public abstract bool Eval(IndicatorLibraryAdapter indicatorLibraryAdapter);
    }
}
