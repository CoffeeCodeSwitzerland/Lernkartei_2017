package controls;

import javafx.stage.Stage;
import mvc.Controller;
import views.GameView;
import views.HelpViewSettings;

public class GameController extends Controller {
	public GameController() {
		super(new Stage(), new HelpViewSettings());
	}

	@Override
	public void initMyModels() {
		// no Models for Help
	}

	@Override
	public void initMyViews() {
		// add only new view's, the helpview is added automaticly as mainView
		this.addUniqueView(new GameView("gameoptionview", this));
		
	}
}
