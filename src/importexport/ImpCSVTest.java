package importexport;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import com.opencsv.*;

public class ImpCSVTest {
	
	// ---------
	// Test Main
	// ---------

	// Anpassen des CSV File namens und des Pfads
	private static final String filename = "testdata";
	private static final String newfilename = "newFile";
	private static final String separator = ":::";
	private static final String filepath = "\\src\\datenbank\\";

	public static void main(String[] args) throws Exception {

		// Liest csv Datei
		String path = System.getProperty("user.dir");
		File file = new File(path + filepath + filename + ".csv");
		CSVReader reader = new CSVReader(new FileReader(file));

		// Inhalte werden in eine Liste geladen
		List<String[]> li = reader.readAll();
		reader.close();

		// Inhalt der Liste li wird in die DB eingespeichert

		ImpCSV q = new ImpCSV(li);

		// Inhalt wird aus der Datenbank in eine ArrayList<String> mit :::
		// Separator gesetzt

		CSVWriter writer = new CSVWriter(new FileWriter(path + filepath + newfilename + ".csv"), ',');

		for (String s : q.ExportCSV(q.csvTable)) {

			String[] entries = s.split(separator);
			writer.writeNext(entries);

		}

		writer.close();

	}

}
