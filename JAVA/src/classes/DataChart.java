package classes;

/**
 * Useful class to define an object of type DataChart
 */
public class DataChart {

	/**
	 * Attributes for the object DataChart
	 */
	private int VALENCE;
	private int AROUSAL;
	private int WEIGHT;

	/**
	 * Constructor for a DataChart
	 * 
	 * @param VALENCE
	 * @param AROUSAL
	 * @param WEIGHT
	 */
	public DataChart(int VALENCE, int AROUSAL, int WEIGHT) {
		// TODO Auto-generated constructor stub
		this.VALENCE = VALENCE;
		this.AROUSAL = AROUSAL;
		this.WEIGHT = WEIGHT;
	}

	/**
	 * Getter methods
	 * 
	 * @return
	 */
	public int getValence() {
		return VALENCE;
	}

	public int getArousal() {
		return AROUSAL;
	}

	public int getWeight() {
		return WEIGHT;
	}

	/**
	 * Printer method for DataChart
	 */
	public void print() {
		System.out.println("VALENCE: " + VALENCE);
		System.out.println("AROUSAL: " + AROUSAL);
		System.out.println("WEIGHT: " + WEIGHT);
	}

}