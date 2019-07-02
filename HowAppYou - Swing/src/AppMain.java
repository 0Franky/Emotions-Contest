import classes.database.SQLiteConnection;
import layout.Configuration.ConfigurationWindow;

public class AppMain {

	public static void main(final String[] args) {

		try {
			if ((args.length == 1 && args[0].equals("-conf")) || (SQLiteConnection.getSpid().equals(""))) {
				ConfigurationWindow.getIstance();
			} else {
				HowAppYouTray.getIstance();
				// Notification.getIstance();
			}
		} catch (final Exception e) {
			// TODO: handle exception
		}

	}

}
