package layout.Notification;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Notification {

	private Notification() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Notification.class.getResource("Notification.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		centerStage(stage, stage.getWidth(), stage.getHeight());
		stage.show();

		this_stage = stage;
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
		if (screenBounds.getWidth() > 1990 && screenBounds.getHeight() > 1000) {
			stage.setX((screenBounds.getWidth() - 520));
			stage.setY((screenBounds.getHeight() - 1000));
		} else {
			stage.setX((screenBounds.getWidth() - width) / 2);
			stage.setY((screenBounds.getHeight() - height) / 2);
		}
	}
}
