package controls;

import debug.Logger;
import javafx.stage.Stage;
import models.*; // Nicht ändern
import mvc.fx.FXController;
import views.*; // Nicht ändern
import views.components.MainViewSettings;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class MainController extends FXController
{
	public MainController(Stage primaryStage) {
		super(new MainViewSettings(primaryStage));
	}

	@Override
	public void initMyModels() {
		Logger.log("Instanziere Models....");
		debug.Debugger.out("Instanziere Models....");
		this.addUniqueModel(new GameModel(),"game");
		this.addUniqueModel(new DoorModel(),"door");
		this.addUniqueModel(new DruckModel(),"drucken");
		this.addUniqueModel(new StackModel(),"stack");
		this.addUniqueModel(new CardModel(),"cards");
		this.addUniqueModel(new LearnModel(),"learn");
		this.addUniqueModel(new ProfilModel(),"profil");
		this.addUniqueModel(new QuizletModel(),"quizlet");
		this.addUniqueModel(new ConfigModel(),"config");
		this.addUniqueModel(new StatisticsModel(),"statistics");
	}

	@Override
	public void initMyViews() {
		Logger.log("Instanziere Views....");
		debug.Debugger.out("Instanziere Views....");
		this.addUniqueView(new MainView(getMainViewName(), this));
		//this.addUniqueView(new StatisticsView("statisticsview", this));
		this.addUniqueView(new StatsView("statsview", this));
		
		addUniqueView(new OptionsView("optionsview", this));
		this.addViewOnNewStage(new HelpView("helpview", new HelpController())); // on new stage
		this.addUniqueView(new GameOptionView("gameoptionview", this));
		this.addUniqueView(new GameView("gameview", this));
		this.addUniqueView(new QuizletImportView("quizlet", this));
		this.addUniqueView(new SimpleEditorView("simpleeditorview", this));
		this.addUniqueView(new EditorView("editorview", this));
		this.addUniqueView(new BBCodeInfoView("bbcodeinfo", this));
		
		this.addUniqueView(new DoorView("doorview", this));
		this.addUniqueView(new StackView("stack", this));
		this.addUniqueView(new RenameView("rename", this));
		
		this.addUniqueView(new LoginView("loginview", this));
		
		this.addUniqueView(new PreLearnView("prelearn", this));
		this.addUniqueView(new LearnView("learnview", this));
		this.addUniqueView(new PostLearnView("postlearn", this));

		
		this.addUniqueView(new ImpressumView("impressumview", this));
		Logger.log("Instanzierung beendet....");
		debug.Debugger.out("Instanzierung beendet....");

	}

	@Override
	public void startApp() {
		this.showMainView();
	}
}
