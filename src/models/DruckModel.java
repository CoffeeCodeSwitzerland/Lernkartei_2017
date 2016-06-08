package models;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import mvc.Model;
import printing.Printer;

public class DruckModel extends Model {
	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Printer p = new Printer();
				p.printer();				
			}
		});
	}
	public int doAction(String functionName, String paramS, double paramD) {
		if (functionName.equals("druck")) {
			final SwingNode swingNode = new SwingNode();
			createSwingContent(swingNode);
		}
		return 0;
	}

}
