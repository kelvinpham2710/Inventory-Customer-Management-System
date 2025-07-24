/*
 * The program creates a class CustomerData that implements Serializable
 */


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class CustomerData implements Iterable<Customer>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	//create a List of type Customer
	private LinkedList<Customer> customerList;
	
	//default constructor
	public CustomerData() {
		customerList = new LinkedList<>();
	}
	
	//iterator method that returns the list as an iterable list
	@Override
	public Iterator<Customer> iterator() {
		return customerList.iterator();
	}
	
	public void addCustomer(Customer customer) {
		customerList.add(customer);
	}
	
	public void removeCustomer(Customer customer) {
		customerList.remove(customer);
	}
	
	public void updateCustName(String name, String accNum) {
		for(Customer customer : customerList) {
			if(accNum.equalsIgnoreCase(customer.getAccNum())) {
				customer.setName(name);
			}
		}
	}
	
	public Customer findCustomer(String accNum) {	
		for(Customer customer : customerList) {
			if(accNum.equalsIgnoreCase(customer.getAccNum())) {
				return customer;
			}
		}
		return null;
	}
	
	public int getListSize() {
		return customerList.size();
	}
	
	public Customer[] convertToArray() {
		
		Object[] objectArray = customerList.toArray();
		
		Customer[] customerArray = new Customer[customerList.size()];
		
		for(int i = 0; i < objectArray.length; i++) {
			customerArray[i] = (Customer) objectArray[i];
		}
		
		return customerArray;
		
	}
	
	public void displayCustomerList() {
		System.out.println("\nCustomer List:");
		System.out.println("--------------");
		for(int i = 0; i < customerList.size(); i++) {
			System.out.printf("%s%4d%-2s%-25s%-17s%s%n", "Customer", i+1, ":", customerList.get(i).getName(), "Account Number:", customerList.get(i).getAccNum());
		}
	}
	
}
