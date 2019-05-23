using CsvHelper;
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
            CSV_Manager.checkDate();
            using (var sw = new StreamWriter(@CSV_Manager.getPATH_CSV(), true))
            {
                var writer = new CsvWriter(sw);
                foreach (string record in items)
                {
                    writer.WriteField(record);
                }
                writer.NextRecord();
            }
        }
    }
}
