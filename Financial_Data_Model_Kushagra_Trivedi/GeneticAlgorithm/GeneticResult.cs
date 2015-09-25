using FDMTermProject.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FDMTermProject
{
    public class GeneticResult
    {
        public int Iteration { get; set; }
        public int Generation { get; set; }
        public double FitnessValue { get; private set; }
        public int Losers { get; private set; }
        public int Winners { get; private set; }
        public int TotalTrades { get; private set; }
        public int StopOut { get; private set; }
        public TradeSystem TradeSystem { get; private set; }
        public TradeCondition BuyCondition { get; private set; }
        public TradeCondition SellCondition { get; private set; }
        public GeneticResult(TradeSystem tradeSystem, double fitnessvalue, int winners, int loosers, int totaltrades, int stopout, int iteration, int generation)
        {
            this.FitnessValue = fitnessvalue;
            this.Winners = winners;
            this.Losers = loosers;
            this.TotalTrades = totaltrades;
            this.Iteration = iteration;
            this.Generation = generation;
            this.TradeSystem = tradeSystem;
            this.BuyCondition = tradeSystem.BuyCondition;
            this.SellCondition = tradeSystem.SellCondition;
            this.StopOut = stopout;
        }
    }
}
