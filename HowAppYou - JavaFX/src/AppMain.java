import java.io.IOException;

import classes.database.SQLiteConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import layout.Configuration.ConfigurationWindow;

public class AppMain extends Application {

	static String[] args;

	/**
	 * Main
	 * 
	 * @param args
	 * @throws IOException
	 * @throws             java.awt.AWTException
	 */
	public static void main(String[] _args) throws IOException, java.awt.AWTException {
		// Just launches the application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.

		args = _args;

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		try {
			if ((args.length == 1 && args[0].equals("-conf")) || (SQLiteConnection.getSpid().equals(""))) {
				ConfigurationWindow.getIstance();
			} else {
				AppFX.getIstance();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
