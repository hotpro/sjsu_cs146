package pa3;

import java.util.Arrays;

/**
 * Created by yutao on 4/18/15.
 */
public class HashTable {

    Entry[] table;
    int sizeIndex = 2500;
    int count;

    public HashTable() {
        table = new Entry[Prime.p[sizeIndex]];
    }

    public void add(String s) {
        String convertedStr = convert(s);
        int h = hash(convertedStr.hashCode());
        int index = h % table.length;
        Entry entry = table[index];
        table[index] = new Entry(s, entry);
        count++;

        int size = table.length;
        double loadFactor = (double) count / Prime.p[sizeIndex];
        if (loadFactor >= 0.5) {
            rehash();
        }
    }

    public Result search(String s) {
        Result result = new Result();
        String convertedStr = convert(s);
        int h = hash(convertedStr.hashCode());
        int index = h % table.length;
        Entry entry = table[index];
        while (entry != null) {
            String c = convert(entry.getValue());
            if (entry.getValue().equals(s)) {
                result.setIsCorrect(true);
            }

            if (c.equals(convertedStr)) {
                result.addAnagram(entry.getValue());
            }

            entry = entry.getNext();
        }
        return result;
    }

    private String convert(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("[^a-z]", "");
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public int hash(int hashCode) {
        return hashCode % Prime.p[sizeIndex + 1];
    }

    public void rehash() {
        sizeIndex++;
        Entry[] newTable = new Entry[Prime.p[sizeIndex]];
        Entry[] oldTable = table;
        table = newTable;
        count = 0;

        for (Entry entry : oldTable) {
            while (entry != null) {
                add(entry.getValue());
                entry = entry.getNext();
            }
        }
    }


    //    public static void main(String[] args) {
//        int[] numbers = new int[] {
//                4371,
//                1323,
//                6173, 4199, 4344, 9679, 1989
//        };
//
//        // h2(x) = 7 − (x mod 7).
//        for (int n : numbers) {
//            System.out.println(n + " : " + h2(n));
//        }
//        for (int n : numbers) {
//            System.out.println(n + " : " + h3(n));
//        }
//    }
//
//    private static int h2(int n) {
//        return 7 - n % 7;
//    }
//
//    private static int h3(int n) {
//        return n % 20;
//    }
}
