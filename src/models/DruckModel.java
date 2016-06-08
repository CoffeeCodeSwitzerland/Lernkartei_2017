package models;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import mvc.Model;
import scrollyv8.gamePanel;

public class DruckModel extends Model{
	
	
	
	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				gamePanel.first = true;
			}
		});
	}	
	public int doAction(String functionName, String paramS, double paramD) {
		if (functionName.equals("start")) {
			final SwingNode swingNode = new SwingNode();
			createSwingContent(swingNode);
		}
		return 0;
	}

}
