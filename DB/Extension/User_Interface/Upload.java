/* This class is responsible for all the methods involving
files, such as reading from a readable text file and 
writing to a text file separated by '/:' characters. 

A readable text file has to have '/:' characters in it which 
separate fields. Without this, the file isn't readable and
a false value is returned. */

import java.util.*; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.PrintWriter;
import java.io.IOException; 

public class Upload {

    private Record record = new Record(); 
    private Key key = new Key(); 

    // scans a file and if there is no '/:' character then the file
    // is invalid
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

    // if file doesn't exist or no colon in it then return false
    private boolean checkValidity(String filename) {

        if(doesFileExist(filename) == false) {
            return false; 
        }
        if(scanForColon(filename) == false) {
            return false; 
        }
        return true; 
    }

    // see if file exists 
    public boolean doesFileExist(String filename) {

        File file = new File(filename);

        if (file.exists()) {
            return true; 
        }
        else {
            return false; 
        }
    }

    // read from file and split fields up with ':' chracter 
    public boolean readFromFile(String filename, Table table, int k) {

        if (checkValidity(filename) == false) {
            return false; 
        }

        try {
            File file = new File(filename);        
            Scanner read = new Scanner(file); 
            String word = read.nextLine();

            String[] header = word.split(" /: ");
            if(k < 0 || k >= header.length) {
                return false; 
            }
            table.createTable(k, header);

            while (read.hasNextLine()) {
                word = read.nextLine();
                String[] records = word.split(" /: ");
                record.setRecord(records);
                List<String> rec = record.getRecord(); 
                if(table.insertIntoTable(rec) == false) {
                    return false; 
                }
            }
        } 
        catch (Exception e) {
            throw new Error(e);
        }
        return true; 
       
    }

    // writes to the file and adds '/:' character between each field
    public void writeToFile(String filename, List<List<String>> tab) {

        try {
            File file = new File (filename + ".txt");
            file.createNewFile(); 
            FileWriter writer = new FileWriter(file);

            for(int i = 0; i < tab.size(); i++) {
                for (int j = 0; j < tab.get(i).size(); j++) {
                    if(j == tab.get(i).size() - 1) {
                        writer.write(tab.get(i).get(j)); 
                    }
                    else {
                        writer.write(tab.get(i).get(j));
                        writer.write(" /: ");
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

        testTable.writeToFileFromTable("testWrite.txt", animal);
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