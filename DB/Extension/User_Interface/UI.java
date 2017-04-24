/* This is the User interface class. This is where the main program 
sits and allows the user to manipulate a database using a number 
of commands. 

These commands include:
"OPTIONS" - to show the commands
"SHOW" - shows the tables in the database
"CREATE" - to create a table 
"INSERT" - to insert into a table 
"UPLOAD" - to upload from a file into a table 
"UPDATE" - to update a field in a table 
"DELETE" - to delete a record from a table 
"SELECT" - to select a record from a table
"PRINT" - to print a table to the screen
"EXIT" - to terminate the program. 

These commands are not case-sensitive, so "OPTIONS" means the same as "options". 

*/

import java.util.*; 
import java.io.*;
import java.io.File; 

public class UI {
    
    private Print print = new Print();

    public static void main (String[] args) {
        boolean testing = false; 
        assert(testing = true);

        UI program = new UI(); 

        if(testing) {
            program.testing(); 
        }
        else if (args.length == 0) {
            program.run(); 
        }
        else {
            throw new Error("Use: java UI or java -ea UI for testing");
        }

    }

    // run program 
    public void run() {

        printInstructions();
        
        String databaseName = findDatabaseName(); 

        Database db = new Database(databaseName); 
        runProgram(db); 

    }

    // print instructions for user to the terminal 
    public void printInstructions() {

        System.out.println("Welcome to this database program.\n");
        System.out.print("You should be able to perform a variety of functions\n"
            + "such as: create tables, select records, upload\n"
            + "tables from files, insert records into tables, update\n" 
            + "fields in a table, delete records from tables, and print tables\n\n");
        System.out.print("All the keywords can be accessed by typing \"OPTIONS\"\n"
            + "when prompted for a command. These words are NOT case\n"
            + "sensitive, so \"options\" has the same meaning as \"OPTIONS\"\n\n");
        System.out.println("First, you must enter the name of your database");
        System.out.println("This can only be one word");
        System.out.print("> ");

    }

    // user input to get database name - can only be one word so will throw
    // error message until correct input found 
    public String findDatabaseName() {

        Scanner input = new Scanner(System.in);
        String name = input.nextLine(); 

        while(checkNameValid(name) != true) {
        	System.out.println("Invalid entry. Database name must only be one word");
        	System.out.print("> ");
            name = input.nextLine(); 
        }

        return name; 
    }

    // check that the database name entered is valid 
    private boolean checkNameValid(String input) {

        if(input.length() == 0) {
            return false; 
        }

        String[] parts = input.split(" ");

        if (parts.length > 1) {
            return false; 
        }
        return true; 
    }

    // run the main program and continue until user types "EXIT" or "exit"
    public void runProgram(Database db) {
  
        boolean running = true; 
        while (running) {
            
            System.out.println("\nEnter some commands. Type \"OPTIONS\" "
            + "for details. \"EXIT\" to exit");
            System.out.print("> ");
            List<String> input = new ArrayList<String>();
            
            String line = getLine(); 
            Scanner lineScan = new Scanner(line);
            while(lineScan.hasNext()) {
                input.add(lineScan.next());
            }
            if(continueProgram(input) == false) {
                System.out.println("User has exited program");
                running = false; 
            }
            else {
                parseProgram(input, db);
            }
        }

    }

    // get the line entered from the user
    public String getLine() {

        Scanner userInput = new Scanner(System.in);

        String line = userInput.nextLine();
        while(acceptInputFromUser(line) == false) {
            System.out.println("Invalid Entry. Enter again. Type \"OPTIONS\""
            + " for details. \"EXIT\" to exit");
            line = userInput.nextLine(); 
        }
        return line; 

    }

    // throw error if user presses enter without typing anything
    public boolean acceptInputFromUser(String line) {
        
        if (line.length() == 0) {
            return false; 
        }
        return true; 
    }

    // continue program unless user types "EXIT" or "exit"
    public boolean continueProgram(List<String> input) {

        if (input.size() == 1 && 
           (input.get(0).equals("EXIT") || input.get(0).equals("exit"))) {
            return false; 
        }
        return true; 
    }

