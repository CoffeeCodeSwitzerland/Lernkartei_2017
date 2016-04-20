package Quizlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Diese Klasse dient lediglich der Demonstration (Kommandozeile) und
 * Überprüfung der Quizlet-Funktionalität
 * 
 * @author ma
 *
 */
public class QuizletTest
{
	// Neues Quizlet Objekt mit Token
	static final Quizlet	q					= new Quizlet("3RhaPk5H9C");

	// Scanner für Input
	static Scanner			scan				= new Scanner(System.in);

	// Strings für Steuerung
	static String			goToMenu			= "menu";
	static String			more				= "more";
	static String			back				= "back";

	static String			menuErrorMessage	= "%s is not a valid input!\n";
	static String			menuMessage			= "Menu";
	static String			quitMessage			= "Thx & bye";

	static int				setCounter			= 1;

	public static void main (String[] args) throws MalformedURLException, IOException, InterruptedException
	{
		// Menu Loop
		boolean exit = false;
		do
		{
			System.out.println(menuMessage);

			// Userinput einlesen
			String userCommand = scan.nextLine();

			// Userinput ausführen
			switch (userCommand)
			{
				// Programm verlassen
				case "0":
					System.out.println(quitMessage);
					exit = true;
					break;

				// Suchen
				case "1":
					Search();
					break;

				// Menu Loop wiederholen
				default:
					System.out.printf(menuErrorMessage, userCommand);
					break;
			}

		} while (!exit);
	}

	/**
	 * Führt eine Suche durch
	 * 
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void Search () throws InterruptedException, MalformedURLException, IOException
	{
		System.out.println("Suchbegriff eingeben:");

		// Liest Begriff ein ...
		String search = scan.nextLine();
		System.out.print("Einen Moment warten");

		// Lade Effekt
		for (int i = 0; i < 3; i++)
		{
			Thread.sleep(400);
			System.out.print(".");
		}
		System.out.println("\n");

		// ... und sucht danach
		ArrayList<String> result = q.SearchSet(search);

		for (String set : result)
		{
			String[] setEntry = set.split(q.separator);

			// Gibt Infos zur Suche aus
			if (result.indexOf(set) == 0)
			{
				System.out.println(setEntry[0] + " Sets gefunden\n"
						+ setEntry[2] + " davon enthalten Bilder\n"
						+ "Seite " + setEntry[3] + " von " + setEntry[1] + "\n");
				System.out.println("Enter drücken um Sets anzuzeigen,\n'" + more
						+ "' eingeben um Karten anzuzeigen,\n'" + back + "' um zum Menu zurück zu kehren");
				scan.nextLine();
				System.out.println("");
			}
			else // Gibt Sets aus
			{

				String hasImages = setEntry[4].equals("true") ? "Ja" : "Nein";

				System.out.println("'" + setEntry[1] + "' von " + setEntry[2]
						+ "\n\n   Karten\t\t" + setEntry[3]
						+ "\n   Bilder\t\t" + hasImages
						+ "\n   Sprache\t\t" + setEntry[6] + " - " + setEntry[7]
						+ "\n   Beschreibung\t\t" + setEntry[5] + "\n");

				// So können (je nach Wert von setCounter) mehrere Sets
				// nacheinander angezeigt werden, ohne dass der User jedes Mal
				// mit Enter bestätigen muss
				if (result.indexOf(set) % setCounter == 0)
				{
					String x = scan.nextLine();

					// Will der User die Karten des Sets ansehen ('more')?
					if (x.equals(more))
					{
						try
						{
							ArrayList<String> terms = q.GetSet(setEntry[0]);

							System.out.println("\n'" + back + "' um zu den Suchresultaten zurückzukehren\n");

							// Gibt ganzes Set aus
							for (String term : terms)
							{
								String[] tArray = term.split(q.separator);
								System.out.println(tArray[1] + "\n" + tArray[2]);
								if (scan.nextLine().equals(back))
								{
									System.out.println("");
									break;
								}
							}
						}
						catch (Exception e)
						{
							System.out.println("Das Set kann leider nicht ausgegeben werden");
							return;
						}
					}
					else if (x.equals(back)) { return; }
				}
				else
				{
					Thread.sleep(400);
				}
			}
		}
	}
}
