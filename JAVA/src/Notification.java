import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Notification {

	private AnchorPane rootLayout = new AnchorPane();
	private Stage stage = new Stage();
	private static Notification istance = null; // riferimento all' istanza

	public AnchorPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Initializes the root layout.
	 */
	public Notification() {
		try {

			// Load root layout from fxml file. FXMLLoader loader = new FXMLLoader();
			Parent rootLayout = FXMLLoader.load(getClass().getResource("/layout/Notification.fxml"));

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			stage.setTitle("Notification");
			stage.setResizable(false);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.err.println("Non Creo la notifica ");
			e.printStackTrace();
		}
	}

	public static Notification getIstance() {
		if (istance == null)
			synchronized (Notification.class) {
				if (istance == null)
					istance = new Notification();
			}
		return istance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
