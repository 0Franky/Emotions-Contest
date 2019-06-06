package layout.Notification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class FxFXMLController implements Initializable {

	// The reference of outputText will be injected by the FXML loader

	// Add a public no-args constructor
	// public FxFXMLController() {
	// }

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	Button dismissButton;

	@FXML
	Button postponeButton;

	@FXML
	Slider mySlider;

	@FXML
	private void initialize() {
	}

	@FXML
	private void postponeAction(ActionEvent event) {
		System.out.println("You clicked postponeButton");
		Notification.getIstance().hide(); // Avvia Timer
	}

	@FXML
	private void dismissAction(ActionEvent event) {
		System.out.println("You clicked dismissButton");
		Notification.getIstance().hide(); // Avvia Timer
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}