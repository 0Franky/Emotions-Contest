package layout.Credits;

import java.io.IOException;

import Title.Title;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreditWindow {

	private CreditWindowController creditWindowController = null;

	private double X, Y;

	private CreditWindow() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(CreditWindow.class.getResource("CreditWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		creditWindowController = loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		// stage.initStyle(StageStyle.TRANSPARENT);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../../Assets/Icon.png")));
		this_stage = stage;

		creditWindowController.lbl_TitleApp.setText(Title.APPLICATION_NAME);
		creditWindowController.lbl_VersionBuild.setText("Build : v" + Title.APPLICATION_VERSION);

		this_stage.show();
	}

	private static Stage this_stage = new Stage();
	private static CreditWindow istance = null; // riferimento all' istanza

	public static CreditWindow getIstance() throws IOException {
		if (istance == null)
			synchronized (CreditWindow.class) {
				if (istance == null) {
					istance = new CreditWindow();
				} else {
					istance.show();
					istance.toFront();
				}
			}
		return istance;
	}

	public void close() {
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

	protected void mousePressed(MouseEvent event) {
		X = this_stage.getX() - event.getScreenX();
		Y = this_stage.getY() - event.getScreenY();
	}

	protected void onWindowDragged(MouseEvent event) {
		this_stage.setX(event.getScreenX() + X);
		this_stage.setY(event.getScreenY() + Y);
	}
}