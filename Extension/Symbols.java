/* This is an enumerated class to symbolise the characters 
'X', 'O' and ' ' */

public enum Symbols {

    X('X'), O('O'), B(' ');

    private final char value; 

    private Symbols(char value) {
        this.value = value; 
    }

    public char getValue() {
        return value; 
    }

}