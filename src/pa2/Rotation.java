package pa2;

/**
 * Created by yutao on 4/1/15.
 */
public class Rotation {
    Type type;

    private Node x;
    private Node y;
    private Node z;

    public Rotation(Type type, Node x, Node y, Node z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Type getType() {
        return type;
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

    enum Type {
        L,
        R,
        LR,
        RL,
    }

    @Override
    public String toString() {
        return type.name() + " rotation: nodes "
                + x.getKey() + ", "
                + y.getKey() + ", "
                + z.getKey();
    }
}
