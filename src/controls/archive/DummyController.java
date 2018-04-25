package controls.archive;

import mvc.fx.FXController;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public abstract class DummyController extends FXController
{
	@Override
	public void initMyViews() {
		// no views for dummy
	}

	@Override
	public void initMyModels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startApp() {
		// TODO Auto-generated method stub
		
	}
}
