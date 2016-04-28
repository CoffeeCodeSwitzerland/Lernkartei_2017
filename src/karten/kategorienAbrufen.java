package karten;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class kategorienAbrufen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L; 
	JButton showDatabase = new JButton("Kategorien Anzeigen");
	JFrame Anzeige = new JFrame();

	public kategorienAbrufen(JFrame f) { 
		f.getContentPane().add(showDatabase);
		showDatabase.setBounds(150, 110, 200, 25);
		showDatabase.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == showDatabase) {

			for (String s : database.Categories.getKategorien("Franz")) {

				Anzeige.getContentPane().add(new JLabel(s));
				
				Anzeige.setSize(400, 800);
				Anzeige.setLayout(new GridLayout(database.Categories.getKategorien("Franz").size(),0));
				Anzeige.setVisible(true);

			}

		}

	}

}
