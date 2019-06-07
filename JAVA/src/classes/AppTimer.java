package classes;

import java.io.IOException;

import layout.Notification.Notification;

public class AppTimer extends Thread {

	private static AppTimer istance = null; // riferimento all' istanza
	private boolean runFlag = true;

	private AppTimer() {// costruttore
		start();
	}

	public void invertFlag() {
		runFlag = !runFlag;
	}

	public static AppTimer getIstance() {
		if (istance == null)
			synchronized (AppTimer.class) {
				if (istance == null)
					istance = new AppTimer();
			}
		return istance;
	}

	public void startTimer(int min) throws InterruptedException {
		System.err.println("Inizio startTimer per = " + min);
		// TimeUnit.MINUTES.sleep(min);

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {

				try {
					if (runFlag) {
						System.err.println("Apro Notifica");

						Notification.getIstance().show();
						Notification.getIstance().toFront();
					} else
						System.err.println("Azione Timer Disattivata");
				} catch (IOException e) {
					System.err.println("Eccezione = Non apro Notifica");
					// e.printStackTrace();
				}
			}
		}, min * 60 * 1000);
		System.err.println("Fine metodo startTimer ");
	}
}
