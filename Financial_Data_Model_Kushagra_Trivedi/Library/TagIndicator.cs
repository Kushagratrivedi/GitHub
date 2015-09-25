using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject.Library
{
    public class ControlTag
    {
        public string FromControlID { get; set; }
        public string ToControlId { get; set; }
        public string IncrementControlId { get; set; }
    }

    public class TagIndicator
    {
        public List<ControlTag> Tags { get; set; }
        public IndicatorValue Indicator { get; set; }
    }
}
