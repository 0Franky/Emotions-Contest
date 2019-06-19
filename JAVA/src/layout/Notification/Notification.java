package layout.Notification;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Class that defines the notification object. it is used to manage the
 * application notification window.
 */
public class Notification {

	/**
	 * Stage this_stage define the layout of the window
	 */
	private static Stage this_stage = new Stage();

	/**
	 * Notification instance is useful to make Notification class "Singleton"
	 */
	private static Notification instance = null;

	/**
	 * NotificationController is useful to manage Slider
	 */
	private NotificationController notificationController = null;

	/**
	 * Variables used to manage the location of the window on screen
	 */
	private double X, Y;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;

	/**
	 * Creates a new Notification
	 *
	 * @return an object of Notification.
	 * @throws IOException Generic I/O error.
	 */
	private Notification() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Notification.class.getResource("Notification.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		notificationController = loader.getController();

		rootLayout.setStyle("-fx-border-color: gray; -fx-border-width: 1px 1px 1px 1px");
		Stage stage = new Stage();

		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Request survey");

		Platform.setImplicitExit(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!canClose) {
					event.consume();
				} else {
					cleanInstance();
				}
			}
		});

		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));
		notificationController.labelSlider.textProperty()
				.bind(Bindings.format("%.0f", notificationController.mySlider.valueProperty()));

		this_stage = stage;
		centerStage(this_stage);
	}

	/**
	 * Return the unique possible instance of the Notification
	 *
	 * @return The Notification.
	 * @throws IOException Generic I/O error.
	 */
	public static Notification getIstance() throws IOException {
		if (instance == null)
			synchronized (Notification.class) {
				if (instance == null) {
					instance = new Notification();
				}
			}
		return instance;
	}

	/**
	 * Set the Stage (Window) of the Notification on right-upper corner of screen
	 *
	 * @param Stage stage (the window of the Notification).
	 */
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		double screenWidth = screenBounds.getWidth();
		// double percentWeight = (double) 34.5 / (double) 100;
		// double screenHeight = screenBounds.getHeight();
		// double percentHeight = (double) 86.4 / (double) 100;

		this_stage.show();

		stage.setX((screenWidth - stage.getWidth()) - 10);
		stage.setY(30);
	}

	/**
	 * Close the Notification
	 */
	public void close() {
		try {
			canClose = true;
			this_stage.close();
		} catch (Exception ex) {
			System.err.println("not Hide");
			ex.printStackTrace();
		}
	}

	/**
	 * Show on screen the Notification
	 */
	public void show() {
		try {
			this_stage.show();
		} catch (Exception ex) {
			System.err.println("not Show");
			ex.printStackTrace();
		}
	}

	/**
	 * Set on front of all opened window the Notification
	 */
	public void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			System.err.println("not Front");
			ex.printStackTrace();
		}
	}

	/**
	 * Method util to manage the drag on screen of the Notification
	 * 
	 * @param MouseEvent event.
	 */
	protected void mousePressed(MouseEvent event) {
		X = this_stage.getX() - event.getScreenX();
		Y = this_stage.getY() - event.getScreenY();
	}

	/**
	 * Method util to manage the drag (release) on screen of the Notification
	 * 
	 * @param MouseEvent event.
	 */
	protected void onWindowDragged(MouseEvent event) {
		this_stage.setX(event.getScreenX() + X);
		this_stage.setY(event.getScreenY() + Y);
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 */
	protected void cleanInstance() {
		instance = null;
	}
}
