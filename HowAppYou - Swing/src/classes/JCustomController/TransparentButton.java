package classes.JCustomController;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class TransparentButton extends JButton {

	public TransparentButton() {

	}

	public TransparentButton(final String text) {
		setText(text);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -3886847236938378133L;

	@Override
	public void updateUI() {
		super.updateUI();
		setVerticalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
		setMargin(new Insets(2, 8, 2, 8));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setForeground(Color.BLACK);
		setIcon(new TranslucentButtonIcon());
	}

	class TranslucentButtonIcon implements Icon {
		private static final int R = 8;
		private int width;
		private int height;

		@Override
		public void paintIcon(final java.awt.Component c, final java.awt.Graphics g, final int x, final int y) {
			if (c instanceof AbstractButton) {
				final AbstractButton b = (AbstractButton) c;
				final Insets i = b.getMargin();
				final int w = ((AbstractButton) c).getWidth();
				final int h = ((AbstractButton) c).getHeight();
				width = w - i.left - i.right;
				height = h - i.top - i.bottom;
				final Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				final Shape area = new RoundRectangle2D.Float(x - i.left, y - i.top, w - 1, h - 1, R, R);
				Color color = new Color(0.6f, 0.6f, 0.6f, .3f);
				final ButtonModel m = b.getModel();
				if (m.isPressed()) {
					color = new Color(0f, 0f, 0f, .3f);
				} else if (m.isRollover()) {
					color = new Color(1f, 1f, 1f, .3f);
				}
				g2.setPaint(color);
				g2.fill(area);
				g2.setPaint(Color.LIGHT_GRAY);
				g2.draw(area);
				g2.dispose();
			}
		}

		@Override
		public int getIconWidth() {
			return Math.max(width, 100);
		}

		@Override
		public int getIconHeight() {
			return Math.max(height, 24);
		}
	}
}
