package scrollyv8;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private static final int NUM_KEYS = 256;
	private static final boolean[] keys = new boolean[NUM_KEYS];
	private static final boolean[] lastKeys = new boolean[NUM_KEYS];

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			lastKeys[i] = keys[i];
		}
	}

	// Während die Taste geklickt wird
	public static boolean isDown(int key) {
		return keys[key];
	}

	// einmal beim klick
	public static boolean wasKeyPressed(int keyCode) {
		return isDown(keyCode) && !lastKeys[keyCode];
	}

	// nur einmal wenn losgelassen wird
	public static boolean wasReleased(int keyCode) {
		return !isDown(keyCode) && lastKeys[keyCode];
	}
}
