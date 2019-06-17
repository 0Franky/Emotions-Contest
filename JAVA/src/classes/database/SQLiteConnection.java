package classes.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
				createTable("DATA");
			}
			if (!tableExists("DATA_TO_SYNC")) {
				createTable("DATA_TO_SYNC");
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

	public static void createTable(String tableName) {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			String sql = "CREATE TABLE " + tableName
					+ " (TIMESTAMP TEXT NOT NULL, ACTIVITY TEXT, VALENCE TEXT, AROUSAL TEXT, STATUS TEXT, NOTES TEXT)";
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

			/*
			 * String sql; sql =
			 * "INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) VALUES ("
			 * + input[0] + ",'" + input[1] + "'," + input[2] + "," + input[3] + ",'" +
			 * input[4] + "','" + input[5] + "')";
			 * 
			 * stmt.executeUpdate(sql);
			 */
			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) VALUES (?,?,?,?,?,?)");
			prepStmt.setString(1, input[0]);
			prepStmt.setString(2, input[1]);
			prepStmt.setString(3, input[2]);
			prepStmt.setString(4, input[3]);
			prepStmt.setString(5, input[4]);
			prepStmt.setString(6, input[5]);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static void addRowToSync(String input[]) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			/*
			 * String sql; sql =
			 * "INSERT INTO DATA_TO_SYNC (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) "
			 * + "VALUES (" + input[0] + ",'" + input[1] + "'," + input[2] + "," + input[3]
			 * + ",'" + input[4] + "','" + input[5] + "')"; stmt.executeUpdate(sql);
			 */

			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO DATA_TO_SYNC (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES) VALUES (?,?,?,?,?,?)");
			prepStmt.setString(1, input[0]);
			prepStmt.setString(2, input[1]);
			prepStmt.setString(3, input[2]);
			prepStmt.setString(4, input[3]);
			prepStmt.setString(5, input[4]);
			prepStmt.setString(6, input[5]);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static final List<Tuple> getAllDataQuery() {
		return runQuery("SELECT * FROM DATA");
	}

	public static final List<Tuple> getAllDataToSyncQuery() {
		return runQuery("SELECT * FROM DATA_TO_SYNC ORDER BY TIMESTAMP ASC");
	}

	public static final void cancelRowToSyncQuery(String Timestamp) {
		runUpdate("DELETE FROM DATA_TO_SYNC WHERE TIMESTAMP = " + Timestamp);
	}

	public static final List<Tuple> getTodaysDataQuery() {
		String startDate = Long.toString(TimeConverter
				.toUnixTime(Date.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
						.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();

		String endDate = Long.toString(
				TimeConverter.toUnixTime(Date.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tomorrow))
						.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));

		return runQuery("SELECT * FROM DATA WHERE TIMESTAMP >= " + startDate + " AND TIMESTAMP < " + endDate);
	}

	public static void runUpdate(String query) {

		Connection con = getConnectionDB();

		try {
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, null);
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
				// int ID = rs.getInt("ID");
				String TIMESTAMP = rs.getString("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				String VALENCE = rs.getString("VALENCE");
				String AROUSAL = rs.getString("AROUSAL");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				tuples.add(new Tuple(TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, stmt);

		return tuples;
	}

	public static List<DataChart> getDataForChart(int day) {

		Connection con = getConnectionDB();
		Statement stmt = null;
		List<DataChart> tuples = new ArrayList<>();

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -day);
			Date pastDate = calendar.getTime();
			System.out.println("pastDate : -" + day + " giorni =" + pastDate);

			String startDate = Long
					.toString(
							TimeConverter
									.toUnixTime(Date
											.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(pastDate))
													.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
											.getTime()));

			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar2.getTime();
			System.out.println("tomorrow : +1 giorno =" + tomorrow);

			String endDate = Long
					.toString(
							TimeConverter
									.toUnixTime(Date
											.from(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tomorrow))
													.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
											.getTime()));

			String query = ("SELECT DISTINCT VALENCE, AROUSAL, COUNT(*) AS WEIGHT FROM DATA WHERE TIMESTAMP >= "
					+ startDate + " AND TIMESTAMP < " + endDate
					+ " AND STATUS='POPUP_CLOSED' GROUP BY VALENCE, AROUSAL");

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