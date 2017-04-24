/* This class is responsible for determiming whether there are
duplicate fields in a key column. It finds the key column 
from the table object and sees if fields have the same values in them. 

If this is true, then a false boolean value is returned. */

import java.util.*;

public class Key {
    

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

        table.createTable(0, "First name", "Last name");

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

        table.createTable(0, "First name", "Last name");
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

        table.createTable(1, "First name", "Last name");
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