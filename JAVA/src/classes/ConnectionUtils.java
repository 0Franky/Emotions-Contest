package classes;

import java.net.URL;
import java.net.URLConnection;

/**
 * Useful class to check Internet Connection
 */
public class ConnectionUtils {

	/**
	 * Return true if Internet is Available or false if it is not
	 * 
	 * @return status
	 */
	public static boolean isConnectedToInternet() {
		boolean status = false;
		try {
			// URL url = new URL("https://www.geeksforgeeks.org/");
			URL url = new URL("https://www.google.it/");
			URLConnection connection = url.openConnection();
			connection.connect();
			status = true;
			System.out.println("Connection Successful");
		} catch (Exception e) {
			System.out.println("Internet Not Connected");
		}

		return status;
	}

}
