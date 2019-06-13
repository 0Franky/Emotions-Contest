package classes;

import java.util.List;

import classes.csv.CSV_WriterBuilder;
import classes.database.SQLiteConnection;

public class Synchronizer {

	public static final void sync() {
		List<Tuple> data = SQLiteConnection.runQuery(SQLiteConnection.getAllDataToSyncQuery());
		for (Tuple row : data) {
			if (CSV_WriterBuilder.getInstance(CSV_WriterBuilder.typeCSV_Writer.built_in).write(row.toList())) {
				SQLiteConnection.cancelRowToSyncQuery(Integer.toString(row.getTimestamp()));
			} else {
				break;
			}
		}
	}

}
