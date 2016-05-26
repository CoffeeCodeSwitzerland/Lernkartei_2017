package globals;

import java.awt.Desktop;
import java.net.URL;

import debug.Debugger;
import debug.Logger;

public abstract class Functions
{
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
	
public static String AntiHTMLTags(String input){
		
		if(input.contains("<") || input.contains(">")){
			input = input.replace("<", "");
			input = input.replace(">", "");
		}
		return input;
	}
	
	public static String ColorBBCode(String input){

		if(input.contains("[color=")){
			String findStr = "[color=";
			int lastIndex = 0;
			int count = 0;

			while(lastIndex != -1){

			    lastIndex = input.indexOf(findStr,lastIndex);

			    if(lastIndex != -1){
			        count ++;
			        lastIndex += findStr.length();
			    }
			}
			int up = 0;
			while(count > up){
			String result = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
			input = input.replace("[color=(" + result + ")]", "<span style=\"color:" + result + "\">");
			input = input.replace("[/color]", "</span>");
			up++;
			}
		}
		return input;
	}
	
	public static String realBbCode2HTML (String input, String... tags)
	{	
		if (tags.length % 2 != 0) 
		{
			Debugger.out("real BB 2 HTML : The cout of tag arguments has to be even");
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
	 * @param toBeCompare
	 * @param by-String
	 * @return true, if toBeCompare is like by-String
	 */
	public static boolean like(String toBeCompare, String by){
	    if(by != null){
	        if(toBeCompare != null){
	            if(by.startsWith("%") && by.endsWith("%")){
	                int index = toBeCompare.toLowerCase().indexOf(by.replace("%", "").toLowerCase());
	                if(index < 0){
	                    return false;
	                } else {
	                    return true;
	                }
	            } else if(by.startsWith("%")){
	                return toBeCompare.endsWith(by.replace("%", ""));
	            } else if(by.endsWith("%")){
	                return toBeCompare.startsWith(by.replace("%", ""));
	            } else {
	                return toBeCompare.equals(by.replace("%", ""));
	            }
	        } else {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
	
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	    	// TODO: hier Meldung an User er sollte ein Standardbrowser haben
	    	if (urlString == null) urlString = "{null}";
	    	Logger.log("Functions.openWebpage("+urlString+"): please set a browser as standard!");
	    	Debugger.out("Functions.openWebpage("+urlString+"): " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}