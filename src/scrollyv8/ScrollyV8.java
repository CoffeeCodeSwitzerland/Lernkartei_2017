package scrollyv8;

import javax.swing.*;

import debug.Debugger;
import models.GameModel;

import java.awt.*;
import java.awt.event.*;


public class ScrollyV8 extends JFrame implements KeyListener, WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	static int width = 900 - 30;
	static int height = 600 - 15;
	static String title = "Sidegame";
	public static gamePanel gPanel = new gamePanel();
	public static ScrollyV8 mf;

	public void dispose() {
		Debugger.out("Killing Player...");
		gPanel.killPlayer();
		super.dispose();
	}

	public void init() {
		gPanel.setFocusable(true);
		gPanel.addKeyListener(this);

	}

	public ScrollyV8() {
		// Settings for frame
		setSize(width, height);
		setResizable(false);
		setTitle("Scrolly");
		// Schliessung vom Fenster
		this.addWindowListener(new WindowAdapter() {
			// @SuppressWarnings("static-access")
			public void windowClosing(WindowEvent e) {
				Debugger.out("Exiting Game");
				gamePanel.gameState = gamePanel.INTRO;
				MidiPlayer.stop();
				gamePanel.sound = false;
				if(GameModel.gameController != null)
				{
					if(GameModel.gameController.getModel("game") != null)
					{
				GameModel.gameController.getModel("game").refreshViews();
					}
				}
			}
		});

		Container content = getContentPane();

		content.add(gPanel, BorderLayout.CENTER);
	}
	// Action Event Handlers

	public void keyPressed(KeyEvent k) {
		gPanel.handleInput('p', k);
	}

	public void keyReleased(KeyEvent k) {
		gPanel.handleInput('r', k);
	}

	public void keyTyped(KeyEvent k) {
		gPanel.handleInput('t', k);
		repaint();
		gPanel.update();
	}

	public static void main(String[] args) {
		Debugger.out("START Game via main...");
		mf = new ScrollyV8();
		mf.setVisible(true);
		mf.init();

		// Thread t = new Thread(new RunAudio(new Audio("alligator.mp3")));
		// t.start();
		// t.stop();

	}
	public static void hideWindow(){
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
		Debugger.out("Game dispose...");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
		Debugger.out("Game dispose...");

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		GameModel.gameController.getModel("game").refreshViews();

	}
}