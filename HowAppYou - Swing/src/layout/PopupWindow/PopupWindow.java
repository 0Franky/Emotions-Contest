package layout.PopupWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import Title.Title;
import classes.AppTimer;
import classes.Synchronizer;
import classes.TimeConverter;
import classes.Tuple;
import classes.database.SQLiteConnection;
import layout.BubbleChart.BubbleChartWindow;

public class PopupWindow implements WindowListener {

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// ; // usa il look di default
		}
	}

	private final JFrame this_stage = new JFrame();
	private final JButton done = new JButton("done");
	private final JComboBox<String> lblActivity = new JComboBox<>();
	private final JComboBox<String> lblProductivity = new JComboBox<>();
	private final JTextArea NotesTextArea = new JTextArea();
	private final JLabel lblMyProductivityIs = new JLabel("My productivity is:");
	private final JLabel lblNotesoptional = new JLabel("Notes (Optional)");
	private String pleasantness = "";
	private String excitement = "";
	private String dominance = "";
	/**
	 * ALL RADIO BUTTON
	 */
	ButtonGroup group_D = new ButtonGroup();
	private final JRadioButton rdbtn_D1 = new JRadioButton("1");
	private final JRadioButton rdbtn_D2 = new JRadioButton("2");
	private final JRadioButton rdbtn_D3 = new JRadioButton("3");
	private final JRadioButton rdbtn_D4 = new JRadioButton("4");
	private final JRadioButton rdbtn_D5 = new JRadioButton("5");

	ButtonGroup group_A = new ButtonGroup();
	private final JRadioButton rdbtn_A1 = new JRadioButton("1");
	private final JRadioButton rdbtn_A2 = new JRadioButton("2");
	private final JRadioButton rdbtn_A3 = new JRadioButton("3");
	private final JRadioButton rdbtn_A4 = new JRadioButton("4");
	private final JRadioButton rdbtn_A5 = new JRadioButton("5");

	ButtonGroup group_V = new ButtonGroup();
	private final JRadioButton rdbtn_V1 = new JRadioButton("1");
	private final JRadioButton rdbtn_V2 = new JRadioButton("2");
	private final JRadioButton rdbtn_V3 = new JRadioButton("3");
	private final JRadioButton rdbtn_V4 = new JRadioButton("4");
	private final JRadioButton rdbtn_V5 = new JRadioButton("5");

	/**
	 * Notification instance is useful to make Notification class "Singleton"
	 */
	private static PopupWindow instance = null;

	/**
	 * Variables used to manage the location of the window on screen
	 */
	private double X, Y;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;

	public PopupWindow() throws IOException {

		// ATTIVA setUndecorated se hai fatto il DRAG AND DROP //

		// this_stage.setUndecorated(true);

		this_stage.setAlwaysOnTop(true);
		this_stage.setResizable(false);
		this_stage.setTitle("HowAppYou Window");
		this_stage.addWindowListener(this);

		centerFrame(this_stage);

		// BLOCCA LA CHIUSURA DALLA X //
		// this_stage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this_stage.setVisible(true);

		final ImageIcon img = new ImageIcon("/Assets/Icon.png");
		this_stage
				.setIconImage(Toolkit.getDefaultToolkit().getImage(PopupWindow.class.getResource("/Assets/Icon.png")));

		final JPanel panel = new JPanel();
		this_stage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JLabel lblWhitchActivity = new JLabel(
				"In which activity have you mainly been involved since the last notification?");
		lblWhitchActivity.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhitchActivity.setFont(new Font("System", Font.BOLD, 18));
		lblWhitchActivity.setBounds(146, 13, 699, 22);
		panel.add(lblWhitchActivity);
		final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

		done.setFont(new Font("System", Font.BOLD, 17));
		done.setBounds(441, 855, 104, 25);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {

					// AZIONI DEL DONE //

					pleasantness = getSelected_RadioGroup(group_V).toString();
					excitement = getSelected_RadioGroup(group_A).toString();
					dominance = getSelected_RadioGroup(group_D).toString();

					System.out.println(getActivity() + " " + pleasantness + " " + excitement + " " + dominance + " "
							+ getProductivity() + " " + getNotes());

					// Controlla Riempimento e stampa risultati//
					try {
						writeResultsInDir();
					} catch (InvocationTargetException | InterruptedException e1) {
						// TODO Auto-generated catch block
						System.out.println("Not writeResultsInDir");
						e1.printStackTrace();
					}

				} catch (final IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(done);
		lblMyProductivityIs.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyProductivityIs.setFont(new Font("System", Font.BOLD, 18));
		lblMyProductivityIs.setBounds(50, 756, 164, 22);

		panel.add(lblMyProductivityIs);
		lblNotesoptional.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotesoptional.setFont(new Font("System", Font.BOLD, 18));
		lblNotesoptional.setBounds(50, 782, 149, 22);

		panel.add(lblNotesoptional);

		NotesTextArea.setBackground(Color.WHITE);
		NotesTextArea.setForeground(Color.BLACK);
		NotesTextArea.setBounds(50, 807, 900, 43);
		panel.add(NotesTextArea);

		final JLabel lblHowDoYou = new JLabel("How do you fell now?");
		lblHowDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowDoYou.setFont(new Font("System", Font.BOLD, 18));
		lblHowDoYou.setBounds(252, 82, 490, 22);
		panel.add(lblHowDoYou);

		// Coding, Bugfixing, Testing, Design, Meeting, Email, Helping, Networking,
		// Learning, Administrative tasks, Documentation
		lblActivity.setBounds(252, 48, 490, 22);
		lblActivity.insertItemAt("Coding", 0);
		lblActivity.insertItemAt("Bugfixing", 1);
		lblActivity.insertItemAt("Testing", 2);
		lblActivity.insertItemAt("Design", 3);
		lblActivity.insertItemAt("Meeting", 4);
		lblActivity.insertItemAt("Email", 5);
		lblActivity.insertItemAt("Helping", 6);
		lblActivity.insertItemAt("Networking", 7);
		lblActivity.insertItemAt("Learning", 8);
		lblActivity.insertItemAt("Administrative tasks", 9);
		lblActivity.insertItemAt("Documentation", 10);
		panel.add(lblActivity);

		// very low, below average, average, above average, very high
		lblProductivity.setBounds(252, 758, 490, 22);
		lblProductivity.insertItemAt("very low", 0);
		lblProductivity.insertItemAt("below average", 1);
		lblProductivity.insertItemAt("average", 2);
		lblProductivity.insertItemAt("above average", 3);
		lblProductivity.insertItemAt("very high", 4);
		panel.add(lblProductivity);

		// PANEL DOMINACE //
		final JPanel Full_Dominance = new JPanel();
		Full_Dominance.setBackground(Color.LIGHT_GRAY);
		Full_Dominance.setBounds(50, 543, 900, 200);
		panel.add(Full_Dominance);
		Full_Dominance.setLayout(null);

		// RADIO PANEL DOMINACE //
		final JPanel DominanceRadioPanel = new JPanel();
		DominanceRadioPanel.setForeground(Color.BLACK);
		DominanceRadioPanel.setBackground(Color.LIGHT_GRAY);
		DominanceRadioPanel.setBounds(40, 160, 820, 20);
		Full_Dominance.add(DominanceRadioPanel);
		DominanceRadioPanel.setLayout(new GridLayout(0, 5, 0, 0));

		// RADIO BUTTON DOMINACE //
		rdbtn_D1.setBackground(Color.LIGHT_GRAY);
		rdbtn_D1.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_D1.setHorizontalAlignment(SwingConstants.CENTER);

		rdbtn_D2.setBackground(Color.LIGHT_GRAY);
		rdbtn_D2.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_D2.setHorizontalAlignment(SwingConstants.CENTER);

		rdbtn_D3.setBackground(Color.LIGHT_GRAY);
		rdbtn_D3.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_D3.setHorizontalAlignment(SwingConstants.CENTER);

		rdbtn_D4.setBackground(Color.LIGHT_GRAY);
		rdbtn_D4.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_D4.setHorizontalAlignment(SwingConstants.CENTER);

		rdbtn_D5.setForeground(Color.BLACK);
		rdbtn_D5.setBackground(Color.LIGHT_GRAY);
		rdbtn_D5.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_D5.setHorizontalAlignment(SwingConstants.CENTER);

		DominanceRadioPanel.add(rdbtn_D1);
		DominanceRadioPanel.add(rdbtn_D2);
		DominanceRadioPanel.add(rdbtn_D3);
		DominanceRadioPanel.add(rdbtn_D4);
		DominanceRadioPanel.add(rdbtn_D5);
		group_D.add(rdbtn_D1);
		group_D.add(rdbtn_D2);
		group_D.add(rdbtn_D3);
		group_D.add(rdbtn_D4);
		group_D.add(rdbtn_D5);

		// PANEL IMAGE DOMINANACE //
		final JPanel DominancePanelImageContainer = new JPanel();
		DominancePanelImageContainer.setForeground(Color.BLACK);
		DominancePanelImageContainer.setBackground(Color.LIGHT_GRAY);
		DominancePanelImageContainer.setBounds(40, 8, 820, 145);
		Full_Dominance.add(DominancePanelImageContainer);
		DominancePanelImageContainer.setLayout(new GridLayout(1, 5, 0, 0));

		// IMAGE DOMINACE //
		final JLabel D1 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Dominance/D1.png"))));
		DominancePanelImageContainer.add(D1);

		final JLabel D2 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Dominance/D2.png"))));
		DominancePanelImageContainer.add(D2);

		final JLabel D3 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Dominance/D3.png"))));
		DominancePanelImageContainer.add(D3);

		final JLabel D4 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Dominance/D4.png"))));
		DominancePanelImageContainer.add(D4);

		final JLabel D5 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Dominance/D5.png"))));
		DominancePanelImageContainer.add(D5);

		// LABEL IMAGES DOMINANCE
		final JLabel Submition = new JLabel("Submition");
		Submition.setFont(new Font("System", Font.BOLD, 13));
		Submition.setHorizontalAlignment(SwingConstants.CENTER);
		Submition.setBounds(50, 180, 69, 16);
		Full_Dominance.add(Submition);

		final JLabel Dominant = new JLabel("Dominant");
		Dominant.setFont(new Font("System", Font.BOLD, 13));
		Dominant.setHorizontalAlignment(SwingConstants.CENTER);
		Dominant.setBounds(740, 180, 69, 16);
		Full_Dominance.add(Dominant);

		final JLabel D_Neutral = new JLabel("Neutral");
		D_Neutral.setHorizontalAlignment(SwingConstants.CENTER);
		D_Neutral.setFont(new Font("System", Font.BOLD, 13));
		D_Neutral.setBounds(400, 180, 69, 16);
		Full_Dominance.add(D_Neutral);

		/**
		 * EXCITEMENT PANEL
		 */

		final JPanel Full_Excited = new JPanel();
		Full_Excited.setLayout(null);
		Full_Excited.setBackground(Color.LIGHT_GRAY);
		Full_Excited.setBounds(50, 330, 900, 200);
		panel.add(Full_Excited);

		final JPanel ExcitedRadioPanel = new JPanel();
		ExcitedRadioPanel.setForeground(Color.BLACK);
		ExcitedRadioPanel.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.setBounds(40, 160, 820, 20);
		ExcitedRadioPanel.setLayout(new GridLayout(0, 5, 0, 0));
		Full_Excited.add(ExcitedRadioPanel);

		rdbtn_A1.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_A1.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_A1.setBackground(Color.LIGHT_GRAY);

		rdbtn_A2.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_A2.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_A2.setBackground(Color.LIGHT_GRAY);

		rdbtn_A3.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_A3.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_A3.setBackground(Color.LIGHT_GRAY);

		rdbtn_A4.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_A4.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_A4.setBackground(Color.LIGHT_GRAY);

		rdbtn_A5.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_A5.setForeground(Color.BLACK);
		rdbtn_A5.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_A5.setBackground(Color.LIGHT_GRAY);

		ExcitedRadioPanel.add(rdbtn_A1);
		ExcitedRadioPanel.add(rdbtn_A2);
		ExcitedRadioPanel.add(rdbtn_A3);
		ExcitedRadioPanel.add(rdbtn_A4);
		ExcitedRadioPanel.add(rdbtn_A5);
		group_A.add(rdbtn_A1);
		group_A.add(rdbtn_A2);
		group_A.add(rdbtn_A3);
		group_A.add(rdbtn_A4);
		group_A.add(rdbtn_A5);

		final JPanel ExcitedImagePanel = new JPanel();
		ExcitedImagePanel.setForeground(Color.BLACK);
		ExcitedImagePanel.setBackground(Color.LIGHT_GRAY);
		ExcitedImagePanel.setBounds(40, 8, 820, 145);
		Full_Excited.add(ExcitedImagePanel);

		final JLabel A1 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Excitement/A1.png"))));

		final JLabel A5 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Excitement/A5.png"))));

		final JLabel A4 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Excitement/A4.png"))));

		final JLabel A3 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Excitement/A3.png"))));

		final JLabel A2 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Excitement/A2.png"))));
		ExcitedImagePanel.setLayout(new GridLayout(1, 5, 0, 0));
		ExcitedImagePanel.add(A1);
		ExcitedImagePanel.add(A5);
		ExcitedImagePanel.add(A4);
		ExcitedImagePanel.add(A3);
		ExcitedImagePanel.add(A2);

		final JLabel lblVeryCalm = new JLabel("Very calm");
		lblVeryCalm.setBounds(50, 180, 69, 16);
		Full_Excited.add(lblVeryCalm);
		lblVeryCalm.setFont(new Font("System", Font.BOLD, 13));
		lblVeryCalm.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblVeryExcited = new JLabel("Very excited");
		lblVeryExcited.setBounds(740, 180, 82, 16);
		Full_Excited.add(lblVeryExcited);
		lblVeryExcited.setFont(new Font("System", Font.BOLD, 13));
		lblVeryExcited.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblNeutral = new JLabel("Neutral");
		lblNeutral.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeutral.setFont(new Font("System", Font.BOLD, 13));
		lblNeutral.setBounds(400, 180, 69, 16);
		Full_Excited.add(lblNeutral);

		final JPanel Full_Pleasant = new JPanel();
		Full_Pleasant.setLayout(null);
		Full_Pleasant.setBackground(Color.LIGHT_GRAY);
		Full_Pleasant.setBounds(50, 117, 900, 200);
		panel.add(Full_Pleasant);

		final JPanel PleasantRadioPanel = new JPanel();
		PleasantRadioPanel.setForeground(Color.BLACK);
		PleasantRadioPanel.setBackground(SystemColor.control);
		PleasantRadioPanel.setBounds(40, 160, 820, 20);
		Full_Pleasant.add(PleasantRadioPanel);
		PleasantRadioPanel.setLayout(new GridLayout(0, 5, 0, 0));

		rdbtn_V1.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_V1.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_V1.setBackground(Color.LIGHT_GRAY);

		rdbtn_V2.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_V2.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_V2.setBackground(Color.LIGHT_GRAY);

		rdbtn_V3.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_V3.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_V3.setBackground(Color.LIGHT_GRAY);

		rdbtn_V4.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_V4.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_V4.setBackground(Color.LIGHT_GRAY);

		rdbtn_V5.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_V5.setForeground(Color.BLACK);
		rdbtn_V5.setFont(new Font("System", Font.BOLD, 13));
		rdbtn_V5.setBackground(Color.LIGHT_GRAY);

		PleasantRadioPanel.add(rdbtn_V1);
		PleasantRadioPanel.add(rdbtn_V2);
		PleasantRadioPanel.add(rdbtn_V3);
		PleasantRadioPanel.add(rdbtn_V4);
		PleasantRadioPanel.add(rdbtn_V5);
		group_V.add(rdbtn_V1);
		group_V.add(rdbtn_V2);
		group_V.add(rdbtn_V3);
		group_V.add(rdbtn_V4);
		group_V.add(rdbtn_V5);

		final JPanel PleasantImagePanel = new JPanel();
		PleasantImagePanel.setForeground(Color.BLACK);
		PleasantImagePanel.setBackground(Color.LIGHT_GRAY);
		PleasantImagePanel.setBounds(40, 8, 820, 145);
		Full_Pleasant.add(PleasantImagePanel);
		PleasantImagePanel.setLayout(new GridLayout(1, 5, 0, 0));

		final JLabel V1 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Pleasantness/V1.png"))));
		PleasantImagePanel.add(V1);

		final JLabel V2 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Pleasantness/V2.png"))));
		PleasantImagePanel.add(V2);

		final JLabel V3 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Pleasantness/V3.png"))));
		PleasantImagePanel.add(V3);

		final JLabel V4 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Pleasantness/V4.png"))));
		PleasantImagePanel.add(V4);

		final JLabel V5 = new JLabel(
				new ImageIcon(ImageIO.read(this.getClass().getResource("/Assets/Pleasantness/V5.png"))));
		PleasantImagePanel.add(V5);

		final JLabel lblVeryUnpleasant = new JLabel("Very unpleasant");
		lblVeryUnpleasant.setBounds(50, 180, 119, 16);
		Full_Pleasant.add(lblVeryUnpleasant);
		lblVeryUnpleasant.setFont(new Font("System", Font.BOLD, 13));
		lblVeryUnpleasant.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblVeryPleasant = new JLabel("Very pleasant");
		lblVeryPleasant.setBounds(720, 180, 104, 16);
		Full_Pleasant.add(lblVeryPleasant);
		lblVeryPleasant.setFont(new Font("System", Font.BOLD, 13));
		lblVeryPleasant.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel label_15 = new JLabel("Neutral");
		label_15.setHorizontalAlignment(SwingConstants.CENTER);
		label_15.setFont(new Font("System", Font.BOLD, 13));
		label_15.setBounds(400, 180, 69, 16);
		Full_Pleasant.add(label_15);

	}

	/**
	 * Return the unique possible instance of the Notification
	 *
	 * @return The Notification.
	 * @throws IOException Generic I/O error.
	 */
	public static PopupWindow getIstance() throws IOException {
		if (instance == null) {
			synchronized (PopupWindow.class) {
				if (instance == null) {
					instance = new PopupWindow();
				}
			}
		}
		return instance;
	}

	/**
	 * Center frame on screen
	 *
	 * @param this_stage
	 */
	public static void centerFrame(final JFrame this_stage) {
		this_stage.pack();
		// make the frame half the height and width
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int height = screenSize.height;
		final int width = screenSize.width;
		this_stage.setSize(1022, 936);
		// center the jframe on screen
		this_stage.setLocationRelativeTo(null);
	}

	/**
	 * Close the PopupWindow
	 */
	public void close() {
		try {
			canClose = true;
			this_stage.setVisible(false);
		} catch (final Exception ex) {
			// nothing
		}
	}

	/**
	 * Show on screen the PopupWindow
	 */
	public void show() {
		try {
			this_stage.setVisible(true);
			this_stage.toFront();
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {

					try {
						writeOpenWindowInDir();
					} catch (final InvocationTargetException e) {
						System.err.println("InvocationTargetException in writeOpenWindowInDir() in show()");
						e.printStackTrace();
					} catch (final InterruptedException e) {
						System.err.println("InterruptedException in writeOpenWindowInDir() in show()");
						e.printStackTrace();
					}

				};
			});
		} catch (final Exception ex) {
			System.err.println("Catch show PopupWindow");
		}
	}

	/**
	 * Set on front of all opened window the PopupWindow
	 */
	public void toFront() {
		try {
			this_stage.toFront();
		} catch (final Exception ex) {
			System.err.println("Catch toFront PopupWindow");
		}
	}

	/**
	 * Define Allerts of not completed field on PopupWindow
	 *
	 * @return Tuple
	 */
	private void MessageBox(final String text) {
		JOptionPane.showMessageDialog(this_stage, text, "Information Missed!", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 *
	 */
	protected void cleanInstance() {
		instance = null;
	}

	/**
	 * Write results of the questionnaire on database, it also starts sync for
	 * online Sheet and start Timer, then it close PopupWindow
	 *
	 * @throws IOException               Generic I/O error.
	 * @throws InvocationTargetException Generic InvocationTarget error.
	 * @throws InterruptedException      Generic Interruptederror.
	 */
	protected void writeResultsInDir() throws InvocationTargetException, InterruptedException, IOException {
		if (checkCorrectionParam()) {
			SQLiteConnection.addRow(getActivityToTuple().toArray()); // write on database //
			SQLiteConnection.addRowToSync(getActivityToTuple().toArray());
			Synchronizer.sync(); // Syncronize to write online //
			AppTimer.getIstance().startTimer(60); // Start Timer at 60 minutes //
			if (BubbleChartWindow.isIstanceNULL() == false) {
				BubbleChartWindow.getIstance().updateChart(); // Refresh BubbleChartWindow when it is already opened //
				System.out.println("Refresh BubbleChart");
			}
			PopupWindow.getIstance().close();
		}
	}

	/**
	 * Write open_popUp on database and start sync for online Sheet
	 *
	 * @throws InvocationTargetException, InterruptedException
	 */
	private void writeOpenWindowInDir() throws InvocationTargetException, InterruptedException {
		SQLiteConnection.addRow(getActivityOpenWindowToTuple().toArray());
		SQLiteConnection.addRowToSync(getActivityOpenWindowToTuple().toArray());
		Synchronizer.sync();
	}

	/**
	 * Create a new Tuple for "POPUP_OPENED" event
	 *
	 * @return Tuple
	 */
	private Tuple getActivityOpenWindowToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())), "", "", "", "", "", "",
				"POPUP_OPENED", "");
	}

	/**
	 * Create a new Tuple for "POPUP_CLOSED" event
	 *
	 * @return Tuple
	 */
	private Tuple getActivityToTuple() {
		return new Tuple(Long.toString(TimeConverter.toUnixTime(System.currentTimeMillis())), getActivity(),
				pleasantness, excitement, dominance, getProductivity(), Title.USER_ID, "POPUP_CLOSED", getNotes());
	}

	/**
	 * return the content of lbl_Activity.getValue()
	 *
	 * @return text
	 */
	private String getActivity() {
		String text = "";
		if (lblActivity.getSelectedItem() != null) {
			text = lblActivity.getSelectedItem().toString();
		}
		return text;
	}

	/**
	 * return the content of lbl_Productivity.getValue()
	 *
	 * @return text
	 */
	private String getProductivity() {
		String text = "";
		if (lblProductivity.getSelectedItem() != null) {
			text = lblProductivity.getSelectedItem().toString();
		}
		return text;
	}

	private String getNotes() {
		String text = "";
		if (NotesTextArea.getText() != null) {
			text = NotesTextArea.getText();
		}
		return text;
	}

	/**
	 * get Selected button from group of RadioButton
	 *
	 * @return D
	 */
	private String getSelected_RadioGroup(final ButtonGroup BG) {
		for (final Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {
			final AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return "";
	}

	/**
	 * Verify if all PopupWindow's fields are completed
	 *
	 * @return Tuple
	 */
	private boolean checkCorrectionParam() {
		boolean status = true;

		if (getActivity() == "") {
			status = false;
			MessageBox("Activity not filled!");
		}

		if (pleasantness == "") {
			status = false;
			MessageBox("Pleasantness not checked!");
		}

		if (excitement == "") {
			status = false;
			MessageBox("Excitement not checked!");
		}

		if (dominance == "") {
			status = false;
			MessageBox("Dominance not checked!");
		}

		if (getProductivity() == "") {
			status = false;
			MessageBox("Productivity not filled!");
		}

		return status;
	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		this_stage.setState(JFrame.NORMAL);
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