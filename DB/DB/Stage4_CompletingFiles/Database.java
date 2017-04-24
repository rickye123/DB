import java.util.*;

/* Database class to control all other classes */

public class Database {

	private Record record; 
	private Table table;

    public static void main(String[] args) {

        Database db = new Database(); 

        db.createDatabase();

    }

    public void createDatabase() {

        record = new Record(); 
        Table people = new Table(); 
        
        people.readFromFile("people.txt", people);

        List<List<String>> tab = people.getTable(); 
        people.displayTable(tab);

        Table animal = new Table();

        animal.readFromFile("animal.txt", animal);
        List<List<String>> tab1 = animal.getTable(); 
        animal.displayTable(tab1);


    }

    void databaseTesting() {

/*        String[] arr = {"Rich", "E", "123"};
        record.setRecord2(arr);*/
/*
        List<String> record1 = record.getRecord();
        record.displayRecord(record1);*/

        String[][] arr2 = {
        	{"ab123", "Jo"}, 
        	{"cd146", "Sam"}, 
        	{"ef789", "Amy"}, 
        	{"gh012", "Pete"}
        };

        System.out.println(arr2.length); // number of rows is 4
        System.out.println(arr2[0].length);

        table.createTable("username", "name");

        record.setRecord(arr2[0]);
        List<String> record1 = record.getRecord();
        record.displayRecord(record1);
        table.insertIntoTable(record1);
        List<List<String>> tab = table.getTable();

        record.setRecord(arr2[1]);
        record1 = record.getRecord();
        record.displayRecord(record1);
        table.insertIntoTable(record1);
        tab = table.getTable();

        record.setRecord(arr2[2]);
        record1 = record.getRecord();
        record.displayRecord(record1);
        table.insertIntoTable(record1);
        tab = table.getTable();

        record.setRecord(arr2[3]);
        record1 = record.getRecord();
        record.displayRecord(record1);
        table.insertIntoTable(record1);
        tab = table.getTable();
        table.displayTable(tab);    	
    }

}