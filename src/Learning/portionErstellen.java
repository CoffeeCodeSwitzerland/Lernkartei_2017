package Learning;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFrame;

public class portionErstellen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton Portionwählen = new JButton("Portion wählen");
	JTextField Portioneingabe = new JTextField();
	JLabel erklärung = new JLabel("Geben sie die Gewünschte Portion ein");
	int Portionengrösse;

	public portionErstellen(int Portionengrösse) {

		this.getContentPane().add(erklärung, BorderLayout.NORTH);
		this.getContentPane().add(Portionwählen, BorderLayout.SOUTH);
		this.getContentPane().add(Portioneingabe, BorderLayout.CENTER);
		Portionwählen.addActionListener(this);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Portionwählen) {
			Portionengrösse = (Integer.parseInt(Portioneingabe.getText()));
			System.out.println(Integer.toString(Portionengrösse));
		}

	}

}
