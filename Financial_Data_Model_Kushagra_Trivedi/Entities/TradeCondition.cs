namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Xml.Serialization;
    using FDMTermProject.Entities.TradeRules;

    /// <summary>
    /// A trade condition that is a set of joined trade rules.
    /// </summary>
    public class TradeCondition
    {
        /// <summary>
        /// Initializes a new instance of the TradeCondition class.
        /// </summary>
        public TradeCondition()
        {
            this.TradeRules = new List<TradeRule>();
            this.RuleJoinTypes = new List<RuleJoinType>();
        }

        /// <summary>
        /// Gets or sets the set of trade rules.
        /// </summary>
        [XmlArray("tradeRules")]
        [XmlArrayItem("SimpleCompareTradeRule", typeof(SimpleCompareTradeRule))]
        [XmlArrayItem("TradeRule", typeof(TradeRule))]
        /// **********************************************
        /// GDBCup - More compares could be added here
        /// **********************************************
        public List<TradeRule> TradeRules
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the set of joins for the trade rules.
        /// </summary>
        [XmlArray("ruleJoinTypes")]
        public List<RuleJoinType> RuleJoinTypes
        {
            get;
            set;
        }

        /// <summary>
        /// Gets the name of the trade condition.
        /// </summary>
        public string Name
        {
            get
            {
                string name = string.Empty;
                for (int i = 0; i < this.TradeRules.Count; i++)
                {
                    name += this.TradeRules[i].Name + " " + this.RuleJoinTypes[i].ToString();
                }

                return name;
            }
        }

        /// <summary>
        /// Add a new trade rule and join condition.
        /// </summary>
        /// <param name="tradeRule">The trade rule to add.</param>
        /// <param name="ruleJoinType">The join condition to add.</param>
        public void Add(TradeRule tradeRule, RuleJoinType ruleJoinType)
        {
            if (this.RuleJoinTypes.Count > 0 && this.RuleJoinTypes.Last() == RuleJoinType.none)
            {
                throw new TradeRuleException("Can not add another trade rule. The previous join type was none.");
            }

            this.TradeRules.Add(tradeRule);
            this.RuleJoinTypes.Add(ruleJoinType);
        }

        /// <summary>
        /// Evaluates the trade rule for the current indicatorLibraryAdapter line.
        /// </summary>
        /// <param name="indicatorLibraryAdapter">The data source to evaluate.</param>
        /// <returns>A boolean value for the conditionon this line.</returns>
        public bool Eval(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            if (this.TradeRules.Count == 0)
            {
                throw new TradeRuleException("Trade rules not defined.");
            }
            
            bool evalValue = true;
            /// *********************************************
            /// GDBCup - Need to code this
            /// *********************************************
            
            return evalValue;
        }


        /// <summary>
        /// Deep clone the object by creating new objects all the way down.
        /// </summary>
        /// <returns>A new object with a different reference and same data.</returns>
        internal TradeCondition Clone()
        {
            return (TradeCondition)this.MemberwiseClone(); // TODO change later
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
        }
    }
}
