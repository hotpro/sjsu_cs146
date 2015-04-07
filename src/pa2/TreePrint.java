package pa2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TreePrint {

	public static void printGap(int gap) {
		for (int i = 0; i < gap; i++) {
			Utils.print(" ");
		}
	}

	public static void printBST(BST myBST, int numNodes, int[] array) {
		for (int i = 0; i < numNodes; i++) {
			Utils.print(array[i] + " ");
		}
		Utils.println("");

		Utils.println("The height of the tree is : " + myBST.getHeight());
		Node root = myBST.getRoot();

		// Inorder Traversal
		ArrayList<Node> myList = new ArrayList<Node>();

		myBST.inOrderTraversal(root, myList);
		Utils.println("Inorder Traversal of the tree: ");
		Iterator<Node> itr = myList.iterator();
		while (itr.hasNext()) {
			Utils.print(itr.next() +" ");
		}
		Utils.println("");

		int h = myBST.getHeight();
		int maxNodes = (int) (Math.pow(2, h) - 1);
		Node[] nodeArray = new Node[maxNodes];
		nodeArray[0] = root;
		for (int i = 1, j = 0; i < maxNodes; j++) {
			Node n = nodeArray[j];
			Node lc = null;
			Node rc = null;
			// System.out.println("Does it enter the loop");
			if (n != null) {
				lc = n.getLeftChild();
			}
			if (n != null) {
				rc = n.getRightChild();
			}
			nodeArray[i++] = lc;
			nodeArray[i++] = rc;
		}

		Utils.println("----------------------------");

		int startGap = (int) (Math.pow(2, h - 1) - 1);
		printGap(startGap);
		int level = 1;
		for (int i = 0; i < maxNodes; i++) {
			if (i != 0) {
				int tmp = i & (i + 1);
				if (tmp == 0) {
					Utils.println("\n");
					h--;
					startGap = (int) (Math.pow(2, h - 1) - 1);
					printGap(startGap);
				}
			}
			Node n = nodeArray[i];
			int nodeGap = (int) (Math.pow(2, h) - 1);
			if (n != null) {
				Utils.print(n.getKey());
			} else {
				Utils.print(" ");
			}
			printGap(nodeGap);

		}

		Utils.println("");
		Utils.println("----------------------------");
	}


//	private static final int LOWEST_LEVEL_GAP = 2;
//	private static final int NODE_WIDTH = 2;
//
//	public static void print(BST bst) {
//		Node root = bst.getRoot();
//		int height = bst.getHeight();
//		if (root == null) {
//			return;
//		}
//
//		List<List<Node>> levels = new ArrayList<List<Node>>();
//		preorder(root, levels, 0);
//
//		// lowest level width
//		int n = ((int)Math.pow(2, height - 1) * (NODE_WIDTH + LOWEST_LEVEL_GAP)) - LOWEST_LEVEL_GAP;
//		for (int i = 0; i < levels.size(); i++) {
//
//			int step = (n - NODE_WIDTH * ((int)Math.pow(2, i + 1) - 1)) / (int)Math.pow(2, i+ 1);
//
//			System.out.printf("%2d:%2d", i, step);
//
//			List<Node> level = levels.get(i);
//			for (int j = 0; j < level.size(); j++) {
//				Node node = level.get(j);
//				if (j == 0) {
//					int horizontalIndex = node.getHorizontalIndex();
//					int spaceNumber = step + horizontalIndex * 2 * step + 2 * horizontalIndex;
//					printSpace(spaceNumber);
//				} else {
//					Node pre = level.get(j - 1);
//					int diff = node.getHorizontalIndex() - pre.getHorizontalIndex();
//					int spaceNumber = step * 2 * diff + 2 * (diff - 1);
//					printSpace(spaceNumber);
//				}
//				System.out.printf("%2d", node.getKey());
//			}
//			System.out.println();
//		}
//
//	}
//
//	private static void printSpace(int step, int n) {
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < step; j++) {
//				System.out.print(".");
//			}
//		}
//	}
//
//	private static void printSpace(int n) {
//		for (int i = 0; i < n; i++) {
//			System.out.print(".");
//		}
//	}
//
//
//
//	private static void preorder(Node node, List<List<Node>> levels, int level) {
//		if (node == null) {
//			return;
//		}
//
//		List<Node> list = null;
//		if (level >= levels.size()) {
//			list = new ArrayList<Node>();
//			levels.add(list);
//		} else {
//			list = levels.get(level);
//		}
//
//		list.add(node);
//
//		if (level == 0) {
//			node.setHorizontalIndex(0);
//		} else {
//			int indexp = node.getParent().getHorizontalIndex();
//			int base = 2 * (indexp + 1);
//			if (node == node.getParent().getLeftChild()) {
//				node.setHorizontalIndex(base - 2);
//			} else {
//				node.setHorizontalIndex(base - 1);
//			}
//		}
//
//		preorder(node.getLeftChild(), levels, level + 1);
//		preorder(node.getRightChild(), levels, level + 1);
//	}

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

		Utils.println("=======================BST START=============================");
		BST bst = new BST();
		bst.generateBSTTree(numbers);
		bst.getRoot().display();
		printBST(bst, n, numbers);
		Utils.println("=======================BST END=============================");

		Utils.println("=======================AVL START=============================");
		AVLTree avlTree = new AVLTree();
		Node root = avlTree.generateAVLTree(numbers);
		root.display();
		printBST(avlTree, n, numbers);
//		print(avlTree);

		for (int key : avlDeletes) {
			if (key > upper || key < lower) {
				Utils.println("key: " + key + " out of range, continue");
				continue;
			}
			avlTree.delete(key);
			avlTree.getRoot().display();
			printBST(avlTree, n, numbers);
		}
		Utils.println("=======================AVL END=============================");

		Utils.println("=======================SPLAY START=============================");
		SplayTree splayTree = new SplayTree(numbers);
		printBST(splayTree, n, numbers);
		for (int key : splayDeletes) {
			if (key > upper || key < lower) {
				Utils.println("key: " + key + " out of range, continue");
				continue;
			}
			splayTree.delete(key);
			splayTree.getRoot().display();
			printBST(splayTree, n, numbers);
		}
		Utils.println("=======================SPLAY END=============================");
	}

	public void printTree(Node root) {

	}

	public void printTree(Node root, boolean isRight, String indent) {
		if (root == null) {
			System.out.println("null");
			return;
		}

		if (root.getRightChild() != null) {

		}

	}

}
