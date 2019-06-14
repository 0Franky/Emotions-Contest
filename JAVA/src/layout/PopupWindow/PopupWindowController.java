package layout.PopupWindow;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.AppTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PopupWindowController implements Initializable {

	@FXML
	protected Button btn_Done;

	@FXML
	protected ComboBox<String> lbl_Activity;

	@FXML
	protected TextArea txt_Notes;

	@FXML
	protected AnchorPane pnl_window;

	@FXML
	private void btn_Done_OnAction(ActionEvent event)
			throws IOException, InvocationTargetException, InterruptedException {
		// System.out.println("You clicked postponeButton");
		PopupWindow.getIstance().writeResultsInDir();
		AppTimer.getIstance().startTimer(60);
	}

	@FXML
	private void cb_Pleasantness_Checking(ActionEvent event) {
		RadioButton cb = (RadioButton) event.getSource();
		try {
			PopupWindow.getIstance().pleasantness = cb.getText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void cb_Excitement_Checking(ActionEvent event) {
		RadioButton cb = (RadioButton) event.getSource();
		try {
			PopupWindow.getIstance().excitement = cb.getText();
			// System.out.append(PopupWindow.getIstance().excitement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void mousePressed(MouseEvent event) throws IOException {
		PopupWindow.getIstance().mousePressed(event);
	}

	@FXML
	protected void onWindowDragged(MouseEvent event) throws IOException {
		PopupWindow.getIstance().onWindowDragged(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	////////////////////////////////////

	protected String getNotes() {
		return txt_Notes.getText();
	}

	protected String getActivity() {
		String text = "";
		if (lbl_Activity.getValue() != null) {
			text = lbl_Activity.getValue().toString();
		}
		return text;
	}
}
