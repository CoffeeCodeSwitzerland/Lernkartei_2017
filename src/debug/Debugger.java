package debug;

public final class Debugger {

	private static boolean debugActive   = true;

	public static void out (String debugText) {
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

	public static boolean isDebugActive() {
		return debugActive;
	}

	public static void setDebugActive(boolean debugActive) {
		Debugger.debugActive = debugActive;
	}
}
