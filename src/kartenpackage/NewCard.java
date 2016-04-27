package kartenpackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.notification.NotificationFactory;
import com.notification.NotificationFactory.Location;
import com.notification.NotificationManager;
import com.notification.manager.SimpleManager;
import com.notification.types.TextNotification;
import com.theme.ThemePackagePresets;
import com.utils.Time;

public class NewCard extends JFrame implements ActionListener{
	
	/*TODO - David
	 *------------
	 *-Editable Text
	 *-Sch�neres UI(�berall)
	 *-values[2] editable machen
	 *-If/Else beim kontrollieren des Speicherns funktioniert noch nicht
	 *-Beim editieren: wenn etwas gel�scht wurde aktualisieren
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
		
		//ActionListener f�r Button 
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
		
		if(antwort == null || frage == null || antwort.getText() == "" && frage.getText() == "" ){
		
			JOptionPane.showMessageDialog(null,"Du musst etwas eingeben!");
	  
		}else{
			
			//Nimmt den Text von JTextFields und speichert sie in DB 
			String[] values = new String[3];
			values[0] = antwort.getText();
			values[1] = frage.getText();
			values[2] = "English Unit 4";
			
			Database.pushToStock(values);
			
			//L�scht den Inhalt der JTextFields
			antwort.setText("");
			frage.setText("");
	 
			//Benachrichtigung wenn es gespeichert wurde
			
			//L�sst die Nachricht aufpopen
			NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
			
			//Zeigt die Nachricht an der ausgew�hlten Position an
			NotificationManager plain = new SimpleManager(Location.NORTHEAST);
			
			//F�gt Text hinzu
			TextNotification notification = factory.buildTextNotification("Lernkartei",  "Karte wurde gespeichert!");
			notification.setCloseOnClick(true);
			
			//Die Notification verschwindet nach 2sek. oder anklicken
			plain.addNotification(notification, Time.seconds(2));
			
		}
		
	}
		
}