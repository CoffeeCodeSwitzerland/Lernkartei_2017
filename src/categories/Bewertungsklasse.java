package categories;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Bewertungsklasse extends JFrame implements ActionListener {
	
	
	private static final long serialVersionUID = 1L;
	
	//Objekte Erstellen 
	JFrame MainFrame = new JFrame();
	JButton richtig = new JButton("Richtig");
	JButton falsch = new JButton("Falsch");
	JButton dreheKarte = new JButton("Drehen");
	JButton Abbrechen = new JButton("Abbrechen");
	JLabel KartenPunkte = new JLabel("Kartenpunkte : " + "1");
	JLabel KartenAnzeigen = new JLabel("Test");
	JLabel NachrichtLabel = new JLabel();
	JFrame Nachricht = new JFrame("Karte wurde Verschoben");
	

	public int a = 1;
	int Kartenpunkte = 1;

	public Bewertungsklasse() {

		
		// Objekte Hinzufügen und platzieren
		richtig.addActionListener(this);
		falsch.addActionListener(this);
		MainFrame.getContentPane().setLayout(null);
		MainFrame.getContentPane().add(falsch);
		MainFrame.getContentPane().add(richtig);
		MainFrame.getContentPane().add(Abbrechen);
		MainFrame.getContentPane().add(dreheKarte);

		richtig.setBounds(350, 100, 100, 20);
		falsch.setBounds(15, 100, 100, 20);
		Abbrechen.setBounds(200, 150, 100, 20);
		dreheKarte.setBounds(200, 40, 100, 20);

		Abbrechen.addActionListener(this);
		dreheKarte.addActionListener(this);

		MainFrame.getContentPane().add(KartenPunkte);
		MainFrame.getContentPane().add(KartenAnzeigen);
		KartenPunkte.setBounds(15, 50, 100, 50);
		KartenAnzeigen.setBounds(225, 80, 100, 20);
		MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		MainFrame.setSize(500, 250);
		MainFrame.setVisible(true);
		//KartenAnzeigen.setText(Database.pullFromStock().get(Zufallszieher.rdm)[1]);

	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		//Wenn richtig gedrückt wird zählt es Kartenpunkte + 1 und eine neue "Karte" wird Angezeigt
		if (e.getSource() == richtig) {
			Zufallszieher.rdm = Zufallszieher.ziehen();
			if(Kartenpunkte < 5)
			{
			Kartenpunkte = Kartenpunkte + 1;
			}
			KartenPunkte.setText("Kartenpunkte : " + Integer.toString(+Kartenpunkte));

			Zufallszieher.rdm++;
			
			//wenn zufallszahl kleiner oder gleich der höchste eintrag der DB ist wird eine zufällige karte angezeit.
			//if (Zufallszieher.rdm <= Database.pullFromStock().size()) {
			//	KartenAnzeigen.setText(Database.pullFromStock().get(Zufallszieher.rdm)[1]);
			//}
			a = 1;
		//Punkt der Karte wird auf 1 gesetzt nächste karte erscheint
		} else if (e.getSource() == falsch) {
			Zufallszieher.rdm = Zufallszieher.ziehen();
			if (Kartenpunkte >= 2) {

				KartenPunkte.setText("Kartenpunkte : " + Integer.toString(Kartenpunkte));

				Zufallszieher.rdm = Zufallszieher.ziehen();
				//KartenAnzeigen.setText(Database.pullFromStock().get(Zufallszieher.rdm)[1]);
				Kartenpunkte = 1;
				a = 1;
			}

		} else if (e.getSource() == Abbrechen) {
			MainFrame.setVisible(false);
		} else if (e.getSource() == dreheKarte) {

			a++;
			
//dreht Karte auf lösugs seite
			if (a % 2 == 0) {
				//KartenAnzeigen.setText(Database.pullFromStock().get(Zufallszieher.rdm)[2]);
				a++;
// sollte karte zurückdrehen
			} else if (a % 2 != 0) {
				//KartenAnzeigen.setText(Database.pullFromStock().get(Zufallszieher.rdm)[1]);
				a++;
			}

		} else if (Kartenpunkte == 1) {
			NachrichtLabel.setText("Diese Karte wird in Kasten 1 Verschoben");
			Nachricht.getContentPane().add(NachrichtLabel, BorderLayout.NORTH);
			Nachricht.setVisible(true);
			Nachricht.pack();
		} else if (Kartenpunkte == 2) {
			NachrichtLabel.setText("Diese Karte wird in Kasten 2 Verschoben");
			Nachricht.getContentPane().add(NachrichtLabel, BorderLayout.NORTH);
			Nachricht.setVisible(true);
			Nachricht.pack();

		} else if (Kartenpunkte == 3) {
			NachrichtLabel.setText("Diese Karte wird in Kasten 3 Verschoben");
			Nachricht.setVisible(true);
			Nachricht.pack();
		} else if (Kartenpunkte == 4) {
			NachrichtLabel.setText("Diese Karte wird in Kasten 4 Verschoben");
			Nachricht.setVisible(true);
			Nachricht.pack();
		} else if (Kartenpunkte == 5) {
			NachrichtLabel.setText("Diese Karte wird in Kasten 5 Verschoben");
			Nachricht.setVisible(true);
			Nachricht.pack();
		}
	}
}
