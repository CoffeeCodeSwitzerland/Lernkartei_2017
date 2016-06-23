package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Supervisor;
import learning.Bewertungsklasse;
import mvc.Model;


public class LearnModel extends Model
{
	/**
	 * @deprecated
	 */
	@Override
	public int doAction (String functionName, String freeStringParam, double freeDoubleParam)
	{
		Supervisor.errorAndDebug(this, "Deprecated method (LearnModel). Please use the new doAction");
		return -9;
	}

	@Override
	public int doAction (Command command, String... param)
	{
		int newPriorityIsValid = 0;

		switch (command)
		{
			case TRUE:

				if (param.length != 1) { return -2; }

				newPriorityIsValid = Bewertungsklasse.cardIsCorrect(param[0]);

				LKDatabase.myUsers.correctCard();
				refreshViews();

				return newPriorityIsValid;

			case FALSE:

				if (param.length != 1) { return -2; }

				newPriorityIsValid = Bewertungsklasse.cardIsFalse(param[0]);

				refreshViews();

				return newPriorityIsValid;

			default:
				int successfulSuper = super.doAction(command, param);
				return successfulSuper;
		}
	};

	@Override
	public ArrayList<String> getDataList (String query)
	{
		if (query == null) { return super.getDataList(null); }

		ArrayList<String> memoList = super.getDataList(null);

		if (!getString(null).equals(query))
		{
			memoList = Bewertungsklasse.getShuffledCards(query);
			super.getDataList(null).clear();
			for (String s : memoList)
			{
				add(s);
			}
			setString(query);
		}

		return memoList;
	}

}
