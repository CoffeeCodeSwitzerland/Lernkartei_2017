package mvc.test;

import mvc.Model;
import mvc.fx.FXController;

/**
 * Diese Klasse kontrolliert alle Sichten und Modelle. Sie bietet die Naviagtion zur nächsten Sicht an.
 * Alle Sichten (ausser Modalfenster) werden hier mit eindeutigen Namen versehen.
 * 
 * @author hugo-lucca
 */
public class TestController extends FXController
{
	public TestController() {
		super(); // primary stage is saved in GUISettings!
	}

	@Override
	public void initMyModels() {
		this.addUniqueModel(new Model("auto"));
		this.addUniqueModel(new Model("lkw"));
	}

	@Override
	public void initMyViews() {
		this.addUniqueView(new SampleFXView ( getMainViewName(),this));
		this.addUniqueView(new TestView ( "test1view",this));
		this.addUniqueView(new TestView ( "test2view",this));
		this.addUniqueView(new TestView ( "test2view",this));
		this.showMainView();
	}

	@Override
	public void startApp() {
		// TODO Auto-generated method stub
		
	}
}