    // keywords that the user can type - can't be made any smaller unforunately
    public void parseProgram(List<String> input, Database db) {

        if(input.get(0).equals("create") || input.get(0).equals("CREATE")) {
            createCmd(input, db);
        }
        else if (input.get(0).equals("upload") || input.get(0).equals("UPLOAD")) {
            uploadCmd(input, db);
        }
        else if (input.get(0).equals("insert") || input.get(0).equals("INSERT")) {
            insertCmd(input, db);
        }
        else if (input.get(0).equals("delete") || input.get(0).equals("DELETE")) {
            deleteCmd(input, db);
        }
        else if (input.get(0).equals("update") || input.get(0).equals("UPDATE")) {
            updateCmd(input, db);
        }
        else if (input.get(0).equals("select") || input.get(0).equals("SELECT")) {
            selectCmd(input, db);
        }
        else if (input.get(0).equals("print") || input.get(0).equals("PRINT")) {
            printCmd(input, db);
        }
        else if (input.get(0).equals("options") || input.get(0).equals("OPTIONS")) {
            optionsCmd(input);
        }
        else if (input.get(0).equals("show") || input.get(0).equals("SHOW")) {
            showCmd(input, db);
        }
        else if (input.get(0).equals("write") || input.get(0).equals("WRITE")) {
            writeCmd(input, db);
        }
        else {
            System.out.println("Did not recognise that command. Try again");
        }
    }

    // run if input.get(0) is "create". Check if valid statement and 
    // split the input if a "/SET" is found. If /SET exists then run 
    // interpretCreateWithKey, else run interpretCreateWithoutKey
    public void createCmd(List<String> input, Database db) {

        if(validCreateCommand(input) == false) {
            System.out.print("Error: CREATE statement needs a table name and parameters\n");
            System.out.println("separated by a '/:' character. Enter query again");
            return; 
        }

        String tableName = input.get(1);

        String[] header = findRecord(input, 2);
        String[] set = header[header.length - 1].split("/SET ");
        header[header.length - 1] = set[0]; 
        if(set.length > 1) {
            interpretCreateWithKey(db, header, set, tableName); 
            return; 
        }

        interpretCreateWithoutKey(db, header, tableName);

    }

    // check to see if create statement is valid
    private boolean validCreateCommand(List<String> input) {

        if(input.size() < 3) {
            return false; 
        }
        if(input.get(2).equals("/:")) {
            return false; 
        }
        return true; 

    }

    // create table with the heading found in createCmd function. As no key
    // specified, the key is set to the first column (0)
    private void interpretCreateWithoutKey(Database db, String[] h, String n) {

        Table table = new Table(n);

        table.createTable(0, h);
        if(db.addTable(table, n) == false) {
            System.out.println("Error: Table name already exists");
            return; 
        }
        System.out.printf("Table \"%s\" successfully created\n", n); 

    }

    // create a table and give it the key found after "/SET"
    private void interpretCreateWithKey(Database db, String[] h, String[] s, String n) {

        if(validColInput(s[1]) == false) {
        	System.out.println("Error: Invalid column");
            return; 
        }
        int key = Integer.parseInt(s[1]);
        Table table = new Table(n);

        if(table.createTable(key, h) == false) {
            System.out.println("Error: Out of range");
            return; 
        }
        if(db.addTable(table, n) == false) {
            System.out.println("Error: Table name already exists");
            return; 
        }
        System.out.printf("Table \"%s\" successfully created\n", n); 

    }

    // run if input.get(0) is "insert". Check if statement valid and 
    // then run interpretInsert
    public void insertCmd(List<String> input, Database db) {

        if (validInsertCommand(input, db) == false) {
            System.out.print("Error: INSERT statement needs a table name and parameters\n");
            System.out.println("separated by a '/:' character. Enter query again");
            return; 
        }

        interpretInsert(input, db);

    }

    // insert statement cannot have less than 3 arguments and the table
    // name must exist 
    private boolean validInsertCommand(List<String> input, Database db) {

        if (input.size() < 3) {
            return false; 
        }
        if(searchThroughDatabase(input, db) == false) {
            return false; 
        } 
        return true; 

    }

    // search through database object to see if there are tables, and then
    // if the table with the specified name exists
    public boolean searchThroughDatabase(List<String> input, Database db) {

        List<Table> database = db.getDatabase(); 

        if (database.size() <= 0) {
            return false; 
        }
        if(nameExists(input.get(1), db) == false) {
            return false; 
        }
        return true; 

    }

    // find the position of the table in db object and convert
    // input into an array of strings. See if the input is valid
    // and then convert String to arraylist and add to tables in 
    // db object
    public void interpretInsert(List<String> input, Database db) {

        String tableName = input.get(1);

        int position = db.returnTableNamePos(tableName);
        String[] record = findRecord(input, 2);
        if(insertValidInput(db, position, record) == false) {
            System.out.println("Error: Rows not same length as columns");
            return; 
        }

        List<String> rec = convertToList(record);

        if(db.addToTable(rec, position) == false) {
            System.out.println("Error: either adding key into key column "
            + "or number of columns in row of different length to header");
            return; 
        } 
        System.out.println("Row successfully inserted\n");

    }

    // convert an array of strings to an array list 
    private List<String> convertToList(String[] record) {

        List<String> rec = new ArrayList<String>(); 

        int i = 0; 

        for (String s : record) {
            rec.add(i, s);
            i++; 
        }

        return rec; 

    }

