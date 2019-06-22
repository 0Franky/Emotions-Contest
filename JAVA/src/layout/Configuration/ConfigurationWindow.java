package layout.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import Title.Title;
import classes.MailSender;
import classes.csv.GoogleDocsUtils;
import classes.database.SQLiteConnection;
import javafx.application.Application;
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

public class ConfigurationWindow extends Application {

	/**
	 * Stage this_stage define the layout of the window
	 */
	protected static Stage this_stage = new Stage();

	/**
	 * ConfigurationWindow instance is useful to make ConfigurationWindow class
	 * "Singleton"
	 */
	private static ConfigurationWindow instance = null;

	/**
	 * ConfigurationWindowController is useful to manage Slider
	 */
	private ConfigurationWindowController ConfigurationWindowController = null;

	private ConfigurationWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ConfigurationWindow.class.getResource("ConfigurationWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		ConfigurationWindowController = loader.getController();

		rootLayout.setStyle("-fx-border-color: gray; -fx-border-width: 1px 1px 1px 1px");
		Stage stage = new Stage();

		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Configuration");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));
		this_stage = stage;
		centerStage(this_stage);

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
		double screenHeight = screenBounds.getWidth();

		this_stage.show();

		stage.setX((screenWidth / 2) - (stage.getWidth() / 2));
		stage.setY((screenHeight / 2) - (stage.getHeight() / 2));
	}

	private void checkAutoFill() {
		
		String companyName = READ_JSON
		String spid = READ_JSON
		
		ConfigurationWindowController.lbl_SheetName.setText(companyName);
		ConfigurationWindowController.lbl_Spid.setText(spid);
	}

	protected void makeSheet() throws IOException, GeneralSecurityException, URISyntaxException {
		String companyName = ConfigurationWindowController.lbl_SheetName.getText();
		final String spid = GoogleDocsUtils.getInstance().createSheet("SurveyResults-" + companyName);
		GoogleDocsUtils.getInstance().shareSheet(spid);
		GoogleDocsUtils.getInstance().getSheetByTitle(spid);
		SQLiteConnection.setSheet(spid);

		for (String email : Title.EMAILS_TO_SEND) {
			MailSender.sendMail(Title.EMAILS_SENDER, Title.PASSWORD_EMAILS_SENDER, email, "SPID " + companyName, spid);
		}
		
		WRITE_JSON

		InfoAlert("Company spid: " + spid + "\nCompany sheet: https://docs.google.com/spreadsheets/d/" + spid
				+ "\n\nEnd.");
	}

	protected void setSheet() {
		String spid = ConfigurationWindowController.lbl_Spid.getText();
		SQLiteConnection.setSheet(spid);

		InfoAlert("CompanySheet: " + spid + "\n\nEnd.");
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
		alert.initOwner(this_stage);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ConfigurationWindow.getIstance();
	}

}
