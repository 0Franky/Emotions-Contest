package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLite {
	public static void main(String args[]) {
		createDB();

		// dropTable();
		// createTable();
		/*
		 * // Inserisce il primo valore (Da utilizzare solo la prima volta) String[]
		 * input = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		 * insertDB(true, input);
		 * 
		 * // Insericse il secondo valore (Da utilizzare sempre) String[] input2 = {
		 * "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		 * insertDB(false,input2);
		 * 
		 * // Stampa la Tabella selectQuery();
		 */
	}

	public static void createDB() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = // DriverManager.getConnection("jdbc:sqlite::resource:resources:EmotionDB.db");
					DriverManager.getConnection("jdbc:sqlite::resource:" + "/EmotionDB.db");
			// DriverManager.getConnection("jdbc:sqlite::resource:EmotionDB.dbC:");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public static void dropTable() {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:EmotionDB.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "DROP TABLE DATA";

			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table dropped successfully");
	}

	public static void createTable() {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = // DriverManager.getConnection("jdbc:sqlite::resource:resources:EmotionDB.db");
					DriverManager.getConnection("jdbc:sqlite::resource:file:/resources/EmotionDB.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE DATA " + "(ID INTEGER PRIMARY KEY  AUTOINCREMENT, "
					+ " TIMESTAMP INT 		NOT NULL, " + " ACTIVITY           TEXT, " + " VALENCE            INT, "
					+ " AROUSAL            INT, " + " STATUS            INT, " + " NOTES        TEXT)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public static void insertDB(boolean first, String input[]) {
		Connection c = null;
		Statement stmt = null;
		String id = "ID,";
		String f = "1,";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:EmotionDB.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

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
			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);

		}
		System.out.println("Records created successfully");
	}

	public static void selectQuery() {

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:EmotionDB.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
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

				System.out.println("ID = " + ID);
				System.out.println("TIMESTAMP = " + TIMESTAMP);
				System.out.println("ACTIVITY = " + ACTIVITY);
				System.out.println("VALENCE = " + VALENCE);
				System.out.println("AROUSAL = " + AROUSAL);
				System.out.println("STATUS = " + STATUS);
				System.out.println("NOTES = " + NOTES);
				System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

}