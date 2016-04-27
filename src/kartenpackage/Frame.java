package kartenpackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//Deklarieren der Attribute
	public JButton newbtn = new JButton("Neue Karte");
	public JButton editbtn = new JButton("Karte bearbeiten");
	public JButton backup = new JButton("Backup");
	private JFrame myframe = new JFrame();
	
	public  Frame(){
		
		//Elemente auf Frame adden
		myframe.add(newbtn);
		myframe.add(editbtn);
		myframe.add(backup);
		
		//ActionListener für Button 
		newbtn.addActionListener(this);
		editbtn.addActionListener(this);
		backup.addActionListener(this);
		
		//Windows Einstellungen
		myframe.setLayout(new GridLayout(3, 0));
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//If/Else Statement um erkennen welcher Button geklickt wurde
		if(e.getSource() == newbtn){
			new NewCard();
		}else if(e.getSource() == editbtn){
			new EditCard();
		}else{
			Backup.BackUp(Database.pullFromStock());
		}
		
	}
	
}