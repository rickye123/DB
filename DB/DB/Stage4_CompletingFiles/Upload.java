import java.util.*; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.PrintWriter;
import java.io.IOException; 

/* This class is responsible for all the methods involving
files. */

public class Upload {

    private Record record = new Record(); 
    private String path; 
    private boolean append_to_file = false; 

    public void readFromFile(String filename, Table table) {

        try {
            File file = new File(filename);        
            Scanner read = new Scanner(file); 

            String word = read.nextLine();
            int length = word.length(); 

            String[] header = word.split(":");

            int i = 0; 
            while ( i < header.length) {
            	System.out.println(header[i]);
            	i++;
            }

            table.createTable(header);

            while (read.hasNextLine()) {
                word = read.nextLine();
                String[] records = word.split(":");
                record.setRecord(records);
                List<String> rec = record.getRecord(); 

                i = 0; 

                while ( i < rec.size()) {
                	System.out.println(rec.get(i));
                	i++;
                }
                table.insertIntoTable(rec);
                
            }
        } 
        catch (Exception e) {
            throw new Error(e);
        }
       
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
            			writer.write(":");
            		}
            		
            	}
            	writer.write("\n");
            }
	    		    	
	    	writer.write(tab.get(0).get(0));
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
            throw new Error("Use java -ea Table");
        }

        Upload tests = new Upload(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {
        Table testTable = new Table(); 

        readFromFile("animal.txt", testTable);

        List<List<String>> tab = testTable.getTable(); 
        testTable.displayTable(tab);

        displayFileTable(tab);

        writeToFile("test.txt", testTable, tab);

        readFromFile("test.txt", testTable);

        tab = testTable.getTable(); 
        testTable.displayTable(tab);

        displayFileTable(tab);

       
    }

    public void displayFileTable(List<List<String>> tab) {
        for (int i = 0; i < tab.size(); i++) {
        	System.out.print(i + "\t");
        	for (int j = 0; j < tab.get(i).size(); j++) { 
        		if(j == tab.get(i).size() - 1) {
        			System.out.print(tab.get(i).get(j));
        		}
        		else {
        			System.out.print(tab.get(i).get(j) +":");
        		}

        	} 
        	System.out.println();
        }
    }
    
}