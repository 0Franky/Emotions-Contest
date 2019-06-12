package classes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Title.Title;
import classes.Tuple;
import javafx.scene.control.Alert;

public class SQLiteConnection {

	public static void main(String args[]) { // getConnectionDB();

		dropTable();
		createTable();

		/* Inserisce il primo valore (Da utilizzare solo la prima volta) */
		String[] input = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(true, input);

		/* Insericse il secondo valore (Da utilizzare sempre) */
		String[] input2 = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(false, input2);

		/* Stampa la Tabella */
		runQuery().print();

	}

	private static Connection getConnectionDB() {
		Connection con = null;

		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			con = DriverManager.getConnection("jdbc:sqlite:src/" + Title.APPLICATION_NAME + "DB.db");

			// System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err
					.println("Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage());
			new Alert(Alert.AlertType.ERROR,
					"Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage())
							.showAndWait();
		}

		return con;
	}

	public static void dropTable() {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			String sql = "DROP TABLE DATA";

			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			// //System.exit(0);
		}

		closeConnectionDB(con, stmt);
	}

	public static void createTable() {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			String sql = "CREATE TABLE DATA " + "(ID INTEGER PRIMARY KEY  AUTOINCREMENT, "
					+ " TIMESTAMP INT 		NOT NULL, " + " ACTIVITY           TEXT, " + " VALENCE            INT, "
					+ " AROUSAL            INT, " + " STATUS            INT, " + " NOTES        TEXT)";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, stmt);
	}

	public static void addRow(boolean first, String input[]) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		String id = "ID,";
		String f = "1,";
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			String sql;

			if (first) { // Per impostare l'AUTOINCREMENT
				sql = "INSERT INTO DATA (" + id + "TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) " + "VALUES (" + f
						+ input[0] + ",'" + input[1] + "'," + input[2] + "," + input[3] + ",'" + input[4] + "','"
						+ input[5] + "');";
			} else {
				sql = "INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) " + "VALUES (" + input[0]
						+ ",'" + input[1] + "'," + input[2] + "," + input[3] + ",'" + input[4] + "','" + input[5]
						+ "');";
			}
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static Tuple runQuery() {

		Connection con = getConnectionDB();
		Statement stmt = null;
		Tuple tuple = null;

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM DATA;");

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
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, stmt);

		return tuple;
	}

	private static void closeConnectionDB(Connection con, Statement stmt) {
		try {
			stmt.close();
			con.commit();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " +
			// e.getMessage()).showAndWait();
		}
	}

}