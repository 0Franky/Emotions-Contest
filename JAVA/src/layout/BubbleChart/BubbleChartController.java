package layout.BubbleChart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/**
 * Class that defines the BubbleChartController. it is used to control the
 * JAVAFX GUI of the BubbleChart
 */
public class BubbleChartController implements Initializable {

	/**
	 * List of objects inside the GUI of the BubbleChart
	 */

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	Slider mySlider;

	@FXML
	Label labelSlider;

	@FXML
	protected AnchorPane pnlLine;

	@FXML
	protected Line lv;

	@FXML
	protected Line lo;

	@FXML
	private void initialize() {
	}

	/**
	 * Manage the slider of the BubbleChartWindow
	 *
	 * @throws IOException Generic I/O error.
	 */
	@FXML
	public void onSliderChanged() throws IOException {
		System.out.println("Slider value = " + (int) mySlider.getValue());
		// labelSliderValue = (int) mySlider.getValue();
		BubbleChartWindow.getIstance().updateChart();
		System.out.println("Refresh BubbleChart");
	}

	/**
	 * Empty initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
