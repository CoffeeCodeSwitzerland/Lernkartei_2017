package mvc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Diese Klasse ist instanzierbar und für das ViewModel nutzbar.
 * Zudem wurde hier die Funktionalität etas erweitert (zum Bsp. mit like()).
 * 
 * @author hugo-lucca
 */
public class Model extends DataModel
{
	public Model (String myName)
	{
		super(myName);
	}

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

	@Override
	public String getString (String query)
	{
		if (query == null || query.equals("string")) {
			return super.getString(query);
		} else {
			try {
				int position = Integer.parseInt(query);
				if (position >= 0 && position < this.getDataList(null).size()) {
					return this.getDataList(null).get(position); 
				}
			} catch (Exception e) {
				return super.getString(query);
			}
		}
		return "";
	}

	@Override
	public int doAction(String functionName, String freeStringParam, double freeDoubleParam) {
		if (functionName.equals("clear")) {
			this.setString(null);
			this.getDataList(null).clear();
		}
		return 0; // no error
	}

	@Override
	public ArrayList<String> getDataList(String query) {
		if (query != null && !query.equals("")) {
			ArrayList<String> reducedList = new ArrayList<>();
			Iterator<String> it = this.getDataList(null).iterator();
			while (it.hasNext()) {
				String s = it.next();
				if (s.equals(query) || s.equalsIgnoreCase(query)|| 
					s.contains(query) || s.matches(query) || like(s,query)) {
					reducedList.add(s);
				}
			}
			return reducedList;
		}
		return this.getDataList(null);
	}
}
