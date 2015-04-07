import java.io.*;
import java.util.*;

public class PA1 {
    private static final String INPUT_FILE_NAME = "pa1input.txt";
    private static final String OUT_FILE_NAME = "pa1output.txt";

    public static void main(String[] args) {

        System.out.println("Worker Start");

        List<Transaction> transactions = readFromFile();
        // printList(transactions);

        Inventory inventory = new Inventory();
        try {
            for (Transaction transaction : transactions) {
                inventory.handleTransaction(transaction);
            }
            List<Transaction> current = inventory.getCurrent();
            List<CostBasisAndGain> costBasisAndGains = inventory
                    .getCostBasisAndGainArrayList();

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(OUT_FILE_NAME);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Table 1: Current Inventory");
                bufferedWriter.newLine();
                String str = String.format("%7s  %4s  %8s  %4s", "Serial#",
                        "Item", "Quantity", "Cost");
                bufferedWriter.write(str);
                writeToFile(bufferedWriter, current);

                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write("Table 2: Cost Basis and Gains");
                bufferedWriter.newLine();
                String s = String.format("%7s  %4s  %8s  %13s  %8s", "Serial#",
                        "Item", "Quantity", "Cost Basis($)", "Gains($)");
                // bufferedWriter.write("Serial#  Item  Quantity  Cost Basis($)  Gains($)");
                bufferedWriter.write(s);

                writeToFile(bufferedWriter, costBasisAndGains);

                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // System.out.println("Table 1: Current Inventory");
            // printList(current);
            // System.out.println("Table 2: Cost Basis and Gains");
            // printList(costBasisAndGains);
        } catch (NoInventoryException nie) {
//            nie.printStackTrace();
            System.out.println(nie.getMessage());
        } catch (ExceedInventoryException eie) {
            System.out.println(eie.getMessage());

        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println("Worker End");
    }

    static abstract class Transaction {
        int serial;
        String item;
        int quantity;
        int price;

        public Transaction(int serial, String item, int quantity, int price) {
            super();
            this.serial = serial;
            this.item = item;
            this.quantity = quantity;
            this.price = price;
        }

        abstract boolean isIn();

        int getSerial() {
            return serial;
        }

        int getQuantity() {
            return quantity;
        }

        int getPrice() {
            return price;
        }

        public String getItem() {
            return item;
        }

        @Override
        public String toString() {
            String s = String.format("%-7s  %-4s  %-8s  %-4s", serial,
                    item, quantity, price);
            return s;
        }
    }

    static class InTransaction extends Transaction {

        public InTransaction(int serial, String item, int q, int price) {
            super(serial, item, q, price);
        }

        @Override
        public boolean isIn() {

            return true;
        }
    }

    static class OutTransaction extends Transaction {

        public OutTransaction(int serial, String item, int quantity, int price) {
            super(serial, item, quantity, price);
        }

        @Override
        public boolean isIn() {

            return false;
        }
    }

    static class Inventory {
        List<Transaction> current = new ArrayList<Transaction>();
        List<Transaction> allTransactions = new ArrayList<Transaction>();
        List<CostBasisAndGain> costBasisAndGainArrayList = new ArrayList<CostBasisAndGain>();
        Map<String, Integer> counterMap = new HashMap<String, Integer>();

        public void handleTransaction(Transaction transaction) throws Exception {
            if (transaction.isIn()) {
                in(transaction);
            } else {
                out(transaction);
            }
        }

        private void in(Transaction transaction) {
            if (counterMap.containsKey(transaction.getItem())) {
                int total = counterMap.get(transaction.getItem());
                total += transaction.getQuantity();
                counterMap.put(transaction.getItem(), total);
            } else {
                counterMap
                        .put(transaction.getItem(), transaction.getQuantity());
            }

            current.add(transaction);
            allTransactions.add(transaction);
        }

        private void out(Transaction transaction) throws Exception {
            int total = 0;
            if (counterMap.containsKey(transaction.getItem())) {
                total = counterMap.get(transaction.getItem());
            }

            if (total == 0) {
                throw new NoInventoryException("no inventory");
            }

            int outQuantity = transaction.getQuantity();
            if (outQuantity > total) {
                // System.out.println("error!!!");
                throw new ExceedInventoryException("out quantity exceeds the inventory");
            } else {

                int allTransactionSize = allTransactions.size();
                // int position = 0;
                int costBasis = 0;
                int sumOfOut = 0;
                for (int j = 0; j < allTransactionSize; j++) {
                    Transaction t = allTransactions.get(j);
                    if (t.getItem().equals(transaction.getItem())) {
                        if (t.isIn()) {
                            costBasis += t.getQuantity() * t.getPrice();
                        } else {
                            sumOfOut += t.getQuantity() * t.getPrice();
                        }
                    }
                }
                sumOfOut += outQuantity * transaction.getPrice();

                CostBasisAndGain costBasisAndGain = new CostBasisAndGain(
                        transaction.getSerial(), transaction.getItem(),
                        transaction.getQuantity(), costBasis, sumOfOut
                        - costBasis);
                costBasisAndGainArrayList.add(costBasisAndGain);

                total -= outQuantity;
                counterMap.put(transaction.getItem(), total);

                if (total == 0) {
                    // sold out
                    Iterator<Transaction> iterator = current.iterator();
                    while (iterator.hasNext()) {
                        Transaction next = iterator.next();
                        if (next.getItem().equals(transaction.getItem())) {
                            iterator.remove();
                        }
                    }
                } else {
                    List<Transaction> itemsToRemove = new ArrayList<Transaction>();
                    for (int j = 0; j < current.size(); j++) {
                        Transaction t = current.get(j);
                        if (t.getItem().equals(transaction.getItem())) {
                            if (outQuantity >= t.getQuantity()) {
                                outQuantity -= t.getQuantity();
                                itemsToRemove.add(t);
                            } else if (outQuantity == 0) {
                                break;
                            } else {
                                current.add(j, new InTransaction(t.getSerial(),
                                        t.getItem(), t.getQuantity()
                                        - outQuantity, t.getPrice()));
                                itemsToRemove.add(t);
                                break;
                            }
                        }
                    }

                    // remove sold items
                    for (Transaction t : itemsToRemove)
                        current.remove(t);
                }
                allTransactions.add(transaction);
            }
        }

        public List<Transaction> getCurrent() {
            return current;
        }

        public List<CostBasisAndGain> getCostBasisAndGainArrayList() {
            return costBasisAndGainArrayList;
        }
    }

    static List<Transaction> readFromFile() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        FileReader fileReader = null;

        try {
            File file = new File(INPUT_FILE_NAME);
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null
                    && line.length() > 0) {
                String[] items = line.split(" +");
                Transaction transaction;
                int serial = i + 1;
                String item = items[1].substring(0, items[1].length() - 1);
                int quantity = Integer.parseInt(items[2]);
                int price = Integer.parseInt(items[4].substring(1));

                if ("in".equals(items[0])) {
                    transaction = new InTransaction(serial, item, quantity,
                            price);
                } else {
                    transaction = new OutTransaction(serial, item, quantity,
                            price);

                }
                transactions.add(transaction);
                i++;

                sb.append(line);
                sb.append("\n");
            }
            fileReader.close();
            // System.out.println("Contents of file:");
            // System.out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return transactions;

    }

