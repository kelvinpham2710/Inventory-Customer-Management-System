/*
 * The program keeps track of the TV inventory and perform multiple activities using Stacks, and customers using Queues, sorting using Recursion
 * Using Heaps, Sets and Maps
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryAndCustomerManagement {

	//final variables
	
		//main menu
	private static final int STOCK_SHELVES = 1;
	private static final int FILL_ORDER = 2;
	private static final int RESTOCK_RETURN = 3;
	private static final int RESTOCK_INVT = 4;
	private static final int CUST_UPDATE = 5;
	private static final int CUST_PURCHASE = 6;
	private static final int CUST_CHECKOUT = 7;
	private static final int DISPLAY_DELIVERY = 8;
	private static final int DISPLAY_INVT = 9;
	private static final int END_PROGRAM = 10;
	
	
		//customer update menu
	private static final int ADD_CUST = 1;
	private static final int DELETE_CUST = 2;
	private static final int CHANGE_NAME = 3;
	private static final int SAVE_CHANGES = 4;
	private static final int DISPLAY_CUST = 5;
	private static final int RETURN_MAIN = 6;
	
	
	private static final String FILE_DIRECTORY = "c:\\test\\";	//the directory will be ' c:\test\ '
	
	private static final String CUSTOMER_FILE_NAME = "CustFile.txt";
	
	private static final String STACK_FILE_NAME = "stack.txt";
	
	private static final int SHELVES_TV = 5;	//stock the shelves with 5 TVs
	
	private static final int SINGLE_TV = 1;
	
	private static final int FIRST_7_ELE = 7;	//the first 7 elements of the ID number
	
	private static final int RESTOCK_TV = 5;	//restock 5 new tv to inventory
	
	private static final int MAX_DELIVERIES = 25;	//maximum number of deliveries in a day
	
	private static final double SALE_TAX = 0.06;
	
	
	public static void main(String[] args) {
		
		//variables
		int choice = 0;
		boolean endProgram = false;
		
		boolean newCustomerAdded = false;
		
		int topIdNum;	//last numbers of the top TV's ID number
		
		String tvSoldFilePath = null;	//tvsold.txt
		
		
		Scanner scanner = new Scanner(System.in);	//create a Scanner object
		
		Stack<TV> stack = new Stack<>();	//create a String Stack object stores TVs
		
		Queue<Customer> customerQueue = new LinkedList<Customer>();	//create a Customer Queue as a LinkedList object
		
		CustomerData customerData = new CustomerData();	//instantiate a new object of type CustomerData stores Customers' info
		
		BinaryTree TVTypeTree = new BinaryTree();
		
		
		//read the inventory
		readInventory(stack);
		
		
		//read customer data
		readCustomer(customerData);
		
		
		topIdNum = topTVNum(stack);	//get the top TV's ID number
		
		
		//display title
		title();
		
		
		
		while(!endProgram) {
			
			
			try {	//try catch block to check for integer input
				mainMenu();
				do {	//check for valid input of a menu entry
					
					System.out.print("Please enter the menu choice: ");
					choice = scanner.nextInt();
					
					if(choice < STOCK_SHELVES || choice > END_PROGRAM) {
						System.out.println("Invalid Choice. Please choose again\n");
						mainMenu();
					}
					
				} while(choice < STOCK_SHELVES || choice > END_PROGRAM);
				
				//break;
				
			} catch(Exception e) {
				System.out.println("Invalid Input. Only number is allowed. Please reenter");
				scanner.nextLine();
			}
			
			
			
			
			//switch statement
			switch(choice) {
			
			case STOCK_SHELVES://1
				if(stack.size() <= SHELVES_TV) {
					System.out.println("There is not enough TV to stock the shelves.");
				} else {
					for(int i = 0; i < SHELVES_TV; i++) {
						System.out.println(stack.pop());
					}
					displayInvtLeft(stack);
				}
				
				break;
				
			case FILL_ORDER://2
				if(stack.size() < SINGLE_TV) {
					System.out.println("There is not enough Tv to fill web order.");
				} else {
					System.out.println("The following TV's has been shipped:");
					System.out.println(stack.pop());
					displayInvtLeft(stack);
				}
				
				break;
				
			case RESTOCK_RETURN://3
				
				scanner.nextLine();
				
				
				if(tvSoldFilePath == null) {
					System.out.print("Please enter in the previous TV Sales file path: ");
					tvSoldFilePath = scanner.nextLine();
				}
				
				TVReturn returnedTVList = new TVReturn(tvSoldFilePath);
				
				TV returnedTV = null;
				
				while(returnedTV == null) {
					System.out.print("Please enter the id number of the returned TV: ");
					String id = scanner.nextLine();
				
					returnedTV = returnedTVList.returnFoundTV(id);
					
					if(returnedTV == null) {
						System.out.println("Item not found in the TV sold list. Please reenter!");
					}
					else {
						System.out.println("Found item:");
						System.out.print(returnedTV);
					}
				}
				
				
				System.out.printf("%n%nThe customer should receive creadit for: $ %.2f%n", returnedTV.getTVType().getPrice() * (1 + SALE_TAX));
				
				addSingleTV(stack, topIdNum + SINGLE_TV);
				displayInvtLeft(stack);
				
				break;
				
			case RESTOCK_INVT://4
				for(int i = 0; i < RESTOCK_TV; i++) {
					topIdNum += SINGLE_TV;
					addSingleTV(stack, topIdNum);
				}
				
				displayInvtLeft(stack);
				
				break;
				
			case CUST_UPDATE://5
				
				boolean returnMain = false;
				boolean changesMade = false;
				int cChoice = 0;
				
				
				while(!returnMain) {
					
					try {	//try catch block to check for integer input
						customerMenu();
						do {	//check for valid input of a menu entry
							
							System.out.print("Please enter the menu choice: ");
							cChoice = scanner.nextInt();
							
							if(cChoice < ADD_CUST || cChoice > RETURN_MAIN) {
								System.out.println("Invalid Choice. Please choose again\n");
								customerMenu();
							}
							
						} while(cChoice < ADD_CUST || cChoice > RETURN_MAIN);
						
						
					} catch(Exception e) {
						System.out.println("Invalid Input. Only number is allowed. Please reenter");
						scanner.nextLine();
					}	//end of try catch
					
					
					
					//Customer Update Menu
					switch(cChoice) {
					
					case ADD_CUST://1
						//variables
						String name, addid = null;
						
						customerData = sortedCustomerData(customerData);
						
						scanner.nextLine();
						System.out.println("Add a new customer to the system");
						
						System.out.print("Please enter the customer name: ");
						name = scanner.nextLine();
						
						do {
							System.out.print("Please enter the customer ID number: ");
							addid = scanner.nextLine();
							
							if(customerData.findCustomer(addid) != null) {
								System.out.println("ERROR - This account already exists, please reenter!!\n");
							}
						} while(customerData.findCustomer(addid) != null);	//end of do while
						
						Customer newCustomer = new Customer(name, addid);
								
						customerData.addCustomer(newCustomer);
						
						changesMade = true;
						
						break;
					
					case DELETE_CUST://2
						//variables
						String delid = null;
						
						customerData = sortedCustomerData(customerData);
						
						scanner.nextLine();
						System.out.println("Remove a customer from the system");
						
						do {
							System.out.print("Please enter the customer ID number: ");
							delid = scanner.nextLine();
							
							if(customerData.findCustomer(delid) == null) {
								System.out.println("ERROR - This account does not exists, please reenter!!\n");
							}
						} while(customerData.findCustomer(delid) == null);
						
						customerData.removeCustomer(customerData.findCustomer(delid));
						
						changesMade = true;
						
						break;
						
					case CHANGE_NAME://3
						//variables
						String changeName, changeid;
						
						scanner.nextLine();
						
						customerData = sortedCustomerData(customerData);
						
						do {
							System.out.println("Change the name of a customer in the system");
							
							System.out.print("Please enter the customer ID number: ");
							changeid = scanner.nextLine();
							
							if(customerData.findCustomer(changeid) == null) {
								System.out.println("ERROR - This account does not exists, please reenter!!\n");
							}					
						} while(customerData.findCustomer(changeid) == null);
						
						System.out.print("Please enter the new customer name: ");
						changeName = scanner.nextLine();
						
						customerData.updateCustName(changeName, changeid);
						
						changesMade = true;
						
						break;
						
					case SAVE_CHANGES://4
						//ask for file directory and write customer data to file
						customerData = sortedCustomerData(customerData);
						writeCustomer(scanner, customerData);
						
						changesMade = false;
						
						newCustomerAdded = false;
						
						break;
					
					case DISPLAY_CUST://5
						customerData = sortedCustomerData(customerData);
						customerData.displayCustomerList();
						break;
						
					case RETURN_MAIN://6
						//variables
						char ans = 0;
						boolean validAns = false;
						
						customerData = sortedCustomerData(customerData);
						customerData.displayCustomerList();
						
						if(changesMade) {
								
							while(!validAns) {
								System.out.print("\nWarning - you have not saved any changes. Do you want to return to the main menu? (y or n) ");
								ans = scanner.next().charAt(0);
								Character.toLowerCase(ans);
									
								if(ans == 'y' || ans == 'n') {
									validAns = true;
								}
								else {
									System.out.println("Error invalid entry, please try again!");
								}
							}
								
							if(ans == 'y') {
								returnMain = true;
							}
	
						}
						else {
							returnMain = true;
						}
						
						break;
					
					}	//end of switch
					
					
					
				}	//end of while
				
				break;
			
				
			case CUST_PURCHASE://6
				
				String custName = null;	//customer's name
				String custAcc;		//customer's account number
				String custAddress;
				int tvPurchased;	//number of tv purchased
				TVType type = null;
				
				
				//read the TVType data
				TVTypeTree = readTVInfo(scanner);
				
				
				//display sorted customer list
				customerData = sortedCustomerData(customerData);
				customerData.displayCustomerList();
				
				
				if(stack.size() <= 0) {	//TVs <= 0
					System.out.println("Error - There's no more TVs left.");
				} else {	//TVs > 0				
					
					System.out.print("\nPlease enter the customer account number or none: ");
					custAcc = scanner.nextLine();
					
					//if 'none' is enter
					if(custAcc.toLowerCase().equals("none")) {
						System.out.println("Customer not found\n");
						
						System.out.print("Please enter the customer name: ");
						custName = scanner.nextLine();
						
						do {
							System.out.print("Please enter a new customer account number for " + custName + ": ");
							custAcc = scanner.nextLine();
							
							if(customerData.findCustomer(custAcc) != null) {
								System.out.println("ERROR - This account number already exists, please reenter!!\n");
							}
						} while(customerData.findCustomer(custAcc) != null);
						
						//add the new customer to the CustomerData
						Customer tempCust = new Customer(custName, custAcc);
						customerData.addCustomer(tempCust);
						
						System.out.println("New customer added!!");
						
						newCustomerAdded = true;
					}
					
					//if there is NO match
					else if(customerData.findCustomer(custAcc) == null) {
						System.out.print("Please enter the customer name: ");
						custName = scanner.nextLine();
						
						//add the new customer to the CustomerData
						Customer tempCust = new Customer(custName, custAcc);
						customerData.addCustomer(tempCust);
						
						newCustomerAdded = true;
					}
					
					//if there is a match
					else {
						custName = customerData.findCustomer(custAcc).getName();
						System.out.println("Customer is: " + customerData.findCustomer(custAcc).getName());
					}
					
					
					//display the list of TV Types
					System.out.println("\nTV Options:");
					System.out.printf("%-10s%-20s%-20s%s\n", "Item", "Brand", "Model", "Cost");
					System.out.printf("%-10s%-20s%-20s%s\n", "----", "-----", "-----", "----");
					TVTypeTree.inorderTraversal(TVTypeTree.getRoot());
					
					
					//ask user for brand and model of the TV
					String brand;
					String model;
					boolean doneSearch = false;
					
					while(!doneSearch) {
						
						//scanner.nextLine();
						System.out.print("Please enter in the brand: ");
						brand = scanner.nextLine();
						System.out.print("Please enter in the model: ");
						model = scanner.nextLine();
						
						type = TVTypeTree.searchNode(brand, model);
						
						if(type != null) {
							System.out.printf("%-15s%-20s%-2s%.2f%n", type.getBrand(), type.getModel(), "$", type.getPrice());
							doneSearch = true;
						}
						else {
							System.out.println("TV Not Found, Please reenter!\n");
						}
						
						
					}
					
					
					//validate TV purchased
					do {
						System.out.print("\nPlease enter the number of TV purchased: ");
						tvPurchased = scanner.nextInt();
						
						if(tvPurchased <= 0 || tvPurchased > stack.size()) {
							if(tvPurchased < 0) {
								System.out.println("Error - you cannot enter in a negative value of TVs to purchase, please reenter");
							} else if(tvPurchased == 0) {
								System.out.println("Error - you cannot enter in a zero value of TVs to purchase, please reenter");
							} else {
								System.out.println("Error - there is not enough TVs to purchase, please reenter");
							}
						}
						
					} while(tvPurchased <= 0 || tvPurchased > stack.size());
					
					
					ArrayList<TV> tvList = new ArrayList<TV>();	//create a TV ArrayList object
					
					
					System.out.printf("%nCustomer %s purchased the following TVs:%n", custName);
					for(int i = 0; i < tvPurchased; i++) {
						System.out.print(stack.peek());
						tvList.add(stack.peek());	//add the top TV from the Stack to the ArrayList
						stack.pop();	//remove the top TV of the Stack					}
					}
					
					Customer newCust = new Customer(custName, custAcc, tvPurchased, tvList, type);	//create a new Customer object
					
					customerQueue.add(newCust);	//add the new Customer object to the LinkedList
					
					
					
					//ask user if the user want delivery
					boolean validAns = false;
					char delAns = 0;
					
					while(!validAns) {
						System.out.print("Does the customer want the TVs delivered? (y or n) ");
						delAns = scanner.next().charAt(0);
						Character.toChars(delAns);
						
						if(delAns == 'y' || delAns == 'n') {
							validAns = true;
						}
						else {
							System.out.println("Error - invalid entry, please try again!");
						}
					}
					
					if(delAns == 'y') {
						scanner.nextLine();
						System.out.print("Please enter customer address: ");
						custAddress = scanner.nextLine();
						appendDeliveryData(scanner, custName, custAddress, custAcc, tvPurchased);
					}
					
					
					
					//display the number of TVs left in the Stack
					displayInvtLeft(stack);
					
					
					
					//if there's any additional customer to the list
					//require user to save data to file
					if(newCustomerAdded) {
						System.out.println("\nCustomer Data has been updated, you must save now.\n");
						writeCustomer(scanner, customerData);
						newCustomerAdded = false;
					}
					
					
					tvList.clear();
					
				}	//end of if else
				
				break;
				
			case CUST_CHECKOUT://7
				if(customerQueue.isEmpty()) {
					System.out.println("Error - There's no customer to checkout.");
				}
				else {
					System.out.print(customerQueue.element());
					
					customerQueue.remove();
					
					System.out.println("\nThere are " + customerQueue.size() + " customers left to checkout.\n");
				}
				
				break;
				
			case DISPLAY_DELIVERY://8
				
				//read the delivery data file
				MaxHeap maxHeap = readDeliveryInfo(scanner);
				
				//display delivery report
				System.out.println("Delivery Report");
				System.out.println("---------------");
				int size = maxHeap.getActualSize();
				for(int i = 0; i < size; i++) {
					DelInfo deliveryInfo = maxHeap.removeRoot();
					if(deliveryInfo != null) {
						System.out.println("Delivery Stop #" + (i+1) + ":");
						System.out.print(deliveryInfo);
					}
				}
				
				
				break;
				
			case DISPLAY_INVT://9
				displayInvt(stack);
				
				break;
				
			case END_PROGRAM://10
				//variables
				char exitAns = 0;
				boolean validExitAns = false;
				
				//if there's still customer in the queue
				if(!customerQueue.isEmpty()) {	
					System.out.println("There are still customers who have not checked out.");
					System.out.println("Please make sure all customers are processed before ending the program");
				}
				
				//if there's unsaved changes
				else if(newCustomerAdded) {
					while(!validExitAns) {
						System.out.println("Warning - you have not saved any changes!!");
						System.out.print("Do you want to end the program without saving the changes? (y or n) ");
						exitAns = scanner.next().charAt(0);
						Character.toChars(exitAns);
						
						if(exitAns == 'y' || exitAns == 'n') {
							validExitAns = true;
						}
						else {
							System.out.println("Error - invalid entry, please try again!");
						}
					}//end of while
					
					if(exitAns == 'y') {	//if yes, set there's no customer added to the list
						newCustomerAdded = false;
					}
				}
				
				/*
				 * if the queue is clear AND there's no customer added
				 * exit the program
				 */
				if(customerQueue.isEmpty() && !newCustomerAdded) {
					displayInvt(stack);
					System.out.println("Thank you for using the program");		
					endProgram = true;	//end the loop
				}
				
				
				break;
			
			}	//end of switch statement
			
			
		}	//end of while loop
		
		
		scanner.close();
		stack.clear();
		customerQueue.clear();
		
	}	//end of main
	
	

	//method displays title of the program
	public static void title() {
		System.out.println("Inventory & Customer Management System");
	}
	
	
	//method displays main menu
	public static void mainMenu() {
		System.out.println("\nMenu Options");
		System.out.println(STOCK_SHELVES + " - Stocks Shelves");
		System.out.println(FILL_ORDER + " - Fill Web Order");
		System.out.println(RESTOCK_RETURN + " - Restock Return");
		System.out.println(RESTOCK_INVT + " - Restock Inventory");
		System.out.println(CUST_UPDATE + " - Customer Update");
		System.out.println(CUST_PURCHASE + " - Customer Purchase");
		System.out.println(CUST_CHECKOUT + " - Customer Checkout");
		System.out.println(DISPLAY_DELIVERY + " - Display Delivery List");
		System.out.println(DISPLAY_INVT + " - Display Inventory");
		System.out.println(END_PROGRAM + " - End Program");
	}
	
	
	//method displays customer update menu
	public static void customerMenu() {
		System.out.println("\nCustomer Update Menu Options");
		System.out.println(ADD_CUST + " - Add a Customer");
		System.out.println(DELETE_CUST + " - Delete a Customer");
		System.out.println(CHANGE_NAME + " - Change Customer Name");
		System.out.println(SAVE_CHANGES + " - Save Changes");
		System.out.println(DISPLAY_CUST + " - Display Customer List");
		System.out.println(RETURN_MAIN + " - Return to Main");
	}
	
	
	//method reads inventory
	public static void readInventory(Stack<TV> stack) {
		/*
		 * create a Scanner object of the text file
		 * read each line of the text file as String
		 * create a new TV object of the line that just read
		 * push each new TV object into the stack
		 */
		
		try {
			
			Scanner sc = new Scanner(new File(FILE_DIRECTORY + STACK_FILE_NAME));
			
			while(sc.hasNextLine()) {	//read each line of the text file
				TV newTV = new TV(sc.nextLine());	//create a new tv object
				stack.push(newTV);	//push new tv object into stack
			}
			
			sc.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found!\n");
		}
		
	}
	
	
	//method reads customer data from file
	public static void readCustomer(CustomerData customerData) {
		
		try {
			
			Scanner sc = new Scanner(new File(FILE_DIRECTORY + CUSTOMER_FILE_NAME));
			
			while(sc.hasNextLine()) {
				String name = sc.nextLine();
				String accNum = sc.nextLine();
				
				Customer newCustomer = new Customer(name, accNum);
				
				customerData.addCustomer(newCustomer);
			}
		
			sc.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found!\n");
		}
			
	}
	
	
	//method reads tv's info from file
	public static BinaryTree readTVInfo(Scanner scanner) {
		//variables
		String filePath;
		boolean done = false;
		BinaryTree tree = new BinaryTree();
		
		scanner.nextLine();
		
		while(!done) {
			
			System.out.print("\nPlease enter in the file path: ");
			filePath = scanner.nextLine();	//tx.txt
			
			try {
				Scanner sc = new Scanner(new File(filePath));
				
				while(sc.hasNextLine()) {
					String brand = sc.nextLine();
					String model = sc.nextLine();
					double price = Double.parseDouble(sc.nextLine());
					
					TVType newType = new TVType(brand, model, price);
					
					tree.insertNode(newType);
				}
				
				done = true;
				sc.close();
				
			} catch(FileNotFoundException e) {
				System.out.println("ERROR - File not found!\n");
			}
			
		}
		
		return tree;
		
	}
	
	
	//method read delivery info file
	public static MaxHeap readDeliveryInfo(Scanner scanner) {
		//variables
		String filePath;
		boolean done = false;
		
		scanner.nextLine();
		
		//create MaxHeap object that max deliveries in a day is 25
		MaxHeap maxHeap = new MaxHeap(MAX_DELIVERIES);
		
		while(!done) {
			System.out.print("\nPlease enter in the delivery info file path: ");
			filePath = scanner.nextLine();
			
			try {
				Scanner sc = new Scanner(new File(filePath));
				
				while(sc.hasNextLine()) {
					String name = sc.nextLine();
					String address = sc.nextLine();
					String accNum = sc.nextLine();
					int tv = Integer.parseInt(sc.nextLine());
					
					DelInfo deliveryInfo = new DelInfo(name, address, accNum, tv);
					maxHeap.insertNode(deliveryInfo);
				}
				
				done = true;
				sc.close();
				
			} catch(FileNotFoundException e) {
				System.out.println("ERROR - File not found!\n");
			}
			
		}//end of while	
		
		maxHeap.balanceHeap();
		return maxHeap;
	}
	
	
	//method append data to the end of the delivery info file
	public static void appendDeliveryData(Scanner scanner, String name, String address, String accNum, int tv) {
		//variables
		String outFile;
		boolean done = false;
		
		
		while(!done) {
			try {
				System.out.print("Please enter in the delivery info file path: ");
				outFile = scanner.nextLine();
				
				File file = new File(outFile);
				
				if(file.exists()) {
					System.out.println("File already exists.");
				}
				
				FileWriter writer = new FileWriter(file, true);
				
				writer.write(System.lineSeparator());
				writer.write(name + System.lineSeparator());
				writer.write(address + System.lineSeparator());
				writer.write(accNum + System.lineSeparator());
				writer.write(tv + System.lineSeparator());
				
				System.out.println("Successfully wrote to the file.");
				
				done = true;
				writer.close();
				
			} catch(IOException e) {
				System.out.println("ERROR - Couldn't write to the file!!");
			}
		}
		
	}

	
	//method asks for file directory and writes customer data to file
	public static void writeCustomer(Scanner scanner, CustomerData customerData) {
		//variables
		String outFile;
		boolean done = false;
		
		scanner.nextLine();
		
		while(!done) {
			
			try {			
				System.out.print("Please enter the name of the file to save: ");
				outFile = scanner.nextLine();
				
				File file = new File(outFile);
				
				FileWriter writer = new FileWriter(outFile);
				for(Customer customer : customerData) {
					writer.write(customer.getName() + System.lineSeparator());
					writer.write(customer.getAccNum() + System.lineSeparator());
				}
				
				System.out.println("File created: " + file.getName());
				
				System.out.println("Successfully wrote to the file.");
				
				done = true;	//end the loop
				
				writer.close();
			} catch(IOException e) {
				System.out.println("ERROR - Couldn't write to the file!!");
			}
			
		}
		
	}
	
	
	//method displays how many tv's left in inventory
	public static void displayInvtLeft(Stack<TV> stack) {
		if(stack.size() == SINGLE_TV) {
			System.out.println("There is " + stack.size() + " TV left in inventory");
		} else {
			System.out.println("There are " + stack.size() + " TV's left in inventory");
		}
	}
	
	
	//method gets the last numbers of the top TV's ID
	public static int topTVNum(Stack<TV> stack) {
		//variables
		String temp = "";	//temporary integer in String
		int idNum;
		
		TV topTV = stack.peek();	//get the top TV in the stack
		String id = topTV.getID();	//get ID of the top TV
		
		//get the last numbers
		for(int i = FIRST_7_ELE; i < id.length(); i++) {
			char c = id.charAt(i);
			temp += c;
		}
		
		idNum = Integer.parseInt(temp);	//convert String in to Integer
		
		return idNum;	
	}
	
	
	//method gets the first 7 elements in the ID number
	public static String tvIdEle(Stack<TV> stack) {
		//variables
		String temp = "";
		
		TV topTV = stack.peek();	//get the top TV in the stack
		String id = topTV.getID();	//get ID of the top TV
		
		//get the first 7 elements
		for(int i = 0; i < FIRST_7_ELE; i++) {
			char c = id.charAt(i);
			temp += c;
		}
		
		return temp;
	}
	
	
	//method adds a single tv to the inventory
	public static void addSingleTV(Stack<TV> stack, int topIdNum) {
		String firstPart = tvIdEle(stack);
		String secondPart = Integer.toString(topIdNum);
		String temp = firstPart+secondPart;	
		
		TV newTv = new TV(temp);	//create new TV object
		
		stack.push(newTv);	//push new TV object into stack
	}
	
	
	//method displays inventory
	public static void displayInvt(Stack<TV> stack) {
		System.out.println("The following " + stack.size() + " TV's are left in inventory:");
		stack.forEach(n -> {
			System.out.printf("%s",n);
		});
	}
	
	
	//method returns temporary sorted CustomerData list
	public static CustomerData sortedCustomerData(CustomerData customerData) {
		
		//convert current CustomerData list in to an array
		Customer[] customerArray = customerData.convertToArray();	
		
		//create a temporary CustomerData list
		CustomerData temporaryData = new CustomerData();
		
		sortAscendingOrder(customerArray, customerArray.length);
		
		//convert sorted array to a temporary CustomerData list
		for(Customer c : customerArray) {
			temporaryData.addCustomer(c);
		}
		
		return temporaryData;
	}
	
	
	//method sorts Customer array data in ASCENDING order by Account Number RECURSIVELY
	public static void sortAscendingOrder(Customer[] array, int arrayLength) {
		
		if(arrayLength <= 1) {
			return;
		}
		
		sortAscendingOrder(array, arrayLength - 1);
		
		Customer lastCustomer = array[arrayLength - 1];
		int j = arrayLength - 2;
		
		while(j >= 0 && array[j].getAccNum().compareToIgnoreCase(lastCustomer.getAccNum()) > 0) {
			array[j+1] = array[j];
			j--;
		}
		
		array[j+1] = lastCustomer;
		
	}
	
	
}	//end of class

