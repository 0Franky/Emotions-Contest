package layout.BubbleChart;

import java.io.IOException;
import java.util.List;

import classes.DataChart;
import classes.database.SQLiteConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BubbleChartWindow {

	private BubbleChart<Number, Number> chart = null;
	private static Stage this_stage = new Stage();

	public BubbleChartWindow() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BubbleChartWindow.class.getResource("BubbleChartWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);

		chart = (BubbleChart) scene.lookup("#chart");

		NumberAxis xAxis = (NumberAxis) chart.getXAxis();
		NumberAxis yAxis = (NumberAxis) chart.getYAxis();

		stage.setTitle("Bubble Chart Sample");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../../Assets/Icon.png")));

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

		// PRELEVA DATI DAL DB //
		populateChart();
		// demoAddBubble();

		this_stage = stage;
		this_stage.show();
		// System.out.println("You shoud see me");

	}

	private static BubbleChartWindow instance = null; // riferimento all' istanza

	public static BubbleChartWindow getIstance() throws IOException {
		if (instance == null)
			synchronized (BubbleChartWindow.class) {
				if (instance == null) {
					instance = new BubbleChartWindow();
				}
			}
		// System.out.println("Return istance");
		this_stage.show();
		return instance;
	}

	public void addBubble(Number xValue, Number yValue, Number weight) {
		XYChart.Series bubble = new XYChart.Series();
		// bubble.setName("Anger");
		bubble.getData().add(new XYChart.Data(xValue, yValue, weight));

		chart.getData().add(bubble);
	}

	private void close() {
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

	private void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			System.err.println("not Front");
			ex.printStackTrace();
		}
	}

	protected void cleanInstance() {
		instance = null;
	}

	private void populateChart() {
		List<DataChart> data = SQLiteConnection.getDataForChart();
		for (DataChart bubble : data) {
			addBubble(bubble.getValence(), bubble.getArousal(), (float) bubble.getWeight() / 8);
		}
	}

	// public static void main(String[] args) {
	// launch(args); /* demoAddBubble(); */
	// }

	/*
	 * private void demoAddBubble() { for (int i = 0; i < 10; i++) { double num =
	 * ((Math.random() * ((0.5 - 0.1) + 1)) + 0.1) % 1; addBubble((int)
	 * (Math.random() * ((9 - 1) + 1)) + 1, (int) (Math.random() * ((9 - 1) + 1)) +
	 * 1, num); //System.out.println(num); } }
	 */

}
