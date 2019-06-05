package classes.csv;

import java.io.IOException;
import java.util.List;

public interface ICSV_Writer {

	void write(List<String> data) throws IOException;

	void write(String data) throws IOException;

}
