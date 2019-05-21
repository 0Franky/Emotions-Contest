using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Threading;

namespace Emotions_Contest
{
    static class CSV_Manager
    {
        private static string CSV_file_name = "";
        private static string PATH_CSV = "";
        private static DateTime lastDate;

        static DispatcherTimer CLOCK_TIME;
        const int DEFAULT_TIMER_TICK = 60;



        public static void init()
        {
            lastDate = DateTime.Now;
            createCSV_file_name(DateTime.Now);

            CLOCK_TIME = new DispatcherTimer();
            CLOCK_TIME.Interval = TimeSpan.FromMinutes(DEFAULT_TIMER_TICK);
            CLOCK_TIME.Tick += CLOCK_TIME_Tick;
            CLOCK_TIME.Start();
        }

        private static void CLOCK_TIME_Tick(object sender, EventArgs e)
        {
            checkDate();
        }


        public static string getActualCSV_Name()
        {
            return CSV_file_name;
        }
        public static string getPATH_CSV()
        {
            return PATH_CSV;
        }

        private static void checkDate()
        {
            if (DateTime.Now.Year != lastDate.Year || DateTime.Now.Month != lastDate.Month || DateTime.Now.Day != lastDate.Day)
            {
                createCSV_file_name(DateTime.Now);
            }

            lastDate = DateTime.Now;
        }


        private static void createCSV_file_name(DateTime date)
        {
            CSV_file_name = lastDate.Year + "-" + DateTime.Now.Month + "-" + DateTime.Now.Day+ ".csv";

            PATH_CSV = Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location) + "\\" + getActualCSV_Name();

            if (!File.Exists(PATH_CSV))
            {
                File.AppendAllText(PATH_CSV, "");
            }
        }
    }
}
