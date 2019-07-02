package layout.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import Title.Title;
import classes.MailSender;
import classes.csv.GoogleDocsUtils;
import classes.database.SQLiteConnection;
import classes.json.RW_JSON;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfigurationWindow {

	/**
	 * Stage this_stage define the layout of the window
	 */
	protected static Stage stage = null;

	/**
	 * ConfigurationWindow instance is useful to make ConfigurationWindow class
	 * "Singleton"
	 */
	private static ConfigurationWindow instance = null;

	/**
	 * ConfigurationWindowController is useful to manage Slider
	 */
	private static ConfigurationWindowController ConfigurationWindowController = null;

	public ConfigurationWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ConfigurationWindow.class.getResource("ConfigurationWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		ConfigurationWindowController = loader.getController();

		rootLayout.setStyle("-fx-border-color: gray; -fx-border-width: 1px 1px 1px 1px");

		Stage primaryStage = new Stage();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

		stage = primaryStage;
		stage.resizableProperty().setValue(Boolean.FALSE);

		stage.initStyle(StageStyle.UTILITY);
		stage.setTitle("Configuration");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));
		centerStage(stage);
		stage.setAlwaysOnTop(true);

		checkAutoFill();
	}

	/**
	 * Return the unique possible instance of the ConfigurationWindow
	 *
	 * @return The ConfigurationWindow.
	 * @throws IOException Generic I/O error.
	 */
	public static ConfigurationWindow getIstance() throws IOException {
		if (instance == null)
			synchronized (ConfigurationWindow.class) {
				if (instance == null) {
					instance = new ConfigurationWindow();
				}
			}
		return instance;
	}

	/**
	 * Set the Stage (Window) of the ConfigurationWindow on right-upper corner of
	 * screen
	 *
	 * @param Stage stage (the window of the ConfigurationWindow).
	 */
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		double screenWidth = screenBounds.getWidth();
		double screenHeight = screenBounds.getHeight();

		stage.show();

		stage.setX((screenWidth / 2) - (stage.getWidth() / 2));
		stage.setY((screenHeight / 2) - (stage.getHeight() / 2));
	}

	private void checkAutoFill() {

		Map<String, String> json_param = RW_JSON.readJson(Title.APPLICATION_NAME + ".conf");

		String companyName = "";
		String spid = "";

		try {
			companyName = json_param.get("companyName");
			spid = json_param.get("spid");
		} catch (Exception e) {
			// TODO: handle exception
		}

		ConfigurationWindowController.txt_SheetName.setText(companyName);
		ConfigurationWindowController.txt_Spid.setText(spid);
	}

	protected void makeSheet() throws IOException, GeneralSecurityException, URISyntaxException {
		String companyName = ConfigurationWindowController.txt_SheetName.getText();
		final String sheetName = "SurveyResults-" + companyName;
		final String spid = GoogleDocsUtils.getInstance().createSheet(sheetName);
		GoogleDocsUtils.getInstance().shareSheetStandard(spid);
		GoogleDocsUtils.getInstance().getSheetByTitle(spid);
		SQLiteConnection.setSheet(spid);

		String mailBody = "<!DOCTYPE html>" + "<html style=\"height: 100%;\">" + "<head>" + "		<style>"
				+ "			li { " + "				float:left; " + "				width:100%; "
				+ "				overflow:hidden;  " + "				overflow:hidden;"
				+ "				margin: 0 auto; " + "				position: absolute; " + "				left: 50%; "
				+ "			}" + "			" + "			li:hover { " + "				height:auto; "
				+ "			}" + "		</style>" + "	</head>" + "	<body  style=\"height: 100%;\">"
				+ "		<div id=\"container\" style=\"width: 100%; height: 100%; position: relative;\">"
				+ "			<div id=\"navi\" style=\"margin: 0 auto; position: absolute; top: 50%; left: 50%; margin-right: -50%; transform: translate(-50%, -50%)\">"
				+ "				Sheet name: <b>" + sheetName + "</b><br>" + "				Maker: <b>" + Title.USER_ID
				+ "</b><br>" + "				Spid: <b>" + spid + "</b><br>"
				+ "				Sheet URL: <b><a target=\"_blank\" href=\"https://docs.google.com/spreadsheets/d/"
				+ spid + "\">https://docs.google.com/spreadsheets/d/" + spid + "</a></b><br>" + "			</div>"
				+ "<ul>"
				+ "				<li><img src=\"http://icons.iconarchive.com/icons/dtafalonso/yosemite-flat/128/Game-Center-icon.png\"></li>"
				+ "			</ul>" + "		</div>" + "	</body>" + "</html>";

		for (String email : Title.EMAILS_TO_SEND) {
			MailSender.sendMail(Title.EMAILS_SENDER, Title.PASSWORD_EMAILS_SENDER, email, "SPID " + companyName,
					mailBody);
		}

		Map<String, String> json_param = new HashMap<>();

		json_param.put("companyName", companyName);
		json_param.put("spid", spid);

		RW_JSON.writeJson(Title.APPLICATION_NAME + ".conf", json_param);

		InfoAlert("Company spid: " + spid + "\nCompany sheet: https://docs.google.com/spreadsheets/d/" + spid
				+ "\n\nEnd.");
	}

	protected void setSheet() {
		String spid = ConfigurationWindowController.txt_Spid.getText();
		SQLiteConnection.setSheet(spid);

		InfoAlert("Company spid: " + spid + "\n\nEnd.");
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 * 
	 * @return Tuple
	 */
	protected void InfoAlert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(stage);
		alert.showAndWait();
	}

	public static void main(String[] args) throws IOException, java.awt.AWTException {
		// Just launches the JavaFX application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.

		/*
		 * try { launch(args); } catch (Exception e) { // TODO: handle exception }
		 */
	}
}
