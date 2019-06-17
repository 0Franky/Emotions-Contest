package layout.BubbleChart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class BubbleChartController implements Initializable {

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	@FXML
	Slider mySlider;

	@FXML
	Label labelSlider;

	private int labelSliderValue;

	@FXML
	private void initialize() {
	}

	@FXML
	public void onSliderChanged() throws IOException {
		labelSlider.textProperty().bind(Bindings.format("%.0f", mySlider.valueProperty()));
		System.out.println("Slider value = " + mySlider.getValue());
		labelSliderValue = (int) mySlider.getValue();
		BubbleChartWindow.getIstance().populateChart(labelSliderValue);
		System.out.println("Refresh BubbleChart");

	}

	public int getlabelSliderValue() {
		return labelSliderValue;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}