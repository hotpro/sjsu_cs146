package pa2;

import java.util.ArrayList;
import java.util.Arrays;

public class BST {

	protected Node root;
	private int height;

	BST() {
		root = null;
	}

	public int getHeight() {
		return getHeight(getRoot());
	}

	protected int getHeight(Node root) {

		if (root == null) {
			return 0;
		}

		int l = getHeight(root.getLeftChild());

		int r = getHeight(root.getRightChild());

		return 1 + Math.max(l, r);
	}

	public Node getRoot() {
		return root;
	}

	public Node generateBSTTree(int[] numbers) {
		if (numbers == null || numbers.length == 0) {
			return null;
		}

		Utils.println("input numbers: " + Arrays.toString(numbers));

		root = new Node(numbers[0]);

		for (int i = 1; i < numbers.length; i++) {
			Node inserted = insert(root, numbers[i]);
			Utils.println("insert: " + numbers[i]);
			root.display();
		}

		return root;
	}

	// This method return the node reference if the key is found. Returns null
	// otherwise.
	public Node find(int val) {

		if (root == null) {
			return null;
		}

		Node currentNode = root;

		while (!currentNode.isLeaf()) {
			if (currentNode.getKey() == val) {
				return currentNode;
			}

			if (val < currentNode.getKey()) {
				currentNode = currentNode.getLeftChild();
			} else {
				currentNode = currentNode.getRightChild();
			}
			if (currentNode == null) {
				return null;
			}
		}

		if (currentNode.getKey() == val) {
			return currentNode;
		}
		return null;
	}

	/**
	 *
	 * @param root
	 * @param key
	 * @return splay node
	 */
	protected Node insert(Node root, int key) {
		Node y = null;
		Node x = root;
		Node node = new Node(key);

		while (x != null) {
			y = x;
			if (x.getKey() < key) {
				x = x.getRightChild();
			} else {
				x = x.getLeftChild();
			}
		}

		node.setParent(y);

		if (y.getKey() < key) {
			y.setRightChild(node);
		} else if (y.getKey() > key){
			y.setLeftChild(node);
		} else {
			return null;
		}

		return node;
	}

	// successor is the node with smallest key in the right subtree
	public Node getSuccessor(Node element) {
		Node successor = null;
		if (element.getRightChild() != null) {
			successor = element.getRightChild();
			if (successor.getLeftChild() == null) {
				successor = element.getRightChild();
			} else {
				while (successor.getLeftChild() != null) {
					successor = successor.getLeftChild();

				}
			}
		} else {
			System.out.println("No  right tree");
			if (element.isRoot()) {
				return null;
			}
			successor = element;
			System.out.println("-- " + element.getKey());
			Node leftChild = successor.getParent().getLeftChild();
			if (leftChild == null) {
				leftChild = successor.getParent();
			}
			while (!successor.getParent().equals(root) && !leftChild.equals(successor)) {
				System.out.println("In loop");
				successor = successor.getParent();
				leftChild = successor.getParent().getLeftChild();
				if (leftChild == null) {
					leftChild = successor.getParent();
				}
			}
			if (successor.getKey() > element.getKey()) {
				successor = successor.getParent();
			} else {
				successor = null;
			}
		}
		return successor;
	}

	// predecessor is the node with biggest key in the left subtree
	public Node getPredecessor(Node element) {
		Node predecessor = null;
		if (element.getLeftChild() != null) {
			predecessor = element.getLeftChild();
			if (predecessor.getRightChild() == null) {
				predecessor = element.getLeftChild();
			} else {
				while (predecessor.getRightChild() != null) {
					predecessor = predecessor.getRightChild();

				}
			}
		} else {
			System.out.println("No  left sub tree");
			if (element.isRoot()) {
				return null;
			}
			predecessor = element;
			System.out.println("-- " + element.getKey());
			Node rightChild = predecessor.getParent().getRightChild();
			if (rightChild == null) {
				rightChild = predecessor.getParent();
			}
			while (!predecessor.getParent().equals(root) && !rightChild.equals(predecessor)) {
				System.out.println("In loop");
				predecessor = predecessor.getParent();
				rightChild = predecessor.getParent().getRightChild();
				if (rightChild == null) {
					rightChild = predecessor.getParent();
				}
			}
			if (predecessor.getParent().getKey() < predecessor.getKey()) {
				predecessor = predecessor.getParent();
			} else {
				// element is the smallest, no predecessor
				predecessor = null;
			}
		}
		return predecessor;
	}

