package layout.PopupWindow;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import classes.AppTimer;
import classes.Synchronizer;
import classes.TimeConverter;
import classes.Tuple;
import classes.database.SQLiteConnection;
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

	private static Stage this_stage = new Stage();
	private static PopupWindow instance = null; // riferimento all' istanza

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
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));

		loadActivityItems();

		this_stage = stage;

		Platform.setImplicitExit(false);
		this_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!canClose) {
					event.consume();
				} else {
					cleanInstance();
				}
			}
		});
		this_stage.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!canClose) {
					event.consume();
				} else {
					cleanInstance();
				}
			}
		});

		show();
	}

	public static PopupWindow getIstance() throws IOException {
		if (instance == null)
			synchronized (PopupWindow.class) {
				if (instance == null) {
					instance = new PopupWindow();
				}
			}

		this_stage.show();

		return instance;
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
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						writeOpenWindowInDir();
					} catch (InvocationTargetException e) {
						System.err.println("InvocationTargetException in writeOpenWindowInDir() in show()");
						e.printStackTrace();
					} catch (InterruptedException e) {
						System.err.println("InterruptedException in writeOpenWindowInDir() in show()");
						e.printStackTrace();
					}
				};
			});
			// writeOpenWindowInDir();
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

	private void loadActivityItems() {
		popupWindowController.lbl_Activity.getItems().removeAll(popupWindowController.lbl_Activity.getItems());
		popupWindowController.lbl_Activity.getItems().addAll("Coding", "Bugfixing", "Testing", "Design", "Meeting",
				"Email", "Helping", "Networking", "Learning", "Administrative tasks", "Documentation");
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
			SQLiteConnection.addRow(getActivityToTuple().toArray());
			SQLiteConnection.addRowToSync(getActivityToTuple().toArray());
			Synchronizer.sync();
			AppTimer.getIstance().startTimer(60); // Avvio Timer a 60
			PopupWindow.getIstance().close();
		}
	}

	private void writeOpenWindowInDir() throws InvocationTargetException, InterruptedException {
		SQLiteConnection.addRow(getActivityOpenWindowToTuple().toArray());
		SQLiteConnection.addRowToSync(getActivityOpenWindowToTuple().toArray());
		Synchronizer.sync();
	}

	private Tuple getActivityOpenWindowToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())), "", "", "",
				"POPUP_OPENED", "");
	}

	private Tuple getActivityToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())),
				popupWindowController.getActivity(), pleasantness, excitement, "POPUP_CLOSED",
				popupWindowController.getNotes());
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
		alert.setTitle("Information Missed!");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(this_stage);
		alert.showAndWait();
	}

	protected void cleanInstance() {
		instance = null;
	}
}
