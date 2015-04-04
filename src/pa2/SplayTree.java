package pa2;

import com.sun.org.apache.regexp.internal.REUtil;

import java.util.Arrays;

/**
 * Created by yutao on 4/2/15.
 */
public class SplayTree extends BST {
    public SplayTree(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return;
        }

        System.out.println(Arrays.toString(numbers));

        root = new Node(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {
            Node splayNode = insert(root, numbers[i]);
            System.out.println("insert: " + numbers[i]);
            root.display();
            splayToRoot(splayNode);
        }
    }


    @Override
    public Node delete(int key) {
        System.out.println("splay tree delete: " + key);
        Node splayNode = splayDelete(key);
        if (splayNode == null) {
            System.out.println("Tree is empty");
            return null;
        }

        splayToRoot(splayNode);
        return splayNode;
    }

    /**
     *
     * @param key
     * @return splay node after delete. The parent of the deleted node if node found, the parent of the external node
     * if not found. null if tree is empty.
     */
    private Node splayDelete(int key) {
        if (root == null) {
            return null;
        }

        Node currentNode = root;
        Node parent = null;

        while (currentNode != null) {
            if (currentNode.getKey() == key) {
                break;
            }

            parent = currentNode;

            if (key < currentNode.getKey()) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
        }

        if (currentNode == null) {
            return parent;
        }

        Node delNode = currentNode;

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
            return deleteWithPredecessor(delNode);
        }

