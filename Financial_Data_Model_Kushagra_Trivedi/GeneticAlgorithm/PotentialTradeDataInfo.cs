using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FDMTermProject
{
    public class PotentialTradeDataInfo
    {
        public List<double> Values { get; set; }
        public List<int> PotentialIndexes { get; set; }
        public PotentialTradeDataInfo()
        {
            Values = new List<double>();
            PotentialIndexes = new List<int>();
        }
    }
}
