package pa2;

import java.util.Arrays;

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

	@SuppressWarnings("unused")
	public Node getRightChild() {
		if (this == null) {
			return null;
		}
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

	public void display(){
		printTree(this, 0, false, "");
	}
	private void printTree(Node root, int level, boolean isRight, String indent){
		String str = String.valueOf(root==null?"#":root.getKey());
		boolean goOn = root != null && (root.getLeftChild()!= null || root.getRightChild()!= null);

		String indentRight = indent + (isRight?"        ":"|       ");
		if(level <= 0) indentRight = "";
		if(goOn) printTree(root.getRightChild(), level+1, true, indentRight);

		if(level!=0){
			System.out.print(indent);
			System.out.println((isRight?" /":" \\") + "------" + str);
		}
		else
			System.out.println(str);

		String indentLeft = indent + (isRight?"|       ":"        ");
		if(level <= 0) indentLeft = "";
		if(goOn) printTree(root.getLeftChild(), level+1, false, indentLeft);
	}

	public static void main(String[] args) {
		int[] input = new int[11];
		Utils.readInputFromFile("pa2input.txt", input);
		int lower = input[0];
		int upper = input[1];
		int n = input[2];
		int[] avlDeletes = new int[4];
		for (int i = 0; i < avlDeletes.length; i++) {
			avlDeletes[i] = input[3 + i];
		}

		int[] splayDeletes = new int[4];
		for (int i = 0; i < splayDeletes.length; i++) {
			splayDeletes[i] = input[7 + i];
		}

		int[] numbers = Utils.createNumbers(lower, upper, n);
		numbers = new int[] {14, 5, 3, 12, 9, 18, 6, 10, 15, 11};
		AVLTree avlTree = new AVLTree();
		Node root = avlTree.generateAVLTree(numbers);
		root.display();

		for (int key : avlDeletes) {
			if (key > upper || key < lower) {
				Utils.print("key: " + key + " out of range, continue");
				continue;
			}
			avlTree.delete(key);
			avlTree.getRoot().display();
		}

		SplayTree splayTree = new SplayTree(numbers);
		for (int key : splayDeletes) {
			if (key > upper || key < lower) {
				Utils.print("key: " + key + " out of range, continue");
				continue;
			}
			splayTree.delete(key);
			splayTree.getRoot().display();
		}
	}
}