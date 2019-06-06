import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import layout.Notification.Notification;

public class PopupWindow {

	private AnchorPane rootLayout = new AnchorPane();
	private Stage subStage = new Stage();

	public Stage getSubStage() {
		return subStage;
	}

	public void setSubStage(Stage subStage) {
		this.subStage = subStage;
	}

	private static PopupWindow istance = null; // riferimento all' istanza

	public AnchorPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	/**
	 * Initializes the root layout.
	 */

	public PopupWindow() {
		try {
			// Load root layout from fxml file.
			Parent rootLayout = FXMLLoader.load(getClass().getResource("/layout/PopupWindow.fxml"));

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			subStage.setTitle("PopupWindow");
			subStage.setScene(scene);
			// subStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PopupWindow getIstance() {
		if (istance == null)
			synchronized (Notification.class) {
				if (istance == null)
					istance = new PopupWindow();
			}
		return istance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
