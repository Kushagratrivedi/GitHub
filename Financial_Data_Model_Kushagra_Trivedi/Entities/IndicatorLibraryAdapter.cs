namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.IO;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// Pulls the indicator information from a csv file one item at a time.
    /// </summary>
    [Serializable]
    public class IndicatorLibraryAdapter
    {
        /// <summary>
        /// The indicators split from the csv.
        /// </summary>
        private double?[] indicators;

        /// <summary>
        /// The indicators split from the csv previous to the current one
        /// </summary>
        private double?[] previousIndicators;

        /// <summary>
        /// The stream reader for the csv file.
        /// </summary>
        //private StreamReader streamReader;

        /// <summary>
        /// The file path for the indicator library.
        /// </summary>
        private string indicatorFile;

        /// <summary>
        /// The stock point for the current line in the indicator library.
        /// </summary>
        private StockPoint stockPoint;

        /// <summary>
        /// Start Date
        /// </summary>
        public DateTime StartDate { get; private set; }

        /// <summary>
        /// End Date
        /// </summary>
        public DateTime EndDate { get; private set; }
        
        /// <summary>
        /// All the data read from the file
        /// </summary>
        public Dictionary<string, List<double?>> Data { get; private set; }

        //public List<Queue<double>> DataQueue { get; private set; }

        /// <summary>
        /// Gets all the stockpoints
        /// </summary>
        public List<StockPoint> StockPoints { get; private set; }

        /// <summary>
        /// Gets the index of first trading point in all the days
        /// </summary>
        public List<int> EndOfDayIndex { get; private set; }

        /// <summary>
        /// Data Table current Row indicator index (zero based)
        /// </summary>
        private int currentLocationIndex;

        /// <summary>
        /// Total number of data
        /// </summary>
        public int DataCount { get; private set; }

        /// <summary>
        /// Initializes a new instance of the IndicatorLibraryAdapter class.
        /// </summary>
        /// <param name="indicatorFile">A file path for the csv file to read.</param>
        public IndicatorLibraryAdapter(string indicatorFile)
        {
            this.indicatorFile = indicatorFile;
            this.Data = new Dictionary<string, List<double?>>();
            //this.DataQueue = new List<Queue<double>>();
            this.StockPoints = new List<StockPoint>();
            this.EndOfDayIndex = new List<int>();

            string[] headerdata;
            string headdata;
            string[] dataSplit;
            string open = string.Empty,
                high = string.Empty,
                low = string.Empty,
                close = string.Empty,
                volume = string.Empty,
                date = string.Empty,
                time = string.Empty;

            DateTime currentTime;
            DateTime previousTime = default(DateTime);
            StockPoint currentsp;
            int index = 0;
            try
            {
                using (FileStream fs = File.Open(indicatorFile, FileMode.Open, FileAccess.Read, FileShare.None))
                using (BufferedStream bs = new BufferedStream(fs))
                using (StreamReader sr = new StreamReader(bs))
                {
                    string line = sr.ReadLine();
                    string value;
                    headerdata = line.Split(',').Select(s => s.Trim()).ToArray();
                    for (int i = 0; i < headerdata.Length; i++)
                    {
                        headdata = headerdata[i];
                        this.Data.Add(headdata, new List<double?>());
                    }

                    while ((line = sr.ReadLine()) != null)
                    {
                        dataSplit = line.Split(',');
                        currentTime = Util.GetDateTimeValue(dataSplit[0], dataSplit[1]);
                        
                        for (int i = 0; i < headerdata.Length; i++)
                        {
                            value = dataSplit[i];
                            headdata = headerdata[i];
                            this.Data[headdata].Add(!string.IsNullOrWhiteSpace(value) ? (double?)double.Parse(value.Replace(":", "")) : null);
                        }

                        currentsp = new StockPoint(currentTime, double.Parse(dataSplit[2]), double.Parse(dataSplit[3]), double.Parse(dataSplit[4]), double.Parse(dataSplit[5]), long.Parse(dataSplit[6]));

                        this.StockPoints.Add(currentsp);
                        if (previousTime.ToShortDateString() != currentTime.ToShortDateString())
                        {
                            if (index > 0)
                            {
                                this.EndOfDayIndex.Add(index - 1);
                            }
                            previousTime = currentsp.PointDateTime;
                        }
                        index++;
                    }
                }
            }
            catch (Exception)
            {
                throw new FileLoadException("File cannot be read. Make sure the file is not opened by another program");
            }

            //foreach (KeyValuePair<string, List<double?>> kv in this.Data)
            //{
            //    this.DataQueue.Add(new Queue<double>(kv.Value.Select(v => ConvertDouble(v))));
            //}

            this.DataCount = index;
            this.EndOfDayIndex.Add(index - 1);

            this.StartDate = ParseDateTime(this.Data["Date"].FirstOrDefault().Value, this.Data["Time"].FirstOrDefault().Value);
            this.EndDate = ParseDateTime(this.Data["Date"].LastOrDefault().Value, this.Data["Time"].LastOrDefault().Value);

            currentLocationIndex = -1;

            MoveNext();

            #region oldcode
            //this.indicatorFile = indicatorFile;
            //this.IndicatorLocations = new Dictionary<string, int>();
            //this.streamReader = new StreamReader(File.Open(this.indicatorFile, System.IO.FileMode.Open));
            //this.MoveNext();
            //for (int i = 0; i < this.indicators.Length; i++)
            //{
            //    this.IndicatorLocations.Add(this.indicators[i].Trim(), i);
            //}
            //this.MoveNext();
            #endregion oldcode
        }

        private double ConvertDouble(string strValue)
        {
            if (string.IsNullOrEmpty(strValue))
                return 0D;
            else
                return double.Parse(strValue.Replace(":", ""));
        }

        private DateTime ParseDateTime(double dateVal, double timeVal)
        {
            DateTime dt = DateTime.Now;
            try
            {
                //mm/dd/yyyy
                dt = DateTime.Parse(string.Format("{0}/{1}/{2} {3}:{4}:00",
                                                (int)dateVal/10000,
                                                (((int)dateVal) % 10000)/100,
                                                ((int)dateVal)%100,
                                                ((int)timeVal)/100,
                                                ((int)timeVal)%100));
            }
            catch (Exception)
            {
            }

            return dt;
        }

        /// <summary>
        /// Gets the indicator names and the associated column.
        /// </summary>
        public Dictionary<string, int> IndicatorLocations
        {
            get
            {
                return Enumerable.Range(0, this.Data.Keys.Count).ToDictionary(idx => this.Data.Keys.ElementAt(idx), idx => idx);
            }
        }

        //public string[] Peek()
        //{
        //    string[] nextValue = null;
        //    if (this.currentLocationIndex < this.DataCount - 1)
        //    {
        //        nextValue = this.Data.Keys.Select(k => this.Data[k][this.currentLocationIndex + 1].Trim()).ToArray();
        //    }
        //    return nextValue;
        //}

        /// <summary>
        /// Moves to the next line of the input.
        /// </summary>
        /// <returns>false if it's at the end of the file.</returns>
        public bool MoveNext()
        {
            if (this.Data == null)
                return false;

            this.currentLocationIndex++;
            if (this.currentLocationIndex >= this.DataCount - 1)
            {
                return false;
            }

            this.stockPoint = null;
            this.previousIndicators = this.indicators;
            this.indicators = this.Data.Keys.Select(k => this.Data[k][this.currentLocationIndex]).ToArray();

            return true;

            #region oldcode
            //if (this.streamReader == null)
            //{
            //    this.previousIndicators = null;
            //    return false;
            //}

            //if (this.streamReader.EndOfStream)
            //{
            //    this.streamReader.Close();
            //    this.streamReader = null;
            //    return false;
            //}

            //this.indicators = this.streamReader.ReadLine().Split(',');
            //this.isLast = this.streamReader.Peek() == -1;
            #endregion oldcode
        }

        /// <summary>
        /// Checks if the last element has red
        /// </summary>
        /// <returns>bool</returns>
        public bool IsLast()
        {
            return this.currentLocationIndex == this.DataCount - 1;
        }

        /// <summary>
        /// Moves the library adapter to a given date time.
        /// </summary>
        /// <param name="dateTime">The date time to move the library to.</param>
        /// <returns>True if the dateTime is found. False if EOF is reached.</returns>
        public bool MoveToDate(DateTime dateTime)
        {
            while (this.GetStockPoint().PointDateTime < dateTime)
            {
                if (!this.MoveNext())
                {
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// Restarts the position of the adapter at the second line of the stream
        /// </summary>
        public void Restart()
        {
            this.currentLocationIndex = -1;
            MoveNext();
        }

        /// <summary>
        /// Gets an indicator for the current line based on its name.
        /// A name beginning with # will return the rest of the string as a double
        /// </summary>
        /// <param name="indicatorName">Indicator name</param>
        /// <returns>Indicator value</returns>
        public double? GetIndicator(string indicatorName)
        {
            double result;
            if (indicatorName.ElementAt(0) == '#')
            {
                if (double.TryParse(indicatorName.Remove(0, 1), out result))
                {
                    return result;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                int i = this.IndicatorLocations[indicatorName];
                return this.previousIndicators[i] == null ? new double?() : this.previousIndicators[i].Value;
            }
        }


        /// <summary>
        /// Gets a indicator for the previous line based on its name.
        /// A name beginning with # will return the rest of the string as a double
        /// </summary>
        /// <param name="indicatorName">Indicator name</param>
        /// <returns>Indicator value</returns>
        public double? GetPreviousIndicator(string indicatorName)
        {
            double result;
            if (indicatorName.ElementAt(0) == '#')
            {
                if (double.TryParse(indicatorName.Remove(0, 1), out result))
                {
                    return result;
                }
            }
            else
            {
                int i = this.IndicatorLocations[indicatorName];
                return this.previousIndicators[i] == null ? new double?() : this.previousIndicators[i].Value;
                //if (double.TryParse(indicator.Trim(), out result))
                //{
                //    return result;
                //}
            }
            return null;
        }


        /// <summary>
        /// Gets a stock point object for the current line.
        /// </summary>
        /// <returns>A stock point object for the current line.</returns>
        public StockPoint GetStockPoint()
        {
            if (this.stockPoint == null)
            {
                int time = (int)this.Data["Time"][currentLocationIndex].Value;
                this.stockPoint = new StockPoint(this.Data["Date"][currentLocationIndex].Value.ToString(),
                                                 string.Format("{0}:{1}", (time/100).ToString("00"), (time%100).ToString("00")).ToString(),
                                                 this.Data["Open"][currentLocationIndex].Value,
                                                 this.Data["High"][currentLocationIndex].Value,
                                                 this.Data["Low"][currentLocationIndex].Value,
                                                 this.Data["Close"][currentLocationIndex].Value,
                                                 ((long)(int)this.Data["Volume"][currentLocationIndex].Value));
                
                //this.stockPoint.Open = this.Data["Date"][currentLocationIndex];
                //this.stockPoint = new StockPoint()                   
                //    this.indicators[this.IndicatorLocations["Date"]],
                //    this.indicators[this.IndicatorLocations["Time"]],
                //    double.Parse(this.indicators[this.IndicatorLocations["Open"]]),
                //    double.Parse(this.indicators[this.IndicatorLocations["High"]]),
                //    double.Parse(this.indicators[this.IndicatorLocations["Low"]]),
                //    double.Parse(this.indicators[this.IndicatorLocations["Close"]]),
                //    long.Parse(this.indicators[this.IndicatorLocations["Volume"]]));
            }

            return this.stockPoint;
        }

        public IndicatorLibraryAdapter Clone(IndicatorLibraryAdapter indicatorLibraryAdapter)
        {
            using (var ms = new MemoryStream())
            {
                var formatter = new System.Runtime.Serialization.Formatters.Binary.BinaryFormatter();
                formatter.Serialize(ms, indicatorLibraryAdapter);
                ms.Position = 0;

                return (IndicatorLibraryAdapter)formatter.Deserialize(ms);
            }
        }
    }
}
