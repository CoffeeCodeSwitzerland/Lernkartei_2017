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

	public static void out(String debugText) {
		if (debugActive && Globals.DebuggerIsOn) {
			if (debugText == null) {
				out();
			} else {
				System.out.println(">>"+debugText);
			}
		}
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
}
