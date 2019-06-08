package layout.Notification;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification {
	private double xOffset = 0;
	private double yOffset = 0;

	private Notification() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Notification.class.getResource("Notification.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		Stage stage = new Stage();

		// grab your root here
		rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		// move around here
		rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});

		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		centerStage(stage, stage.getWidth(), stage.getHeight());
		this_stage = stage;
		this_stage.show();
	}

	private static Stage this_stage = new Stage();
	private static Notification istance = null; // riferimento all' istanza

	public static Notification getIstance() throws IOException {
		if (istance == null)
			synchronized (Notification.class) {
				if (istance == null) {
					istance = new Notification();
				}
			}
		return istance;
	}

	public void hide() {
		try {
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

	private void centerStage(Stage stage, double width, double height) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		// FULL HD // 1920x1080
		if (screenBounds.getWidth() > 1900 && screenBounds.getHeight() > 1000) {
			stage.setX((screenBounds.getWidth() - 510));
			stage.setY((screenBounds.getHeight() - 1000));
		} else {
			stage.setX((screenBounds.getWidth() - width) / 2);
			stage.setY((screenBounds.getHeight() - height) / 2);
		}
	}

}
