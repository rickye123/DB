import java.util.*; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.PrintWriter;
import java.io.IOException; 

/* This class is responsible for all the methods involving
files. 
*/
public class Upload {

    private Record record = new Record(); 

    public boolean scanForColon(String filename) {
    
        try {

            File file = new File(filename);
            Scanner read = new Scanner(file);
            String word = read.nextLine();
            boolean valid = false;

            for (char c : word.toCharArray()) {
                if (c == ':') {
                    valid = true;
                }
            }
            return valid; 
        }
        catch (Exception e) {
            throw new Error(e);
        }

    }

    private boolean checkValidity(String filename) {

        if(doesFileExist(filename) == false) {
            return false; 
        }
        if(scanForColon(filename) == false) {
            return false; 
        }
        return true; 
    }

    public boolean doesFileExist(String filename) {

        File file = new File(filename);

        if (file.exists()) {
            return true; 
        }
        else {
            return false; 
        }
    }

    public boolean readFromFile(String filename, Table table) {

        if (checkValidity(filename) == false) {
        	return false; 
        }

        try {
            File file = new File(filename);        
            Scanner read = new Scanner(file); 
            String word = read.nextLine();

            String[] header = word.split(" : ");
            table.createTable(header);

            while (read.hasNextLine()) {
                word = read.nextLine();
                String[] records = word.split(" : ");
                record.setRecord(records);
                List<String> rec = record.getRecord(); 
                table.insertIntoTable(rec);                
            }
        } 
        catch (Exception e) {
            throw new Error(e);
        }
        return true; 
       
    }

    public void writeToFile(String filename, Table table, List<List<String>> tab) {
        // write to file with fields separated by a : character 
        try {
            File file = new File (filename);
            file.createNewFile(); 
            FileWriter writer = new FileWriter(file);

            for(int i = 0; i < tab.size(); i++) {
                for (int j = 0; j < tab.get(i).size(); j++) {
                    if(j == tab.get(i).size() - 1) {
                        writer.write(tab.get(i).get(j)); 
                    }
                    else {
                        writer.write(tab.get(i).get(j));
                        writer.write(" : ");
                    }
                }
                writer.write("\n");
            }
            writer.flush();
            writer.close(); 
        }
        catch (Exception e) {
            throw new Error(e);
        }

    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Upload");
        }

        Upload tests = new Upload(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {

        Table testTable = new Table(); 
        Table testTable2 = new Table();
        Print print = new Print();

        testTable.readFromFileToTable("./test_files/valid_files/animal.txt", testTable);
        List<List<String>> animal = testTable.getTable();
        assert(animal.get(0).get(0).equals("id"));
        assert(animal.get(1).get(1).equals("fido"));
        assert(animal.get(2).get(2).equals("cat"));
        assert(animal.get(3).get(3).equals("zb123"));

        testTable.writeToFileFromTable("testWrite.txt", testTable);
        testTable2.readFromFileToTable("testWrite.txt", testTable2);
        List<List<String>> test = testTable2.getTable();
        assert(test.get(0).get(0).equals("id"));
        assert(test.get(1).get(1).equals("fido"));
        assert(test.get(2).get(2).equals("cat"));
        assert(test.get(3).get(3).equals("zb123"));

        // check validity function 
        // first file contains no ':' character so it is invalid 
        assert(checkValidity("./test_files/invalid_files/invalidfile1.txt") == false);
        assert(checkValidity("./test_files/valid_files/animal.txt") == true);
        assert(checkValidity("./test_files/valid_files/people.txt") == true);

        assert(checkValidity("test.txt") == false); // file doesn't exist

        // doesFileExist function
        assert(doesFileExist("./test_files/invalid_files/invalidfile1.txt") == true);
        assert(doesFileExist("./test_files/valid_files/animal.txt") == true);
        
    }
   
}