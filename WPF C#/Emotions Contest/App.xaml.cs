using Emotions_Contest.Classes;
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
        static DispatcherTimer CLOCK_TIME;
        const int DEFAULT_TIMER_TICK = 60;
        private static int postponesTime = -1;
        

        protected override void OnStartup(StartupEventArgs e)
        {
            CSV_Manager.init();

            CLOCK_TIME = new DispatcherTimer();
            CLOCK_TIME.Interval = TimeSpan.FromMinutes(DEFAULT_TIMER_TICK);
            CLOCK_TIME.Tick += CLOCK_TIME_Tick;
            CLOCK_TIME.Start();

            invokePopup();
        }
        
        private void CLOCK_TIME_Tick(object sender, EventArgs e)
        {
            invokePopup();
        }

        private void invokePopup()
        {
            if (SingletonClasses.getMainForm() == null && SingletonClasses.getPopupRequester() == null)
            {
                PopupRequester popupRequest = new PopupRequester(exitApp, postpones);
                popupRequest.Show();
                setPositionWindow(popupRequest);
                popupRequest.WindowState = WindowState.Normal;
            }

            if (CLOCK_TIME.Interval.TotalMinutes != DEFAULT_TIMER_TICK)
            {
                CLOCK_TIME.Stop();
                CLOCK_TIME.Interval = TimeSpan.FromMinutes(DEFAULT_TIMER_TICK);
                CLOCK_TIME.Start();
            }
        }

        private void setPositionWindow(PopupRequester window)
        {
            const int margin = 0; // 10;
            window.Left = System.Windows.SystemParameters.WorkArea.Right - window.Width - margin;
            window.Top = System.Windows.SystemParameters.WorkArea.Bottom - window.Height - margin;
        }

        private void exitApp()
        {
            Current.Shutdown();
        }

        public static void restartTimerPopupRequestor()
        {
                CLOCK_TIME.Stop();
                CLOCK_TIME.Interval = TimeSpan.FromMinutes(DEFAULT_TIMER_TICK);
                CLOCK_TIME.Start();
        }

        private void postpones()
        {
            if (postponesTime != -1)
            {
                CLOCK_TIME.Stop();
                CLOCK_TIME.Interval = TimeSpan.FromMinutes(postponesTime);
                CLOCK_TIME.Start();

                postponesTime = -1;
            }
        }

        public static void setPostponesTime(int minutes)
        {
            postponesTime = minutes;
        }
    }
}
