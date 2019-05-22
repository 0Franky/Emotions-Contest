using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emotions_Contest
{
    class CSV_Writer
    {
        public static void write(List<String> items)
        {
            var csv = new StringBuilder();

            string rowCSV = items[0];
            for (int i = 1; i < items.Count; i++)
            {
                rowCSV += "," + items[i];
            }
            csv.AppendLine(rowCSV);
            
            File.AppendAllText(CSV_Manager.getPATH_CSV(), csv.ToString());
        }
    }
}
