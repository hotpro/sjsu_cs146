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

            Splay splay = findSplay(splayNode);

            if (splay != null) {
                splay(splay);
            }
        }
    }

    private void splay(Splay splay) {
        System.out.println(splay);
        switch (splay.getType()) {
            case R_ROTATION:
                break;
            case R_ZIG_ZIG:
                break;
            case R_ZIG_ZAG:
                break;
            case L_ROTATION:;
                break;
            case L_ZIG_ZIG:
                break;
            case L_ZIG_ZAG:
                break;
        }
    }

    private Splay findSplay(Node node) {
        Node x = node;
        Node y = x.getParent();
        Node z = null;
        if (y != null) {
            z = y.getParent();
        }

        if (y == null) {
            // x is root, do not need to splay
            return null;
        }

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
