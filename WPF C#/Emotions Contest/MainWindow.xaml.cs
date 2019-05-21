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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Emotions_Contest
{
    /// <summary>
    /// Logica di interazione per MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private string pleasantness = "";
        private string excitement = "";

        public MainWindow()
        {
            InitializeComponent();
        }



        private bool checkCorrectionParam()
        {
            bool status = true;

            if (lbl_Activity.Text == "")
            {
                status = false;
                MessageBox.Show("Activity not filled!");
            }

            if (pleasantness == "")
            {
                status = false;
                MessageBox.Show("Pleasantness not checked!");
            }

            if (excitement == "")
            {
                status = false;
                MessageBox.Show("Excitement not checked!");
            }

            return status;
        }

        private void getResults()
        {
            if (checkCorrectionParam())
            {
                CSV_Writer.write(ConversionParamToArrays.convert(lbl_Activity.Text, pleasantness, excitement, txt_Notes.Text));
            }
        }

        private void Cb_Pleasantness_Checking(object sender, RoutedEventArgs e)
        {
            RadioButton cb = (RadioButton)sender;
            pleasantness = cb.Content.ToString();
        }

        private void Cb_Excitement_Checking(object sender, RoutedEventArgs e)
        {
            RadioButton cb = (RadioButton)sender;
            excitement = cb.Content.ToString();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Press Done");
            MessageBox.Show("Results\n" +
                "\nActivity: " + lbl_Activity.Text +
                "\nPleasantness: " + pleasantness +
                "\nExcitement: " + excitement +
                "\nNotes: " + txt_Notes.Text
                );
        }
    }
}