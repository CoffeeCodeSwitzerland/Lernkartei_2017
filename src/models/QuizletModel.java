package models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import globals.Globals;
import quizlet.Quizlet;
import mvc.Model;

public class QuizletModel extends Model {

	@Override
	public ArrayList<String> getDataList (String query) {
		
		if (query.split(Globals.SEPARATOR)[0].equals("search")) {
			
			Quizlet q = new Quizlet("3RhaPk5H9C");
			try {
				return q.searchSet(query.split(Globals.SEPARATOR)[1]);
			}
			catch (MalformedURLException e) {
				debug.Debugger.out(e.getMessage());
			}
			catch (IOException e) {
				debug.Debugger.out(e.getMessage());
			}
		} else if (query.split(Globals.SEPARATOR)[0].equals("set")) {
			
			Quizlet q = new Quizlet("3RhaPk5H9C");
			try {
				return q.getSet(query.split(Globals.SEPARATOR)[1]);
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
