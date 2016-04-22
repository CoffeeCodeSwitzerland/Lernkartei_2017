package database;
import java.sql.*;
import java.util.ArrayList;

public class ImpInsert {

	public ArrayList<String> values = null;
	private Connection c = null;
	private Statement stmt = null;
	private String attributes = "";
	private String insertValues = "";

	public ImpInsert(ImpTable table_name, ArrayList<String> insValues) {

		if (table_name != null) {

			values = insValues;

		}

	}

	public void pushToDB(ImpTable table_name) {

		if (table_name != null) {

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				// Attributes

				for (int j = 0; j < table_name.Attrs.size(); j++) {

					if (j < table_name.Attrs.size() - 1) {

						attributes += table_name.Attrs.get(j).getName() + ",";

					} else {

						attributes += table_name.Attrs.get(j).getName();

					}

				}

				// Values

				for (int k = 0; k < values.size(); k++) {

					if (k < values.size() - 1) {

						if (table_name.Attrs.get(k).getDatatype() == "INTEGER") {

							insertValues += values.get(k) + ",";

						} else {

							insertValues += "'" + values.get(k) + "'" + ",";

						}

					} else {

						if (table_name.Attrs.get(k).getDatatype() == "INTEGER") {

							insertValues += values.get(k);

						} else {

							insertValues += "'" + values.get(k) + "'";

						}

					}

				}

				String sql = "INSERT INTO " + table_name.getName() + "(" + attributes + ")" + " VALUES (" + insertValues
						+ ");";

				stmt.executeUpdate(sql);

				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

		}
	}

	public void delValues(ImpTable table_name) {

		if (table_name != null) {

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				String sql = "DELETE FROM " + table_name.getName() + ";";

				stmt.executeUpdate(sql);

				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

		}
	}

	public void pullFromDB(ImpTable table_name) {

		if (table_name != null) {

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + table_name.getName() + ";");

				while (rs.next()) {

					Integer ID = rs.getInt("ID_" + table_name.getName());
					System.out.println("\nID_" + table_name.getName() + " = " + ID);

					for (int l = 0; l < table_name.Attrs.size(); l++) {

						if (table_name.Attrs.get(l).getDatatype() == "TEXT") {

							String select = rs.getString(table_name.Attrs.get(l).getName());
							System.out.println(table_name.Attrs.get(l).getName() + " = " + select);

						} else {

							if (table_name.Attrs.get(l).getName().contains("_")) {
								
								String[] splited = table_name.Attrs.get(l).getName().split("_");
								
								if (splited[0].equals("FK")) {

									Integer ForeignKey = rs.getInt(table_name.Attrs.get(l).getName());

									String selectFK = "SELECT * FROM " + splited[1] + " WHERE ID_" + splited[1] + " = "
											+ ForeignKey;
									
									ResultSet sct = stmt.executeQuery(selectFK);
									
									while (sct.next()) {
										
										System.out.println("\nFREMDSCHLÜSSELVERWEIS AUF: " + splited[1] + "\n");
										
										for (int o = 0; o < ImpTable.Tables.size(); o++) {
											
											if (ImpTable.Tables.get(o).getName().toUpperCase().equals(splited[1].toUpperCase())) {
												
												for (int i = 0; i < ImpTable.Tables.get(o).Attrs.size(); i++) {
													
													if (ImpTable.Tables.get(o).Attrs.get(i).getDatatype().equals("INTEGER")) {
														
														Integer print = sct.getInt(ImpTable.Tables.get(o).Attrs.get(i).getName());
														System.out.println(ImpTable.Tables.get(o).Attrs.get(i).getName() + " = " + print.toString());
														
													} else {
													
														String print2 = sct.getString(ImpTable.Tables.get(o).Attrs.get(i).getName());
														System.out.println(ImpTable.Tables.get(o).Attrs.get(i).getName() + " = " + print2);
														
													}
													
												}

											}
											
										}
										
									}
																		
								}

							} else {

								Integer select = rs.getInt(table_name.Attrs.get(l).getName());
								System.out.println(table_name.Attrs.get(l).getName() + " = " + select);

							}

						}

					}

				}

				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}

	}

}
