package serverdb.sql;

import java.util.ArrayList;
import java.util.List;

public class Value extends Query
{

	ArrayList<String> input = new ArrayList<String>();
	
	public Value(ArrayList<String> attr) 
	{
	   input = attr;
	}

	public String getAttributes()
	{
		String concatAttributes = "";
		
		for(String attribute: input)
		{
			if(attribute != input.get(input.size()-1))
			{
				concatAttributes += attribute +",";
			}
			else
			{
				concatAttributes += attribute;
			}
		}	
		return concatAttributes;
	}
}
