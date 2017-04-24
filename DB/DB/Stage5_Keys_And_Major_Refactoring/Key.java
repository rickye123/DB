import java.util.*;

/* First stage: assume that key is the first field 
Key can be automatically generated, e.g. AUTO_INCREMENT in sql 

Make sure that keys are unique, i.e. that there can't be two records
with the same key in the same table. 

Keys can be used as a more stable way to refer to records, rather than 
using row numbers - the row number of a record changes as other 
records are inserted or deleted, but the key doesn't change. */

public class Key {
    
    // need to automatically generate a key if the user doesn't specify one

    // method which searches the key column to find if they are duplicates 
    public boolean searchForDuplicates(List<List<String>> table, int key) {

        int i = 1; 
        
        while(i < table.size()) {
            int j = 1; 
            while (i + j < table.size()) {
                if (table.get(i).get(key).equals(table.get(i + j).get(key))) {
                    return true; 
                }
                j++;
            }
            i++;
        }
        return false; 
    }

    
    public boolean keySearch(List<List<String>> table, int key) {

        if(searchForDuplicates(table, key) == true) {
            return false; 
        }
        return true; 

    }

    // methods which allow the user to set which row the key is? - that can 
    // go into Table class? 

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Key");
        }

        Key tests = new Key(); 
        tests.testing(); 
        System.out.println("All Tests Passed");

    }

    private void testing() {

        noDuplicatesTest();
        duplicatesTest();

    }

    private void noDuplicatesTest() {

        Table table = new Table();
        Record record = new Record(); 
        Key key = new Key();

        table.createTable("First name", "Last name");

        record.setRecord("Rich", "Ellor");
        List<String> rec = record.getRecord();
        table.setKeyColumn(0);
        table.insertIntoTable(rec);
        List<List<String>> tab = table.getTable(); 
        record.setRecord("Rick", "Elliot");
        rec = record.getRecord();
        table.insertIntoTable(rec);
        tab = table.getTable();
        int myKey = table.getKeyColumn(); 
        assert(searchForDuplicates(tab, myKey) == false);

    }

    private void duplicatesTest() {

        duptest1();
        duptest2();

    }

    private void duptest1() {
        Table table = new Table();
        Record record = new Record(); 

        table.createTable("First name", "Last name");
        record.setRecord("Rich", "Ellor");

        List<String> rec = record.getRecord(); 

        table.insertIntoTable(rec);
        List<List<String>> tab = table.getTable(); 
        record.setRecord("Rich", "Elliot");
        rec = record.getRecord();
        assert(table.insertIntoTable(rec) == false);

    }

    private void duptest2() {
        Table table = new Table();
        Record record = new Record(); 

        table.createTable("First name", "Last name");
        record.setRecord("Rich", "Ellor");

        List<String> rec = record.getRecord(); 
        table.setKeyColumn(1);

        table.insertIntoTable(rec);
        List<List<String>> tab = table.getTable(); 
        record.setRecord("Ricky", "Ellor");
        rec = record.getRecord();
        assert(table.insertIntoTable(rec) == false);

    }

}