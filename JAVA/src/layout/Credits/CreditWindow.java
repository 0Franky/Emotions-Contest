package layout.Credits;

import java.io.IOException;

import Title.Title;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class that defines the CreditWindow object. it is used to manage the
 * application CreditWindow window.
 */
public class CreditWindow {

	/**
	 * Stage this_stage define the layout of the window
	 */
	private static Stage this_stage = new Stage();

	/**
	 * CreditWindow instance is useful to make CreditWindow class "Singleton"
	 */
	private static CreditWindow instance = null;

	/**
	 * Variables used to manage the location of the window on screen
	 */
	private double X, Y;

	/**
	 * CreditWindowController creditWindowController is useful to make
	 * CreditWindowController class "Singleton"
	 */
	private CreditWindowController creditWindowController = null;

	/**
	 * Creates a new CreditWindow
	 *
	 * @return an object of CreditWindow.
	 * @throws IOException Generic I/O error.
	 */
	private CreditWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(CreditWindow.class.getResource("CreditWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		creditWindowController = loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setTitle("Credit & Inforation");

		Platform.setImplicitExit(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				cleanInstance();
			}
		});

		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));
		this_stage = stage;

		creditWindowController.lbl_TitleApp.setText(Title.APPLICATION_NAME);
		creditWindowController.lbl_VersionBuild.setText("Build : v" + Title.APPLICATION_VERSION);

		this_stage.show();
	}

	/**
	 * Return the unique possible instance of the CreditWindow
	 *
	 * @return The CreditWindow.
	 * @throws IOException Generic I/O error.
	 */
	public static CreditWindow getIstance() throws IOException {
		if (instance == null)
			synchronized (CreditWindow.class) {
				if (instance == null) {
					instance = new CreditWindow();
				} else {
					instance.show();
					instance.toFront();
				}
			}
		return instance;
	}

	/**
	 * Close the CreditWindow
	 */
	public void close() {
		try {
			cleanInstance();
			this_stage.close();
		} catch (Exception ex) {
			System.err.println("not Hide");
			ex.printStackTrace();
		}
	}

	/**
	 * Show on screen the CreditWindow
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
	 * Set on front of all opened window the CreditWindow
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
	 * Method util to manage the drag on screen of the CreditWindow
	 * 
	 * @param MouseEvent event.
	 */
	protected void mousePressed(MouseEvent event) {
		X = this_stage.getX() - event.getScreenX();
		Y = this_stage.getY() - event.getScreenY();
	}

	/**
	 * Method util to manage the drag on screen of the CreditWindow
	 * 
	 * @param MouseEvent event.
	 */
	protected void onWindowDragged(MouseEvent event) {
		this_stage.setX(event.getScreenX() + X);
		this_stage.setY(event.getScreenY() + Y);
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 * 
	 * @param Stage stage (the window of the CreditWindow).
	 */
	protected void cleanInstance() {
		instance = null;
	}
}