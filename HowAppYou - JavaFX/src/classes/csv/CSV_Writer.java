/**
* 
*/
package classes.csv;

import java.awt.EventQueue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Francesco
 *
 */
class CSV_Writer implements ICSV_Writer {

	/**
	 * Empty Constructor for CSV_Writer
	 */
	private CSV_Writer() {

	}

	/**
	 * Return the unique possible instance of the CSV_Writer
	 * 
	 * @return new CSV_Writer
	 */
	public static CSV_Writer getInstance() {

		return new CSV_Writer();
	}

	private static final int MAX_ATTEMPT = 100;

	/**
	 * Descrizione: Questa classe si occupa di scrivere fogli CSV.
	 * 
	 * @return
	 * 
	 * @throws IOException @throws
	 */
	private synchronized void writeCSV(List<String> data) {

		boolean status = false;
		int attempt = 0;

		do {

			if (attempt >= MAX_ATTEMPT) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Attenzione un programma sta utilizzando il file di salvataggio da troppo tempo!%n"
						+ "Affinchè il programma continui a funzionare correttamente, chiudere tale programma!");
				alert.showAndWait();
			}

			try {
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

				status = true;
			} catch (Exception e) {
				attempt++;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (!status);
	}

	/**
	 * write List<String> data on CSV
	 * 
	 * @param String data
	 * @return boolean
	 */
	public boolean write(List<String> data) {
		if (!data.isEmpty()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					writeCSV(data);
				};
			});
		}
		return true;
	}

	/**
	 * write data on CSV
	 * 
	 * @param String data
	 * @return boolean
	 */
	public boolean write(String data) {
		if (!data.isEmpty()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					writeCSV(Arrays.asList(data));
				};
			});
		}
		return true;
	}

}
