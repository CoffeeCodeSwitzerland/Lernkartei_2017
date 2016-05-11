package Learning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Zufallszieher extends JFrame implements ActionListener {

	public static int rdm = 0;
	private static final long serialVersionUID = 1L;

	JButton Portionwählen = new JButton("Portion wählen");
	JButton Abbrechen = new JButton("Abbrechen");
	JButton unbegrenztlernen = new JButton("Ohne Portionen Lernen");
	JTextField Portioneingabe = new JTextField();
	JLabel erklärung = new JLabel("Geben sie die Gewünschte Portion ein");
	int Portionengrösse = 0;

	//Zufallszhl erstellen
	public static int ziehen() {
		int zufallszahl = 0;

		//zufallszahl = (int) ((Math.random() * Database.pullFromStock().size()));

		return zufallszahl;
	}

	public Zufallszieher() {

		this.getContentPane().add(erklärung);
		erklärung.setBounds(20, 20, 300, 30);

		this.getContentPane().add(Portionwählen);
		Portionwählen.setBounds(25, 100, 200, 25);

		this.getContentPane().add(Portioneingabe);
		Portioneingabe.setBounds(25, 60, 200, 25);

		this.getContentPane().add(Abbrechen);
		Abbrechen.setBounds(250, 100, 200, 25);

		this.getContentPane().add(unbegrenztlernen);
		unbegrenztlernen.setBounds(250, 60, 200, 25);

		this.getContentPane().setLayout(null);
		Portionwählen.addActionListener(this);
		Abbrechen.addActionListener(this);
		unbegrenztlernen.addActionListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 250);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Portionwählen) {
			Portionengrösse = (Integer.parseInt(Portioneingabe.getText()));
			ziehen();
			new Bewertungsklasse();

		}

		else if (e.getSource() == Abbrechen) {
			this.setVisible(false);
		} else if (e.getSource() == unbegrenztlernen) {

			new Bewertungsklasse();
		}

	}

}
