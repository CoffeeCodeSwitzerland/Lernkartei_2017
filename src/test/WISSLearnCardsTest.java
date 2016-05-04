package test;

import static org.junit.Assert.*;

import org.junit.Test;

import application.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class WISSLearnCardsTest extends Application {

	public static void myTest (Stage primaryStage) {
		MainController m = new MainController(primaryStage);
		assertEquals(null,m.getView("zzview"));
		assertNotEquals(null,m.getView("helpview"));
	}

	@Test
	public void test() {
		String [] args = new String [1];
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		myTest(primaryStage);
	}

}
