package controls;

import debug.Logger;
import javafx.stage.Stage;
import models.BoxModel;
import models.CardModel;
import models.DoorModel;
import models.GameModel;
import mvc.Controller;
import views.BoxView;
import views.DoorView;
import views.EditorView;
import views.GameView;
import views.HelpView;
import views.ImpressumView;
import views.KarteiView;
import views.LearnView;
import views.MainView;
import views.MainViewSettings;
import views.OptionsView;
import views.StatisticsView;
import views.StatsView;
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
	public MainController(Stage primaryStage) {
		super(primaryStage, new MainViewSettings());
	}

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
		this.addUniqueView(new MainView(getMainViewName(), this));
		this.addUniqueView(new StatisticsView("statisticsview", this));
		this.addUniqueView(new StatsView("statsview", this));
		this.addUniqueView(new DoorView("doorview", this));
		this.addUniqueView(new OptionsView("optionsview", this));
		this.addUniqueView(new HelpView("helpview")); // on new stage
		this.addUniqueView(new GameView("gameview", this));
		this.addUniqueView(new KarteiView("karteiview", this));
		this.addUniqueView(new BoxView("boxview", this));
		this.addUniqueView(new EditorView("editorview", this));
		this.addUniqueView(new LearnView("learnview", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
		Logger.log("Instanzierung beendet....");
	}
}
