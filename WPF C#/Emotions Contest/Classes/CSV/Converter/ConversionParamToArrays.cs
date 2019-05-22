﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emotions_Contest
{
    class ConversionParamToArrays
    {
        //public static List<string> convert(string activity, int pleasantness, int excitement, string notes = "")
        //{
        //    List<string> listParam = new List<string>();

        //    listParam.Add(activity);
        //    listParam.Add(pleasantness.ToString());
        //    listParam.Add(excitement.ToString());
        //    listParam.Add(notes);

        //    return listParam;
        //}


        public static List<string> convert(DateTime startDate, string activity, string pleasantness, string excitement, string notes, DateTime endDate)
        {
            List<string> listParam = new List<string>();

            listParam.Add(startDate.ToString("yyyy/MM/dd HH:mm:ss"));
            listParam.Add(activity);
            listParam.Add(pleasantness);
            listParam.Add(excitement);
            listParam.Add(notes);
            listParam.Add(endDate.ToString("yyyy/MM/dd HH:mm:ss"));

            return listParam;
        }
    }
}
