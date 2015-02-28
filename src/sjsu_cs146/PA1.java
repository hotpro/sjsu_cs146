package sjsu_cs146;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PA1 {
	private static final String INPUT_FILE_NAME = "pa1input2.txt";
	private static final String OUT_FILE_NAME = "pa1output.txt";

    public static void main(String[] args) {
    	readFromFile();
        printList(transactions);
    	basisAndGain();
    	output();

    }
    
    static abstract class Trans {
    	int serial;
    	String item;
    	int q;
    	int price;
    	
    	public Trans(int serial, String item, int q, int price) {
			super();
			this.serial = serial;
			this.item = item;
			this.q = q;
			this.price = price;
		}
		abstract boolean isIn();
    	int getSerial() {
    		return serial;
    	}
    	int getQ() {
    		return q;
    	}
    	int getPrice() {
    		return price;
    	}

        public String getItem() {
            return item;
        }

        @Override
        public String toString() {
            return "Trans{" +
                    "serial=" + serial +
                    ", item='" + item + '\'' +
                    ", q=" + q +
                    ", price=" + price +
                    '}';
        }
    }

    static class In extends Trans {

		public In(int serial, String item, int q, int price) {
			super(serial, item, q, price);
		}

		@Override
		public boolean isIn() {
			
			return true;
		}

        @Override
        public String toString() {
            return "In{}  " + super.toString();
        }
    }

    static class Out extends Trans{

		public Out(int serial, String item, int q, int price) {
			super(serial, item, q, price);
		}

		@Override
		public boolean isIn() {
			
			return false;
		}

        @Override
        public String toString() {
            return "Out{} " + super.toString();
        }
    }
    
    static ArrayList<Trans> transactions = new ArrayList<Trans>();

    static void readFromFile() {

    	FileReader fileReader = null;

    	try {
			File file = new File(INPUT_FILE_NAME);
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = null;
            int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				String[] transes = line.split(" +");
				Trans trans;
				if ("in".equals(transes[0])) {
					trans = new In(i, transes[1], Integer.parseInt(transes[2]),
                            Integer.parseInt(transes[4].substring(1)));
				} else {
                    trans = new Out(i, transes[1], Integer.parseInt(transes[2]),
                            Integer.parseInt(transes[4].substring(1)));

				}
				transactions.add(trans);
                i++;
				
				sb.append(line);
				sb.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
//			System.out.println(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

    }
    
    static void basisAndGain() {
        int total = 0;
        ArrayList<Trans> current = new ArrayList<Trans>();
        ArrayList<CostBasisAndGain> costBasisAndGainArrayList = new ArrayList<CostBasisAndGain>();
        for (int i = 0; i < transactions.size(); i++) {
            Trans trans = transactions.get(i);
            if (trans.isIn()) {
                current.add(trans);
                total += trans.getQ();
            } else {
                int outQuantity = trans.getQ();
                if (outQuantity > total) {
                    System.out.println("error!!!");
                } else {

                    int p = 0;
                    int costBasis = 0;
                    int sumOut = 0;
                    for (int j = 0; j < i; j++) {
                        Trans t1 = transactions.get(j);
                        if (t1.isIn()) {
                            costBasis += t1.getQ() * t1.getPrice();
                        } else {
                            sumOut += t1.getQ() * t1.getPrice();
                        }
                    }
                    sumOut += outQuantity * trans.getPrice();

                    total -= outQuantity;

                    CostBasisAndGain costBasisAndGain = new CostBasisAndGain(trans.getSerial(),
                            trans.getItem(), trans.getQ(), costBasis, sumOut - costBasis );
                    costBasisAndGainArrayList.add(costBasisAndGain);


                    if (total == 0) {
                        // sold out
                        current.clear();
                    } else {
                        for (int j = 0; j < current.size(); j++) {
                            Trans c = current.get(j);
                            if (outQuantity >= c.getQ()) {
                                outQuantity -= c.getQ();
                            } else {
                                p = j;
                                break;
                            }
                        }

                        // remove sold items

                        for (int j = 0; j < p; j++) {
                            current.remove(0);
                        }

                        Trans transp = current.get(0);
                        current.remove(0);
                        current.add(new In(transp.getSerial(), transp.getItem(), transp.getQ() - outQuantity,
                                transp.getPrice()));

                    }
                }

                System.out.println("Table 1: Current Inventory");
                printList(current);
                System.out.println("Table 2: Cost Basis and Gains");
                printList(costBasisAndGainArrayList);

            }
        }


    }

    static class CostBasisAndGain {
        int serial;
        String item;
        int quantity;
        int costBasis;
        int gains;

        public CostBasisAndGain(int serial, String item, int quantity, int costBasis, int gains) {
            this.serial = serial;
            this.item = item;
            this.quantity = quantity;
            this.costBasis = costBasis;
            this.gains = gains;
        }

        @Override
        public String toString() {
            return "CostBasisAndGain{" +
                    "serial=" + serial +
                    ", item='" + item + '\'' +
                    ", quantity=" + quantity +
                    ", costBasis=" + costBasis +
                    ", gains=" + gains +
                    '}';
        }
    }


    static void output() {

    }

    static void printList(List<?> list) {
        for (Object o: list) {
            System.out.println(o.toString());
        }
    }


}

