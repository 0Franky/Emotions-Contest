package layout;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import layout.Notification.Notification;

public class AppTimer {

	private static AppTimer istance = null; // riferimento all' istanza
	private Timer T = new Timer();

	private AppTimer() {
	}// costruttore

	public static AppTimer getIstance() {
		if (istance == null)
			synchronized (AppTimer.class) {
				if (istance == null)
					istance = new AppTimer();
			}
		return istance;
	}

	public void setTimer(int min) throws IOException, InterruptedException {
		/*
		 * T.schedule(new TimerTask() {
		 * 
		 * @Override public void run() { System.out.println("Tempo scaduto, passati = "
		 * + min + " minuti");
		 * 
		 * } }, min * 60 * 1000); // Minuti * Secondi * Millisecondi
		 */
		TimeUnit.MINUTES.sleep(min);
		Notification.getIstance().show();
	}

}