    // see if the record entered has the same number of columns 
    // as the table it is being entered into 
    public boolean insertValidInput(Database db, int pos, String[] r) {

        List<List<String>> table = db.getDatabaseTable(pos);

        int colCount = table.get(0).size(); 

        if (r.length != colCount) {
            return false; 
        }
        return true; 
    }

    // run if input.get(0) is "print". Check if statement valid, 
    // find position of table in db object and then pass this 
    // to printTable method in print object so that a '*' character
    // appears in the table printed to the terminal 
    public void printCmd(List<String> input, Database db) {

        if(validPrintCommand(input, db) == false) {
            System.out.print("Error: Invalid print command\n");
            return; 
        }

        int pos = db.returnTableNamePos(input.get(1));
        List<List<String>> table = db.getDatabaseTable(pos); 
        int key = db.getKeyCol(pos);
        print.printTable(table, key);

    }

    // print statement can only be two arguments. Check if the 
    // table (input at input.get(1)) exists in the database
    private boolean validPrintCommand(List<String> input, Database db) {

        if (input.size() != 2) {
            return false; 
        }

        if(searchThroughDatabase(input, db) == false) {
            return false; 
        } 
        return true;
    }

    // searches through table names and sees if input is 
    // equal to any of them
    public boolean nameExists(String input, Database db) {
        
        int i = 0; 

        List<String> tablename = db.getTableName(); 

        while (i < tablename.size()) {
            if(input.equals(tablename.get(i))) {
                return true; 
            }
            i++;
        }
        return false; 
    }


    public void writeCmd(List<String> input, Database db) {

        if(validWriteCommand(input, db) == false) {
        	System.out.println("Error: WRITE statement needs"
            + " 2 arguments, a valid tablename and the file to be "
            + "written to");
            return; 

        }
        String tableName = input.get(1);
        String fileName = input.get(2);
        int position = db.returnTableNamePos(tableName);
        List<List<String>> table = db.getDatabaseTable(position);
        db.writeToFile(table, fileName);

        System.out.printf("Table %s successfully written to %s.txt", tableName, fileName); 

    }

    public boolean validWriteCommand(List<String> input, Database db) {

        if(input.size() != 3) {
            return false; 
        }
        if(searchThroughDatabase(input, db) == false) {
            return false; 
        }
        return true;
    }

    // run if input.get(0) is "upload". Upload can only be 3
    // arguments if there is no key specified or 5 if there is
    public void uploadCmd(List<String> input, Database db) {

        if (input.size() != 3 && input.size() != 5) {
            System.out.println("Error: UPLOAD statement needs 2"
                + " arguments (table name and file) and an optional"
                + " set command which takes an integer");
            return; 
        }

        if(input.size() == 3) {
            interpretUploadWithoutKey(input, db);
            return; 
        }
        if(input.size() == 5) {
            interpretUploadWithKey(input, db);
        }

    }

    // run if the size of input is 3, no key has been specified. Find the
    // tablename and filename and then call readFromFileTable to see if 
    // valud file or file exists. If it does, add it to the table, providing
    // the tablename is unique 
    private void interpretUploadWithoutKey(List<String> input, Database db) {

        String tableName = input.get(1), fileName = input.get(2);

        Table table = new Table(tableName);
        if(table.readFromFileToTable(fileName, table) == false) {
            System.out.println("Error: Invalid file or file doesn't exist");
            return; 
        }

        if(db.addTable(table, tableName) == false) {
            System.out.println("Error: Table name already exists");
            return; 
        } 
        System.out.printf("Table \"%s\" successfully created\n", tableName);

    }

    // this is run if a SET command is provided after the filename. See 
    // if SET keyword exists. Then readFromFileToTable and addToTable 
    // similar to the interpretUploadWithoutKey function
    private void interpretUploadWithKey(List<String> input, Database db) {

        if(validUploadWithKey(input) == false) {
            System.out.println("Error: needs a SET keyword followed by an integer");
            return; 
        }

        String tableName = input.get(1), fileName = input.get(2);
        int key = Integer.parseInt(input.get(4));

        Table table = new Table(tableName);
        if(table.readFromFileToTable(fileName, table, key) == false) {
            System.out.println("Error: Invalid file, file doesn't exist,"
                + " or duplicates in key column");
            return; 
        }

        if(db.addTable(table, tableName) == false) {
            System.out.println("Error: Table name already exists");
            return; 
        } 
        System.out.printf("Table \"%s\" successfully created\n", tableName);

    }

    // if input is larger than 3 and is equal to 5, check if 4th String is "set"
    // or "SET" and check to see if 5th String is a valid column (integer)
    private boolean validUploadWithKey(List<String> input) {

        if (!(input.get(3).equals("SET")) && !(input.get(3).equals("set"))) {
            return false; 
        }
        if(validColInput(input.get(4)) == false) {
            return false; 
        }
        return true; 
    }

