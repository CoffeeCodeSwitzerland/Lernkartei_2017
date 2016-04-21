package karten;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class kategorienErstellen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JFrame MainFrame = new JFrame();

	JButton katgeorieErstellen = new JButton("Kasten Erstellen");
	JButton Abbrechen = new JButton("Abbrechen");
	// JButton kategorieLöschen = new JButton("Kasten Löschen");

	JTextField kategorieEingabe = new JTextField();

	// Hier dann Kasten einfügen
	String Eingabe = new String();

	public kategorienErstellen() {

		new kategorienAbrufen(MainFrame);

		katgeorieErstellen.addActionListener(this);
		kategorieEingabe.addActionListener(this);
		// kategorieLöschen.addActionListener(this);
		MainFrame.getContentPane().setLayout(null);
		MainFrame.getContentPane().add(katgeorieErstellen);
		katgeorieErstellen.setBounds(25, 80, 200, 25);
		MainFrame.getContentPane().add(kategorieEingabe);
		kategorieEingabe.setBounds(25, 50, 440, 20);
		MainFrame.getContentPane().add(Abbrechen);
		Abbrechen.setBounds(265,80, 200 ,25);

		// MainFrame.getContentPane().add(kategorieLöschen);

		MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		MainFrame.setSize(500, 250);
		MainFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Eingabe = kategorieEingabe.getText();

		if (e.getSource() == katgeorieErstellen)
		// Neuen Kasten erstellen
		{
			// Überschrift für den Kasten einfügen und in SQL einfügen
			// Hier in KAtegorien Tabelle einfügen SQL..

			Kategorien.Kategorie.Eingabe(Eingabe);

			System.out.println(Eingabe);

			// Kasten Neben anderem Visible Einfügen
		}

		// else if (e.getSource() == kategorieLöschen) {
		// Eingabe = "";
		// kategorieEingabe.setText("");
		// }

	}

}
