package layout.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class ConfigurationWindowController implements Initializable {

	@FXML
	protected TextField txt_SheetName;

	@FXML
	protected Button btn_Done_SheetName;

	@FXML
	protected TextField txt_Spid;

	@FXML
	protected Button btn_Done_Spid;

	/**
	 * Manage the click on "OK" btn_Done_SheetName
	 * 
	 * @param ActionEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	private void pressed_btn_Done_SheetName(ActionEvent event) throws IOException {
		if (!txt_SheetName.getText().equals("")) {
			try {
				ConfigurationWindow.getIstance().makeSheet();
			} catch (GeneralSecurityException | URISyntaxException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				WarningAlert("No sheets created!\nSomething went wrong! Try again later...");
			}
		} else {
			WarningAlert("Name sheet not filled!");
		}
	}

	/**
	 * Manage the click on "OK" btn_Done_Spid
	 * 
	 * @param ActionEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	private void pressed_btn_Done_Spid(ActionEvent event) throws IOException {
		if (!txt_Spid.getText().equals("")) {
			ConfigurationWindow.getIstance().setSheet();
		} else {
			WarningAlert("Spid not filled!");
		}
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 * 
	 * @return Tuple
	 */
	protected void WarningAlert(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Information Missed!");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(ConfigurationWindow.stage);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
