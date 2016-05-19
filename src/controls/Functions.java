package controls;

import debug.Debugger;

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
}
