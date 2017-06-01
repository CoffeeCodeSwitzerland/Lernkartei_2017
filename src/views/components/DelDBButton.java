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
			if(Alert.ok("Löschvorgang bestätigen", "Wollen Sie die Programm-Daten wirklich unwiederruflich löschen?"))
			{
				deleteFile(new File("C:/Users/"+Environment.getUserName()+"/AppData/Roaming/"+Globals.db_Path));
				Alert.ok("Daten gelöscht", "Ihre Daten werden beim nächsten Beenden des Programms gelöscht.");
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
