package pa2;

public class Node {
	private int key;
	private Node leftChild;
	private Node rightChild;
	private Node parent;

	public Node(int key) {
		this.key = key;
		this.leftChild = null;
		this.parent = null;
		this.rightChild = null;

	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	

	public boolean isLeaf() {
		if (this.getLeftChild() == null && this.getRightChild() == null)
			return true;
		else
			return false;
	}
	
	public boolean isRoot() {
		if (this.getParent() == null)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString() {
		return "" + key;
	}
	public boolean equals(Node element) {
				
		if (element == null) {
			return false;
		} else {
			if(key == element.getKey())
				return true;
			else
				return false;
		}
	}
}