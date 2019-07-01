package classes;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	private static boolean runFlag = false;

	/**
	 *
	 * ScheduledExecutorService
	 */
	final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

	/**
	 * Constructor fo Timer whit Start Thread
	 */
	private AppTimer() {
		start();
	}

	/**
	 * Reverser fo the flag (Start or Stop Thread)
	 */
	public static void invertFlag() {
		runFlag = !runFlag;
	}

	/**
	 * Return the unique possible instance of the AppTimer
	 *
	 * @return The AppTimer.
	 */
	public static AppTimer getIstance() {
		if (instance == null) {
			synchronized (AppTimer.class) {
				if (instance == null) {
					instance = new AppTimer();
				}
			}
		}
		return instance;
	}

	/**
	 * Stop the Timer when it is running
	 */
	public void stopTimer() {
		runFlag = false;
		ses.shutdown();
		System.err.println("TIMER SHUTDOWN");
		instance = null;
	}

	/**
	 * Start Timer for "min" minutes
	 *
	 * @param min
	 * @throws InterruptedException Generic Interrupted error
	 */
	public void startTimer(final int min) throws InterruptedException {
		System.err.println("Inizio startTimer per = " + min);
		final int sec = min * 60;

		ses.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (!runFlag) {
					invertFlag();
				} else {
					System.out.println(new Date());
					try {
						Notification.getIstance();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ses.shutdown();
					System.out.println("TIMER SHUTDOWN");
				}
			}
		}, 0, sec, TimeUnit.SECONDS);
	}
}
