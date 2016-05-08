package views;

import javafx.scene.Parent;
import mvc.Controller;
import mvc.FXView;

/**
 *	Hilfesystem Info Anzeige
 * 
 * @author hugo-lucca
 *
 */
public class HelpInfoView extends FXView
{

	public HelpInfoView(String newName, Controller newController) {
		// this constructor is the same for all view's on same stage
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
