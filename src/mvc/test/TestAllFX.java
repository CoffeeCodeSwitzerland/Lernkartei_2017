package mvc.test;

import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestAllFX extends Application {

	@Test
	public void myTest() { 
		ControllerTest.myTest();
		
	}

	@Test
	public void test() {
		String [] args = new String [1];
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		myTest();
	}
}
