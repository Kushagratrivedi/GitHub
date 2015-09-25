namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Windows.Forms;
    using FDMTermProject.Entities;

    /// <summary>
    /// Data manipulation class
    /// This class loads validates the data, then loads the OHLC mfvvalues on the grid.
    /// </summary>
    public static class DataHelper
    {
        /// <summary>
        /// Load the List with StockPoint objects
        /// </summary>
        /// <param name="stockPoints">Stock points to fill.</param>
        /// <param name="st">Stream with CSV data.</param>
        public static void LoadList(ref StockPoints stockPoints, Stream st)
        {
            StreamReader sr = new StreamReader(st);

            string line;
            int lineNumber = 1;
            double open;
            double high;
            double low;
            double close;
            long volume;

            while (!sr.EndOfStream)
            {
                line = sr.ReadLine();
                string[] terms = line.Split(',');


                for (int i = 0; i < 7; i++)
                {
                    if (terms[i] == String.Empty)
                    {
                        terms[i] = "0";
                    }
                }

                open = (double)Double.Parse(terms[0]);
                high = (double)Double.Parse(terms[1]);
                low = (double)Double.Parse(terms[2]);
                close = (double)Double.Parse(terms[3]);
                volume = Int64.Parse(terms[4]);
                StockPoint stockPoint = new StockPoint(terms[6], terms[5], open, high, low, close, volume);
                stockPoints.Add(stockPoint);
                lineNumber++;
            }
        }

        /// <summary>
        /// Populates the datagrid with a given StockPoints object.
        /// </summary>
        /// <param name="stockPoints">Data to put in the grid.</param>
        /// <param name="dgv">Grid to fill with data.</param>
        public static void PopulateGrid(ref StockPoints stockPoints, DataGridView dgv)
        {            
            string[] terms = new string[8];
            int i = 1;
            foreach (StockPoint s in stockPoints)
            {
                terms[0] = string.Empty + i;
                terms[1] = string.Empty + s.Open;
                terms[2] = string.Empty + s.High;
                terms[3] = string.Empty + s.Low;
                terms[4] = string.Empty + s.Close;
                terms[5] = string.Empty + s.Volume;
                terms[6] = s.Time;
                terms[7] = s.Date;


                dgv.Rows.Add(terms);
                i++;
            }
        }
    }
}
