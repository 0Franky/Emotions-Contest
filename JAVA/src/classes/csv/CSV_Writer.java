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

				// TODO: handle exception
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} while (!status);
	}

	public void write(List<String> data) {
		if (!data.isEmpty()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					writeCSV(data);
				};
			});
		}
	}

	public void write(String data) {
		if (!data.isEmpty()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					writeCSV(Arrays.asList(data));
				};
			});
		}
	}

}
