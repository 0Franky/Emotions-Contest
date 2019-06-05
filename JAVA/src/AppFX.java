import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;

import javax.imageio.ImageIO;

import Title.Title;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// Java 8 code
public class AppFX extends Application {

	private AnchorPane rootLayout;

	// one icon location is shared between the application tray icon and task bar
	// icon.
	// you could also use multiple icons to allow for clean display of tray icons on
	// hi-dpi devices.
	private static final String iconImageLoc = "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";

	// application stage is stored so that it can be shown and hidden based on
	// system tray icon operations.
	private Stage stage;

	// a timer allowing the tray icon to provide a periodic notification event.
	private Timer notificationTimer = new Timer();

	// format used to display the current time in a tray icon notification.
	private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

	// sets up the javafx application.
	// a tray icon is setup for the icon, but the main stage remains invisible until
	// the user
	// interacts with the tray icon.
	@Override
	public void start(final Stage stage) {
		// stores a reference to the stage.
		this.stage = stage;

		// instructs the javafx system not to exit implicitly when the last application
		// window is shut.
		Platform.setImplicitExit(false);

		// sets up the tray icon (using awt code run on the swing thread).
		javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

		initRootLayout();

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppFX.class.getResource("/layout/Notification.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);

			stage.setTitle("Notification");
			stage.setResizable(false);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void NewStage() {
		try {
			Stage subStage = new Stage();
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppFX.class.getResource("/layout/PopupWindow.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			subStage.setTitle("PopupWindow");
			stage.setResizable(false);
			subStage.setScene(scene);
			stage.hide();
			subStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			URL imageLoc = new URL(iconImageLoc);
			java.awt.Image image = ImageIO.read(imageLoc);
			java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

			// if the user double-clicks on the tray icon, show the main app stage.
			// trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			java.awt.MenuItem openItem1 = new java.awt.MenuItem("Show retrospective");
			java.awt.MenuItem openItem2 = new java.awt.MenuItem("Show " + Title.APPLICATION_NAME);
			java.awt.MenuItem openItem3 = new java.awt.MenuItem("Export to csv");
			openItem2.addActionListener(event -> Platform.runLater(this::NewStage));

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
	 */
	/*
	 * private void showStage() { if (stage != null) { stage.show();
	 * stage.toFront(); } }
	 */

	public static void main(String[] args) throws IOException, java.awt.AWTException {
		// Just launches the JavaFX application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.
		launch(args);
	}
}
