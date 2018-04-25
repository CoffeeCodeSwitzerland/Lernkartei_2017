package controls;

import mvc.fx.FXController;
import views.help.*;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verf�gung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class HelpController extends FXController
{
	@Override
	public void initMyModels() {
		// no Models for Help
	}

	@Override
	public void initMyViews() {
		// add only new view's, the helpview is added automaticly as mainView
		this.addUniqueView(new QuizletInfoView("quizletview", this));
		this.addUniqueView(new TroubleShootView("troubleshootview", this));
		this.addUniqueView(new ManualView("manualview", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
		this.addUniqueView(new LogView("logview", this));
		//this.addUniqueView(new MainView(getMainViewName(), this));
	}

	@Override
	public void startApp() {
	}
}
