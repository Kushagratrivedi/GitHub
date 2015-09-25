namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// To be determined.
    /// </summary>
    public enum CrossOverType
    {
    }

    /// <summary>
    /// Trade rule type
    /// </summary>
    public enum RuleType
    {
    }

    /// <summary>
    /// Rule compare type
    /// </summary>
    [Serializable]
    public enum IndicatorCompareType
    {
        /// <summary>
        /// Greater Than
        /// </summary>
        GT,

        /// <summary>
        /// Less Than--
        /// </summary>
        LT,
        UP_XOVER,
        DOWN_XUNDER

        /// *********************************************
        /// GDBCup - Could have more compare types here
        /// *********************************************
        
    }


    /// <summary>
    /// To be determined.
    /// </summary>
    public enum TechnicalIndicator
    {
    }

    /// <summary>
    /// The join types for two trade rules
    /// </summary>
    public enum RuleJoinType
    {
        /// <summary>
        /// No join type
        /// </summary>
        none,

        /// <summary>
        /// Join with an and
        /// </summary>
        and,

        /// <summary>
        /// Join with an or
        /// </summary>
        or
    }

    /// <summary>
    /// A long or short trade.
    /// </summary>
    public enum TradeType
    {
        /// <summary>
        /// A short trade that makes money when the price goes down.
        /// </summary>
        shortTrade,

        /// <summary>
        /// A long trade that makes money when the price goes up.
        /// </summary>
        longTrade
    }

    /// <summary>
    /// What point a trade is at.
    /// </summary>
    public enum TradeStatus
    {
        /// <summary>
        /// Looking to buy a contract.
        /// </summary>
        buying,
        
        /// <summary>
        /// Looking to sell contracts.
        /// </summary>
        selling,

        /// <summary>
        /// The trade is complete.
        /// </summary>
        complete
    }
}
