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

/**
 * Class that defines the BubbleChartWindow object. it is used to manage the
 * application BubbleChartWindow window.
 */
public class BubbleChartWindow {

	/**
	 * Stage this_stage define the layout of the window
	 */
	private static Stage this_stage = new Stage();

	/**
	 * BubbleChartWindow instance is useful to make BubbleChartWindow class
	 * "Singleton"
	 */
	private static BubbleChartWindow instance = null;

	/**
	 * BubbleChartController bubbleChartController is useful to make
	 * BubbleChartController class "Singleton"
	 */
	private BubbleChartController bubbleChartController = null;

	/**
	 * Define a Chart of type BubbleChart
	 */
	private BubbleChart<Number, Number> chart = null;

	/**
	 * Creates a new BubbleChartWindow
	 *
	 * @return an object of BubbleChartWindow.
	 * @throws IOException Generic I/O error.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

		populateChart(0); /* GETS DATA FROM DB */

		this_stage = stage;
	}

	/**
	 * Return the unique possible instance of the BubbleChartWindow
	 *
	 * @return The BubbleChartWindow.
	 * @throws IOException Generic I/O error.
	 */
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

	/**
	 * Check if the istance is null
	 *
	 * @return boolean result of (instance == null)
	 * @throws IOException Generic I/O error.
	 */
	public static boolean isIstanceNULL() throws IOException {
		return (instance == null);
	}

	/**
	 * Adds a Bubble to the chart
	 * 
	 * @params Number xValue, Number yValue, Number weight (coordinates and value of
	 *         the bubble)
	 * @throws IOException Generic I/O error.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addBubble(Number xValue, Number yValue, Number weight) {
		XYChart.Series bubble = new XYChart.Series();
		// bubble.setName("Anger");
		bubble.getData().add(new XYChart.Data(xValue, yValue, weight));

		chart.getData().add(bubble);
	}

	/**
	 * Close the BubbleChartWindow
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
	 * Show on screen the BubbleChartWindow
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
	 * Set on front of all opened window the BubbleChartWindow
	 */
	private void toFront() {
		try {
			this_stage.toFront();
		} catch (Exception ex) {
			System.err.println("not Front");
			ex.printStackTrace();
		}
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 * 
	 * @param Stage stage (the window of the BubbleChartWindow).
	 */
	protected void cleanInstance() {
		instance = null;
	}

	/**
	 * Populate the Chart adding all bubbles
	 * 
	 * @param int day (to set the range to display on the chart [day,today]).
	 */
	public void populateChart(int day) {
		chart.getData().clear();
		List<DataChart> data = SQLiteConnection.getDataForChart(day);
		for (DataChart bubble : data) {
			addBubble(bubble.getValence(), bubble.getArousal(), (float) bubble.getWeight() / 8);
		}
	}

	/**
	 * update the Chart refreshing all bubbles
	 * 
	 * @param int day (to set the range to display on the chart [day,today]).
	 */
	public void updateChart() {
		populateChart((int) bubbleChartController.mySlider.getValue());
	}
}
