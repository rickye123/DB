import java.util.*;

/* This class should deal with tables, also called relations or 
types. A table is a COLLECTION of records, all having the same
number of fields, in the same order, with the same names.

Table should hold the field names, rather than repeating them
in every record object. Fields are called columns or attributes

Common operations: select a record (perhaps by row number), 
insert a record, delete a record, update a record. May wish
to do operations on multiple records at one time. 

Other operations: create a table with given column names, 
or alter a table, e.g. by adding an extra column.  */

public class Table {

    private List<List<String>> table = new ArrayList<List<String>>(); 
    private int rowNumber = 0; 
    private int columns = 0; 
    private Record records; 

    public void readFromFile(String filename, Table table) {

        Upload file = new Upload(); 

        file.readFromFile(filename, table);

    }

    // create a table with given columns (setter method)
    public void createTable(String ... data) {

        table.add(new ArrayList<String>());

        int i = 0; 

        for (String s : data) {
            table.get(0).add(data[i]);
            i++; 
        }
        incrementRows();
        numberColumns(data);

    }

    // getter method
    public List<List<String>> getTable() {
        return table; 
    }

    // determine number of columns based on number of variables passed
    private int numberColumns(String ... data) {
        
        for (String s : data) {
        	columns++; 
        }
        return columns; 
    }

    // delete a record at the given row
    public void deleteRecord(int selectRow) {

        invalidRow(selectRow);

        int length = table.get(selectRow).size(); 

        table.get(selectRow).clear();

        replaceRow(selectRow); 
        moveRowsUp(selectRow);

    }

    // replace the null row with the row after it
    private void replaceRow(int selectRow) {

        int j = 0; 
        
        while (j < table.get(selectRow + 1).size()) {
            table.get(selectRow).add(table.get(selectRow + 1).get(j));
            j++; 
        }
    }

    private void moveRowsUp(int selectRow) {

        int i = selectRow + 2; 

        while(i < table.size()) {
            int j = 0; 
            table.get(i - 1).clear();
            while (j < table.get(i).size()) {
            	
                table.get(i - 1).add(table.get(i).get(j));
                j++;
            }
            i++;

        }

        table.get(table.size() -1).clear();
        rowNumber--;

    }

    // display a record at the specified row number of the table 
    public List<String> selectRecord(int selectRow) {

        invalidRow(selectRow);

        List<String> record = new ArrayList<String>(); 

        int length = table.get(selectRow).size();
        int i = 0; 

        while (i < length) {
        	record.add(i, table.get(selectRow).get(i));
            i++; 
        }

        return record; 
    }

    // throw error if row selected is not in table 
    private void invalidRow(int selectRow) {

        if (checkRowOOB(selectRow) == true) {
            throw new Error("Cannot reference a row out of bounds");
        }

    }

    // check if the row selected is out of bounds 
    private boolean checkRowOOB(int row) {

        if (row <= 0 || row >= rowNumber) {
            return true; 
        }
        return false; 
    }

    // update a cell in table with value at position [row][col]
    public void updateTable(int row, int col, String value) {

        int length = findTableSize(table);

        if (outOfBounds(row, col, length) == true) {
            throw new Error("Cannot update table out of bounds");
        }

        table.get(row).set(col, value); 

    }

    // check if the row and col passed to updateTable are out of bounds
    private boolean outOfBounds(int row, int col, int length) {

        if (row > length || row <= 0 || col >= columns || col < 0) {
            return true; 
        }
        return false; 
    }

    // insert a record into table 
    public void insertIntoTable(List<String> record) {

        records = new Record(); 

        if (checkRecordLength(record) == false) {
            throw new Error("Cannot insert more fields than there are columns");
        }

        // create row
        table.add(new ArrayList<String>());

        int i = 0; 

        for (String s : record) {
            table.get(rowNumber).add(record.get(i));
            i++;
        }       
        incrementRows();

    }