    // run if input.get(0) is "update". See if command is valid and then
    // if the 3rd string is row, find the command by looking at the row, 
    // else find the command by the value in its key column
    public void updateCmd(List<String> input, Database db) {
         
        if(validUpdateCommand(input, db) == false) {
            System.out.println("Error: UPDATE statement "
                + "needs valid file name and either: row followed by\n"
                + "an integer col followed by an integer then the\n"
                + "new value to be put in, or a key value, followed by\n"
                + "the value to be changed and the new value to be put in");
            return; 
        }

        if(input.get(2).equals("row")) {
            interpretUpdateRow(input, db); 
            return; 
        }

        interpetUpdateByKey(input, db);
    }

    // when updating key, needs the value in the key column, the 
    // value to be changed in that row, then the new value it should be
    // changed to
    public void interpetUpdateByKey(List<String> input, Database db) {

        String[] field = findRecord(input, 2); 
        if(field.length < 3) {
            System.out.println("Error: Must have at least three arguments "
            + "separated by a '/:' character");
            return; 
        }

        String key = field[0], oldValue = field[1], newValue = field[2]; 
        String tableName = input.get(1);
        int position = db.returnTableNamePos(tableName);

        if(db.updateByKey(key, oldValue, newValue, position) == false) {
            System.out.println("Error: Field or table doesn't exist, or trying to "
                + "add duplicate into key column");
            return; 
        }

        System.out.printf("Record with key containing \"%s\" in table \"%s\" successfully "
            + "deleted\n", key, tableName); 
    }

    // take input and concatenate Strings in the array list and then split
    // them up into an array of Strings whenever a ':' character is found
    public String[] findRecord(List<String> input, int index) {

        String field = input.get(index); 

        int i = index + 1;
        while (i < input.size()) {
            field = field.concat(" " + input.get(i));
            i++;
        }
        String[] record = field.split(" /: ");

        return record; 
    }

    // if updating field by row see if "row" and "col" are found and
    // are followed by valid values (integers). Then concatenate the 
    // input following column integer, as this can be anything with spaces, 
    // commas, etc.
    public void interpretUpdateRow(List<String> input, Database db) {

        if(validUpdateRowCommand(input, db) == false) {
            System.out.println("Error: Invalid input");
            return; 
        }
        int row = Integer.parseInt(input.get(3));
        int col = Integer.parseInt(input.get(5));
        String tableName = input.get(1);
        int position = db.returnTableNamePos(tableName);
        String field = concatField(input, 6); 

        if(db.updateByRowCol(row, col, field, position) == false) {
            System.out.println("Error: Invalid row or col selected, or"
                + " adding duplicate into key column");
            return; 
        }

        System.out.printf("Row %d in table \"%s\" successfully "
            + "updated\n", row, tableName);     
    }

    // take arraylist and create one string
    private String concatField(List<String> input, int index) {
        String field = input.get(index);

        int i = index + 1; 
        while (i < input.size()) {
            field = field.concat(" " + input.get(i));
            i++;
        }
        return field; 
    }

    // check to see that the value following "row" is an integer, 
    // then that the next String is "col" and then the following value
    // is also an integer
    private boolean validUpdateRowCommand(List<String> input, Database db) {
        if(validRowInput(input.get(3)) == false) {
            return false; 
        }
        if(!input.get(4).equals("col")) {
            return false; 
        }
        if(validColInput(input.get(5)) == false) {
            return false; 
        }
        return true; 
    }

    // column input can be any integer, including 0. This is to 
    // ensure that no floating point numbers or characters are 
    // passed as the row or col values
    public boolean validColInput(String input) {

        char str[] = input.toCharArray(); 

        int i = 0; 

        while (i < str.length) {
            if(str[i] < '0' || str[i] > '9') {
                return false; 
            }
            i++;
        }
        return true; 

    }

    // valid update statement needs at least 5 Strings, and there
    // must be a table that exists in the database with the specified tablename
    private boolean validUpdateCommand(List<String> input, Database db) {

        if(input.size() < 5) {
            return false; 
        }
        if(searchThroughDatabase(input, db) == false) {
            return false;
        } 
        return true; 
    }

    // run if input.get(0) is "delete". Will remove an entire row 
    // from the a specified table. Check if statement is valid 
    // and then can remove table based on its row or its key
    public void deleteCmd(List<String> input, Database db) {
        
        if(validDeleteCommand(input, db) == false) {
            System.out.println("Error: DELETE statement "
                + "needs valid file name and either: row followed by "
                + "an integer, or just key value");
        }

        if (input.get(2).equals("row")) {
            interpretDeleteRow(input, db);
            return; 
        }

        interpretDeleteByKey(input, db); 
        
    }

