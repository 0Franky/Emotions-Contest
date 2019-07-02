package classes;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MoveMouseListener implements MouseListener, MouseMotionListener {
	JComponent target;
	Point start_drag;
	Point start_loc;

	public MoveMouseListener(final JComponent target) {
		this.target = target;
	}

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