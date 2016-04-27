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

	//Deklarieren der Attribute
	private JButton savebtn = new JButton("Speichern");
	private JButton delbtn = new JButton();
	public JTextField editJFs;
	private JFrame myframe = new JFrame();
	private ArrayList<JButton> btns = new ArrayList<JButton>();
	public ArrayList<String> IDs = new ArrayList<String>();
	public static ArrayList<JTextField> addJFs = new ArrayList<JTextField>();
	
	public EditCard(){
			
		//Anzahl Datensätze = JTextField
		for (int j = 0; j < Database.pullFromStock().size(); j++) {	
			
			IDs.add((Database.pullFromStock().get(j)[0]));
			
			for (int i = 0; i < Database.pullFromStock().get(j).length; i++) {	
					
				myframe.add(editJFs = new JTextField(Database.pullFromStock().get(j)[i]));
				addJFs.add(editJFs);
				
				if(i == 3){	
					myframe.add(delbtn = new JButton(Integer.toString(j +1)));
					delbtn.addActionListener(this);
					btns.add(delbtn);
				}
				
			}	
			
		}	
		
		//Elemente auf Frame adden
		myframe.add(savebtn);	 
		
		//ActionListener für Button 
		savebtn.addActionListener(this);
		
		//Windows Einstellungen
		myframe.setLayout(new GridLayout(0,5)); 
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == savebtn) {
			
			//Speichern
			String[] vls = new String[3];
			ArrayList<String[]> vlsArray = new ArrayList<String[]>();

			//überspring jedes 4te Feld(weil es eine ID ist) und speichert dann den Rest
			for (int i = 0; i < Database.getEdited().size(); i++) {

				if (i % 4 != 0)
				{
					vls[(i % 4) - 1] = Database.getEdited().get(i);
				
				}else{
				
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
			
			//Löschen
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
