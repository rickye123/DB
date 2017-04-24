/* This class is responsible for all the printing of tables / records
to the terminal. */

import java.util.*;

public class Print {

    private List<Integer> maxFieldLengths = new ArrayList<Integer>();

    // prints a record to the screen 
    public void displaySelectedRecord(List<String> record) {

        System.out.println("Record selected:");

        int i = 0; 

        for (String s : record) {
            System.out.format("%-10s\t", record.get(i));
            i++;
        }
        System.out.println();
 
    }

    public List<Integer> getFieldLengths() {
        return maxFieldLengths;
    }

    // prints a table and is used purely for debugging
    public void displayTable(List<List<String>> table) {

        System.out.println();
        for (int i = 0; i < table.size(); i++) {
            System.out.print(i + "\t"); 
            for (int j = 0; j < table.get(i).size(); j++) {

                System.out.format("%-10s\t", table.get(i).get(j));
            }
            System.out.println();
        }
        System.out.println(); 
    }

    // the main print method - used to print a formatted table
    public void printTable(List<List<String>> table, int ... keyCol) {

        createPrintableFormat(table);
        int colLength = table.get(0).size();
        int dash = numDashes(colLength);
        int key = findKeyCol(keyCol);

        System.out.println();
        for (int i = 0; i < table.size(); i++) {
            printDashes(dash);
            for (int j = 0; j < table.get(i).size(); j++) {
                if (maxFieldLengths.get(j) < 15) {
                    if(i == 0 && key == j) {
                        System.out.printf("|* %-13s", table.get(i).get(j));
                    }
                    else {
                        System.out.printf("|%-15s", table.get(i).get(j));
                    }
                }
                else {
                    String format = "|%-" + maxFieldLengths.get(j) + "s";
                    System.out.printf(format, table.get(i).get(j));
                }
            }
            System.out.print("|");
            System.out.println();
        }
        printDashes(dash);
        System.out.println(); 
    }

    // finds the key column so that a '*' character is added to the column 
    private int findKeyCol(int ... keyCol) {

        int key; 

        if(keyCol.length == 0) {
           key = 0; 
        }
        else if(keyCol.length > 1) {
           throw new Error("Cannot have more than one key column in this database");
        }
        else {
            key = keyCol[0];
        }
        return key; 

    }

    // used to print the tables that exist in the database 
    public void printTablesInDatabase(String ... tableNames) {

        List<String> tables = createPrintableDB(tableNames);
        Collections.sort(tables);
        
        int max = findMax(tables);
        int dash = numDashesForDB(max);
        
        printDashes(dash);
        System.out.printf("|%-30s|\n", "Tables");
        printDashes(dash);
        for (int i = 0; i < tables.size(); i++) {
            if(max < 30) {
                System.out.printf("|%-30s", tables.get(i));
            }
            else {
                String format = "|%-" + max + "s";
                System.out.printf(format, tables.get(i));
            }
            System.out.print("|");
            System.out.println();
            printDashes(dash);
        }
    }

    // used in printTablesInDatabase function to return the table 
    // names as an ArrayList 
    private List<String> createPrintableDB(String ... tablesNames) {
        int i = 0; 
        List<String> tables = new ArrayList<String>();
        for (String s : tablesNames) {
            tables.add(i, s);
            i++; 
        }
        return tables; 
    }

    // finds the max length of a column 
    private int findMax(List<String> tables) {
    	int max = 0; 
        for (int i = 0; i < tables.size(); i++) {
            String s = tables.get(i); 
            s.toCharArray();
            int length = s.length();

            if (length > max) {
                max = length; 
            }
        }
        return max; 
    }

    // finds the total number of dashes 
    public int numDashesForDB(int max) {
        int total = 0; 

        if (max < 30) {
            total += 30; 
        }
        else {
            total += max; 
        }
        total += 2; 
        return total; 
    }

    // print the number of dashes
    public void printDashes(int dash) {
        
        int i = 0; 

        while ( i < dash) {
        	System.out.print("-");
        	i++;
        }
        System.out.println();

    }

    public int numDashes(int colLength) {

        int total = 0; 
        int i = 0; 
        
        for (Integer in : maxFieldLengths) {
            if (maxFieldLengths.get(i) < 15) {
                total += 15;
            }
            else {
                total += maxFieldLengths.get(i);
            }
            i++;
        }

        // add extra dashes for the | characters (one per column) + extra 1
        total += colLength + 1;

        return total; 

    }

    // prints a record
    public void printRecord(List<String> record) {

        int i = 0; 

        System.out.println("Column" + "\t" + "Data"); 
        for (String s : record) {
            System.out.println( (i + 1) + "\t" + record.get(i));
            i++;
        }
        System.out.println("");

    }

    // creates a printable table and the max length of columns
    private void createPrintableFormat(List<List<String>> table) {

        clearFields();
        int colCount = table.get(0).size();

        // array storing the max columns 
        int[] columnMaxs = new int[colCount];
        int j = 0; 

        while ( j < colCount) {
            int max = 0; 
            for (int i = 0; i < table.size(); i++) {
                String s = table.get(i).get(j);
                s.toCharArray();
                int length = s.length();
                
                if (length > max) {
                    max = length; 
                }             
            }
            columnMaxs[j] = max; 
            j++;
        }

        addToFields(columnMaxs, colCount);
    }

    // clears the maxFieldLengths list
    private void clearFields() {

        if (!maxFieldLengths.isEmpty()) {
            int size = maxFieldLengths.size();
            int i = 0; 
            while (i < size) {
                maxFieldLengths.remove(0);
                i++;
            }
        }

    }

    // returns the record selected and a header at a position in the database
    public void displayRecord(List<String> r, List<String> h, Database db, int p) {

        List<List<String>> table = new ArrayList<List<String>>(); 
        table.add(h);
        table.add(r);
        int key = db.getKeyCol(p); 
        printTable(table, key);
    
    }

    // add the column lengths to maxFieldLengths arraylist
    private void addToFields(int[] columnMaxs, int count) {

        int i = 0; 

        while (i < count) {
            maxFieldLengths.add(columnMaxs[i]);
            i++;
        }

    }

    // displays the table with a ':' character between each field
    public void displayFileTable(List<List<String>> tab) {
        for (int i = 0; i < tab.size(); i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < tab.get(i).size(); j++) { 
                if(j == tab.get(i).size() - 1) {
                    System.out.print(tab.get(i).get(j));
                }
                else {
                    System.out.print(tab.get(i).get(j) + " /: ");
                }

            } 
            System.out.println();
        }
    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Print");
        }

        Print tests = new Print(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {

        Record record = new Record();
        Print print = new Print();
        Table table = new Table();

        record.setRecord("Rich", "Ellor", "50, Overhill Road, Stratton, Cirencester, Gloucestershire, GL72LG");
        List<String> rec = record.getRecord(); 
        print.printRecord(rec);

        table.createTable(0, "First name", "Last name", "Address");
        table.insertIntoTable(rec);
        List<List<String>> tab = table.getTable(); 

        print.printTable(tab);
        List<Integer> max1 = print.getFieldLengths();

        Table people = new Table();
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        List<List<String>> peeps = people.getTable(); 
        print.printTable(peeps);
        List<Integer> max = print.getFieldLengths();

        Table animal = new Table();
        int key = animal.getKeyCol(); 
        animal.readFromFileToTable("test_files/valid_files/animal.txt", animal);
        List<List<String>> ani = animal.getTable(); 
        print.printTable(ani, key);
        List<Integer> max2 = print.getFieldLengths();

    }
}