    // delete statement needs at least 3 arguments and the table
    // needs to exist in database
    public boolean validDeleteCommand(List<String> input, Database db) {
        if(input.size() < 3) {
            return false; 
        }

        if(searchThroughDatabase(input, db) == false) {
            return false;
        } 
        return true; 
    }

    // if user specifies that they wish to delete by row, e.g. 
    // "DELETE table row 2" then run this method. Check that the value
    // following row is an intger, then remove the row from table
    public void interpretDeleteRow(List<String> input, Database db) {

        if(validDeleteRowCommand(input, db) == false) {
            System.out.println("Error: Invalid command");
            return; 
        }

        int selectRow = Integer.parseInt(input.get(3));
        String tableName = input.get(1);
        int position = db.returnTableNamePos(tableName);

        if(db.removeFromTableByRow(selectRow, position) == false) {
            System.out.println("Error: Invalid row selected");
            return; 
        }

        System.out.printf("Row %d in table \"%s\" successfully "
            + "deleted\n", selectRow, tableName); 
    }

    // user may wish to remove a row based on the value in its key
    // column. Finds the position of table in db object, concatenates
    // the fields (3rd argument) as this can be of a varied size with 
    // spaces etc. 
    public void interpretDeleteByKey(List<String> input, Database db) {

        String field = concatField(input, 2); 
        String tableName = input.get(1);
        int position = db.returnTableNamePos(tableName);

        if(db.removeFromTableByKey(field, position) == false) {
            System.out.println("Error: Field doesn't exist");
            return; 
        }

        System.out.printf("Record with key containing \"%s\" in table \"%s\" successfully "
            + "deleted\n", field, tableName); 
    }

    // a valid statement including a "row" value must be only 4 arguments long
    // and the value following row must be an integer 
    private boolean validDeleteRowCommand(List<String> input, Database db) {
        if (input.size() != 4) {
            return false; 
        }
        if (validRowInput(input.get(3)) == false) {
            return false;
        }
        return true; 
    }

    // run if input.get(0) is "select". Check if statement valid
    // and user can choose to select by row or by key
    public void selectCmd(List<String> input, Database db) {

        if(validSelectCommand(input, db) == false) {
            System.out.println("Error: SELECT needs ...");
            return; 
        }

        if (input.get(2).equals("row")) {
            interpretSelectByRow(input, db);
            return; 
        }

        interpretSelectByKey(input, db);

    }

    // if user selects by row. Checks to see if input valid and 
    // then finds the record from the db object and its header
    // and then prints this using the print object
    public void interpretSelectByRow(List<String> input, Database db) {

        if(input.size() != 4) {
            System.out.println("Error: Invalid command");
            return; 
        }
        if (validRowInput(input.get(3)) == false) {
            System.out.println("Error: Invalid command");
            return; 
        }

        int selectRow = Integer.parseInt(input.get(3));
        int position = db.returnTableNamePos(input.get(1));
        List<String> record = db.selectRecordByRow(selectRow, position);

        if(record.get(0).equals("Empty set")) {
            System.out.println("Error: Invalid row selected");
            return; 
        }

        List<String> header = db.getDatabaseTable(position).get(0);
        print.displayRecord(record, header, db, position);
    }

    // if user chooses to select by key, check if input is valid 
    // and then concatenate third argument as this can be of a varied length. 
    // Return invalid row is row with key value doesn't exist
    public void interpretSelectByKey(List<String> input, Database db) {

        if(input.size() < 3) {
            System.out.println("Error: Invalid command");
            return; 
        }

        String field = concatField(input, 2);
        int position = db.returnTableNamePos(input.get(1));
        List<String> record = db.selectRecordByKey(field, position); 

        if(record.get(0).equals("Empty set")) {
            System.out.println("Error: Invalid row selected");
            return; 
        }
        List<String> header = db.getDatabaseTable(position).get(0);
        print.displayRecord(record, header, db, position);

    }

    // check if select command is valid - must be at least 3 arguments and 
    // the table specified must be in the database
    private boolean validSelectCommand(List<String> input, Database db) {
        if(input.size() < 3) {
            return false; 
        }
        if(searchThroughDatabase(input, db) == false) {
            return false; 
        }
        return true; 
    }

    // row can only be an integer, but cannot be row 0, as this is
    // the header and cannot be selected
    public boolean validRowInput(String input) {

        char str[] = input.toCharArray(); 

        if(str[0] == '0') {
            return false; 
        }

        int i = 1; 

        while (i < str.length) {
            if(str[i] < '0' || str[i] > '9') {
                return false; 
            }
            i++;
        }
        return true; 

    }

