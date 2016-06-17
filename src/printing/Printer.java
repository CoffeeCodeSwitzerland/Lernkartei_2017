//Author: Yanis Weibel

package printing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Printer extends JFrame {

	public static final long serialVersionUID = 1L;

	public void printer(String Stack) {
		setSize(500, 150);
		setVisible(true);
		Button bttn = new Button("Kartenstapel ausdrucken.");
		add(BorderLayout.CENTER, bttn);
		bttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				druckeKartenset(Stack);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
			}
		});
	}

	public void druckeKartenset(String Stack) {
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
				for (int b = 0; b < 4; b++) {

					iPageWidthEight = iPageWidth / 10;
					for (int c = 0; c < 2; c++) {
						int kartenID = c + 1;
						String[] VorderUndRückseite = database.CardEntity.getFrontAndBackside(Stack, kartenID);

						pg.drawString(VorderUndRückseite[0], iPageWidthEight, iPageHeightEight);

						iPageWidthEight += iPageWidth / 4;

						pg.drawString(VorderUndRückseite[1], iPageWidthEight, iPageHeightEight);

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