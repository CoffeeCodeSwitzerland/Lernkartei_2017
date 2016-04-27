package models;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;

public class GameModel extends Model {

    public GameModel(String myName) {
		super(myName);
	}

	private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		scrollyv8.ScrollyV8.main(null); // starte hier Spiel
            }
            
        });
    }

	@Override
	public int doAction(String functionName, String paramS, double paramD) {
		if (functionName.equals("start")) {
	        final SwingNode swingNode = new SwingNode();
        	createSwingContent(swingNode);
		}
		return 0;
	}
}