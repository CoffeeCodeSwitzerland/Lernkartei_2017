package karten;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class kategorienAbrufen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton showDatabase = new JButton("Kategorien Anzeigen");
	JFrame Anzeige = new JFrame();

	public kategorienAbrufen(JFrame f) {
		f.getContentPane().add(showDatabase);
		showDatabase.setBounds(150, 110, 200, 25);
		showDatabase.addActionListener(this);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == showDatabase) {

			for (String[] s : Kategorie.Ausgabe()) {

				for (int i = 0; i < s.length; i++) {
					
					Anzeige.getContentPane().add(new JLabel(s[i]));
				}
				
				Anzeige.setSize(400, 800);
				Anzeige.setLayout(new GridLayout(Kategorie.Ausgabe().size(),0));
				Anzeige.setVisible(true);

			}

		}

	}

}
