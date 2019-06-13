package classes.csv;

import java.util.List;

public interface ICSV_Writer {

	boolean write(List<String> data);

	boolean write(String data);

}
