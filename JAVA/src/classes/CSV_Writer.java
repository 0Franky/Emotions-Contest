/**
 * 
 */
package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Francesco
 *
 */
public class CSV_Writer {

	/**
	 * Descrizione: Questa classe si occupa di scrivere fogli CSV.
	 * @throws IOException 
	 */
	public static void write(List<String> data) throws IOException {
		FileWriter csvWriter = new FileWriter("new.csv");  
		csvWriter.append("Name");  
		csvWriter.append(",");  
		csvWriter.append("Role");  
		csvWriter.append(",");  
		csvWriter.append("Topic");  
		csvWriter.append("\n");
		
		for (List<String> rowData : rows) {  
		    csvWriter.append(String.join(",", rowData));
		    csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}

}
