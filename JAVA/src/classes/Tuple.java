package classes;

import java.util.Arrays;
import java.util.List;

/**
 * Useful class to define the object Tuple (the new entry for the table)
 */
public class Tuple {

	/**
	 * Attributes for the definition of a new Tuple
	 */
	private String TIMESTAMP;
	private String ACTIVITY;
	private String VALENCE;
	private String AROUSAL;
	private String PRODUCTIVITY;
	private String DOMINANCE;
	private String USER_ID;
	private String STATUS;
	private String NOTES;

	/**
	 * Constructor for a new Tuple
	 * 
	 * @param TIMESTAMP
	 * @param ACTIVITY
	 * @param VALENCE
	 * @param AROUSAL
	 * @param DOMINANCE
	 * @param PRODUCTIVITY
	 * @param USER_ID
	 * @param STATUS
	 * @param NOTES
	 */
	public Tuple(String TIMESTAMP, String ACTIVITY, String VALENCE, String AROUSAL, String DOMINANCE,
			String PRODUCTIVITY, String USER_ID, String STATUS, String NOTES) {
		// TODO Auto-generated constructor stub

		this.TIMESTAMP = TIMESTAMP.trim();
		this.ACTIVITY = ACTIVITY.trim();
		this.VALENCE = VALENCE.trim();
		this.AROUSAL = AROUSAL.trim();
		this.DOMINANCE = DOMINANCE.trim();
		this.PRODUCTIVITY = PRODUCTIVITY.trim();
		this.USER_ID = USER_ID.trim();
		this.STATUS = STATUS.trim();
		this.NOTES = NOTES.trim();
	}

	/**
	 * Getter Methods
	 * 
	 * @return ...
	 */
	public String getTimestamp() {
		return TIMESTAMP;
	}

	public String getActivity() {
		return ACTIVITY;
	}

	public String getValence() {
		return VALENCE;
	}

	public String getArousal() {
		return AROUSAL;
	}

	public String getDominance() {
		return DOMINANCE;
	}

	public String getProductivity() {
		return PRODUCTIVITY;
	}

	public String getUserId() {
		return USER_ID;
	}

	public String getStatus() {
		return STATUS;
	}

	public String getNotes() {
		return NOTES;
	}

	/**
	 * Converter of Tuple to an Array of Strings
	 * 
	 * @return String[]
	 */
	public String[] toArray() {
		return new String[] { TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, DOMINANCE, PRODUCTIVITY, USER_ID, STATUS, NOTES };
	}

	/**
	 * Converter of Tuple to a List of Strings
	 * 
	 * @return String[]
	 */
	public List<String> toList() {
		return Arrays.asList(toArray());
	}

	/**
	 * Simple print() method for a Tuple
	 */
	public void print() {
		System.out.println("TIMESTAMP: " + TIMESTAMP);
		System.out.println("ACTIVITY: " + ACTIVITY);
		System.out.println("VALENCE: " + VALENCE);
		System.out.println("AROUSAL: " + AROUSAL);
		System.out.println("PRODUCTIVITY: " + PRODUCTIVITY);
		System.out.println("DOMINANCE: " + DOMINANCE);
		System.out.println("USER_ID: " + USER_ID);
		System.out.println("STATUS: " + STATUS);
		System.out.println("NOTES: " + NOTES);
	}

}
