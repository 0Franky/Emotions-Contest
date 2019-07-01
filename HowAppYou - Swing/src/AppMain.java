import layout.Configuration.ConfigurationWindow;

public class AppMain {

	public static void main(final String[] args) {

		/*
		 * try { BubbleChartWindow.getIstance().show(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

		try {
			if (args.length == 1 && args[0].equals("-conf")) {
				ConfigurationWindow.getIstance();
			} else {
				SystemTray.getIstance();
				// Notification.getIstance();
			}
		} catch (final Exception e) {
			// TODO: handle exception
		}

	}

}
