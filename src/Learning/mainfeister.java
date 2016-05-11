package Learning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class mainfeister extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JFrame MainFrame = new JFrame();
	JButton Lernen = new JButton("Lernen");
	JButton KarteErstellen = new JButton("KarteErstellen");
	JButton KategorieErstellen = new JButton("KategorieErstellen");

	public mainfeister() {

		Lernen.addActionListener(this);
		KarteErstellen.addActionListener(this);
		KategorieErstellen.addActionListener(this);

		MainFrame.getContentPane().setLayout(null);
		MainFrame.setSize(500, 250);
		MainFrame.getContentPane().add(Lernen);
		Lernen.setBounds(25, 50, 440, 20);

		MainFrame.getContentPane().add(KarteErstellen);
		KarteErstellen.setBounds(265, 80, 200, 25);

		MainFrame.getContentPane().add(KategorieErstellen);
		KategorieErstellen.setBounds(25, 80, 200, 25);

		MainFrame.setVisible(true);
		MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Lernen) {

			new Zufallszieher();

		} else if (e.getSource() == KarteErstellen) {

			//new Frame();

		} else if (e.getSource() == KategorieErstellen) {

			new kategorienErstellen();

		}

	}

}
