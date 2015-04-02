package pa2;


/**
 * Created by yutao on 3/29/15.
 */
public class AVLTree {

    public Node generateAVLTree(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }
        Node root = new Node(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {
            Node inserted = insert2(root, numbers[i]);
            Rotation rotation = findRotation(inserted);
            if (rotation != null) {
                Node node = rotate(rotation);
                if (node != null) {
                    root = node;
                }
            }
            root.display();
        }

        return root;
    }

    private void insert1(Node node, int n) {
        if (n > node.getKey()) {
            Node right = node.getRightChild();
            if (right != null) {
                insert1(right, n);
            } else {
                right = new Node(n);
                right.setParent(node);
                node.setRightChild(right);
            }
        } else {
            Node left = node.getLeftChild();
            if (left != null) {
                insert1(left, n);
            } else {
                left = new Node(n);
                left.setParent(node);
                node.setLeftChild(left);
            }
        }
    }

    private Node insert2(Node root, int key) {
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
        } else {
            y.setLeftChild(node);
        }

        return node;
    }

    private boolean isBalance(Node node) {
        if (node == null) {
            return true;
        }

        int r = getHeight(node.getRightChild());
        int l = getHeight(node.getLeftChild());

        return Math.abs(r - l) <= 1 && isBalance(node.getLeftChild()) && isBalance(node.getRightChild());
    }

    private Rotation findRotation(Node node) {
        Node x = node;
        Node y = null;

        if (x != null) {
            y = x.getParent();
        }

        Node z = null;
        if (y != null) {
            z = y.getParent();
        }

        while (z != null) {
            if (!isBalance(z)) {
                Rotation.Type type = null;
                if (y.getRightChild() == x) {
                    if (z.getRightChild() == y) {
                        type = Rotation.Type.L;
                    } else {
                        type = Rotation.Type.LR;
                    }
                } else {
                    if (z.getRightChild() == y) {
                        type = Rotation.Type.RL;
                    } else {
                        type = Rotation.Type.R;
                    }
                }
                return new Rotation(type, x, y, z);
            }

            x = y;
            y = z;
            z = z.getParent();
        }

        return null;
    }

    private int getHeight(Node root) {

        if (root == null) {
            return 0;
        }

        int l = getHeight(root.getLeftChild());

        int r = getHeight(root.getRightChild());

        return r > l ? r + 1 : l + 1;
    }

    private Node rotate(Rotation rotation) {
        System.out.println(rotation);
        switch (rotation.type) {
            case L:
                return lRotate(rotation.getX(), rotation.getY(), rotation.getZ());
            case LR:
                return lrRotate(rotation.getX(), rotation.getY(), rotation.getZ());
            case R:
                return rRotate(rotation.getX(), rotation.getY(), rotation.getZ());
            case RL:
                return rlRotate(rotation.getX(), rotation.getY(), rotation.getZ());
        }

        return null;

    }

    private Node lRotate(Node x, Node y, Node z) {
        Node root = null;

        Node zP = z.getParent();
        if (zP == null) {
            root = y;
        } else {
            if (zP.getRightChild() == z) {
                zP.setRightChild(y);
            } else {
                zP.setLeftChild(y);
            }
        }
        y.setParent(zP);

        if (y.getLeftChild() != null) {
            z.setRightChild(y.getLeftChild());
            y.getLeftChild().setParent(z);
        } else {
            z.setRightChild(null);
        }

        y.setLeftChild(z);
        z.setParent(y);

        return root;
    }

    private Node rRotate(Node x, Node y, Node z) {
        Node root = null;

        Node zP = z.getParent();
        if (zP == null) {
            root = y;
        } else {
            if (zP.getLeftChild() == z) {
                zP.setLeftChild(y);
            } else {
                zP.setRightChild(y);
            }
        }
        y.setParent(null);

        if (y.getRightChild() != null) {
            z.setLeftChild(y.getRightChild());
            y.getRightChild().setParent(z);
        } else {
            z.setLeftChild(null);
        }

        y.setRightChild(z);
        z.setParent(y);

        return root;
    }

    private Node lrRotate(Node x, Node y, Node z) {
        Node root = null;

        Node zp = z.getParent();
        if (zp == null) {
            root = x;
        } else {
            if (zp.getLeftChild() == z) {
                zp.setLeftChild(x);
            } else {
                zp.setRightChild(x);
            }
        }
        x.setParent(zp);

        if (x.getLeftChild() != null) {
            y.setRightChild(x.getLeftChild());
            x.getLeftChild().setParent(y);
        } else {
            y.setRightChild(null);
        }

        x.setLeftChild(y);
        y.setParent(x);

        if (x.getRightChild() != null) {
            z.setLeftChild(y.getRightChild());
            y.getRightChild().setParent(z);
        } else {
            z.setLeftChild(null);
        }

        x.setRightChild(z);
        z.setParent(x);

        return root;

    }

    private Node rlRotate(Node x, Node y, Node z) {

        Node root = null;

        Node zp = z.getParent();
        if (zp == null) {
            root = x;
        } else {
            if (zp.getLeftChild() == z) {
                zp.setLeftChild(x);
            } else {
                zp.setRightChild(x);
            }
        }
        x.setParent(zp);

        Node xl = x.getLeftChild();
        z.setRightChild(xl);
        if (xl != null) {
            xl.setParent(z);
        }

        x.setLeftChild(z);
        z.setParent(x);

        Node xr = x.getRightChild();
        y.setLeftChild(xr);
        if (xr != null) {
            xr.setParent(y);
        }

        x.setRightChild(y);
        y.setParent(x);

        return root;

    }

    private void delete(int key) {

    }
}
