package classes.csv;

import java.io.FileNotFoundException;
/**
 * Classe contenente metodi per utilizzare l'API Google Sheets.
 */
//<<Boundary>>
import java.io.IOException;
import java.io.InputStream;
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

import Title.Title;
import classes.database.SQLiteConnection;

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
	private static final String APPLICATION_NAME = Title.APPLICATION_NAME;

	/**
	 * Permissions to manage Google Drive.
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);

	/**
	 * The location where the SON credential file is stored on the Internet.
	 */

	/**
	 * GoogleDocsUtils instance is useful to make GoogleDocsUtils class "Singleton"
	 */
	private static GoogleDocsUtils singleton = null;

	/**
	 * Return the unique possible instance of the GoogleDocsUtils
	 *
	 * @return The GoogleDocsUtils.
	 */
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
	 * Define the PAGE_SHEET_NAME
	 */
	private static String PAGE_SHEET_NAME = "Sheet1";

	/**
	 * Set Sheet spid name
	 */
	static String spid_SurveyResults = "";

	/**
	 * Methods to authorize the use of GOOGLE APIs
	 *
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private GoogleCredential authorize() throws GeneralSecurityException, IOException, URISyntaxException {
		final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

		/**
		 * json online file whit used credentials
		 */
		final URL url = new URL(Title.JSON_CREDENTIAL_URL);

		final InputStream in = url.openStream();

		GoogleCredential credential = GoogleCredential.fromStream(in, httpTransport, JSON_FACTORY);

		credential = credential.createScoped(SCOPES);

		credential.refreshToken();
		System.out.println("credential = " + credential);
		return credential;
	}

	/**
	 * Main Tester
	 *
	 * @param args
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws ServiceException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	public static void main(final String[] args)
			throws GeneralSecurityException, IOException, ServiceException, URISyntaxException, InterruptedException {

		GoogleDocsUtils.getInstance();

		// GoogleDocsUtils gs = GoogleDocsUtils.getInstance();

		// gs.appendSheet(new Tuple("1560862033", "", "", "", "", "", Title.USER_ID,
		// "POPUP_OPENED", "").toList());
		// gs.appendSheet(new Tuple("1560862033", "", "", "", "", "", Title.USER_ID,
		// "POPUP_OPENED", "").toList());

		System.out
				.println("CURRENT SPID: https://docs.google.com/spreadsheets/d/" + GoogleDocsUtils.spid_SurveyResults);
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

	/**
	 * Deletes a spreadsheet. (Intentionally not used)
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
	 * Initialize GoogleDocsUtilis.
	 */
	private void initDocs() {
		try {
			credential = authorize();
			sheetsService = getSheetsService();
			driveService = getDriveService();

			spid_SurveyResults = SQLiteConnection.getSpid();
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
		Permission userPermission = new Permission().setType("anyone").setRole("reader");
		driveService.permissions().create(spid, userPermission).setFields("id").queue(batch, callback);
		userPermission = new Permission().setType("user").setRole("reader").setEmailAddress(Title.EMAILS_TO_SEND[0]);
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

		if (header != null && header.size() != 0) {
			final List<Object> columnHeader = new ArrayList<>();
			columnHeader.addAll(header);
			writeData.add(columnHeader);
		}

		for (final List<String> someDataRow : res) {
			final List<Object> dataRow = new ArrayList<>();
			dataRow.addAll(someDataRow);
			writeData.add(dataRow);
		}

		final ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
		final Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(spid, "A1", vr);
		request.setValueInputOption("USER_ENTERED");
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

		if (header != null && header != "") {
			final List<Object> columnHeader = new ArrayList<>();
			columnHeader.add(header);
			writeData.add(columnHeader);
		}

		for (final Object someData : res) {
			final List<Object> dataRow = new ArrayList<>();
			dataRow.add(someData);
			writeData.add(dataRow);
		}

		final ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
		final Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(spid, "A1", vr);
		request.setValueInputOption("USER_ENTERED");

		request.execute();
	}

	/**
	 * write on Sheet with append
	 *
	 * @param List<String> data
	 * @return boolean status_write_List
	 */
	@Override
	public boolean write(final List<String> data) {
		boolean status_write_List = false;

		if (!data.isEmpty()) {
			synchronized (GoogleDocsUtils.class) {
				try {
					appendSheet(data);
					status_write_List = true;
				} catch (IOException | GeneralSecurityException | URISyntaxException e) {
					System.err.println("write fallito");
					e.printStackTrace();
				}
			}
		}

		return status_write_List;
	}

	/**
	 * write on Sheet with append
	 *
	 * @param String data
	 * @return boolean status_write_String
	 */
	@Override
	public boolean write(final String data) {
		boolean status_write_String = false;

		if (!data.isEmpty()) {
			synchronized (GoogleDocsUtils.class) {
				try {
					appendSheet(data);
					status_write_String = true;
				} catch (IOException | GeneralSecurityException | URISyntaxException e) {
				}
			}
		}

		return status_write_String;
	}

	/**
	 * Append on Sheet a String
	 *
	 * @param input
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	public void appendSheet(final String input) throws IOException, GeneralSecurityException, URISyntaxException {
		final List<String> data = new ArrayList<>();
		data.add(input);

		appendSheet(data);
	}

	/**
	 * Append on Sheet a List<String> input
	 *
	 * @param List<String> input
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	public void appendSheet(final List<String> input) throws IOException, GeneralSecurityException, URISyntaxException {
		// APPEND //

		int numRows = 1;
		try {
			numRows = sheetsService.spreadsheets().values().get(spid_SurveyResults, "Sheet1!A1:F").execute().getValues()
					.size() + 1;
		} catch (final Exception e) {
			// e.printStackTrace();
		}

		updateSheet(PAGE_SHEET_NAME + "!A" + numRows, input); /*
																 * PER UNA MIGLIORE DINAMICITA' SI PUO' CANCELLARE (O SE
																 * NECESSARIO INSERIRE) ``+ ":H"``
																 */
		System.out.println("End appendSheet");
	}

	/**
	 * read Sheet in a range
	 *
	 * @param range
	 * @return List<List<Object>> values
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	public List<List<Object>> readSheet(final String range)
			throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// READ //

		final ValueRange response = sheetsService.spreadsheets().values().get(spid_SurveyResults, range).execute();

		final List<List<Object>> values = response.getValues();

		if (values == null || values.isEmpty()) {
			System.out.println("Not found Data");
		} else {
			for (final List<Object> row : values) {
				System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));
			}
		}
		return values;
	}

	/**
	 * updateSheet in a range from a List<String> input
	 *
	 * @param range
	 * @param input
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	public void updateSheet(final String range, final List<String> input)
			throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// UPDATE //

		final List<Object> data = new ArrayList<>();
		data.addAll(input);

		final ValueRange body = new ValueRange().setValues(Arrays.asList(data));
		sheetsService.spreadsheets().values().update(spid_SurveyResults, range, body).setValueInputOption("RAW")
				.execute();
	}

	/**
	 * delete On Sheet from a StartIndex
	 *
	 * @param ID         of the Sheet
	 * @param StartIndex
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	public void deleteOnSheet(final int ID, final int StartIndex)
			throws IOException, GeneralSecurityException, URISyntaxException {
		sheetsService = getSheetsService();
		// DELETE //
		final DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
				.setRange(new DimensionRange().setSheetId(ID)// 2063166065)
						.setDimension("ROWS").setStartIndex(StartIndex)// 541)
				);

		final List<Request> requests = new ArrayList<>();
		requests.add(new Request().setDeleteDimension(deleteRequest));

		final BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		sheetsService.spreadsheets().batchUpdate(spid_SurveyResults, body).execute();
	}
}