        return parent;
    }

    private Node deleteWithPredecessor(Node delNode) {
        Node parent = delNode.getParent();
        Node leftChild = delNode.getLeftChild();
        Node rightChild = delNode.getRightChild();
        Node predecessor = getPredecessor(delNode);
        Node predecessorLeftChild = predecessor.getLeftChild();
        Node predecessorParent = predecessor.getParent();

        // swap
        if (parent != null) {
            if (parent.getLeftChild().equals(delNode)) {
                parent.setLeftChild(predecessor);
            } else {
                parent.setRightChild(predecessor);
            }
        }
        predecessor.setParent(parent);

        rightChild.setParent(predecessor);
        predecessor.setRightChild(rightChild);

        if (predecessor.equals(leftChild)) {
            leftChild.setParent(parent);
            return predecessor;
        } else {
            leftChild.setParent(predecessor);
            predecessor.setLeftChild(leftChild);

            if (predecessorLeftChild != null) {
                predecessorLeftChild.setParent(predecessorParent);
            }
            predecessorParent.setRightChild(predecessorLeftChild);
        }

        return predecessorParent;
    }

    private Node deleteWithSuccessor(Node delNode) {
        Node parent = delNode.getParent();
        Node leftChild = delNode.getLeftChild();
        Node rightChild = delNode.getRightChild();
        Node successor = getSuccessor(delNode);
        Node successorRightChild = successor.getRightChild();
        Node successorParent = successor.getParent();


        if (parent.getLeftChild().equals(delNode)) {
            parent.setLeftChild(successor);
        } else {
            parent.setRightChild(successor);
        }
        successor.setParent(parent);

        leftChild.setParent(successor);
        successor.setLeftChild(leftChild);

        if (successor.equals(rightChild)) {
            rightChild.setParent(parent);
            return successor;
        } else {
            rightChild.setParent(successor);
            successor.setRightChild(rightChild);

            if (successorRightChild != null) {
                successorRightChild.setParent(successorParent);
            }
            successorParent.setLeftChild(successorRightChild);
        }

        return successorParent;
    }

    /**
     *
     * @param val
     * @return the node reference if the key is found. Returns the parent of external node otherwise.
     * null if tree is empty.
     */
    @Override
    public Node find(int val) {

        if (root == null) {
            return null;
        }

        Node currentNode = root;
        Node parent = null;

        while (currentNode != null) {
            if (currentNode.getKey() == val) {
                return currentNode;
            }

            parent = currentNode;

            if (val < currentNode.getKey()) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
        }

        return parent;
    }

    private void splayToRoot(Node node) {
        Splay splay = findSplay(node);

        while (splay != null) {
            splay(splay);
            splay = findSplay(node);
            getRoot().display();
        }
        root = node;
        getRoot().display();
    }

    private void splay(Splay splay) {
        Node x = splay.getX();
        Node y = splay.getY();
        Node z = splay.getZ();
        Splay.Type type = splay.getType();

        System.out.println(splay);
        switch (splay.getType()) {
            case R_ROTATION: {
                if (x.getRightChild() != null) {
                    x.getRightChild().setParent(y);
                }
                y.setLeftChild(x.getRightChild());

                x.setRightChild(y);
                y.setParent(x);

                x.setParent(null);

                break;
            }
            case R_ZIG_ZIG: {

                Node zp = z.getParent();
                if (zp != null) {
                    if (zp.getRightChild() == z) {
                        zp.setRightChild(x);
                    } else {
                        zp.setLeftChild(x);
                    }

                }
                x.setParent(zp);

                if (x.getRightChild() != null) {
                    x.getRightChild().setParent(y);
                }
                y.setLeftChild(x.getRightChild());

                x.setRightChild(y);
                y.setParent(x);

                if (y.getRightChild() != null) {
                    y.getRightChild().setParent(z);
                }
                z.setLeftChild(y.getRightChild());

                y.setRightChild(z);
                z.setParent(y);

                break;
            }
            case R_ZIG_ZAG: {
                Node zp = z.getParent();
                if (zp != null) {
                    if (zp.getRightChild() == z) {
                        zp.setRightChild(x);
                    } else {
                        zp.setLeftChild(x);
                    }
                }
                x.setParent(zp);

                if (x.getRightChild() != null) {
                    x.getRightChild().setParent(y);
                }
                y.setLeftChild(x.getRightChild());

                x.setRightChild(y);
                y.setParent(x);

                if (x.getLeftChild() != null) {
                    x.getLeftChild().setParent(z);
                }
                z.setRightChild(x.getLeftChild());

                x.setLeftChild(z);
                z.setParent(x);

                break;
            }
            case L_ROTATION: {
                if (x.getLeftChild() != null) {
                    x.getLeftChild().setParent(y);
                }
                y.setRightChild(x.getLeftChild());

                x.setLeftChild(y);
                y.setParent(x);

                x.setParent(null);
                break;
            }
            case L_ZIG_ZIG: {
                Node zp = z.getParent();
                if (zp != null) {
                    if (zp.getRightChild() == z) {
                        zp.setRightChild(x);
                    } else {
                        zp.setLeftChild(x);
                    }

                }
                x.setParent(zp);

                if (x.getLeftChild() != null) {
                    x.getLeftChild().setParent(y);
                }
                y.setRightChild(x.getLeftChild());

                x.setLeftChild(y);
                y.setParent(x);

                if (y.getLeftChild() != null) {
                    y.getLeftChild().setParent(z);
                }
                z.setRightChild(y.getLeftChild());

                y.setLeftChild(z);
                z.setParent(y);
                break;
            }
            case L_ZIG_ZAG: {
                Node zp = z.getParent();
                if (zp != null) {
                    if (zp.getRightChild() == z) {
                        zp.setRightChild(x);
                    } else {
                        zp.setLeftChild(x);
                    }
                }
                x.setParent(zp);

                if (x.getRightChild() != null) {
                    x.getRightChild().setParent(z);
                }
                z.setLeftChild(x.getRightChild());

                x.setRightChild(z);
                z.setParent(x);

                if (x.getLeftChild() != null) {
                    x.getLeftChild().setParent(y);
                }
                y.setRightChild(x.getLeftChild());

                x.setLeftChild(y);
                y.setParent(x);
                break;
            }
        }
    }

    private Splay findSplay(Node node) {
        Node x = node;
        Node y = x.getParent();
        Node z = null;

        if (y == null) {
            // x is root, do not need to splay
            return null;
        }

        z = y.getParent();

        Splay.Type type = null;
        if (z == null) {
            // x doesn't have grandparent, one level rotation
            if (x == y.getLeftChild()) {
                type = Splay.Type.R_ROTATION;
            } else {
                type = Splay.Type.L_ROTATION;
            }
            return new Splay(x, y, z, type);
        }

        if (x == y.getLeftChild()) {
            if (y == z.getLeftChild()) {
                type = Splay.Type.R_ZIG_ZIG;
            } else {
                type = Splay.Type.R_ZIG_ZAG;
            }

        } else {
            if (y == z.getLeftChild()) {
                type = Splay.Type.L_ZIG_ZAG;
            } else {
                type = Splay.Type.L_ZIG_ZIG;
            }
        }

        return new Splay(x, y, z, type);
    }
}
