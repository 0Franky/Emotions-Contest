package classes;

import java.util.Arrays;
import java.util.List;

public class Tuple {

	private String TIMESTAMP;
	private String ACTIVITY;
	private String VALENCE;
	private String AROUSAL;
	private String STATUS;
	private String NOTES;

	public Tuple(String TIMESTAMP, String ACTIVITY, String VALENCE, String AROUSAL, String STATUS, String NOTES) {
		// TODO Auto-generated constructor stub

		this.TIMESTAMP = TIMESTAMP.trim();
		this.ACTIVITY = ACTIVITY.trim();
		this.VALENCE = VALENCE.trim();
		this.AROUSAL = AROUSAL.trim();
		this.STATUS = STATUS.trim();
		this.NOTES = NOTES.trim();
	}

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

	public String getStatus() {
		return STATUS;
	}

	public String getNotes() {
		return NOTES;
	}

	public String[] toArray() {
		return new String[] { TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES };
	}

	public List<String> toList() {
		return Arrays.asList(new String[] { TIMESTAMP, ACTIVITY, VALENCE, AROUSAL, STATUS, NOTES });
	}

	public void print() {
		System.out.println("TIMESTAMP: " + TIMESTAMP);
		System.out.println("ACTIVITY: " + ACTIVITY);
		System.out.println("VALENCE: " + VALENCE);
		System.out.println("AROUSAL: " + AROUSAL);
		System.out.println("STATUS: " + STATUS);
		System.out.println("NOTES: " + NOTES);
	}

}
