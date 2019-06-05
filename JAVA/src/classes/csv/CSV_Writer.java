/**
* 
*/
package classes.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Francesco
 *
 */
class CSV_Writer implements ICSV_Writer {

	private CSV_Writer() {

	}

	// private static CSV_Writer singleton = null;

	public static CSV_Writer getInstance() {
		// if (singleton == null) {
		// singleton = new CSV_Writer();
		// }
		// return singleton;

		return new CSV_Writer();
	}

	/**
	 * Descrizione: Questa classe si occupa di scrivere fogli CSV.
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	private synchronized void writeCSV(List<String> data) throws IOException {

		FileWriter csvWriter = new FileWriter(CSV_Manager.getPATH_CSV(), true);

		for (String rowData : data) {

			if (rowData.contains("\"")) {
				rowData = rowData.replace("\"", "\"\"");
			}

			if (rowData.contains(";")) {
				rowData = "\"" + rowData + "\"";
			}

			csvWriter.append(rowData + ";");
		}
		csvWriter.append("\n");

		csvWriter.flush();
		csvWriter.close();
	}

	public void write(List<String> data) throws IOException {
		if (!data.isEmpty()) {
			writeCSV(data);
		}
	}

	public void write(String data) throws IOException {
		if (!data.isEmpty()) {
			writeCSV(Arrays.asList(data));
		}
	}

}
