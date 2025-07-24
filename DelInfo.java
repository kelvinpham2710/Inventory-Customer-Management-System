/*
 * The program creates a class DelInfo with default constructors, get and set methods, toString method for Delivery Information
 */


public class DelInfo {
	
	private String name;
	private String address;
	private String accNum;
	private int tvBuy;
	
	public DelInfo() {
		name = null;
		address = null;
		accNum = null;
		tvBuy = 0;
	}
	
	public DelInfo(String nm, String adr, String acc, int tv) {
		name = nm;
		address = adr;
		accNum = acc;
		tvBuy = tv;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public void setAccNum(String acc) {
		accNum = acc;
	}
	
	public void setAddress(String adr) {
		address = adr;
	}
	
	public void setTVBuy(int tv) {
		tvBuy = tv;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAccNum() {
		return accNum;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getTVBuy() {
		return tvBuy;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Name: %-22sAccount Number: %s\n", name, accNum));
		sb.append(String.format("Address: %s\n", address));
		sb.append(String.format("Number of TVs: %d\n\n", tvBuy));
		
		return sb.toString();
	}
	
}
