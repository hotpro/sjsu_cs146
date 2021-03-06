package pa2;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

public class Node {
    private int key;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public void setHorizontalIndex(int horizontalIndex) {
        this.horizontalIndex = horizontalIndex;
    }

    int horizontalIndex;

    public Node(int key) {
        this.key = key;
        this.leftChild = null;
        this.parent = null;
        this.rightChild = null;

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    @SuppressWarnings("unused")
    public Node getRightChild() {
        if (this == null) {
            return null;
        }
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public boolean isLeaf() {
        if (this.getLeftChild() == null && this.getRightChild() == null)
            return true;
        else
            return false;
    }

    public boolean isRoot() {
        if (this.getParent() == null)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }

    public boolean equals(Node element) {

        if (element == null) {
            return false;
        } else {
            if (key == element.getKey())
                return true;
            else
                return false;
        }
    }

    public void display() {
        printTree(this, 0, false, "");
    }

    private void printTree(Node root, int level, boolean isRight, String indent) {
        String str = String.valueOf(root == null ? "#" : root.getKey());
        boolean goOn = root != null && (root.getLeftChild() != null || root.getRightChild() != null);

        String indentRight = indent + (isRight ? "        " : "|       ");
        if (level <= 0) indentRight = "";
        if (goOn) printTree(root.getRightChild(), level + 1, true, indentRight);

        if (level != 0) {
            Utils.print(indent);
            Utils.println((isRight ? " /" : " \\") + "------" + str);
        } else
            Utils.println(str);

        String indentLeft = indent + (isRight ? "|       " : "        ");
        if (level <= 0) indentLeft = "";
        if (goOn) printTree(root.getLeftChild(), level + 1, false, indentLeft);
    }
}