	/**
	 *
	 * @param key
	 * @return the parent of the deleted node, null if not found.
	 */
	public Node delete(int key) {
		Node delNode = find(key);

		if (delNode == null) {
			System.out.println("Node not found");
			return null;
		}
		// case 1: It is a leaf node

		if (delNode.isLeaf()) {
			if (delNode.isRoot()) {
				root = null;
			}

			if (delNode.equals(delNode.getParent().getLeftChild())) {
				delNode.getParent().setLeftChild(null);
			} else {
				delNode.getParent().setRightChild(null);

			}
			return delNode.getParent();
		}

		// case 2: the node to be deleted has 1 child
		if (delNode.getRightChild() == null || delNode.getLeftChild() == null) {
			{
				if (delNode.isRoot()) {
					if (delNode.getLeftChild() != null) {
						root = delNode.getLeftChild();
					} else {
						root = delNode.getRightChild();
					}
					return null;
				}

				Node p1 = delNode.getParent();
				Node c1 = null;
				if (delNode.getLeftChild() != null) {
					c1 = delNode.getLeftChild();
				} else {
					c1 = delNode.getRightChild();
				}
				c1.setParent(p1);
				if (p1.getLeftChild().equals(delNode)) {
					p1.setLeftChild(c1);
				} else {
					p1.setRightChild(c1);
				}
				return delNode.getParent();
			}
		}

		// case 3: when the node to be deleted has 2 children

		if (delNode.getRightChild() != null && delNode.getLeftChild() != null) {
			Node p1 = delNode.getParent();
			Node c1 = delNode.getLeftChild();
			Node c2 = delNode.getRightChild();
			Node s1 = getSuccessor(delNode);

			if (p1.getLeftChild().equals(delNode)) {
				p1.setLeftChild(s1);
			} else {
				p1.setRightChild(s1);
			}

			s1.setParent(p1);
			c1.setParent(s1);
			s1.setLeftChild(c1);
			if (s1.equals(c2)) {
				c2.setParent(p1);
			} else {
				c2.setParent(s1);
				s1.setRightChild(c2);
			}

			return delNode.getParent();
		}

		return null;
	}

	public void printTree(int numNodes) {
		int[] array = { 50, 39, 31, 55, 14, 16, 11, 27, 10, 4 };
		for (int i = 0; i < numNodes; i++) {
			Node tmpNode = this.find(array[i]);
			try {
				System.out.print("Node: " + tmpNode.getKey() + " ");

			} catch (NullPointerException e) {
				System.out.println("Node " + array[i] + " doesnt exist.");
				continue;
			}

			try {
				System.out.print("Parent: " + tmpNode.getParent() + " ");

			} catch (NullPointerException e) {
				System.out.print("Parent: Null ");
			}
			try {
				System.out.print("RightChild: "
						+ tmpNode.getRightChild().getKey() + " ");

			} catch (NullPointerException e) {
				System.out.print("RightChild: Null ");
			}
			try {
				System.out.print("LeftChild: "
						+ tmpNode.getLeftChild().getKey() + " ");

			} catch (NullPointerException e) {
				System.out.print("LeftChild: Null ");
			}
			System.out.println();
		}
	}
	
	public void inOrderTraversal(Node n, ArrayList<Node> arrList)
	{
		if(n == null)
			return;
		inOrderTraversal(n.getLeftChild(), arrList);
		//System.out.println(n.getKey());
		arrList.add(n);
		inOrderTraversal(n.getRightChild(), arrList);
	}
	


}
