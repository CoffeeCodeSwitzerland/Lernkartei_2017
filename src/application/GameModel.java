package application;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class GameModel extends Model {

    public GameModel(String myName) {
		super(myName);
		// TODO Auto-generated constructor stub
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