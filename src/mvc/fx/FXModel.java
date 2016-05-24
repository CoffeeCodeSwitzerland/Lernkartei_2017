package mvc.fx;

import java.util.Iterator;

import globals.Functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.Model;

/**
 * Diese Klasse ist instanzierbar und für das ViewModel nutzbar.
 * Zudem wurde hier die Funktionalität etas erweitert (zum Bsp. mit like()).
 * 
 * @author hugo-lucca
 */
public class FXModel extends Model
{
	private final ObservableList<String> observableDataList = FXCollections.observableArrayList();
	
	public ObservableList<String> getObservableDataList() {
		return observableDataList;
	}

	public ObservableList<String> getObservableDataList(String query) {
		if (query != null && !query.equals("")) {
			ObservableList<String> reducedList = FXCollections.observableArrayList();
			Iterator<String> it = getObservableDataList().iterator();
			while (it.hasNext()) {
				String s = it.next();
				if (s.equals(query) || s.equalsIgnoreCase(query)|| 
					s.contains(query) || s.matches(query) || Functions.like(s,query)) {
					reducedList.add(s);
				}
			}
			return reducedList;
		}
		return getObservableDataList();
	}
}
