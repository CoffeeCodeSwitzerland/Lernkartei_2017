package debug;

import globals.Globals;

public final class Debugger {

	/*-
	 * Purpose:	
	 * - to simplify debug
	 * - you may toggle debug activity with
	 * 			calling setDebugActive(true¦false)
	 * 
	 * @AUTHOR Hugo Lucca
	 */

	private static boolean debugActive = true;

	public static void out(String debugText, int calls, String param1, String param2) {
		if (debugActive && Globals.DebuggerIsOn) {
			if (debugText == null) {
				out();
			} else {
				String debugPrefix ="";
				for (int i=calls+1; i>=3; i--) {
					if (i < calls+1) debugPrefix += "->"; 
					debugPrefix += 
							Thread.currentThread().getStackTrace()[i].getClassName()+"."+
							Thread.currentThread().getStackTrace()[i].getMethodName();
					if ((calls+2-i) % 2 == 0 && i != 3) debugPrefix += "\n";
				}
				String p = "(";
				if (param1 != null) p += param1;
				if (param2 != null) {
					if (param1 != null) p+=",";
					p+=param2;
				}
				p+=")";
				String spacing ="  ";
				for (int i=0; i < 54-debugText.length();i++) spacing +=" ";
				System.out.println(">>"+debugText +spacing+"{"+debugPrefix+p+"}");
			}
		}
	}

	public static void out(String debugText, String param1, String param2) {
		if (param1 == null) param1 = "{null}";
		if (param2 == null) param2 = "{null}";
		if (debugActive && Globals.DebuggerIsOn) {
			if (debugText == null) {
				out();
			} else {
				System.out.println(">>"+debugText);
			}
		}
	}

	public static void out(String debugText, String param1, int param2) {
		if (param1 == null) param1 = "{null}";
		out (debugText, 3, param1, Integer.toString(param2));
	}

	public static void out(String debugText, int param1, int param2) {
		out (debugText, 3, Integer.toString(param1), Integer.toString(param2));
	}

	public static void out(String debugText, String param) {
		if (param == null) param = "{null}";
		out (debugText, 3, param, null);
	}

	public static void out(String debugText, int calls) {
		out (debugText, calls, null, null);
	}

	public static void out(String debugText) {
		out (debugText, 3, null, null);
	}

	public static void out() {
		if (debugActive && Globals.DebuggerIsOn) {
			System.out.print("*");
		}
	}

	public static void eol() {
		if (debugActive && Globals.DebuggerIsOn) {
			System.out.println("");
		}
	}

	public static void out(char c, int len) {
		if (debugActive && Globals.DebuggerIsOn) {
			for (int i = 0; i < len; i++) {
				System.out.print(c);
			}
		}
		eol();
	}

	public static void title(String debugText) {
		if (debugActive && Globals.DebuggerIsOn) {
			out(debugText);
			out('=', debugText.length());
		}
	}

	public static boolean isDebugActive() {
		return debugActive;
	}

	public static void setDebugActive(boolean debugActive) {
		Debugger.debugActive = debugActive;
	}

	public static void stop() {
		setDebugActive(false);
	}

	public static void start() {
		setDebugActive(true);
	}
}
