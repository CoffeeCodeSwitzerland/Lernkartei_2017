package controls;

import debug.Logger;
import javafx.stage.Stage;
import models.*;
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
		this.addUniqueModel(new StackModel("stack"));
		this.addUniqueModel(new CardModel("cards"));
		this.addUniqueModel(new LearnModel("learn"));
		this.addUniqueModel(new ProfilModel("profil"));
		this.addUniqueModel(new QuizletModel("quizlet"));
		this.addUniqueModel(new ConfigModel("config"));
	}

	@Override
	public void initMyViews() {
		Logger.log("Instanziere Views....");
		this.addUniqueView(new MainView(getMainViewName(), this));
		this.addUniqueView(new StatisticsView("statisticsview", this));
		this.addUniqueView(new StatsView("statsview", this));
		this.addUniqueView(new DoorView("doorview", this));
		this.addUniqueView(new OptionsView("optionsview", this));
		this.addViewOnNewStage(new HelpView("helpview", new HelpController())); // on new stage
		this.addUniqueView(new GameOptionView("gameoptionview", this));
		this.addUniqueView(new GameView("gameview", this));
		this.addUniqueView(new StackView("stack", this));
		this.addUniqueView(new QuizletImportView("quizlet", this));
		this.addUniqueView(new SimpleEditorView("simpleeditorview", this));
		this.addUniqueView(new EditorView1("editorview", this));
		this.addUniqueView(new LearnView("learnview", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
		Logger.log("Instanzierung beendet....");
		this.showMainView();
	}
}
