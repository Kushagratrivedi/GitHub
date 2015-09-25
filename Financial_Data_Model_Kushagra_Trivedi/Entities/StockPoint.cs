namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// Contains the OHLCV data for a specific stock points in time.
    /// </summary>
    [Serializable]
    public class StockPoint
    {
        /// <summary>
        /// Cached value for ValidationErrors
        /// </summary>
        //private List<string> validationErrors = null;

        /// <summary>
        /// Initializes a new instance of the StockPoint class.
        /// </summary>
        public StockPoint()
        {
            /// *********************************************
            /// GDBCup - Code may be needed here
            /// *********************************************
        }

        /// <summary>
        /// Initializes a new instance of the StockPoint class with the given mfvvalues.
        /// </summary>
        /// <param name="open">The opening value</param>
        /// <param name="high">The highest value during the point's period</param>
        /// <param name="low">The lowest value during the point's period</param>
        /// <param name="close">The closing value</param>
        /// <param name="volume">The number of trades during the point's period.</param>
        /// <param name="time">The ending time for the point's period</param>
        /// <param name="date">The date of the point</param>
        public StockPoint(string date, string time, double open, double high, double low, double close, long volume)
        {
            this.Open   = open;
            this.High   = high;
            this.Low    = low;
            this.Close  = close;
            this.Volume = volume;

            this.Time = time;
            this.Date = date;            

            this.PointDateTime = this.ParseDateTime(date, time);
        }

        public StockPoint(DateTime datetime, double open, double high, double low, double close, long volume)
        {
            this.Open = open;
            this.High = high;
            this.Low = low;
            this.Close = close;
            this.Volume = volume;

            this.Time = datetime.ToString("HH:mm");
            this.Date = datetime.ToString("yyyyMMdd");

            this.PointDateTime = datetime;
        }

        /// <summary>
        /// Gets the date and time of the stock point
        /// </summary>
        public DateTime PointDateTime { get; private set; }

        /// <summary>
        /// Gets the date string passed in
        /// </summary>
        public string Date { get; private set; }

        /// <summary>
        /// Gets the time string passed in
        /// </summary>
        public string Time { get; private set; }

        /// <summary>
        /// Gets the open stock price for period
        /// </summary>
        public double Open { get; private set; }

        /// <summary>
        /// Gets the high stock price for period 
        /// </summary>
        public double High { get; private set; }

        /// <summary>
        /// Gets the low stock price for period
        /// </summary>
        public double Low { get; private set; }

        /// <summary>
        /// Gets the closing stock price for period
        /// </summary>
        public double Close { get; private set; }

        /// <summary>
        /// Gets the number of stock trades for period
        /// </summary>
        public long Volume { get; private set; }

        /// <summary>
        /// Gets the a list of errors with the given period
        /// </summary>
        
        /// <summary>
        /// ToString override.
        /// </summary>
        /// <returns>Comma seperated list of Date, Time, and OHLCV mfvvalues</returns>
        public override string ToString()
        {
            return this.Date + ", " + this.Time + ", " + this.Open + ", " + this.High + ", " + this.Low + ", " + this.Close + ", " + this.Volume;
        }

        /// <summary>
        /// Determines if the time is in normal hours. (08:30 - 15:15)
        /// </summary>
        /// <returns>Returns true if the time of day is in normal hours.</returns>
        public bool IsNormalHours()
        {
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
            return true;
        }

        /// <summary>
        /// Determines if the time is in normal hours. (08:30 - 14:45)
        /// </summary>
        /// <returns>Returns true if the time of day is in normal hours.</returns>
        public bool IsTradingHours()
        {
            /// ************************************************
            /// GDBCup - Need to code
            /// ************************************************
            return true;
        }

        /// <summary>
        /// Converts a string date and string time to a datetime object. 
        /// </summary>
        /// <param name="dateString">The date as a string.</param>
        /// <param name="timeString">The time as a string.</param>
        /// <returns>
        /// DateTime object with the given date and time.
        /// Returns the minimum date if the dateString is invalid.
        /// </returns>
        private DateTime ParseDateTime(string dateString, string timeString)
        {
            DateTime dt = default(DateTime);
            try
            {
                dt = DateTime.Parse(string.Format("{0}/{1}/{2} {3}:{4}:00",
                                                dateString.Substring(4, 2),
                                                dateString.Substring(6, 2),
                                                dateString.Substring(0, 4),
                                                new String(timeString.TakeWhile(c => c != ':').ToArray()),
                                                timeString.Substring(timeString.Length - 2, 2)));
            }
            catch (Exception) { }

            return dt;

            //int closingYear, closingMonth, closingDay, closingHour, closingMin;
            //if (dateString.Length == 8)
            //{
            //    closingYear = Int32.Parse(dateString.Substring(0, 4));
            //    closingMonth = Int32.Parse(dateString.Substring(4, 2));
            //    closingDay = Int32.Parse(dateString.Substring(6, 2));
            //}
            //else
            //{
            //    closingYear = 0;
            //    closingMonth = 0;
            //    closingDay = 0;
            //}

            //string[] temp = timeString.Split(':');
            //if (temp.Length == 2)
            //{
            //    closingHour = Int32.Parse(temp[0]);
            //    closingMin = Int32.Parse(temp[1]);
            //}
            //else
            //{
            //    closingHour = 0;
            //    closingMin = 0;
            //}

            //if (closingYear != 0)
            //{
            //    return new DateTime(closingYear, closingMonth, closingDay, closingHour, closingMin, 0);
            //}

            //return DateTime.MinValue;
        }
    }
}
