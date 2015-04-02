package pa2;

import com.sun.prism.impl.packrect.LevelSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreePrint {

	public static void main(String[] args) {
//		int[] numbers = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
//		int[] numbers = new int[] {3, 2, 1};
//		int[] numbers = new int[] {3, 1, 2};
//		int[] numbers = new int[] {1, 3, 2};
		int[] numbers = new int[60];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i + 1;
		}

		AVLTree avlTree = new AVLTree();
		Node root = avlTree.generateAVLTree(numbers);
		TreePrint treePrint = new TreePrint();
		treePrint.print(root);


//		Random generator = new Random();
//		//int numNodes = generator.nextInt(2) + 10;
//		int numNodes = 10;
//		System.out.println("Number of nodes: " + numNodes);
//		//int[] array = new int[numNodes];
//		int[] array = {50, 39, 31, 55, 14, 16, 11, 27, 10, 4};
//		//int key = generator.nextInt(62) + 1;
//		int key;
//		BSTTree myBSTTree = new BSTTree();
//
//		for (int i = 0; i < numNodes; i++) {
//			//key = generator.nextInt(62) + 1;
//			// System.out.println("Trying to insert: " +key);
//			myBSTTree.add(array[i]);
//
//			key = array[i];
//		}
//
//		for (int i = 0; i < numNodes; i++) {
//			System.out.print(array[i] + " ");
//		}
//		System.out.println();
//
//		System.out.println("The height of the tree is : "+ myBSTTree.getHeight());
//		myBSTTree.printTree(numNodes);
//		System.out.println();
//		myBSTTree.delete(14);
//		myBSTTree.printTree(numNodes);

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

	public void print(Node root) {
		if (root == null) {
			return;
		}

		List<List<Node>> levels = new ArrayList<List<Node>>();
		preorder(root, levels, 0);

		int n = (int)Math.pow(2, 5) * 3;
		for (int i = 0; i < levels.size(); i++) {

			int step = n / (int)Math.pow(2, i + 1);
			System.out.print(i + ":" + step + " ");

			List<Node> level = levels.get(i);
			for (int j = 0; j < level.size(); j++) {
				Node node = level.get(j);
				if (j == 0) {
					int horizontalIndex = node.getHorizontalIndex();
					printSpace(step, horizontalIndex + 1);
				} else {
					Node pre = level.get(j - 1);
					printSpace(step, 2* (node.getHorizontalIndex() - pre.getHorizontalIndex()));
				}
				System.out.print(node);
			}
			System.out.println();
		}

	}

	void printSpace(int step, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < step; j++) {
				System.out.print(" ");
			}
		}
	}



	private void preorder(Node node, List<List<Node>> levels, int level) {
		if (node == null) {
			return;
		}

		List<Node> list = null;
		if (level >= levels.size()) {
			list = new ArrayList<Node>();
			levels.add(list);
		} else {
			list = levels.get(level);
		}

		list.add(node);

		if (level == 0) {
			node.setHorizontalIndex(0);
		} else {
			int phi = node.getParent().getHorizontalIndex();
			int base = 2 * (phi + 1);
			if (node == node.getParent().getLeftChild()) {
				node.setHorizontalIndex(base - 2);
			} else {
				node.setHorizontalIndex(base - 1);
			}
		}

		preorder(node.getLeftChild(), levels, level + 1);
		preorder(node.getRightChild(), levels, level + 1);
	}
}
