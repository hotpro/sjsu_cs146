package pa2;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by yutao on 4/4/15.
 */
public class Utils {
    /**
     * Generate a random sequence of unique numbers for given lower and upper limits and number of nodes.
     *
     * @param lower lower limit
     * @param upper upper limit
     * @param n     number of nodes
     * @return random sequence array if n <= (upper - lower + 1), an array of n zero otherwise
     */
    public static int[] createNumbers(int lower, int upper, int n) {
        int[] numbers = new int[n];
        int range = upper - lower + 1;

        if (n > range) {
            return numbers;
        }

        Set<Integer> set = new HashSet<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int random = rand.nextInt(range);
            while (set.contains(random)) {
                random = rand.nextInt(range);
            }
            numbers[i] = random;
            set.add(random);
        }
        return numbers;
    }
}
