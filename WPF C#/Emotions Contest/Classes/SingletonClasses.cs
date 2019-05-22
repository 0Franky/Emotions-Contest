﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Emotions_Contest.Classes
{
    static class SingletonClasses
    {
        private static MainWindow mainWindow = null;

        public static void setMainForm(MainWindow window)
        {
            mainWindow = window;
        }

        public static MainWindow getMainForm()
        {
            return mainWindow;
        }
    }
}
