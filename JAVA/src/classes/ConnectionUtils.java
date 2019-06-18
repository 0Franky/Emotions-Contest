package classes;

import java.net.URL;
import java.net.URLConnection;

public class ConnectionUtils {

	public static boolean isConnectedToInternet() {
		boolean status = false;
		try {
			URL url = new URL("https://www.geeksforgeeks.org/");
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
