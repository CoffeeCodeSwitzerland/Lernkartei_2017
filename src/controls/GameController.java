package controls;

import mvc.fx.FXController;
import views.game.*;

public class GameController extends FXController {
	
	@Override
	public void initMyModels() {
		// no Models for Help
	}

	@Override
	public void initMyViews() {
		// add only new view's, the helpview is added automaticly as mainView
		this.addUniqueView(new GameView("gameoptionview",this));
		this.addUniqueView(new TuttoHelpView("tuttohelpview",this));
	}

	@Override
	public void startApp() {
		
	}
}
