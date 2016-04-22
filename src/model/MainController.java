package model;

import gui.HelpView;
import gui.MainView;
import gui.OptionsView;
import javafx.stage.Stage;


public class MainController
{
	// Alle Views
	MainView	main;
	HelpView	help;
	OptionsView	options;

	public MainController (Stage primaryStage)
	{
		primaryStage.setTitle("WISS Learn Cards [Alpha]");

		main = new MainView(primaryStage, this);
		options = new OptionsView(primaryStage, this);
		help = new HelpView();
	}

	public void showMain ()
	{
		main.show();
	}

	public void showOptions ()
	{
		options.show();
	}

	public void showHelp ()
	{
		help.show();
	}

	// TODO quit method
}
