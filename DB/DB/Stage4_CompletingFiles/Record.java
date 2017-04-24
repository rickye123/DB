import java.util.*;

/* This class should store individual records. 
Records contain cells or items, such as an Animals
table, which has an id, name, kind and owner (username)
and a username table which has a username and name of 
the person.

The database needs to be independent of the table. It
needs to be completely general-purpose and hold ANY
type of record, no matter what table it is from and
stores fields which are STRINGS. 

Operations on the Record object may be to find 
out how many strings it has, and to get or set individual
strings. 

Ignore the field names for now and just store the 
string values in a specific order.

Record class is just a place to gather code later */

public class Record {

    private List<String> record = new ArrayList<String>();

    public void setRecord(String ... data) {

        if(!record.isEmpty()) {
            record.clear();
        }

        int i = 0; 
        
        for (String s : data) {
            record.add(i, s);
            i++; 
        }

    }

    public void setRecord2(String[] data) {
    	int i = 0; 

    	for (String s : data) {
    		record.add(i ,s);
    	}
    }

    public List<String> getRecord() {
    	return record; 
    }

    // finds the length of the record 
    public int findRecordLength(List<String> record) {

        int length = record.size(); 

        return length; 
    
    }

    public void displayRecord(List<String> record) {

        int i = 0; 

        System.out.println("Column" + "\t" + "Data"); 
        for (String s : record) {
            System.out.println( (i + 1) + "\t" + record.get(i));
            i++;
        }
        System.out.println("");

    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Record");
        }

        Record testRecord = new Record(); 
        testRecord.testingList(); 
        System.out.println("All Tests Passed");

    }

    private void testingList() {

        Record testRecord1 = new Record(); 
        testRecord1.setRecord("Test", "1");
        List<String> listTest1 = testRecord1.getRecord(); 
        
        assert(listTest1.get(0).equals("Test"));
        assert(listTest1.get(1).equals("1"));
        assert(testRecord1.findRecordLength(listTest1) == 2);

        Record testRecord2 = new Record(); 
        testRecord2.setRecord("Test", "Record", "Multiple", "Values");
        List<String> listTest2 = testRecord2.getRecord(); 
        
        assert(listTest2.get(0).equals("Test"));
        assert(listTest2.get(1).equals("Record"));
        assert(listTest2.get(2).equals("Multiple"));
        assert(listTest2.get(3).equals("Values"));
        assert(testRecord2.findRecordLength(listTest2) == 4);

    }

}