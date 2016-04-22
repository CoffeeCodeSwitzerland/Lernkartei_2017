package kartenpackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	public JButton newbtn = new JButton("Neue Karte");
	public JButton editbtn = new JButton("Karte bearbeiten");
	public JButton backup;
	
	public  Frame(){
		JFrame myframe = new JFrame();
		
		backup = new JButton("Backup");
		
		myframe.add(newbtn);
		myframe.add(editbtn);
		myframe.add(backup);
		
		newbtn.addActionListener(this);
		editbtn.addActionListener(this);
		backup.addActionListener(this);
		
		myframe.setLayout(new GridLayout(3, 0));
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == newbtn){
			NewCard nc = new NewCard();
		}else if(e.getSource() == editbtn){
			EditCard nc = new EditCard();
		}else{
			Backup.BackUp(Database.pullFromStock());
		}
		
	}
	
	
}