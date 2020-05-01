public class GameBoard {
	private int[][] grid; 						// the grid that stores the pieces
	private int currentPlayer;

	public GameBoard(int width, int height) {
		grid = new int[height][width];
		currentPlayer = 1;

		// Initialize starting positions
		grid[0][0] = 1;
		grid[grid.length - 1][grid[0].length - 1] = 1;
		grid[grid.length - 1][0] = 2;
		grid[0][grid[0].length - 1] = 2;

	}

	// Make the requested move at (row, col) by changing the grid.
	// returns false if no move was made, true if the move was successful.
	public boolean move(int startRow, int startCol, int targetRow, int targetCol) {
		System.out.print("[DEBUGGING INFO] You tried to move FROM " + startRow + ", " + startCol);
		System.out.println(" TO " + targetRow + ", " + targetCol);

		// check if move is not valid.  If so, return false.
		if ( distance(startRow, startCol, targetRow, targetCol) < 1) {
			return false;
		}

		grid[targetRow][targetCol] = currentPlayer;
		if ( distance(startRow, startCol, targetRow, targetCol) == 2) {
			grid[startRow][startCol] = 0;
		}

		switchPlayerTurn();

		return true; // if move was valid, return true
	}


	public static int distance(int startRow, int startCol, int targetRow, int targetCol) {
		int distance = 0;
		int delta_row = targetRow - startRow;
		int delta_col = targetCol - startCol;

		if ( Math.abs(delta_row) > 2 || Math.abs(delta_col) > 2 ) {
			return -1; // invalid move
		} else if (delta_row == 0 && delta_col == 0) {
			return 0;
		} else if (Math.abs(delta_row) < 2 && Math.abs(delta_col) < 2) {
			return 1;
		} else {
			return 2;
		}
	}

	private void switchPlayerTurn() {
		currentPlayer = currentPlayer == 1 ? 2 : 1;
	}

	/*
	 * Return true if the game is over. False otherwise.
	 */
	public boolean isGameOver() {

		/*** YOU COMPLETE THIS METHOD ***/

		return false;
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public boolean isInGrid(int row, int col) {
		return row >=0 && row < grid.length && col >= 0 && col < grid[0].length;
	}

	public boolean isPlayerAt(int row, int col) {
		if (!isInGrid(row, col)) {
			return false;
		}
		return grid[row][col] == currentPlayer;
	}

	public boolean isCellEmpty(int row, int col) {
		if (!isInGrid(row, col)) {
			return false;
		}
		return grid[row][col] == 0;
	}
}