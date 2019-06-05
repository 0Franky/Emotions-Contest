package classes.csv;

public final class CSV_WriterBuilder {

	private CSV_WriterBuilder() {
		// TODO Auto-generated constructor stub
	}

	public enum typeCSV_Writer {
		built_in, google_sheet
	}

	/**
	 * Descrizione: È un metodo che restituisce un'istanza a tutte le classi che
	 * sono in grado di generare sheet secondo la stringa data in input.
	 * 
	 * @param classType stringa che contiene il nome della classe in grado di
	 *                  generare sheet.
	 * 
	 * @return Restituisce l'istanza tramite l'interfaccia ad essa associata.
	 */
	public static ICSV_Writer getInstance(final typeCSV_Writer classType) {

		ICSV_Writer instance = null;

		switch (classType) {

		case built_in:

			instance = CSV_Writer.getInstance();
			break;

		default:
			System.out.println("Nessuna istanza trovata al parametro di OnlineDocsBuilder\n");
			break;

		}

		return instance;

	}

}
