using FDMTermProject.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject
{
    public enum GeneticDataType
    {
        Buy = 1,
        Sell,
        Both
    }

    public class GeneticData
    {
        public TradeRule TradeRule { get; set; }
        public List<double> Values { get; set; }
        public GeneticDataType Type { get; set; }
        public List<int> PotentialIndexes { get; set; }
        public GeneticData(GeneticDataType type = GeneticDataType.Both)
        {
            this.Type = type;
        }
    }
}
