package views;

import globals.ConfigDefaults;
import globals.ConfigDefaults.Configurations;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.BackButton;
import views.components.CheckBoxOption;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.NumberLabelOption;
import views.components.VerticalScroller;


/**
 * Optionen
 * 
 * @author miro albrecht
 *
 */
public class OptionsView extends FXView
{
	public OptionsView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	boolean		isChangeingCardLimitValue	= false;
	
	String		lastValidCardLimit;
	TextField	cardLearnLimit;

	String		descTxtCardLimit			= "Anzahl Karten, die auf einmal gelernt werden, limitieren.";

	BackButton	backBtn						= new BackButton(getFXController());

	@Override
	public Parent constructContainer ()
	{
		NumberLabelOption nlo = new NumberLabelOption("cardLimit", descTxtCardLimit,
				Globals.defaultStackPartSize, Globals.maxStackPartSize, Globals.minStackPartSize,
				getFXController());
		
		VBox optiLay = new VBox(20);

		optiLay.setPadding(new Insets(30));
		optiLay.setAlignment(Pos.CENTER);
		
		optiLay.getChildren().addAll(nlo.toNodesWithSepp());
		
		ConfigDefaults.ini();
		for (Configurations c : Configurations.values())
		{
			CheckBoxOption cbo = new CheckBoxOption(c, getFXController());
			optiLay.getChildren().addAll(cbo.toNodesWithSepp());
		}
		
		// Remove the last separator
		optiLay.getChildren().remove(optiLay.getChildren().size() - 1);
		
		VerticalScroller scroLay = new VerticalScroller(optiLay);
		ControlLayout contLay = new ControlLayout(backBtn);
		MainLayout mainLay = new MainLayout(scroLay, contLay);

		getFXController().getModel("config").registerView(this);

		return mainLay;
	}

	@Override
	public void refreshView ()
	{
		backBtn.requestFocus();
	}
}
