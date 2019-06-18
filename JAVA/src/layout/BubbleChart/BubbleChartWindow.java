package layout.BubbleChart;

import java.io.IOException;
import java.util.List;

import classes.DataChart;
import classes.database.SQLiteConnection;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BubbleChartWindow {

	private static Stage this_stage = new Stage();
	private static BubbleChartWindow instance = null; // riferimento all' istanza

	private BubbleChartController bubbleChartController = null;

	private BubbleChart<Number, Number> chart = null;

	private BubbleChartWindow() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BubbleChartWindow.class.getResource("BubbleChartWindow.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();

		bubbleChartController = loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(rootLayout);
		stage.setScene(scene);
		stage.setTitle("Retrospection");
		stage.setResizable(false);

		Platform.setImplicitExit(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				cleanInstance();
			}
		});

		stage.setAlwaysOnTop(true);
		this_stage = stage;

		chart = (BubbleChart) scene.lookup("#chart");

		NumberAxis xAxis = (NumberAxis) chart.getXAxis();
		NumberAxis yAxis = (NumberAxis) chart.getYAxis();

		stage.setTitle("Bubble Chart");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/Icon.png")));

		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(0);
		xAxis.setUpperBound(10);
		xAxis.setTickUnit(1);

		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(10);
		yAxis.setTickUnit(1);

		xAxis.setLabel("Calm");
		yAxis.setLabel("Excited");
		chart.setTitle("Feeling chart");

		bubbleChartController.labelSlider.textProperty()
				.bind(Bindings.format("%.0f", bubbleChartController.mySlider.valueProperty()));

		/* PRELEVA DATI DAL DB */
		populateChart(0);
		// stage.show();
		/* demoAddBubble(); */

		this_stage = stage;
		// this_stage.show();
		/* System.out.println("You shoud see me"); */

	}

	public static BubbleChartWindow getIstance() throws IOException {
		if (instance == null)
			synchronized (BubbleChartWindow.class) {
				if (instance == null) {
					instance = new BubbleChartWindow();
				} else {
					instance.show();
					instance.toFront();
				}
			}
		return instance;
	}

	public static boolean isIstanceNULL() throws IOException {
		return (instance == null);
	}

	public void addBubble(Number xValue, Number yValue, Number weight) {
		XYChart.Series bubble = new XYChart.Series();
		// bubble.setName("Anger");
		bubble.getData().add(new XYChart.Data(xValue, yValue, weight));

		chart.getData().add(bubble);
	}

	public void close() {
		try {
			cleanInstance();
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

	public void populateChart(int day) {
		chart.getData().clear();
		List<DataChart> data = SQLiteConnection.getDataForChart(day);
		for (DataChart bubble : data) {
			addBubble(bubble.getValence(), bubble.getArousal(), (float) bubble.getWeight() / 8);
		}
	}

	public void updateChart() {
		chart.getData().clear();
		populateChart((int) bubbleChartController.mySlider.getValue());
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
