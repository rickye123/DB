/* This class deals with tables, also called relations or 
types. A table is a COLLECTION of records, all having the same
number of fields, in the same order, with the same names.

Tables hold the field names, rather than repeating them
in every record object. Fields are called columns or attributes

Common operations: select a record (by row number of key), 
insert a record, delete a record, update a record. 

Other operations: create a table with given column names, 
or alter a table, e.g. by adding an extra column.  */

import java.util.*;

public class Table {

    private List<List<String>> table = new ArrayList<List<String>>(); 
    private int rowNumber = 0; 
    private int columns = 0; 
    private int keyCol = 0; 
    private Record records; 
    private Key key = new Key(); 
    private String tableName; 

    // Table constructor 
    public Table(String ... name) {

        int num = 0; 
        for (String s : name) {
            num++;
        }

        if (num == 0) {
            tableName = "untitled";
            return; 
        }

        if (num > 1) {
            throw new Error("Table can only have one name");
        }
        else { 
            List<String> names = new ArrayList<String>(); 
            for (String s : name) {
                names.add(0, s); 
            }
            tableName = names.get(0);
        }
    }

    public int getKeyCol() {
        return keyCol; 
    }
    public int getRowNumber() {
        return rowNumber; 
    }

    public String getTableName() {
        return tableName; 
    }

    public int getKeyColumn() {
        return keyCol; 
    }

    // to be incremented after each record is added
    private int incrementRows() {
        rowNumber++; 
        return rowNumber;
    }

    // clear the entire table
    public void clearTable() {

        while (!table.isEmpty()) {
            int size = table.size();
            int i = 0; 
            while (i < size) {
                table.remove(0);
                i++;
            } 
        }
    }

    // if table is empty, read from file into table
    public boolean readFromFileToTable(String filename, Table tab, int ... key) {

        Upload file = new Upload(); 
        
        int primaryKey = findKeyCol(key); 
        keyCol = primaryKey;

        if(table.isEmpty()) {
            if(file.readFromFile(filename, tab, primaryKey) == false) {
                keyCol = 0;
                tab.clearTable();
                return false; 
            }
            return true; 
        }
        else {
            return false; 
        }
        
    }

    public int findKeyCol(int ... keyCol) {
        int key; 

        if (keyCol.length == 0) {
            key = 0; 
        }
        else if (keyCol.length > 1) {
            key = 0; 
        }
        else {
            key = keyCol[0]; 
        }
        return key; 
    }


    public boolean setKeyColumn(int columnNumber) {

        if(checkValidKey(columnNumber) == false) {
            return false; 
        }
        keyCol = columnNumber; 
        return true; 
    }

    private boolean checkValidKey(int columnNumber) {
        if(columnNumber > columns || columnNumber < 0) {
            return false; 
        }
        return true; 
    }

    // writes the contents of tab object into file filename
    public void writeToFileFromTable(String filename, List<List<String>> tab) {

    	Upload file = new Upload(); 

    	file.writeToFile(filename, tab);
    }

