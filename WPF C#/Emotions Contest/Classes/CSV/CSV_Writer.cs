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

            //using (var sw = new StreamWriter(@CSV_Manager.getPATH_CSV(), false, System.Text.Encoding.UTF8))
            //{
            //    //var csv = new StringBuilder();
            //    //var csv = new CsvWriter(writer);
            //    //csv.Configuration.Encoding = Encoding.UTF8;

            //    //csv.WriteRecords(values);
            //    //csv.WriteRecords(items.AsEnumerable());

            //    //string rowCSV = items[0];
            //    //for (int i = 1; i < items.Count; i++)
            //    //{
            //    //    rowCSV += "," + items[i];
            //    //}
            //    //csv.AppendLine(rowCSV);

            //    //IEnumerable records = items.ToList();

            //    var csv = new CsvWriter(sw);

            //    foreach (string value in items)
            //    {
            //        //csv.WriteField(value);
            //        csv.WriteConvertedField(value);
            //    }
            //    //writer.Close();
            //}

            ////CSV_Manager.checkDate();
            ////File.AppendAllText(CSV_Manager.getPATH_CSV(), csv.ToString());





            using (var sw = new StreamWriter(@CSV_Manager.getPATH_CSV()))
            {
                //var reader = new CsvReader(sr);
                var writer = new CsvWriter(sw);

                //CSVReader will now read the whole file into an enumerable
                IEnumerable<string> records = items;

                //Write the entire contents of the CSV file into another
                writer.WriteRecords(records);

                //Now we will write the data into the same output file but will do it 
                //Using two methods.  The first is writing the entire record.  The second
                //method writes individual fields.  Note you must call NextRecord method after 
                //using Writefield to terminate the record.

                //Note that WriteRecords will write a header record for you automatically.  If you 
                //are not using the WriteRecords method and you want to a header, you must call the 
                //Writeheader method like the following:
                //
                //writer.WriteHeader<DataRecord>();
                //
                //Do not use WriteHeader as WriteRecords will have done that already.

                writer.NextRecord();
                foreach (string record in records)
                {
                    writer.WriteField(record);
                    //ensure you write end of record when you are using WriteField method
                }
                writer.NextRecord();
            }
        }
    }
}
