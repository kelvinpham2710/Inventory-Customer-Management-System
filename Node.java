/*
 * The program creates a class Node with one constructor, get and set methods, hold specific nodes for the binary tree
 */


public class Node {
	
	private TVType type;
	private Node left;
	private Node right;
	
	public Node(TVType t) {
		type = t;
		left = null;
		right = null;
	}
	
	public void setTVType(TVType t) {
		type = t;
	}
	
	public void setLeft(Node l) {
		left = l;
	}
	
	public void setRight(Node r) {
		right = r;
	}
	
	public TVType getTVType() {
		return type;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
}
