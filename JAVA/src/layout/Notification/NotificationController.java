package layout.Notification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.AppTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import layout.PopupWindow.PopupWindow;

/**
 * Class that defines the NotificationController. it is used to control the
 * JAVAFX GUI of the Notification
 */
public class NotificationController implements Initializable {

	/**
	 * List of objects inside the GUI of the Notification
	 */

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane AnchorPaneNotification;

	@FXML
	private Button dismissButton;

	@FXML
	private Button postponeButton;

	@FXML
	Slider mySlider;

	@FXML
	Label labelSlider;

	@FXML
	private void initialize() {
	}

	/**
	 * Manage the click (ActionEvent) on "postpone" button
	 *
	 * @param ActionEvent event
	 * @throws IOException           Generic I/O error.
	 * @throws NumberFormatException Generic NumberFormat error.
	 * @throws InterruptedException  Generic Interruption error.
	 */
	@FXML
	private void postponeAction(ActionEvent event) throws IOException, NumberFormatException, InterruptedException {
		String Time = labelSlider.getText();
		System.out.println("Minuti da passare al Timer: " + Time);
		Notification.getIstance().close();
		AppTimer.getIstance().startTimer(Integer.parseInt(Time)); // Set Timer //
		Notification.getIstance().cleanInstance(); // <<-->> CANCEL THE INSTANCE <<-->>
	}

	/**
	 * Opens the popUp-Window and close the Notification
	 */
	@FXML
	public void openPopUp() throws IOException {
		Notification.getIstance().close();
		PopupWindow.getIstance();
	}

	/**
	 * Manage the click (ActionEvent) on "dismiss" button
	 *
	 * @param ActionEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	private void dismissAction(ActionEvent event) throws IOException {
		Notification.getIstance().close();
	}

	/**
	 * Manage the press (MouseEvent) of the Notification
	 *
	 * @param MouseEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	public void mousePressed(MouseEvent event) throws IOException {
		Notification.getIstance().mousePressed(event);
	}

	/**
	 * Manage the Drag (MouseEvent) of the Notification
	 *
	 * @param MouseEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	protected void onWindowDragged(MouseEvent event) throws IOException {
		Notification.getIstance().onWindowDragged(event);
	}

	/**
	 * Empty initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
}
