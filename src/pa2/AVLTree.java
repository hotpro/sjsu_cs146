package pa2;

/**
 * Created by yutao on 3/29/15.
 */
public class AVLTree {

    Node generateAVLTree(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }
        Node root = new Node(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {
            insert(root, numbers[i]);
        }

        return root;
    }

    private void insert(Node node, int n) {
        if (n > node.getValue()) {
            Node right = node.getRight();
            if (right != null) {
                insert(right, n);
            } else {
                right = new Node(n);
                node.setRight(right);
            }
        } else {
            Node left = node.getLeft();
            if (left != null) {
                insert(left, n);
            } else {
                left = new Node(n);
                node.setLeft(left);
            }
        }
    }
}
