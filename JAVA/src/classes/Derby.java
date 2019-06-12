package classes.csv;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.derby.drda.NetworkServerControl;

public class Derby {

	// private static String dbURL =
	// "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
	// jdbc:derby://localhost:1527/MyDbTest;create=true");
	public static String host = "//localhost:1527/";
	public static String protocol = "jdbc:derby:";
	private static String nomeDB = "EmotionDB";
	// private static String tableName = "DATA";
	// jdbc Connection
	private static Connection conn = null;
	private static Statement stmt = null;
	private static NetworkServerControl server = null;

	public static void main(String[] args) throws Exception {
		createConnection();
		dropTable();
		createTable();

		// TEST CON TIMESTAMP RANDOM
		int j = 100000000;
		int n = 123456789 - j;
		Random random = new Random();
		int k = random.nextInt(n) + j;// Valori compresi tra j e n

		String[] input = { k + "", "Working", "2", "3", "Closed", "bugfixing" };
		insert(input);
		selectAll();
		shutdown();
	}

	private static void createConnection() {
		try {
			server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
			server.start(null);
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(protocol + host + nomeDB + ";create=true");
			System.out.println("Acceso al Database Riuscito");
		} catch (Exception except) {
			System.out.println("Acceso al Database non Riuscito");
			except.printStackTrace();
		}
	}

	public static void createTable() {
		try {
			stmt = conn.createStatement();
			String sql = "CREATE TABLE DATA " + "(TIMESTAMP INT 		NOT NULL, "
					+ " ACTIVITY           VARCHAR(30), " + " VALENCE            INT, " + " AROUSAL            INT, "
					+ " STATUS             VARCHAR(30), " + " NOTES         VARCHAR(30))";
			stmt.execute(sql);
			System.out.println("Tabella creata con successo");
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Tabella già esistente");
			sqlExcept.printStackTrace();
		}
	}

	public static void dropTable() {
		try {
			stmt = conn.createStatement();
			String sql = "DROP TABLE DATA ";
			stmt.execute(sql);
			System.out.println("Tabella cancellata con successo");
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Tabella non cancellata");
			sqlExcept.printStackTrace();
		}
	}

	private static void insert(String input[]) {
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) " + "VALUES (" + input[0]
					+ ",'" + input[1] + "'," + input[2] + "," + input[3] + ",'" + input[4] + "','" + input[5] + "')";
			System.out.println(sql);
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Insert successfull");
			sqlExcept.printStackTrace();
		}
	}

	private static void selectAll() {
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM DATA");

			while (rs.next()) {
				// INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)
				// int ID = rs.getInt("ID");
				int TIMESTAMP = rs.getInt("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				// System.out.println("ID = " + ID);
				System.out.println("TIMESTAMP = " + TIMESTAMP);
				System.out.println("ACTIVITY = " + ACTIVITY);
				System.out.println("VALENCE = " + VALENCE);
				System.out.println("AROUSAL = " + AROUSAL);
				System.out.println("STATUS = " + STATUS);
				System.out.println("NOTES = " + NOTES);
				System.out.println();
			}
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Select Query Failed");
			sqlExcept.printStackTrace();
		}
	}

	private static void shutdown() throws Exception {

		try {
			if (server != null) {
				server.shutdown();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(protocol + nomeDB + ";shutdown=true");
				System.out.println(protocol + nomeDB + ";shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {
			System.out.println("ShutdownFailed");
			System.out.println(protocol + nomeDB + ";shutdown=true");
		}
	}
}
