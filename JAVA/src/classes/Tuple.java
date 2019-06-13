package classes;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

	private int ID;
	private int TIMESTAMP;
	private String ACTIVITY;
	private int VALENCE;
	private int AROUSAL;
	private String STATUS;
	private String NOTES;

	public Tuple(int ID, int TIMESTAMP, String ACTIVITY, int VALENCE, int AROUSAL, String STATUS, String NOTES) {
		// TODO Auto-generated constructor stub

		this.ID = ID;
		this.TIMESTAMP = TIMESTAMP;
		this.ACTIVITY = ACTIVITY;
		this.VALENCE = VALENCE;
		this.AROUSAL = AROUSAL;
		this.STATUS = STATUS;
		this.NOTES = NOTES;
	}

	public int getID() {
		return ID;
	}

	public int getTimestamp() {
		return TIMESTAMP;
	}

	public String getActivity() {
		return ACTIVITY;
	}

	public int getValence() {
		return VALENCE;
	}

	public int getArousal() {
		return AROUSAL;
	}

	public String getStatus() {
		return STATUS;
	}

	public String getNotes() {
		return NOTES;
	}

	public String[] toArray() {
		return new String[] { Integer.toString(ID), Integer.toString(TIMESTAMP), ACTIVITY, Integer.toString(VALENCE),
				Integer.toString(AROUSAL), STATUS, NOTES };
	}

	public List<String> toList() {
		List<String> data = new ArrayList<>();
		data.add(Integer.toString(ID));
		data.add(Integer.toString(TIMESTAMP));
		data.add(ACTIVITY);
		data.add(Integer.toString(VALENCE));
		data.add(Integer.toString(AROUSAL));
		data.add(STATUS);
		data.add(NOTES);

		return data;
	}

	public void print() {
		System.out.println("ID: " + ID);
		System.out.println("TIMESTAMP: " + TIMESTAMP);
		System.out.println("ACTIVITY: " + ACTIVITY);
		System.out.println("VALENCE: " + VALENCE);
		System.out.println("AROUSAL: " + AROUSAL);
		System.out.println("STATUS: " + STATUS);
		System.out.println("NOTES: " + NOTES);
	}

}
