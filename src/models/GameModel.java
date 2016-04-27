package models;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import scrollyv8.ScrollyV8;

public class GameModel extends Model {

	private final ScrollyV8 mf = new ScrollyV8();

    public GameModel(String myName) {
		super(myName);
	}

	private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mf.setVisible(true);
                mf.init();
           }
            
        });
    }
	
	public void dispose () {
		mf.dispose();
	}

	@Override
	public int doAction(String functionName, String paramS, double paramD) {
		if (functionName.equals("start")) {
	        final SwingNode swingNode = new SwingNode();
        	createSwingContent(swingNode);
		}
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query)
	{
		// TODO Auto-generated method stub
		return null;
	}
}