    private boolean checkRecordLength(List<String> record) {

        records = new Record(); 

        if (records.findRecordLength(record) != columns) {
            return false; 
        }
        return true; 

    }

    // to go in display class 
    public void displaySelectedRecord(List<String> record) {

        System.out.println("Record selected:");

        int i = 0; 

        for (String s : record) {
            System.out.format("%-10s\t", record.get(i));
            i++;
        }
        System.out.println();
 
    }

    // finds size of table and skips rows which are null 
    public int findTableSize(List<List<String>> table) {

        int length = table.size(); 

        int i = 0; 

        while (i < table.size()) {
            if (table.get(i).isEmpty()) {
                length--;
            }
            i++; 
        }
        length--;
        return length; 
    }

    // purely for debugging 
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

    // to be incremented after each record is added
    private int incrementRows() {
        rowNumber++; 
        return rowNumber;
    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Table");
        }

        Table tests = new Table(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {

        // tests incrementRows function 
        assert(incrementRows() == 1); 

        // test OutOfBounds() function
        testOutOfBounds(); 
        // test checkRecordLength() function
        testCheckRecordLength();
        // test insert, select, create, delete, update,
        // find table size, and other related functions 
        integrationTestTables(); 

    }

    void testOutOfBounds() {
    
        // set number of columns for testing purposes 
        columns = 3; 

        // false cases 
        assert(outOfBounds(-1, 0, 1) == true); 
        assert(outOfBounds(0, 0, 1) == true);
        assert(outOfBounds(10, 2, 9) == true);

        // true cases 
        assert(outOfBounds(1, 0, 1) == false); 
        assert(outOfBounds(10, 1, 10) == false);
        assert(outOfBounds(1, 0, 10) == false);
    }

    void testCheckRecordLength() {

        columns = 3; 

        // false cases 
        Record testRecord = new Record(); 
        testRecord.setRecord("Rich", "Ellor");
        List<String> testRec = testRecord.getRecord(); 
        assert(checkRecordLength(testRec) == false);

        testRecord.setRecord("Rich", "Ellor", "123", "456");
        testRec = testRecord.getRecord();
        assert(checkRecordLength(testRec) == false); 

        // true cases 
        testRecord.setRecord("Rich", "Ellor", "123");
        testRec = testRecord.getRecord(); 
        assert(checkRecordLength(testRec) == true); 

    }

    void integrationTestTables() {

        Record testRecord = new Record(); 
        Table testTable = new Table(); 

        // test createTable, insertIntoTable and getTable
        testTable.createTable("First name", "Last name");

        testRecord.setRecord("Rich", "Ellor");
        List<String> testRec = testRecord.getRecord(); 
        testTable.insertIntoTable(testRec);
        List<List<String>> testTab = testTable.getTable(); 
        assert(testTab.get(0).get(0).equals("First name"));
        assert(testTab.get(0).get(1).equals("Last name"));
        assert(testTab.get(1).get(0).equals("Rich"));
        assert(testTab.get(1).get(1).equals("Ellor"));

        // test select, delete and update record 
        List<String> selectR = testTable.selectRecord(1); 
        assert(selectR.get(0).equals("Rich"));
        assert(selectR.get(1).equals("Ellor"));
        assert(findTableSize(testTab) == 1);

        // add new row for testing purposes
        testRecord.setRecord("Test", "Subject");
        testRec = testRecord.getRecord(); 
        testTable.insertIntoTable(testRec);
        testTab = testTable.getTable(); 
        assert(findTableSize(testTab) == 2);

        testTable.deleteRecord(1); 
        // now test subject has moved to where rich ellor was
        assert(testTab.get(1).get(0).equals("Test"));
        assert(testTab.get(1).get(1).equals("Subject"));
        assert(findTableSize(testTab) == 1);

        // update "Subject" to "Case"
        testTable.updateTable(1, 1, "Case");
        assert(testTab.get(1).get(1).equals("Case"));

    }

}