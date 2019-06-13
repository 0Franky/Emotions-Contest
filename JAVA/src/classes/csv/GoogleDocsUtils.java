package classes.csv;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Classe contenente metodi per utilizzare l'API Google Sheets.
 */
//<<Boundary>>
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gdata.util.ServiceException;

/**
 * Utility class for creating, sharing, and deleting Google spreadsheets. For
 * more, refer to
 * <a href="https://developers.google.com/sheets/api/samples/">this
 * documentation</a>.
 * 
 * Tipo classe: Boundary
 */
public final class GoogleDocsUtils implements ICSV_Writer {

	/**
	 * The app name.
	 */
	private static final String APPLICATION_NAME = "sna4so";

	/**
	 * Permissions to manage Google Drive.
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);

	/**
	 * The location where the SON credential file is stored on the Internet.
	 */
	// private static final String URL =
	// "http://neo.di.uniba.it/credentials/TOKEN-s456hh.json";

	private static GoogleDocsUtils singleton = null;

	public static GoogleDocsUtils getInstance() {

		if (singleton == null) {
			singleton = new GoogleDocsUtils();
		}

		return singleton;
	}

	/**
	 * The instance of the Google Spreadsheet service.
	 */
	private Sheets sheetsService;

	/**
	 * The instance of the Google Drive service.
	 */
	private Drive driveService;

	/**
	 * The object built from the JSON credential file.
	 */
	private Credential credential;

	/**
	 * Default constructor, authenticates and instantiate services.
	 */
	private GoogleDocsUtils() {
		initDocs();
	}

	/**
	 * Performs Google authentication process.
	 *
	 * @return Credential object.
	 * @throws IOException              Generic I/O error
	 * @throws GeneralSecurityException Failed authentication.
	 * @throws URISyntaxException       Malformed URI.
	 */
	/*
	 * private Credential authorize() throws IOException, GeneralSecurityException,
	 * URISyntaxException { final GoogleCredential authCred =
	 * GoogleCredential.fromStream(new URL(URL).openStream()).toBuilder()
	 * .setServiceAccountScopes(SCOPES).build(); return authCred; }
	 */

	private final String CLIENT_ID = "provasheet@clear-backup-238511.iam.gserviceaccount.com";
	// private final List<String> SCOPES =
	// Arrays.asList("https://spreadsheets.google.com/feeds");
	private final String P12FILE = "Auth.p12";

	private GoogleCredential authorize() throws GeneralSecurityException, IOException, URISyntaxException {
		JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

		URL fileUrl = this.getClass().getResource(P12FILE);
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(CLIENT_ID)
				.setServiceAccountPrivateKeyFromP12File(new File(fileUrl.toURI())).setServiceAccountScopes(SCOPES)
				.build();
		credential.refreshToken();

		return credential;
	}

	public static void main(String[] args)
			throws GeneralSecurityException, IOException, ServiceException, URISyntaxException, InterruptedException {
		// TODO Auto-generated method stub
		GoogleDocsUtils gs = GoogleDocsUtils.getInstance();

		ArrayList<String> list = new ArrayList<String>();
		list.add("Ciao");
		list.add("Come");
		list.add("va");
		list.add("?");
		list.add("?");
		list.add("?");
		list.add("?");
		// gs.write(list);
		// gs.writePublicSheet("HEADER", list);
		gs.appendSheet(list.toArray(new String[0]));
	}

	/**
	 * Creates a new sheet on every execution.
	 *
	 * @param title Spreadsheet title.
	 * @return The spreadsheet id.
	 * @throws IOException Generic I/O error.
	 */
	public String createSheet(final String title) throws IOException {
		Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
		spreadsheet = sheetsService.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
		final String spid = spreadsheet.getSpreadsheetId();
		System.out.println("Spreadsheet ID: " + spid);
		System.out.println("Spreadhsheet URL: https://docs.google.com/spreadsheets/d/" + spid);
		return spid;

	}

	// Intentionally not used it. Use it to delete a sheet.
	/**
	 * Deletes a spreadsheet.
	 *
	 * @param spid The spreadsheet id.
	 * @throws IOException Generic I/O error.
	 */
	@SuppressWarnings("unused")
	private void deleteSheet(final String spid) throws IOException {
		driveService.files().delete(spid).execute();

	}

