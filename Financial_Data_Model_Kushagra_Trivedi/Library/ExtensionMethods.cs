namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Windows.Forms;

    /// <summary>
    /// A static class to put any extension methods needed.
    /// </summary>
    public static class ExtensionMethods
    {
        /// <summary>
        /// Gets a cell from the row based on its column name.
        /// </summary>
        /// <param name="row">The row to pull the cell from.</param>
        /// <param name="name">The name of the column of the cell wanted.</param>
        /// <returns>The cell in the column given.</returns>
        public static DataGridViewCell GetCellByColumnName(this DataGridViewRow row, string name)
        {
            foreach (DataGridViewCell cell in row.Cells)
            {
                if (cell.OwningColumn.Name == name)
                {
                    return cell;
                }
            }

            return null;
        }
    }
}
