namespace FDMTermProject.GeneticAlgorithmNS
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;

    /// <summary>
    /// A helper class for generating indicators at random.
    /// </summary>
    public class IndicatorHelper
    {
        /// <summary>
        /// A list of all the indicators.
        /// </summary>
        private string[] indicators;

        /// <summary>
        /// A random number generator.
        /// </summary>
        private Random rand;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group A is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupA;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group B is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupB;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group C is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupC;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group D is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupD;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group E is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupE;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group F is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupF;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group G is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupG;

        /// <summary>
        /// A set of comparable indicators.
        /// GDBCup - Group H is a set of indicators that can be compared to each other
        /// </summary>
        private string[] indicatorsGroupH;

        /// <summary>
        /// Initializes a new instance of the IndicatorHelper class.
        /// </summary>
        /// <param name="indicators">The indicators to use for generation.</param>
        public IndicatorHelper(string[] indicators)
        {
            this.indicators = indicators;
            this.rand = new Random();
            this.PopulateIndicatorGroups();
        }

        /// <summary>
        /// Get a set of 2 random indicators and a compare type.
        /// </summary>
        /// <param name="indicator1">The first random indicator.</param>
        /// <param name="compareType">The compare type for the indicator set.</param>
        /// <param name="indicator2">The second random indicator.</param>
        public void GetRandomIndicators(out string indicator1, out IndicatorCompareType compareType, out string indicator2)
        {   
            indicator1 = this.GetRandomIndicator();
            compareType = this.GetRandomCompareType();
            indicator2 = this.GetRandomIndicator(indicator1, compareType);
        }

        /// <summary>
        /// Gets a rule join type at random (and, or) with or's being less frequent.
        /// </summary>
        /// <returns>a random rule join type (and, or)</returns>
        public RuleJoinType GetRandomRuleJoinType()
        {
            int num = this.rand.Next(10);
            if (num < 3)
            {
                return RuleJoinType.or;
            }
            else
            {
                return RuleJoinType.and;
            }
        }

        /// <summary>
        /// Gets an indicator from the set at random.
        /// </summary>
        /// <returns>An indicator </returns>
        private string GetRandomIndicator()
        {
            int num;
            do
            {
                num = this.rand.Next(this.indicators.Length);
            } 
            while (num < 7 && !(num >= 2 && num <= 5));
            return this.indicators[num];
        }

        /// <summary>
        /// Gets a second indicator that can be used with the given indicator and compare type.
        /// </summary>
        /// <param name="indicator">Indicator to compare to.</param>
        /// <param name="compareType">Compare type for the indicators.</param>
        /// <returns>A random indicator to caompare with indicator and compareType.</returns>
        private string GetRandomIndicator(string indicator, IndicatorCompareType compareType)
        {
            int num;
            double floatingNum;
            if (indicator.StartsWith("Open", true, null) ||
                indicator.StartsWith("High", true, null) ||
                indicator.StartsWith("Low", true, null) ||
                indicator.StartsWith("Close", true, null) ||
                indicator.StartsWith("EMA", true, null))
            {
                return this.indicatorsGroupA[this.rand.Next(this.indicatorsGroupA.Length)];
            }
            /// *********************************************
            /// GDBCup - Replace SomeIndicatorName with an Indicator name
            /// *********************************************
            else if (indicator.StartsWith("SomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {

                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else if (indicator.StartsWith("AnotherSomeIndicatorName", true, null))
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
            else
            {
                /// *********************************************
                /// GDBCup - Code is needed here
                /// *********************************************
                return "someString"; /// Revise once coded up
            }
        }

        /// <summary>
        /// Get an indicator compare type at random.
        /// </summary>
        /// <returns>An indicator compare type.</returns>
        private IndicatorCompareType GetRandomCompareType()
        {
            int num = this.rand.Next(10);
            switch (num)
            {
                case 0: return IndicatorCompareType.GT;
                case 1: return IndicatorCompareType.LT;
                /// *********************************************
                /// GDBCup - Could have more compare types here
                /// *********************************************
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                default: return IndicatorCompareType.GT;
            }
        }

        /// <summary>
        /// Fill the indicator groups with the indicators.
        /// </summary>
        private void PopulateIndicatorGroups()
        {
            List<string> listA = new List<string>();
            List<string> listB = new List<string>();
            List<string> listC = new List<string>();
            List<string> listD = new List<string>();
            List<string> listE = new List<string>();
            List<string> listF = new List<string>();
            List<string> listG = new List<string>();
            List<string> listH = new List<string>();

            /// *****************************************************
            /// GDBCup - Statements in for loop below need some work
            /// You may need to add/delete lines and replace terms
            /// *****************************************************

            foreach (string indicator in this.indicators)
            {
                if (indicator.StartsWith("Open", true, null) ||
                    indicator.StartsWith("High", true, null) ||
                    indicator.StartsWith("Low", true, null) ||
                    indicator.StartsWith("Close", true, null) ||
                    indicator.StartsWith("IndicatorNameHere", true, null))
                {
                    listA.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listB.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listC.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null) ||
                    indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listD.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listE.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listF.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listG.Add(indicator);
                }
                else if (indicator.StartsWith("AnotherIndicatorNameHere", true, null))
                {
                    listH.Add(indicator);
                }
            }

            this.indicatorsGroupA = listA.ToArray();
            this.indicatorsGroupB = listB.ToArray();
            this.indicatorsGroupC = listC.ToArray();
            this.indicatorsGroupD = listD.ToArray();
            this.indicatorsGroupE = listE.ToArray();
            this.indicatorsGroupF = listF.ToArray();
            this.indicatorsGroupG = listG.ToArray();
            this.indicatorsGroupH = listH.ToArray();
        }
    }
}
