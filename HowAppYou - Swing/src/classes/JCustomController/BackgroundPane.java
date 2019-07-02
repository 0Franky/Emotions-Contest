package classes.JCustomController;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BackgroundPane extends JPanel implements MouseListener, MouseMotionListener {

	private BufferedImage frontImage;
	private float alpha = 1;

	public BackgroundPane(final BufferedImage img) {
		setBackground(img);
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

	public void setBackground(final BufferedImage img, final float newAlpha, final boolean autosize) {
		try {
			// frontImage =
			// ImageIO.read(Notification.class.getResource("/Assets/Pleasantness/V2.png"));
			frontImage = img;

			if (autosize) {
				frontImage = resize(img, getWidth(), getHeight());
			}
			setAlpha(newAlpha);
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	public BackgroundPane(final JComponent target) {
		// Drag and Drop
		this.target = target;
	}

	public void setBackground(final BufferedImage img, final float newAlpha, final int width, final int height) {
		try {
			// frontImage =
			// ImageIO.read(Notification.class.getResource("/Assets/Pleasantness/V2.png"));
			frontImage = resize(img, width, height);
			setAlpha(newAlpha);
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	public static BufferedImage resize(final BufferedImage img, final int newW, final int newH) {
		final Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		final BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
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
		// try {
		// return new Dimension(frontImage.getWidth(), frontImage.getHeight());
		// } catch (final Exception e) {
		return new Dimension(getWidth(), getHeight());
		// }
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

	/// DRAG AND DROP PANEL //

	JComponent target;
	Point start_drag;
	Point start_loc;

	public static JFrame getFrame(final Container target) {
		if (target instanceof JFrame) {
			return (JFrame) target;
		}
		return getFrame(target.getParent());
	}

	Point getScreenLocation(final MouseEvent e) {
		final Point cursor = e.getPoint();
		final Point target_location = this.target.getLocationOnScreen();
		return new Point((int) (target_location.getX() + cursor.getX()),
				(int) (target_location.getY() + cursor.getY()));
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		this.start_drag = this.getScreenLocation(e);
		this.start_loc = this.getFrame(this.target).getLocation();
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		final Point current = this.getScreenLocation(e);
		final Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
				(int) current.getY() - (int) start_drag.getY());
		final JFrame frame = this.getFrame(target);
		final Point new_location = new Point((int) (this.start_loc.getX() + offset.getX()),
				(int) (this.start_loc.getY() + offset.getY()));
		frame.setLocation(new_location);
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

}