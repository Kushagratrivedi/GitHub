namespace FDMTermProject.Entities.TradeRules
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// A custom exception class
    /// </summary>
    [Serializable]
    public class TradeRuleException : ApplicationException
    {
        /// <summary>
        /// Initializes a new instance of the TradeRuleException class
        /// </summary>
        /// <param name="message">The exception's message</param>
        /// <param name="innerException">The exception which caused this exception</param>
        public TradeRuleException(string message, Exception innerException) :
            base(message, innerException)
        { 
        }

        /// <summary>
        /// Initializes a new instance of the TradeRuleException class
        /// </summary>
        /// <param name="message">The exception's message</param>
        public TradeRuleException(string message) : base(message)
        { 
        }
    }
}
