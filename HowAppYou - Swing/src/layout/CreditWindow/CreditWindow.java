package layout.CreditWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
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
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CreditWindow implements WindowListener {

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// ; // usa il look di default
		}
	}

	JFrame this_stage = new JFrame();

	JPanel panel;

	final static Color BG_COLOR = Color.WHITE;

	/**
	 * Notification instance is useful to make Notification class "Singleton"
	 */
	private static CreditWindow instance = null;

	/**
	 * Boolean used set the possibility to close the window
	 */
	private boolean canClose = false;
	private final JLabel lblNotesoptional = new JLabel("Notes (Optional)");
	// private final JLabel Feeling = new JLabel("Feeling Chart");

	private CreditWindow() throws IOException {
		this_stage.setTitle("CreditWindow");

		this_stage.setResizable(false);
		this_stage.setSize(583, 578);
		this_stage.addWindowListener(this);
		this_stage
				.setIconImage(Toolkit.getDefaultToolkit().getImage(CreditWindow.class.getResource("/Assets/Icon.png")));
		/*
		 * // final JPanel panel = new JPanel(); final BackgroundPane panel = new
		 * BackgroundPane(); panel.setBounds(-25, -25, this_stage.getWidth(),
		 * this_stage.getHeight());
		 * panel.setBackground(ImageIO.read(Notification.class.getResource(
		 * "/Assets/Icon.png")), 0.13f, (int) (this_stage.getWidth() * 1.3), (int)
		 * (this_stage.getHeight() * 1.3)); this_stage.getContentPane().add(panel,
		 * BorderLayout.CENTER); panel.setLayout(null);
		 *
		 */
		panel = new JPanel();
		panel.setBackground(BG_COLOR);
		this_stage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		final JLabel lblHowAppYou = new JLabel("HowAppYou");
		lblHowAppYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowAppYou.setFont(new Font("Harrington", Font.BOLD, 35));
		lblHowAppYou.setBounds(180, 34, 227, 52);
		panel.add(lblHowAppYou);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 285, 340, 207);
		panel.add(scrollPane);

		final JTextArea TextArea = new JTextArea();
		TextArea.setEnabled(false);
		TextArea.setEditable(false);
		TextArea.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		TextArea.setText("This program is free software; \r\n" + "you can redistribute it and/or modify it.\r\n"
				+ "This app was made to gather information \r\n" + "and help the development of an AI.\r\n" + "\r\n"
				+ "This program is distributed in the\r\n" + " hope that it will be useful, \r\n"
				+ "but WITHOUT ANY WARRANTY; \r\n" + "We hope the notification will not be too stressfull\r\n" + "\r\n"
				+ "You should have received a manual to use\r\n"
				+ "this software, if not go to the official GitHub page.");
		scrollPane.setViewportView(TextArea);

		final JLabel Ester = new JLabel("");
		Ester.setHorizontalAlignment(SwingConstants.CENTER);
		Ester.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Ester.setIcon(new ImageIcon(CreditWindow.class.getResource("/Assets/Icon_mini.png")));
		Ester.setForeground(new Color(0, 0, 178));
		Ester.setFont(new Font("Tahoma", Font.PLAIN, 17));
		Ester.setBounds(561, 527, 16, 16);
		panel.add(Ester);

		final JLabel Authors = new JLabel("Authors:");
		Authors.setHorizontalAlignment(SwingConstants.CENTER);
		Authors.setFont(new Font("Yu Gothic", Font.BOLD, 23));
		Authors.setBounds(28, 142, 95, 28);
		panel.add(Authors);

		final JLabel GH_Chrism1c = new JLabel("https://github.com/Chrism1c");
		GH_Chrism1c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GH_Chrism1c.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GH_Chrism1c.setBounds(201, 143, 227, 28);
		GH_Chrism1c.setForeground(Color.BLUE.darker());
		panel.add(GH_Chrism1c);

		final JLabel GH_0Franky = new JLabel("https://github.com/0Franky");
		GH_0Franky.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GH_0Franky.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GH_0Franky.setBounds(201, 174, 227, 28);
		GH_0Franky.setForeground(Color.BLUE.darker());
		panel.add(GH_0Franky);

		final JLabel GH_Emotions = new JLabel("https://github.com/0Franky/Emotions-Contest");
		GH_Emotions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GH_Emotions.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GH_Emotions.setBounds(201, 215, 358, 28);
		GH_Emotions.setForeground(Color.BLUE.darker());
		panel.add(GH_Emotions);

		final JLabel GitHub = new JLabel("GitHub Page:");
		GitHub.setHorizontalAlignment(SwingConstants.CENTER);
		GitHub.setFont(new Font("Yu Gothic", Font.BOLD, 23));
		GitHub.setBounds(28, 214, 150, 28);
		panel.add(GitHub);

		final JLabel Icon = new JLabel("");
		Icon.setIcon(new ImageIcon(CreditWindow.class.getResource("/Assets/Icon.png")));
		Icon.setBounds(-97, -97, 531, 481);
		panel.add(Icon);

		final JButton btnOk = new JButton("OK");
		btnOk.setBounds(240, 505, 97, 25);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					CreditWindow.getIstance().close();
				} catch (final IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnOk);

		centerStage();

		addActionLinks(GH_Chrism1c, "https://github.com/Chrism1c");
		addActionLinks(GH_0Franky, "https://github.com/0Franky");
		addActionLinks(GH_Emotions, "https://github.com/0Franky/Emotions-Contest");
		addActionLinks(Ester, "https://www.youtube.com/watch?reload=9&v=-i1esxAZqUc");

		this_stage.setVisible(true);

	}

	private static void addActionLinks(final JLabel JL, final String URL) {
		JL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(URL));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

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
	 * Return the unique possible instance of the CreditWindow
	 *
	 * @return The CreditWindow.
	 * @throws IOException Generic I/O error.
	 */
	public static CreditWindow getIstance() throws IOException {
		if (instance == null) {
			synchronized (CreditWindow.class) {
				if (instance == null) {
					instance = new CreditWindow();
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
	 * Close the CreditWindow
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
	 * Show on screen the CreditWindow
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
	 * Set on front of all opened window the CreditWindow
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
	 * Destroys the Window, to free of memory of the CreditWindow when it is closed
	 *
	 * @param Stage stage (the window of the CreditWindow).
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
