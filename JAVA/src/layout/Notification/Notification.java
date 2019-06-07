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
		// stage.show();

		this_stage = stage;
		this_stage.show();
	}

	private static Stage this_stage = new Stage();
	private static Notification istance = null; // riferimento all' istanza

	public static Notification getIstance() throws IOException {
		System.out.println("Sono in getIstance");
		if (istance == null)
			synchronized (Notification.class) {
				if (istance == null) {
					System.out.println("creo la Notifica");
					istance = new Notification();
				}
			}
		System.out.println("return istance");
		return istance;
	}

	public void hide() {
		try {
			this_stage.close();
		} catch (Exception ex) {
			System.err.println("not Hide");
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
		}
	}
}
