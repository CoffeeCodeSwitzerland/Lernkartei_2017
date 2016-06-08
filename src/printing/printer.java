package printing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class printer extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void printing(String sFensterTitel) {
		printer wnd = new printer();
		wnd.setSize(500, 150);
		wnd.setVisible(true);
		Button bttn = new Button("Button betätigen, um Ausdruck einer Testseite zu starten ...");
		add(BorderLayout.CENTER, bttn);
		bttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				druckeTestseite();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
				System.exit(0);
			}
		});
	}

	void druckeTestseite() {
		PrintJob prjob = getToolkit().getPrintJob(this, "Testseite", null);
		if (null != prjob) {
			final int iPageWidth = 842;
			final int iPageHeight = 595;

			Graphics pg = prjob.getGraphics();

			if (null != pg && 0 < 1) {
				int iPageHeightQuarter = iPageHeight / 4;
				int iPageWidthQuarter = iPageWidth / 4;

				int iPageHeightEight = iPageHeight / 8;
				int iPageWidthEight = iPageWidth / 16;

				// Senkrechte Linien
				for (int i = 0; i < 3; i++) {
					pg.drawLine(iPageWidthQuarter, 0, iPageWidthQuarter, iPageHeight);
					iPageWidthQuarter += iPageWidth / 4;
				}
				// Waagrechte Linien
				for (int a = 0; a < 3; a++) {
					pg.drawLine(0, iPageHeightQuarter, iPageWidth, iPageHeightQuarter);
					iPageHeightQuarter += iPageHeight / 4;
				}
				// Text Einfiügen
				for (int b = 0; b < 4; b++) 
				{

					iPageWidthEight = iPageWidth / 10;
					for(int c = 0; c < 2; c++)
					{
						pg.drawString("Karte 1", iPageWidthEight, iPageHeightEight);
						
						iPageWidthEight += iPageWidth / 4;
						
						pg.drawString("Karte 2", iPageWidthEight, iPageHeightEight);
						
						iPageWidthEight += iPageWidth / 4;
					}

					iPageHeightEight += iPageHeight / 4;
				}
				pg.dispose();
			}
			prjob.end();
		}
	}

}