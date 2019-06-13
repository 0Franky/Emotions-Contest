package classes;

import java.util.List;

import classes.csv.CSV_WriterBuilder;
import classes.csv.ICSV_Writer;
import classes.database.SQLiteConnection;

public class Synchronizer {

	public static final void sync() {
		List<Tuple> data = SQLiteConnection.getAllDataToSyncQuery();
		ICSV_Writer writer = CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.google_sheet);
		for (Tuple row : data) {
			if (writer.write(row.toList())) {
				SQLiteConnection.cancelRowToSyncQuery(row.getTimestamp());
			} else {
				break;
			}
		}
	}

}
