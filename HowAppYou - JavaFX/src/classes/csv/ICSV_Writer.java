package classes.csv;

import java.util.List;

/**
 * Interface for CSV_Writer
 */
public interface ICSV_Writer {

	boolean write(List<String> data);

	boolean write(String data);

}