    // create a table with given columns (setter method)
    public boolean createTable(int key, String ... data) {

        columns = 0; 

        table.add(new ArrayList<String>());

        int i = 0; 

        for (String s : data) {
            table.get(0).add(data[i]);
            i++; 
        }

        int primarykey = findKeyCol(key);
        if(primarykey >= data.length || primarykey < 0) {
            clearTable(); 
            return false; 
        }
        keyCol = primarykey; 
        incrementRows();
        numberColumns(data);
        return true; 

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
    public boolean deleteRecord(int selectRow) {

        if(invalidRow(selectRow) == true) {
            return false; 
        }

        int length = table.get(selectRow).size(); 

        table.remove(selectRow);
        rowNumber--;
        return true; 

    }

    // delete a record by key 
    public boolean deleteRecordByKey(String field) {

        if(getRowByKey(field) == false) {
            return false; 
        }

        int selectRow = referenceRecordByKey(field);

        int length = table.get(selectRow).size();

        table.remove(selectRow);
        rowNumber--; 
        return true; 
    }

    // sees if row for selecting record is valid
    public boolean selectRecordValid(int selectRow) {

        if(checkRowOOB(selectRow) == true) {
            return false; 
        }
        return true; 

    }

    // display a record at the specified row number of the table 
    public List<String> selectRecord(int selectRow) {

        if(checkRowOOB(selectRow) == true) {
            List<String> empty = new ArrayList<String>(1);
            empty.add("Empty set");
            return empty; 
        }

        List<String> record = new ArrayList<String>(); 

        int length = table.get(selectRow).size();
        int i = 0; 

        while (i < length) {
            record.add(i, table.get(selectRow).get(i));
            i++; 
        }

        return record; 
    }

    // return the record that exists at the row where the field
    // is in the key column
    public List<String> selectRecordByKey(String field) {

        List<String> record = new ArrayList<String>();

        int selectRow = referenceRecordByKey(field);
        if(selectRow == -1) {
            record.add("Empty set");
            return record; 
        }

        int i = 0; 
        int length = table.get(selectRow).size();

        while (i < length) {
            record.add(i, table.get(selectRow).get(i));
            i++; 
        }

        return record; 

    }

    // sees if the row returned is in the table based on 
    // the field value in the key column
    public boolean checkRecordByKeyValid(String field) {

        if(getRowByKey(field) == false) {
            return false; 
        }
        return true; 

    }

    public boolean getRowByKey(String field) {

        int row = referenceRecordByKey(field); 
        
        if (row < 0) {
            return false; 
        }
        return true; 
    }

    public int referenceRecordByKey(String field) {

        // search through key column and find the field       
        int selectRow = searchForField(field);

        if(selectRow == 0) {
            return -1; 
        }
        return selectRow;
    }

    private int searchForField(String field) {

        int i = 0; 

        while (i < table.size()) {
            if(table.get(i).get(keyCol).equals(field)) {
                return i; 
            }
            i++;
        }
        return 0;
    }

    // throw error if row selected is not in table 
    private boolean invalidRow(int selectRow) {

        if (checkRowOOB(selectRow) == true) {
            return true; 
        }
        return false; 

    }

    // check if the row selected is out of bounds 
    private boolean checkRowOOB(int row) {

        if (row <= 0 || row >= rowNumber) {
            return true; 
        }
        return false; 
    }

    // update a cell in table with value at position [row][col]. If
    // duplicate to value in key column, return false
    public boolean updateRecord(int row, int col, String value) {

        int length = findNumRows(table);

        if (outOfBounds(row, col, length) == true) {
            return false; 
        }

        String oldValue = table.get(row).get(col); 
        table.get(row).set(col, value); 

        if(key.keySearch(table, keyCol) == false) {
            table.get(row).set(col, oldValue);
            return false; 
        }
        return true; 

    }

    // update a cell in table with new value at the position where
    // the field is in the key column
    public boolean updateRecordByKey(String field, String oldV, String newV) {
        
        if (getRowByKey(field) == false) {
            return false; 
        }

        int selectRow = referenceRecordByKey(field);
        int selectCol = findOldValue(oldV, selectRow);
        if(selectCol < 0) {
            return false; 
        }

        table.get(selectRow).set(selectCol, newV);

        if(key.keySearch(table, keyCol) == false) {
            table.get(selectRow).set(selectCol, oldV);
            return false; 
        }
        return true; 

    }

    // find the column in the record selected by key
    private int findOldValue(String oldV, int selectRow) {

        int i = 0; 

        while (i < table.get(selectRow).size()) {
            if (table.get(selectRow).get(i).equals(oldV)) {
                return i; 
            }
            i++; 
        }
        return -1; 

    }

    // check if the row and col passed to updateTable are out of bounds
    private boolean outOfBounds(int row, int col, int length) {

        if (row > length || row <= 0 || col >= columns || col < 0) {
            return true; 
        }
        return false; 
    }

    // insert a record into table 
    public boolean insertIntoTable(List<String> record) {

        records = new Record(); 

        if (checkRecordLength(record) == false) {
            return false; 
        }

        table.add(new ArrayList<String>());

        int i = 0; 

        for (String s : record) {
            table.get(rowNumber).add(record.get(i));
            i++;
        }
        if(key.keySearch(table, keyCol) == false) {
        	table.remove(rowNumber);
            return false; 
        }
        incrementRows();
        return true; 

    }

    // sees if the fields in the record are the same as the 
    // the number of fields (columns) in the table 
    private boolean checkRecordLength(List<String> record) {

        records = new Record(); 

        if (records.findRecordLength(record) != columns) {
            return false; 
        }
        return true; 

    }

    // finds size of table and skips rows which are null 
    public int findNumRows(List<List<String>> table) {

        int length; 

        if (table.isEmpty()) {
            length = 0;
            return length;
        } 

        length = table.size(); 
        // remove header
        length--;

        return length; 
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

        testOutOfBounds(); 
        testCheckRecordLength();
        testCheckRowOOB();
        testNumColumns();
        testKeyColumns();
        testReadFromFile();
        testSetKeyColumn(); 
        testDeleteRecord();
        testDeleteRecordByKey(); 
        testSelectRecordValid(); 
        testSelectRecord();
        testSelectRecordByKey();
        testCheckRecordByKeyValid(); 
        testGetRowByKey();
        testReferenceRecordByKey();
        testSearchForField();
        testUpdateRecord();
        testUpdateRecordByKey();
        testFindOldValue();
        testInsertIntoTable();
        // test insert, select, create, delete, update,
        // find table size, and other related functions 
        integrationTestTables(); 

    }

    private void testOutOfBounds() {
    
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

    private void testCheckRecordLength() {

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

    private void testCheckRowOOB() {

        rowNumber = 4; 

        // when row is NOT out of bounds 
        assert(checkRowOOB(3) == false);
        assert(checkRowOOB(1) == false);
        assert(checkRowOOB(2) == false);

        // when row is out of bounds 
        // 0 out of bounds because can't select row 0 as this is the header
        assert(checkRowOOB(0) == true); 
        // 4 out of bounds because the current rowNumber is where the next row
        // to be inserted will go. Therefore, it does not exist yet 
        assert(checkRowOOB(4) == true);
        assert(checkRowOOB(-1) == true);
        assert(checkRowOOB(5) == true);
    }

    private void testNumColumns() {

    	columns = 0; 
        String[] testData = {"Name", "Username", "id"};
        assert(numberColumns(testData) == 3);

        columns = 0; 
        testData = new String[] {"Name", "Username"};
        assert(numberColumns(testData) == 2);

    }

    private void testKeyColumns() {

        columns = 3; 

        assert(checkValidKey(0) == true);
        assert(checkValidKey(1) == true);
        assert(checkValidKey(2) == true);
        assert(checkValidKey(3) == true);

        assert(checkValidKey(4) == false);
        assert(checkValidKey(-1) == false); 
    }

    private void testReadFromFile() {

        Table table = new Table(); 
        Table table2 = new Table(); 
        assert(table.readFromFileToTable("./test_files/valid_files/animal.txt", 
        table) == true);
        // false as file is invalid
        assert(table2.readFromFileToTable("./test_files/invalid_files/invalidfile1.txt", 
        table2) == false);
        // false as table "table" already exists
        assert(table.readFromFileToTable("./test_files/valid_files/animal.txt", 
        table) == false);
    }

    private void testSetKeyColumn() {

    	columns = 3; 

        assert(setKeyColumn(1) == true);
        assert(setKeyColumn(2) == true);
        assert(setKeyColumn(3) == true);
        assert(setKeyColumn(0) == true);

        assert(setKeyColumn(-1) == false);
        assert(setKeyColumn(4) == false);

    }

    private void testDeleteRecord() {

        Table table = new Table(); 

        table.readFromFileToTable("./test_files/valid_files/animal.txt", table); 
        assert(table.deleteRecord(1) == true); 

        // false cases
        assert(table.deleteRecord(3) == false);
        assert(table.deleteRecord(0) == false);

    }

    private void testDeleteRecordByKey() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);
        assert(table.deleteRecordByKey("re16621") == true);
        assert(table.deleteRecordByKey("be") == true);

        assert(table.deleteRecordByKey("hello") == false);
        assert(table.deleteRecordByKey("be") == false);

    }

    private void testSelectRecordValid() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.selectRecordValid(1) == true);
        assert(table.selectRecordValid(2) == true);
        assert(table.selectRecordValid(3) == true);
        assert(table.selectRecordValid(4) == true);

