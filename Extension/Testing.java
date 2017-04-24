/* This class is solely for testing purposes */

public class Testing {

    private Board game = new Board(); 
    private Position pos = new Position();
    private Display board = new Display();
    private AI cpu = new AI();
    private Symbols symbol; 

    // tests a number of functions 
    public void executeTests() {

        determineMoveTests();
        noMoreSpacesTests();
        checkForBlockTests(); 
        blockDiagonalTests();
        blockVerticalTests();
        blockHorizontalTests(); 
        checkForWinTests();
        winDiagonalTests();
        winVerticalTests();
        winHorizontalTests();
        determineFirstTurnTests();
        validEntryTests();
        lineTests();
        someoneWonTests();
        symbolTurnTests();
        switchTurnTests();
        positionTests(); 
        System.out.println("All Tests Passed");

    }

    // tests determineMove() function 
    private void determineMoveTests() {

        // valid tests 
        char[][] testGrid = { {'O', 'X', 'X'}, {'O', 'X', ' '}, {' ', ' ', ' '} };
        assert(cpu.determineMove(board, pos, testGrid) == true); 
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', ' ', ' '}, {'X', 'O', 'X'} };
        assert(cpu.determineMove(board, pos, testGrid) == true); 
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {'X', 'O', 'X'} };
        assert(cpu.determineMove(board, pos, testGrid) == true); 
        testGrid = new char[][] { {' ', 'O', ' '}, {' ', 'O', 'X'}, {' ', 'X', 'X'} };
        assert(cpu.determineMove(board, pos, testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {'O', ' ', ' '}, {' ', ' ', 'O'}, {' ', ' ', 'X'} };
        assert(cpu.determineMove(board, pos, testGrid) == false);
        testGrid = new char[][] { {' ', ' ', ' '}, {'X', ' ', ' '}, {' ', ' ', 'O'} };
        assert(cpu.determineMove(board, pos, testGrid) == false);

    }

