package sjsu_cs146;

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

    static String readFromFile() {
    	return "";

    }
    
    static void basisAndGain() {
    	
    }

    static void output() {

    }


}

