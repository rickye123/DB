/* This class is responsible for input and output. It will display 
the noughts and crosses board and accept input from the user, e.g. 
their move, or whether they or the computer can go first */

import java.util.*; 

class Display {

    // creates new 2d array of size 3 by 3 
    private char[][] grid = new char[3][3]; 

    // display a 3 by 3 grid, with ' '
    public void setGrid(Symbols symbol) {

        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grid[i][j] = Symbols.B.getValue();
            }
        }

    }

    public char[][] getGrid() {
        return grid; 
    }

    // displays grid with columns and numbers on the side 
    public void displayGrid() {
        System.out.println("\n    1   2   3");
        System.out.print("a   " + grid[0][0] + " | " + grid[0][1]  + " | " + grid[0][2]);
        System.out.println("\n   ---+---+---");
        System.out.println("b   " + grid[1][0] + " | " + grid[1][1]  + " | " + grid[1][2]);
        System.out.println("   ---+---+---");
        System.out.println("c   " + grid[2][0] + " | " + grid[2][1]  + " | " + grid[2][2] + "\n");
    }

    // ask for a 1 or a 2 from the user and return this value - throw errors if invalid input
    public String askForGameType() {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter \"1\" to play computer or \"2\" to play two player");
        String userInput = input.nextLine();

        while(validGameType(userInput) != true) {
            System.out.println("Invalid input. Enter 1 or 2");
            userInput = input.nextLine();
        }

        return userInput; 

    }

    // check to see if input from askForGameType() is valid 
    private boolean validGameType(String input) {
    
        if(input.length() != 1) {
            return false; 
        }

        if (input.charAt(0) < '1' || input.charAt(0) > '2') {
            return false; 
        }

        return true; 
    }

    // asks the user who should go first, them or the computer. Will only accept
    // "User" and "Computer" as input 
    public char promptForFirstTurn() {
        System.out.println("Decide who goes first: Type \"User\" or \"Computer\"");
        Scanner input = new Scanner(System.in);

        char turn; 

        String userInput = input.nextLine();

        while(validEntry(userInput) != true) {
            System.out.println("Invalid Entry. Enter \"User\" or \"Computer\"");
            userInput = input.nextLine();
        }
        turn = determineFirstTurn(userInput);
        return turn; 
    }

    // determine if input is valid - can only be "User" or "Computer", otherwise false
    public boolean validEntry(String input) {

        if(input.equals("User")) {
            return true; 
        }
        else if (input.equals("Computer")) {
            return true; 
        }
        return false; 
        
    }

    // if entry is "User" then 'X' goes first, else 'O' goes first 
    public char determineFirstTurn(String input) {

        char symbol = Symbols.B.getValue(); 

        try {
            if (input.equals("User")) {
                symbol = Symbols.X.getValue();
            }
            else if (input.equals("Computer")) {
                symbol = Symbols.O.getValue();
            }
        } catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }
        return symbol;

    }

    // asks for input from the user, continues asking until input valid 
    public void promptInput(Board game, Display board, Symbols symbol, Position pos) {
        
        Scanner input = new Scanner(System.in);

        System.out.println("Please type in a letter and a number such as b3:");
        String userInput = input.nextLine();

        while(pos.validPosition(userInput, board) == false) {
            System.out.println("Invalid move. Enter again:");
            userInput = input.nextLine();
        }

    }

    // adds 'X' or 'O' to grid depending on the current symbol 
    public void playerMove(Position pos, Symbols symbol) {
        int[] coordinates = new int[2]; 

        coordinates = pos.getPosition();

        int row = coordinates[0]; 
        int col = coordinates[1]; 

        if (symbol == Symbols.X) {
            grid[row][col] = Symbols.X.getValue();
        }
        else {
            grid[row][col] = Symbols.O.getValue();
        }
    }   


}