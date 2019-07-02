package classes.JCustomController;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ControlButton extends JButton {

	public final static Color BUTTON_TOP_GRADIENT = new Color(176, 176, 176);
	public final static Color BUTTON_BOTTOM_GRADIENT = new Color(156, 156, 156);
	public static int SMOOTH = 15;

	public ControlButton(final String text) {
		setText(text);
	}

	public ControlButton(final String text, final int smooth) {
		setText(text);
		this.SMOOTH = smooth;
	}

	public void setSmooth(final int smooth) {
		this.SMOOTH = smooth;
	}

	public ControlButton() {
	}

	@Override
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g.create();
		final RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);

		g2.setPaint(new GradientPaint(new Point(0, 0), BUTTON_TOP_GRADIENT, new Point(0, getHeight()),
				BUTTON_BOTTOM_GRADIENT));
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), SMOOTH, SMOOTH);
		g2.dispose();
	}

	public static void main(final String args[]) {
		final JFrame frame = new JFrame("test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ControlButton btn = new ControlButton("Hello, World");
		frame.getContentPane().add(btn);
		frame.pack();
		frame.setVisible(true);
		btn.setSmooth(80);
		btn.setText("ciaooooooooooooooooooooooooooooooooooooooo");
	}
}