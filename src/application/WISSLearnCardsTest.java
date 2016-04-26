package application;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;

public class WISSLearnCardsTest extends Application {

	public static void myTest (Stage primaryStage) {
		MainController m = new MainController(primaryStage);
		assertEquals(null,m.show("zzview"));
		assertNotEquals(null,m.show("helpview"));
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
