import java.util.*;

/* Database class to control all other classes */

public class Database {

    public static void main(String[] args) {

        Database db = new Database(); 

        db.stringRecords(); 

        db.createDatabase();


    }

    public void stringRecords() {

        Record strRecord1 = new Record(); 

        strRecord1.stringSetRecord("Kin", "Tin");
        String[] stringArray = strRecord1.stringGetRecord(); 
        strRecord1.displayStringRecord(stringArray);

        strRecord1.stringSetRecord("Rich", "Ellor", "ABC", "123", "456", "789");
        String[] stringArray2 = strRecord1.stringGetRecord(); 
        strRecord1.displayStringRecord(stringArray2);
    
    }

    public void createDatabase() {

        Record record1 = new Record(); 

        record1.setRecord("Rich", "Ellor");
        List<String> rec1 = record1.getRecord(); 
        record1.displayRecord(rec1);

    }

}