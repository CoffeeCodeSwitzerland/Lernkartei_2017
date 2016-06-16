package models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import globals.Globals;
import quizlet.Quizlet;
import mvc.Model;

public class QuizletModel extends Model {

	@Override
	public ArrayList<String> getDataList (String query)
	{
		String[] queryData = query.split(Globals.SEPARATOR);
		
		if (queryData.length != 2 && queryData.length != 3)
		{
			return null;
		}
		if (queryData.length == 2)
		{
			queryData = new String[]{queryData[0], queryData[1], null};
		}
		
		if (queryData[0].equals("search")) {
			
			Quizlet q = new Quizlet("3RhaPk5H9C");
			try {
				return q.searchSet(queryData[1], queryData[2]);
			}
			catch (MalformedURLException e) {
				debug.Debugger.out(e.getMessage());
			}
			catch (IOException e) {
				debug.Debugger.out(e.getMessage());
			}
		} else if (queryData[0].equals("set")) {
			
			Quizlet q = new Quizlet("3RhaPk5H9C");
			try {
				return q.getSet(queryData[1]);
			}
			catch (MalformedURLException e) {
				debug.Debugger.out(e.getMessage());
			}
			catch (IOException e) {
				debug.Debugger.out(e.getMessage());
			}
		} else {
			return null;			
		}
		return null;
	}
}
