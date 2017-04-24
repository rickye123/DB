/* The database class wraps up all the workings of the previous classes. 
It allows multiple tables to be added and manipulated in similar ways 
as those in the Table.java class.  */

import java.util.*;
import java.io.*; 
import java.io.File; 
import java.nio.file.*; 

public class Database {

    private Record record; 
    private Table table;
    private String name; 
    private List<Table> database = new ArrayList<Table>();
    private List<String> tableName = new ArrayList<String>();
    private List<Integer> keyCol = new ArrayList<Integer>(); 

    public Database(String name) {
        this.name = name; 
    }

    public String getDatabaseName() {
        return name; 
    }

    public List<String> getTableName() {
        return tableName; 
    }

    public int numTables() {
        return database.size(); 
    }

    public int getKeyCol(int index) {
        return keyCol.get(index); 
    }

    public List<Table> getDatabase(){
        return database; 
    }

    List<List<String>> getDatabaseTable(int index) {
        return database.get(index).getTable();
    }

    // removes a table, its name, and the keycolumn based on index
    public void removeTable(int index) {
        database.remove(index);
        tableName.remove(index);
        keyCol.remove(index);
    }

    // create database method checks to see if directory has already been made. 
    // allows user to create a database folder
    public void createDatabase(String directory) {

        if (validPath(directory) == false) {
        	System.out.println("Directory already exists. Change directory name");
        	return; 
        }

        try {
        	File dir = new File(directory);
        	dir.mkdir();
        }
        catch(Exception e) {
            throw new Error(e);
        }
    }

    // if folder already exists, return false
    public boolean validPath(String directory) {
        Path path = Paths.get(directory);

        if (Files.exists(path)) {
            return false; 
        }
        return true; 
    }

    public void writeToFile(List<List<String>> table, String filename) {

        Upload file = new Upload(); 

        file.writeToFile(filename, table); 
    }

    // add table to database array list if it has a unique name
    public boolean addTable(Table table, String name) {

    	int primarykey = table.getKeyColumn();

        if(uniqueTableName(name) == true) {
            database.add(table);
            tableName.add(name);
            if(table.setKeyColumn(primarykey) == false) {
                return false; 
            }
            keyCol.add(primarykey); 
            return true; 
        }
        return false; 
        
    }

    // updates a field in a table of the database based on row and column and
    // finds the table with p
    public boolean updateByRowCol(int row, int col, String value, int p) {

        int tableCount = numTables(); 

        if(p > tableCount - 1 || p < 0) {
            return false; 
        }
        if(database.get(p).updateRecord(row, col, value) == true) {
            return true; 
        }
        return false; 

    }

    // updates field in table found at p based on key value. Can select
    // any value in that row to be replaced by a new value
    public boolean updateByKey(String key, String oldV, String newV, int p) {

        int tableCount = numTables(); 

        if(p > tableCount - 1 || p < 0) {
            return false; 
        }
        if(database.get(p).updateRecordByKey(key, oldV, newV) == true) {
            return true; 
        }
        return false; 

    }

    // returns the position of a table in the database
    public int returnTableNamePos(String input) {

        List<String> tablename = getTableName(); 

        int i = 0; 

        while (i < tablename.size()) {
            if(input.equals(tablename.get(i))) {
                return i; 
            }
            i++;
        }
        return -1;
    }

    // allows user to add a record to a table at a certain 
    // position in the database
    public boolean addToTable(List<String> record, int position) {

        int tableCount = numTables(); 

        if(position <= tableCount && position >= 0) {
            if(database.get(position).insertIntoTable(record) == false) {
                return false; 
            } 
            return true; 
        }
        return false; 
    }

    // removes a record from a table in the database based on its row
    public boolean removeFromTableByRow(int row, int position) {

        int tableCount = numTables(); 

        if(position > tableCount - 1 || position < 0) {
            return false; 
        }
        if(database.get(position).deleteRecord(row) == true) {
            return true; 
        } 
        return false; 

    }

    // removes a record from a table in the database based on its key
    public boolean removeFromTableByKey(String field, int position) {

        int tableCount = numTables(); 

        if(position > tableCount - 1 || position < 0) {
            return false; 
        }
        if(database.get(position).deleteRecordByKey(field) == true) {
            return true; 
        }
        return false;  

    }

    // selects a record from a table based on its key. Returns "Empty set" if not found
    public List<String> selectRecordByKey(String field, int position) {
        
        int tableCount = numTables(); 

        if(position > tableCount - 1 || position < 0) {
            List<String> empty = new ArrayList<String>(1);
            empty.add("Empty set");
            return empty; 
        }
        List<String> record = database.get(position).selectRecordByKey(field);
        return record; 

    }

