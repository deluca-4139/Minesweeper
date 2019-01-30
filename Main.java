import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	static int type = 0;
	static Square[][] board;
	static Random rand = new Random();
	static boolean gameFinished = false;
	static int numFlagged = 0;

	/*
	 * Main method.
	 * Gets the type of game the user wants, generates a board, and then runs the looping play method.
	 * See individual method comments for details.
	 */
	public static void main(String[] args) {
		getType();
		board = genBoard();
		try {
			play();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			play();
		}
	}
	
	/*
	 * The initial method that gets ran at the start of the game.
	 * Prints a greeting message and acquires the type of game via user input.
	 * Will print an error message and exit if the required info is not given. 
	 */
	public static void getType() {
		System.out.println("Welcome to Minesweeper!");
		System.out.println("-----------------------");
		System.out.println("Please select a board type.");
		System.out.println("");
		System.out.println("1 - Standard 9x9 Board with 10 mines.");
		System.out.println("2 - Intermediate 16x16 Board with 40 mines.");
		System.out.println("3 - Create your own board!");
		Scanner scan = new Scanner(System.in);
		try {
			type = scan.nextInt();
		}
		catch(InputMismatchException e) {
			System.out.println("You must input a valid choice!");
			scan.close();
			System.exit(1);
		}
	}
	
	/* 
	 * Generates the play board based on the type of board the user selects at the start.
	 * Creates a blank 2D array of Squares, then selects random coordinates for the mines.
	 * Calls getNeighbors() at the end to update the data fields for the squares. 
	 */
	public static Square[][] genBoard() {
		switch(type) {
			case 1:
				Square[][] intermediateBoard = new Square[8][8];
				for(int i = 0;i < intermediateBoard.length;i++) {
					for(int n = 0;n < intermediateBoard[0].length;n++) {
						intermediateBoard[i][n] = new Square();
					}
				}
				boolean done = false;
				int u = 0;
				while(!done) {
					int i = rand.nextInt(7);
					int n = rand.nextInt(7);
					if(!intermediateBoard[i][n].isMine()) {
						intermediateBoard[i][n].setMine(true);
						u++;
						if(u==10) {
							done = true;
						}
					}
				}
				intermediateBoard = getNeighbors(intermediateBoard);
				return intermediateBoard;
			case 2:
				intermediateBoard = new Square[16][16];
				for(int i = 0;i < intermediateBoard.length;i++) {
					for(int n = 0;n < intermediateBoard[0].length;n++) {
						intermediateBoard[i][n] = new Square();
					}
				}
				done = false;
				u = 0;
				while(!done) {
					int i = rand.nextInt(15);
					int n = rand.nextInt(15);
					if(!intermediateBoard[i][n].isMine()) {
						intermediateBoard[i][n].setMine(true);
						u++;
						if(u==40) {
							done = true;
						}
					}
				}
				intermediateBoard = getNeighbors(intermediateBoard);
				return intermediateBoard;
		}
		return null;
	}
	
	/* 
	 * Method for finding how many mines surround a square. 
	 * Called when generating board to fill the data field for a square. 
	 * Goes around the square and tests each of the surrounding 8 squares for mines, then increments accordingly. 
	 * Returns the fixed board; meaning all of the data fields in each square have been updated based on the 
	 * number of mines that surround it. 
	 */
	public static Square[][] getNeighbors(Square[][] fixBoard) {
		for(int i = 0;i < fixBoard.length;i++) {
			int numOfMines = 0;
			for(int n = 0;n < fixBoard[0].length;n++) {
				//if(fixBoard[i][n].isMine()) { /*This may break the method. Shouldn't be necessary if the isMine method is used, which makes the data unnecessary if the square is a mine. Comment out if method breaks!*/
				//	break;
				//}
				try {
					if(fixBoard[i-1][n].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i-1][n+1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i][n+1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i+1][n+1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i+1][n].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i+1][n-1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i][n-1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				try {
					if(fixBoard[i-1][n-1].isMine()) {
						numOfMines++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				fixBoard[i][n].setData(numOfMines);
				numOfMines = 0;
			}
		}
		return fixBoard;
	}
	
	/* 
	 * Method for updating the "GUI" of the board.
	 * Prints an outline of integers showing the grid, and then loops through all the squares and uses the square's toString()
	 * method to print the correct character based on the state of the square. 
	 */
	public static void printBoard() {
		for(int i = 0;i < 100 + 10;i++) { //Effectively clears the screen. Probably not the best option but it works for now
			System.out.println("");
		}
		if(type==1 ) {
			System.out.println("  1  2  3  4  5  6  7  8");
			for(int n = 0;n < board.length;n++) {
				System.out.print((n + 1) + " ");
				for(int i = 0;i < board[n].length;i++) {
					System.out.print(board[n][i]);
					System.out.print("  ");
				}
				System.out.println("");
			}
		}
		if(type==2) {
			System.out.println("  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16");
			for(int n = 0;n < board.length;n++) {
				System.out.print((n + 1) + " ");
				for(int i = 0;i < board[n].length;i++) {
					System.out.print(board[n][i]);
					System.out.print("  ");
				}
				System.out.println("");
			}
		}
	}
	
	/*
	 * Main play method for the game. Will loop until the game is finished, either by winning or losing.
	 * Updates the display by printing the board and then printing choices for the user. Then checks for game completion;
	 * if not complete, loops again. 
	 */
	public static void play() {
		printBoard();
		while(!gameFinished) {
			System.out.println("");
			System.out.println("Please select a choice.");
			System.out.println("1: Strike a square");
			System.out.println("2: Flag a square");
			updateSquare();
			checkIfDone();
			printBoard();
		}
	}
	
	/*
	 * Takes an input and then updates the square (clears or flags it) and game status.
	 */
	public static void updateSquare() {
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		switch(input) {
		case 1:
			int row = 0;
			int col = 0;
			System.out.print("Please specify a row.");
			row = scan.nextInt() - 1;
			System.out.print("Please specify a column.");
			col = scan.nextInt() - 1;
			if(board[row][col].isMine()) {
				lose();
			}
			else {
				board[row][col].setHasBeenChosen(true);
				if(board[row][col].getData()==0) {
					updateNeighbors(row, col);
				}
			}
			break;
		case 2:
			if ((type==1 && numFlagged<=10) || (type==2 && numFlagged<=40)) {
				row = 0;
				col = 0;
				System.out.print("Please specify a row.");
				row = scan.nextInt() - 1;
				System.out.print("Please specify a column.");
				col = scan.nextInt() - 1;
				board[row][col].setFlagged(true);
				numFlagged++;
				break;
			}
		}
	}
	
	/*
	 * Function used to update all surrounding empty squares if an empty square is chosen in the updateSquare() method.
	 */
	public static void updateNeighbors(int i, int n) {
		try {
			if(board[i-1][n].getData()==0 && !board[i-1][n].isMine() && !board[i-1][n].hasBeenChosen()) {
				board[i-1][n].setHasBeenChosen(true);
				updateNeighbors(i-1,n);
			}
			else if(!board[i-1][n].isMine()) {
				board[i-1][n].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i-1][n+1].getData()==0 && !board[i-1][n+1].isMine() && !board[i-1][n+1].hasBeenChosen()) {
				board[i-1][n+1].setHasBeenChosen(true);
				updateNeighbors(i-1,n+1);
			}
			else if(!board[i-1][n+1].isMine()) {
				board[i-1][n+1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i][n+1].getData()==0 && !board[i][n+1].isMine() && !board[i][n+1].hasBeenChosen()) {
				board[i][n+1].setHasBeenChosen(true);
				updateNeighbors(i,n+1);
			}
			else if(!board[i][n+1].isMine()) {
				board[i][n+1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i+1][n+1].getData()==0 && !board[i+1][n+1].isMine() && !board[i+1][n+1].hasBeenChosen()) {
				board[i+1][n+1].setHasBeenChosen(true);
				updateNeighbors(i+1,n+1);
			}
			else if(!board[i+1][n+1].isMine()) {
				board[i+1][n+1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i+1][n].getData()==0 && !board[i+1][n].isMine() && !board[i+1][n].hasBeenChosen()) {
				board[i+1][n].setHasBeenChosen(true);
				updateNeighbors(i+1,n);
			}
			else if(!board[i+1][n].isMine()) {
				board[i+1][n].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i+1][n-1].getData()==0 && !board[i+1][n-1].isMine() && !board[i+1][n-1].hasBeenChosen()) {
				board[i+1][n-1].setHasBeenChosen(true);
				updateNeighbors(i+1,n-1);
			}
			else if(!board[i+1][n-1].isMine()) {
				board[i+1][n-1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i][n-1].getData()==0 && !board[i][n-1].isMine() && !board[i][n-1].hasBeenChosen()) {
				board[i][n-1].setHasBeenChosen(true);
				updateNeighbors(i,n-1);
			}
			else if(!board[i][n-1].isMine()) {
				board[i][n-1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if(board[i-1][n-1].getData()==0 && !board[i-1][n-1].isMine() && !board[i-1][n-1].hasBeenChosen()) {
				board[i-1][n-1].setHasBeenChosen(true);
				updateNeighbors(i-1,n-1);
			}
			else if(!board[i-1][n-1].isMine()) {
				board[i-1][n-1].setHasBeenChosen(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	/*
	 * Called after every loop of the play() method. Will check to see if the correct number of mines have been flagged,
	 * and if so, will check to see if all of the mines have been flagged. If this is met, the user wins. 
	 */
	public static void checkIfDone() {
		if((type==1 && numFlagged==10) || (type==2 && numFlagged==40)) { //May break, not sure if the if statement works correctly
			int intRequiredFlagged = 0;
			for(int i = 0;i < board.length;i++) {
				for(int n = 0;n < board[0].length;n++) {
					if(board[i][n].isFlagged() && board[i][n].isMine()) {
						intRequiredFlagged++;
					}
				}
			}
			if(intRequiredFlagged == numFlagged) {
				win();
			}
		}
	}
	
	/*
	 * Sets all squares to be chosen (show the entire board) and update the board, then print a winning message and exit.
	 * Called from checkIfDone() if all mines have been flagged.
	 */
	public static void win() {
		for(int i = 0;i < board.length;i++) {
			for(int n = 0;n < board[0].length;n++) {
				board[i][n].setHasBeenChosen(true);
			}
		}
		printBoard();
		System.out.println("Woohoo! You won!");
		System.exit(1);
	}
	
	/*
	 * Sets all squares to be chosen (show the entire board) and update the board, then print a losing message and exit.
	 * Called when a user chooses a square containing a mine. 
	 */
	public static void lose() {
		for(int i = 0;i < board.length;i++) {
			for(int n = 0;n < board[0].length;n++) {
				board[i][n].setHasBeenChosen(true);
			}
		}
		printBoard();
		System.out.println("Sorry! You lost!");
		System.exit(1);
	}

}
