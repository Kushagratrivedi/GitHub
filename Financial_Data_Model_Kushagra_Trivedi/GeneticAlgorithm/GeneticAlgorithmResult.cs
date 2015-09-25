using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FDMTermProject
{
    public class GeneticAlgorithmResult
    {
        public List<GeneticResult> GeneticResults { get; set; }
        public string Status { get; set; }
        public bool Cancelled { get; set; }
        public int Iteration { get; set; }
        public int Generation { get; set; }
        public GeneticResult BestResult { get; set; }

        public GeneticAlgorithmResult(List<GeneticResult> geneticresults)
        {
            this.GeneticResults = geneticresults;
        }
    }
}
