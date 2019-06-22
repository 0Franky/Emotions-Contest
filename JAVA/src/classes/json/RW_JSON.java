package classes.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RW_JSON {

	/**
	 * write Json file
	 * 
	 * @param jsonName
	 * @param parametri
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void writeJson(String jsonName, Map parametri) {

		JSONObject parameters = new JSONObject();
		parameters.putAll(parametri);

		JSONArray parametersList = new JSONArray();
		parametersList.add(parameters);

		// Write JSON file
		try (FileWriter file = new FileWriter(jsonName)) {

			file.write(parametersList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read Json file
	 * 
	 * @param jsonName
	 */
	public static void readJson(String jsonName) {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(jsonName)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray parametersList = (JSONArray) obj;
			System.out.println(parametersList);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * MAIN TEST
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> parametri = new HashMap<String, String>();

		parametri.put("1", "Lokesh");
		parametri.put("2", "Gupta");
		parametri.put("3", "fuck");
		parametri.put("4", "test");
		parametri.put("5", "marocco");
		writeJson("Config.json", parametri);
		readJson("Config.json");
	}

}
