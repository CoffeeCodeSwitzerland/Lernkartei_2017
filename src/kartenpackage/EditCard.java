package kartenpackage;

import kartenpackage.Database;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class EditCard implements ActionListener{

	public int i;
	public JButton savebtn;
	private ArrayList<JButton> btns = new ArrayList<JButton>();
	private JButton delbtn;
	public String sedf;
	public JTextField jugo;
	public ArrayList<String> IDs = new ArrayList<String>();
	public static ArrayList<JTextField> editTxfs = new ArrayList<JTextField>();
	
	public EditCard(){
				
		JFrame myframe = new JFrame();
		
		for (int j = 0; j < Database.pullFromStock().size(); j++) {	
			
			IDs.add((Database.pullFromStock().get(j)[0]));
			
			for (i = 0; i < Database.pullFromStock().get(j).length; i++) {			
				System.out.println(Database.pullFromStock().get(j)[i]);	
				myframe.add(jugo = new JTextField(Database.pullFromStock().get(j)[i]));
				editTxfs.add(jugo);
				if(i == 3){	
					myframe.add(delbtn = new JButton(Integer.toString(j +1)));
					delbtn.addActionListener(this);
					btns.add(delbtn);
				}
			}		
		}	
		
		myframe.setLayout(new GridLayout(0,5));  
		
		savebtn = new JButton("SAVE");
		
		myframe.add(savebtn);
		savebtn.addActionListener(this);
		
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == savebtn) {
			
			String[] vls = new String[3];
			ArrayList<String[]> vlsArray = new ArrayList<String[]>();
			
			for (int i = 0; i < Database.getEdited().size(); i++) {

				if (i % 4 != 0)
				{
					vls[(i % 4) - 1] = Database.getEdited().get(i);
				}
				else
				{
					if (i != 0)
						vlsArray.add(vls);
					vls = new String[3];
				}
			}
			vlsArray.add(vls);
			
			Database.delStock();
			
			for (String[] s : vlsArray) {
			
				Database.pushToStock(s);
			
			}
			
		} else {
			
			Integer btnID = null;
			
			for (JButton s : btns) {
				
				if(e.getSource() == s){
					
					btnID = Integer.parseInt(s.getText());
					
					Database.delEntry(IDs.get(btnID - 1));
				}
				
			}
			
		}
		
	}
}
