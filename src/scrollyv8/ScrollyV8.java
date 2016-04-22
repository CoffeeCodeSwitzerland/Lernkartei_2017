package scrollyv8;

/**
 *
 * @author Andrew MacRae <macrae@berkeley.edu>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.File;

public class ScrollyV8 extends JFrame implements KeyListener
{    
    gamePanel gPanel = new gamePanel();

    public void init()
    {        
        gPanel.setFocusable(true);
        gPanel.addKeyListener(this);

    }

    public ScrollyV8()
    {
//Settings for frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        ScrollyV8 mf = new ScrollyV8();
        mf.setVisible(true);
        mf.init();
    }
}