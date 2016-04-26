package kartenpackage;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class NewCard extends JFrame implements MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	public static Database db;
	public static JTextField antwort, frage;
	public static String s;
	public Graphics g;
	public String q;
	public JButton changebtn, savebtn;
	//private ArrayList<String> card = new ArrayList<String>();
	
	public NewCard(){
		
		JFrame myframe = new JFrame();
		
		antwort = new JTextField();
		frage = new JTextField();
		changebtn = new JButton("BOLD");
		savebtn = new JButton("SAVE");
		
		myframe.add(antwort);
		myframe.add(frage);
		myframe.add(changebtn);
		myframe.add(savebtn);
		
		//antwort.addMouseListener(this);
		changebtn.addActionListener(this);
		savebtn.addActionListener(this);
		
		//tfq.setContentType("text/html");
		//tfa.setContentType("text/html");
		
		myframe.setLayout(new GridLayout(4, 0));
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
		myframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
	}
	
	 public void mouseReleased(MouseEvent e) {
	    //if (tfq.getSelectedText() != null) { // See if they selected something 
	      //  s = tfq.getSelectedText();
	        //tfq.setContentType("text/html");
	        
	       //s = "<html><body><b>" + s + "</b></body></html>";
	        
	       //tfq.setText(s);
	        
	              
	        //System.out.println(s);
	        
	        //Font myFont = new Font("Segoe UI", Font.ITALIC | Font.BOLD, 12);
			
		
	        
	   // }
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String[] values = new String[3];
		values[0] = antwort.getText();
		values[1] = frage.getText();
		values[2] = "English Unit 4";
		
		Database.pushToStock(values);
	  
		}
		
	}

	

