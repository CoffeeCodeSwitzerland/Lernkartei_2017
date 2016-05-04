package application;

import debug.Logger;
import gui.*;
import models.*;
import mvc.Controller;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class MainController extends Controller
{
	@Override
	public void initMyModels() {
		Logger.stop();
		Logger.log("Instanziere Models....");
		this.addUniqueModel(new GameModel("game"));
		this.addUniqueModel(new DoorModel("door"));
		this.addUniqueModel(new BoxModel("box"));
		this.addUniqueModel(new CardModel("cards"));
	}

	@Override
	public void initMyViews() {
		Logger.log("Instanziere Views....");
		this.addUniqueView(new MainView(getMainView(), this));
		Logger.log("Instanziere Statistics....");
		this.addUniqueView(new StatisticsView("statisticsview", this));
		Logger.log("Instanziere Doors....");
		this.addUniqueView(new DoorView("doorview", this));
		Logger.log("Instanziere Options....");
		this.addUniqueView(new OptionsView("optionsview", this));
		this.addUniqueView(new HelpView("helpview"));
		this.addUniqueView(new GameView("gameview", this));
		this.addUniqueView(new KarteiView("karteiview", this));
		this.addUniqueView(new BoxView("boxview", this));
		this.addUniqueView(new EditorView("editorview", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
		Logger.log("Instanzierung beendet....");
	}
}
