package pa3;


import java.io.*;

/**
 * Created by yutao on 4/30/15.
 */
public class Main {
    public static void main(String[] args) {
        HashTable table = new HashTable();

        System.out.println("building a dictionary...");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("Small-dictionary25K.txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                table.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("start checking...");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("pa3InputFile.txt"));
            bufferedWriter = new BufferedWriter(new FileWriter("pa3Output.txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String word = line.trim();

                Result result = table.search(word);
                System.out.println(word);
                bufferedWriter.write(word);
                bufferedWriter.newLine();

                if (result.isCorrect) {
                    System.out.print("\"Correct spelling\"\n");
                    bufferedWriter.write("\"Correct spelling\"\n");
                } else {
                    System.out.print("\"Incorrect spelling\"\n");
                    bufferedWriter.write("\"Incorrect spelling\"\n");
                }

                if (result.getAnagrams().size() > 0) {
                    System.out.println("Valid anagrams:");
                    bufferedWriter.write("Valid anagrams:");
                    bufferedWriter.newLine();
                    for (String anagram : result.getAnagrams()) {
                        System.out.println(anagram);
                        bufferedWriter.write(anagram);
                        bufferedWriter.newLine();
                    }
                } else {
                    System.out.println("No Valid anagrams.");
                    bufferedWriter.write("No Valid anagrams.");
                    bufferedWriter.newLine();
                }
                System.out.println();
                bufferedWriter.newLine();

                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println("finished");
    }


}
