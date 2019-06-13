package classes.database;

import java.net.InetAddress;
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

import org.apache.derby.drda.NetworkServerControl;

import Title.Title;
import classes.DataChart;
import classes.TimeConverter;
import classes.Tuple;
import javafx.scene.control.Alert;

public class Derby {

	// private static String dbURL =
	// "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
	// jdbc:derby://localhost:1527/MyDbTest;create=true");
	// public static String host = "//localhost:1527/";
	// public static String host = "/src/";
	public static String host = "";
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

		System.out.println(getTodaysDataQuery());

		if (!tableExists("DATA")) {
			createTable();
		}

		/* Inserisce il primo valore (Da utilizzare solo la prima volta) */
		String[] input = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(input);

		/* Insericse il secondo valore (Da utilizzare sempre) */
		String[] input2 = { "123456789", "Working", "2", "3", "Closed", "bugfixing" };
		addRow(input2);

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

		if (!tableExists("DATA")) {
			createTable();
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
			System.out.println("Tabella già esistente");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);
	}

	public static boolean tableExists(String tableName) {
		boolean result = false;
		Connection con = getConnectionDB();
		// Statement stmt = null;

		try {
			// stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
			// ResultSet.CONCUR_READ_ONLY);
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			result = rs.next();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}

		closeConnectionDB(con, null);

		return result;
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

	public static void addRow(String input[]) {
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

	public static void deleteRow(String searchedField, String tableField) {
		Connection con = null;
		Statement stmt = null;

		try {
			con = getConnectionDB();
			stmt = con.createStatement();
			String sql = "DELETE FROM DATA WHERE " + searchedField + "=" + tableField;
			System.out.println(sql);
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Insert successfull");
			sqlExcept.printStackTrace();
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
		Connection con = null;
		Statement stmt = null;
		List<Tuple> tuples = new ArrayList<Tuple>();

		try {
			con = getConnectionDB();
			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)

				// int ID = rs.getInt("ID");
				int TIMESTAMP = rs.getInt("TIMESTAMP");
				String ACTIVITY = rs.getString("ACTIVITY");
				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				String STATUS = rs.getString("STATUS");
				String NOTES = rs.getString("NOTES");

				tuples.add(new Tuple(TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES));
			}
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Select Query Failed");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);

		return tuples;
	}

	public static List<DataChart> getDataForChart() {
		Connection con = null;
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

			con = getConnectionDB();
			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// INSERT INTO DATA (TIMESTAMP,ACTIVITY,VALENCE,AROUSAL,STATUS,NOTES)

				int VALENCE = rs.getInt("VALENCE");
				int AROUSAL = rs.getInt("AROUSAL");
				int WEIGHT = rs.getInt("WEIGHT");

				tuples.add(new DataChart(VALENCE, AROUSAL, WEIGHT));
			}
			stmt.close();
		} catch (SQLException sqlExcept) {
			System.out.println("Select Query Failed");
			sqlExcept.printStackTrace();
		}

		closeConnectionDB(con, stmt);

		return tuples;
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
				con.commit();
				con.close();
				try {
					DriverManager.getConnection(protocol + ";shutdown=true");
				} catch (SQLException e) {
					System.out.println(protocol + ";shutdown=true");
				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("ShutdownFailed");
			System.out.println(protocol + ";shutdown=true");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " +
			// e.getMessage()).showAndWait();
		}
	}
}
