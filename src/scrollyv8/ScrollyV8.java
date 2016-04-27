package scrollyv8;

/**
 *
 * @author Andrew MacRae <macrae@berkeley.edu>
 */

import javax.swing.*;

import debug.Debugger;

import java.awt.*;
import java.awt.event.*;
//import java.io.File;

public class ScrollyV8 extends JFrame implements KeyListener, WindowListener
{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	gamePanel gPanel = new gamePanel();

    public void dispose()
    {        
    	Debugger.out("Killing Player...");
        gPanel.killPlayer();
    	super.dispose();
    }

    public void init()
    {        
        gPanel.setFocusable(true);
        gPanel.addKeyListener(this);

    }

    public ScrollyV8()
    {
//Settings for frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900-30, 600-15);
        setResizable(false);
        setTitle("Funky Application Monkey's Jump 'n' Run!");
        Container content = getContentPane();

        content.add(gPanel, BorderLayout.CENTER);
    }
// Action Event Handlers

    public void keyPressed(KeyEvent k)
    {
        gPanel.handleInput('p',k);
    }

    public void keyReleased(KeyEvent k)
    {
        gPanel.handleInput('r',k);
    }

    public void keyTyped(KeyEvent k)
    {
        gPanel.handleInput('t',k);
        repaint();       
        gPanel.update();
    }
    
    public static void main(String[] args)
    {
		Debugger.out("START Game via main...");
        ScrollyV8 mf = new ScrollyV8();
        mf.setVisible(true);
        mf.init();
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
}