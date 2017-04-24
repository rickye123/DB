/* This class will check whose turn it is and determine 
whether there is a winner */

public class Board {

    private Symbols symbol; 
    // accepts the symbol as its turn and returns whether
    // it is player 1 or player 2's turn
    public void playerTurn(char turn) {

        try {
            if(turn == Symbols.X.getValue()) {
                System.out.print("It's player 1's turn ");
            }
            else if (turn == Symbols.O.getValue()) {
                System.out.print("It's player 2's turn ");
            }
        } 
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

    }

    // determines who is the winner based on whose turn it is
    private void winner(char turn) {

        try {
            if(turn == Symbols.X.getValue()) {
                System.out.println("Player 1 Wins");
            }
            else if(turn == Symbols.O.getValue()) {
                System.out.println("Player 2 Wins");
            }
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }
    }

    // swithces from 'X' to 'O' or from 'O' to 'X'
    public char switchTurn(char turn) {

        try {
            if(turn == Symbols.X.getValue()) {
                turn = Symbols.O.getValue(); 
            }
            else if (turn == Symbols.O.getValue()) {
                turn = Symbols.X.getValue(); 
            }
        }
        catch (Exception e) {
        	System.out.println("Exception caught: " + e);
        }

        return turn; 
    }

    // returns the symbol to determine whether 'O' or 'X' will
    // be printed on next turn 
    public Symbols symbolTurn(char turn, Symbols symbol) {
 
        try {
            if (turn == Symbols.X.getValue()) {
                symbol = Symbols.X;
            }
            else if (turn == Symbols.O.getValue()) {
                symbol = Symbols.O;
            }
        }
        catch (Exception e) {
        	System.out.println("Exception caught at " + e);
        }

        return symbol; 
    }
    
    // tests to see whether the game has been won or drawn (no spaces left)
    public boolean gameFinished(Display board, Board game, char turn) {

        char[][] grid = new char[3][3]; 

        grid = board.getGrid();

        if(someoneWon(grid) == true) {
        	game.winner(turn); 
        	return true; 
        }
        if(noMoreSpaces(grid) == true) {
            System.out.println("No more possible moves. Game Over!");
        	return true; 
        }
        return false; 
    }

    // cycles through grid and if there are ' ' characters present 
    // then there are still moves to be mades 
    public boolean noMoreSpaces(char[][] grid) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(grid[i][j] == Symbols.B.getValue()) {
                    return false; 
                }
            }
        }
        return true; 
    }

    // looks at each winning combo and if the cells match then someone
    // has won
    public boolean someoneWon(char[][] grid) {

        if (line(grid[0][0], grid[0][1], grid[0][2])) return true;
        if (line(grid[1][0], grid[1][1], grid[1][2])) return true;
        if (line(grid[2][0], grid[2][1], grid[2][2])) return true;
        if (line(grid[0][0], grid[1][0], grid[2][0])) return true;
        if (line(grid[0][1], grid[1][1], grid[2][1])) return true;
        if (line(grid[0][2], grid[1][2], grid[2][2])) return true;
        if (line(grid[0][0], grid[1][1], grid[2][2])) return true;
        if (line(grid[2][0], grid[1][1], grid[0][2])) return true;
        return false; 

    }

    // if characters match and are not equal to ' ' then there is a 
    // winning line 
    public boolean line(char x, char y, char z) {
        return x == y && y == z && z != Symbols.B.getValue();        
    }

}