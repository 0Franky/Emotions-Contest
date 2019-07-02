package layout.Configuration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Title.Title;
import classes.MailSender;
import classes.JCustomController.BackgroundPane;
import classes.csv.GoogleDocsUtils;
import classes.database.SQLiteConnection;
import classes.json.RW_JSON;
import layout.PopupWindow.PopupWindow;

public class ConfigurationWindow implements WindowListener {

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// ; // usa il look di default
		}
	}

	JButton btn_Done_SheetName = new JButton("Done");
	JFrame this_stage = new JFrame();

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;

	/**
	 * ConfigurationWindow instance is useful to make ConfigurationWindow class
	 * "Singleton"
	 */
	private static ConfigurationWindow instance = null;
	private final JLabel lblMakingSheetprima = new JLabel("Making sheet (prima configurazione)");
	private final JLabel lblSettingSheetprima = new JLabel("Setting sheet (prima configurazione)");
	private final JPanel panel_1 = new JPanel();
	private final JTextField txt_SheetName;
	private final JPanel panel_2 = new JPanel();
	private final JButton btn_Done_Spid = new JButton("Done");
	private final JTextField txt_Spid = new JTextField();
	private final JLabel lblSpidesSuncompany = new JLabel("SPID (es. SUN_COMPANY):");

	private ConfigurationWindow() throws IOException {
		this_stage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this_stage.setAlwaysOnTop(true);
		this_stage.setIconImage(
				Toolkit.getDefaultToolkit().getImage(ConfigurationWindow.class.getResource("/Assets/Icon.png")));
		this_stage.setType(Type.UTILITY);
		this_stage.setResizable(false);

		this_stage.setSize(640, 250);
		this_stage.addWindowListener(this);

		// final JPanel panel = new JPanel();
		final BackgroundPane panel = new BackgroundPane();
		panel.setBounds(-25, -25, this_stage.getWidth(), this_stage.getHeight());
		panel.setBackground(ImageIO.read(ConfigurationWindow.class.getResource("/Assets/Icon.png")), 0.13f,
				(int) (this_stage.getWidth() * 1.3), (int) (this_stage.getHeight() * 1.3));
		this_stage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JLabel Configuration = new JLabel("Configuration");
		Configuration.setHorizontalAlignment(SwingConstants.CENTER);
		Configuration.setFont(new Font("System", Font.BOLD, 18));
		Configuration.setBounds(247, 13, 148, 22);
		panel.add(Configuration);
		panel_1.setBounds(22, 48, 590, 60);

		panel.add(panel_1);
		panel_1.setLayout(null);
		lblMakingSheetprima.setBounds(10, 5, 237, 16);
		panel_1.add(lblMakingSheetprima);
		lblMakingSheetprima.setHorizontalAlignment(SwingConstants.LEFT);
		lblMakingSheetprima.setFont(new Font("System", Font.BOLD | Font.ITALIC, 13));
		btn_Done_SheetName.setBounds(503, 25, 65, 25);
		panel_1.add(btn_Done_SheetName);

		btn_Done_SheetName.setFont(new Font("Dialog", Font.BOLD, 12));

		txt_SheetName = new JTextField();
		txt_SheetName.setBounds(184, 25, 311, 25);
		panel_1.add(txt_SheetName);
		txt_SheetName.setColumns(10);

		final JLabel lblNewLabel = new JLabel("Name sheet (es. SUN_COMPANY):");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(12, 25, 165, 25);
		panel_1.add(lblNewLabel);
		panel_2.setLayout(null);
		panel_2.setBounds(22, 121, 590, 60);

		panel.add(panel_2);
		btn_Done_Spid.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_Done_Spid.setBounds(503, 25, 65, 25);

		panel_2.add(btn_Done_Spid);
		txt_Spid.setColumns(10);
		txt_Spid.setBounds(184, 25, 311, 25);

		panel_2.add(txt_Spid);
		lblSpidesSuncompany.setHorizontalAlignment(SwingConstants.LEFT);
		lblSpidesSuncompany.setBounds(12, 25, 130, 25);

		panel_2.add(lblSpidesSuncompany);
		lblSettingSheetprima.setBounds(10, 5, 239, 22);
		panel_2.add(lblSettingSheetprima);
		lblSettingSheetprima.setHorizontalAlignment(SwingConstants.LEFT);
		lblSettingSheetprima.setFont(new Font("System", Font.BOLD | Font.ITALIC, 13));

		btn_Done_SheetName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					makeSheet();
				} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("btn_Done_SheetName");
			}
		});

		btn_Done_Spid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setSheet();
				System.out.println("btn_Done_Spid");
			}
		});

		checkAutoFill();

		centerStage();

		this_stage.setVisible(true);
	}

	/**
	 * Return the unique possible instance of the Notification
	 *
	 * @return The Notification.
	 * @throws IOException Generic I/O error.
	 */
	public static ConfigurationWindow getIstance() throws IOException {
		if (instance == null) {
			synchronized (ConfigurationWindow.class) {
				if (instance == null) {
					instance = new ConfigurationWindow();
				}
			}
		}
		return instance;
	}

	/**
	 * Set the Stage (Window) of the Notification on right-upper corner of screen
	 *
	 * @param Stage stage (the window of the Notification).
	 */
	private void centerStage() {
		// java - get screen size using the Toolkit class
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenHeight = screenSize.height;
		final int screenWidth = screenSize.width;

		this_stage.isShowing();

		this_stage.setLocation(((screenWidth / 2) - (this_stage.getWidth() / 2)),
				((screenHeight / 2) - (this_stage.getHeight() / 2)));
	}

	/**
	 * Close the Notification
	 */
	public void close() {
		try {
			canClose = true;
			openPopUp();
			this_stage.dispatchEvent(new WindowEvent(this_stage, WindowEvent.WINDOW_CLOSING));
		} catch (final Exception ex) {
			System.err.println("not Hide");
			ex.printStackTrace();
		}
	}

	/**
	 * Show on screen the Notification
	 */
	public void show() {
		try {
			this_stage.isShowing();
		} catch (final Exception ex) {
			System.err.println("not Show");
			ex.printStackTrace();
		}
	}

	/**
	 * Set on front of all opened window the Notification
	 */
	public void toFront() {
		try {
			this_stage.toFront();
		} catch (final Exception ex) {
			System.err.println("not Front");
			ex.printStackTrace();
		}
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 */
	protected void cleanInstance() {
		instance = null;
	}

	private void checkAutoFill() {

		final Map<String, String> json_param = RW_JSON.readJson(Title.APPLICATION_NAME + ".conf");

		String companyName = "";
		String spid = "";

		try {
			companyName = json_param.get("companyName");
			spid = json_param.get("spid");
		} catch (final Exception e) {
			// TODO: handle exception
		}

		txt_SheetName.setText(companyName);
		txt_Spid.setText(spid);
	}

	protected void makeSheet() throws IOException, GeneralSecurityException, URISyntaxException {
		final String companyName = txt_SheetName.getText();
		if (!companyName.equals("")) {
			final String sheetName = "SurveyResults-" + companyName;
			final String spid = GoogleDocsUtils.getInstance().createSheet(sheetName);
			GoogleDocsUtils.getInstance().shareSheetStandard(spid);
			GoogleDocsUtils.getInstance().getSheetByTitle(spid);
			SQLiteConnection.setSheet(spid);

			final String mailBody = "<!DOCTYPE html>" + "<html style=\"height: 100%;\">" + "<head>" + "		<style>"
					+ "			li { " + "				float:left; " + "				width:100%; "
					+ "				overflow:hidden;  " + "				overflow:hidden;"
					+ "				margin: 0 auto; " + "				position: absolute; "
					+ "				left: 50%; " + "			}" + "			" + "			li:hover { "
					+ "				height:auto; " + "			}" + "		</style>" + "	</head>"
					+ "	<body  style=\"height: 100%;\">"
					+ "		<div id=\"container\" style=\"width: 100%; height: 100%; position: relative;\">"
					+ "			<div id=\"navi\" style=\"margin: 0 auto; position: absolute; top: 50%; left: 50%; margin-right: -50%; transform: translate(-50%, -50%)\">"
					+ "				Sheet name: <b>" + sheetName + "</b><br>" + "				Maker: <b>"
					+ Title.USER_ID + "</b><br>" + "				Spid: <b>" + spid + "</b><br>"
					+ "				Sheet URL: <b><a target=\"_blank\" href=\"https://docs.google.com/spreadsheets/d/"
					+ spid + "\">https://docs.google.com/spreadsheets/d/" + spid + "</a></b><br>" + "			</div>"
					+ "<ul>"
					+ "				<li><img src=\"http://icons.iconarchive.com/icons/dtafalonso/yosemite-flat/128/Game-Center-icon.png\"></li>"
					+ "			</ul>" + "		</div>" + "	</body>" + "</html>";

			for (final String email : Title.EMAILS_TO_SEND) {
				MailSender.sendMail(Title.EMAILS_SENDER, Title.PASSWORD_EMAILS_SENDER, email, "SPID " + companyName,
						mailBody);
			}

			final Map<String, String> json_param = new HashMap<>();

			json_param.put("companyName", companyName);
			json_param.put("spid", spid);

			RW_JSON.writeJson(Title.APPLICATION_NAME + ".conf", json_param);

			InfoAlert("Company spid: " + spid + "\nCompany sheet: https://docs.google.com/spreadsheets/d/" + spid
					+ "\n\nEnd.");
		} else {
			WarningAlert("Name sheet not filled!");
		}
	}

	protected void setSheet() {
		final String spid = txt_Spid.getText();
		if (!spid.equals("")) {
			SQLiteConnection.setSheet(spid);

			InfoAlert("Company spid: " + spid + "\n\nEnd.");
		} else {
			WarningAlert("Spid not filled!");
		}
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 *
	 * @return Tuple
	 */
	protected void InfoAlert(final String text) {
		JOptionPane.showConfirmDialog(this_stage, text, "Information", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 *
	 * @return Tuple
	 */
	protected void WarningAlert(final String text) {
		JOptionPane.showConfirmDialog(this_stage, text, "Warning!", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Opens the popUp-Window and close the Notification
	 */
	public void openPopUp() throws IOException {
		ConfigurationWindow.getIstance().close();
		PopupWindow.getIstance();
	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		SQLiteConnection.closeConnectionDB(SQLiteConnection.getConnectionDB(), null);
	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}
}