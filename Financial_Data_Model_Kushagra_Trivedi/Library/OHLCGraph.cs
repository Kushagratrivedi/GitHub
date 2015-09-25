namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Drawing;
    using System.Linq;
    using System.Text;
    using FDMTermProject.Entities;
    using ZedGraph;

    /// <summary>
    /// This class displays the OHLC graph and the technical indicators.
    /// </summary>
    public class Chart
    {
        /// <summary>
        /// Initializes a new instance of the Chart class.
        /// </summary>
        public Chart()
        {
            // default cons
        }

        /// <summary>
        /// Draws the Open High Low Close graph.
        /// </summary>
        /// <param name="data">The stock points to plot.</param>
        /// <param name="control">The control to plot on.</param>
        public void DrawOHLCGraph(ref StockPoints data, ref ZedGraph.ZedGraphControl control)
        {
            StockPointList pointList = new StockPointList();
            XDate date = new XDate();
            foreach (StockPoint s in data)
            {
                date = new XDate(s.PointDateTime.Year, s.PointDateTime.Month, s.PointDateTime.Day, s.PointDateTime.Hour, s.PointDateTime.Minute, 0);
                StockPt p = new StockPt(date.XLDate, s.High, s.Low, s.Open, s.Close, s.Volume);
                pointList.Add(p);
            }

            Color[] colors = { Color.Red, Color.Black };
            Fill myFill = new Fill(colors);
            myFill.Type = FillType.GradientByColorValue;
            myFill.SecondaryValueGradientColor = Color.Empty;
            myFill.RangeMin = 1;
            myFill.RangeMax = 2;

            MasterPane masterPane = control.MasterPane;
            masterPane.Margin.All = 10;
            masterPane.InnerPaneGap = 5;

            GraphPane ohlcPane = new GraphPane(new Rectangle(10, 10, 10, 10), "OHLC", "Time", "Price");

            ohlcPane.IsBoundedRanges = true;

            OHLCBarItem bar = ohlcPane.AddOHLCBar("Price", pointList, Color.Empty);
            bar.Bar.GradientFill = myFill;
            bar.Bar.IsAutoSize = true;
            bar.Bar.Size = 5;

            ohlcPane.Title.Text = "OHLC Graph";
            ohlcPane.XAxis.Type = AxisType.DateAsOrdinal;
            ohlcPane.XAxis.Title.Text = "Date";
            ohlcPane.YAxis.Title.Text = "Price";
            ohlcPane.Margin.All = 0;
            ohlcPane.Margin.Top = 10;
            ohlcPane.YAxis.MinSpace = 10;
            ohlcPane.Y2Axis.MinSpace = 10;
            ohlcPane.AxisChange();
            
            masterPane.Add(ohlcPane);
            
            control.IsShowHScrollBar = true;
            control.ScrollMinX = 0;
            control.ScrollMaxX = data.Count;
            control.GraphPane.XAxis.Scale.Max = data.Count;
            if (data.Count >= 99)
            {
                control.GraphPane.XAxis.Scale.Min = data.Count - 99;
            }
            else
            {
                control.GraphPane.XAxis.Scale.Min = 0;
            }

            using (Graphics g = control.CreateGraphics())
            {
                masterPane.SetLayout(g, PaneLayout.SingleColumn);
                masterPane.AxisChange(g);

                // Synchronize the Axes
                //// g.Dispose();
            }
        }

        /// <summary>
        /// Clear the graph control.
        /// </summary>
        /// <param name="control">The graph control to clear.</param>
        public void ClearCurves(ZedGraphControl control)
        {
            MasterPane masterPane = control.MasterPane;
            masterPane.PaneList.Clear();
        }

        /// <summary>
        /// Add an overlay without an offset.
        /// </summary>
        /// <param name="data">The points to plot.</param>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="control">The control to plot on.</param>
        /// <param name="color">The color for this key.</param>
        public void AddOverlay(Dictionary<DateTime, Dictionary<string, double>> data, string key, ZedGraphControl control, Color color)
        {
            this.AddOverlay(data, key, control, color, 0);
        }

        /// <summary>
        /// Add an overlayto the graph.
        /// </summary>
        /// <param name="data">The points to plot.</param>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="control">The control to plot on.</param>
        /// <param name="color">The color for this key.</param>
        /// <param name="offset">The starting point for this plot.</param>
        public void AddOverlay(Dictionary<DateTime, Dictionary<string, double>> data, string key, ZedGraphControl control, Color color, int offset)
        {
            Random r = new Random();
            PointPairList list = new PointPairList();
            int i = offset + 1;
            GraphPane ohlcPane = control.MasterPane.PaneList[0];
            foreach (DateTime s in data.Keys.OrderBy(k => k))
            {
                try
                {
                    double value = data[s][key];
                    XDate date = new XDate(s.Year, s.Month, s.Day, s.Hour, s.Minute, 0);
                    list.Add(i, value);
                    i++;
                }
                catch 
                {
                }
            }

            LineItem line = ohlcPane.AddCurve(key, list, color, SymbolType.None);
            line.IsOverrideOrdinal = true;
        }

        /// <summary>
        /// Add a graph with a solid line and no offset.
        /// </summary>
        /// <param name="data">The points to plot.</param>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="control">The control to plot on.</param>
        /// <param name="color">The color for this key.</param>
        public void AddGraph(Dictionary<DateTime, Dictionary<string, double>> data, string key, ZedGraphControl control, Color color)
        {
            this.AddGraph(data, key, control, color, 0, System.Drawing.Drawing2D.DashStyle.Solid);
        }

        /// <summary>
        /// Add a graph with a solid line.
        /// </summary>
        /// <param name="data">The points to plot.</param>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="control">The control to plot on.</param>
        /// <param name="color">The color for this key.</param>
        /// <param name="offset">The starting point for this plot.</param>
        public void AddGraph(Dictionary<DateTime, Dictionary<string, double>> data, string key, ZedGraphControl control, Color color, int offset)
        {
            this.AddGraph(data, key, control, color, offset, System.Drawing.Drawing2D.DashStyle.Solid);
        }

        /// <summary>
        /// Add a new graph to the control.
        /// </summary>
        /// <param name="data">The points to plot.</param>
        /// <param name="key">Name of the set to put in the graph key</param>
        /// <param name="control">The control to plot on.</param>
        /// <param name="color">The color for this key.</param>
        /// <param name="offset">The starting point for this plot.</param>
        /// <param name="lineStyle">The style of line to plot.</param>
        public void AddGraph(
            Dictionary<DateTime, Dictionary<string, double>> data, 
            string key, 
            ZedGraphControl control, 
            Color color, 
            int offset, 
            System.Drawing.Drawing2D.DashStyle lineStyle)
        {
            GraphPane pane;
            int num_panels = control.MasterPane.PaneList.Count;
            if (num_panels == 2)
            {
                pane = control.MasterPane.PaneList[1];
            }
            else
            {
                pane = new GraphPane();
                control.MasterPane.PaneList.Add(pane);
            }

            pane.XAxis.Scale.Max = data.Count;
            if (data.Count >= 99)
            {
                pane.XAxis.Scale.Min = data.Count - 99;
            }
            else
            {
                pane.XAxis.Scale.Min = 0;
            }

            PointPairList list = ExtractPointPair(data, key, offset);

            LineItem line = pane.AddCurve(key, list, color, SymbolType.None);
            line.Line.Style = lineStyle;
            line.IsOverrideOrdinal = true;

            pane.IsBoundedRanges = true;
            pane.Title.Text = "OHLC Graph";
            pane.XAxis.Type = AxisType.Ordinal;
            pane.XAxis.Title.Text = "Date";
            pane.YAxis.Title.Text = "KEY";
            pane.Margin.All = 0;
            pane.Margin.Bottom = 10;
            pane.YAxis.MinSpace = 80;
            pane.Y2Axis.MinSpace = 20;
            pane.AxisChange();
            control.MasterPane.AxisChange();
            using (Graphics g = control.CreateGraphics())
            {
                control.MasterPane.SetLayout(g, PaneLayout.SingleColumn);
                control.MasterPane.AxisChange(g);

                // Synchronize the Axes
                ////control.IsAutoScrollRange = true;
                control.IsShowHScrollBar = true;
                ////control.IsShowVScrollBar = true;
                control.IsSynchronizeXAxes = true;
                ////g.Dispose();
            }
        }

        /// <summary>
        /// Sets the y range of the 2nd graph pane.
        /// </summary>
        /// <param name="control">The control to update.</param>
        /// <param name="yaxisMin">The minimum for the y axis.</param>
        /// <param name="yaxisMax">The maximum for the y axis.</param>
        public void SetSecondPaneYRange(ZedGraphControl control, int yaxisMin, int yaxisMax)
        {
            GraphPane pane;
            int num_panels = control.MasterPane.PaneList.Count;
            if (num_panels == 2)
            {
                pane = control.MasterPane.PaneList[1];
            }
            else
            {
                pane = new GraphPane();
                control.MasterPane.PaneList.Add(pane);
            }

            pane.YAxis.Scale.Max = yaxisMax;
            pane.YAxis.Scale.Min = yaxisMin;
        }

        /// <summary>
        /// Gets the point pair for a given key/offset from the data.
        /// </summary>
        /// <param name="data">Data to extract the point pair from</param>
        /// <param name="key">The name of the dataset.</param>
        /// <param name="offset">Where to start getting the data.</param>
        /// <returns>The point pair for the given Data, key, and offset.</returns>
        private static PointPairList ExtractPointPair(Dictionary<DateTime, Dictionary<string, double>> data, string key, int offset)
        {
            PointPairList list = new PointPairList();
            int i = offset + 1;
            foreach (DateTime s in data.Keys.OrderBy(k => k))
            {
                try
                {
                    double value = data[s][key];
                    ////XDate date = new XDate(s.Year, s.Month, s.Day, s.Hour, s.Minute, 0);
                    list.Add(i, value);
                    i++;
                    ////list.Add(date.XLDate, value);
                }
                catch
                {
                }
            }

            return list;
        }
    }
}