	/**
	 * Instantiates the the Google Drive service.
	 *
	 * @return Instance of the Google Drive service.
	 * @throws IOException              Generic I/O error.
	 * @throws GeneralSecurityException Failed authentication.
	 * @throws URISyntaxException       Malformed URI.
	 */
	private Drive getDriveService() throws IOException, GeneralSecurityException, URISyntaxException {
		return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
				credential).setApplicationName(APPLICATION_NAME).build();
	}

	/**
	 * Returns the spreadsheet id by title.
	 *
	 * @param spid The spreadsheet id.
	 * @throws IOException Generic I/O error.
	 */
	public void getSheetByTitle(final String spid) throws IOException {
		final Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spid);
		// Spreadsheet response = request.execute();
		request.execute();
		// System.out.println(response);
	}

	/**
	 * Instantiates the the Google Sheets service.
	 *
	 * @return Instance of the Google Sheets service.
	 * @throws IOException              Generic I/O error.
	 * @throws GeneralSecurityException Failed authentication.
	 * @throws URISyntaxException       Malformed URI.
	 */
	private Sheets getSheetsService() throws IOException, GeneralSecurityException, URISyntaxException {
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
				credential).setApplicationName(APPLICATION_NAME).build();
	}

	/**
	 * inizializzazione GoogleDocsUtilis.
	 */
	private void initDocs() {
		try {
			credential = authorize();
			sheetsService = getSheetsService();
			driveService = getDriveService();
		} catch (final Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Makes the spreadsheet readable to anyone with the link.
	 *
	 * @param spid The spreadsheet id.
	 * @throws IOException              Generic I/O error.
	 * @throws GeneralSecurityException Failed authentication.
	 * @throws URISyntaxException       Malformed URI.
	 */
	public void shareSheet(final String spid) throws IOException, GeneralSecurityException, URISyntaxException {
		final JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
			@Override
			public void onFailure(final GoogleJsonError e, final HttpHeaders responseHeaders) throws IOException {
				// Handle error
				System.err.println(e.getMessage());
			}

			@Override
			public void onSuccess(final Permission permission, final HttpHeaders responseHeaders) throws IOException {
				System.out.println("Permission ID: " + permission.getId());
			}
		};
		final BatchRequest batch = driveService.batch();
		final Permission userPermission = new Permission().setType("anyone").setRole("reader");
		driveService.permissions().create(spid, userPermission).setFields("id").queue(batch, callback);

		batch.execute();

	}

	/**
	 * Descrizione: Metodo che tramite un'intestazione e dei dati, produce uno sheet
	 * di visivilità pubblica e ne stampa l'URL.
	 * 
	 * @param header  The header of spreadSheet of the results.
	 * @param results The ArrayList of the results.
	 * 
	 * @throws FileNotFoundException    errore in caso di file non trovato
	 * @throws IOException              errore in caso di parametri errati
	 * @throws InterruptedException     Thrown when a thread is waiting and it is
	 *                                  interrupted.
	 * @throws GeneralSecurityException eccezione di default di Java
	 * @throws URISyntaxException       eccezione in caso di errore nell'URI
	 */
	// @Override
	public void writePublicSheet(final List<String> header, final List<List<String>> results)
			throws FileNotFoundException, IOException, InterruptedException, GeneralSecurityException,
			URISyntaxException {

		final String spid = createSheet("SurveyResults");
		shareSheet(spid);
		getSheetByTitle(spid);
		writeSheet(spid, header, results);

	}

	/**
	 * Metodo che genera un foglio Google Sheet e ci scrive, in una colonna, i
	 * risultati di una query.
	 *
	 * @param header  intestazione della colonna
	 * @param results risultati ottenuti dall'esecuzione della query
	 * @throws FileNotFoundException    errore in caso di file non trovato
	 * @throws IOException              errore in caso di parametri errati
	 * @throws InterruptedException     Thrown when a thread is waiting and it is
	 *                                  interrupted.
	 * @throws GeneralSecurityException eccezione di default di Java
	 * @throws URISyntaxException       eccezione in caso di errore nell'URI
	 */
	// @Override
	public void writePublicSheet(final String header, final List<String> results) throws FileNotFoundException,
			IOException, InterruptedException, GeneralSecurityException, URISyntaxException {

		final String spid = createSheet("SurveyResults");
		shareSheet(spid);
		getSheetByTitle(spid);
		writeSheet(spid, header, results);
	}

	/**
	 * Write results to the spreadsheet. Also, see
	 * <a href="https://developers.google.com/sheets/api/guides/values">here</a>.
	 *
	 * @param spid   The spreadsheet id.
	 * @param header The header of spreadSheet of the results.
	 * @param res    The ArrayList of the results.
	 * @throws IOException Generic I/O error.
	 */
	public void writeSheet(final String spid, final List<String> header, final List<List<String>> res)
			throws IOException {

		final List<List<Object>> writeData = new ArrayList<>();

		final List<Object> columnHeader = new ArrayList<>();
		columnHeader.addAll(header);
		writeData.add(columnHeader);

		for (final List<String> someDataRow : res) {
			final List<Object> dataRow = new ArrayList<>();
			dataRow.addAll(someDataRow);
			writeData.add(dataRow);
		}

		final ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
		final Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(spid, "A1", vr);
		request.setValueInputOption("USER_ENTERED");

		// UpdateValuesResponse response = request.execute();
		request.execute();
	}

	/**
	 * Write results to the spreadsheet. Also, see
	 * <a href="https://developers.google.com/sheets/api/guides/values">here</a>.
	 *
	 * @param spid   The spreadsheet id.
	 * @param header The header of spreadSheet of the results.
	 * @param res    The ArrayList of the results.
	 * @throws IOException Generic I/O error.
	 */
	public void writeSheet(final String spid, final String header, final List<String> res) throws IOException {
		final List<List<Object>> writeData = new ArrayList<>();

		final List<Object> columnHeader = new ArrayList<>();
		columnHeader.add(header);
		writeData.add(columnHeader);

		for (final Object someData : res) {
			final List<Object> dataRow = new ArrayList<>();
			dataRow.add(someData);
			writeData.add(dataRow);
		}

		final ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
		final Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(spid, "A1", vr);
		request.setValueInputOption("USER_ENTERED");

		// UpdateValuesResponse response = request.execute();
		request.execute();
	}

	private static String PAGE_SHEET_NAME = "Sheet1";
	static String spid_SurveyResults = "1UGOsvpRuOgCJ8HahYCh6eoKCqOpsuzvy4cD89Rd1mpA";
	/*
	 * https://docs.google.com/spreadsheets/d/
	 * 1UGOsvpRuOgCJ8HahYCh6eoKCqOpsuzvy4cD89Rd1mpA
	 */

	static boolean status_write_List = true;

	@Override
	public boolean write(List<String> data) {
		status_write_List = true;

		if (!data.isEmpty()) {
			synchronized (GoogleDocsUtils.class) {
				// getSheetByTitle(spid_SurveyResults);
				// writeSheet(spid_SurveyResults, "SurveyResults", data);
				// createSheet(spid_SurveyResults);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							appendSheet(data.toArray(new String[0]));
						} catch (IOException | GeneralSecurityException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							status_write_List = false;
						}
					};
				});
			}
		}

		return status_write_List;
	}

	static boolean status_write_String = true;

	@Override
	public boolean write(String data) {
		status_write_String = true;

		if (!data.isEmpty()) {
			synchronized (GoogleDocsUtils.class) {
				// getSheetByTitle(spid_SurveyResults);
				// writeSheet(spid_SurveyResults, "SurveyResults", Arrays.asList(data));
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							appendSheet(new String[] { data });
						} catch (IOException | GeneralSecurityException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							status_write_String = false;
						}
					};
				});
			}
		}

		return status_write_String;
	}

	public void appendSheet(String input[]) throws IOException, GeneralSecurityException {
		// APPEND //
		ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(input)));

		/* AppendValuesResponse appendResult = */
		sheetsService.spreadsheets().values().append(spid_SurveyResults, PAGE_SHEET_NAME, body)
				.setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true)
				.execute();
	}

	public List<List<Object>> readSheet(String range) throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// READ //
		// String range = "congress!A2:F10";

		ValueRange response = sheetsService.spreadsheets().values().get(spid_SurveyResults, range).execute();

		List<List<Object>> values = response.getValues();

		if (values == null || values.isEmpty()) {
			System.out.println("Not found Data");
		} else {
			for (List<Object> row : values) {
				System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));
			}
		}
		return values;
	}

	public void updateSheet(String range, String input[])
			throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// UPDATE //
		// String range = "congress!A2:F10";
		ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(input)));

		/*
		 * UpdateValuesResponse result =
		 * sheetsService.spreadsheets().values().update(spid_SurveyResults, range, body)
		 * .setValueInputOption("RAW").execute();
		 */
		sheetsService.spreadsheets().values().update(spid_SurveyResults, range, body).setValueInputOption("RAW")
				.execute();
	}

	public void deleteOnSheet(int ID, int StartIndex) throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// DELETE //
		DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest().setRange(new DimensionRange().setSheetId(ID)// 2063166065)
				.setDimension("ROWS").setStartIndex(StartIndex)// 541)
		);

		List<Request> requests = new ArrayList<>();
		requests.add(new Request().setDeleteDimension(deleteRequest));

		BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		sheetsService.spreadsheets().batchUpdate(spid_SurveyResults, body).execute();
	}
}
