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
public class DummyController extends Controller
{
	public DummyController() {
		super(new Stage(), new HelpViewSettings());
	}

	@Override
	public void initMyModels() {
		// no Models for dummy
	}

	@Override
	public void initMyViews() {
		// no views for dummy
	}
}
