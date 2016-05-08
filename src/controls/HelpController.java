package controls;

import javafx.stage.Stage;
import mvc.Controller;
import views.*;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class HelpController extends Controller
{
	public HelpController() {
		super(new Stage(), new HelpViewSettings());
	}

	@Override
	public void initMyModels() {
		// no Models for Help
	}

	@Override
	public void initMyViews() {
		// add only new view's, the helpview is added automaticly as mainView
		this.addUniqueView(new HelpSerachView("index", this));
		this.addUniqueView(new HelpInfoView("manual", this));
		this.addUniqueView(new ImpressumView("impressum", this));
	}
}
