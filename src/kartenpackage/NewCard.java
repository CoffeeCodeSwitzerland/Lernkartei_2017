package kartenpackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class NewCard extends JFrame implements ActionListener{
	
	/*TODO - David
	 *------------
	 *-Editable Text
	 *-Schöneres UI(Überall)
	 *-values[2] editable machen
	 */

	private static final long serialVersionUID = 1L;
	
	//Deklarieren der Attribute
	public static Database db;
	public static String s, q;
	public JTextField antwort = new JTextField();
	public JTextField frage = new JTextField();
	public JButton boldbtn = new JButton("Bold");
	public JButton savebtn = new JButton("Speichern");
	private JFrame myframe = new JFrame();
	
	public NewCard(){
		
		//Elemente auf Frame adden
		myframe.add(antwort);
		myframe.add(frage);
		myframe.add(boldbtn);
		myframe.add(savebtn);
		
		//ActionListener für Button 
		boldbtn.addActionListener(this);
		savebtn.addActionListener(this);
		
		//Windows Einstellungen
		myframe.setLayout(new GridLayout(4, 0));
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//Nimmt den Text von JTextFields und speichert sie in DB 
		String[] values = new String[3];
		values[0] = antwort.getText();
		values[1] = frage.getText();
		values[2] = "English Unit 4";
		
		Database.pushToStock(values);
	  
		}
		
	}