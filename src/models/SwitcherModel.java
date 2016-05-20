package models;

import mvc.Model;

public class SwitcherModel extends Model {

	public SwitcherModel (String myName) {
		super(myName);
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {
		
		// 0 = Funktionsname falsch
		// 1 = Hat geklappt, egal welche Action
		// 1 = Bei check bedeutet: 1 = Ist in Tabelle, 0 = Ist nicht in Tabelle
		//-1 = paramS nicht gefunden / Stack nicht vorhanden

		if (functionName.equals("new")) {
			if (database.Switcher.newSwitch(paramS)) {
				return 1;
			} else {
				return -1;
			}
		}
		else if (functionName.equals("delete")) {
			if (database.Switcher.delSwitch(paramS)) {
				return 1;
			} else {
				return -1;
			}
		}
		else if (functionName.equals("check")) {
			if (database.Switcher.checkSwitched(paramS)) {
				return 1;
			} else {
				return -1;
			}
		}

		return 0;
	}

}
