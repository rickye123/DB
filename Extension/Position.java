/* This class will determine whether the user's input is valid, i.e.
whether the characters entered are correct, and whether the 
cell is already occupied. It also contains all the positions that are
possible for the AI to execute */

class Position {

    private int[] pos = new int[2]; 
    private Symbols symbol; 

    public int[] getPosition() {
        return pos; 
    }

    // check valid char and put position into array pos
    // then check to see if cell already occupied
    public boolean validPosition(String input, Display board) {

        char[][] grid = new char[3][3]; 
        Symbols symbol = Symbols.B; 

        grid = board.getGrid();

        if(invalidChar(input) == false) {
            return false; 
        }

        pos[0] = input.charAt(0) - 'a'; 
        pos[1] = input.charAt(1) - '1'; 

        if (grid[pos[0]][pos[1]] != Symbols.B.getValue()) {
            return false; 
        }
        return true; 
    }

    // check that input is valid 
    private boolean invalidChar(String input) {

        if(input.length() != 2) {
            return false; 
        }
        if (input.charAt(0) < 'a' || input.charAt(0) > 'c') {
            return false; 
        }
        if (input.charAt(1) < '1' || input.charAt(1) > '3') {
            return false; 
        }
        return true; 
    }

    // give a random position based on the cells that are empty (' ')
    public void randomPosition (int[][] emptyCells) {
        
        int randomRow = (int)(Math.random() * 3);
        int randomCol = (int)(Math.random() * 3);
        boolean status = false; 

        while(status != true) {
            randomRow = (int)(Math.random() * 3);
            randomCol = (int)(Math.random() * 3);
            if(emptyCells[randomRow][randomCol] != 0) {
                status = true; 
            }

        }

        pos[0] = randomRow; 
        pos[1] = randomCol;

    }

