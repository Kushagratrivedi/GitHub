using FDMTermProject.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject
{
    public class TradeChromosome
    {
        public List<int> BuyIndexes { get; set; }
        public List<int> SellIndexes { get; set; }
        public List<int> BuyJoinIndexes { get; set; }
        public List<int> SellJoinIndexes { get; set; }

        public TradeChromosome()
        {
            this.BuyIndexes = new List<int>();
            this.SellIndexes = new List<int>();
            this.BuyJoinIndexes = new List<int>();
            this.SellJoinIndexes = new List<int>();
        }
    }


}
