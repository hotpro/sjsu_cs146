package pa2;

import java.io.*;
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

    public static void print(String s) {
        print(System.out, s);
        PrintStream printStream = null;
        try {
            printStream = new PrintStream("pa2output.txt");
            print(printStream, s);
            printStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static void print(PrintStream printStream, String s) {
        printStream.println(s);
    }

    public static void readInputFromFile(String fileName, int[] input) {

        FileReader fileReader = null;
        int i = 0;
        int length = input.length;
        try {
            fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                input[i] = Integer.parseInt(line);
                i++;
                if (i >= length) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void test(Node node, BST bst) {
        if (node == null) {
            return;
        }
        System.out.println("Node " + node+ " predecessor: " + bst.getPredecessor(node));
        test(node.getLeftChild(), bst);
        test(node.getRightChild(), bst);
    }
}
