package classes.csv;

import java.io.File;

public class CSV_Manager {

	private static String CSV_file_name = "";
	private static String PATH_FOLDER = System.getProperty("user.dir") + "\\CSV";
	private static String PATH_CSV = "";

	private static boolean __started = false;

	private CSV_Manager() {
		// TODO Auto-generated constructor stub
	}

	private static void init() {
		checkFolder();
		__started = true;
		CSV_file_name = "savelog.csv";
		PATH_CSV = PATH_FOLDER + "\\" + getActualCSV_Name();
	}

	private static void checkFolder() {
		if (new File(PATH_FOLDER).exists() == false) {
			(new File(PATH_FOLDER)).mkdirs();
		}
	}

	public static String getPATH_CSV() {
		if (!__started) {
			init();
		}

		return PATH_CSV;
	}

	public static String getActualCSV_Name() {
		if (!__started) {
			init();
		}

		return CSV_file_name;
	}

}
