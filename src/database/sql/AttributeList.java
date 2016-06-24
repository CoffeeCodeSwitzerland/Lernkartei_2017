package database.sql;

import java.util.ArrayList;

import debug.Logger;

/**
 * @author WISS
 *
 */
public class AttributeList {

	final private ArrayList<Attribute> myAttributes = new ArrayList<>();
	private int countPrimary = 0;
	/**
	 * To build a list of attributes
	 * 
	 * @return the attribute-list or "" for problems
	 */
	public void add(Attribute a) {
		// Add only new attributes
		if (a != null) {
			if (a.isPrimary()) countPrimary++;
			if (myAttributes.size() == 0) {
				myAttributes.add(a);
			} else {
				boolean found = false;
				for (int i = 0; i < myAttributes.size(); i++) {
					if (myAttributes.get(i).getName().equals(a.getName())) {
						found = true;
						break;
					}
				}
				if (!found)
					myAttributes.add(a);
			}
		} else {
			Logger.out("tried to add invalid null attribute");
		}
	}

	public void add(AttributeList al) {
		for (int i = 0; i < al.size(); i++) {
			this.add(al.get(i));
		}
	}

	public int size() {
		return myAttributes.size();
	}

	public Attribute get(int i) {
		return myAttributes.get(i);
	}

	public String getCommaSeparatedList(boolean addPrimary) {
		int size = myAttributes.size();
		int commas =0;
		if (size >= 1) {
			String attributeList = "";
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					int o;
					switch (a.getType()) {
					case INT:
						o = Attribute.Datatype.INT.ordinal();
						break;
					case FKEY:
						o = Attribute.Datatype.FKEY.ordinal();
						break;
					case PKEY:
						o = Attribute.Datatype.PKEY.ordinal();
						break;
					case TEXT:
					default:
						o = Attribute.Datatype.TEXT.ordinal();
						break;
					}
					if (!(a.isPrimary() && addPrimary==false)) {
						attributeList += a.getName() + " " + Attribute.SQLDataTypes[o];
						if (commas < size - 1 - ((addPrimary==true) ? 0:countPrimary) ) {
							attributeList += ",";
							commas++;
						}
					}
				}
			}
			return attributeList;
		} else {
			Logger.out("invalid data to build a list of attributes!");
		}
		return "";
	}

	protected String getSeekedKeys() {
		int size = myAttributes.size();
		if (size >= 1) {
			String attributeList = "";
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					if (a.isKey()) {
						attributeList += a.getName();

						int o;
						switch (a.getType()) {
						case INT:
							o = Attribute.Datatype.INT.ordinal();
							break;
						case FKEY:
							o = Attribute.Datatype.FKEY.ordinal();
							break;
						case PKEY:
							o = Attribute.Datatype.PKEY.ordinal();
							break;
						case TEXT:
						default:
							o = Attribute.Datatype.TEXT.ordinal();
							break;
						}
						attributeList += Attribute.SQLDataTypes[o];
						if (i < size - 1) {
							attributeList += ",";
						}
					}
				}
			}
			return attributeList;
		} else

		{
			Logger.out("invalid data to build a list of key attributes!");
		}
		return "";
	}

	public String getKeyName() {
		int size = myAttributes.size();
		if (size >= 1) {
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					if (a.isKey()) {
						return a.getName();
					}
				}
			}
		} else {
			Logger.out("key name not found!");
		}
		return "";
	}

	public Attribute seekKeyNamed(String seekName) {
		int size = myAttributes.size();
		if (size >= 1) {
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
//					debug.Debugger.out("ATT("+a.getName().toLowerCase()+") ?= '"+seekName.toLowerCase()+"'");
					if (a.getName().toLowerCase().equals(seekName.toLowerCase())) {
						return a;
					}
				}
			}
			Logger.out("key not found in list of ("+size+")!", seekName);
		} else {
			Logger.out("list is empty, key not found!", seekName);
		}
		return null;
	}

	public PrimaryKey getPKey() {
		int size = myAttributes.size();
		if (size >= 1) {
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					if (a.isPrimary()) {
						return (PrimaryKey) a;
					}
				}
			}
		} else {
			Logger.out("pimary key not found!");
		}
		return null;
	}

	public String toKeyList(boolean addPK) {
		int size = myAttributes.size();
		if (size >= 1) {
			String keyList = "";
			int commas =0;
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					if (a != null) {
						if (!(addPK == false && a.isPrimary())) {
							keyList += a.getName();
							if (commas < size - 1 -((addPK==true)?0:countPrimary)) {
								keyList += ", "; 
								commas++;
							}
//							debug.Debugger.out("KLIST:",a.getName()+" P:"+a.isPrimary());
						}
					}
				}
			}
			return keyList;
		} else {
			Logger.out("invalid data to build a list of keys!");
		}
		return "";
	}

	/**
	 * To build a list of values from a data array (with mixed String and
	 * integer)
	 * 
	 * @return list of values or "" for problems
	 */
	public String toValueList(boolean addPrimary) {
		int size = myAttributes.size();
		if (size >= 1) {
			String valueList = "";
			int commas=0;
			for (int i = 0; i < size; i++) {
				Attribute a = myAttributes.get(i);
				if (a != null) {
					if (!(addPrimary==false && a.isPrimary())) {
						String value = a.getValue();
						if (a.isValue() == false) {
							if (value == null)
								value = "";
							valueList += "'" + value + "'";
						} else {
							if (value == null)
								value = "0";
							valueList += value;
						}
						if (commas < (size - 1 - ((addPrimary==true)?0:countPrimary))) {
//							debug.Debugger.out("VAL:"+value+":"+i+"/"+size+"/"+addPrimary+"/V:"+a.isValue());
							valueList += ",";
							commas++;
						}
						//debug.Debugger.out("("+i+")");
					}
				}
			}
			return valueList;
		} else {
			Logger.out("invalid data to build a list of values!");
		}
		return "";
	}

	/**
	 * To build a clause sequence from data
	 * 
	 * @param sep
	 * @return
	 */
	public String toClause(String sep) {
		int size = myAttributes.size();
		if (size >= 1 && sep != null && !sep.equals("")) {
			String clause = "";
			for (int i = 0; i < size; i++) {
				if (myAttributes.get(i) != null) {
					Attribute a = myAttributes.get(i);
					String value = a.getValue();
					if (!clause.equals(""))
						clause += sep + " ";
					if (a.isValue() == false) {
						if (value == null)
							value = "";
						clause += a.getName() + " = '" + a.getValue() + "' ";
					} else {
						if (value == null)
							value = "0";
						clause += a.getName() + " = " + a.getValue() + " ";
					}
				}
			}
			return clause;
		} else {
			Logger.out("invalid data to build a clause!");
		}
		return "";
	}

	/**
	 * To build a default list of value-type-flags
	 * 
	 * @param attributes
	 * @param defaultValue
	 * @return the flag-list or null for errors
	 */
	// protected boolean[] toIsValues(String[] attributes, boolean defaultValue)
	// {
	// if (attributes != null & attributes.length >= 1) {
	// boolean[] isValues = new boolean[attributes.length];
	// for (int i = 0; i < isValues.length; i++) {
	// isValues[i] = defaultValue;
	// }
	// return isValues;
	// }
	// Logger.out("invalid data to build a flag list!");
	// return null;
	// }
}
