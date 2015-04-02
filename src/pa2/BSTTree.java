package pa2;

public class BSTTree {

	Node root;
	int height;

	BSTTree() {
		root = null;
	}

	public int getHeight() {
		return height;
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

	// Inserts a node c as a child of node p. Sets the correct values for parent
	// and left/Right child fields of both the node.
	public void insert(Node p, Node c) {
		// Inserting on a empty tree
		if (p == null) {
			p = c;
		} else {
			c.setParent(p);
			if (p.getKey() < c.getKey()) {// Inserting the node as a right
											// child;

				p.setRightChild(c);
			} else {
				// Inserting the node as a left child
				p.setLeftChild(c);
			}
		}
	}

	// Traverses the BST and inserts a node with key equal to the given value at
	// the correct location.
	public void add(int key) {

		if (root == null) {// The tree is empty while trying to add a key);
			root = new Node(key);
			return;
		}

		Node childNode = new Node(key);
		// Traverse and find the parent node.
		Node parent = root;
		while (!parent.isLeaf()) { // System.out.println("5. The node being checked "
									// + parent.getKey() is not a leaf while
									// trying to add a key
			if (parent.getKey() == key) {
				// Cannot insert because the key of the parent node is same as
				// the key of the new node key);
				return;
			}

			if (key < parent.getKey()) {
				if (parent.getLeftChild() != null) {
					parent = parent.getLeftChild();
				} else {
					insert(parent, childNode);
					return;
				}
			} else {
				if (parent.getRightChild() != null) {
					parent = parent.getRightChild();
				} else {
					insert(parent, childNode);
					return;
				}
			}
		}

		if (parent.getKey() == key) {
			// Cannot insert at this leaf node because the key of the leaf node
			// is same as the key of the new node");
		}
		// Inserting the node at leaf
		insert(parent, childNode);
		height++;
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
			Node lc = successor.getParent().getLeftChild();
			if (lc == null) {
				lc = successor.getParent();
			}
			while (!successor.getParent().equals(root) && !lc.equals(successor)) {
				System.out.println("In loop");
				successor = successor.getParent();
				lc = successor.getParent().getLeftChild();
				if (lc == null) {
					lc = successor.getParent();
				}
			}
			successor = successor.getParent();
		}
		return successor;
	}

	public void delete(int key) {
		Node delNode = find(key);

		if (delNode == null) {
			System.out.println("Node not found");
			return;
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
			return;
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
					return;
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
			}
			return;
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

		}
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

}
