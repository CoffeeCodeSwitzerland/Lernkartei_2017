 package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Supervisor;
import mvc.Model;
import mvc.ModelInterface.Command;


public class DMOModifyStackModel extends Model
{
	static ArrayList<String> data = new ArrayList<String>();
	
	
	@Override
	public ArrayList<String> getDataList (String action)
	{
		return data;
	}
	
	@Override
	public void setDataList (ArrayList<String> dataList)
	{
		data = dataList;
	}
	
	@Override
	public void addDataList (ArrayList<String> dataList)
	{
		for (String s: dataList)
		{
			data.add(s);
		}
	}
	
	@Override
	public void addData (String value)
	{
		data.add(value);
	}
	
	
}
