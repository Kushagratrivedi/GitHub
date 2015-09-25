namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Library;

    /// <summary>
    /// Custom collection for StockPoints
    /// </summary>
    public class StockPoints : System.Collections.CollectionBase, IEnumerable<StockPoint>
    {
        /// <summary>
        /// Initializes a new instance of the StockPoints class.
        /// </summary>
        public StockPoints()
        {
            // Empty default constructor.
        }

        /// <summary>
        /// Initializes a new instance of the StockPoints class with the given StockPoints.
        /// </summary>
        /// <param name="listOfPoints">List of stock points to add to the class.</param>
        public StockPoints(List<StockPoint> listOfPoints)
        {
            this.AddRange(listOfPoints.ToArray());
        }

        /// <summary>
        /// Stock point accessor via []
        /// </summary>
        /// <param name="index">The index of the stock point to return.</param>
        /// <returns>A single stock point.</returns>
        public StockPoint this[int index]
        {
              get { return (StockPoint)this.List[index]; }
              set { this.List[index] = value; }
        }

        /// <summary>
        /// Finds all validation errors for the set of points.
        /// </summary>
        /// <param name="dataGridView">The error data grid view.</param>
        public void FillErrrorConsole(System.Windows.Forms.DataGridView dataGridView)
        {
            // Get all of the single line validation errors.
            int lineNumber = 1;

            foreach (StockPoint stockPoint in this)
            {

                lineNumber++;
            }
        }

        #region CollectionBase and IEnumerable methods

        /// <summary>
        /// Find the index of an item.
        /// </summary>
        /// <param name="item">A stock point</param>
        /// <returns>The index of the given stock point.</returns>
        public int IndexOf(StockPoint item)
        {
            return this.List.IndexOf(item);
        }

        /// <summary>
        /// Add an item to the collection and check for time errors at the same time.
        /// </summary>
        /// <param name="item">A stock point to add to the colleciton.</param>
        /// <returns>The position where the item was inserted.</returns>
        public int Add(StockPoint item)
        {
            if (this.Count > 0)
            {
                int index = this.Count - 1;

                /// ************************************************
                /// GDBCup - Place validation checks here
                /// ************************************************

            }

            return this.List.Add(item);
        }

        /// <summary>
        /// Remove an item from the collection.
        /// </summary>
        /// <param name="item">The stock point to remove from the collection.</param>
        public void Remove(StockPoint item)
        {
            this.InnerList.Remove(item);
        }

        /// <summary>
        /// Copies the elements of the collection to an array starting at a given array index.
        /// </summary>
        /// <param name="array">Array to copy the stock points into.</param>
        /// <param name="index">The index to start inserting the items into the Array.</param>
        public void CopyTo(Array array, int index)
        {
            this.List.CopyTo(array, index);
        }

        /// <summary>
        /// Add all the stock points in StockPoints to the collection.
        /// </summary>
        /// <param name="collection">Stock points to add to the collection.</param>
        public void AddRange(StockPoints collection)
        {
            foreach (StockPoint point in collection)
            {
                this.List.Add(point);
            }
        }

        /// <summary>
        /// Add an array of mfvvalues to the collection
        /// </summary>
        /// <param name="collection">An array of stockPoints to be added to the collection.</param>
        public void AddRange(StockPoint[] collection)
        {
            this.AddRange(collection);
        }

        /// <summary>
        /// Determines if the collection contains the stock point.
        /// </summary>
        /// <param name="item">Stock point to find in the collection.</param>
        /// <returns>True if the item is in the collection.</returns>
        public bool Contains(StockPoint item)
        {
            return this.List.Contains(item);
        }

        /// <summary>
        /// Inserts a stock point into the collection at the given index.
        /// </summary>
        /// <param name="index">The index o insert the stock point.</param>
        /// <param name="item">The stock point to insert into the colleciton.</param>
        public void Insert(int index, StockPoint item)
        {
            this.List.Insert(index, item);
        }

        /// <summary>
        /// Gets the enumerator.
        /// </summary>
        /// <returns>A stock point enumerator.</returns>
        public new IEnumerator<StockPoint> GetEnumerator()
        {
            return new StockPointsEnumerator(this);
        }

        /// <summary>
        /// Custom enumerator for the StockPoint class.
        /// </summary>
        private class StockPointsEnumerator : IEnumerator<StockPoint>
        {
            /// <summary>
            /// stock points collection
            /// </summary>
            private StockPoints stockPoints;

            /// <summary>
            /// position of the current item.
            /// </summary>
            private int position = -1;

            /// <summary>
            /// Initializes a new instance of the StockPointsEnumerator class for the given StockPoints.
            /// </summary>
            /// <param name="stockPoints">The stock points collection to enumerate.</param>
            public StockPointsEnumerator(StockPoints stockPoints)
            {
                this.stockPoints = stockPoints;
            }

            /// <summary>
            /// Gets the stock point at the current position.
            /// </summary>
            public StockPoint Current
            {
                get
                {
                    try
                    {
                        return this.stockPoints[this.position];
                    }
                    catch (IndexOutOfRangeException)
                    {
                        throw new InvalidOperationException();
                    }
                }
            }

            /// <summary>
            /// Gets the object at the current position.
            /// </summary>
            object System.Collections.IEnumerator.Current
            {
                get
                {
                    return this.Current;
                }
            }

            /// <summary>
            /// Moves to the next item in the collection.
            /// </summary>
            /// <returns>True if there is an item at that location.</returns>
            public bool MoveNext()
            {
                this.position++;
                return this.position < this.stockPoints.Count;
            }

            /// <summary>
            /// Resets the position to before the first item.
            /// </summary>
            public void Reset()
            {
                this.position = -1;
            }

            /// <summary>
            /// Disposes of the enumerator.
            /// </summary>
            public void Dispose()
            {
                GC.SuppressFinalize(this);
            }
        }
        #endregion
    }
}
