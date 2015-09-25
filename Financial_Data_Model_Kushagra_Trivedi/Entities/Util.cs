using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;

namespace FDMTermProject.Entities
{
    public static class Util
    {
        /// <summary>
        /// Parses the string date and time supplied and returns in DateTime format
        /// </summary>
        /// <param name="strDate">date in string formt</param>
        /// <param name="strTimetime">time in string format</param>
        /// <returns>Formatted Datetime</returns>
        public static DateTime GetDateTimeValue(string strDate, string strTimetime)
        {
            return DateTime.Parse(string.Format("{0}/{1}/{2} {3}:00",
                String.Join("", strDate.Take(4)),
                String.Join("", strDate.Skip(4).Take(2)),
                String.Join("", strDate.Skip(6).Take(2)),
                strTimetime.Replace(" ", "")));
        }
        
        /// <summary>
        /// Returns Datatable for the csv file
        /// </summary>
        /// <param name="strFilePath">csv file path</param>
        /// <returns>Data Table of CSV file</returns>
        public static DataTable ConvertCSVtoDataTable(string strFilePath)
        {
            using (StreamReader sr = new StreamReader(strFilePath))
            {
                string[] headers = sr.ReadLine().Split(',');
                DataTable dt = new DataTable();
                foreach (string header in headers)
                {
                    dt.Columns.Add(header.Trim());
                }
                while (!sr.EndOfStream)
                {
                    string[] rows = sr.ReadLine().Split(',');
                    DataRow dr = dt.NewRow();
                    for (int i = 0; i < headers.Length; i++)
                    {
                        dr[i] = rows[i];
                    }
                    dt.Rows.Add(dr);
                }
                return dt;
            }
        }

        public static bool CheckDateIsInBetween(DateTime DateTimeToCheck, DateTime StartDate, DateTime EndDate)
        {
            return DateTimeToCheck >= StartDate && DateTimeToCheck <= EndDate;
        }
    }
}
