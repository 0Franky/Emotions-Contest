package classes;

/**
 * Useful class to covert Time into Unix semantics
 */
public class TimeConverter {

	/**
	 * Time Converter
	 * 
	 * @param time
	 * @return time Unix
	 */
	public static final long toUnixTime(long time) {
		return time / 1000L;
	}

}
