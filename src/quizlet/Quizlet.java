package quizlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import debug.Supervisor;


/**
 * Für die Kommunikation mit der Quizlet API
 * 
 * @author ma
 *
 */
public class Quizlet
{
	String						token;
	String						searchSetUrl	= "https://api.quizlet.com/2.0/search/sets";
	String						setUrl			= "https://api.quizlet.com/2.0/sets/";
	String						tokenUrl		= "";

	/**
	 * Separiert Informationen in Strings
	 */
	public String				separator		= ":::";

	// Dient als Zwischenspeicher für ArrayLists
	private ArrayList<String>	tempList;

	/**
	 * Für die Kommunikation mit der Quizlet API
	 * 
	 * @param token
	 *            Quizlet Developer Key, zwingend nötig
	 */
	public Quizlet (String token)
	{
		if (token != null && !token.equals(""))
		{
			this.token = token;
			tokenUrl = "?client_id=" + this.token;
		}
	}

	/**
	 * Führt eine Suche in den öffentlichen Sets von Quizlet durch
	 * 
	 * @param query
	 *            Such-String
	 * @return ArrayList mit den Ergebnissen<br>
	 *         Element 0: total_results, total_pages, image_set_count, page <br>
	 *         Alle Anderen: id, title, created_by, term_count, has_images,
	 *         description, lang_terms, lang_definitions
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public ArrayList<String> searchSet (String query, String page)
	{
		if (query == null)
			return null;

		// Codiert den Query String zu einem gültigen URI
		try
		{
			query = URLEncoder.encode(query, Request.charset);
		}
		catch (UnsupportedEncodingException e)
		{
			Supervisor.errorAndDebug(this, e.toString());
			return null;
		}

		// Setzt den vollständigen URL zusammen
		String queryUrl = searchSetUrl + tokenUrl + "&q=" + query;

		if (page != null)
		{
			queryUrl += "&page=" + page;
		}
		
		// Fragt das JSON Object ab
		JSONObject obj = new JSONObject();
		try
		{
			obj = Request.GetJSONObject(queryUrl);
		}
		catch (MalformedURLException e)
		{
			Supervisor.errorAndDebug(this, e.toString());
			return null;
		}
		catch (IOException e)
		{
			Supervisor.errorAndDebug(this, e.toString());
			return null;
		}
		// Speichert das JSON Array mit den Sets
		JSONArray arr = obj.getJSONArray("sets");

		tempList = new ArrayList<String>();

		// Erstes Element des Arrays: Infos über die Suche
		String tempString = obj.getInt("total_results")
				+ separator + obj.getInt("total_pages")
				+ separator + obj.getInt("image_set_count")
				+ separator + obj.getInt("page");

		tempList.add(tempString);

		// Fügt die Sets dem Array hinzu
		for (int i = 0; i < arr.length(); i++)
		{
			tempString = Integer.toString(arr.getJSONObject(i).getInt("id"))
					+ separator + arr.getJSONObject(i).getString("title")
					+ separator + arr.getJSONObject(i).getString("created_by")
					+ separator + arr.getJSONObject(i).getInt("term_count")
					+ separator + arr.getJSONObject(i).getBoolean("has_images")
					+ separator + arr.getJSONObject(i).getString("description")
					+ separator + arr.getJSONObject(i).getString("lang_terms")
					+ separator + arr.getJSONObject(i).getString("lang_definitions")
					+ separator + i;
			tempList.add(tempString);
		}

		return tempList;
	}
	
	public ArrayList<String> searchSet (String query)
	{
		return searchSet(query, null);
	}

	/**
	 * Fragt ein spezifisches Set ab
	 * 
	 * @param id
	 *            Die Quizlet ID eines Sets (Kann man durch SearchSet
	 *            herausfinden)
	 * @return ArrayList mit dem Ergebnis<br>
	 *         Element 0: id, title, created_by, term_count, has_images,
	 *         description, lang_terms, lang_definitions<br>
	 *         Alle Anderen: id, term, definition
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public ArrayList<String> getSet (String id)
	{
		if (id == null)
			return null;

		// Setzt den URL zusammen
		String queryUrl = setUrl + id + tokenUrl;

		// Speichert JSON Object
		JSONObject obj = new JSONObject();
		try
		{
			obj = Request.GetJSONObject(queryUrl);
		}
		catch (MalformedURLException e)
		{
			Supervisor.errorAndDebug(this, e.toString());
			return null;
		}
		catch (IOException e)
		{
			Supervisor.errorAndDebug(this, e.toString());
			return null;
		}
		JSONArray arr = obj.getJSONArray("terms");

		tempList = new ArrayList<String>();

		// Element 0: Infos über das Set
		String tempString = obj.getInt("id")
				+ separator + obj.getString("title")
				+ separator + obj.getString("created_by")
				+ separator + obj.getInt("term_count")
				+ separator + obj.getBoolean("has_images")
				+ separator + obj.getString("description")
				+ separator + obj.getString("lang_terms")
				+ separator + obj.getString("lang_definitions");

		tempList.add(tempString);

		// Karten
		for (int i = 0; i < arr.length(); i++)
		{
			tempString = arr.getJSONObject(i).getInt("id")
					+ separator + arr.getJSONObject(i).getString("term")
					+ separator + arr.getJSONObject(i).getString("definition");
			tempList.add(tempString);
		}

		return tempList;
	}
}
