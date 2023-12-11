/**
 * This class represents the configurations of the game board
 * Its methods include creating a hash dictionary, checking for wins, and implementing all methods needed by computerPlay
 * @author Maya Lekhi
 */
public class Configurations {
	/**
	 * Game board tile data
	 */
    private char[][] board;
	/**
	 * Game board dimensions
	 */
    private int boardSize;
	/**
	 * Length of tokens in appropriate shape needed to win
	 */
    private int lengthToWin;
	/**
	 * Maximum level of game tree that will be explored by program
	 */
    private int maxLevels;
	/**
	 * Hash dictionary to store configurations
	 */
    private HashDictionary configs;

	/**
	 * Creates a configuration object with initialized board, size, levels, and length to win
	 * @param boardSize		game board that move will be displayed on
	 * @param lengthToWin	row where token will be placed
	 * @param maxLevels		column where token will be placed
	 */
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
    	// setting instance variables based on input
        this.boardSize = boardSize;
        this.maxLevels = maxLevels;
        this.lengthToWin = lengthToWin;
        // creating character board based on inputted board size
        board = new char[boardSize][boardSize];
        
        // iterating through the 3d array and setting each board space to empty
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
        // creating a dictionary for the configuration data
        this.configs = createDictionary();
    }

	/**
	 * Creates empty hash dictionary
	 * @return empty hash dictionary with table size of 9973
	 */
    public HashDictionary createDictionary() {
    	// using a hash dictionary with the largest prime number under 10000 to avoid collisions
        return new HashDictionary(9973);
    }

	/**
	 * Stores the characters of board in a String and checks whether if String is in the hashTable
	 * @param hashTable	hashTable where the string may be stored	
	 * @return empty hash dictionary with table size of 9973
	 */
    public int repeatedConfiguration(HashDictionary hashTable) {
    	// converting the board characters to a string
        String config = boardToString();
        // checking whether the string representing the board is is the hashTable and it's associated score if applicable
        return hashTable.get(config);
    }

	/**
	 * Represents characters of board in a string and inserts string in hashDictionary
	 * @param hashDictionary	hashDictionary where the string config will be added
	 * @param score				score of the configuration
	 */    
    public void addConfiguration(HashDictionary hashDictionary, int score) {
    	// converting the board characters to a string
        String config = boardToString();
        // creating new data object for string board and score
        Data data = new Data(config, score);
        // trying to put it in the hashdictionary
        try {
            hashDictionary.put(data);
        // handling any dictionary exception errors
        } catch (DictionaryException e) {
        }
    }

	/**
	 * Method to save the specified row and column as the specified symbol on the board
	 * @param row		integer specifying row number
	 * @param col		integer specifying column number
	 * @param symbol	symbol representing the character playing
	 */    
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

	/**
	 * Method to verify if a given space is empty
	 * @param row		integer specifying row number
	 * @param col		integer specifying column number
	 * @return boolean indicating whether or not the square is available
	 */    
    public boolean squareIsEmpty(int row, int col) {
    	// checking if board location contains an empty space
        if (board[row][col] == ' ') {
        	return true;
        } else {
        	return false;
        }
    }

	/**
	 * Method to check for the player's X or Plus shaped wins that fulfill the length criteria
	 * @param symbol		symbol representing the character playing
	 * @return boolean representing if the given symbol/player has won
	 */    
    public boolean wins(char symbol) {
    	// iterating through the board, excluding the sidemost spaces as the Plus center cannot be found there
        for (int x = 1; x < this.boardSize - 1; ++x) {
            for (int y = 1; y < this.boardSize - 1; ++y) {
            	// running the check for plus helper function and seeing if it's output is long enough to win
                if (this.checkForPlus(x, y, symbol) >= this.lengthToWin) {
                    return true;
                }
            }
        }
    	// iterating through the board, excluding the sidemost spaces as the X center cannot be found there
        for (int x = 1; x < this.boardSize - 1; ++x) {
            for (int y = 1; y < this.boardSize - 1; ++y) {
            	// running the check for X helper function and seeing if it's output is long enough to win
                if (this.checkForX(x, y, symbol) >= this.lengthToWin) {
                    return true;
                }
            }
        }
        // returning false if the length to win is not met
        return false;
    }
    
	/**
	 * Method to check if the board spaces are full, indicating a draw
	 * @return boolean representing if there is a draw or not
	 */
    public boolean isDraw() {
    	// iterating through the board spaces
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
            	// returning false if any board spaces are empty
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        // returning true if no board spaces are empty
        return true; 
    }

	/**
	 * Method to indicate if the game should continue or not
	 * @return integer indicating if someone has won, if there is a draw, or if undecided
	 */
    public int evalBoard() {
    	// returning 0 if the human won
        if (wins('X')) {
            return 0; 
        // returning 3 if the computer won
        } else if (wins('O')) {
            return 3;
        // returning 2 if there is a draw
        } else if (isDraw()) {
            return 2;
        // returning 1 if the game is undecided
        } else {
            return 1;
        }
     }
	
	/**
	 * Helper function to convert the game board into a string representation
	 * @return game board represented as a string
	 */
    private String boardToString() {
    	// initializing a string builder
        StringBuilder sb = new StringBuilder();
        // iterating through the board tiles
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
            	// appending each character on the board to the string builder
                sb.append(board[i][j]);
            }
        }
        // returning a string representation of the completed string builder
        return sb.toString();
    }
	    
	/**
	 * Helper function to check the given space for an X-win and determine the amount of points
	 * @param row		integer specifying row number of the X-center
	 * @param col		integer specifying column number of the X-center
	 * @param symbol	symbol representing the character playing
	 * @return points made by the X
	 */
    private int checkForX(int row, int col, char symbol) {
    	// initializing the points number as one to account for the center piece
        int pointsCount = 1;
        
        // checking if the surrounding spaces (top left, top right, bottom left, bottom right) form an X with the given symbol
        if (this.board[row][col] == symbol && this.board[row + 1][col + 1] == symbol && this.board[row + 1][col - 1] == symbol && this.board[row - 1][col - 1] == symbol && this.board[row - 1][col + 1] == symbol) {
        	// setting the points to 5 for the new spaces
        	pointsCount = 5;
        	
        	// checking if the spaces bottom right extend to create a larger X and incrementing accordingly
            for (int i = 2; row + i < this.boardSize && col + i < this.boardSize && this.board[row + i][col + i] == symbol; i++) {
            		pointsCount += 1; 
            }
        	// checking if the spaces top left extend to create a larger X and incrementing accordingly
            for (int i = 2; row - i >= this.boardSize && col - i >= this.boardSize && this.board[row - i][col - i] == symbol; i++) {
            		pointsCount += 1; 
            }
        	// checking if the spaces bottom left extend to create a larger X and incrementing accordingly
            for (int i = 2; row + i < this.boardSize && col - i >= this.boardSize && this.board[row + i][col - i] == symbol; i++) {
            		pointsCount += 1; 
            }
        	// checking if the spaces top right extend to create a larger X and incrementing accordingly
            for (int i = 2; row - i >= this.boardSize && col + i < this.boardSize && this.board[row - i][col + i] == symbol; i++) {
            		pointsCount += 1; 
            }
        }
        return pointsCount;
    }

	/**
	 * Helper function to check the given space for an Plus-win and determine the amount of points
	 * @param row		integer specifying row number of the Plus-center
	 * @param col		integer specifying column number of the Plus-center
	 * @param symbol	symbol representing the character playing
	 * @return points made by the plus
	 */
    private int checkForPlus(int row, int col, char symbol) {
    	// initializing the points number as one to account for the center piece
        int pointsCount = 1;
        
        // checking if the surrounding spaces (up, down, left, right) form a plus with the given symbol
        if (this.board[row][col] == symbol && this.board[row + 1][col] == symbol && this.board[row - 1][col] == symbol && this.board[row][col + 1] == symbol && this.board[row][col - 1] == symbol) {
        	// setting the points to 5 for the new spaces
        	pointsCount = 5;
        	
        	// checking if the spaces below extend to create a larger plus and incrementing accordingly
            for (int i = 2; row + i < this.boardSize && this.board[row + i][col] == symbol; i++) {
            	pointsCount += 1; 
            }
        	// checking if the spaces above extend to create a larger plus and incrementing accordingly
            for (int i = 2; row - i >= this.boardSize && this.board[row - i][col] == symbol; i++) {
            		pointsCount += 1; 
            }
        	// checking if the spaces right extend to create a larger plus and incrementing accordingly
            for (int i = 2; col + i < this.boardSize && this.board[row][col + i] == symbol; i++) {
            		pointsCount += 1; 
            }
        	// checking if the spaces left extend to create a larger plus and incrementing accordingly
            for (int i = 2; col - i >= this.boardSize && this.board[row][col - i] == symbol; i++) {
            		pointsCount += 1; 
            }
        }
        return pointsCount;
    }
}