/*
 * The program creates a class TVType with default constructor, get and set methods, toString method for brand, model, and price
 */


public class TVType {
	
	private String brand;
	private String model;
	private double price;
	
	//default constructor
	public TVType() {
		brand = null;
		model = null;
		price = 0.0;
	}
	
	public TVType(String b, String m, double p) {
		brand = b;
		model = m;
		price = p;
	}
	
	public void setBrand(String b) {
		brand = b;
	}
	
	public void setModel(String m) {
		model = m;
	}
	
	public void setPrice(double p) {
		price = p;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public String getModel() {
		return model;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String toString(int counter) {
		return String.format("%-10d%-20s%-20s%s%7.2f", counter, brand, model, "$", price);
	}
	
	public String toString() {
		return String.format("%-20s%-20s%s%7.2f", brand, model, "$", price);
	}
	
}
