package statistics;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class StatsViewTest extends Application 
{

	ObservableList<String> TestList = FXCollections.observableArrayList();
	
	@Before
	public void setUp() throws Exception
	{
		
	}

	@After
	public void tearDown() throws Exception
	{
		
	}

	@Test
	public void test()
	{
		myTest();
	}
	
	public void myTest() 
	{
		System.out.println("Test start");
	}

	@Override
	public void start(Stage arg0) throws Exception
	{
		myTest();	
	}

}