        assert(table.selectRecordValid(0) == false);
        assert(table.selectRecordValid(7) == false);
        assert(table.selectRecordValid(8) == false);
        assert(table.selectRecordValid(90) == false);
    }

    private void testSelectRecord() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);
        List<String> rec1 = table.selectRecord(0); 
        assert(rec1.get(0).equals("Empty set"));
        rec1 = table.selectRecord(1);
        assert(rec1.get(0).equals("re16621"));
        assert(rec1.get(1).equals("Rich Ellor"));

    }

    private void testSelectRecordByKey() {

        Table table = new Table();
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);
        List<String> rec1 = table.selectRecordByKey("hello");
        assert(rec1.get(0).equals("Empty set"));
        rec1 = table.selectRecordByKey("re16621");
        assert(rec1.get(0).equals("re16621"));
        assert(rec1.get(1).equals("Rich Ellor"));

    }

    private void testCheckRecordByKeyValid() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.checkRecordByKeyValid("re16621") == true);
        assert(table.checkRecordByKeyValid("cd456") == true);
        assert(table.checkRecordByKeyValid("hello") == false);
    }

    private void testGetRowByKey() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.getRowByKey("re16621") == true);
        assert(table.getRowByKey("cd456") == true);
        assert(table.getRowByKey("hello") == false);
    }

    private void testReferenceRecordByKey() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.referenceRecordByKey("re16621") == 1);
        assert(table.referenceRecordByKey("cd456") == 2);
        assert(table.referenceRecordByKey("hello") == -1);
    }

    private void testSearchForField() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.searchForField("re16621") == 1);
        assert(table.searchForField("cd456") == 2);
        assert(table.searchForField("hello") == 0);
    }

    private void testUpdateRecord() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.updateRecord(0, 0, "B20251074") == false);
        assert(table.updateRecord(1, 0, "B20251074") == true);
        assert(table.updateRecord(7, 0, "B20251074") == false);
        assert(table.updateRecord(4, 2, "Rich") == false);

    }

    private void testUpdateRecordByKey() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.updateRecordByKey("re16621", "re16621", "B20251074") == true);
        assert(table.updateRecordByKey("B20251074", "Rich Ellor", "Richard") == true);
        assert(table.updateRecordByKey("hello", "Rich Ellor", "Richard") == false);
        assert(table.updateRecordByKey("re16621", "Richard", "Rich") == false);
    }

    private void testFindOldValue() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);

        assert(table.findOldValue("Rich Ellor", 1) == 1);
        assert(table.findOldValue("Rich Ellor", 2) == -1);
        assert(table.findOldValue("Rich Ellor", 0) == -1);
        assert(table.findOldValue("Becca", 6) == 1);
    }

    private void testInsertIntoTable() {

        Table table = new Table(); 
        table.readFromFileToTable("./test_files/valid_files/people.txt", table);
        Record record = new Record(); 

        record.setRecord("Extra", "Info");
        List<String> rec = record.getRecord(); 
        assert(table.insertIntoTable(rec) == true);

        record.setRecord("Extra", "Info", "Extra");
        rec = record.getRecord(); 
        assert(table.insertIntoTable(rec) == false);

        record.setRecord("re16621", "Richard");
        rec = record.getRecord(); 
        assert(table.insertIntoTable(rec) == false);

    }

    private void integrationTestTables() {

        Record testRecord = new Record(); 
        Table testTable = new Table(); 

        // test createTable, insertIntoTable and getTable
        testTable.createTable(0, "First name", "Last name");

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
        assert(findNumRows(testTab) == 1);

        // add new row for testing purposes
        testRecord.setRecord("Test", "Subject");
        testRec = testRecord.getRecord(); 
        testTable.insertIntoTable(testRec);
        testTab = testTable.getTable(); 
        assert(findNumRows(testTab) == 2);

        testTable.deleteRecord(1); 
        // now test subject has moved to where rich ellor was
        assert(testTab.get(1).get(0).equals("Test"));
        assert(testTab.get(1).get(1).equals("Subject"));
        assert(findNumRows(testTab) == 1);

        // update "Subject" to "Case"
        testTable.updateRecord(1, 1, "Case");
        assert(testTab.get(1).get(1).equals("Case"));

    }

}