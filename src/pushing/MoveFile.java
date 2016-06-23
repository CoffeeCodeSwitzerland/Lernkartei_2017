package pushing;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;


public class MoveFile {

	public static void main(String[] args) {

		String userName = System.getProperty("user.name");
		URL location = MoveFile.class.getProtectionDomain().getCodeSource().getLocation();
//		String sourceOrdner = location.getPath();
//		sourceOrdner = sourceOrdner.substring(0, sourceOrdner.length() - 13);
		
		
		File source = new File(location.getPath() + "/pushing/jars");
		File dest = new File("C:/Users/" + userName +"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
		
		
//		JOptionPane.showMessageDialog(null, source.getPath());
//		JOptionPane.showMessageDialog(null, dest.getAbsolutePath());
		
        System.out.println(location.getFile());
		
//		 System.out.println(dest.getAbsolutePath());
		
		try {
		    FileUtils.copyDirectory(source, dest);
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
}
}

