package pushing;

import java.io.File;

public class DeleteFile {

	public DeleteFile() {

			String userName = System.getProperty("user.name");
			
			File file = new File("C:/Users/" + userName +"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/Push.jar");      

	        if(file.exists()){
	            file.delete();
	        }
		
	}

}