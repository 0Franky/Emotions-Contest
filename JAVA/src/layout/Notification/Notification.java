package layout.Notification;

import java.io.IOException;

import javafx.application.Platform;
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

public class Notification {

	private double X, Y;

	private boolean canClose = false;

	private Notification() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Notification.class.getResource("Notification.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
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

		this_stage = stage;
		centerStage(this_stage, this_stage.getWidth(), this_stage.getHeight());
	}

	private static Stage this_stage = new Stage();
	private static Notification instance = null; // riferimento all' istanza

	public static Notification getIstance() throws IOException {
		if (instance == null)
			synchronized (Notification.class) {
				if (instance == null) {
					instance = new Notification();
				}
			}
		return instance;
	}

	public void close() {
		try {
			canClose = true;
			this_stage.close();
		} catch (Exception ex) {
			System.err.println("not Hide");
			ex.printStackTrace();
		}
	}

	public void show() {
		try {
			this_stage.show();
		} catch (Exception ex) {
			System.err.println("not Show");
			ex.printStackTrace();
		}
	}

	public void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			System.err.println("not Front");
			ex.printStackTrace();
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

	private void centerStage(Stage stage, double width, double height) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		double screenWidth = screenBounds.getWidth();
		// double percentWeight = (double) 34.5 / (double) 100;

		// double screenHeight = screenBounds.getHeight();
		// double percentHeight = (double) 86.4 / (double) 100;

		this_stage.show();

		stage.setX((screenWidth - stage.getWidth()) - 10);
		stage.setY(30);

	}

	protected void cleanInstance() {
		instance = null;
	}
}
