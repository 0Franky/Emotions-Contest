package classes;

import java.awt.EventQueue;
import java.util.List;

import classes.csv.CSV_WriterBuilder;
import classes.csv.ICSV_Writer;
import classes.database.SQLiteConnection;

public class Synchronizer {

	public static final void sync() {
		if (ConnectionUtils.isConnectedToInternet()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						List<Tuple> data = SQLiteConnection.getAllDataToSyncQuery();
						ICSV_Writer writer = CSV_WriterBuilder
								.getInstance(CSV_WriterBuilder.typeCSV_Writer.google_sheet);
						for (Tuple row : data) {
							if (writer.write(row.toList())) {
								SQLiteConnection.cancelRowToSyncQuery(row.getTimestamp());
							} else {
								break;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println("Exception in Synchronizer()");
						e.printStackTrace();
					}
				};
			});
		}
	}

}
