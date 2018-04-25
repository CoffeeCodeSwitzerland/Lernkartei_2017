package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.ConfigDefaults;
import globals.ConfigDefaults.Configurations;
import globals.Globals;
import javafx.scene.Parent;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.BackButton;
import views.components.CheckBoxOption;
import views.components.ControlLayout;
import views.components.DelDBButton;
import views.components.MainLayout;
import views.components.NumberLabelOption;
import views.components.OptionInterface;
import views.components.StandardVBox;
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

	private BackButton backBtn = new BackButton(getFXController());
	private DelDBButton delBtn = new DelDBButton("Daten löschen");

	@Override
	public Parent constructContainer ()
	{
		ArrayList<OptionInterface> optionEntries = new ArrayList<>();

		NumberLabelOption stackPartSize = new NumberLabelOption(
				"cardLimit",
				"Anzahl Karten, die auf einmal gelernt werden, limitieren.",
				Globals.defaultStackPartSize,
				Globals.maxStackPartSize,
				Globals.minStackPartSize,
				getFXController());
		
		optionEntries.add(stackPartSize);

		ConfigDefaults.ini();
		for (Configurations c : Configurations.values())
		{
			try {
				optionEntries.add(new CheckBoxOption(c, getFXController()));
			} catch (Exception e) {
				Debugger.out("OptionsView-constructContainer: did not found a Model named '...'!");
			}		
		}

		StandardVBox optiLay = new StandardVBox();

		for (OptionInterface oi : optionEntries)
		{
			optiLay.add(oi.toNodesWithSeparator());
		}

		// Remove the last separator
		optiLay.getChildren().remove(optiLay.getChildren().size() - 1);

		VerticalScroller scroLay = new VerticalScroller(optiLay);
		ControlLayout contLay = new ControlLayout(backBtn, delBtn);
		MainLayout mainLay = new MainLayout(scroLay, contLay);

		try {
			getFXController().getModel("config").registerView(this);
		} catch (Exception e) {
			Debugger.out("OptionsView-constructContainer: did not found a Model named 'config'!");
		}		

		return mainLay;
	}

	@Override
	public void refreshView ()
	{
		backBtn.requestFocus();
	}
}
