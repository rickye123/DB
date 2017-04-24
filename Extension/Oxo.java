/* This class conrols all the others in this package */

import java.util.*;
import java.util.concurrent.TimeUnit; 

class Oxo {

    private Symbols symbol; 

    public static void main(String[] args) {

        boolean testing = false; 
        assert(testing = true); 

        Oxo noughtsAndCrosses = new Oxo();
        Board game = new Board();
        Testing tests = new Testing();
        Symbols symbol = Symbols.X; // start player is X
        
        if(testing) {
            tests.executeTests();
        }
        else if (args.length == 0) {
            noughtsAndCrosses.playGame(game, symbol);
        }
        else {
            System.err.println("Use:");
            System.err.println("    java -ea Oxo for testing or");
            System.err.println("    java Oxo to play");
            System.exit(1);
        }

    }

    // the main function which plays the game Noughts and Crosses
    private void playGame(Board game, Symbols symbol) {

        Display board = new Display();

    	String gameType = board.askForGameType(); 

        try {
            if(gameType.equals("1")) {
        	    playAgainstComputer(game, board, symbol); 
            }
            else if(gameType.equals("2")) {
        	    playTwoPlayer(game, board, symbol);
            }
        } catch (Exception e) {
        	System.out.println("Exception caught" + e);
        }

    }

    // play against the computer and call functions to see who goes first 
    private void playAgainstComputer(Board game, Display board, Symbols symbol) {

        Position pos = new Position();
        AI cpu = new AI();

        board.setGrid(symbol); 
        board.displayGrid();

        char turn = board.promptForFirstTurn();

        try {
            if(turn == Symbols.X.getValue()) {
                playUserFirst(board, game, turn, symbol, pos, cpu);
            }
            else if (turn == Symbols.O.getValue()) {
                playComputerFirst(board, game, turn, symbol, pos, cpu);
            }
        } catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

    }

    // if player enters "User" then user starts and game is played 
    private void playUserFirst(Display board, Board game, char turn, Symbols symbol, Position pos, AI cpu) {

        boolean gameOver = false; 

        while (gameOver != true) {

            game.playerTurn(turn);
            symbol = game.symbolTurn(turn, symbol);
            System.out.println(" " + "(" + symbol + ")");

            if(playerGameOver(board, game, turn, symbol, pos, cpu) == true) {
                gameOver = true; 
            }

        }

    }

    // process of execution when user goes first 
    private boolean playerGameOver(Display board, Board game, char t, Symbols s, Position p, AI cpu) {

        if(userTurn(board, game, t, s, p) == true) {
            return true; 
        }

        t = game.switchTurn(t);

        delayFunction(1000); // delay by 1 second

        if(cpuTurn(board, game, t, s, p, cpu) == true) {
            return true; 
        }

        t = game.switchTurn(t);
        return false; 
    }

    // delay computer's move by specified time 
    private boolean delayFunction(int milliseconds) {

        System.out.println("Computer is thinking...");

        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } 
        catch (Exception e) { 
            return false; 
        }
        return true; 

    }

    // prompt user to input their move, e.g. a1, display and then check if game 
    // is finished 
    private boolean userTurn(Display board, Board game, char t, Symbols s, Position p) {

        board.promptInput(game, board, s, p);
        board.playerMove(p, s); 
        board.displayGrid(); 

        if(checkGameState(board, game, t) == true) {
            return true; 
        }
        return false; 

    }

    // work out the moves the cpu can take, display grid and check if game is finished
    private boolean cpuTurn(Display board, Board game, char t, Symbols s, Position p, AI cpu) {

        cpu.commenceMove(board, p);
        cpu.cpuMove(p, s, board);
        board.displayGrid(); 

        if(checkGameState(board, game, t) == true) {
            return true; 
        }
        return false; 

    }

    // check to see if game is finished 
    private boolean checkGameState(Display board, Board game, char turn) {
        
        if(game.gameFinished(board, game, turn) == true) {
            return true; 
        }
        return false; 
    }

    // if user enters "Computer" then the computer starts and the game is played 
    private void playComputerFirst(Display board, Board game, char turn, Symbols symbol, Position pos, AI cpu) {

        boolean gameOver = false; 

        while (gameOver != true) {

            game.playerTurn(turn);
            symbol = game.symbolTurn(turn, symbol);
            System.out.println(" " + "(" + symbol + ")");

            if(cpuGameOver(board, game, turn, symbol, pos, cpu) == true) {
                gameOver = true; 
            }

        }
    }

    // process of execution on when computer goes first
    private boolean cpuGameOver(Display board, Board game, char t, Symbols s, Position p, AI cpu) {
        
        delayFunction(1000); // delay by 1 second

        if(cpuTurn(board, game, t, s, p, cpu) == true) {
            return true; 
        }

        t = game.switchTurn(t);
        s = Symbols.X;

        if(userTurn(board, game, t, s, p) == true) {
            return true; 
        }

        t = game.switchTurn(t);
        return false; 

    }


    // play as two players and end when game finished 
    private void playTwoPlayer(Board game, Display board, Symbols symbol) {

        Position pos = new Position();

        board.setGrid(symbol); 
        board.displayGrid();

        char turn = Symbols.X.getValue();
        boolean gameOver = false; 

        while (gameOver != true) {
            game.playerTurn(turn);
            symbol = game.symbolTurn(turn, symbol);
            System.out.println(" " + "(" + symbol + ")");
            board.promptInput(game, board, symbol, pos);
            board.playerMove(pos, symbol); 
            board.displayGrid(); 
            if(game.gameFinished(board, game, turn) == true) {
                gameOver = true; 
            }

            turn = game.switchTurn(turn); 
        }

    }

}
