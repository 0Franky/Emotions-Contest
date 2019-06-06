import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FxFXMLController implements Initializable {

	// The reference of outputText will be injected by the FXML loader

	// @FXML
	// private TextArea outputText;

	// location and resources will be automatically injected by the FXML loader
	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	Button dismissButton;

	@FXML
	Button postponeButton;

	// Add a public no-args constructor
	public FxFXMLController() {
	}

	@FXML
	private void initialize() {
	}

	@FXML
	private void dismissAction(ActionEvent event) {
		System.out.println("You clicked button: ");
		// Notification.getIstance().getStage().hide();
		// Avvia Timer
	}

	@FXML
	private void postponeAction() {
		// Avvia Timer Leggendo lo Slider
	}

	@FXML
	private void handleOnButtonAction(ActionEvent event) {
		System.out.println("You clicked button: " + ((Button) event.getSource()).getId());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}