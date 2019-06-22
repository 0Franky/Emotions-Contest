package Title;

/**
 * Class util to makes Strings indipendet from code
 */
public class Title {

	/**
	 * Makes dynamic the NAME of the App
	 */
	public static final String APPLICATION_NAME = "HowAppYou";

	/**
	 * Makes dynamic the VERSION of the App
	 */
	public static final String APPLICATION_VERSION = "1.0";

	/**
	 * json URL online file whit credentials
	 */
	public static final String JSON_CREDENTIAL_URL = "http://extremisinfo.altervista.org/service_account.json";

	/**
	 * E-mails to send the spid
	 */
	public static final String[] EMAILS = { "f.scarati2@studenti.uniba.it", "christian-miccolis@libero.it" };

	/**
	 * User-id
	 */
	public static final String USER_ID = System.getProperty("user.name");
}
