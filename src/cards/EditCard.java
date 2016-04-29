package cards;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.NotificationManager;
import com.notification.manager.SimpleManager;
import com.notification.types.TextNotification;
import com.theme.ThemePackagePresets;
import com.utils.Time;

import database.Database;

public class EditCard implements ActionListener{

	//Deklarieren der Attribute
	private JButton savebtn = new JButton("Speichern");
	private JButton delbtn = new JButton();
	public JTextField editJFs;
	public static JFrame myframe = new JFrame();
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
				
				if(i == 4){	
					myframe.add(delbtn = new JButton(Integer.toString(j +1)));
					delbtn.addActionListener(this);
					btns.add(delbtn);
				}
				
			}	
			
		}	
		
		//Farbiger Text
		int j = 4;
		int p = 2;
		int a = 1;
		
		for (int i = 0; i < addJFs.size(); i++) {
			
			if (i == j) {
				
				System.out.println(addJFs.get(i).getText());
				j += 5;
				
				for(int q = 0; q < addJFs.size(); q++){
					
					if(q == p){
						
						System.out.println(addJFs.get(q).getText());
						//addJFs.get(q).setText(addJFs.get(i).getText());
						
							String ko = addJFs.get(i).getText();							
						   int op = Integer.parseInt(ko);						  
					       Color color = new Color(op);
					       addJFs.get(q).setForeground(color);
							
						
						p+=5;
						
						break;
					}
					
					for(int n = 0; n < addJFs.size(); n++){
						
						if(q == a){
							
							System.out.println(addJFs.get(q).getText());
							//addJFs.get(q).setText(addJFs.get(i).getText());
							
								String ko = addJFs.get(i).getText();							
							   int op = Integer.parseInt(ko);						  
						       Color color = new Color(op);
						       addJFs.get(q).setForeground(color);
								
							
							a+=5;
							
							break;
						}
				}}
			}			
		}
		
		//Elemente auf Frame adden
		myframe.add(savebtn);	 
		
		//ActionListener für Button 
		savebtn.addActionListener(this);
		
		//Windows Einstellungen
		myframe.setLayout(new GridLayout(0,6)); 
		myframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == savebtn) {
			
			//Speichern
			String[] vls = new String[4];
			ArrayList<String[]> vlsArray = new ArrayList<String[]>();

			//überspring jedes 5te Feld(weil es eine ID ist) und speichert dann den Rest
			for (int i = 0; i < Database.getEdited().size(); i++) {

				if (i % 5 != 0)
				{
					vls[(i % 5) - 1] = Database.getEdited().get(i);
				
				}else{
				
					if (i != 0)
						vlsArray.add(vls);
					vls = new String[4];
				}
			}
			
			vlsArray.add(vls);			
			Database.delStock();
			
			for (String[] s : vlsArray) {
			
				Database.pushToStock(s);
			
			}
			
			//Benachrichtigung wenn es gespeichert wurde
			
			//Lässt die Nachricht aufpopen
			NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
			
			//Zeigt die Nachricht an der ausgewählten Position an
			NotificationManager plain = new SimpleManager(Location.NORTHEAST);
			
			//Fügt Text hinzu
			TextNotification notification = factory.buildTextNotification("Lernkartei",  "Karten wurden gespeichert!");
			notification.setCloseOnClick(true);
			
			//Die Notification verschwindet nach 2sek. oder anklicken
			plain.addNotification(notification, Time.seconds(2));
			
		} else {
			
			//Löschen
			Integer btnID = null;
			
			for (JButton s : btns) {
				
				if(e.getSource() == s){
					
					btnID = Integer.parseInt(s.getText());
					Database.delEntry(IDs.get(btnID - 1));
				}
				
			}
			
			//Benachrichtigung wenn es gelöscht wurde
			
			//Lässt die Nachricht aufpopen
			NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
			
			//Zeigt die Nachricht an der ausgewählten Position an
			NotificationManager plain = new SimpleManager(Location.NORTHEAST);
			
			//Fügt Text hinzu
			TextNotification notification = factory.buildTextNotification("Lernkartei",  "Karte wurde gelöscht!");
			notification.setCloseOnClick(true);
			
			//Die Notification verschwindet nach 2sek. oder anklicken
			plain.addNotification(notification, Time.seconds(2));
			
		}
		
	}
	
}
