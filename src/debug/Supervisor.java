package debug;

public class Supervisor {
	/**
	 * Diese rein statische Klasse fasst folgende Funktionalität zusammen:
	 * ================================================================== -
	 * erzeugen eines Log's und abspeicher in Datei (neu anlegen oder
	 * überschreiben möglich) - debug Meldungen mit Ein- und
	 * Ausschaltmöglichkeit - situativ einstellbare Reaktionen auf Warungen,
	 * Fehler und Exception - Umsetzung des "Raketenprinzips" (TODO: unabh.
	 * Überwachungsthread für Fehlerzustände)
	 * 
	 * @AUTHOR Hugo Lucca
	 */

	private static int supervisorShowState = 0; // 0..4
	private static int supervisorExitState = 1; // 0..4

	public static void error(Object callingObject, String myError) {
		if (supervisorShowState < 2) {
			Logger.log("Error", callingObject, myError);
			if (supervisorExitState < 2)
				System.exit(1);
		}
	}

	public static void errorAndDebug(Object callingObject, String myError) {
		warn (callingObject, myError);
		if (callingObject == null)
			Debugger.out(">>>ERROR: "+myError+" at ???");
		else
			Debugger.out(">>>ERROR: "+myError+" at "+ callingObject.toString());
	}

	public static void warn(Object callingObject, String myWarning) {
		if (supervisorShowState < 1) {
			Logger.log("Warning", callingObject, myWarning);
			if (supervisorExitState < 1) {
				System.exit(1);
			}
		}
	}

	public static void warnAndDebug(Object callingObject, String myWarning) {
		warn (callingObject, myWarning);
		if (callingObject == null)
			Debugger.out(">>>WRNING: "+myWarning+" at ???");
		else
			Debugger.out(">>>WRNING: "+myWarning+" at "+ callingObject.toString());
	}

	public static void exception(Exception e, String text) {
		if (supervisorShowState < 2) {
			Logger.log(e, text);
			if (supervisorExitState < 2)
				System.exit(3);
		}
	}

	public static void exception(Exception e) {
		exception(e, null);
	}

	public static void setSupervisorShowState(int supervisorShowState) {
		if (supervisorShowState > 4)
			supervisorShowState = 4;
		if (supervisorShowState < 0)
			supervisorShowState = 0;
		Supervisor.supervisorShowState = supervisorShowState;
	}

	public static void setSupervisorExitState(int supervisorExitState) {
		if (supervisorExitState > 4)
			supervisorExitState = 4;
		if (supervisorExitState < 0)
			supervisorExitState = 0;
		Supervisor.supervisorExitState = supervisorExitState;
	}

	public static int getSupervisorShowState() {
		return supervisorShowState;
	}

	public static int getSupervisorExitState() {
		return supervisorExitState;
	}
}
