/* This class is responsible for handling the AI, i.e.
its moves (block or win) */

class AI {

    private Symbols symbol; 

    // gets the position from pos object and displays to the board 
    void cpuMove(Position pos, Symbols symbol, Display board) {

    	char[][] grid = new char[3][3]; 

    	grid = board.getGrid();
        int[] coordinates = new int[2]; 

        coordinates = pos.getPosition();

        int row = coordinates[0]; 
        int col = coordinates[1]; 

        grid[row][col] = Symbols.O.getValue();

    }

    // determines the move to be made by first finding the empty cells
    // and then deciding whether to do a win move, a block move, or a 
    // random move
    void commenceMove(Display board, Position pos) {

        char[][] grid = new char[3][3]; 
        Symbols symbol = Symbols.B; 
        int[][] emptyCells = new int[3][3]; 

        grid = board.getGrid(); 

        findEmptyCells(emptyCells, grid); 

        if(determineMove(board, pos, grid) == false) {
            pos.randomPosition(emptyCells); 
        }

    }

    // finds the empty cells in the array and assigns values to them
    // if cell is blank, then a 1 is assigned (this is useful for finding
    // a random position)
    void findEmptyCells(int[][] emptyCells, char[][] grid) {

        int row = 0;

        for (int i = 0; i < 3; i++) {
            int col = 0; 
            for (int j = 0; j < 3; j++) {
                
                if(grid[i][j] == Symbols.B.getValue()) {
                    emptyCells[row][col] = 1;                 
                }
                else {
                    emptyCells[row][col] = 0;               
                }
                col++;
            }
            row++;
        }
    }

    // can computer do a move that wins, or one that blocks opponent?
    boolean determineMove(Display board, Position pos, char[][] grid) {

      if(winMove(board, pos, grid) == true) {
            return true; 
        }
        if(blockMove(board, pos, grid) == true) {
            return true; 
        }

        return false; 
    }

    // looks at the symbols on the board and checks if it can win 
    boolean winMove(Display board, Position pos, char[][] grid) {

        char[][] occupiedCells = new char[3][3]; 
 
        observeBoard(occupiedCells, grid);
        if(checkForWin(occupiedCells, pos) == true) {
            return true; 
        }
        return false; 

    }

    // return the values of the board and put into array occupiedCells
    void observeBoard(char[][] occupiedCells, char[][] grid) {

        int row = 0;

        for (int i = 0; i < 3; i++) {
            int col = 0; 
            for (int j = 0; j < 3; j++) {
                
                if(grid[i][j] == Symbols.X.getValue()) {
                    occupiedCells[row][col] = Symbols.X.getValue();                 
                }
                else if (grid[i][j] == Symbols.O.getValue()) {
                    occupiedCells[row][col] = Symbols.O.getValue();               
                }
                else {
                    occupiedCells[row][col] = Symbols.B.getValue();
                }
                col++;
            }
            row++;
        }

    }

    // checks moves horizontally, vertically and diagonally for a win move
    boolean checkForWin(char[][] occupiedCells, Position pos) {
        if (pos.winHorizontal(occupiedCells) == true) {
            return true; 
        }
        if(pos.winVertical(occupiedCells) == true) {
            return true; 
        }
        if(pos.winDiagonal(occupiedCells) == true) {
            return true;                  
        }
        return false; 
    } 

    // determine whether opponent needs to be blocked 
    boolean blockMove(Display board, Position pos, char[][] grid) {
               
        char[][] occupiedCells = new char[3][3]; 
 
        observeBoard(occupiedCells, grid);
        if(checkForBlock(occupiedCells, pos) == true) {
            return true; 
        }
        return false; 

    }

    // checks horizontally, vertically, and diagonally whether a block 
    // move is needed
    boolean checkForBlock(char[][] occupiedCells, Position pos) {
        if (pos.blockHorizontal(occupiedCells) == true) {
            return true; 
        }
        if(pos.blockVertical(occupiedCells) == true) {
            return true; 
        }
        if(pos.blockDiagonal(occupiedCells) == true) {
            return true;                  
        }
        return false; 
    } 

}