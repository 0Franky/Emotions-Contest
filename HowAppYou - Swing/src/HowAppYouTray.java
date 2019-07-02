import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Title.Title;
import classes.AppTimer;
import classes.Tuple;
import classes.csv.CSV_Manager;
import classes.csv.CSV_WriterBuilder;
import classes.csv.ICSV_Writer;
import classes.database.SQLiteConnection;
import layout.BubbleChart.BubbleChartWindow;
import layout.CreditWindow.CreditWindow;
import layout.Notification.Notification;
import layout.PopupWindow.PopupWindow;

public class HowAppYouTray {
	/**
	 * ConfigurationWindow instance is useful to make ConfigurationWindow class
	 * "Singleton"
	 */
	private static HowAppYouTray instance = null;

	/**
	 * Return the unique possible instance of the App
	 *
	 * @return The ConfigurationWindow.
	 * @throws Exception
	 */
	public static HowAppYouTray getIstance() throws Exception {
		if (instance == null) {
			synchronized (HowAppYouTray.class) {
				if (instance == null) {
					instance = new HowAppYouTray();
				}
			}
		}
		return instance;
	}

	/**
	 * Definition of the icon of the App
	 *
	 * Online links to the used icons
	 * "http://www.icons101.com/icon_png/size_512/id_78555/Game_Center.png";
	 * "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
	 */
	private static final String iconImageLoc = "/Assets/Icon_mini.png";

	/**
	 * application stage is stored so that it can be shown and hidden based on
	 * system tray icon operations.
	 */
	private final JFrame this_stage;

	/**
	 * Sets up the tray icon, database and the main stage (Notification)
	 */
	public HowAppYouTray() throws Exception {

		/*
		 * stores a reference to the stage. this.stage = stage;
		 *
		 * instructs the java system not to exit implicitly when the last application
		 * window is shut.
		 */

		final JFrame primaryStage = new JFrame();

		this_stage = primaryStage;

		SQLiteConnection.getConnectionDB();

		// sets up the tray icon (using awt code run on the swing thread).
		javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

		if (SQLiteConnection.getSpid().equals("")) {
			JOptionPane.showMessageDialog(this_stage, "No spid is setted, application exiting.", "Information Missed!",
					JOptionPane.ERROR);
			exitApp();
		}

		Notification.getIstance();
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
				JOptionPane.showMessageDialog(this_stage, "No system tray support, application exiting.", "Error!",
						JOptionPane.ERROR);
				System.exit(0);
			}

			// set up a system tray icon.
			final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

			final BufferedImage image = ImageIO.read(getClass().getResource(iconImageLoc));
			final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			final java.awt.MenuItem btn_Restrospective = new java.awt.MenuItem("Show retrospective");
			final java.awt.MenuItem btn_ShowPopUp = new java.awt.MenuItem("Show " + Title.APPLICATION_NAME);
			final java.awt.MenuItem btn_Credit = new java.awt.MenuItem("Credit & info");
			final java.awt.MenuItem btn_ExportCSV = new java.awt.MenuItem("Export to csv");
			// java.awt.MenuItem btn_Credits = new java.awt.MenuItem("Info & Credits");

			btn_ShowPopUp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						showPopupWindow();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btn_Credit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						showCreditWindow();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btn_ExportCSV.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					saveCSV();
				}
			});

			btn_Restrospective.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						showBubbleChartWindow();
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			// the convention for tray icons seems to be to set the default icon for opening
			// the application stage in a bold font.
			final java.awt.Font defaultFont = java.awt.Font.decode(null);
			final java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
			btn_Restrospective.setFont(boldFont);
			btn_ShowPopUp.setFont(boldFont);
			btn_ExportCSV.setFont(boldFont);
			btn_Credit.setFont(boldFont);

			// to really exit the application, the user must go to the system tray icon
			// and select the exit option, this will shutdown Java and remove the
			// tray icon (removing the tray icon will also shut down AWT).
			final java.awt.MenuItem exitItem = new java.awt.MenuItem("Quit");
			exitItem.addActionListener(event -> {
				tray.remove(trayIcon);
				exitApp();
			});

			// setup the popup menu for the application.
			final java.awt.PopupMenu popup = new java.awt.PopupMenu();
			popup.add(btn_Restrospective);
			popup.addSeparator();
			popup.add(btn_ShowPopUp);
			popup.addSeparator();
			popup.add(btn_Credit);
			popup.addSeparator();
			popup.add(btn_ExportCSV);
			popup.addSeparator();
			// popup.add(btn_Credits);
			// popup.addSeparator();
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
	 * Close the app closing the connection to DB
	 *
	 * @throws IOException
	 */
	private void exitApp() {
		SQLiteConnection.closeConnectionDB(SQLiteConnection.getConnectionDB(), null);
		System.exit(0);
	}

	/**
	 * Shows the PopupWindow application stage and ensures that it is brought out
	 * the front of all stages. Stop timer if it is running and close Notification
	 * if it is opened
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
			PopupWindow.getIstance().show();
			PopupWindow.getIstance().toFront();
		}
	}

	/**
	 * Shows the CreditWindow application stage and ensures that it is brought out
	 * the front of all stages.
	 *
	 * @throws IOException
	 */
	private void showCreditWindow() throws IOException {
		CreditWindow.getIstance().show();
	}

	/**
	 * Shows the BubbleChartWindow application stage and ensures that it is brought
	 * out the front of all stages.
	 *
	 * @throws IOException
	 */
	private void showBubbleChartWindow() throws IOException {
		BubbleChartWindow.getIstance().show();
	}

	/**
	 * Allow the export of a file .csv with all data already stored
	 */
	private void saveCSV() {
		System.out.println("CSV START");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Documents", ".csv"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		final int result = fileChooser.showOpenDialog(this_stage);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			if (!fileChooser.getSelectedFile().getAbsolutePath().endsWith(".csv")) {
				selectedFile = new File(fileChooser.getSelectedFile() + ".csv");
			}

			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			if (selectedFile != null) {
				CSV_Manager.setPATH_AND_NAME_CSV(selectedFile);
				final List<Tuple> data = SQLiteConnection.getAllDataQuery();
				final ICSV_Writer writer = CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in);
				for (final Tuple row : data) {
					writer.write(row.toList());
				}
			}
		}
		System.out.println("CSV END");
	}
}
