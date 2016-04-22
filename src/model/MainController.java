package model;

import gui.HelpView;
import gui.MainView;
import gui.OptionsView;
import javafx.stage.Stage;

public class MainController
{
	MainView main;
	HelpView help;
	OptionsView options;
	
	public MainController (Stage primaryStage)
	{
		main = new MainView(primaryStage, this);
		help = new HelpView();
		options = new OptionsView(primaryStage, this);
	}
	
	public void setMainView()
	{
		main.setMainScene();
	}
	
	public void setOptionsView()
	{
		options.setOptionsScene();
	}

}
