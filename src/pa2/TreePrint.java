package pa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Iterator;

public class TreePrint {

	public static void printGap(int gap) {
		for (int i = 0; i < gap; i++) {
			System.out.print(" ");
		}
	}

	public static void main(String[] args) {

		Random generator = new Random();
		int numNodes = generator.nextInt(2) + 10;
		System.out.println("Number of nodes: " + numNodes);
		int[] array = new int[numNodes];
		int key;
		BST myBST = new BST();

		// One for loop to store numbers in array

		// Generate the random numbers nd store in the array
		for (int i = 0; i < numNodes; i++) {

			// assume the value already exists in the array
			boolean isExists = true;
			while (isExists) {
				key = generator.nextInt(62) + 1;
				// Search if the value exists in the array.
				isExists = false;
				for (int j = 0; j < numNodes; j++) {
					if (key == array[j]) {
						isExists = true;
						break;
					}
				}
				if (isExists == false) {
					array[i] = key;
				}
			}
		}

		for (int i = 0; i < numNodes; i++) {
			myBST.add(array[i]);

			// This line only for testing.
			// key = array[i];
		}

		for (int i = 0; i < numNodes; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();

		System.out.println("The height of the tree is : " + myBST.getHeight());
		// myBST.printTree(numNodes);
		// System.out.println();
		// myBST.delete(array[5]);
		// myBST.printTree(numNodes);

		// Node tempNode = myBST.find(14);
		// System.out.println(myBST.getSuccessor(tempNode).getKey());
		// System.out.println();
		// tempNode = myBST.find(11);
		// System.out.println(myBST.getSuccessor(tempNode).getKey());
		// System.out.println();
		// tempNode = myBST.find(27);
		// System.out.println(myBST.getSuccessor(tempNode).getKey());
		// tempNode = myBST.find(50);
		// System.out.println(myBST.getSuccessor(tempNode).getKey());
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
}
