package classes.JCustomController;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BackgroundPane extends JPanel {

	private BufferedImage frontImage;
	private float alpha = 1;

	public BackgroundPane(final BufferedImage img) {
		try {
			// frontImage =
			// ImageIO.read(Notification.class.getResource("/Assets/Pleasantness/V2.png"));
			frontImage = img;
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	public BackgroundPane() {
	}

	public void setBackground(final BufferedImage img) {
		try {
			// frontImage =
			// ImageIO.read(Notification.class.getResource("/Assets/Pleasantness/V2.png"));
			frontImage = img;
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	public void setBackground(final BufferedImage img, final float newAlpha) {
		try {
			// frontImage =
			// ImageIO.read(Notification.class.getResource("/Assets/Pleasantness/V2.png"));
			frontImage = img;
			setAlpha(newAlpha);
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		try {
			return new Dimension(frontImage.getWidth(), frontImage.getHeight());
		} catch (final Exception e) {
			return new Dimension(getWidth(), getHeight());
		}
	}

	public void setAlpha(final float alpha) {
		this.alpha = alpha;
		repaint();
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2 = (Graphics2D) g;

		// Paint foreground image with appropriate alpha value

		final Composite old = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		final int x = (getWidth() - frontImage.getWidth()) / 2;
		final int y = (getHeight() - frontImage.getHeight()) / 2;
		g2.drawRenderedImage(frontImage, AffineTransform.getTranslateInstance(x, y));
		g2.setComposite(old);
	}

	private static void createAndShowUI() throws IOException {
		final BackgroundPane app = new BackgroundPane(
				ImageIO.read(BackgroundPane.class.getResource("/Assets/Pleasantness/V2.png")));

		final JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final JSlider source = (JSlider) e.getSource();
				app.setAlpha(source.getValue() / 100f);
			}
		});
		slider.setValue(100);

		final JFrame frame = new JFrame("Transparent Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(app);
		frame.add(slider, BorderLayout.SOUTH);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					createAndShowUI();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}