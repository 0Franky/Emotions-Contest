package layout.Notification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import classes.AppTimer;
import classes.JCustomController.BackgroundPane;
import classes.JCustomController.TransparentButton;
import layout.PopupWindow.PopupWindow;

public class Notification implements WindowListener {

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// ; // usa il look di default
		}
	}

	TransparentButton dismiss = new TransparentButton("dismiss");
	TransparentButton postpone = new TransparentButton("postpone");
	JFrame this_stage = new JFrame();
	JSlider mySlider = new JSlider();

	/**
	 * Notification instance is useful to make Notification class "Singleton"
	 */
	private static Notification instance = null;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;

	public Notification() throws IOException {

		// this_stage.setUndecorated(true);

		this_stage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// ATTIVA setUndecorated se hai fatto il DRAG AND DROP //

		// this_stage.setUndecorated(true);

		this_stage
				.setIconImage(Toolkit.getDefaultToolkit().getImage(Notification.class.getResource("/Assets/Icon.png")));
		this_stage.setAlwaysOnTop(true);
		this_stage.setTitle("HowAppYou Notification");

		this_stage.setSize(435, 170);
		this_stage.addWindowListener(this);
		this_stage.setResizable(false);

		// BLOCCA LA CHIUSURA DALLA X //
		// this_stage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 * final BackgroundPane BG_Image = new BackgroundPane(); BG_Image.setBounds(0,
		 * 0, 429, 512); panel.add(BG_Image);
		 * BG_Image.setBackground(ImageIO.read(Notification.class.getResource(
		 * "/Assets/Icon.png")), 0.1f);
		 */

		final BackgroundPane panel = new BackgroundPane();
		panel.setBounds(0, 0, 429, 512);
		panel.setBackground(ImageIO.read(Notification.class.getResource("/Assets/Icon.png")), 0.15f);

		// final JPanel panel = new JPanel();
		this_stage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JLabel SliderLabel = new JLabel("1");
		SliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SliderLabel.setFont(new Font("System", Font.BOLD, 15));
		SliderLabel.setBounds(12, 76, 44, 26);
		panel.add(SliderLabel);
		mySlider.setPaintLabels(true);

		mySlider.setOpaque(false);

		mySlider.setMinorTickSpacing(60);
		mySlider.setMajorTickSpacing(119);
		mySlider.setPaintTicks(true);
		mySlider.setMinimum(1);
		mySlider.setValue(1);
		mySlider.setMaximum(120);
		mySlider.setBorder(new CompoundBorder());
		mySlider.setBounds(68, 76, 183, 46);

		mySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				SliderLabel.setText(String.valueOf(mySlider.getValue()));
			}
		});

		panel.add(mySlider);

		final JLabel lblHowDoYou = new JLabel("How do you feeling?");
		lblHowDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowDoYou.setFont(new Font("System", Font.BOLD, 18));
		lblHowDoYou.setBounds(12, 13, 239, 22);
		panel.add(lblHowDoYou);

		final JLabel lblClickHereTo = new JLabel("Click here to open pop-up!");
		final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		lblClickHereTo.setBorder(border);
		lblClickHereTo.setFont(new Font("System", Font.BOLD, 17));
		lblClickHereTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblClickHereTo.setVerticalAlignment(SwingConstants.CENTER);
		lblClickHereTo.setBounds(12, 38, 239, 22);
		lblClickHereTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				try {
					openPopUp();
				} catch (final IOException e1) {
				}
			}
		});
		panel.add(lblClickHereTo);

		postpone.setFont(new Font("System", Font.BOLD, 17));
		postpone.setBounds(260, 60, 150, 50);
		postpone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final int Time = mySlider.getValue();
				System.out.println("Minuti da passare al Timer: " + Time);
				try {
					postponeAction(e);
				} catch (final Exception e1) {
				}
			}
		});
		panel.add(postpone);

		dismiss.setFont(new Font("System", Font.BOLD, 17));
		dismiss.setBounds(260, 8, 150, 50);
		dismiss.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					dismissAction(e);
				} catch (final IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(dismiss);

		centerStage();

		this_stage.setVisible(true);
	}

	// END GUI

	/**
	 * Return the unique possible instance of the Notification
	 *
	 * @return The Notification.
	 * @throws IOException Generic I/O error.
	 */
	public static Notification getIstance() throws IOException {
		if (instance == null) {
			synchronized (Notification.class) {
				if (instance == null) {
					instance = new Notification();
				}
			}
		}
		return instance;
	}

	/**
	 * Set the Stage (Window) of the Notification on right-upper corner of screen
	 *
	 * @param Stage stage (the window of the Notification).
	 */
	private void centerStage() {
		// java - get screen size using the Toolkit class
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenWidth = screenSize.width;

		this_stage.isShowing();

		this_stage.setLocation((screenWidth - this_stage.getWidth()) - 10, (30));
	}

	/**
	 * Close the Notification
	 */
	public void close() {
		try {
			canClose = true;
			this_stage.setVisible(false);
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
			this_stage.setVisible(true);
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
	 */
	protected void cleanInstance() {
		instance = null;
	}

	/**
	 * Manage the click (ActionEvent) on "postpone" button
	 *
	 * @param ActionEvent event
	 * @throws IOException           Generic I/O error.
	 * @throws NumberFormatException Generic NumberFormat error.
	 * @throws InterruptedException  Generic Interruption error.
	 */
	private void postponeAction(final ActionEvent event)
			throws IOException, NumberFormatException, InterruptedException {
		final int Time = mySlider.getValue();
		System.out.println("Minuti da passare al Timer: " + Time);
		Notification.getIstance().close();
		AppTimer.getIstance().startTimer((Time)); // Set Timer //
		Notification.getIstance().cleanInstance(); // <<-->> CANCEL THE INSTANCE <<-->>
	}

	/**
	 * Opens the popUp-Window and close the Notification
	 */
	public void openPopUp() throws IOException {
		Notification.getIstance().close();
		PopupWindow.getIstance();
	}

	/**
	 * Manage the click (ActionEvent) on "dismiss" button
	 *
	 * @param ActionEvent event
	 * @throws IOException Generic I/O error.
	 */
	private void dismissAction(final ActionEvent event) throws IOException {
		Notification.getIstance().close();
	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub
		if (canClose) {
			this_stage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			cleanInstance();
		}
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