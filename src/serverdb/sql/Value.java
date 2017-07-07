package serverdb.sql;

import java.util.ArrayList;
import java.util.List;

public class Value extends Query
{

	ArrayList<String> input = new ArrayList<String>();
	
	public Value(ArrayList<String> val) 
	{
	   input = val;
	}

	public String getValues()
	{
		String concatValues = "";
		
		for(String value: input)
		{
			if(value != input.get(input.size()-1))
			{
				concatValues += value +",";
			}
			else
			{
				concatValues += value;
			}
		}	
		return concatValues;
	}
}
