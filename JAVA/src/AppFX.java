import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Title.Title;
import classes.AppTimer;
import classes.Tuple;
import classes.csv.CSV_Manager;
import classes.csv.CSV_WriterBuilder;
import classes.csv.ICSV_Writer;
import classes.database.SQLiteConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layout.BubbleChart.BubbleChartWindow;
import layout.Credits.CreditWindow;
import layout.Notification.Notification;
import layout.PopupWindow.PopupWindow;

// Java 8 code
public class AppFX extends Application {

	/*
	 * one icon location is shared between the application tray icon and task bar
	 * icon. you could also use multiple icons to allow for clean display of tray
	 * icons on hi-dpi devices. altri possibili link da cui recuperare l'immagine
	 * 
	 * "http://www.icons101.com/icon_png/size_512/id_78555/Game_Center.png";
	 * "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
	 * 
	 */
	private static final String iconImageLoc = "/Assets/Icon_mini.png";

	/*
	 * application stage is stored so that it can be shown and hidden based on
	 * system tray icon operations.
	 */
	private Stage this_stage;

	/*
	 * a timer allowing the tray icon to provide a periodic notification event.
	 * private Timer notificationTimer = new Timer();
	 */

	/*
	 * format used to display the current time in a tray icon notification. private
	 * DateFormat timeFormat = SimpleDateFormat.getTimeInstance();
	 * 
	 * sets up the javafx application. a tray icon is setup for the icon, but the
	 * main stage remains invisible until the user interacts with the tray icon.
	 */

	@Override
	public void start(final Stage stage) throws Exception {

		/*
		 * stores a reference to the stage. this.stage = stage;
		 * 
		 * instructs the javafx system not to exit implicitly when the last application
		 * window is shut.
		 */

		Platform.setImplicitExit(false);
		// sets up the tray icon (using awt code run on the swing thread).
		javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

		Notification.getIstance();
		// PopupWindow.getIstance();

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
				new Alert(Alert.AlertType.ERROR, "No system tray support, application exiting.").showAndWait();
				Platform.exit();
			}

			// set up a system tray icon.
			java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
			/*
			 * File imageLoc = new File(iconImageLoc); java.awt.Image image =
			 * ImageIO.read(imageLoc);
			 */
			BufferedImage image = ImageIO.read(getClass().getResource(iconImageLoc));
			java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

			// if the user double-clicks on the tray icon, show the main app stage.
			// trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			java.awt.MenuItem btn_Restrospective = new java.awt.MenuItem("Show retrospective");
			java.awt.MenuItem btn_ShowPopUp = new java.awt.MenuItem("Show " + Title.APPLICATION_NAME);
			java.awt.MenuItem btn_ExportCSV = new java.awt.MenuItem("Export to csv");
			java.awt.MenuItem btn_Credits = new java.awt.MenuItem("Info & Credits");

			btn_ShowPopUp.addActionListener(event -> Platform.runLater(() -> {
				try {
					showPopupWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));

			btn_Credits.addActionListener(event -> Platform.runLater(() -> {
				try {
					showCreditWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));

			btn_ExportCSV.addActionListener(event -> Platform.runLater(() -> {
				saveCSV();
			}));

			btn_Restrospective.addActionListener(event -> Platform.runLater(() -> {
				try {
					showBubbleChartWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));

			// the convention for tray icons seems to be to set the default icon for opening
			// the application stage in a bold font.
			java.awt.Font defaultFont = java.awt.Font.decode(null);
			java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
			btn_Restrospective.setFont(boldFont);
			btn_ShowPopUp.setFont(boldFont);
			btn_ExportCSV.setFont(boldFont);
			btn_Credits.setFont(boldFont);

			// to really exit the application, the user must go to the system tray icon
			// and select the exit option, this will shutdown JavaFX and remove the
			// tray icon (removing the tray icon will also shut down AWT).
			java.awt.MenuItem exitItem = new java.awt.MenuItem("Quit");
			exitItem.addActionListener(event -> {
				// notificationTimer.cancel();
				Platform.exit();
				tray.remove(trayIcon);
			});

			// setup the popup menu for the application.
			final java.awt.PopupMenu popup = new java.awt.PopupMenu();
			popup.add(btn_Restrospective);
			popup.addSeparator();
			popup.add(btn_ShowPopUp);
			popup.addSeparator();
			popup.add(btn_ExportCSV);
			popup.addSeparator();
			popup.add(btn_Credits);
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
	private void showPopupWindow() throws IOException {
		if (PopupWindow.getIstance() != null) {
			if (Notification.getIstance() != null) {
				Notification.getIstance().close();
			}
			if (AppTimer.getIstance() != null) {
				AppTimer.getIstance().stopTimer();
				System.out.println("Timer Interrotto");
			}
			PopupWindow.getIstance();
			PopupWindow.getIstance().toFront();
		}
	}

	/**
	 * Shows the application stage and ensures that it is brought ot the front of
	 * all stages.
	 * 
	 * @throws IOException
	 */
	private void showCreditWindow() throws IOException {
		CreditWindow.getIstance().show();
	}

	/**
	 * Shows the application stage and ensures that it is brought ot the front of
	 * all stages.
	 * 
	 * @throws IOException
	 */
	private void showBubbleChartWindow() throws IOException {
		BubbleChartWindow.getIstance().show();
	}

	public static void main(String[] args) throws IOException, java.awt.AWTException {
		// Just launches the JavaFX application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.
		try {
			launch(args);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void saveCSV() {
		FileChooser.ExtensionFilter csvExtensionFilter = new FileChooser.ExtensionFilter(
				"CSV - Comma-Separated Values (.csv)", "*.csv");

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.setSelectedExtensionFilter(csvExtensionFilter);
		fileChooser.getExtensionFilters().add(csvExtensionFilter);
		File outFile = fileChooser.showSaveDialog(this_stage);

		/*
		 * if (outFile == null) { Alert alert = new Alert(AlertType.ERROR);
		 * alert.setTitle("No folder selected"); alert.setHeaderText(null);
		 * alert.setContentText("Aborting operation!");
		 * alert.initStyle(StageStyle.UTILITY); alert.initOwner(this_stage);
		 * alert.showAndWait(); }
		 */

		if (outFile != null) {
			CSV_Manager.setPATH_AND_NAME_CSV(outFile);
			List<Tuple> data = SQLiteConnection.getAllDataQuery();
			ICSV_Writer writer = CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in);
			for (Tuple row : data) {
				writer.write(row.toList());
			}
			// CSV_Manager.reset_Manager();
		}
	}
}
