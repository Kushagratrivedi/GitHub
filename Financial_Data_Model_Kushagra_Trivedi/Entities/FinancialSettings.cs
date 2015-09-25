namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// Financial settings for backtesting.
    /// </summary>
    public class FinancialSettings
    {
        /// <summary>
        /// Initializes a new instance of the FinancialSettings class.
        /// Default constructor needed for serialization.
        /// </summary>
        public FinancialSettings()
        {
            this.MaxDrawdown = 5.0;
        }

        /// <summary>
        /// Initializes a new instance of the FinancialSettings class.
        /// </summary>
        /// <param name="dollarsPerPoint">Cost in dollars of each point gained or lost</param>
        /// <param name="equityPerContract">Equity per contract</param>
        /// <param name="initialCapital">Starting capital</param>
        /// <param name="maxContracts">Maximum number of contracts that can be bought</param>
        /// <param name="maxDrawdown">Stop loss value</param>
        /// <param name="roundTripCommission">Round trip commission</param>
        public FinancialSettings(
            double dollarsPerPoint, 
            double equityPerContract,
            double initialCapital,
            int maxContracts,
            double maxDrawdown,
            double roundTripCommission)
        {
            this.DollarsPerPoint = dollarsPerPoint;
            this.EquityPerContract = equityPerContract;
            this.InitialCapital = initialCapital;
            this.MaxContracts = maxContracts;
            this.MaxDrawdown = maxDrawdown;
            this.RoundTripCommission = roundTripCommission;
        }

        /// <summary>
        /// Gets or sets the stop loss value.
        /// </summary>
        public double MaxDrawdown
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets equity per contract.
        /// </summary>
        public double EquityPerContract
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the maximum number of contracts that can be bought.
        /// </summary>
        public int MaxContracts
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the round trip commission.
        /// </summary>
        public double RoundTripCommission
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets the cost in dollars of each point gained or lost.
        /// </summary>
        public double DollarsPerPoint
        {
            get;
            set;
        }

        /// <summary>
        /// Gets or sets starting capital.
        /// </summary>
        public double InitialCapital
        {
            get;
            set;
        }
    }
}
