package pa2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TreePrint {

	public static void printGap(int gap) {
		for (int i = 0; i < gap; i++) {
			System.out.print(" ");
		}
	}

	public static void printBST(BST myBST, int numNodes, int[] array) {
		for (int i = 0; i < numNodes; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();

		System.out.println("The height of the tree is : " + myBST.getHeight());
		Node root = myBST.getRoot();

		// Inorder Traversal
		ArrayList<Node> myList = new ArrayList<Node>();

		myBST.inOrderTraversal(root, myList);
		System.out.println("Inorder Traversal of the tree: ");
		Iterator<Node> itr = myList.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next() +" ");
		}
		System.out.println();

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

		System.out.println("----------------------------");

		int startGap = (int) (Math.pow(2, h - 1) - 1);
		printGap(startGap);
		int level = 1;
		for (int i = 0; i < maxNodes; i++) {
			if (i != 0) {
				int tmp = i & (i + 1);
				if (tmp == 0) {
					System.out.println("\n");
					h--;
					startGap = (int) (Math.pow(2, h - 1) - 1);
					printGap(startGap);
				}
			}
			Node n = nodeArray[i];
			int nodeGap = (int) (Math.pow(2, h) - 1);
			if (n != null) {
				System.out.print(n.getKey());
			} else {
				System.out.print(" ");
			}
			printGap(nodeGap);

		}

		System.out.println();
		System.out.println("----------------------------");

		root.display();

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
//		numbers = new int[] {14, 5, 3, 12, 9, 18, 6, 10, 15, 11};
		AVLTree avlTree = new AVLTree();
		Node root = avlTree.generateAVLTree(numbers);
		root.display();
		printBST(avlTree, n, numbers);

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
