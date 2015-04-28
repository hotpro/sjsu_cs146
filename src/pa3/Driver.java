package pa3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Driver {

	public static void main(String[] args) {

		// START CODE build Char map
		// Make a map from alphabets to numbers.
		final String filename = "Charmap.txt";
		BufferedReader rd = null;
		HashMap<Character, Integer> hash = new HashMap<>();
		int key = 1;
		try {
			rd = new BufferedReader(new FileReader(new File(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String inputLine = null;
		try {
			while ((inputLine = rd.readLine()) != null) {
				char ch = inputLine.charAt(0);
				hash.put(ch, key);
				key++;
				// System.out.println(key +" " +charmap[key]);
			}

			// // Get keys.
			// Set<Character> keys = hash.keySet();
			//
			// // Loop over String keys.
			// for (Character i : keys) {
			// System.out.println(i +" " +hash.get(i));
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// END CODE build Char map

		// START CODE read and store words in hash
		BufferedReader rd1 = null;
		final String filename1 = "Small-dictionary25K.txt";
		try {
			rd1 = new BufferedReader(new FileReader(new File(filename1)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Array list for linear probing style hash
		HashMap<BigInteger, ArrayList<String>> dictionaryMap = new HashMap<>();
		String word = null;
		try {
			while ((word = rd1.readLine()) != null) {
				System.out.println("CurrentWord: " + word);
				word = word.toUpperCase();
				char[] nameArray = word.toCharArray();
				// System.out.print("Char array :");
				// for (int i = 0; i < nameArray.length; i++) {
				// System.out.print(nameArray[i] + " ");
				// }
				// System.out.println();

				int[] numArray = new int[nameArray.length];
				for (int i = 0; i < nameArray.length; i++) {
					numArray[i] = hash.get(nameArray[i]);
				}
				// System.out.print("Output int array: ");
				// for (int i = 0; i < numArray.length; i++) {
				// System.out.print(numArray[i] + " ");
				// }
				// System.out.println();

				// Sort the output array so that we can map the word and the
				// Anagram to the same key
				Arrays.sort(numArray);
				// System.out.print("Sorted Array: ");
				// for (int i = 0; i < numArray.length; i++) {
				// System.out.print(numArray[i] + " ");
				// }
				// System.out.println();

				String numStr = "";
				for (int i = 0; i < numArray.length; i++) {
					numStr = numStr + numArray[i];
				}
				System.out.println("  " + numStr);
				BigInteger numVal = new BigInteger(numStr);

				if (dictionaryMap.containsKey(numVal)) {
					ArrayList<String> arrList = dictionaryMap.get(numVal);
					arrList.add(word);
					dictionaryMap.put(numVal, arrList);
				} else {
					ArrayList<String> newArrList = new ArrayList<String>();
					newArrList.add(word);
					dictionaryMap.put(numVal, newArrList);

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader rd3 = null;
		final String inputFile = "InputFile.txt";
		try {
			rd3 = new BufferedReader(new FileReader(new File(inputFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String inputFileLine = null;
		try {
			while ((inputFileLine = rd3.readLine()) != null) {
				System.out.println("CurrentWord: " + word);
				word = word.toUpperCase();
				char[] nameArray = word.toCharArray();
				int[] numArray = new int[nameArray.length];
				for (int i = 0; i < nameArray.length; i++) {
					numArray[i] = hash.get(nameArray[i]);
				}
				Arrays.sort(numArray);
				String numStr = "";
				for (int i = 0; i < numArray.length; i++) {
					numStr = numStr + numArray[i];
				}
				System.out.println("  " + numStr);
				BigInteger numVal = new BigInteger(numStr);

				if (dictionaryMap.containsKey(numVal)) {
					ArrayList<String> arrList = dictionaryMap.get(numVal);
					arrList.add(word);
					dictionaryMap.put(numVal, arrList);
				} else {
					ArrayList<String> newArrList = new ArrayList<String>();
					newArrList.add(word);
					dictionaryMap.put(numVal, newArrList);

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
