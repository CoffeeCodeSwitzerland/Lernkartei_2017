package Quizlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuizletTest
{
	// Neues Quizlet Objekt mit Token
	static final Quizlet	q			= new Quizlet("3RhaPk5H9C");

	// Scanner für Input
	static Scanner			scan		= new Scanner(System.in);

	// Strings für Steuerung
	static String			goToMenu	= "menu";
	static String			more		= "more";

	public static void main (String[] args) throws MalformedURLException, IOException, InterruptedException
	{
		System.out.println("Suchbegriff eingeben:");

		// Liest Begriff ein ...
		String search = scan.nextLine();
		System.out.print("Einen Moment warten");

		// Lade Effekt
		for (int i = 0; i < 3; i++)
		{
			Thread.sleep(500);
			System.out.print(".");
		}
		System.out.println("\n");

		// ... und sucht danach
		ArrayList<String> result = q.SearchSet(search);

		for (String set : result)
		{
			String[] sArray = set.split(q.separator);

			// Gibt Infos zur Suche aus
			if (result.indexOf(set) == 0)
			{
				System.out.println(sArray[0] + " Sets gefunden\n"
						+ sArray[2] + " davon enthalten Bilder\n"
						+ "Seite " + sArray[3] + " von " + sArray[1] + "\n");
				System.out.println("Enter drücken um Sets anzuzeigen,\n'" + more + "' eingeben um Karten anzuzeigen,\n'quit' um Prgoramm zu verlassen");
				scan.nextLine();
				System.out.println(sArray[0]);
			}
			else
			{
				// Gibt Sets aus
				String hasImages = sArray[4].equals("true") ? "Ja" : "Nein";

				System.out.println("'" + sArray[1] + "' von " + sArray[2]
						+ "\n\n   Karten\t\t" + sArray[3]
						+ "\n   Bilder\t\t" + hasImages
						+ "\n   Sprache\t\t" + sArray[6] + " - " + sArray[7]
						+ "\n   Beschreibung\t\t" + sArray[5] + "\n");

				String x = scan.nextLine();
				Thread.sleep(1000);

				// Will der User die Karten des Sets ansehen ('more')?
				if (x.equals(more))
				{
					try
					{
						ArrayList<String> terms = q.GetSet(sArray[0]);

						System.out.println("\n'quit' um zu den Suchresultaten zurückzukehren\n");

						// Gibt ganzes Set aus
						for (String term : terms)
						{
							String[] tArray = term.split(q.separator);
							System.out.println(tArray[1] + "\n" + tArray[2]);
							if (scan.nextLine().equals("quit"))
							{
								System.out.println("");
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.out.println("Error, das Set kann nicht ausgegeben werden (Main.java, Line 84)");
						return;
					}
				}
				else if (x.equals("quit")) { return; }
			}
		}
	}
}
