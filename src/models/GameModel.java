package models;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import scrollyv8.ScrollyV8;
import debug.*;

public class GameModel extends Model {

	private ScrollyV8 mf;

    public GameModel(String myName) {
		super(myName);
		mf = new ScrollyV8();
	}

	private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	if (mf != null) {
                    mf.setVisible(true);
                    mf.init();
            	}
           }
        });
    }
	
	public void dispose () {
		Debugger.out("Dispose Game");
        mf.setVisible(false);
		mf.dispose();
		mf = null;
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