package roger.db.sql;
import java.sql.*;
import java.util.ArrayList;

public class Table {

	// Variabeldeklaration

	private String Tbl_Name;

	// Changeable Database Name --> DB_Name

	private String DB_Name = "test.db";
	public ArrayList<Attributes> Attrs = new ArrayList<Attributes>();
	public static ArrayList<Table> Tables = new ArrayList<Table>();
	private Connection c = null;
	private Statement stmt = null;
	private String sql = "";
	private String tables = "";
	private String nuller = "";

	// Konstruktor, welche den Namen gibt und die Datenbank erstellt

	public Table(String Table_name) {

		Tbl_Name = Table_name;
		
		Tables.add(this);

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// Methode, welche Attribute der Liste Attrs hinzufügt

	public void addAttrs(Attributes attr) {

		Attrs.add(attr);

	}

	// Methode, welche die erste Tabelle erstellt

	public void generate() {

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);

			if (Attrs.size() == 0) {
				System.out.println("No Attributes have been added! Please Add with: addAttr() Method");
			}

			stmt = c.createStatement();
			tables += "(ID_" + Tbl_Name + " INTEGER PRIMARY KEY AUTOINCREMENT,";

			for (int i = 0; i < (Attrs.size()); i++) {

				if (Attrs.get(i).getNotNull()) {
					nuller = " NOT NULL";
				} else {
					nuller = "";
				}

				if (i < Attrs.size() - 1) {

					tables += Attrs.get(i).getName() + " " + Attrs.get(i).getDatatype() + nuller + ",";

				} else {

					tables += Attrs.get(i).getName() + " " + Attrs.get(i).getDatatype() + nuller;

				}

			}
			
			sql = "DROP TABLE IF EXISTS " + Tbl_Name;
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS " + Tbl_Name + " " + tables + ");";

			stmt.executeUpdate(sql);
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public String getName() {
		return Tbl_Name;
	}

}