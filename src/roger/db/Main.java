package roger.db;
import roger.db.sql.*;
public class Main {

	public static void main(String[] args) {
		
		// FK's müssen FK_TabellenName, auf welche sie verweist.
		// PK's werden autogeneriert, und heissen PK_TabellenName

		final Attributes vname = new Attributes("Vorname", "TEXT", true);
		final Attributes name = new Attributes("Nachname", "TEXT", true);
		final Attributes adr = new Attributes("Adresse", "TEXT", true);
		final Attributes plz = new Attributes("PLZ", "INTEGER", true);
		final Attributes ort = new Attributes("Ort", "TEXT", true);
		final Attributes fk_work = new Attributes("FK_Subjects", "INTEGER", true);
		
		final Attributes subject = new Attributes("Subject", "TEXT", true);
		final Attributes money = new Attributes("Salary", "INTEGER", true);
		
		
		Table workers = new Table("Mitarbeiter");
		workers.addAttrs(vname);		
		workers.addAttrs(name);
		workers.addAttrs(adr);
		workers.addAttrs(plz);
		workers.addAttrs(ort);
		workers.addAttrs(fk_work);
		
		Table subjects = new Table("Subjects");
		subjects.addAttrs(subject);		
		subjects.addAttrs(money);

		workers.generate();
		subjects.generate();
		
		Insert workInsert = new Insert(workers);
		workInsert.pushToDB(workers);
		
		Insert subInsert1 = new Insert(subjects);
		subInsert1.pushToDB(subjects);
		
		Insert subInsert2 = new Insert(subjects);
		subInsert2.pushToDB(subjects);
		
		workInsert.pullFromDB(workers);

	}

}
