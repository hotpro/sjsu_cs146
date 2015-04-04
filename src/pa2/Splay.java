package pa2;

/**
 * Created by yutao on 4/2/15.
 */
public class Splay {
    Node x;
    Node y;
    Node z;
    Type type;

    enum Type {
        L_ROTATION,
        R_ROTATION,
        R_ZIG_ZIG,
        L_ZIG_ZIG,
        R_ZIG_ZAG,
        L_ZIG_ZAG,
    }

    public Splay(Node x, Node y, Node z, Type type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    public Node getX() {
        return x;
    }

    public Node getY() {
        return y;
    }

    public Node getZ() {
        return z;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Splay{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", type=" + type.name() +
                '}';
    }
}
