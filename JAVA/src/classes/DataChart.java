package classes;

public class DataChart {

	private int VALENCE;
	private int AROUSAL;
	private int WEIGHT;

	public DataChart(int VALENCE, int AROUSAL, int WEIGHT) {
		// TODO Auto-generated constructor stub
		this.VALENCE = VALENCE;
		this.AROUSAL = AROUSAL;
		this.WEIGHT = WEIGHT;
	}

	public int getValence() {
		return VALENCE;
	}

	public int getArousal() {
		return AROUSAL;
	}

	public int getWeight() {
		return WEIGHT;
	}

	public void print() {
		System.out.println("VALENCE: " + VALENCE);
		System.out.println("AROUSAL: " + AROUSAL);
		System.out.println("WEIGHT: " + WEIGHT);
	}

}