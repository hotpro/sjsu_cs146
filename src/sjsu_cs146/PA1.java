package sjsu_cs146;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PA1 {
	private static final String INPUT_FILE_NAME = "pa1input.txt";
	private static final String OUT_FILE_NAME = "pa1output.txt";

    public static void main(String[] args) {
    	readFromFile();
    	basisAndGain();
    	output();

    }
    
    static abstract class Trans {
    	int q;
    	int price;
    	String serial;
    	
    	abstract boolean isInt();
    	String getSerial() {
    		return serial;
    	}
    	int getQ() {
    		return q;
    	}
    	int getPrice() {
    		return price;
    	}
    	
    }

    static class In extends Trans {

		@Override
		public boolean isInt() {
			
			return true;
		}
    }

    static class Out extends Trans{

		@Override
		public boolean isInt() {
			
			return false;
		}
    }
    
    static ArrayList<Trans> transactions = new ArrayList<Trans>();

    static void readFromFile() {
    	try {
			File file = new File(INPUT_FILE_NAME);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] transes = line.split(" ");
				Trans trans;
				if ("in".equals(transes[0])) {
					trans = new In();
				} else {
					trans = new Out();
					
				}
				transactions.add(trans);
				
				
					
				
				
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    static void basisAndGain() {
    	
    }

    static void output() {

    }


}

