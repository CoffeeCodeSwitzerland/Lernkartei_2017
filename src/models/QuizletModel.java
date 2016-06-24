package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Debugger;
import globals.Globals;
import mvc.Model;
import quizlet.Quizlet;

public class QuizletModel extends Model {

	Quizlet q;
	ArrayList<String> cards;
	
	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				
				init();

				Debugger.out("___" + param[0] + " " + param[1] + " " + param[2]);
				cards = q.getSet(param[0]);
				
				
				if (cards == null)
				{
					return -1;
				}
				

				setString(Integer.toString(cards.size()));
				
				for (String s : cards)
				{
					add(s);
				}
				

				cards = super.getDataList(null);
				
				int isSuccessful = LKDatabase.myStacks.newStack(param[1], param[2]);
				
				if (isSuccessful == -1)
				{
					return -1;
				}
				
				return 1;
				
			case UPDATE:
				
				
				if (cards != null)
				{
					if (cards.size() > 0)
					{
						String[] currentCard = cards.get(0).split(Globals.SEPARATOR);
						
						LKDatabase.myCards.pushToStock(new String[]{currentCard[1], currentCard[2], param[0], Integer.toString(1), Integer.toString(-16777216)});
						
						cards.remove(0);
						
						if (cards.size() == 0)
						{
							return 0;
						}
						
						return (1000 * (Integer.parseInt(getString(null)) / cards.size()));
					}
					
					cards.clear();
					setString(null);
					return -7;
				}
				
				
				return -1;
			default:
				return super.doAction(command, param);
		}
	}
	
	@Override
	public ArrayList<String> getDataList (String query)
	{
		if (query == null)
		{
			return null;
		}
		
		String[] queryData = query.split(Globals.SEPARATOR);
		
		if (queryData.length != 2)
		{
			return null;
		}
		
		init();
		
		return q.searchSet(queryData[0], queryData[1]);
	}
	
	private void init ()
	{
		if (q == null)
		{
			q = new Quizlet("3RhaPk5H9C");
		}
	}
}
