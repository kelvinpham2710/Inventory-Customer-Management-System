/*
 * The program creates a class Customer with default constructor, get and set methods
 */


import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//private static final double TV_COST = 199.95;
	private static final double SALES_TAX = 0.06;
	
	private String name;
	private String accNum;
	private int tvBuy;
	private double tvCost;
	private ArrayList<TV> idList = new ArrayList<TV>();
	private TVType type;
	
	public Customer() {	//default constructor
		name = "none";
		accNum = "none";
		tvBuy = 0;
		tvCost = 0.0;
		idList = null;
		type = null;
	}
	
	public Customer(String nm, String acc, int tvs, ArrayList<TV> list) {		
		name = nm;
		accNum = acc;
		tvBuy = tvs;
		idList.addAll(list);
	}
	
	public Customer(String nm, String acc, int tvs, ArrayList<TV> list, TVType t) {		
		name = nm;
		accNum = acc;
		tvBuy = tvs;
		idList.addAll(list);
		type = t;
	}
	
	public Customer(String nm, String acc) {
		name = nm;
		accNum = acc;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public void setAccNum(String acc) {
		accNum = acc;
	}
	
	public void setTVBuy(int tvs) {
		tvBuy = tvs;
	}
	
	public void setCost(double cost) {
		tvCost = cost;
	}
	
	public void setList(ArrayList<TV> list) {
		idList = list;
	}
	
	public void setTVType(TVType t) {
		type = t;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAccNum() {
		return accNum;
	}
	
	public int getTVBuy() {
		return tvBuy;
	}
	
	public double getCost() {
		return tvCost;
	}
	
	public ArrayList<TV> getList(){
		return idList;
	}
	
	public TVType getTVType() {
		return type;
	}
	
	/*
	public double calculateCost() {
		double subTotal = tvBuy * TV_COST;
		return subTotal + (subTotal * SALES_TAX);
	}
	*/
	
	public double calculateNewPrice() {
		double subTotal = tvBuy * type.getPrice();
		return subTotal + (subTotal * SALES_TAX);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nCheckout Receipt:\n");
		sb.append("Customer: " + name + "\n");
		sb.append("Account Number: " + accNum + "\n");
		sb.append("Purchased " + tvBuy + " TVs for $" + String.format("%.2f", calculateNewPrice()) + "\n");
		
		for (int i = 0; i < idList.size(); i++) {
			sb.append("TV ID Purchased is: " + idList.get(i).getID() + "\n");
		}

		return sb.toString();
			
	}
}
