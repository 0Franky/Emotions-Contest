package layout.PopupWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classes.csv.CSV_WriterBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopupWindow {

	private static Stage this_stage = new Stage();
	private static PopupWindow istance = null; // riferimento all' istanza

	protected String pleasantness = "";
	protected String excitement = "";

	PopupWindowController popupWindowController = null;

	/**
	 * Initializes the root layout.
	 * 
	 * @throws IOException
	 */
	private PopupWindow() throws IOException {
		// Load root layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PopupWindow.class.getResource("PopupWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		popupWindowController = loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		// stage.show();

		this_stage = stage;
	}

	public static PopupWindow getIstance() throws IOException {
		if (istance == null)
			synchronized (PopupWindow.class) {
				if (istance == null) {
					istance = new PopupWindow();
				}
			}
		return istance;
	}

	public void hide() {
		try {
			this_stage.hide();
		} catch (Exception ex) {
			// nothing
		}
	}

	public void show() {
		try {
			this_stage.show();
			writeOpenWindowInDir();
		} catch (Exception ex) {
			// nothing
		}
	}

	public void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			// nothing
		}
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method stub
	 * try { PopupWindow.getIstance().show(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	///////////////////////

	protected void writeResultsInDir() throws IOException {
		if (checkCorrectionParam()) {
			CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in).write(activityToList());
			PopupWindow.getIstance().hide();
		}
	}

	private void writeOpenWindowInDir() throws IOException {
		CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in).write(activityOpenWindowToList());
	}

	private List<String> activityOpenWindowToList() {
		long unixTime = System.currentTimeMillis() / 1000L;

		List<String> data = new ArrayList<String>();
		data.add(Long.toString(unixTime));
		data.add("");
		data.add("");
		data.add("");
		data.add("POPUP_OPENED");
		data.add("");
		return data;
	}

	private List<String> activityToList() {
		long unixTime = System.currentTimeMillis() / 1000L;

		List<String> data = new ArrayList<String>();
		data.add(Long.toString(unixTime));
		data.add(popupWindowController.getActivity());
		data.add(pleasantness);
		data.add(excitement);
		data.add("POPUP_CLOSED");
		data.add(popupWindowController.getNotes());
		return data;
	}

	private boolean checkCorrectionParam() {
		boolean status = true;

		if (popupWindowController.getActivity() == "") {
			status = false;
			MessageBox("Activity not filled!");
		}

		if (pleasantness == "") {
			status = false;
			MessageBox("Pleasantness not checked!");
		}

		if (excitement == "") {
			status = false;
			MessageBox("Excitement not checked!");
		}

		return status;
	}

	private void MessageBox(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText(text);
		alert.showAndWait();
	}
}