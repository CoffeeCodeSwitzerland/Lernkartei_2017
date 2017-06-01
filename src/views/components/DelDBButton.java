package views.components;

import java.io.File;

import database.LKDatabase;
import globals.Environment;
import globals.Globals;

public class DelDBButton extends AppButton
{
	public DelDBButton(String name)
	{
		super(name);
		this.setMaxSize(50, 50);
		setOnAction(e ->
		{
			if(Alert.ok("L�schvorgang best�tigen", "Wollen Sie die Programm-Daten wirklich unwiederruflich l�schen?"))
			{
				deleteFile(new File("C:/Users/"+Environment.getUserName()+"/AppData/Roaming/"+Globals.db_Path));
				Alert.ok("Daten gel�scht", "Ihre Daten werden beim n�chsten Beenden des Programms gel�scht.");
			}
		});
		
	}
	
	private void deleteFile(File file) {
		LKDatabase.myConfigDB.closeDB();
		LKDatabase.myWLCDB.closeDB();
		
	    if (file.isDirectory())
	        for (File f : file.listFiles())
	        {
	            f.deleteOnExit();
	        }
	    else
	    {
	        file.deleteOnExit();
	    }
	}

}