    // run if input.get(0) is "show". Can only be one argument, 
    // show, and there must be tables in database. Prints 
    // the database names. 
    public void showCmd(List<String> input, Database db) {

        List<Table> database = db.getDatabase(); 

        if (input.size() > 1) {
            System.out.println("Error: SHOW only accepts one command");
            return; 
        }

        if (database.size() <= 0) {
            System.out.println("Error: No tables in database"); 
            return; 
        }

        String[] names = convertNames(db); 
        print.printTablesInDatabase(db, names);

    }

    //
    public String[] convertNames(Database db) {

        List<String> tableNames = db.getTableName(); 
        String[] names = new String[tableNames.size()];

        int i = 0; 

        while (i < tableNames.size()) {
            names[i] = tableNames.get(i);
            i++; 
        }
        return names; 

    }

    // run if input.get(0) is "options". Details the program for the user
    public void optionsCmd(List<String> input) {
        System.out.println("Options:");
        System.out.println("This program has a number of keywords to allow you to"
            + " manipulate your database");
        System.out.println(); 
        System.out.println("CREATE: use this to create a table in database");
        System.out.println("E.G. \"CREATE tableName field1 : field2 ...\".\n"
            + "Can also specify the key column by entering \"/SET colVal\"");
        System.out.println("E.G. \"CREATE tableName field1 : field2 /SET 1\" "
            + "\nwill make the field2 column the key. If this is not specified"
            + "\nthe key column defaults to 0 (first column)");
        System.out.println(); 
        System.out.println("UPLOAD: use this to create a table from a readable "
            + "text file");
        System.out.println("E.G. \"UPLOAD tableName textfile.txt\" "
            + "OR \"UPLOAD tableName textfile.txt SET 1\" to set the key column."
            + "If key not specified, it defaults to the first column.");
        System.out.println(); 
        System.out.println("SELECT: use this to select a record from a table in "
            + "the database");
        System.out.println("E.G. \"SELECT tableName row 1\" (by row) "
            + "OR \"SELECT tableName ab123\" (by key)");
        System.out.println(); 
        System.out.println("DELETE: use this to delete a record from a table in "
            + "the database");
        System.out.println("E.G. \"DELETE tableName row 1\" (by row) "
            + "OR \"DELETE tableName ab123\" (by key)");
        System.out.println(); 
        System.out.println("UPDATE: use this to update a field in a record from a "
            + "table in the database");
        System.out.println("E.G. \"UPDATE tableName row 1 col 1 newfield\" (by row, col) "
            + "OR \"UPDATE tableName keyValue : fieldInRow : newfield\" (by key)");
        System.out.println(); 
        System.out.println("INSERT: use this to insert a record into a "
            + "table in the database");
        System.out.println("E.G. \"INSERT tableName field1 : field2 ...\" (by row, col)");
        System.out.println(); 
        System.out.println("PRINT: use this to print a table in the database");
        System.out.println("E.G. \"PRINT tableName\"");
        System.out.println(); 
        System.out.println("SHOW: use this to show the tables in the database");
        System.out.println(); 
        System.out.println("WRITE: use this to write tables to a .txt file");
        System.out.println("E.G. \"WRITE tableName file.txt\"");
        System.out.println();
        System.out.println("EXIT: use this to exit program");
    }

    // unit testing 
    private void testing() {

        testCheckNameValid();
        testAcceptInputFromUser(); 
        testDeriveInput(); 
        testValidCreateCommand();
        testConvertNames();
        testValidDeleteCommand();
        testValidDeleteRowCommand();
        testValidSelectCommand();
        testConcatField();
        testValidUpdateRowCommand();
        testValidUpdateCommand();
        testFindRecord();
        testValidInsertCommand();
        testSearchThroughDatabase();
        testNameExists();
        testConvertToList();
        testValidPrintCommand();
        testInsertValidInput();
        testValidUploadWithKey();
        System.out.println("All Tests Passed");
    }

    private void testCheckNameValid() {
        assert(checkNameValid("") == false); 
        assert(checkNameValid("some input") == false);
        assert(checkNameValid("one") == true); 
    }

    private void testAcceptInputFromUser() {
        assert(acceptInputFromUser("") == false);
        assert(acceptInputFromUser("this is a line") == true);
    }

    private void testDeriveInput() {

        List<String> input1 = new ArrayList<String>(); 
        input1.add("exit"); 
        assert(continueProgram(input1) == false);
        List<String> input2 = new ArrayList<String>(); 
        input2.add("EXIT");
        assert(continueProgram(input2) == false);
        List<String> input3 = new ArrayList<String>(); 
        input3.add("exit now");
        assert(continueProgram(input3) == true);
        List<String> input4 = new ArrayList<String>(); 
        input4.add("EXIT now");
        assert(continueProgram(input4) == true);
        List<String> input5 = new ArrayList<String>(); 
        input5.add("hello");
        assert(continueProgram(input5) == true);

    }

