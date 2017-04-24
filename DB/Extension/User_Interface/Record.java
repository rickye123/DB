/* This class stores individual records. 
Records contain cells or items, such as an Animals
table, which has an id, name, kind and owner (username)
and a username table which has a username and name of 
the person.

The database needs to be independent of the table. It
needs to be completely general-purpose and hold ANY
type of record, no matter what table it is from and
stores fields which are STRINGS. 

Operations on the Record object are to find 
out how many strings it has, and to get or set individual
strings. 

Records are just arrays of strings

*/

import java.util.*;

public class Record {

    private List<String> record = new ArrayList<String>();

    // set record 
    public void setRecord(String ... data) {

        clearRecord();

        int i = 0; 
        
        for (String s : data) {
            if(findFieldLength(s) == false) {
	            System.out.println("Field is too large");
	            clearRecord(); 
                return; 
            }
            record.add(i, s);
            i++; 
        }

    }

    // getter method 
    public List<String> getRecord() {
        return record; 
    }

    // removes fields in arraylist record
    private void clearRecord() {

        if(!record.isEmpty()) {
            int size = record.size();
            int i = 0; 
            while (i < size) {
                record.remove(0);
                i++;
            }
        }

    }

    // finds the length of String (number of characters)
    // if size is larger than 80 return false
    private boolean findFieldLength(String data) {

        data.toCharArray(); 

        int length = data.length(); 

        if (suitableSize(length) == false) {
            return false; 
        }
        return true; 
    }

    // returns false if length is greater than 80 or less than 0
    private boolean suitableSize(int length) {

        if (length > 80 || length < 0) {
            return false; 
        }
        return true; 

    }

    // finds the length of the record 
    public int findRecordLength(List<String> record) {

        int length = record.size(); 

        return length; 
    
    }

    public static void main (String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        if(!testing) {
            throw new Error("Use java -ea Record");
        }

        Record testRecord = new Record(); 
        testRecord.tests(); 
        System.out.println("All Tests Passed");

    }

    private void tests() {

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

        assert(suitableSize(81) == false);
        assert(suitableSize(60) == true);
        assert(suitableSize(-1) == false);
        assert(suitableSize(20) == true);

        // returns field is too large 
        //Record testRecord3 = new Record(); 
        // testRecord3.setRecord("This is a very long record and should return" +
        //    " false as it is larger than 80 characters");
        

    }

}