package classes.csv;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Alert;

/**
 * Class to manage CSV
 */
public class CSV_Manager {

	/**
	 * Attributes to set CSV_file_name, PATH_FOLDER and PATH_CSV
	 */
	private static String CSV_file_name = "";
	private static String PATH_FOLDER = System.getProperty("user.dir") + "\\CSV";
	private static String PATH_CSV = "";

	/**
	 * boolean to check if is started or not
	 */
	private static boolean __started = false;

	/**
	 * Empty Constructor
	 */
	private CSV_Manager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * init to checkFolder end start the saving of csv
	 */
	private static void init() {
		checkFolder();
		__started = true;
		CSV_file_name = "savelog.csv";
		PATH_CSV = PATH_FOLDER + "\\" + getActualCSV_Name();
	}

	/**
	 * Check if Folder exist or not
	 */
	private static void checkFolder() {
		if (new File(PATH_FOLDER).exists() == false) {
			(new File(PATH_FOLDER)).mkdirs();
		}
	}

	/**
	 * Set path and name of the new CSV file
	 * 
	 * @param file
	 */
	public static void setPATH_AND_NAME_CSV(File file) {
		String _path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
		String _fileName = file.getName();

		PATH_FOLDER = _path;
		CSV_file_name = _fileName;
		PATH_CSV = _path + "\\" + _fileName;

		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage()).showAndWait();
		}
		__started = true;
	}

	/**
	 * reset_Manager restarting init()
	 */
	public static void reset_Manager() {
		init();
	}

	/**
	 * getter for the Path of CSV
	 * 
	 * @return PATH_CSV
	 */
	public static String getPATH_CSV() {
		if (!__started) {
			init();
		}
		return PATH_CSV;
	}

	/**
	 * getter for the name of CSV
	 * 
	 * @return CSV_file_name
	 */
	public static String getActualCSV_Name() {
		if (!__started) {
			init();
		}
		return CSV_file_name;
	}
}
