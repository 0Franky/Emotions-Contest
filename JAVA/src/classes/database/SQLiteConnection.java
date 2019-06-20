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

	/**
	 * Attributes for SQLiteConnection String host = IP of local db host boolean
	 * existTable = check Table existence boolean checking = check something
	 * Connection con = Create a connection object for SQLite connection
	 */
	private static String host = "";
	private static boolean existTable = false;
	private static boolean checking = false;
	private static Connection con = null;

	/**
	 * Start a new Connection on Title.APPLICATION_NAME + "DB.db"
	 * 
	 * @return con
	 */
	public static Connection getConnectionDB() {
		if (con == null) {

			try {
				System.out.println("Try to open database: jdbc:sqlite:" + host + Title.APPLICATION_NAME + "DB.db");
				Class.forName("org.sqlite.JDBC").newInstance();
				con = DriverManager.getConnection("jdbc:sqlite:" + host + Title.APPLICATION_NAME + "DB.db");

				// System.out.println("Opened database successfully");
			} catch (Exception e) {
				System.err.println(
						"Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage());
				new Alert(Alert.AlertType.ERROR,
						"Method: getConnectionDB() | Class  : SQLiteConnection | msg system : " + e.getMessage())
								.showAndWait();
			}
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

	/**
	 * Check if Table already Exists
	 * 
	 * @param tableName
	 * @return
	 */
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
		forceUpdateDB(con);

		return result;
	}

	/**
	 * Destroy the main table if Exists
	 */
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
		}

		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * Create the Main table
	 * 
	 * @param String    tableName
	 * @param tableName
	 */
	public static void createTable(String tableName) {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			String sql = "CREATE TABLE " + tableName
					+ " (TIMESTAMP TEXT NOT NULL, ACTIVITY TEXT, VALENCE TEXT, AROUSAL TEXT, DOMINANCE TEXT, PRODUCTIVITY TEXT, STATUS TEXT, NOTES TEXT)";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * add a Row into DB (row is in String input[] format)
	 * 
	 * @param input
	 */
	public static void addRow(String input[]) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,DOMINANCE,PRODUCTIVITY,STATUS,NOTES) "
							+ "VALUES (?,?,?,?,?,?,?,?)");

			for (int i = 0; i < input.length; i++) {
				prepStmt.setString(i + 1, input[i]);
			}

			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}
		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * add a addRowToSync into DB (row is in String input[] format)
	 * 
	 * @param input
	 */
	public static void addRowToSync(String input[]) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			PreparedStatement prepStmt = con.prepareStatement(
					"INSERT INTO DATA_TO_SYNC (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,DOMINANCE,PRODUCTIVITY,STATUS,NOTES) "
							+ "VALUES (?,?,?,?,?,?,?,?)");

			for (int i = 0; i < input.length; i++) {
				prepStmt.setString(i + 1, input[i]);
			}

			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * SELECT all FROM DATA Table
	 * 
	 * @return List<Tuple>
	 */
	public static final List<Tuple> getAllDataQuery() {
		return runQuery("SELECT * FROM DATA");
	}

	/**
	 * SELECT all FROM DATA_TO_SYNC Table
	 * 
	 * @return List<Tuple>
	 */
	public static final List<Tuple> getAllDataToSyncQuery() {
		return runQuery("SELECT * FROM DATA_TO_SYNC ORDER BY TIMESTAMP ASC");
	}

	/**
	 * cancel a Row with "Timestamp" into DATA_TO_SYNC Table
	 * 
	 * @param Timestamp
	 */
	public static final void cancelRowToSyncQuery(String Timestamp) {
		runUpdate("DELETE FROM DATA_TO_SYNC WHERE TIMESTAMP = " + Timestamp);
	}

	/**
	 * get all Tuples made Today
	 * 
	 * @return List<Tuple>
	 */
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

	/**
	 * Execute an Update Query
	 * 
	 * @param query
	 */
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

		forceUpdateDB(con);
	}

	/**
	 * Execute a Generic Query
	 * 
	 * @param query
	 * @return List<Tuple>
	 */
	public static List<Tuple> runQuery(String query) {

		Connection con = getConnectionDB();
		Statement stmt = null;
		List<Tuple> tuples = new ArrayList<Tuple>();

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String TIMESTAMP = rs.getString("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				String VALENCE = rs.getString("VALENCE");
				String AROUSAL = rs.getString("AROUSAL");
				String DOMINANCE = rs.getString("DOMINANCE");
				String PRODUCTIVITY = rs.getString("PRODUCTIVITY");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				tuples.add(new Tuple(TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, DOMINANCE, PRODUCTIVITY, STATUS, NOTES));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeStatement(stmt);
		forceUpdateDB(con);

		return tuples;
	}

	/**
	 * get all Tuples made from [Today-day,Today]
	 * 
	 * @param day
	 * @return List<DataChart>
	 */
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
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				int WEIGHT = rs.getInt("WEIGHT");

				tuples.add(new DataChart(VALENCE, AROUSAL, WEIGHT));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeStatement(stmt);
		forceUpdateDB(con);

		return tuples;
	}

	/**
	 * create the Sheet Table
	 */
	private static void createSheetTable() {
		Connection con = getConnectionDB();
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			String sql = "CREATE TABLE SHEET (SPID TEXT NOT NULL)";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * Set spid into Sheet Table
	 * 
	 * @param spid
	 */
	public static void setSheet(String spid) {
		Connection con = getConnectionDB();
		Statement stmt = null;
		try {
			if (!tableExists("SHEET")) {
				createSheetTable();
			}

			con.setAutoCommit(false);
			stmt = con.createStatement();
			PreparedStatement prepStmt = con.prepareStatement("INSERT INTO SHEET (SPID) VALUES (?)");
			prepStmt.setString(1, spid);
			prepStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			e.printStackTrace();
		}

		closeStatement(stmt);
		forceUpdateDB(con);
	}

	/**
	 * getter for Spid into SHEET Table
	 * 
	 * @return spid
	 */
	public static String getSpid() {

		Connection con = getConnectionDB();
		Statement stmt = null;
		String spid = "";

		if (tableExists("SHEET")) {
			try {
				con.setAutoCommit(false);

				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT SPID FROM SHEET");

				while (rs.next()) {
					spid = rs.getString("SPID");
				}
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
			}

			closeStatement(stmt);
		}

		forceUpdateDB(con);

		return spid;
	}

	/**
	 * close current Statement
	 * 
	 * @param stmt
	 */
	private static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				// e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}

	/**
	 * close Connection to database
	 * 
	 * @param con
	 * @param stmt
	 */
	public static void closeConnectionDB(Connection con, Statement stmt) {
		try {
			closeStatement(stmt);

			if (con != null) {
				con.commit();
				con.close();
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Force the execution of a commit when database connection is still opened
	 * 
	 * @param con
	 */
	public static void forceUpdateDB(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
