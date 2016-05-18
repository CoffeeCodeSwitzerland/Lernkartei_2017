package controls;

import mvc.fx.FXController;
import views.HelpInfoView;
import views.HelpSerachView;
import views.ImpressumView;
import views.QuizletInfoView;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
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
		this.addUniqueView(new HelpSerachView("indexview", this));
		this.addUniqueView(new HelpInfoView("manualview", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
	}

	@Override
	public void startApp() {
	}
}