    static class CostBasisAndGain {
        int serial;
        String item;
        int quantity;
        int costBasis;
        int gains;

        public CostBasisAndGain(int serial, String item, int quantity,
                                int costBasis, int gains) {
            this.serial = serial;
            this.item = item;
            this.quantity = quantity;
            this.costBasis = costBasis;
            this.gains = gains;
        }

        @Override
        public String toString() {
            String s = String.format("%-7s  %-4s  %-8s  %-13s  %-8s", serial,
                    item, quantity, costBasis, gains);
            /*
             * return serial + "   " + item + "   " + quantity + "   " +
             * costBasis + "   " + gains;
             */
            return s;
        }
    }

    static void writeToFile(BufferedWriter bufferedWriter, List<?> list)
            throws IOException {
        for (Object o : list) {
            bufferedWriter.newLine();
//            System.out.println(o.toString());
            bufferedWriter.write(o.toString());
        }
    }

    static class NoInventoryException extends Exception {
        public NoInventoryException(String message) {
            super(message);
        }
    }
    static class ExceedInventoryException extends Exception {
        public ExceedInventoryException(String message) {
            super(message);
        }
    }

//    static void writeToFile2(BufferedWriter bufferedWriter,
//                             List<Transaction> list) throws IOException {
//        for (Transaction o : list) {
//            bufferedWriter.newLine();
//            String s = String.format("%-7s  %-4s  %-8s  %-4s", o.serial,
//                    o.item, o.quantity, o.price);
//            bufferedWriter.write(s);
//        }
//    }

    /*
     * static void printList(List<?> list) { for (Object o : list) {
     * System.out.println(o.toString()); } }
     */
}
