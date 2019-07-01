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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import javax.swing.border.Border;

public class PopupWindow implements WindowListener {

	JButton done = new JButton("done");
	JFrame this_stage = new JFrame();

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
	private final JLabel lblMyProductivityIs = new JLabel("My productivity is:");
	private final JLabel lblNotesoptional = new JLabel("Notes (Optional)");

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
		lblWhitchActivity.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWhitchActivity.setBounds(146, 13, 699, 22);
		panel.add(lblWhitchActivity);
		final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

		done.setFont(new Font("Tahoma", Font.BOLD, 17));
		done.setBounds(441, 855, 104, 25);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {

					// AZIONI DEL DONE//

					PopupWindow.getIstance().close();
				} catch (final IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(done);
		lblMyProductivityIs.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyProductivityIs.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMyProductivityIs.setBounds(50, 756, 164, 22);

		panel.add(lblMyProductivityIs);
		lblNotesoptional.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotesoptional.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNotesoptional.setBounds(50, 782, 149, 22);

		panel.add(lblNotesoptional);

		final JTextArea NotesTextArea = new JTextArea();
		NotesTextArea.setBackground(Color.WHITE);
		NotesTextArea.setForeground(Color.WHITE);
		NotesTextArea.setBounds(50, 807, 900, 43);
		panel.add(NotesTextArea);

		final JLabel lblHowDoYou = new JLabel("How do you fell now?");
		lblHowDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowDoYou.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHowDoYou.setBounds(252, 82, 490, 22);
		panel.add(lblHowDoYou);

		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(252, 48, 490, 22);
		panel.add(comboBox);

		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(252, 758, 490, 22);
		panel.add(comboBox_1);

		final JPanel Full_Dominance = new JPanel();
		Full_Dominance.setBackground(Color.LIGHT_GRAY);
		Full_Dominance.setBounds(50, 543, 900, 200);
		panel.add(Full_Dominance);
		Full_Dominance.setLayout(null);

		final JPanel DominanceRadioPanel = new JPanel();
		DominanceRadioPanel.setForeground(Color.BLACK);
		DominanceRadioPanel.setBackground(Color.LIGHT_GRAY);
		DominanceRadioPanel.setBounds(40, 160, 820, 20);
		Full_Dominance.add(DominanceRadioPanel);
		DominanceRadioPanel.setLayout(new GridLayout(0, 5, 0, 0));

		final JRadioButton rdbtnNewRadioButton = new JRadioButton("1");
		rdbtnNewRadioButton.setBackground(Color.LIGHT_GRAY);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		DominanceRadioPanel.add(rdbtnNewRadioButton);

		final JRadioButton radioButton = new JRadioButton("2");
		radioButton.setBackground(Color.LIGHT_GRAY);
		radioButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton.setHorizontalAlignment(SwingConstants.CENTER);
		DominanceRadioPanel.add(radioButton);

		final JRadioButton radioButton_1 = new JRadioButton("3");
		radioButton_1.setBackground(Color.LIGHT_GRAY);
		radioButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
		DominanceRadioPanel.add(radioButton_1);

		final JRadioButton radioButton_2 = new JRadioButton("4");
		radioButton_2.setBackground(Color.LIGHT_GRAY);
		radioButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_2.setHorizontalAlignment(SwingConstants.CENTER);
		DominanceRadioPanel.add(radioButton_2);

		final JRadioButton radioButton_3 = new JRadioButton("5");
		radioButton_3.setForeground(Color.BLACK);
		radioButton_3.setBackground(Color.LIGHT_GRAY);
		radioButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_3.setHorizontalAlignment(SwingConstants.CENTER);
		DominanceRadioPanel.add(radioButton_3);

		final JPanel DominancePanelImageContainer = new JPanel();
		DominancePanelImageContainer.setForeground(Color.BLACK);
		DominancePanelImageContainer.setBackground(Color.LIGHT_GRAY);
		DominancePanelImageContainer.setBounds(40, 8, 820, 145);
		Full_Dominance.add(DominancePanelImageContainer);
		DominancePanelImageContainer.setLayout(new GridLayout(1, 5, 0, 0));

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

		final JLabel Submition = new JLabel("Submition");
		Submition.setFont(new Font("Tahoma", Font.BOLD, 13));
		Submition.setHorizontalAlignment(SwingConstants.CENTER);
		Submition.setBounds(50, 180, 69, 16);
		Full_Dominance.add(Submition);

		final JLabel Dominant = new JLabel("Dominant");
		Dominant.setFont(new Font("Tahoma", Font.BOLD, 13));
		Dominant.setHorizontalAlignment(SwingConstants.CENTER);
		Dominant.setBounds(740, 180, 69, 16);
		Full_Dominance.add(Dominant);

		final JLabel D_Neutral = new JLabel("Neutral");
		D_Neutral.setHorizontalAlignment(SwingConstants.CENTER);
		D_Neutral.setFont(new Font("Tahoma", Font.BOLD, 13));
		D_Neutral.setBounds(400, 180, 69, 16);
		Full_Dominance.add(D_Neutral);

		final JPanel Full_Excited = new JPanel();
		Full_Excited.setLayout(null);
		Full_Excited.setBackground(Color.LIGHT_GRAY);
		Full_Excited.setBounds(50, 330, 900, 200);
		panel.add(Full_Excited);

		final JPanel ExcitedRadioPanel = new JPanel();
		ExcitedRadioPanel.setForeground(Color.BLACK);
		ExcitedRadioPanel.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.setBounds(40, 160, 820, 20);
		Full_Excited.add(ExcitedRadioPanel);

		final JRadioButton radioButton_4 = new JRadioButton("1");
		radioButton_4.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_4.setBackground(Color.LIGHT_GRAY);

		final JRadioButton radioButton_5 = new JRadioButton("2");
		radioButton_5.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_5.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.setLayout(new GridLayout(0, 5, 0, 0));
		ExcitedRadioPanel.add(radioButton_4);
		ExcitedRadioPanel.add(radioButton_5);

		final JRadioButton radioButton_6 = new JRadioButton("3");
		radioButton_6.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_6.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.add(radioButton_6);

		final JRadioButton radioButton_7 = new JRadioButton("4");
		radioButton_7.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_7.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.add(radioButton_7);

		final JRadioButton radioButton_8 = new JRadioButton("5");
		radioButton_8.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_8.setForeground(Color.BLACK);
		radioButton_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_8.setBackground(Color.LIGHT_GRAY);
		ExcitedRadioPanel.add(radioButton_8);

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
		lblVeryCalm.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVeryCalm.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblVeryExcited = new JLabel("Very excited");
		lblVeryExcited.setBounds(740, 180, 82, 16);
		Full_Excited.add(lblVeryExcited);
		lblVeryExcited.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVeryExcited.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblNeutral = new JLabel("Neutral");
		lblNeutral.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeutral.setFont(new Font("Tahoma", Font.BOLD, 13));
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

		final JRadioButton radioButton_9 = new JRadioButton("1");
		radioButton_9.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_9.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_9.setBackground(Color.LIGHT_GRAY);
		PleasantRadioPanel.add(radioButton_9);

		final JRadioButton radioButton_10 = new JRadioButton("2");
		radioButton_10.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_10.setBackground(Color.LIGHT_GRAY);
		PleasantRadioPanel.add(radioButton_10);

		final JRadioButton radioButton_11 = new JRadioButton("3");
		radioButton_11.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_11.setBackground(Color.LIGHT_GRAY);
		PleasantRadioPanel.add(radioButton_11);

		final JRadioButton radioButton_12 = new JRadioButton("4");
		radioButton_12.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_12.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_12.setBackground(Color.LIGHT_GRAY);
		PleasantRadioPanel.add(radioButton_12);

		final JRadioButton radioButton_13 = new JRadioButton("5");
		radioButton_13.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_13.setForeground(Color.BLACK);
		radioButton_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		radioButton_13.setBackground(Color.LIGHT_GRAY);
		PleasantRadioPanel.add(radioButton_13);

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
		lblVeryUnpleasant.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVeryUnpleasant.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel lblVeryPleasant = new JLabel("Very pleasant");
		lblVeryPleasant.setBounds(720, 180, 104, 16);
		Full_Pleasant.add(lblVeryPleasant);
		lblVeryPleasant.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVeryPleasant.setHorizontalAlignment(SwingConstants.CENTER);

		final JLabel label_15 = new JLabel("Neutral");
		label_15.setHorizontalAlignment(SwingConstants.CENTER);
		label_15.setFont(new Font("Tahoma", Font.BOLD, 13));
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
			this_stage.dispatchEvent(new WindowEvent(this_stage, WindowEvent.WINDOW_CLOSING));
		} catch (final Exception ex) {
			// nothing
		}
	}

	/**
	 * Show on screen the PopupWindow
	 */
	private void show() {
		try {
			this_stage.isShowing();
			this_stage.toFront();
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					/*
					 * try { writeOpenWindowInDir(); } catch (InvocationTargetException e) {
					 * System.err.
					 * println("InvocationTargetException in writeOpenWindowInDir() in show()");
					 * e.printStackTrace(); } catch (InterruptedException e) {
					 * System.err.println("InterruptedException in writeOpenWindowInDir() in show()"
					 * ); e.printStackTrace(); }
					 */
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
		JOptionPane.showMessageDialog(null, "Information Missed!", text, JOptionPane.WARNING_MESSAGE);
		/*
		 * final Alert alert = new Alert(AlertType.WARNING);
		 * alert.setTitle("Information Missed!"); alert.setHeaderText(null);
		 * alert.setContentText(text); alert.initStyle(StageStyle.UTILITY); //
		 * alert.initOwner(this_stage); alert.showAndWait();
		 */
	}

	/**
	 * Destroys the Window, to free of memory of the notification when it is closed
	 *
	 */
	protected void cleanInstance() {
		instance = null;
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