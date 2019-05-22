using Emotions_Contest.Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Emotions_Contest
{
    /// <summary>
    /// Logica di interazione per Window1.xaml
    /// </summary>
    public partial class PopupRequester : Window
    {
        private Action exitApp = null;
        private Action postponesFunc = null;

        private enum type_Postpon {
            _10_min = 10,
            _30_min = 30,
            _1_h = 60,
            _3_h = 180,
            _5_h = 300,
            _1_y = 302400
        };

        public PopupRequester(Action _exitApp, Action _postponesFunc)
        {
            exitApp = _exitApp;
            postponesFunc = _postponesFunc;

            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            SingletonClasses.setPopupRequester(this);
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            SingletonClasses.setPopupRequester(null);
        }

        private void closeMe()
        {
            this.Close();
        }


        private void Button_Click(object sender, RoutedEventArgs e)
        {
            openPopup();
            closeMe();
        }

        private void openPopup()
        {
            MainWindow window = new MainWindow();
            window.Show();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            exitApp();
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            pospones();
        }

        private void pospones()
        {
            type_Postpon postponTime = type_Postpon._10_min;

            switch (comBx_PospTime.SelectedIndex)
            {
                case 1:
                    postponTime = type_Postpon._10_min;
                    break;
                case 2:
                    postponTime = type_Postpon._30_min;
                    break;
                case 3:
                    postponTime = type_Postpon._1_h;
                    break;
                case 4:
                    postponTime = type_Postpon._3_h;
                    break;
                case 5:
                    postponTime = type_Postpon._5_h;
                    break;
                case 6:
                    postponTime = type_Postpon._1_y;
                    break;
            }

            App.setPostponesTime((int)postponTime);
            postponesFunc();
            closeMe();
        }
    }
}
