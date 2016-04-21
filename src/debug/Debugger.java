package debug;

public final class Debugger {

	private static boolean debugActive = true;

	public static void out(String debugText) {
		if (debugActive) {
			if (debugText == null) {
				out();
			} else {
				System.out.println(debugText);
			}
		}
	}

	public static void out() {
		if (debugActive) {
			System.out.print("*");
		}
	}

	public static void eol() {
		if (debugActive) {
			System.out.println("");
		}
	}

	public static void out(char c, int len) {
		if (debugActive) {
			for (int i = 0; i < len; i++) {
				System.out.print(c);
			}
		}
		eol();
	}

	public static void title(String debugText) {
		if (debugActive) {
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
