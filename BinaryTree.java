/*
 * The program creates a class BinaryTree with default constructor, get and set methods
 * hold all the methods used to create, traverse and find an element in binary tree
 */


public class BinaryTree {
	
	private Node root;
	private static boolean isFound = false;
	private static int counter;
	
	//default constructor
	public BinaryTree() {
		root = null;
	}
	
	public void setRoot(Node r) {
		root = r;
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void resetFlag() {
		isFound = false;
	}
	
	public void insertNode(TVType type) {
		Node newNode = new Node(type);
		boolean flag = false;
		
		//if tree is empty, insert first element
		if(root == null) {
			root = newNode;
			return;
		}
		
		else {
			Node currentRoot = root, parent = null;
			
			while(!flag) {
				parent = currentRoot;
				
				if(type.getPrice() < currentRoot.getTVType().getPrice()) {
					currentRoot = currentRoot.getLeft();
					if(currentRoot == null) {
						parent.setLeft(newNode);
						flag = true;
					}
				}
				else {
					currentRoot = currentRoot.getRight();
					if(currentRoot == null) {
						parent.setRight(newNode);
						flag = true;
					}
				}
				
			}//end of while
			
			return;
			
		}//end of else
		
	}	
	
	public void inorderTraversal(Node root) {
		if(root != null) {
			inorderTraversal(root.getLeft());
			counter++;
			System.out.println(root.getTVType().toString(counter));
			inorderTraversal(root.getRight());
		}
	}
	
	
	public TVType searchNode(String brand, String model) {
		
		resetFlag();	//reset 'isFound' to false
		return search(root, brand, model);
		
		/*
		if(root == null) {	//empty tree
			return null;	//return empty node
		}
		
		else {
			Node currentRoot = root;
			
			while(currentRoot != null) {
				
				int brandCompare = brand.compareToIgnoreCase(currentRoot.getTVType().getBrand());
				int modelCompare = model.compareToIgnoreCase(currentRoot.getTVType().getModel());
				
				if(brandCompare == 0 && modelCompare == 0) {
					isFound = true;
					return currentRoot.getTVType();
				}
				else if(brandCompare > 0 || (brandCompare == 0 && modelCompare > 0)) {
					currentRoot = currentRoot.getRight();
;				}
				else if(brandCompare < 0 || (brandCompare == 0 && modelCompare < 0)) {
					currentRoot = currentRoot.getLeft();
				}
				
			}//end of while
						
			return null;
		}//end of else
		*/
	}
	
	private TVType search(Node root, String brand, String model) {
		
		TVType tempTV = null;
		
		if(root == null) {	//empty tree
			return null;	//return empty node
		}
		else {
			
			tempTV = root.getTVType();
			if(brand.equalsIgnoreCase(root.getTVType().getBrand()) && model.equalsIgnoreCase(root.getTVType().getModel())) {
				isFound = true;
				return tempTV;
			}
			else {
				tempTV = null;
			}
			
			//search in left subtree
			if(isFound == false && root.getLeft() != null) {
				tempTV = search(root.getLeft(), brand, model);
			}
			
			//search in right subtree
			if(isFound == false && root.getRight() != null) {
				tempTV = search(root.getRight(), brand, model);
			}
			
		}
		
		return tempTV;	
		
	}
	
}
