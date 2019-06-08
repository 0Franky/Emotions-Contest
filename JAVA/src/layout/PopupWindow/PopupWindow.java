package layout.PopupWindow;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import classes.csv.CSV_WriterBuilder;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PopupWindow {

	private static Stage this_stage = null;
	private static PopupWindow istance = null; // riferimento all' istanza

	protected String pleasantness = "";
	protected String excitement = "";

	private boolean canClose = false;

	private double X, Y;

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
		rootLayout.setStyle(
				".root { -fx-background-color: transparent; -fx-background-radius: 6; }; -fx-background-color: rgba(242, 242, 242, 1); -fx-border-width: 1px 1px 1px 1px; -fx-background-radius: 6;");

		popupWindowController = loader.getController();

		Stage stage = new Stage();

		this_stage = stage;

		Scene scene = new Scene(rootLayout);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Popup survey");
		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../../Assets/Icon.png")));

		Platform.setImplicitExit(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!canClose) {
					event.consume();
				}
			}
		});

		show();

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

	public void close() {
		try {
			canClose = true;
			this_stage.close();
		} catch (Exception ex) {
			// nothing
		}
	}

	private void show() {
		try {
			this_stage.show();
			this_stage.toFront();
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

	protected void mousePressed(MouseEvent event) {
		X = this_stage.getX() - event.getScreenX();
		Y = this_stage.getY() - event.getScreenY();
	}

	protected void onWindowDragged(MouseEvent event) {
		this_stage.setX(event.getScreenX() + X);
		this_stage.setY(event.getScreenY() + Y);
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method stub
	 * try { PopupWindow.getIstance().show(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	///////////////////////

	protected void writeResultsInDir() throws InvocationTargetException, InterruptedException, IOException {
		if (checkCorrectionParam()) {
			CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in).write(activityToList());
			PopupWindow.getIstance().close();
		}
	}

	private void writeOpenWindowInDir() throws InvocationTargetException, InterruptedException {
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
		alert.initOwner(this_stage);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}
