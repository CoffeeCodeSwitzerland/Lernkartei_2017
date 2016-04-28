package kartenpackage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
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
	 *-In JavaFX umwandeln
	 *-Beim editieren: wenn etwas gelöscht wurde aktualisieren
	 *-Beim editieren: Farbe manuel aktualsieren
	 *-Beim editieren: Fehler bei wiederöffnen
	 */

	private static final long serialVersionUID = 1L;
	
	//Deklarieren der Attribute
	public static Database db;
	public static String s, q;
	public JTextField antwort = new JTextField();
	public JTextField frage = new JTextField();
	public JButton colorbtn = new JButton("Farbe");
	public JButton savebtn = new JButton("Speichern");
	private JFrame myframe = new JFrame();
	public Color c = Color.BLACK;
	public int cop;
	public String op = null;
	
	public NewCard(){
		
		//Elemente auf Frame adden
		myframe.add(antwort);
		myframe.add(frage);
		myframe.add(colorbtn);
		myframe.add(savebtn);
		
		//ActionListener für Button 
		colorbtn.addActionListener(this);
		savebtn.addActionListener(this);
		
		//Windows Einstellungen
		myframe.setLayout(new GridLayout(4, 0));
		myframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == savebtn){
			if(antwort.getText().equals("") && frage.getText().equals("") || frage.getText().equals("") || antwort.getText().equals("")){
		
				JOptionPane.showMessageDialog(null,"Du musst etwas eingeben!");
	  
			}else{
					
				
				if(c != null){
				  cop = c.getRGB();		
				  op = Integer.toString(cop);
				}
				  
			
				//Nimmt den Text von JTextFields und speichert sie in DB 
				String[] values = new String[4];
				values[0] = antwort.getText();
				values[1] = frage.getText();
				values[2] = "Description";
				values[3] = op;
				
				Database.pushToStock(values);
				
				//Löscht den Inhalt der JTextFields
				antwort.setText("");
				frage.setText("");
		 
				//Benachrichtigung wenn es gespeichert wurde
				
				//Lässt die Nachricht aufpopen
				NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
				
				//Zeigt die Nachricht an der ausgewählten Position an
				NotificationManager plain = new SimpleManager(Location.NORTHEAST);
				
				//Fügt Text hinzu
				TextNotification notification = factory.buildTextNotification("Lernkartei",  "Karte wurde gespeichert!");
				notification.setCloseOnClick(true);
				
				//Die Notification verschwindet nach 2sek. oder anklicken
				plain.addNotification(notification, Time.seconds(2));
			}
				
		}else{
			
			c = JColorChooser.showDialog(null, "Wähle eine Farbe", Color.BLACK);
		      if (c != null){
		        antwort.setForeground(c);
		        frage.setForeground(c);
		      }		      
		      
		}
		
	}
		
}