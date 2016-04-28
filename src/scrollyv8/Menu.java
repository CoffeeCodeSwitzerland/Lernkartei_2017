package scrollyv8;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu {
	
	private final Button[] options;
	private int currentSelection;
	
	public Menu(){
		options = new Button[3];
		options[0] = new Button(" Play ", 200 + 0 * 80,
				new Font("Arial" , Font.PLAIN, 32), new Font("Arial" , Font.BOLD, 48),
				Color.WHITE, Color.YELLOW);
		options[1] = new Button(" Options ", 200 + 1 * 80,
				new Font("Arial" , Font.PLAIN, 32), new Font("Arial" , Font.BOLD, 48),
				Color.WHITE, Color.YELLOW);
		options[2] = new Button(" Exit ", 200 + 2 * 80,
				new Font("Arial" , Font.PLAIN, 32), new Font("Arial" , Font.BOLD, 48),
				Color.WHITE, Color.YELLOW);
	}
	
	public void tick(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ){
			currentSelection --;
			if(currentSelection < 0){currentSelection = options.length - 1;}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ){
			currentSelection ++;
			if(currentSelection >= options.length){currentSelection = 0;}
		}
		/*boolean clicked = false;
		for (int i = 0; i < options.length; i++) {
			if(options[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1 , 1))){
				currentSelection = i;
				clicked = MouseInput.wasPressed(MouseEvent.BUTTON1);
			}*/
		
		if(/*clicked ||*/e.getKeyCode() == KeyEvent.VK_ENTER){ select();}
	}
	
	private void select(){
		switch(currentSelection){
		case 0:
			System.out.println("play");
			gamePanel.gameState = gamePanel.LOADLEVEL;
			break;
		case 1:
			System.out.println("Options");
			break;
		case 2:
			System.out.println("Exit");

			break;
		}
	}
	
	
	public void render(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ScrollyV8.width, ScrollyV8.height);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 72), Color.ORANGE, ScrollyV8.title, 80);

		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection){
				options[i].setSelected(true);
			}else options[i].setSelected(false);
			
			options[i].render(g);
		}
	}

}
