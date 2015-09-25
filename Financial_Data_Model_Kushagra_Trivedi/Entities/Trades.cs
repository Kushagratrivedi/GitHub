namespace FDMTermProject.Entities
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// A custom collection for trades during a backtest.
    /// </summary> 
    public class Trades : System.Collections.CollectionBase, IEnumerable<Trade>
    {
        /// <summary>
        /// Initializes a new instance of the Trades class.
        /// </summary>
        public Trades()
        {
        }

        /// <summary>
        /// Gets the table of trades.
        /// </summary>
        public DataTable TradeTable
        {
            get
            {
                DataTable tradeTable = new DataTable("TradeTable");
                tradeTable.Columns.Add(new DataColumn("Symbol", typeof(string)));
                tradeTable.Columns.Add(new DataColumn("Quantity", typeof(int)));
                tradeTable.Columns.Add(new DataColumn("Entry Date", typeof(string)));
                tradeTable.Columns.Add(new DataColumn("Entry Time", typeof(string)));
                tradeTable.Columns.Add(new DataColumn("Entry Price", typeof(double)));
                tradeTable.Columns.Add(new DataColumn("Exit Date", typeof(string)));
                tradeTable.Columns.Add(new DataColumn("Exit Time", typeof(string)));
                tradeTable.Columns.Add(new DataColumn("Exit Price", typeof(double)));
                tradeTable.Columns.Add(new DataColumn("Profit", typeof(double)));
                tradeTable.Columns.Add(new DataColumn("Type", typeof(string)));

                string lastState = string.Empty;
                DataRow r = null;
                foreach (Trade trade in this)
                {
                    r = tradeTable.NewRow();
                    r["Symbol"] = "ES";
                    r["Quantity"] = trade.Quantity;
                    r["Entry Date"] = trade.EntryDate;
                    r["Entry Time"] = trade.EntryTime;
                    r["Entry Price"] = trade.EntryPrice;
                    r["Exit Date"] = trade.ExitDate;
                    r["Exit Time"] = trade.ExitTime;
                    r["Exit Price"] = trade.ExitPrice;
                    r["Profit"] = Math.Round(trade.Profit, 2);
                    r["Type"] = trade.TradeType == TradeType.longTrade ? "Long" : "Short";
                    /// *********************************************
                    /// GDBCup - Need to code the rest of this
                    /// *********************************************
                    tradeTable.Rows.Add(r);
                }

                return tradeTable;
            }
        } 

        /// <summary>
        /// Trade accessor via []
        /// </summary>
        /// <param name="index">The index of the trade to return.</param>
        /// <returns>A single trade.</returns>
        public Trade this[int index]
        {
            get { return (Trade)this.List[index]; }
            set { this.List[index] = value; }
        }

        /// <summary>
        /// Find the index of an item.
        /// </summary>
        /// <param name="item">A trade to find the index of.</param>
        /// <returns>The index of the given trade.</returns>
        public int IndexOf(Trade item)
        {
            return this.List.IndexOf(item);
        }

        /// <summary>
        /// Add an item to the collection and check for time errors at the same time.
        /// </summary>
        /// <param name="item">A trade to add to the colleciton.</param>
        /// <returns>The position where the item was inserted.</returns>
        public int Add(Trade item)
        {
            if (item.Status != TradeStatus.complete)
            {
                throw new Exception("Complete the trade before adding it to the collection.");
            }

            return this.List.Add(item);
        }

        /// <summary>
        /// Remove an item from the collection.
        /// </summary>
        /// <param name="item">The trade to remove from the collection.</param>
        public void Remove(Trade item)
        {
            this.InnerList.Remove(item);
        }

        /// <summary>
        /// Copies the elements of the collection to an array starting at a given array index.
        /// </summary>
        /// <param name="array">Array to copy the trades into.</param>
        /// <param name="index">The index to start inserting the items into the Array.</param>
        public void CopyTo(Array array, int index)
        {
            this.List.CopyTo(array, index);
        }

        /// <summary>
        /// Add all the trades in Trades to the collection.
        /// </summary>
        /// <param name="collection">Trades to add to the collection.</param>
        public void AddRange(Trades collection)
        {
            foreach (Trade trade in collection)
            {
                this.List.Add(trade);
            }
        }

        /// <summary>
        /// Add an array of mfvvalues to the collection
        /// </summary>
        /// <param name="collection">An array of trades to be added to the collection.</param>
        public void AddRange(Trade[] collection)
        {
            this.AddRange(collection);
        }

        /// <summary>
        /// Determines if the collection contains the trade.
        /// </summary>
        /// <param name="item">Trade to find in the collection.</param>
        /// <returns>True if the item is in the collection.</returns>
        public bool Contains(Trade item)
        {
            return this.List.Contains(item);
        }

        /// <summary>
        /// Inserts a trade into the collection at the given index.
        /// </summary>
        /// <param name="index">The index o insert the trade.</param>
        /// <param name="item">The trade to insert into the colleciton.</param>
        public void Insert(int index, Trade item)
        {
            this.List.Insert(index, item);
        }

        /// <summary>
        /// Gets the enumerator.
        /// </summary>
        /// <returns>A trade enumerator.</returns>
        public new IEnumerator<Trade> GetEnumerator()
        {
            return new TradesEnumerator(this);
        }

        /// <summary>
        /// Custom enumerator for the Trade class.
        /// </summary>
        private class TradesEnumerator : IEnumerator<Trade>
        {
            /// <summary>
            /// trades collection
            /// </summary>
            private Trades trades;

            /// <summary>
            /// position of the current item.
            /// </summary>
            private int position = -1;

            /// <summary>
            /// Initializes a new instance of the TradesEnumerator class for the given Trades.
            /// </summary>
            /// <param name="trades">The trades collection to enumerate.</param>
            public TradesEnumerator(Trades trades)
            {
                this.trades = trades;
            }

            /// <summary>
            /// Gets the trade at the current position.
            /// </summary>
            public Trade Current
            {
                get
                {
                    try
                    {
                        return this.trades[this.position];
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
                return this.position < this.trades.Count;
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
    }
}