    // find the position to put 'O' when cpu can win horizontally
    public boolean winHorizontal(char[][] cells) {

        int i = 0, j = 0; 

        while (i < 3) {
            if ( (cells[i][j] == Symbols.O.getValue()) && 
                 (cells[i][j + 1] == Symbols.O.getValue()) && 
                 (cells[i][j + 2] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j + 2; 
                    return true;
            }
            if ( (cells[i][j] == Symbols.O.getValue()) && 
                 (cells[i][j + 2] == Symbols.O.getValue()) && 
                 (cells[i][j + 1] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j + 1;
                    return true;
            }
            if ( (cells[i][j + 1] == Symbols.O.getValue()) && 
                 (cells[i][j + 2] == Symbols.O.getValue()) && 
                 (cells[i][j] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j; 
                    return true;
            }
            i++; 
        }
        return false; 
    }

    // find the position to put 'O' when cpu can win vertically
    public boolean winVertical(char[][] cells) {

        int i = 0, j = 0; 

        while (j < 3) {
            if ( (cells[i][j] == Symbols.O.getValue()) && 
                 (cells[i + 1][j] == Symbols.O.getValue()) && 
                 (cells[i + 2][j] == Symbols.B.getValue())) {
                    pos[0] = i + 2; pos[1] = j;
                    return true;
            }
            if ( (cells[i][j] == Symbols.O.getValue()) && 
                 (cells[i + 2][j] == Symbols.O.getValue()) && 
                 (cells[i + 1][j] == Symbols.B.getValue())) {
                    pos[0] = i + 1; pos[1] = j;
                    return true;
            }
            if ( (cells[i + 1][j] == Symbols.O.getValue()) && 
                 (cells[i + 2][j] == Symbols.O.getValue()) && 
                 (cells[i][j] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j; 
                    return true;
            }
            j++; 
        }
        return false; 
    }
    
    // determine whether cpu can win diagonally 
    public boolean winDiagonal(char[][] cells) {

        if(winDiagRight(cells) == true) {
            return true; 
        }
        if(winDiagLeft(cells) == true) {
            return true; 
        }
        return false; 

    }

    // find the position to put 'O' when cpu can win diagonally
    // from top left to bottom right
    public boolean winDiagRight(char[][] cells) {
        if ( (cells[0][0] == Symbols.O.getValue()) &&
             (cells[1][1] == Symbols.O.getValue()) &&
             (cells[2][2] == Symbols.B.getValue()) ) {
                pos[0] = 2; pos[1] = 2;
                return true; 
        }
        if ( (cells[0][0] == Symbols.O.getValue()) &&
             (cells[2][2] == Symbols.O.getValue()) &&
             (cells[1][1] == Symbols.B.getValue()) ) {
                pos[0] = 1; pos[1] = 1;
                return true; 
        }
        if ( (cells[1][1] == Symbols.O.getValue()) &&
             (cells[2][2] == Symbols.O.getValue()) &&
             (cells[0][0] == Symbols.B.getValue()) ) {
                pos[0] = 0; pos[1] = 0;
                return true; 
        }
        return false; 
    }

    // find the position to put 'O' when cpu can win diagonally
    // from top right to bottom left
    public boolean winDiagLeft(char[][] cells) {
        if ( (cells[2][0] == Symbols.O.getValue()) &&
             (cells[1][1] == Symbols.O.getValue()) &&
             (cells[0][2] == Symbols.B.getValue()) ) {
                pos[0] = 0; pos[1] = 2;
                return true; 
        }
        if ( (cells[2][0] == Symbols.O.getValue()) &&
             (cells[0][2] == Symbols.O.getValue()) &&
             (cells[1][1] == Symbols.B.getValue()) ) {
                pos[0] = 1; pos[1] = 1;
                return true; 
        }
        if ( (cells[1][1] == Symbols.O.getValue()) &&
             (cells[0][2] == Symbols.O.getValue()) &&
             (cells[2][0] == Symbols.B.getValue()) ) {
                pos[0] = 2; pos[1] = 0;
                return true; 
        }
        return false; 
    }

    // find the position to put 'O' when user can win horizontally
    public boolean blockHorizontal(char[][] cells) {

        int i = 0, j = 0; 

        while (i < 3) {
            if( (cells[i][j] == Symbols.X.getValue()) && 
                (cells[i][j + 1] == Symbols.X.getValue()) &&
                (cells[i][j + 2] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j + 2; 
                    return true; 
            }
            if( (cells[i][j] == Symbols.X.getValue()) && 
                (cells[i][j + 2] == Symbols.X.getValue()) &&
                (cells[i][j + 1] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j + 1; 
                    return true; 
            }
            if( (cells[i][j + 1] == Symbols.X.getValue()) && 
                (cells[i][j+ 2] == Symbols.X.getValue()) &&
                (cells[i][j] == Symbols.B.getValue())) {
                    pos[0] = i; pos[1] = j; 
                    return true; 
            }
            i++;
        }
        return false; 
    }

    // find the position to put 'O' when user can win vertically
    public boolean blockVertical(char[][] cells) {

        int i = 0, j = 0; 

        while (j < 3) {
            if( (cells[i][j] == Symbols.X.getValue() ) && 
                (cells[i + 1][j] == Symbols.X.getValue()) && 
                (cells[i + 2][j] == Symbols.B.getValue())) {
                    pos[0] = i + 2; pos[1] = j; 
                    return true; 
            }
            if( (cells[i][j] == Symbols.X.getValue() ) && 
                (cells[i + 2][j] == Symbols.X.getValue()) &&
                (cells[i + 1][j] == Symbols.B.getValue()) ) {
                    pos[0] = i + 1; pos[1] = j; 
                    return true; 
            }
            if( (cells[i + 1][j] == Symbols.X.getValue() ) && 
                (cells[i + 2][j] == Symbols.X.getValue()) &&
                (cells[i][j] == Symbols.B.getValue()) ) {
                    pos[0] = i; pos[1] = j; 
                    return true; 
            }
            j++;
        }
        return false; 
    }

    // determine whether cpu needs to block diagonally 
    public boolean blockDiagonal(char[][] cells) {

        if(blockDiagRight(cells) == true) {
            return true; 
        }
        if(blockDiagLeft(cells) == true) {
            return true; 
        }
        return false; 

    }

    // find the position to put 'O' when user can win diagonally
    // from top left to bottom right
    public boolean blockDiagRight(char[][] cells) {

        if( (cells[0][0] == Symbols.X.getValue()) && 
            (cells[1][1] == Symbols.X.getValue()) &&
            (cells[2][2] == Symbols.B.getValue())) {
            pos[0] = 2;  pos[1] = 2; 
            return true; 
        }
        if( (cells[0][0] == Symbols.X.getValue()) &&
            (cells[2][2] == Symbols.X.getValue()) &&
            (cells[1][1] == Symbols.B.getValue())) {
            pos[0] = 1;  pos[1] = 1; 
            return true; 
        }
        if( (cells[1][1] == Symbols.X.getValue()) &&
            (cells[2][2] == Symbols.X.getValue()) &&
            (cells[0][0] == Symbols.B.getValue())) {
            pos[0] = 0;  pos[1] = 0; 
            return true; 
        }
        return false;
    }

    // find the position to put 'O' when user can win diagonally
    // from top right to bottom left
    public boolean blockDiagLeft(char[][] cells) {

        if( (cells[0][2] == Symbols.X.getValue()) && 
            (cells[1][1] == Symbols.X.getValue()) &&
            (cells[2][0] == Symbols.B.getValue())) {
            pos[0] = 2; pos[1] = 0; 
            return true; 
        }
        if( (cells[0][2] == Symbols.X.getValue()) && 
            (cells[2][0] == Symbols.X.getValue()) &&
            (cells[1][1] == Symbols.B.getValue())) {
            pos[0] = 1; pos[1] = 1; 
            return true; 
        }
        if( (cells[2][0] == Symbols.X.getValue()) &&
            (cells[1][1] == Symbols.X.getValue()) &&
            (cells[0][2] == Symbols.B.getValue())) {
            pos[0] = 0; pos[1] = 2; 
            return true; 
        }
        return false; 
    }

}