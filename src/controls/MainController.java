package controls;

import debug.Logger;
import javafx.stage.Stage;
// Nicht �ndern
import models.*;
import mvc.fx.FXController;
// Nicht �ndern
import views.BBCodeInfoView;
import views.CreateDoorView;
import views.DMODoorView;
import views.DMOModifyDoorView;
import views.DMOModifyStackView;
import views.DMOStackView;
import views.DatabaseSelectionView;
import views.DoorStackInformationView;
import views.DoorView;
import views.EditorView;
import views.GameOptionView;
import views.GroupCreateView;
import views.GroupMemberView;
import views.GroupView;
import views.HelpView;
import views.LearnView;
import views.LernenSelectionView;
import views.LoginView;
import views.MainView;
import views.ManagementSelectionView;
import views.OptionsView;
import views.OwnDBConnectionView;
import views.PostLearnView;
import views.PreLearnView;
import views.QuizletImportView;
import views.RegisterView;
import views.RenameView;
import views.SaveDownloaddoorOnServerDialogView;
import views.SaveDownloadstackForeignDMOView;
import views.SaveDownloadstackLocalView;
import views.SaveDownloadstackOnServerDialogView;
import views.SaveDownloadstackOwnDMOView;
import views.SaveUploadstackOnServerDialogView;
import views.DoorSavepathSelectionView;
import views.ServerDoorView;
import views.ServerStackView;
import views.SimpleEditorView;
import views.StackSavepathSelectionView;
import views.StackView;
import views.StatsView;
import views.TeamworkServerDoorView;
import views.UserListView;
import views.UserView;
import views.components.MainViewSettings;
import views.game.GameView;
/**
 * This class controls all models and views in lists managed by the MVC package.
 * Views are 
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verf�gung gestellt. Alle Sichten (ausser Modalfenster) werden
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
		Logger.log("MainController.initMyModels: Instanziere Models....");
		debug.Debugger.out("MainController.initMyModels: Instanziere Models....");
		//TODO this.addUniqueModel(new DatabaseSelectionModel(), "databaseselectionmodel");
		this.addUniqueModel(new GameModel(),"game");
		this.addUniqueModel(new TuttoModel(),"tutto");
		this.addUniqueModel(new DoorModel(),"door");
		this.addUniqueModel(new DruckModel(),"drucken");
		this.addUniqueModel(new StackModel(),"stack");
		this.addUniqueModel(new CardModel(),"cards");
		this.addUniqueModel(new LearnModel(),"learn");
		this.addUniqueModel(new ProfilModel(),"profil");
		this.addUniqueModel(new QuizletModel(),"quizlet");
		this.addUniqueModel(new ConfigModel(),"config");
		this.addUniqueModel(new StatisticsModel(),"statistics");
		this.addUniqueModel(new UserSecurityModel(), "usersecuritymodel");
		this.addUniqueModel(new ServerStackModel(), "serverstack");
		this.addUniqueModel(new DoorStackInformationModel(), "doorstackinformationmodel");
		this.addUniqueModel(new DMOModifyDoorModel(), "dmomodifydoormodel");
		this.addUniqueModel(new DMOModifyStackModel(), "dmomodifystackmodel");
	}

	@Override
	public void initMyViews() {
		Logger.log("MainController.initMyViews: Instanziere Views....");
		debug.Debugger.out("MainController.initMyViews: Instanziere Views....");
		this.addUniqueView(new MainView(getMainViewName(), this));
		//this.addUniqueView(new StatisticsView("statisticsview", this));
		this.addUniqueView(new StatsView("statsview", this));
		
		addUniqueView(new OptionsView("optionsview", this));
		this.addViewOnNewStage(new HelpView("helpview", new HelpController())); // on new stage
		this.addUniqueView(new GameOptionView("gameoptionview", this));
		this.addUniqueView(new GameOptionView("tuttohelpview", this));
		this.addUniqueView(new GameView("gameview", this));
		this.addUniqueView(new QuizletImportView("quizlet", this));
		this.addUniqueView(new SimpleEditorView("simpleeditorview", this));
		this.addUniqueView(new EditorView("editorview", this));
		this.addUniqueView(new BBCodeInfoView("bbcodeinfo", this));
		
		this.addUniqueView(new DoorView("doorview", this));
		this.addUniqueView(new StackView("stack", this));
		this.addUniqueView(new RenameView("rename", this));
		
		this.addUniqueView(new LoginView("loginview", this));
		this.addUniqueView(new RegisterView("registerview", this));
		this.addUniqueView(new UserView("userview", this));
		this.addUniqueView(new GroupView("groupview", this));
		this.addUniqueView(new ManagementSelectionView("managementselectionview", this));
		this.addUniqueView(new GroupCreateView("groupcreateview", this));
		this.addUniqueView(new GroupMemberView("groupmemberview", this));
		this.addUniqueView(new UserListView("userlistview", this));
		this.addUniqueView(new ServerDoorView("serverdoorview", this));
		this.addUniqueView(new ServerStackView("serverstackview", this));
		this.addUniqueView(new DoorStackInformationView("doorstackinformationview", this));
		this.addUniqueView(new SaveDownloadstackOnServerDialogView("savedownloadstackonserverdialogview", this));
		this.addUniqueView(new DoorSavepathSelectionView("doorsavepathselectionview", this));
		this.addUniqueView(new TeamworkServerDoorView("teamworkserverdoorview", this));
		this.addUniqueView(new CreateDoorView("createdoorview", this));
		this.addUniqueView(new SaveDownloadstackOwnDMOView("savedownloadstackowndmoview", this));
		//this.addUniqueView(new SaveDownloadstackOwnDMOView("savedownloadstackowndmoview", this));
		this.addUniqueView(new SaveDownloadstackForeignDMOView("savedownloadstackforeigndmoview", this));
		this.addUniqueView(new SaveUploadstackOnServerDialogView("saveuploadstackonserverdialogview", this));
		this.addUniqueView(new SaveDownloadstackLocalView("savedownloadstacklocalview", this));
		//this.addUniqueView(new DeriveServerDoorView("deriveserverdoorview", this));
		this.addUniqueView(new StackSavepathSelectionView("stacksavepathselectionview",this));
		this.addUniqueView(new SaveDownloaddoorOnServerDialogView("savedownloaddooronserverdialogview",this));
		this.addUniqueView(new DMOModifyDoorView("dmomodifydoorview",this));
		this.addUniqueView(new DMOModifyStackView("dmomodifystackview",this));
		this.addUniqueView(new DMODoorView("dmodoorview",this));
		this.addUniqueView(new DMOStackView("dmostackview",this));
		
		
		this.addUniqueView(new PreLearnView("prelearn", this));
		this.addUniqueView(new LearnView("learnview", this));
		this.addUniqueView(new PostLearnView("postlearn", this));
		this.addUniqueView(new LernenSelectionView("lernenselectionview", this));
		
		this.addUniqueView(new DatabaseSelectionView("databaseselectionview", this));
		this.addUniqueView(new OwnDBConnectionView("owndbconnectionview", this));

		
//		this.addUniqueView(new ImpressumView("impressumview", this));
	}

	@Override
	public void startApp() {
		this.showMainView();
	}
}
