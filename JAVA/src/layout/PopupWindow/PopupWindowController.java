package layout.PopupWindow;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Class that defines the PopupWindowController. it is used to control the
 * JAVAFX GUI of the PopupWindow
 */
public class PopupWindowController implements Initializable {

	/**
	 * List of objects inside the GUI of the PopupWindow
	 */

	@FXML
	protected Button btn_Done;

	@FXML
	protected ComboBox<String> lbl_Activity;

	@FXML
	protected TextArea txt_Notes;

	@FXML
	protected AnchorPane pnl_window;

	@FXML
	protected GridPane g1;

	@FXML
	protected GridPane g2;

	/**
	 * Manage the click (ActionEvent) on "Done" button starting the write of results
	 * on DB and Sheet
	 *
	 * @param ActionEvent event
	 * @throws IOException               Generic I/O error.
	 * @throws InvocationTargetException Generic InvocationTarget error.
	 * @throws InterruptedException      Generic Interruption error.
	 */
	@FXML
	private void btn_Done_OnAction(ActionEvent event)
			throws IOException, InvocationTargetException, InterruptedException {
		PopupWindow.getIstance().writeResultsInDir();
	}

	/**
	 * Check the click (ActionEvent) on a Pleasantness radio button
	 *
	 * @param ActionEvent event
	 */
	@FXML
	private void cb_Pleasantness_Checking(ActionEvent event) {
		RadioButton cb = (RadioButton) event.getSource();
		try {
			PopupWindow.getIstance().pleasantness = cb.getText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check the click (ActionEvent) on a Excitement radio button
	 *
	 * @param ActionEvent event
	 */
	@FXML
	private void cb_Excitement_Checking(ActionEvent event) {
		RadioButton cb = (RadioButton) event.getSource();
		try {
			PopupWindow.getIstance().excitement = cb.getText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manage the press (MouseEvent) of the PopupWindow
	 *
	 * @param MouseEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	public void mousePressed(MouseEvent event) throws IOException {
		PopupWindow.getIstance().mousePressed(event);
	}

	/**
	 * Manage the Drag (MouseEvent) of the PopupWindow
	 *
	 * @param MouseEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	protected void onWindowDragged(MouseEvent event) throws IOException {
		PopupWindow.getIstance().onWindowDragged(event);
	}

	/**
	 * Empty initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	/**
	 * return the content of txt_Notes
	 * 
	 * @return txt_Notes.getText
	 */
	protected String getNotes() {
		return txt_Notes.getText();
	}

	/**
	 * return the content of lbl_Activity.getValue()
	 * 
	 * @return text
	 */
	protected String getActivity() {
		String text = "";
		if (lbl_Activity.getValue() != null) {
			text = lbl_Activity.getValue().toString();
		}
		return text;
	}
}
