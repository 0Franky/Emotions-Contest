import java.io.IOException;

import layout.Configuration.ConfigurationWindow;

public class AppMain {

	/**
	 * Main
	 * 
	 * @param args
	 * @throws IOException
	 * @throws             java.awt.AWTException
	 */
	public static void main(String[] args) throws IOException, java.awt.AWTException {
		// Just launches the application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.

		try {
			if (args.length == 1 && args[0].equals("-conf")) {
				ConfigurationWindow.main(args);
			} else {
				AppFX.main(args);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
