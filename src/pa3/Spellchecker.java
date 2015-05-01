package pa3;

import java.util.Arrays;

/**
 * Created by yutao on 4/29/15.
 */
public class Spellchecker {
    public static void main(String[] args) {
        Spellchecker spellchecker = new Spellchecker();

        String s = "1Aacdb";
        System.out.println(spellchecker.convert(s));
    }

    private String convert(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("[^a-z]", "");
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
