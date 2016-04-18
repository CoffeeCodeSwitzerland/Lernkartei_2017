package roger.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import com.opencsv.*;
import roger.csv.imports.*;

public class Main {

	// Anpassen des CSV File namens
	private static final String filename = "test";
	private static final String separator = ":::";

	public static void main(String[] args) throws Exception {

		// Liest csv Datei
		String path = System.getProperty("user.dir");
		File file = new File(path + "\\bin\\CSV\\src\\data.csv");
		CSVReader reader = new CSVReader(new FileReader(file));

		// Inhalte werden in eine Liste geladen
		List<String[]> li = reader.readAll();
		reader.close();

		// Inhalt der Liste li wird in die DB eingespeichert
		ImportCSV q = new ImportCSV(li);

		// Inhalt wird aus der Datenbank in eine ArrayList<String> mit :::
		// Separator gesetzt

		CSVWriter writer = new CSVWriter(new FileWriter(path + "\\bin\\CSV\\src\\" + filename + ".csv"), ',');

		for (String s : q.ExportCSV(q.csvTable)) {

			String[] entries = s.split(separator);
			writer.writeNext(entries);

		}

		writer.close();

	}

}