    private void testValidCreateCommand() {

        List<String> input1 = new ArrayList<String>(); 
        input1.add("CREATE");
        input1.add("table");
        input1.add("field1");
        assert(validCreateCommand(input1) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("create");
        input2.add("table");
        input2.add("field1");
        input2.add("/:");
        input2.add("field2");
        assert(validCreateCommand(input2) == true);
        List<String> input3 = new ArrayList<String>(); 
        input3.add("CREATE");
        input3.add("table");
        assert(validCreateCommand(input3) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("create");
        input4.add("table");
        input4.add("/:");
        assert(validCreateCommand(input4) == false);
    }

    private void testConvertNames() {

        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        Table animal = new Table(); 
        people.readFromFileToTable("test_files/valid_files/animal.txt", animal);
        db.addTable(animal, "animal");
        String[] names = convertNames(db); 
        assert(names[0].equals("people"));
        assert(names[1].equals("animal"));
    }

    private void testValidDeleteCommand() {

        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");

        List<String> input1 = new ArrayList<String>(); 
        input1.add("DELETE");
        input1.add("people");
        input1.add("re16621");
        assert(validDeleteCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>(); 
        input2.add("delete");
        input2.add("people");
        input2.add("re16621");
        assert(validDeleteCommand(input2, db) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("delete");
        input3.add("people");
        assert(validDeleteCommand(input3, db) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("delete");
        input4.add("animal");
        input4.add("1");
        assert(validDeleteCommand(input4, db) == false);
    }

    private void testValidDeleteRowCommand() {

       Database db = new Database("test1");

       List<String> input1 = new ArrayList<String>();
       input1.add("DELETE");
       input1.add("people");
       input1.add("row");
       input1.add("1");
       assert(validDeleteRowCommand(input1, db) == true);
       List<String> input2 = new ArrayList<String>(); 
       input2.add("delete");
       input2.add("people");
       input2.add("row");
       assert(validDeleteRowCommand(input2, db) == false);
       List<String> input3 = new ArrayList<String>();
       input3.add("delete");
       input3.add("people");
       input3.add("row");
       input3.add("1");
       input3.add("a");
       assert(validDeleteRowCommand(input3, db) == false);
       List<String> input4 = new ArrayList<String>(); 
       input4.add("DELETE");
       input4.add("people");
       input4.add("row");
       input4.add("abc");
       assert(validDeleteRowCommand(input4, db) == false);
       List<String> input5 = new ArrayList<String>();
       input5.add("delete");
       input5.add("people");
       input5.add("row");
       input5.add("1.4");
       assert(validDeleteRowCommand(input5, db) == false);
    }

    private void testValidSelectCommand() {

        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");

        List<String> input1 = new ArrayList<String>();
        input1.add("SELECT");
        input1.add("people");
        input1.add("re16621");
        assert(validSelectCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>(); 
        input2.add("select");
        input2.add("people");
        input2.add("re16621");
        assert(validSelectCommand(input2, db) == true);
        List<String> input3 = new ArrayList<String>(); 
        input3.add("select");
        input3.add("animal");
        input3.add("1");
        assert(validSelectCommand(input3, db) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("select");
        input4.add("people");
        assert(validSelectCommand(input4, db) == false);

    }

    private void testConcatField() {

        List<String> input = new ArrayList<String>(); 
        input.add("select");
        input.add("people");
        input.add("50,");
        input.add("Overhill Road,");
        input.add("Stratton, Cirencester");

        String field = concatField(input, 2); 
        assert(field.equals("50, Overhill Road, Stratton, Cirencester"));
    }

    private void testValidUpdateRowCommand() {

        Database db = new Database("test1");
        List<String> input1 = new ArrayList<String>();
        input1.add("UPDATE");
        input1.add("people");
        input1.add("row");
        input1.add("1");
        input1.add("col");
        input1.add("1");
        assert(validUpdateRowCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("update");
        input2.add("people");
        input2.add("row");
        input2.add("1.2");
        input2.add("col");
        input2.add("1.2");
        assert(validUpdateRowCommand(input2, db) == false);
    }

    private void testValidUpdateCommand() {

        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        List<String> input1 = new ArrayList<String>(); 
        input1.add("UPDATE");
        input1.add("people");
        input1.add("row");
        input1.add("1");
        input1.add("col");
        input1.add("1");
        assert(validUpdateCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("update");
        input2.add("people");
        input2.add("row");
        input2.add("1");
        input2.add("col");
        input2.add("1");
        assert(validUpdateCommand(input2, db) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("update");
        input3.add("people");
        input3.add("row");
        input3.add("1");
        assert(validUpdateCommand(input3, db) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("UPDATE");
        input4.add("animal");
        input4.add("row");
        input4.add("1");
        input4.add("col");
        input4.add("1");
        assert(validUpdateCommand(input4, db) == false);
    }

    private void testFindRecord() {

        List<String> input1 = new ArrayList<String>();
        input1.add("insert");
        input1.add("people");
        input1.add("re16621");
        input1.add("/:");
        input1.add("Rich Ellor");
        String[] record1 = findRecord(input1, 2);
        assert(record1[0].equals("re16621"));
        assert(record1[1].equals("Rich Ellor"));
        List<String> input2 = new ArrayList<String>();
        input2.add("Rich");
        input2.add("/:");
        input2.add("Ellor");
        input2.add("/:");
        input2.add("Rich Ellor");
        input2.add("/:");
        input2.add("re16621");
        String[] record2 = findRecord(input2, 0);
        assert(record2[0].equals("Rich"));
        assert(record2[1].equals("Ellor"));
        assert(record2[2].equals("Rich Ellor"));
        assert(record2[3].equals("re16621"));
    }

    private void testValidInsertCommand() {
        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        List<String> input1 = new ArrayList<String>(); 
        input1.add("INSERT");
        input1.add("people");
        input1.add("field1");
        assert(validInsertCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("insert");
        input2.add("people");
        input2.add("field1");
        assert(validInsertCommand(input2, db) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("insert");
        input3.add("people");
        assert(validInsertCommand(input3, db) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("INSERT");
        input4.add("animal");
        input4.add("field1");
        assert(validInsertCommand(input4, db) == false);
    }

    private void testSearchThroughDatabase() {
        Database db = new Database("test1");
        List<String> input1 = new ArrayList<String>();
        input1.add("upload");
        input1.add("table");
        input1.add("people.txt");
        assert(searchThroughDatabase(input1, db) == false);
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        List<String> input2 = new ArrayList<String>();
        input2.add("upload");
        input2.add("people");
        input2.add("people.txt");
        assert(searchThroughDatabase(input2, db) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("upload");
        input3.add("animal");
        input3.add("animal.txt");
        assert(searchThroughDatabase(input3, db) == false);
    }

    private void testNameExists() {
        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        String input1 = "people";
        assert(nameExists(input1, db) == true);
        String input2 = "animal";
        assert(nameExists(input2, db) == false);
    }

    private void testConvertToList() {

    	String[] input1 = {"Rich", "Ellor"};
        List<String> rec1 = convertToList(input1); 
        assert(rec1.get(0).equals("Rich"));
        assert(rec1.get(1).equals("Ellor"));
    }

    private void testValidPrintCommand() {

        Database db = new Database("test1");
        Table people = new Table(); 
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        List<String> input1 = new ArrayList<String>();
        input1.add("PRINT");
        input1.add("people");
        assert(validPrintCommand(input1, db) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("print");
        input2.add("people");
        assert(validPrintCommand(input2, db) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("print");
        input3.add("people");
        input3.add("animal");
        assert(validPrintCommand(input3, db) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("print");
        assert(validPrintCommand(input4, db) == false);
        List<String> input5 = new ArrayList<String>();
        input5.add("print");
        input5.add("animal");
        assert(validPrintCommand(input5, db) == false);
    }

    private void testInsertValidInput() {

        Database db = new Database("test1");
        Table people = new Table();
        people.readFromFileToTable("test_files/valid_files/people.txt", people);
        db.addTable(people, "people");
        List<String> input1 = new ArrayList<String>();
        input1.add("insert");
        input1.add("people");
        input1.add("cd");
        input1.add("/:");
        input1.add("fe");
        String tableName = input1.get(1);
        int position = db.returnTableNamePos(tableName);
        String[] record1 = findRecord(input1, 2);
        assert(insertValidInput(db, position, record1) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("INSERT");
        input2.add("people");
        input2.add("cd");
        String[] record2 = findRecord(input2, 2);
        assert(insertValidInput(db, position, record2) == false);

    }

    private void testValidUploadWithKey() {

        List<String> input1 = new ArrayList<String>();
        input1.add("upload");
        input1.add("people");
        input1.add("people.txt");
        input1.add("SET");
        input1.add("4");
        assert(validUploadWithKey(input1) == true);
        List<String> input2 = new ArrayList<String>();
        input2.add("upload");
        input2.add("people");
        input2.add("people.txt");
        input2.add("set");
        input2.add("3");
        assert(validUploadWithKey(input2) == true);
        List<String> input3 = new ArrayList<String>();
        input3.add("upload");
        input3.add("people");
        input3.add("people.txt");
        input3.add("hello");
        assert(validUploadWithKey(input3) == false);
        List<String> input4 = new ArrayList<String>();
        input4.add("upload");
        input4.add("people");
        input4.add("people.txt");
        input4.add("set");
        input4.add("1.3");
        assert(validUploadWithKey(input4) == false);

    }

/*


*/

}