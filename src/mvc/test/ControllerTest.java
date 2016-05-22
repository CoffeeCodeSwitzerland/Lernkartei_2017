package mvc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;
import mvc.*;

public class ControllerTest extends Application {

	public static void myTest() {
		Controller c = new TestController();

		//assertEquals(true, c.getMainViewName().equals("mainview"));
		//assertNotEquals(null, c.getModel("auto"));
		assertNotEquals(null, c.getModel("lkw"));
		assertEquals(null, c.getModel("xyz"));
		//assertEquals(3,c.getViews().size());
		//Iterator<View> it = c.getViews().iterator();
//		int found = 0;
//		while (it.hasNext()) {
//			View v = it.next();
//			if (v.getName().equals("test1view")) {
//				found++;
//			}
//		}
//		assertEquals(1,found);

//		it = c.getViews().iterator();
//		found = 0;
//		while (it.hasNext()) {
//			View v = it.next();
//			if (v.getName().equals("test2view")) {
//				found++;
//			}
//		}
//		assertEquals(1,found);
//	
//		it = c.getViews().iterator();
//		found = 0;
//		while (it.hasNext()) {
//			View v = it.next();
//			if (v.getName().equals("mainview")) {
//				found++;
//			}
//		}
//		assertEquals(1,found);
//	
//		it = c.getViews().iterator();
//		found = 0;
//		while (it.hasNext()) {
//			View v = it.next();
//			if (v.getName().equals("view")) {
//				found++;
//			}
//		}
//		assertEquals(0,found);
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
