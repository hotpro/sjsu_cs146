package sjsu_cs146;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PA1 {

    public static void main(String[] args) {

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

    static class Int extends Trans {

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

    static public void readFromFile() 
    {
    	try {
			File file = new File("pa1input.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
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

    static public void output() {

    }


}

