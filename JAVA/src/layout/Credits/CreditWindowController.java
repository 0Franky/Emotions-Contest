package layout.Credits;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CreditWindowController {

	@FXML
	private Hyperlink GitHub0Franky;

	@FXML
	private Hyperlink GitHubChrism1c;

	@FXML
	private Hyperlink GitHubLink;

	@FXML
	private Button buttonOk;

	@FXML
	Label lbl_TitleApp;

	@FXML
	Label lbl_VersionBuild;

	@FXML
	Text txt_AppDescription;

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

	@FXML
	private void pressedOk(ActionEvent event) throws IOException {
		// System.out.println("You clicked ok");
		// Chiudi Finestra //
		CreditWindow.getIstance().close();
	}

}
