package layout.BubbleChart;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BubbleChartWindow extends Application {

	private BubbleChart<Number, Number> chart = null;

	@Override
	public void start(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BubbleChartWindow.class.getResource("BubbleChartWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.show();

		chart = (BubbleChart) scene.lookup("#chart");

		NumberAxis xAxis = (NumberAxis) chart.getXAxis();
		NumberAxis yAxis = (NumberAxis) chart.getYAxis();

		stage.setTitle("Bubble Chart Sample");

		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(0);
		xAxis.setUpperBound(9);
		xAxis.setTickUnit(1);

		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(9);
		yAxis.setTickUnit(1);

		xAxis.setLabel("Calm");
		yAxis.setLabel("Excited");
		chart.setTitle("Feeling during the day");

		// demoAddBubble();
	}

	public void addBubble(Number xValue, Number yValue, Number weight) {
		XYChart.Series bubble = new XYChart.Series();
		// bubble.setName("Anger");
		bubble.getData().add(new XYChart.Data(xValue, yValue, weight));

		chart.getData().add(bubble);
	}

	/*
	 * public static void main(String[] args) { launch(args); }
	 * 
	 * private void demoAddBubble() { for (int i = 0; i < 10; i++) { double num =
	 * ((Math.random() * ((0.5 - 0.1) + 1)) + 0.1) % 1; addBubble((int)
	 * (Math.random() * ((9 - 1) + 1)) + 1, (int) (Math.random() * ((9 - 1) + 1)) +
	 * 1, num); //System.out.println(num); } }
	 */
}
