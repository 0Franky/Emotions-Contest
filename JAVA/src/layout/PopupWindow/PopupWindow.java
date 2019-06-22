package layout.PopupWindow;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import Title.Title;
import classes.AppTimer;
import classes.Synchronizer;
import classes.TimeConverter;
import classes.Tuple;
import classes.database.SQLiteConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import layout.BubbleChart.BubbleChartWindow;

/**
 * Class that defines the PopupWindow object. it is used to manage the
 * application PopupWindow.
 */
public class PopupWindow {

	/**
	 * Stage this_stage define the layout of the window
	 */
	private static Stage this_stage = new Stage();

	/**
	 * Notification instance is useful to make PopupWindow class "Singleton"
	 */
	private static PopupWindow instance = null;

	/**
	 * PopupWindowController is useful to control popUp
	 */
	private PopupWindowController popupWindowController = null;

	/**
	 * Variables used to manage the location of the window on screen
	 */
	private double X, Y;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;

	/**
	 * String used set the pleasantness
	 */
	protected String pleasantness = "";

	/**
	 * String used set the excitement
	 */
	protected String excitement = "";

	/**
	 * String used set the dominance
	 */
	protected String dominance = "";

	/**
	 * Creates a new PopupWindow
	 *
	 * @return an object of PopupWindow.
	 * @throws IOException Generic I/O error.
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
		loadProductivityItems();

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
		dimCorrection();
	}

	/**
	 * Makes PopupWindow's layout fluid on different screen resolutions
	 */
	private void dimCorrection() {
		GridPane grid = popupWindowController.g1;
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		double screenWidth = screenBounds.getWidth();
		double screenHeight = screenBounds.getHeight();

		for (Node node : grid.getChildren()) {
			ImageView img = (ImageView) node;

			// img.fitWidthProperty().bind(grid.widthProperty());
			img.fitHeightProperty().bind(grid.heightProperty());
		}

		grid = popupWindowController.g2;

		for (Node node : grid.getChildren()) {
			ImageView img = (ImageView) node;

			// img.fitWidthProperty().bind(grid.widthProperty());
			img.fitHeightProperty().bind(grid.heightProperty());
		}

		grid = popupWindowController.g3;

		for (Node node : grid.getChildren()) {
			ImageView img = (ImageView) node;

			// img.fitWidthProperty().bind(grid.widthProperty());
			img.fitHeightProperty().bind(grid.heightProperty());
		}

		if (this_stage.getHeight() > screenHeight) {
			this_stage.setHeight(screenHeight);
			this_stage.setWidth((924 * screenHeight) / 813);
		}

		if (this_stage.getWidth() > screenWidth) {
			this_stage.setWidth(screenWidth);
		}

		this_stage.setX((screenWidth / 2) - (this_stage.getWidth() / 2));
		this_stage.setY((screenHeight / 2) - (this_stage.getHeight() / 2));
	}

	/**
	 * Return the unique possible instance of the PopupWindow
	 *
	 * @return The PopupWindow.
	 * @throws IOException Generic I/O error.
	 */
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

	/**
	 * Close the PopupWindow
	 */
	public void close() {
		try {
			canClose = true;
			this_stage.close();
		} catch (Exception ex) {
			// nothing
		}
	}

	/**
	 * Show on screen the PopupWindow
	 */
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
		} catch (Exception ex) {
			System.err.println("Catch show PopupWindow");
		}
	}

	/**
	 * Set on front of all opened window the PopupWindow
	 */
	public void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			System.err.println("Catch toFront PopupWindow");
		}
	}

	private void loadActivityItems() {
		popupWindowController.lbl_Activity.getItems().removeAll(popupWindowController.lbl_Activity.getItems());
		popupWindowController.lbl_Activity.getItems().addAll("Coding", "Bugfixing", "Testing", "Design", "Meeting",
				"Email", "Helping", "Networking", "Learning", "Administrative tasks", "Documentation");
	}

	private void loadProductivityItems() {
		popupWindowController.lbl_Productivity.getItems().removeAll(popupWindowController.lbl_Productivity.getItems());
		popupWindowController.lbl_Productivity.getItems().addAll("very low", "below average", "average",
				"above aver-age", "very high");
	}

	/**
	 * Method util to manage the drag on screen of the PopupWindow
	 * 
	 * @param MouseEvent event.
	 */
	protected void mousePressed(MouseEvent event) {
		X = this_stage.getX() - event.getScreenX();
		Y = this_stage.getY() - event.getScreenY();
	}

	/**
	 * Method util to manage the drag (release) on screen of the PopupWindow
	 * 
	 * @param MouseEvent event.
	 */
	protected void onWindowDragged(MouseEvent event) {
		this_stage.setX(event.getScreenX() + X);
		this_stage.setY(event.getScreenY() + Y);
	}

	/**
	 * Write results of the questionnaire on database, it also starts sync for
	 * online Sheet and start Timer, then it close PopupWindow
	 * 
	 * @throws IOException               Generic I/O error.
	 * @throws InvocationTargetException Generic InvocationTarget error.
	 * @throws InterruptedException      Generic Interruptederror.
	 */
	protected void writeResultsInDir() throws InvocationTargetException, InterruptedException, IOException {
		if (checkCorrectionParam()) {
			SQLiteConnection.addRow(getActivityToTuple().toArray()); // write on database //
			SQLiteConnection.addRowToSync(getActivityToTuple().toArray());
			Synchronizer.sync(); // Syncronize to write online //
			AppTimer.getIstance().startTimer(60); // Start Timer at 60 minutes //
			if (BubbleChartWindow.isIstanceNULL() == false) {
				BubbleChartWindow.getIstance().updateChart(); // Refresh BubbleChartWindow when it is already opened //
				System.out.println("Refresh BubbleChart");
			}
			PopupWindow.getIstance().close();
		}
	}

	/**
	 * Write open_popUp on database and start sync for online Sheet
	 * 
	 * @throws InvocationTargetException, InterruptedException
	 */
	private void writeOpenWindowInDir() throws InvocationTargetException, InterruptedException {
		SQLiteConnection.addRow(getActivityOpenWindowToTuple().toArray());
		SQLiteConnection.addRowToSync(getActivityOpenWindowToTuple().toArray());
		Synchronizer.sync();
	}

	/**
	 * Create a new Tuple for "POPUP_OPENED" event
	 * 
	 * @return Tuple
	 */
	private Tuple getActivityOpenWindowToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())), "", "", "", "", "", "",
				"POPUP_OPENED", "");
	}

	/**
	 * Create a new Tuple for "POPUP_CLOSED" event
	 * 
	 * @return Tuple
	 */
	private Tuple getActivityToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())),
				popupWindowController.getActivity(), pleasantness, excitement, dominance,
				popupWindowController.getProductivity(), Title.USER_ID, "POPUP_CLOSED",
				popupWindowController.getNotes());
	}

	/**
	 * Verify if all PopupWindow's fields are completed
	 * 
	 * @return Tuple
	 */
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

		if (dominance == "") {
			status = false;
			MessageBox("Dominance not checked!");
		}

		if (popupWindowController.getProductivity() == "") {
			status = false;
			MessageBox("Productivity not filled!");
		}

		return status;
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 * 
	 * @return Tuple
	 */
	private void MessageBox(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Information Missed!");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(this_stage);
		alert.showAndWait();
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 * 
	 */
	protected void cleanInstance() {
		instance = null;
	}
}