    // tests on noMoreSpaces function
    private void noMoreSpacesTests() {

        // spaces left 
        char[][] testGrid = { {'X', 'X', 'X'}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(game.noMoreSpaces(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', ' '}, {'X', 'X', 'X'}, {' ', 'O', ' '} };
        assert(game.noMoreSpaces(testGrid) == false);
        testGrid = new char[][] { {' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '} };
        assert(game.noMoreSpaces(testGrid) == false);

        // no more spaces 
        testGrid = new char[][] { {'X', 'O', 'X'}, {'X', 'O', 'O'}, {'O', 'X', 'O'} };
        assert(game.noMoreSpaces(testGrid) == true);
        testGrid = new char[][] { {'O', 'O', 'X'}, {'X', 'X', 'O'}, {'O', 'X', 'X'} };
        assert(game.noMoreSpaces(testGrid) == true);

    }

    // tests blockDiagonal() function 
    private void blockDiagonalTests() {

        // valid tests
        char[][] testGrid = { {'X', 'O', ' '}, {'O', 'X', ' '}, {' ', 'O', ' '} };
        assert(pos.blockDiagonal(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', ' ', ' '}, {'X', 'O', 'X'} };
        assert(pos.blockDiagonal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {'X', 'O', 'X'} };
        assert(pos.blockDiagonal(testGrid) == true);
        testGrid = new char[][] { {'O', 'X', 'X'}, {'O', 'X', ' '}, {' ', ' ', ' '} };
        assert(pos.blockDiagonal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', ' ', 'O'}, {'X', 'X', ' '} };
        assert(pos.blockDiagonal(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', ' '}, {' ', 'X', 'O'}, {'X', 'O', ' '} };
        assert(pos.blockDiagonal(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'O', 'O'}, {'O', 'X', 'X'} };
        assert(pos.blockDiagonal(testGrid) == false);
        testGrid = new char[][] { {'X', 'O', 'X'}, {' ', 'O', 'O'}, {'O', ' ', 'X'} };
        assert(pos.blockDiagonal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'X', 'O', ' '}, {' ', 'O', 'X'} };
        assert(pos.blockDiagonal(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'X', 'O', 'X'}, {'O', 'X', 'O'} };
        assert(pos.blockDiagonal(testGrid) == false);

    }

    // tests blockHorizontal() function
    private void blockHorizontalTests() {
        
        // valid tests
        char[][] testGrid = { {'X', 'X', ' '}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'X'}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'O'}, {'X', 'X', ' '}, {' ', 'X', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'O'}, {'X', ' ', 'X'}, {' ', 'X', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'O'}, {' ', 'X', 'X'}, {' ', 'X', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', ' '}, {'X', 'X', ' '} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', ' '}, {'X', ' ', 'X'} };
        assert(pos.blockHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', ' '}, {' ', 'X', 'X'} };
        assert(pos.blockHorizontal(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'X', ' '}, {'X', 'O', 'O'} };
        assert(pos.blockHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {' ', 'X', ' '}, {'X', ' ', 'O'} };
        assert(pos.blockHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'X', ' '}, {'O', 'X', 'O'} };
        assert(pos.blockHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'O', 'O', 'X'} };
        assert(pos.blockHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(pos.blockHorizontal(testGrid) == false);
    }

    // tests blockVertical() function 
    private void blockVerticalTests() {

        // valid tests
        char[][] testGrid = { {'X', 'O', ' '}, {'X', 'O', ' '}, {' ', 'O', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', 'O', ' '}, {'X', 'O', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'X', 'O', ' '}, {'X', 'O', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {'O', 'X', 'O'}, {'O', 'X', ' '}, {' ', ' ', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', ' ', 'O'}, {' ', 'X', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'O'}, {' ', 'X', 'O'}, {' ', 'X', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'O', 'X'}, {'O', 'X', ' '} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'O', ' '}, {'O', ' ', 'X'} };
        assert(pos.blockVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', ' '}, {' ', 'O', 'X'}, {' ', 'X', 'X'} };
        assert(pos.blockVertical(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', 'X'}, {'X', 'O', 'O'} };
        assert(pos.blockVertical(testGrid) == false);
        testGrid = new char[][] { {'O', ' ', 'O'}, {' ', 'O', 'X'}, {'X', ' ', 'O'} };
        assert(pos.blockVertical(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {' ', 'X', 'O'} };
        assert(pos.blockVertical(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(pos.blockVertical(testGrid) == false);

    }

    // tests checkForWin() function 
    private void checkForWinTests() {

        // valid tests 
        char[][] testGrid = { {'O', 'O', ' '}, {' ', 'X', ' '}, {' ', 'X', ' '} };
        assert(cpu.checkForWin(testGrid, pos) == true); 
        testGrid = new char[][] { {'O', ' ', 'X'}, {' ', 'X', ' '}, {'O', 'X', ' '} };
        assert(cpu.checkForWin(testGrid, pos) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'X', 'O', ' '}, {'O', 'X', 'O'} };
        assert(cpu.checkForWin(testGrid, pos) == true);
        testGrid = new char[][] { {'X', 'O', 'O'}, {'X', 'O', ' '}, {' ', ' ', ' '} };
        assert(cpu.checkForWin(testGrid, pos) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'X', ' '}, {'X', 'O', 'O'} };
        assert(cpu.checkForWin(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {' ', 'X', 'X'}, {'X', ' ', 'O'} };
        assert(cpu.checkForWin(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {' ', 'X', 'O'} };
        assert(cpu.checkForWin(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(cpu.checkForWin(testGrid, pos) == false);

    }

    // tests checkForBlock() function
    private void checkForBlockTests() {

        // valid tests
        char[][] testGrid = { {'X', 'X', ' '}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(cpu.checkForBlock(testGrid, pos) == true); 
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', 'O', ' '}, {'X', 'O', ' '} };
        assert(cpu.checkForBlock(testGrid, pos) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {'X', 'O', 'X'} };
        assert(cpu.checkForBlock(testGrid, pos) == true);
        testGrid = new char[][] { {'O', 'X', 'X'}, {'O', 'X', ' '}, {' ', ' ', ' '} };
        assert(cpu.checkForBlock(testGrid, pos) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'O', ' '}, {'O', 'X', 'X'} };
        assert(cpu.checkForBlock(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'O', 'O'}, {'O', ' ', 'X'} };
        assert(cpu.checkForBlock(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'X', 'O', ' '}, {' ', 'O', 'X'} };
        assert(cpu.checkForBlock(testGrid, pos) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'X', 'O', 'X'}, {'O', 'X', 'O'} };
        assert(cpu.checkForBlock(testGrid, pos) == false);

    }

    // tests winHorizontal() function
    private void winHorizontalTests() {

        // valid tests
        char[][] testGrid = { {'O', 'O', ' '}, {' ', 'X', ' '}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'O'}, {' ', 'X', ' '}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', ' '}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'X'}, {'O', 'O', ' '}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'X'}, {'O', ' ', 'O'}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'X'}, {' ', 'O', 'O'}, {' ', 'X', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'X', ' '}, {'O', 'O', ' '} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'X', ' '}, {'O', ' ', 'O'} };
        assert(pos.winHorizontal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'X', ' '}, {' ', 'O', 'O'} };
        assert(pos.winHorizontal(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'X', 'X'}, {' ', 'X', ' '}, {'X', 'O', 'O'} };
        assert(pos.winHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {' ', 'X', ' '}, {'X', ' ', 'O'} };
        assert(pos.winHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {' ', 'X', ' '}, {'O', 'X', 'O'} };
        assert(pos.winHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'O', 'O', 'X'} };
        assert(pos.winHorizontal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(pos.winDiagonal(testGrid) == false);

    }

    // tests winVertical() function 
    private void winVerticalTests() {

        // valid tests
        char[][] testGrid = { {'O', 'X', ' '}, {'O', 'X', ' '}, {' ', 'X', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'X'}, {' ', 'X', ' '}, {'O', 'X', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', ' '}, {'O', 'X', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {'X', 'O', 'X'}, {'X', 'O', ' '}, {' ', ' ', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'X', ' ', 'X'}, {' ', 'O', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'X'}, {' ', 'O', 'X'}, {' ', 'O', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {' ', 'X', 'O'}, {'X', 'O', ' '} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {' ', 'X', ' '}, {'X', ' ', 'O'} };
        assert(pos.winVertical(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', ' '}, {' ', 'X', 'O'}, {' ', 'O', 'O'} };
        assert(pos.winVertical(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', 'X'}, {'X', 'O', 'O'} };
        assert(pos.winVertical(testGrid) == false);
        testGrid = new char[][] { {'O', 'X', 'O'}, {' ', 'X', 'X'}, {'X', ' ', 'O'} };
        assert(pos.winVertical(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {' ', 'X', 'O'} };
        assert(pos.winVertical(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(pos.winVertical(testGrid) == false);

    }

    // tests winDiagonal() function
    private void winDiagonalTests() {

        // valid tests
        char[][] testGrid = { {'O', 'X', ' '}, {'X', 'O', ' '}, {' ', 'X', ' '} };
        assert(pos.winDiagonal(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', 'X'}, {' ', ' ', ' '}, {'O', 'X', 'O'} };
        assert(pos.winDiagonal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'X', 'O', ' '}, {'O', 'X', 'O'} };
        assert(pos.winDiagonal(testGrid) == true);
        testGrid = new char[][] { {'X', 'O', 'O'}, {'X', 'O', ' '}, {' ', ' ', ' '} };
        assert(pos.winDiagonal(testGrid) == true);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'X', ' ', 'X'}, {'O', 'O', ' '} };
        assert(pos.winDiagonal(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', ' '}, {' ', 'O', 'X'}, {'O', 'X', ' '} };
        assert(pos.winDiagonal(testGrid) == true);

        // invalid tests
        testGrid = new char[][] { {' ', 'O', 'O'}, {' ', 'X', 'X'}, {'X', 'O', 'O'} };
        assert(pos.winDiagonal(testGrid) == false);
        testGrid = new char[][] { {'O', 'X', 'O'}, {' ', 'X', 'X'}, {'X', ' ', 'O'} };
        assert(pos.winDiagonal(testGrid) == false);
        testGrid = new char[][] { {' ', 'O', 'X'}, {'O', 'X', ' '}, {' ', 'X', 'O'} };
        assert(pos.winDiagonal(testGrid) == false);
        testGrid = new char[][] { {' ', 'X', 'O'}, {'O', 'X', 'O'}, {'X', 'O', 'X'} };
        assert(pos.winDiagonal(testGrid) == false);

    }

    // tests determineFirstTurn() function
    private void determineFirstTurnTests() {
        assert(board.determineFirstTurn("User") == 'X');
        assert(board.determineFirstTurn("Computer") == 'O');
    }

    // tests validEntry() function 
    private void validEntryTests() {

        // valid tests
        assert(board.validEntry("User") == true);
        assert(board.validEntry("Computer") == true);

        // invalid tests
        assert(board.validEntry("user") == false);
        assert(board.validEntry("computer") == false);
        assert(board.validEntry("comp") == false);
        assert(board.validEntry("a") == false);
        assert(board.validEntry("aeq1") == false);
        assert(board.validEntry("!Q£") == false);
    }

    // tests line() function
    private void lineTests() {

        // valid tests 
        assert(game.line('X', 'X', 'X') == true); 
        assert(game.line('O', 'O', 'O') == true);

        // invalid tests
        assert(game.line('X', 'O', 'O') == false);
        assert(game.line('O', 'X', 'O') == false);
        assert(game.line('O', 'O', 'X') == false);
        assert(game.line(' ', ' ', ' ') == false);
        assert(game.line('X', ' ', ' ') == false);
        assert(game.line('O', 'O', ' ') == false);
    }

    // tests someoneWon() function 
    private void someoneWonTests() {

        // winning line tests 
        char[][] testGrid = { {'X', 'X', 'X'}, {' ', 'O', ' '}, {' ', 'O', ' '} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', ' '}, {'X', 'X', 'X'}, {' ', 'O', ' '} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', ' '}, {' ', 'O', ' '}, {'X', 'X', 'X'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {'O', ' ', ' '}, {'O', 'X', ' '}, {'O', ' ', 'X'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', ' '}, {'X', 'O', ' '}, {' ', 'O', 'X'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {' ', 'O', ' '}, {'X', 'O', ' '}, {' ', 'O', 'X'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {' ', ' ', 'O'}, {'X', ' ', 'O'}, {' ', ' ', 'O'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', 'X', 'O'}, {' ', ' ', 'X'} };
        assert(game.someoneWon(testGrid) == true);
        testGrid = new char[][] { {'X', ' ', 'O'}, {' ', 'O', 'X'}, {'O', ' ', ' '} };
        assert(game.someoneWon(testGrid) == true);

        // no winning line
        testGrid = new char[][] { {' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '} };
        assert(game.someoneWon(testGrid) == false);
        testGrid = new char[][] { {'O', ' ', 'X'}, {'X', 'X', 'O'}, {'O', 'X', ' '} };
        assert(game.someoneWon(testGrid) == false);
        testGrid = new char[][] { {'X', 'O', 'X'}, {'X', 'O', 'O'}, {'O', 'X', 'O'} };
        assert(game.someoneWon(testGrid) == false);
        testGrid = new char[][] { {'O', 'O', 'X'}, {'X', 'X', 'O'}, {'O', 'X', 'X'} };
        assert(game.someoneWon(testGrid) == false);

    }

    // tests symbolTurn() function 
    private void symbolTurnTests() {

        symbol = Symbols.X; 
        char turn = Symbols.X.getValue();

        assert(game.symbolTurn(turn, symbol) == Symbols.X);
        turn = Symbols.O.getValue(); 
        assert(game.symbolTurn(turn, symbol) == Symbols.O);
        turn = Symbols.X.getValue(); 
        assert(game.symbolTurn(turn, symbol) == Symbols.X);
    }

    // tests switchTurn() function 
    private void switchTurnTests() {

        char turn = Symbols.X.getValue(); 
        assert(game.switchTurn(turn) == 'O');
        turn = Symbols.O.getValue(); 
        assert(game.switchTurn(turn) == 'X');
    }

    // tests validPosition() function 
    private void positionTests() {

        board.setGrid(symbol); 

        int[] testCoordinates = new int[2]; 

        // valid test cases 
        assert(pos.validPosition("a1", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 0);
        assert((testCoordinates[1]) == 0);
        assert(pos.validPosition("a2", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 0);
        assert((testCoordinates[1]) == 1);
        assert(pos.validPosition("a3", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 0);
        assert((testCoordinates[1]) == 2);
        assert(pos.validPosition("b1", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 1);
        assert((testCoordinates[1]) == 0);
        assert(pos.validPosition("b2", board) == true);
        testCoordinates = pos.getPosition();
        assert((testCoordinates[0]) == 1);
        assert((testCoordinates[1]) == 1);
        assert(pos.validPosition("b3", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 1);
        assert((testCoordinates[1]) == 2);
        assert(pos.validPosition("c1", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 2);
        assert((testCoordinates[1]) == 0);
        assert(pos.validPosition("c2", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 2);
        assert((testCoordinates[1]) == 1);
        assert(pos.validPosition("c3", board) == true);
        testCoordinates = pos.getPosition(); 
        assert((testCoordinates[0]) == 2);
        assert((testCoordinates[1]) == 2);

        // invalid test cases 
        assert(pos.validPosition("d2", board) == false);
        assert(pos.validPosition("b0", board) == false);
        assert(pos.validPosition("b4", board) == false);
        assert(pos.validPosition("2b", board) == false);
        assert(pos.validPosition("b2x", board) == false);
        assert(pos.validPosition("b", board) == false);
        assert(pos.validPosition("", board) == false);
    }

}