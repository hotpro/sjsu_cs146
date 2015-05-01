package pa3;

/**
 * Created by yutao on 4/29/15.
 */
public class Entry {
    private String value;
    private Entry next;

    public Entry(String value, Entry next) {
        this.value = value;
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Entry getNext() {
        return next;
    }

    public void setNext(Entry next) {
        this.next = next;
    }
}
