package scrollyv8;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import debug.Debugger;
import debug.Logger;

/*
 * Still to do: - Spike/Foreground Layer - mountains - New Level - Enemies
 * Sprites - Boss too small - parallax - ... - quicksort? - Level 2 - Splash
 * screens - ... - New behaviour?
 */
public class gamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	final int HOME = 0;
	final int OFFICE = 1;
	final int LAPTOP = 2;
	final int JAR = 3;
	int workLoc = JAR;
	static boolean sound = true;
	public static final int INTRO = 0, PLAYING = 1, CUTSCENE = 2, GAMEOVER = 3, JUSTDIED = 4, LOADINTRO = 5,
			LOADLEVEL = 6, BOSS = 7, PASSEDBOSS = 8, WON = 9; // temporary
																// variables
	Thread fred;
	double t = 0;
	private int W = 900; // screen
							// width
	private int drawW = 865; //
	private int H = 600; // screen
							// height
	private int drawH = 570; // screen
								// width
	private boolean left, right, jump, ground; // down, up, doubleJump
	private double x, y, playerStartX, playerStartY, pw, ph;
	private double vx, vy;
	private double bossXL, bossXR, bossYU, bossYD;
	private double bossScreenX, bossScreenY;
	private double grav;
	private double CX, CY;
	@SuppressWarnings("unused")
	private double screenX, screenY, tmpScreenX;
	private int skyeY = 0;
	private double dVx = 0.3;
	private double friction = 0.2;
	private int bounce = 9;
	private int spriteRate;
	private int lives;
	private int tickCounter;
	static int gameState;
	private int bossIndex;
	private int level;
	private int levelMax;
	boolean network; // Load
						// files
						// locally(F)
						// or
						// from
						// server(T)?
	private int brickWidth;
	private Graphics2D g2D;
	private GradientPaint skye;
	private Color gnd;
	static MidiPlayer mPlayer;
	static ClipPlayer clips;
	private Image introScreen, gameOverScreen, loadScreen, level1Screen, level2Screen, youWinScreen;
	private String status = "";
	private TileLayer tiles, fg1, bg1, bg2, bg3, spikes;
	private TileManager tm;
	private Player sp;
	private Enemy[] enemies;
	private int numEnemies;
	public static String spritePath, soundPath, screenPath;
	private String levelBase, pathFile;
	@SuppressWarnings("unused")
	private double levelMinX, levelMinY, levelMaxX, levelMaxY;
	private int sleepTime;
	private int mapW, mapH;
	private int mapTileW, mapTileH;
	private int spriteGID;
	@SuppressWarnings("unused")
	private Thread t1 = new Thread(new RunAudio(new Audio("alligator.mp3")));;
	private boolean running = true;
	public static boolean first = true;

	// private AudioClip levelClip;
	// private AudioClip clipL1, clipL2, clipDie, clipGameOver, clipPassed,
	// clipBoss;

	public gamePanel() {
		// INITIALIZE VARIABLES
		// constants
		grav = 0.4;
		// game timers
		sleepTime = 14;
		tickCounter = 100;
		// player variables
		dVx = 0.25;
		friction = 0.02;
		bounce = 9;
		spriteRate = 16; // lower is faster :0

		x = y = 25;
		vx = vy = 0;
		lives = database.Score.getLifecount() -1;
		jump = false;
		//doubleJump = false;
		ground = true;
		pw = 30;
		ph = 45;
		// Dimensions
		CX = 450;
		CY = 300;
		brickWidth = 30;
		// Keylistener booleans
		// up, down = false;
		left = right = false;
		// Game state variables
		bossIndex = 0;
		level = 1;
		levelMax = 2;
		gameState = LOADINTRO;

		right = left  = false; //  down, up
		screenX = x;
		screenY = y;
		tmpScreenX = 25;

		switch (workLoc) {
		case HOME:
			levelBase = "D:/Documents/Prog/Java/ScrollyV8/Levels/Dev/";
			pathFile = "D:/Documents/Prog/Java/ScrollyV8/";
			break;
		case OFFICE:
			levelBase = "C:/Users/Andrew/Documents/Prog/Java/ScrollyV8/Levels/Dev/";
			pathFile = "C:/Users/Andrew/Documents/Prog/Java/ScrollyV8/";
			break;
		case LAPTOP:
			levelBase = "/Users/amacrae/Documents/Prog/Java/ScrollyV8/Levels/Dev/";
			pathFile = "/Users/amacrae/Documents/Prog/Java/ScrollyV8/";
			break;
		case JAR:
			levelBase = "GameFiles/Levels/Dev/";
			pathFile = "GameFiles/";
			break;
		}

		screenPath = pathFile + "Images/Screens/";
		spritePath = pathFile + "Images/Sprites/";
		soundPath = pathFile + "Sounds/";

		mPlayer = new MidiPlayer(soundPath + "Level3.mid", true);

		resetTiles();

		try {
			Debugger.out("Adding Image: " + screenPath + "Mark1.png");
			introScreen = ImageIO.read(new File(screenPath + "Mark1.png"));
			loadScreen = ImageIO.read(new File(screenPath + "LoadMark1.png"));
			level1Screen = ImageIO.read(new File(screenPath + "level1.png"));
			level2Screen = ImageIO.read(new File(screenPath + "level2.png"));
			gameOverScreen = ImageIO.read(new File(screenPath + "GameOverMark1.png"));
			youWinScreen = ImageIO.read(new File(screenPath + "YouWin.png"));
		} catch (IOException e) {
			Logger.log("Error loading screen images");
			e.printStackTrace();
		}

		tm = new TileManager(250);
		fred = new Thread(this);
		fred.start();
	}
	private void jumping(){
		vy -= bounce;
		jump = false;
		ground = false;
	}
	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponents(g);
		if (gameState == LOADINTRO) {
			g.drawImage(loadScreen, 0, 0, drawW, drawH, null);
			g.setColor(Color.white);
			g.drawString(status, 10, H - 10);

		} else if (gameState == INTRO) {
			// menu.render(g);
			g.drawImage(introScreen, 0, 0, drawW, drawH, null);
		} else if (gameState == LOADLEVEL) {
			Image toDraw = null;
			switch (level) {
			case 1:
				toDraw = level1Screen;
				break;
			case 2:
				toDraw = level2Screen;
				break;
			}
			g.drawImage(toDraw, 0, 0, drawW, drawH, null);
		} else if (gameState == PLAYING) {
			g2D = (Graphics2D) g;
			g2D.setPaint(skye);
			g2D.fill(new Rectangle(0, 0, W, Math.max(skyeY - (int) screenY, 0)));
			g.setColor(gnd);
			g.fillRect(0, Math.max(skyeY - (int) screenY, 0), W, H - Math.max(skyeY - (int) screenY, 0));
			drawLayer(g, bg3, 4);
			drawLayer(g, bg2, 2);
			drawLayer(g, bg1, 1);
			drawLayer(g, tiles, 1);
			drawLayer(g, spikes, 1);
			sp.draw(g, screenX - CX, screenY - CY);
			for (int i = 0; i < numEnemies; i++) {
				enemies[i].draw(g, screenX - CX, screenY - CY);
			}
			drawLayer(g, fg1, 1);
			for (int i = 0; i < lives; i++) {
				sp.drawStatic(g, (int) (2 * CX - 150 + i * (sp.w + 5)), 5);
			}
		} else if (gameState == BOSS) {
			g2D = (Graphics2D) g;
			g2D.setPaint(skye);
			g2D.fill(new Rectangle(0, 0, W, Math.max(skyeY - (int) screenY, 0)));
			g.setColor(gnd);
			g.fillRect(0, Math.max(skyeY - (int) screenY, 0), W, H - Math.max(skyeY - (int) screenY, 0));
			drawLayer(g, bg3, 4, bossScreenX, bossScreenY);
			drawLayer(g, bg2, 2, bossScreenX, bossScreenY);
			drawLayer(g, bg1, 1, bossScreenX, bossScreenY);
			drawLayer(g, tiles, 1, bossScreenX, bossScreenY);
			drawLayer(g, spikes, 1, bossScreenX, bossScreenY);
			sp.draw(g, bossScreenX - CX, bossScreenY - CY);

			for (int i = 0; i < numEnemies; i++) {
				enemies[i].draw(g, bossScreenX - CX, bossScreenY - CY);
			}

			for (int i = 0; i < lives; i++) {
				sp.drawStatic(g, (int) (2 * CX - 200 + i * (sp.w + 5)), 5);
			}
			for (int i = 0; i < enemies[bossIndex].getHP(); i++) {
				g.setColor(Color.red);
				g.fillRect((int) (2 * CX - 100), 20 + i * 20, 40, 20);
			}
			if (!enemies[bossIndex].isAlive()) {
				level++;
				gameState = PASSEDBOSS;
				tickCounter = 250;
				skye = new GradientPaint(W / 2, H, Color.YELLOW, W / 2, 0, Color.WHITE);
				if (sound) {
					mPlayer.setTrack(soundPath + "passedLevel.mid", false);
					mPlayer.play();
				}
			}
		} else if (gameState == PASSEDBOSS) {
			g2D = (Graphics2D) g;
			g2D.setPaint(skye);
			g2D.fill(new Rectangle(0, 0, W, Math.max(skyeY - (int) screenY, 0)));
			g.setColor(gnd);
			g.fillRect(0, Math.max(skyeY - (int) screenY, 0), W, H - Math.max(skyeY - (int) screenY, 0));
			drawLayer(g, bg3, 4, bossScreenX, bossScreenY);
			drawLayer(g, bg2, 2, bossScreenX, bossScreenY);
			drawLayer(g, bg1, 1, bossScreenX, bossScreenY);
			drawLayer(g, tiles, 1, bossScreenX, bossScreenY);
			drawLayer(g, spikes, 1, bossScreenX, bossScreenY);
			sp.draw(g, bossScreenX - CX, bossScreenY - CY);

			for (int i = 0; i < numEnemies; i++) {
				enemies[i].draw(g, bossScreenX - CX, bossScreenY - CY);
			}

			for (int i = 0; i < lives; i++) {
				sp.drawStatic(g, (int) (2 * CX - 150 + i * (sp.w + 5)), 5);
			}
			for (int i = 0; i < enemies[bossIndex].getHP(); i++) {
				g.setColor(Color.red);
				g.fillRect((int) (2 * CX - 100), 20 + i * 20, 40, 20);
			}
		} else if (gameState == GAMEOVER) {
			g.drawImage(gameOverScreen, 0, 0, drawW, drawH, null);
		} else if (gameState == JUSTDIED) {
			if ((tickCounter / 8) % 2 == 0) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.red);
			}
			g.fillRect(0, 0, W, H);
			for (int i = 0; i < lives; i++) {
				sp.drawStatic(g, (int) (CX + i * (sp.w + 5)), (int) CY - 10);
			}
		} else if (gameState == WON) {
			g.drawImage(youWinScreen, 0, 0, drawW, drawH, null);
		}

	}

	public void update() {
		repaint();
	}

	// Thread Stuff
	public void start() {
		fred = new Thread(this);
		fred.start();
	}

	public void run() {
		while (true) {
			// move();
			update();

			if (gameState == LOADINTRO) {
				Debugger.out("Loading sprite Images ...");
				status = "Loading sprite Images ...";
				repaint();

				String[] fNames_r = { spritePath + "smiley_r_1.PNG", spritePath + "smiley_r_2.PNG",
						spritePath + "smiley_r_3.PNG", spritePath + "smiley_r_4.PNG", spritePath + "smiley_r_5.PNG",
						spritePath + "smiley_r_6.PNG", spritePath + "smiley_r_7.PNG", spritePath + "smiley_r_8.PNG",
						spritePath + "smiley_r_9.PNG", spritePath + "smiley_r_10.PNG", spritePath + "smiley_r_11.PNG" };
				String[] fNames_l = { spritePath + "smiley_l_1.PNG", spritePath + "smiley_l_2.PNG",
						spritePath + "smiley_l_3.PNG", spritePath + "smiley_l_4.PNG", spritePath + "smiley_l_5.PNG",
						spritePath + "smiley_l_6.PNG", spritePath + "smiley_l_7.PNG", spritePath + "smiley_l_8.PNG",
						spritePath + "smiley_l_9.PNG", spritePath + "smiley_l_10.PNG", spritePath + "smiley_l_11.PNG" };
				String[] fNames_ju = { spritePath + "smiley_j_r_u.PNG" };
				String[] fNames_Ju = { spritePath + "smiley_j_l_u.PNG" };
				String[] fNames_jd = { spritePath + "smiley_j_r_d.PNG" };
				String[] fNames_Jd = { spritePath + "smiley_j_l_d.PNG" };
				String[] fNames_s = { spritePath + "smiley_stand_r_1.PNG" };
				String[] fNames_S = { spritePath + "smiley_stand_l_1.PNG" };

				sp = new Player(x, y, pw, ph, fNames_r, fNames_l, fNames_ju, fNames_Ju, fNames_jd, fNames_Jd, fNames_s,
						fNames_S, spriteRate);
				Debugger.out("done.\n");
				Debugger.out("Loading tile Images ...");
				status = "Loading tile Images ...";
				repaint();

				loadLevel(levelBase + "level1.tmx");
				Debugger.out("done.\n");

				levelMinX = levelMinY = 0;
				levelMaxX = tiles.get(tiles.size() - 1).x + brickWidth;
				levelMaxY = tiles.get(tiles.size() - 1).y + 2 * brickWidth;

				Debugger.out("Loading background ...");
				status = "Loading background ...";
				repaint();

				Debugger.out("done.\n");
				if (sound) {
					mPlayer.setTrack(soundPath + "Level3.mid", true);
					mPlayer.play();
				}

				gameState = INTRO;
				Debugger.out("INTRO.\n");
				repaint();
			} else if (gameState == LOADLEVEL) {
				Debugger.out("LOADLEVEL.\n");
				String lPath;
				switch (level) {
				case 1:
					skye = new GradientPaint(W / 2, H, Color.BLUE, W / 2, 0, Color.WHITE);
					gnd = Color.green;
					skyeY = 820;
					lPath = levelBase + "level1.tmx";
					bossScreenX = 135 * brickWidth;
					bossScreenY = 11 * brickWidth;
					bossXL = 128 * brickWidth;
					bossXR = bossXL + 30 * brickWidth;
					bossYU = 11 * brickWidth;
					bossYD = bossYU + 15 * brickWidth;
					if (sound) {
						mPlayer.setTrack(soundPath + "Level1.mid", true);
						mPlayer.play();
					}
					break;
				case 2:
					skye = new GradientPaint(W / 2, H, Color.RED, W / 2, 0, Color.WHITE);
					gnd = new Color(12, 134, 61);
					skyeY = 1020;
					lPath = levelBase + "level2.tmx";
					bossScreenX = 164 * brickWidth;
					bossScreenY = 21 * brickWidth;
					bossXR = 171 * brickWidth;
					bossXL = bossXR - 30 * brickWidth;
					bossYU = 16 * brickWidth;
					bossYD = bossYU + 25 * brickWidth;
					if (sound) {
						mPlayer.setTrack(soundPath + "Level2.mid", true);
						mPlayer.play();
					}
					break;
				default:
					skye = new GradientPaint(W / 2, H, Color.BLUE, W / 2, 0, Color.WHITE);
					lPath = levelBase + "level1.tmx";
					bossScreenX = 135 * brickWidth;
					bossScreenY = 11 * brickWidth;
					bossXL = 128 * brickWidth;
					bossXR = bossXL + 30 * brickWidth;
					bossYU = 11 * brickWidth;
					bossYD = bossYU + 15 * brickWidth;
					lPath = levelBase + "level1.tmx";
					if (sound) {
						mPlayer.setTrack(soundPath + "Level1.mid", true);
						mPlayer.play();
					}
					break;
				}
				loadLevel(lPath);
				Debugger.out("done.\n");

				levelMinX = levelMinY = 0;
				levelMaxX = tiles.get(tiles.size() - 1).x + brickWidth;
				levelMaxY = tiles.get(tiles.size() - 1).y + 2 * brickWidth;

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				gameState = PLAYING;
				Debugger.out("Playing.\n");
				repaint();
			} else if (gameState == PLAYING || gameState == BOSS) {
				vx *= (1 - .1);// friction
				if (Math.abs(vx) < friction) {
					vx = 0;
				}
				if (right) {
					vx += dVx;
				}
				if (left) {
					vx -= dVx;
				}
				if (jump && ground) {
					jumping();
				}
				vy += grav;
				if (willCollide(0, vy)) {
					vy = 0;
					ground = true;
				}
				if (willCollide(vx, 0)) {
					vx = 0;
				}

				for (int i = 0; i < numEnemies; i++) {
					if (enemies[i].isAlive()) {
						if (enemies[i].isIn(sp, vx, 0)) {
							killPlayer();
							break;
						}
						if (enemies[i].isIn(sp, 0, vy)) {
							vy = -2 * bounce / 3;
							enemies[i].kill();
							if (sound) {
								playClip(enemies[i].getType());
							}
						}
					}
				}
				x += vx;
				y += vy;

				sp.iterate(Math.abs(vx));

				boolean tWCH, tWCV, tWF;
				for (int i = 0; i < numEnemies; i++) {
					if (isOnScreen(enemies[i])) {
						if (enemies[i].isAlive()) {
							tWCH = tWCV = tWF = false;
							if (willCollideNPC(enemies[i].vx, -2, enemies[i])) {
								tWCH = true;
							}
							for (int j = 0; j < numEnemies; j++) {
								if ((i != j) && enemies[j].isAlive()) {
									if (enemies[j].isIn(enemies[i], 0, 0)) {
										tWCH = true;
									}
								}
							}
							if (willCollideNPC(0, enemies[i].vy, enemies[i])) {
								tWCV = true;
							}
							if (enemies[i].ground) {
								if (willFall(enemies[i])) {
									tWF = true;
								}
							}
							enemies[i].iterate(.5, tWCH, tWCV, tWF, 0, 0.1);
						} else {
							enemies[i].iterate(.5, false, true, false, 0, 0);
						}
					}
				}
				if (gameState == BOSS) {
					switch (level) {
					case 1:
						skye = new GradientPaint(W / 2, H, Color.BLUE, W / 2, 0, Color.BLACK);
						gnd = new Color(12, 134, 61);
						skyeY = 820;
						break;
					case 2:
						skye = new GradientPaint(W / 2, H, Color.RED, W / 2, 0, Color.BLACK);
						gnd = new Color(12, 134, 61);
						skyeY = 1020;
						break;
					default:
						skye = new GradientPaint(W / 2, H, Color.BLUE, W / 2, 0, Color.BLACK);
						break;
					}
					tWCH = tWCV = tWF = false;
					if (willCollideNPC(enemies[bossIndex].vx, -2, enemies[bossIndex])) {
						tWCH = true;
					}
					for (int j = 0; j < numEnemies; j++) {
						if ((bossIndex != j) && enemies[j].isAlive()) {
							if (enemies[j].isIn(enemies[bossIndex], 0, 0)) {
								tWCH = true;
							}
						}
					}
					if (willCollideNPC(0, enemies[bossIndex].vy, enemies[bossIndex])) {
						tWCV = true;
					}
					if (enemies[bossIndex].ground) {
						if (willFall(enemies[bossIndex])) {
							tWF = true;
						}
					}
					enemies[bossIndex].iterate(.5, tWCH, tWCV, tWF, 0, 0.1);
				}
				char tC = sp.getState();
				if (!ground) {
					if ((tC == 'r') || (tC == 'j') || (tC == 'g')) {
						if (vy < 0) {
							sp.setState('j');
						} else {
							sp.setState('g');
						}
					} else {
						if (vy < 0) {
							sp.setState('J');
						} else {
							sp.setState('G');
						}
					}
				} else if (vx > 0) {
					sp.setState('r');
				} else if (vx < 0) {
					sp.setState('l');
				} else if (tC == 'r' || tC == 's' || tC == 'j') {
					sp.setState('s');
				} else {
					sp.setState('S');
				}
				sp.x = x;
				sp.y = y;

				if (x >= bossXL && x < bossXR && gameState != BOSS && y >= bossYU && y < bossYD) {
					gameState = BOSS;
					if (sound) {
						mPlayer.setTrack(soundPath + "boss.mid", true);
						mPlayer.play();
					}
				}
				repaint();
			} else if (gameState == JUSTDIED) {
				if (tickCounter > 0) {
					tickCounter--;
				} else {
					if (sound) {
						if (level == 1) {
							mPlayer.setTrack(soundPath + "Level1.mid", true);
						}
						if (level == 2) {
							mPlayer.setTrack(soundPath + "Level2.mid", true);
						}
						mPlayer.play();
					}
					gameState = PLAYING;
					x = playerStartX;
					y = playerStartY;
					vx = vy = 0;
					jump = false;
				}
				repaint();
			} else if (gameState == PASSEDBOSS) {
				if (tickCounter > 0) {
					tickCounter--;
				} else {
					if (level <= levelMax) {
						gameState = LOADLEVEL;
						x = playerStartX;
						y = playerStartY;
						vx = vy = 0;
						jump = false;
					} else {
						tickCounter = 300;
						gameState = WON;
						if (sound) {
							mPlayer.setTrack(soundPath + "Level3.mid", true);
							mPlayer.play();
						}
					}
				}
				repaint();
			} else if (gameState == GAMEOVER) {
				if (tickCounter > 0) {
					tickCounter--;
				} else {
					if (sound) {
						mPlayer.setTrack(soundPath + "Level3.mid", true);
						mPlayer.play();
					}
					loadLevel(levelBase + "level1.tmx");
					gameState = INTRO;
					lives = database.Score.getLifecount();			
					first = false;
					//Hier neutrino
					
					//ScrollyV8.mf.dispose();
					
					//ScrollyV8.gPanel.setVisible(false);
					
				}
				repaint();
			} else if (gameState == WON) {
				if (tickCounter > 0) {
					tickCounter--;
				} else {
					loadLevel(levelBase + "level1.tmx");
					gameState = INTRO;
					lives = database.Score.getLifecount();
					level = 1;
				}
				repaint();
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				Debugger.out(e.toString());
			}
		}
	}
	// For now, only keyboard. Could generalize to mouse, etc.

	@SuppressWarnings("deprecation")
	public void handleInput(char code, KeyEvent e) {
		switch (code) {
		case 'p': // Key Pressed
			if (gameState == INTRO) {
				if(first)
				gameState = LOADLEVEL;
				
				// menu.tick(e);
			
			else if(!first){
				JOptionPane.showMessageDialog(null, "Sie müssen zuerst wieder lernen!");
//				ScrollyV8.gPanel.setVisible(false);
//				ScrollyV8.mf.setVisible(false);
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				right = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				left = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (ground) {
					jump = true;
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameState = INTRO;
			}
			// Mute
			if (e.getKeyCode() == KeyEvent.VK_M) {
				if (MidiPlayer.player.isRunning()) {
					MidiPlayer.player.stop();
					sound = false;
				} else
					MidiPlayer.player.start();
				sound = true;
			}
			// Pausenmenü

			if (gameState == PLAYING) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					if (!running) {
						fred.resume();
						running = true;
					} else {
						fred.suspend();
						running = false;
					}
				}

			}

			break;
		case 'r': // Released
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				right = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				left = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (ground) {
					jump = false;
				}
			}
			break;
		case ('t'): // Typed
			if (e.getKeyChar() == 'c') {
				//Debugger.out("Player Coords (" + sp.x + "," + sp.y + ")");
			}
			break;
		}
	}
	// ********************** Level Loader ***************************

	public void loadLevel(String fName) {
		resetTiles();
		int currTileSetWidth = 0;
		int currTileSetHeight = 0;
		int currGID = 0;
		int currLayerIndex = -3;

		int tileSetCounter = 0;
		int layerCounter = 0;

		XMLEvent event;
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(fName);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = startElement.getAttributes();
					if (startElement.getName().getLocalPart() == "map") {
						Debugger.out("Map File Detected...");
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							switch (attribute.getName().toString()) {
							case ("version"):
								break;
							case ("orientation"):
								break;
							case ("width"):
								mapW = Integer.parseInt(attribute.getValue());
								break;
							case ("height"):
								mapH = Integer.parseInt(attribute.getValue());
								break;
							case ("tilewidth"):
								mapTileW = Integer.parseInt(attribute.getValue());
								break;
							case ("tileheight"):
								mapTileH = Integer.parseInt(attribute.getValue());
								break;
							default:
								break;
							}
						}
						Debugger.out("Found " + mapW + "x" + mapH + " tile map with " + mapTileW + "x" + mapTileH
								+ " pixel tiles.");
					}
					if (startElement.getName().getLocalPart() == "tileset") {
						Debugger.out("Parsing Tileset " + tileSetCounter);
						String tName = "";
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							switch (attribute.getName().toString()) {
							case ("firstgid"):
								currGID = Integer.parseInt(attribute.getValue());
								break;
							case ("name"):
								tName = attribute.getValue();
								Debugger.out("\tTileset Name = " + tName);
								break;
							case ("tilewidth"):
								currTileSetWidth = Integer.parseInt(attribute.getValue());
								break;
							case ("tileheight"):
								currTileSetHeight = Integer.parseInt(attribute.getValue());
								break;
							default:
								break;
							}
						}
						if (tName.equals("Sprites")) {
							spriteGID = currGID;
						}
					}
					if (startElement.getName().getLocalPart() == "image") {
						Debugger.out("Parsing image for tileset " + tileSetCounter);
						String tileName = "";
						int imageWidth = 480;
						@SuppressWarnings("unused")
						int imageHeight = 30;
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next(); // Bug -
																		// currently
																		// ignores
																		// first
																		// element.
							switch (attribute.getName().toString()) {
							case ("source"):
								tileName = levelBase + attribute.getValue();
								break;
							case ("width"):
								imageWidth = Integer.parseInt(attribute.getValue());
								break;
							case ("height"):
								imageHeight = Integer.parseInt(attribute.getValue());
								break;
							default:
								break;
							}
						}
						try {
							BufferedImage tIm = ImageIO.read(new File(tileName));
							tm.add(tIm, currGID - 1, imageWidth / currTileSetWidth, currTileSetWidth,
									currTileSetHeight);
							Debugger.out("\tAdded tileset " + tileName + ".\n\t" + imageWidth / currTileSetWidth
									+ " tiles of width " + currTileSetWidth);
						} catch (IOException e) {
							e.printStackTrace();
						}
						tileSetCounter++;
					}
					if (startElement.getName().getLocalPart() == "layer") {
						Debugger.out("Parsing Layer " + layerCounter);
						String tName = "";
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next(); // Bug -
																		// currently
																		// ignores
																		// first
																		// element.
							switch (attribute.getName().toString()) {
							case ("name"):
								tName = attribute.getValue();
								switch (tName) {
								case "tiles":
									currLayerIndex = 0;
									break;
								case "bg1":
									currLayerIndex = 1;
									break;
								case "bg2":
									currLayerIndex = 2;
									break;
								case "bg3":
									currLayerIndex = 3;
									break;
								case "fg1":
									currLayerIndex = 4;
									break;
								case "spikes":
									currLayerIndex = 5;
									break;
								case "spriteLayer":
									currLayerIndex = -1;
									break;
								default:
									currLayerIndex = -2;
								}
								Debugger.out("\tName of Layer = " + tName + " with index " + currLayerIndex);
								break;
							case ("width"):
								Debugger.out("\tWidth of Layer = " + attribute.getValue() + " units");
								break;
							case ("height"):
								Debugger.out("\tHeight of Layer = " + attribute.getValue() + " units");
								break;
							default:
								break;
							}
						}
					}
					if (startElement.getName().getLocalPart() == "data") {
						Debugger.out("Parsing Data for Layer " + layerCounter);
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next(); // Bug -
																		// currently
																		// ignores
																		// first
																		// element.
							switch (attribute.getName().toString()) {
							case ("encoding"):
								Debugger.out("\tEncoding = " + attribute.getValue());
								break;
							default:
								break;
							}
						}
						event = eventReader.nextEvent();
						String stEvent = "";
						while (!event.isEndElement()) {
							stEvent = stEvent + event.toString().replace("\n", "").replace("\r", ""); // Tiled
																										// places
																										// extra
																										// newlines
																										// in
																										// csv
																										// portion
							event = eventReader.nextEvent();
						}
						if (currLayerIndex == -1) {
							loadSprites(stEvent, mapW, mapH, mapTileW, mapTileH);
						} else {
							Debugger.out("Loading Layer with index " + currLayerIndex);
							loadLayer(currLayerIndex, stEvent, mapW, mapH, mapTileW, mapTileH);
						}
						layerCounter++;
					}
				}
			}
			tiles.sort();
			bg3.sort();
			bg2.sort();
			bg1.sort();
			fg1.sort();
			spikes.sort();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		// End of XML parsing
	}

	public boolean loadLayer(int index, String data, int nX, int nY, int tW, int tH) {
		String[] tokens = data.split(",");
		if (tokens.length != nX * nY) {
			Logger.log("Error decoding file");
			return false;
		} else {
			int cnt = 0;
			int tmp = 0;
			for (int j = 0; j < nY; j++) {
				for (int i = 0; i < nX; i++) {
					tmp = Integer.parseInt(tokens[cnt]);
					if (tmp != 0) {
						// Debugger.out("Added Tile "+(tmp-1)+" at (x,y) =
						// ("+i+","+j+")");
						TilePoint ttp = tm.getTile(tmp - 1);

						Image tIm = createImage(ttp.getImage().getSource());
						Tile tTile = new Tile(i * mapTileW, j * mapTileH, ttp.width, ttp.height, tmp - 1, tIm);
						switch (index) {
						case 0:
							tiles.add(tTile);
							break;
						case 1:
							bg1.add(tTile);
							break;
						case 2:
							bg2.add(tTile);
							break;
						case 3:
							bg3.add(tTile);
							break;
						case 4:
							fg1.add(tTile);
							break;
						case 5:
							spikes.add(tTile);
							break;
						default:
							// tiles.add(tTile);
							break;
						}
					}
					cnt++;
				}
			}
			return true;
		}
	}

	public boolean loadSprites(String data, int nX, int nY, int tW, int tH) {
		// start sprite GID
		Debugger.out("Sprite GID = " + spriteGID);
		String[] tokens = data.split(",");
		if (tokens.length != nX * nY) {
			Logger.log("Error decoding file");
			return false;
		} else {
			int cnt = 0;
			int cntE = 0;
			numEnemies = 0;
			int tmp = 0;
			for (int k = 0; k < tokens.length; k++) {
				if (Integer.parseInt(tokens[cnt]) - spriteGID > 0) {
					numEnemies++;
				}
				cnt++;
			}
			cnt = 0;

			enemies = new Enemy[numEnemies];
			for (int j = 0; j < nY; j++) {
				for (int i = 0; i < nX; i++) {
					tmp = Integer.parseInt(tokens[cnt]) - spriteGID;

					switch (tmp) {
					case 0:
						x = playerStartX = i * mapTileW;
						y = playerStartY = j * mapTileH;
						break;
					case 1:
						enemies[cntE] = new Peon(i * mapTileW, j * mapTileH, mapTileW, mapTileH, spritePath);
						cntE++;
						break;
					case 2:
						enemies[cntE] = new Robob(i * mapTileW, j * mapTileH, 40, mapTileH, spritePath);
						cntE++;
						break;
					case 3:
						enemies[cntE] = new Gator(i * mapTileW, j * mapTileH, 30, mapTileH, spritePath);
						cntE++;
						break;
					case 4:
						bossIndex = cntE;
						enemies[cntE] = new Boss(i * mapTileW, j * mapTileH, 4 * mapTileW, 2 * mapTileH,
								spritePath + "Boss/");
						cntE++;
						break;
					default:
						break;
					}
					cnt++;
				}
			}
			return true;
		}
	}

	// ******************* HELPER FUNCTIONS **********************
	public boolean willCollide(double dx, double dy) {
		// find indices of al tiles in N-tile radius of Player. Note tiles are
		// sorted by x, then y
		// First find tiles coordinates of player and start at min(x-N,0)
		int N = 3;
		// indices: (u,v) - coords, (x,y)
		int xPlayerLeft = Math.max(((int) (x / brickWidth)) - N, 0);
		int xPlayerRight = Math.min(((int) (x / brickWidth)) + N, (int) (tiles.get(tiles.size() - 1).x) / brickWidth);
		// strat/stop index of grid
		int uL = 0;
		while ((int) (tiles.get(uL).x / brickWidth) < xPlayerLeft) {
			uL++;
		}
		int uR = uL; // Start with right coord at left coord and increase index
						// until xRight>xPlayer+N
		while (tiles.get(uR).x / brickWidth < xPlayerRight) {
			uR++;
		}
		uR = Math.min(uR, tiles.size() - 1); // if region extends beyond last
												// point, truncate it.
		// Could search through y coords here, but since levels are short,
		// height-wise, probably no more efficient.s
		for (int i = uL; i < uR; i++) {
			if (tiles.get(i).isIn(sp, dx, dy)) {
				return true;
			}
		}
		// Now for the spikes
		xPlayerRight = Math.min(((int) (x / brickWidth)) + N, (int) (spikes.get(spikes.size() - 1).x) / brickWidth);
		uL = 0;
		while ((spikes.get(uL).x / brickWidth < xPlayerLeft) && (uL < spikes.size() - 1))
		// while(spikes.get(xL).x/brickWidth<xi)
		{
			uL++;
		}
		uR = uL;
		while (spikes.get(uR).x / brickWidth < xPlayerRight) {
			uR++;
		}

		for (int i = uL; i < uR + 1; i++) {
			if (spikes.get(i).isIn(sp, dx, dy)) {
				killPlayer();
				return true;
			}
		}
		return false;
	}

	public boolean willCollideNPC(double dx, double dy, Enemy e) {
		double scX = 0;
		double eX = e.x;

		if (eX < CX) {
			scX = CX;
		} else if (eX > levelMaxX - CX) {
			scX = levelMaxX - CX;
		} else {
			scX = eX;
		}

		int bSX = Math.min((int) ((scX - CX) / brickWidth), 0);
		int bFX = Math.min((int) ((scX + CX) / brickWidth) + 1, (int) (tiles.get(tiles.size() - 1).x) / brickWidth);
		int sX = 0;
		int fX = 0;
		while (tiles.get(sX).x / brickWidth < bSX) {
			sX++;
		}
		while (tiles.get(fX).x / brickWidth < bFX) {
			fX++;
		}

		for (int i = sX; i < fX; i++) {
			if (tiles.get(i).isIn(e, dx, dy)) {
				return true;
			}
		}
		return false;
	}

	public void killPlayer() {
		if (sound) {
			MidiPlayer.stop();
			ClipPlayer.DIE.play();
			database.Score.death();
			Debugger.out("DIE!");
		}
		lives--;
		x = playerStartX;
		y = playerStartY;
		if (lives >= 0) {
			gameState = JUSTDIED;
			// if(sound)
			// {
			// levelClip.stop();
			// clipBoss.stop();
			// clipDie.play();
			// }
			tickCounter = 250;
		} else {
			gameState = GAMEOVER;
			level = 1;
			tickCounter = 400;
			if (sound) {
				mPlayer.setTrack(soundPath + "gameOver.mid", false);
				mPlayer.play();
			}
		}
	}

	@SuppressWarnings("unused")
	private void winPlayer() {
		x = y = 25;
		vx = vy = 0;
		jump = false;
		lives++;
	}

	private boolean willFall(Enemy e) {
		double d = e.w;
		if (e.vx < 0) {
			d *= -1;
		}
		if (!willCollideNPC(e.vx + d, 9, e)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isOnScreen(Squob s) {
		if ((s.x > x - 2 * CX) && (s.x < x + 2 * CX)) {
			return true;
		} else {
			return false;
		}
	}

	private void drawLayer(Graphics g, TileLayer tl, int depth) {
		if (tl.size() > 0) {
			if (x < CX) {
				screenX = CX;
			} else if (x > levelMaxX - CX) {
				screenX = (levelMaxX - CX * (2 - depth)) / depth; // Coordinate
																	// Transformed
																	// for
																	// depth
			} else {
				screenX = (x - CX * (1 - depth)) / depth;
			}

			if (y > levelMaxY - CY) {
				screenY = Math.max(levelMaxY - CY, 0);
			} else {
				screenY = y;
			}
			int bSX = Math.min((int) ((screenX - CX) / brickWidth), 0);
			int bFX = Math.min((int) ((screenX + CX) / brickWidth) + 1, (int) (tl.get(tl.size() - 1).x) / brickWidth);
			int sX = 0;
			int fX = 0;
			while (tl.get(sX).x / brickWidth < bSX) {
				sX++;
			}
			while (tl.get(fX).x / brickWidth < bFX) {
				fX++;
			}
			// for (int i = sX; i <= fX; i++)
			for (int i = sX; i <= fX; i++) {
				tl.get(i).draw(g, screenX - CX, screenY - CY);
			}
		}
	}

	private void drawLayer(Graphics g, TileLayer tl, int depth, double x0, double y0) {
		if (tl.size() > 0) {
			screenX = (x0 - CX * (1 - depth)) / depth;
			screenY = y0;
			int bSX = Math.min((int) ((screenX - CX) / brickWidth), 0);
			int bFX = Math.min((int) ((screenX + CX) / brickWidth) + 1, (int) (tl.get(tl.size() - 1).x) / brickWidth);
			int sX = 0;
			int fX = 0;
			while (tl.get(sX).x / brickWidth < bSX) {
				sX++;
			}
			while (tl.get(fX).x / brickWidth < bFX) {
				fX++;
			}
			// for (int i = sX; i <= fX; i++)
			for (int i = sX; i <= fX; i++) {
				tl.get(i).draw(g, screenX - CX, screenY - CY);
			}
		}
	}

	private void resetTiles() {
		tiles = new TileLayer(10000);
		fg1 = new TileLayer(200);
		bg1 = new TileLayer(200);
		bg2 = new TileLayer(200);
		bg3 = new TileLayer(200);
		spikes = new TileLayer(200);
		tm = new TileManager(250);
	}

	private void playClip(char c) {
		switch (c) {
		case 'a':
			ClipPlayer.DEATH_GATOR.play();
			break;
		case 'b':
			ClipPlayer.DEATH_BOSS.play();
			break;
		case 'g':
			ClipPlayer.DEATH_GREAPER.play();
			break;
		case 'r':
			ClipPlayer.DEATH_ROBOB.play();
			break;
		case 'B':
			ClipPlayer.BOSS_HIT.play();
			break;
		}
	}
}