import java.util.*; 
import java.io.File; 

/* This class is responsible for all the methods involving
files. */

public class Upload {

    private Record record = new Record(); 

    public void readFromFile(String filename, Table table) {

        try {
            File file = new File(filename);        
            Scanner read = new Scanner(file); 

            String word = read.nextLine();
            int length = word.length(); 

            String[] header = word.split(":");

            int i = 0; 

            while (i < header.length) {
                System.out.println(header[i]);
                i++;
            }

            table.createTable(header);

            while (read.hasNextLine()) {
                word = read.nextLine();
                String[] records = word.split(":");
                record.setRecord(records);
                List<String> rec = record.getRecord(); 
                table.insertIntoTable(rec);
                
            }
        } 
        catch (Exception e) {
            throw new Error(e);
        }
       
    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Table");
        }

        Upload tests = new Upload(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {
        Table testTable = new Table(); 

        readFromFile("address.txt", testTable);

        List<List<String>> tab = testTable.getTable(); 
        testTable.displayTable(tab);

    }

    
}