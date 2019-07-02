package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BlackBox extends JPanel {
	@Override
	public void paintComponent(final Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public static void main(final String[] args) {
		final JFrame this_stage = new JFrame();
		this_stage.setPreferredSize(new Dimension(300, 280));

		final BlackBox panel0 = new BlackBox();
		this_stage.getContentPane().add(panel0);
		this_stage.setUndecorated(true);

		final MoveMouseListener panel = new MoveMouseListener(panel0);
		panel0.addMouseListener(panel);
		panel0.addMouseMotionListener(panel);

		this_stage.pack();
		this_stage.setVisible(true);
	}
}
