package classes;

import java.util.ArrayList;
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

		this.TIMESTAMP = TIMESTAMP;
		this.ACTIVITY = ACTIVITY;
		this.VALENCE = VALENCE;
		this.AROUSAL = AROUSAL;
		this.STATUS = STATUS;
		this.NOTES = NOTES;
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
		List<String> data = new ArrayList<>();
		data.add(TIMESTAMP);
		data.add(ACTIVITY);
		data.add(VALENCE);
		data.add(AROUSAL);
		data.add(STATUS);
		data.add(NOTES);

		return data;
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
