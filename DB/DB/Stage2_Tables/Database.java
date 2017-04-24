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

        table = new Table(); 

        table.createTable("First name", "Last name", "username");
        record.setRecord("Rich", "Ellor", "re16621");

        String[] arr = {"Rich", "E", "123"};
        List<String> rec1 = new ArrayList<String>(); 

        int i = 0; 
        for (String s : arr) {
            rec1.add(i, s);
            i++;
        }

        table.insertIntoTable(rec1);
        
        List<String> rec = record.getRecord(); 
        table.insertIntoTable(rec); 
        List<List<String>> printTable = table.getTable(); 
        table.displayTable(printTable);

        table.updateTable(1, 2, "B20251074");
        table.displayTable(printTable);

        record.setRecord("Becca", "Brown", "bb16622");
        rec = record.getRecord(); 
        table.insertIntoTable(rec);
        printTable = table.getTable(); 
        table.displayTable(printTable);


    }

}