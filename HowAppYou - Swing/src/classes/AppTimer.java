package classes;

import java.io.IOException;
import java.util.Timer;

import javafx.application.Platform;
import layout.Notification.Notification;

/**
 * Useful class to set/stop a Timer for Time controlled Notification
 */
public class AppTimer extends Thread {

	/**
	 * AppTimer instance is useful to make AppTimer class "Singleton"
	 */
	private static AppTimer instance = null;

	/**
	 * Boolean used set the possibility to run the Thread or not
	 */
	private boolean runFlag = true;

	/**
	 * Attribute Timer
	 */
	private Timer T = new Timer();

	/**
	 * Constructor fo Timer whit Start Thread
	 */
	private AppTimer() {
		start();
	}

	/**
	 * Reverser fo the flag (Start or Stop Thread)
	 */
	public void invertFlag() {
		runFlag = !runFlag;
	}

	/**
	 * Return the unique possible instance of the AppTimer
	 *
	 * @return The AppTimer.
	 */
	public static AppTimer getIstance() {
		if (instance == null)
			synchronized (AppTimer.class) {
				if (instance == null)
					instance = new AppTimer();
			}
		return instance;
	}

	/**
	 * Stop the Timer when it is running
	 */
	public void stopTimer() {
		T.cancel();
		instance = null;
		System.err.println("Timer Cancellato");
	}

	/**
	 * Start Timer for "min" minutes
	 * 
	 * @param min
	 * @throws InterruptedException Generic Interrupted error
	 */
	public void startTimer(int min) throws InterruptedException {
		System.err.println("Inizio startTimer per = " + min);
		int sec = 60;
		T.schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					try {
						if (runFlag) {
							Notification.getIstance().show();
							Notification.getIstance().toFront();
						} else
							System.err.println("Azione Timer Disattivata");
					} catch (IOException e) {
						// e.printStackTrace();
					}
				});
			}
		}, min * sec * 1000);
	}
}
