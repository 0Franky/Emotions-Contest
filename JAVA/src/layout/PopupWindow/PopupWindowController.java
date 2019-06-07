package layout.PopupWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class PopupWindowController implements Initializable {

	@FXML
	protected Button btn_Done;

	@FXML
	protected ComboBox<String> lbl_Activity;

	@FXML
	protected TextArea txt_Notes;

	@FXML
	private void btn_Done_OnAction(ActionEvent event) throws IOException {
		// System.out.println("You clicked postponeButton");
		PopupWindow.getIstance().writeResultsInDir();
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