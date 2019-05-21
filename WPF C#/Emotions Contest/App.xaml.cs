using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Threading;

namespace Emotions_Contest
{
    /// <summary>
    /// Logica di interazione per App.xaml
    /// </summary>
    public partial class App : Application
    {
        DispatcherTimer CLOCK_TIME;
        const int DEFAULT_TIMER_TICK = 60;

        protected override void OnStartup(StartupEventArgs e)
        {
            CSV_Manager.init();

            CLOCK_TIME = new DispatcherTimer();
            CLOCK_TIME.Interval = TimeSpan.FromMinutes(DEFAULT_TIMER_TICK);
            CLOCK_TIME.Tick += CLOCK_TIME_Tick;
            CLOCK_TIME.Start();
        }
        
        private void CLOCK_TIME_Tick(object sender, EventArgs e)
        {
            MessageBox.Show("POPUP!");
        }
    }
}
