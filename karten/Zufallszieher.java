package karten;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Zufallszieher extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton Portionwählen = new JButton("Portion wählen");
	JTextField Portioneingabe = new JTextField();
	JLabel erklärung = new JLabel("Geben sie die Gewünschte Portion ein");
	int Portionengrösse = 0;

	public Zufallszieher() {

		this.getContentPane().add(erklärung, BorderLayout.NORTH);
		this.getContentPane().add(Portionwählen, BorderLayout.SOUTH);
		this.getContentPane().add(Portioneingabe, BorderLayout.CENTER);
		Portionwählen.addActionListener(this);
		this.setTitle("Portion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Portionwählen) {
			Portionengrösse = (Integer.parseInt(Portioneingabe.getText()));
			System.out.println(Integer.toString(Portionengrösse));


			for (int i = 1; i < Portionengrösse; i++) {
				int zufall = 1 + (int) (Math.random() * Portionengrösse);
				System.out.println(zufall);
			}
		}
	}

}
