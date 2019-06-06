import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;

import Title.Title;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import layout.Notification.Notification;
import layout.PopupWindow.PopupWindow;

// Java 8 code
public class AppFX extends Application {

	// one icon location is shared between the application tray icon and task bar
	// icon.
	// you could also use multiple icons to allow for clean display of tray icons on
	// hi-dpi devices.
	// altri possibili link da cui recuperare l'immagine
	// "http://www.icons101.com/icon_png/size_512/id_78555/Game_Center.png";
	// "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
	private static final String iconImageLoc = "Assets/Icon_mini.png";

	// application stage is stored so that it can be shown and hidden based on
	// system tray icon operations.
	public Stage stage;

	// a timer allowing the tray icon to provide a periodic notification event.
	private Timer notificationTimer = new Timer();

	// format used to display the current time in a tray icon notification.
	// private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

	// sets up the javafx application.
	// a tray icon is setup for the icon, but the main stage remains invisible until
	// the user
	// interacts with the tray icon.

	@Override
	public void start(final Stage stage) throws Exception {
		// stores a reference to the stage.
		// this.stage = stage;

		// instructs the javafx system not to exit implicitly when the last application
		// window is shut.
		Platform.setImplicitExit(false);
		// sets up the tray icon (using awt code run on the swing thread).
		javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

		Notification.getIstance();
		PopupWindow.getIstance();

	}

	/**
	 * Sets up a system tray icon for the application.
	 */
	private void addAppToTray() {
		try {
			// ensure awt toolkit is initialized.
			java.awt.Toolkit.getDefaultToolkit();

			// app requires system tray support, just exit if there is no support.
			if (!java.awt.SystemTray.isSupported()) {
				System.out.println("No system tray support, application exiting.");
				Platform.exit();
			}

			// set up a system tray icon.
			java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
			File imageLoc = new File(iconImageLoc);
			java.awt.Image image = ImageIO.read(imageLoc);
			java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

			// if the user double-clicks on the tray icon, show the main app stage.
			// trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			java.awt.MenuItem openItem1 = new java.awt.MenuItem("Show retrospective");
			java.awt.MenuItem openItem2 = new java.awt.MenuItem("Show " + Title.APPLICATION_NAME);
			java.awt.MenuItem openItem3 = new java.awt.MenuItem("Export to csv");
			openItem2.addActionListener(event -> Platform.runLater(() -> {
				try {
					showSubStage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));

			// the convention for tray icons seems to be to set the default icon for opening
			// the application stage in a bold font.
			java.awt.Font defaultFont = java.awt.Font.decode(null);
			java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
			openItem1.setFont(boldFont);
			openItem2.setFont(boldFont);
			openItem3.setFont(boldFont);

			// to really exit the application, the user must go to the system tray icon
			// and select the exit option, this will shutdown JavaFX and remove the
			// tray icon (removing the tray icon will also shut down AWT).
			java.awt.MenuItem exitItem = new java.awt.MenuItem("Quit");
			exitItem.addActionListener(event -> {
				notificationTimer.cancel();
				Platform.exit();
				tray.remove(trayIcon);
			});

			// setup the popup menu for the application.
			final java.awt.PopupMenu popup = new java.awt.PopupMenu();
			popup.add(openItem1);
			popup.addSeparator();
			popup.add(openItem2);
			popup.addSeparator();
			popup.add(openItem3);
			popup.addSeparator();
			popup.add(exitItem);
			trayIcon.setPopupMenu(popup);

			// add the application tray icon to the system tray.
			tray.add(trayIcon);
		} catch (java.awt.AWTException | IOException e) {
			System.out.println("Unable to init system tray");
			e.printStackTrace();
		}
	}

	/**
	 * Shows the application stage and ensures that it is brought ot the front of
	 * all stages.
	 * 
	 * @throws IOException
	 */
	private void showSubStage() throws IOException {
		if (PopupWindow.getIstance() != null) {
			if (Notification.getIstance() != null) {
				Notification.getIstance().hide();
			}
			PopupWindow.getIstance().show();
			PopupWindow.getIstance().toFront();
		}
	}

	public static void main(String[] args) throws IOException, java.awt.AWTException {
		// Just launches the JavaFX application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.
		launch(args);
	}
}
