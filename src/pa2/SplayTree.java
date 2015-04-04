package pa2;

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
            System.out.println("insert: " + splayNode);
            root.display();
            balanceAfterInsert(splayNode);

            root.display();
        }
    }

    /**
     *
     * @param key
     * @return splay node after delete. The parent of the deleted node if node found, the parent of the external node
     * if not found.
     */
    private Node splayDelete(int key) {
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

    /**
     *
     * @param val
     * @return the node reference if the key is found. Returns the parent of external node otherwise.
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

    @Override
    public Node delete(int key) {
        return splayDelete(key);
    }

    private void balanceAfterInsert(Node node) {
        Splay splay = findSplay(node);

        while (splay != null) {
            splay(splay);
            splay = findSplay(node);
        }
        root = node;
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
                x.setParent(z);

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
                    if (zp.getRightChild() != null) {
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
                x.setParent(z);

                if (x.getLeftChild() != null) {
                    x.getLeftChild().setParent(y);
                }
                y.setRightChild(x.getRightChild());

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
                    if (zp.getRightChild() != null) {
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
