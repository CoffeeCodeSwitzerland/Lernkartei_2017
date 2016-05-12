package mvc;

import javafx.stage.Stage;
/**
 * Diese Klasse ist des Basis Codegerüst für die Instanzierung der View's und der Model's.
 * Sie bietet die Naviagtion zum nächsten View an und die Suche nach einem bestimmten Modell an.
 * 
 * @author hugo-lucca
 */
public abstract class FXController extends Controller
{
	private FXSettings theFXSettings;

	public FXController ()
	{
		this(new Stage(), new FXSettings());
	}

	public FXController (Stage primaryStage, FXSettings newFXSettings)
	{
		this.theFXSettings = newFXSettings;
		if (this.theFXSettings == null) {
			theFXSettings = new FXSettings();
		}
		if (primaryStage == null) {
			primaryStage = new Stage();
		}
		getTheFXSettings().setPrimaryStage(primaryStage);

		this.start();
	}

	public FXSettings getTheFXSettings() {
		return theFXSettings;
	}

	public void setTheFXSettings(FXSettings theFXSettings) {
		this.theFXSettings = theFXSettings;
	}

	public boolean addViewOnNewStage (FXView newView) {		
		this.addUniqueView(newView);
		
		newView.getController().addUniqueView(newView);
		newView.getController().setMainViewName(newView.getName());
		
		return true;
	}
}
