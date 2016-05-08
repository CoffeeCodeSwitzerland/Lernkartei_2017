package views;

import javafx.scene.Parent;
import mvc.Controller;
import mvc.FXView;

/**
 * Hilfe System Index-Suche
 *  
 * @author hugo-lucca
 *
 */
public class HelpSerachView extends FXView
{

	public HelpSerachView(String newName, Controller newController) {
		// this constructor is the same for all view's on same stage
		super(newName, newController);
		Parent p = constructContainer();
		if (p==null) {
			p = getMainLayout();
		}
		p.setId(this.getName());
		setupScene(p);
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
