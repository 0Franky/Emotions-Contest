package layout.Notification;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
		stage.show();

		this_stage = stage;
	}

	private static Stage this_stage = null;
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
			this_stage.hide();
		} catch (Exception ex) {
			// nothing
		}
	}

	public void show() {
		try {
			this_stage.show();
		} catch (Exception ex) {
			// nothing
		}
	}
}
