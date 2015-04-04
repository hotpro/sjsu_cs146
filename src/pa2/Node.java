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
//		int[] numbers = Utils.createNumbers(1, 20, 10);
		int[] numbers = new int[] {19, 0, 3, 18, 11, 10, 2, 9, 14, 13};

//		int[] numbers = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
//		int[] numbers = new int[] {1, 2, 3, 4};
//		int[] numbers = new int[] {3, 2, 1};
//		int[] numbers = new int[] {3, 1, 2};
//		int[] numbers = new int[] {1, 3, 2};
//		AVLTree avlTree = new AVLTree();
//		Node root = avlTree.generateAVLTree(numbers);
//		root.display();
//
//		avlTree.delete(19);
//		avlTree.getRoot().display();
//		avlTree.delete(14);
//		avlTree.getRoot().display();
//		avlTree.delete(13);
//		avlTree.getRoot().display();

		SplayTree splayTree = new SplayTree(numbers);
//		test(splayTree.getRoot(), splayTree);
		splayTree.delete(2);
		splayTree.getRoot().display();
	}

	public static void test(Node node, BST bst) {
		if (node == null) {
			return;
		}
		System.out.println("Node " + node+ " predecessor: " + bst.getPredecessor(node));
		test(node.getLeftChild(), bst);
		test(node.getRightChild(), bst);
	}
}