    // selects a record from a table based on its row. Returns "Empty set" if not found
    public List<String> selectRecordByRow(int row, int position) {

        int tableCount = numTables(); 

        if(position > tableCount - 1 || position < 0) {
            List<String> empty = new ArrayList<String>(1);
            empty.add("Empty set");
            return empty;  
        }
        List<String> record = database.get(position).selectRecord(row); 
        return record; 

    }

    // see if tablename exists in array list
    private boolean tableExists(String name) {

        List<String> names = getTableName(); 

        int i = 0; 

        while (i < names.size()) {
            if (name.equals(names.get(i))) {
                return true; 
            }
            i++;
        }
        return false;

    }

    // sees if table name is unique -> used for addTable function
    public boolean uniqueTableName(String name) {

        for (int i = 0; i < tableName.size(); i++) {
        	if(name.equals(tableName.get(i))) {
                return false; 
            }
        }
        return true; 
    }

    public static void main(String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Database");
        }

        Database tests = new Database("test"); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    public void testing() {

        Database db = new Database("Testdb");
        Table table = new Table(); 
        table.readFromFileToTable("test_files/valid_files/people.txt", table);
        db.addTable(table, "people");

        testUniqueTableNames(db); 
        testTableExists(db);
        testSelectRecordByKey(db);
        testSelectRecordByRow(db);
        testRemoveFromTableByKey(db);
        testRemoveFromTableByRow(db);
        testAddToTable(db);
        testUpdateByRowCol(db);
        testUpdateByKey(db);
        testValidPath(db);
        testAddTable(db);
        testReturnTableNamePos(db);


    }

    private void testUniqueTableNames(Database db) {

        assert(db.uniqueTableName("people") == false);
        assert(db.uniqueTableName("animal") == true); 
    }

    private void testTableExists(Database db) {

        assert(db.tableExists("people") == true);
        assert(db.tableExists("animal") == false);
    }

    private void testSelectRecordByKey(Database db) {

        List<String> record = db.selectRecordByKey("re16621", 0);
        assert(record.get(0).equals("re16621"));
        assert(record.get(1).equals("Rich Ellor"));

        record = selectRecordByKey("test", 0);
        assert(record.get(0).equals("Empty set"));
    }

    private void testSelectRecordByRow(Database db) {

        List<String> record = db.selectRecordByRow(1, 0);
        assert(record.get(0).equals("re16621"));
        assert(record.get(1).equals("Rich Ellor"));

        record = selectRecordByRow(8, 0);
        assert(record.get(0).equals("Empty set"));
    }

    private void testRemoveFromTableByKey(Database db) {

        assert(db.removeFromTableByKey("re16621", 0) == true);
        assert(db.removeFromTableByKey("re16621", 1) == false);
        assert(db.removeFromTableByKey("hello", 0) == false);

    }

    private void testRemoveFromTableByRow(Database db) {

        assert(db.removeFromTableByRow(1, 0) == true);
        assert(db.removeFromTableByRow(0, 1) == false);
        assert(db.removeFromTableByRow(0, 0) == false);
    }

    private void testAddToTable(Database db) {

        Record record = new Record(); 
        record.setRecord("re16621", "Rich Ellor");
        List<String> rec = record.getRecord();
        assert(db.addToTable(rec, 0) == true); 
        record.setRecord("B20251074", "Rich");
        rec = record.getRecord();
        assert(db.addToTable(rec, 0) == true);
    }

    private void testUpdateByRowCol(Database db) {

        assert(db.updateByRowCol(1, 0, "hello", 0) == true);
        assert(db.updateByRowCol(0, 0, "hello", 1) == false);
        assert(db.updateByRowCol(0, 0, "hello", 0) == false);
        assert(db.updateByRowCol(8, 1, "hello", 0) == false);
    }

    private void testUpdateByKey(Database db) {

        assert(db.updateByKey("re16621", "Rich Ellor", "Richard Ellor", 0) == true);
        assert(db.updateByKey("re1662", "Rich Ellor", "Richard Ellor", 0) == false);
        assert(db.updateByKey("re16621", "Rich ", "Richard Ellor", 0) == false);
        assert(db.updateByKey("re16621", "Richard Ellor", "Rich Ellor", 0) == true);
        assert(db.updateByKey("re16621", "Richard Ellor", "Rich Ellor", 1) == false);
    }

    private void testValidPath(Database db) {

        assert(validPath("test_files") == false);
        assert(validPath("test") == true);
    }

    private void testAddTable(Database db) {

        Table table2 = new Table(); 
        assert(db.addTable(table2, "people") == false);
        table2.readFromFileToTable("test_files/valid_files/animal.txt", table2);
        assert(db.addTable(table2, "animal") == true);
    }

    private void testReturnTableNamePos(Database db) {

        assert(db.returnTableNamePos("people") == 0);
        assert(db.returnTableNamePos("animal") == 1);
        assert(db.returnTableNamePos("hello") == -1);
    }

}