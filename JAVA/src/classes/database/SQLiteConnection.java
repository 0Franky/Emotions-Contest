package classes.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Title.Title;
import classes.DataChart;
import classes.TimeConverter;
import classes.Tuple;
import javafx.scene.control.Alert;

public class SQLiteConnection {

	public static void main(String args[]) {
		// getConnectionDB();

		System.out.println(getTodaysDataQuery());

		// dropTable();
		if (!tableExists("DATA")) {
			createTable();
		}

		/* Inserisce il primo valore (Da utilizzare solo la prima volta) */
		String[] input = { "1560376810", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(input);

		/* Insericse il secondo valore (Da utilizzare sempre) */
		String[] input2 = { "1460376820", "Relaxing", "2", "3", "Closed", "CoffèTime" };
		addRow(input2);

		System.out.println("========= COUNT(*) in DATA: =========");
		int nTuple = runQuery(getAllDataQuery()).size();
		System.out.println("Nella tabella sono presenti: " + nTuple + " Tuple");

		/* Stampa la Tabella */
		runQuery(getAllDataQuery()).get(0).print();
		System.out.println("========= TODAY: =========");
		List<Tuple> tuples = runQuery(getTodaysDataQuery());
		if (tuples.size() > 0) {
			for (Tuple tuple : tuples) {
				tuple.print();
			}
		}
	}

	// public static String host = "/src/";
	public static String host = "";
	public static boolean existTable = false;
	public static boolean checking = false;

	private static Connection getConnectionDB() {
		Connection con = null;

		try {
			System.out.println("Try to open database: jdbc:sqlite:" + host + Title.APPLICATION_NAME + "DB.db");
			Class.forName("org.sqlite.JDBC").newInstance();
			con = DriverManager.getConnection("jdbc:sqlite:" + host + Title.APPLICATION_NAME + "DB.db");

			// System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err
					.println("Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage());
			new Alert(Alert.AlertType.ERROR,
					"Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage())
							.showAndWait();
		}

		if (existTable == false && checking == false) {
			checking = true;
			if (!tableExists("DATA")) {
				createTable();
			}
			existTable = true;
		}

		return con;
	}

	public static boolean tableExists(String tableName) {
		boolean result = false;
		Connection con = getConnectionDB();

		try {
			con.setAutoCommit(false);
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			rs.next();
			result = rs.getRow() > 0;
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, null);

		return result;
	}

	public static void dropTable() {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
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
			con.setAutoCommit(false);
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

	public static void addRow(String input[]) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			String sql;
			sql = "INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) " + "VALUES (" + input[0] + ",'"
					+ input[1] + "'," + input[2] + "," + input[3] + ",'" + input[4] + "','" + input[5] + "');";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static final String getAllDataQuery() {
		return "SELECT * FROM DATA";
	}

	public static final String getTodaysDataQuery() {
		String startDate = Long.toString(TimeConverter
				.toUnixTime(Date.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
						.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();

		String endDate = Long.toString(
				TimeConverter.toUnixTime(Date.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tomorrow))
						.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));

		return ("SELECT * FROM DATA WHERE TIMESTAMP >= " + startDate + " AND TIMESTAMP < " + endDate);
	}

	public static List<Tuple> runQuery(String query) {

		Connection con = getConnectionDB();
		Statement stmt = null;
		List<Tuple> tuples = new ArrayList<Tuple>();

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)
				int ID = rs.getInt("ID");
				int TIMESTAMP = rs.getInt("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				tuples.add(new Tuple(ID, TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, stmt);

		return tuples;
	}

	public static List<DataChart> getDataForChart() {

		Connection con = getConnectionDB();
		Statement stmt = null;
		List<DataChart> tuples = new ArrayList<>();

		try {
			String startDate = Long
					.toString(
							TimeConverter
									.toUnixTime(Date
											.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
													.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
											.getTime()));

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar.getTime();

			String endDate = Long
					.toString(
							TimeConverter
									.toUnixTime(Date
											.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tomorrow))
													.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
											.getTime()));

			String query = ("SELECT DISTINCT VALENCE, AROUSAL, COUNT(*) AS WEIGHT FROM DATA WHERE TIMESTAMP >= "
					+ startDate + " AND TIMESTAMP < " + endDate);

			con.setAutoCommit(false);

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				int WEIGHT = rs.getInt("WEIGHT");

				tuples.add(new DataChart(VALENCE, AROUSAL, WEIGHT));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, stmt);

		return tuples;
	}

	private static void closeConnectionDB(Connection con, Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				// con.setAutoCommit(true);
				con.commit();
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " +
			// e.getMessage()).showAndWait();
		}
	}

}