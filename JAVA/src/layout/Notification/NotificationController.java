package layout.Notification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.AppTimer;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import layout.PopupWindow.PopupWindow;

public class NotificationController implements Initializable {

	// The reference of outputText will be injected by the FXML loader

	// Add a public no-args constructor
	// public FxFXMLController() {
	// }

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	AnchorPane AnchorPaneNotification;

	@FXML
	Button dismissButton;

	@FXML
	Button postponeButton;

	@FXML
	Slider mySlider;

	@FXML
	Label labelSlider;

	@FXML
	private void initialize() {
	}

	@FXML
	private void postponeAction(ActionEvent event) throws IOException, NumberFormatException, InterruptedException {
		System.out.println("You clicked postponeButton");
		String Time = labelSlider.getText();
		// Time = Time.substring(0, Time.length() - 1);
		System.out.println("Minuti da passare al Timer: " + Time);
		Notification.getIstance().close();
		// Imposto il Timer //
		AppTimer.getIstance().startTimer(Integer.parseInt(Time));
		// System.out.println("Fine Thread");
		Notification.getIstance().cleanInstance(); // <<-->> CANCEL THE INSTANCE <<-->>
	}

	@FXML
	public void onSliderChanged() {
		labelSlider.textProperty().bind(Bindings.format("%.0f", mySlider.valueProperty()));
		/*
		 * int sliderValue = (int) mySlider.getValue();
		 * labelSlider.setStyle("-fx-font-weight: bold;");
		 * labelSlider.setText(sliderValue + "'"); // System.out.println("sliderValue "
		 * + sliderValue);
		 */
	}

	@FXML
	public void openPopUp() throws IOException {
		Notification.getIstance().close();
		PopupWindow.getIstance();
	}

	@FXML
	private void dismissAction(ActionEvent event) throws IOException {
		// System.out.println("You clicked dismissButton");
		Notification.getIstance().close();
	}

	@FXML
	public void mousePressed(MouseEvent event) throws IOException {
		Notification.getIstance().mousePressed(event);
	}

	@FXML
	protected void onWindowDragged(MouseEvent event) throws IOException {
		Notification.getIstance().onWindowDragged(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}