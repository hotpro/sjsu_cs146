package pa3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yutao on 4/30/15.
 */
public class Result {
    boolean isCorrect;
    List<String> anagrams;

    public Result() {
        anagrams = new ArrayList<>();
    }

    public void addAnagram(String s) {
        anagrams.add(s);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public List<String> getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(List<String> anagrams) {
        this.anagrams = anagrams;
    }
}
