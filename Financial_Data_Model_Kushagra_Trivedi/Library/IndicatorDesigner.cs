using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace FDMTermProject.Library
{
    public class IndicatorDesigner
    {
        public IEnumerable<string> Headers { get; private set; }
        public IEnumerable<IEnumerable<String>> Contents { get; private set; }

        public IndicatorDesigner(IEnumerable<string> headers, IEnumerable<IEnumerable<String>> contents)
        {
            this.Headers = headers;
            this.Contents = contents;
        }

        public DataTable GetDataTable(string tableName = null)
        {
            DataTable dt = new DataTable(tableName == null ? "IndicatorDesignerTable" : tableName);
            if (this.Headers != null)
            {
                foreach (string header in this.Headers)
                {
                    dt.Columns.Add(header);
                }
            }

            if (this.Contents != null)
            {
                foreach (IEnumerable<string> content in this.Contents)
                {
                    dt.Rows.Add(content.ToArray());
                }
            }

            return dt;
        }

    }
}
