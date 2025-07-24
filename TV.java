/*
 * The program creates a class TV with default constructor, get and set methods
 */


public class TV implements Comparable<TV> {
	private String id;
	private TVType type;
	
	public TV() {	//default constructor
		id = null;
	}
	
	public TV(String nm) {	//constructor w/ parameter
		id = nm;
		type = null;
	}
	
	public TV(String nm, TVType t) {
		id = nm;
		type = t;
	}
	
	public void setID(String nm) {
		id = nm;
	}
	
	public void setTVType(TVType t) {
		type = t;
	}
	
	public String getID() {
		return id;
	}
	
	public TVType getTVType() {
		return type;
	}
	
	public String toString() {
		if (type == null) {
			return String.format("The TV id number is: %s%n", id);
		} else {
			return String.format("The TV id number is: %s%n%s", id, type);
		}
	}

	@Override
	public int compareTo(TV o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
