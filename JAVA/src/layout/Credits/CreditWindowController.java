package layout.Credits;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CreditWindowController {

	/**
	 * List of objects inside the GUI of the CreditWindow
	 */

	@FXML
	private Hyperlink GitHub0Franky;

	@FXML
	private Hyperlink GitHubChrism1c;

	@FXML
	private Hyperlink GitHubLink;

	@FXML
	private Button buttonOk;

	@FXML
	ImageView My;

	@FXML
	Label lbl_TitleApp;

	@FXML
	Label lbl_VersionBuild;

	@FXML
	private Text txt_AppDescription;

	/**
	 * Open the Browser on the link "https://github.com/0Franky/Emotions-Contest"
	 */
	@FXML
	private void gotoPage() {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI("https://github.com/0Franky/Emotions-Contest");
			desktop.browse(oURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the Browser on the link "https://github.com/Chrism1c"
	 */
	@FXML
	private void gotoChrism1c() {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI("https://github.com/Chrism1c");
			desktop.browse(oURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the Browser on the link "https://github.com/0Franky"
	 */
	@FXML
	private void goto0Franky() {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI("https://github.com/0Franky");
			desktop.browse(oURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manage the click on "OK" button and close the CreditWindow
	 * 
	 * @param ActionEvent event
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	private void pressedOk(ActionEvent event) throws IOException {
		CreditWindow.getIstance().close();
	}

	// What a cat is happening?
	@FXML
	private void miao() {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.browse(new URI("https://www.youtube.com/watch?v=-i1esxAZqUc"));
			CreditWindow.getIstance().close();
		} catch (Exception e) {
		}
	}
}
