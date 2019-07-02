package layout.BubbleChart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

import classes.DataChart;
import classes.database.SQLiteConnection;

public class BubbleChartWindow implements WindowListener {

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// ; // usa il look di default
		}
	}

	JFrame this_stage = new JFrame();
	JSlider mySlider = new JSlider();

	JFreeChart chart;
	ChartPanel BubbleChart;

	XYZDataset dataset;

	JPanel panel;

	final static Color BG_COLOR = Color.WHITE;

	/**
	 * Notification instance is useful to make Notification class "Singleton"
	 */
	private static BubbleChartWindow instance = null;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;
	private final JLabel lblNotesoptional = new JLabel("Notes (Optional)");
	// private final JLabel Feeling = new JLabel("Feeling Chart");

	private BubbleChartWindow() throws IOException {
		this_stage.setTitle("Bubble Chart Window");

		this_stage.setResizable(false);
		this_stage.setSize(726, 579);
		this_stage.addWindowListener(this);
		this_stage.setIconImage(
				Toolkit.getDefaultToolkit().getImage(BubbleChartWindow.class.getResource("/Assets/Icon.png")));

		panel = new JPanel();
		panel.setBackground(BG_COLOR);
		this_stage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JLabel SliderLabel = new JLabel("0");
		SliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SliderLabel.setFont(new Font("System", Font.BOLD, 15));
		SliderLabel.setBounds(217, 475, 44, 26);
		panel.add(SliderLabel);
		mySlider.setPaintLabels(true);

		mySlider.setBackground(BG_COLOR);
		mySlider.setMajorTickSpacing(15);
		mySlider.setPaintTicks(true);
		mySlider.setValue(0);
		mySlider.setMinorTickSpacing(15);
		mySlider.setMaximum(30);
		mySlider.setBorder(new CompoundBorder());
		mySlider.setBounds(290, 475, 385, 44);

		mySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				SliderLabel.setText(String.valueOf(mySlider.getValue()));
				final JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					updateChart();
				}
			}
		});

		panel.add(mySlider);

		// START CHART
		dataset = new DefaultXYZDataset();
		chart = ChartFactory.createBubbleChart("Feeling Chart", "Calm", "Excited", dataset);

		// Set range for X-Axis
		final XYPlot plot = chart.getXYPlot();
		final NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(0, 6);

		// Set range for Y-Axis
		final NumberAxis range = (NumberAxis) plot.getRangeAxis();
		range.setRange(0, 6);

		final ValueMarker verticalLine = new ValueMarker((domain.getUpperBound() + domain.getLowerBound()) / 2);
		verticalLine.setPaint(Color.black);
		plot.addDomainMarker(verticalLine);

		final ValueMarker horizontalLine = new ValueMarker((range.getUpperBound() + range.getLowerBound()) / 2);
		horizontalLine.setPaint(Color.black);
		plot.addRangeMarker(horizontalLine);

		// Format label
		final XYBubbleRenderer renderer = (XYBubbleRenderer) plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);

		// Create Panel
		chart.clearSubtitles();
		BubbleChart = new ChartPanel(chart);
		BubbleChart.setForeground(BG_COLOR);
		BubbleChart.setMouseZoomable(false);
		BubbleChart.setBounds(22, 50, 658, 403);
		panel.add(BubbleChart);
		// END CHART

		final JLabel lblHowDoYou = new JLabel("Select day range:");
		lblHowDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowDoYou.setFont(new Font("System", Font.BOLD, 18));
		lblHowDoYou.setBounds(22, 476, 183, 22);
		panel.add(lblHowDoYou);
		// Feeling.setHorizontalAlignment(SwingConstants.CENTER);
		// Feeling.setFont(new Font("System", Font.BOLD, 18));
		// Feeling.setBounds(269, 13, 149, 22);

		// panel.add(Feeling);

		centerStage();

		this_stage.setVisible(true);

		populateChart(0);

		updateUI();

	}

	private void updateUI() {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					SwingUtilities.updateComponentTreeUI(this_stage);
				}
			});
		} catch (final Exception e) {
			System.err.println(
					"ERROR: updateUI()\nCOMMAND: SwingUtilities.updateComponentTreeUI(this_stage);\nERROR DETAILS:\n");
			e.printStackTrace();
		}
	}

	/**
	 * Set the Stage (Window) of the Notification on right-upper corner of screen
	 *
	 * @param Stage stage (the window of the Notification).
	 */
	private void centerStage() {
		// java - get screen size using the Toolkit class
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenHeight = screenSize.height;
		final int screenWidth = screenSize.width;

		this_stage.isShowing();

		this_stage.setLocation(((screenWidth / 2) - (this_stage.getWidth() / 2)),
				((screenHeight / 2) - (this_stage.getHeight() / 2)));
	}

	/**
	 * Return the unique possible instance of the BubbleChartWindow
	 *
	 * @return The BubbleChartWindow.
	 * @throws IOException Generic I/O error.
	 */
	public static BubbleChartWindow getIstance() throws IOException {
		if (instance == null) {
			synchronized (BubbleChartWindow.class) {
				if (instance == null) {
					instance = new BubbleChartWindow();
				} else {
					instance.show();
					instance.toFront();
				}
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
	 * Close the Notification
	 */
	public void close() {
		try {
			canClose = true;
			this_stage.dispatchEvent(new WindowEvent(this_stage, WindowEvent.WINDOW_CLOSING));
		} catch (final Exception ex) {
			System.err.println("not Hide");
			ex.printStackTrace();
		}
	}

	/**
	 * Show on screen the Notification
	 */
	public void show() {
		try {
			this_stage.isShowing();
		} catch (final Exception ex) {
			System.err.println("not Show");
			ex.printStackTrace();
		}
	}

	/**
	 * Set on front of all opened window the Notification
	 */
	public void toFront() {
		try {
			this_stage.toFront();
		} catch (final Exception ex) {
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
	public void populateChart(final int day) {
		// final DefaultXYZDataset newData = dataset;
		// newData.removeAllSeries();
		// dataset.removeAllSeries

		// dataset = new DefaultXYZDataset();

		final int countSeries = dataset.getSeriesCount();
		for (int i = 0; i < countSeries; i++) {
			((DefaultXYZDataset) dataset).removeSeries(dataset.getSeriesKey(0));
		}

		final List<DataChart> data = SQLiteConnection.getDataForChart(day);
		for (final DataChart bubble : data) {
			addBubble(bubble.getValence(), bubble.getArousal(), (float) bubble.getWeight() / 8);
		}

		updateUI();
	}

	/**
	 * Adds a Bubble to the chart
	 *
	 * @params Number xValue, Number yValue, Number weight (coordinates and value of
	 *         the bubble)
	 * @throws IOException Generic I/O error.
	 */
	public void addBubble(final double xValue, final double yValue, final double weight) {
		((DefaultXYZDataset) dataset).addSeries(Integer.toString(dataset.getSeriesCount()), new double[][] { { xValue }, // X-Value
				{ yValue }, // Y-Value
				{ weight } // Z-Value
		});
	}

	/**
	 * update the Chart refreshing all bubbles
	 *
	 * @param int day (to set the range to display on the chart [day,today]).
	 */
	public void updateChart() {
		populateChart(mySlider.getValue());
	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		cleanInstance();
	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
