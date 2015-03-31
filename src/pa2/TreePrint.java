package pa2;

import java.util.Random;

public class TreePrint {

	public static void main(String[] args) {

		Random generator = new Random();
		//int numNodes = generator.nextInt(2) + 10;
		int numNodes = 10;
		System.out.println("Number of nodes: " + numNodes);
		//int[] array = new int[numNodes];
		int[] array = {50, 39, 31, 55, 14, 16, 11, 27, 10, 4};
		//int key = generator.nextInt(62) + 1;
		int key;
		BST myBST = new BST();

		for (int i = 0; i < numNodes; i++) {
			//key = generator.nextInt(62) + 1;
			// System.out.println("Trying to insert: " +key);
			myBST.add(array[i]);

			key = array[i];
		}

		for (int i = 0; i < numNodes; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();

		System.out.println("The height of the tree is : "+myBST.getHeight());
		myBST.printTree(numNodes);
		System.out.println();
		myBST.delete(14);
		myBST.printTree(numNodes);

//		Node tempNode = myBST.find(14);
//		System.out.println(myBST.getSuccessor(tempNode).getKey());
//		System.out.println();
//		tempNode = myBST.find(11);
//		System.out.println(myBST.getSuccessor(tempNode).getKey());
//		System.out.println();
//		tempNode = myBST.find(27);
//		System.out.println(myBST.getSuccessor(tempNode).getKey());
//		tempNode = myBST.find(50);
//		System.out.println(myBST.getSuccessor(tempNode).getKey());

	}
}
