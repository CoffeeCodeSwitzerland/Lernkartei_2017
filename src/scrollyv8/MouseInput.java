package scrollyv8;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
	private static final int NUM_BUTTONS = 10;

	private static final boolean[] buttons = new boolean[NUM_BUTTONS];
	private static final boolean[] lastButtons = new boolean[NUM_BUTTONS];
	private static int x = -1, y = -1;
	private static int lastX = x, lastY = y;
	private static boolean moving;

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
			moving = true;
		
	}

	public static void update() {
		for (int i = 0; i < NUM_BUTTONS; i++) {
			lastButtons[i] = buttons[i];
			
			//TODO : Muss man überprüfen wie lange die Maus still ist.
			//Nur falsch wenn 5 seconds nichts bewegt
			if(x == lastX && y == lastY) moving = false;
			lastX = x;
			lastY = y;
		}
	}

	public static boolean isDown(int button) {
		return buttons[button];
	}

	public static boolean wasPressed(int button) {
		return isDown(button) && !lastButtons[button];
	}

	public static boolean wasReleased(int button) {
		return !isDown(button) && lastButtons[button];
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}

	public static boolean isMoving() {
		return moving;
	}

}
