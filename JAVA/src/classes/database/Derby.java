package classes.database;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.drda.NetworkServerControl;

import Title.Title;
import classes.Tuple;

public class Derby {

	// private static String dbURL =
	// "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
	// jdbc:derby://localhost:1527/MyDbTest;create=true");
	// public static String host = "//localhost:1527/";
	public static String host = "/src/";
	public static String protocol = "jdbc:derby:";
	private static String nomeDB = Title.APPLICATION_NAME + "DB.db";
	// private static String tableName = "DATA";
	// jdbc Connection
	// private static Connection conn = null;
	// private static Statement stmt = null;
	private static NetworkServerControl server = null;

	public static void main(String[] args) {
		/*
		 * // getConnectionDB(); dropTable(); createTable();
		 * 
		 * // TEST CON TIMESTAMP RANDOM int j = 100000000; int n = 123456789 - j; Random
		 * random = new Random(); int k = random.nextInt(n) + j;// Valori compresi tra j
		 * e n
		 * 
		 * String[] input = { k + "", "Working", "2", "3", "Closed", "bugfixing" };
		 * addRow(input); runQuery(); // closeConnectionDB();
		 *
		 */

		createTable();

		/* Inserisce il primo valore (Da utilizzare solo la prima volta) */
		String[] input = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(input);

		/* Insericse il secondo valore (Da utilizzare sempre) */
		String[] input2 = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(input2);

		/* Stampa la Tabella */
		runQuery().print();
	}

	private static Connection getConnectionDB() {
		Connection con = null;

		try {
			server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
			server.start(null);
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			// Get a connection
			con = DriverManager.getConnection(protocol + host + nomeDB + ";create=true");
			System.out.println("Acceso al Database Riuscito\n" + protocol + host + nomeDB + ";create=true");
		} catch (Exception except) {
			System.out.println("Acceso al Database non Riuscito");
			except.printStackTrace();
		}

		return con;
	}

	public static void createTable() {
		Connection con = null;
		Statement stmt = null;

		try {
			con = getConnectionDB();
			stmt = con.createStatement();
			String sql = "CREATE TABLE DATA "
					+ "(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ " TIMESTAMP INT 		NOT NULL, " + " ACTIVITY           VARCHAR(30), "
					+ " VALENCE            INT, " + " AROUSAL            INT, " + " STATUS             VARCHAR(30), "
					+ " NOTES         VARCHAR(30))";
			stmt.execute(sql);
			System.out.println("Tabella creata con successo");
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Tabella gi� esistente");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static void dropTable() {
		Connection con = null;
		Statement stmt = null;

		try {
			con = getConnectionDB();
			stmt = con.createStatement();
			String sql = "DROP TABLE DATA ";
			stmt.execute(sql);
			System.out.println("Tabella cancellata con successo");
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Tabella non cancellata");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	private static void addRow(String input[]) {
		Connection con = null;
		Statement stmt = null;

		try {
			con = getConnectionDB();
			stmt = con.createStatement();
			String sql = "INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) " + "VALUES (" + input[0]
					+ ",'" + input[1] + "'," + input[2] + "," + input[3] + ",'" + input[4] + "','" + input[5] + "')";
			System.out.println(sql);
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Insert successfull");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	private static Tuple runQuery() {
		Connection con = null;
		Statement stmt = null;
		Tuple tuple = null;

		try {
			con = getConnectionDB();
			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM DATA");

			while (rs.next()) {
				// INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)

				int ID = rs.getInt("ID");
				int TIMESTAMP = rs.getInt("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				tuple = new Tuple(ID, TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES);
			}
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Select Query Failed");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);

		return tuple;
	}

	private static void closeConnectionDB(Connection con, Statement stmt) {

		try {
			if (server != null) {
				server.shutdown();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				DriverManager.getConnection(protocol + host + nomeDB + ";shutdown=true");
				System.out.println(protocol + nomeDB + ";shutdown=true");
				con.commit();
				con.close();
			}
		} catch (Exception e) {
			System.out.println("ShutdownFailed");
			System.out.println(protocol + host + nomeDB + ";shutdown=true");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " +
			// e.getMessage()).showAndWait();
		}
	}
}
