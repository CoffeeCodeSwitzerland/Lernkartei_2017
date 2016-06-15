package globals;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import debug.Debugger;
import debug.Logger;
import views.components.Alert;


/**
 * 
 * @author miro albrecht, Dr.Med. David Schor
 *
 */
public abstract class Functions
{
	/**
	 * Ersetzt beliebig definierbare BB-Codes 1:1 mit dem entsprechenden
	 * HTML-Tag (Ersetzt nur eckige Klammern mit spitzen).
	 * 
	 * @param input
	 *            Der String mit den BBCodes
	 * @param tags
	 *            Alle zu ersetztenden Tags (ohne Klammern, zB 'sup')
	 * @return Gibt den Inputstring mit HTML Tags zurück
	 */
	public static String simpleBbCode2HTML (String input, String... tags)
	{
		for (String s : tags)
		{
			if (input.contains(startBB(s)) && input.contains(endBB(s)))
			{
				input = input.replace(startBB(s), startHTML(s));
				input = input.replace(endBB(s), endHTML(s));
			}
		}

		return input;
	}

	/**
	 * Entfernt alle spitzen Klammern, um HTML Tags unschädlich zu machen
	 * 
	 * @param input
	 *            String mit HTML Tags
	 * @return Gibt gesäuberten String zurück
	 */
	public static String removeHTMLTags (String input)
	{
		if (input.contains("<") || input.contains(">"))
		{
			input = input.replace("<", "");
			input = input.replace(">", "");
		}

		return input;
	}

	/**
	 * Ersetzt Color-BB-Codes mit den entsprechenden HTML Tags
	 * 
	 * @param input
	 *            String mit Color-BB-Codes
	 * @return String mit HTML-Color-Tag
	 */
	public static String colorBbCode2HTML (String input, String...tags)
	{
		//public static String[] = {"<span style=\"color:", "<img src=\""};
		
		for (String s : Globals.complexTags)
		{
			System.out.println(endHTML(s));
			String fist = "[" + s + "=(";
			if (input.contains(fist))
			{
				System.out.println(input);
				String findStr = fist;
				int lastIndex = 0;
				int count = 0;
	
				while (lastIndex != -1)
				{
	
					lastIndex = input.indexOf(findStr, lastIndex);
	
					if (lastIndex != -1)
					{
						count++;
						lastIndex += findStr.length();
					}
				}
			
			int up = 0;
			while (count > up)
			{
				String result = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
				//result = Convert.ToFile(result);
				input = input.replace(fist + result + ")]", "<img src=\"" + result + "\">");
				input = input.replace(endBB(s), endHTML(s));
				up++;
				System.out.println(input);
			}
		}
		}

		return input;
		
	}

	/**
	 * Ersetzt beliebig definierbare BB-Codes mit beliebigen HTML-Tags.
	 * 
	 * @param input
	 *            String mit den BBCodes
	 * @param tags
	 *            Alle Tags ohne Klammern. Auf jeden BBCode folgt der jeweilige
	 *            HTML Tag: b, strong, i, em, etc...
	 * @return String mit HTML Codes
	 */
	public static String realBbCode2HTML (String input, String... tags)
	{
		if (tags.length % 2 != 0)
		{
			Debugger.out("real BBCode 2 HTML : The cout of tag arguments has to be even");
			return null;
		}

		for (int i = 0; i < tags.length; i += 2)
		{
			if (input.contains(startBB(tags[i])) && input.contains(endBB(tags[i])))
			{
				input = input.replace(startBB(tags[i]), startHTML(tags[i + 1]));
				input = input.replace(endBB(tags[i]), endHTML(tags[i + 1]));
			}
		}

		return input;
	}

	private static String startBB (String input)
	{
		return "[" + input + "]";
	}

	private static String endBB (String input)
	{
		return startBB("/" + input);
	}

	private static String startHTML (String input)
	{
		return "<" + input + ">";
	}

	private static String endHTML (String input)
	{
		return startHTML("/" + input);
	}

	/**
	 * Trial version of the "like" to compare two strings
	 * 
	 * @param toBeCompare
	 * @param by-String
	 * @return true, if toBeCompare is like by-String
	 */
	public static boolean like (String toBeCompare, String by)
	{
		if (by != null)
		{
			if (toBeCompare != null)
			{
				if (by.startsWith("%") && by.endsWith("%"))
				{
					int index = toBeCompare.toLowerCase().indexOf(by.replace("%", "").toLowerCase());
					if (index < 0)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
				else if (by.startsWith("%"))
				{
					return toBeCompare.endsWith(by.replace("%", ""));
				}
				else if (by.endsWith("%"))
				{
					return toBeCompare.startsWith(by.replace("%", ""));
				}
				else
				{
					return toBeCompare.equals(by.replace("%", ""));
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Versucht den URL im Standartbrowser zu öffnen
	 * 
	 * @param urlString
	 *            URL (sollte gültig sein)
	 */
	public static void openWebpage (String urlString)
	{
		try
		{
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		}
		catch (Exception e)
		{
			// TODO: hier Meldung an User er sollte ein Standardbrowser haben
			// TODO Testen, ob lösung funktioniert...
			Alert.simpleInfoBox("Kein Standartbrowser gefunden", "Der Standartbrowser konnte nicht geöffnet werden. "
					+ "Möglicherweise müssen Sie einen Browser als Standartprogram für Webseiten festelegen.");
			if (urlString == null) urlString = "{null}";
			Logger.log("Functions.openWebpage(" + urlString + "): please set a browser as standard!");
			Debugger.out("Functions.openWebpage(" + urlString + "): " + e.getMessage());
			e.printStackTrace();
		}
	}

	// TODO javadoc kommentar: zweck der funktion
	public static String fileToString (File textFile) throws FileNotFoundException
	{

		// Checks if file exists
		if (!textFile.exists()) { throw new FileNotFoundException("File does not exist: " + textFile); }

		StringBuilder contents = new StringBuilder();

		try
		{
			BufferedReader input = new BufferedReader(new FileReader(textFile));
			try
			{
				String line = null;
				while ((line = input.readLine()) != null)
				{
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			}
			finally
			{
				input.close();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return contents.toString();
	}
	
	public static String FullBb2HTML (String text)
	{
		String imgs = text;
		imgs = Functions.removeHTMLTags(imgs);
		imgs = Functions.colorBbCode2HTML(imgs);
		imgs = Functions.simpleBbCode2HTML(imgs, Globals.evenTags);
		imgs = Functions.realBbCode2HTML(imgs, Globals.pairedTags);
		
		return imgs;
		
	
	}
}