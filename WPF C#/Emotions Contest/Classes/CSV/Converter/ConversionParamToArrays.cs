using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emotions_Contest
{
    class ConversionParamToArrays
    {
        public static List<string> convert(string activity, int pleasantness, int excitement, string notes = "")
        {
            List<string> listParam = new List<string>();

            listParam.Add(activity);
            listParam.Add(pleasantness.ToString());
            listParam.Add(excitement.ToString());
            listParam.Add(notes);

            return listParam;
        }


        public static List<string> convert(string activity, string pleasantness, string excitement, string notes = "")
        {
            List<string> listParam = new List<string>();

            listParam.Add(activity);
            listParam.Add(pleasantness);
            listParam.Add(excitement);
            listParam.Add(notes);

            return listParam;
        }
    }
}
