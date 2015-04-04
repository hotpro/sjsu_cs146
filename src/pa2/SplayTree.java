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
