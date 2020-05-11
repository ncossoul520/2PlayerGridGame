public class GameBoard {
	private int[][] grid; 						// the grid that stores the pieces
	private int currentPlayer, otherPlayer;
	private int[] color_count;

	public GameBoard(int width, int height) {
		grid = new int[height][width];
		currentPlayer = 1;
		otherPlayer = 2;
		color_count = new int[]{width * height, 0, 0};

		// Initialize starting positions
		setCell(0, 0, 1);
		setCell(grid.length - 1, grid[0].length - 1, 1);
		setCell(grid.length - 1, 0, 2);
		setCell(0, grid[0].length - 1, 2);
	}

	// Make the requested move at (row, col) by changing the grid.
	public boolean move(int startRow, int startCol, int targetRow, int targetCol) {
		System.out.print("[DEBUGGING INFO] You tried to move FROM " + startRow + ", " + startCol);
		System.out.println(" TO " + targetRow + ", " + targetCol);

		// return false if move is not valid
		if ( distance(startRow, startCol, targetRow, targetCol) < 1) {
			return false;
		}

		setCell(targetRow, targetCol, currentPlayer);
		if ( distance(startRow, startCol, targetRow, targetCol) == 2) {
			setCell(startRow, startCol, 0);
		}

		return true; // move was valid
	}

	public void infectOpponent(int row, int col) {
		for (int r = row-1; r < row+2; r++) {
			for (int c = col-1; c < col+2; c++) {
				if ( isInGrid(r, c) && grid[r][c] == otherPlayer ) {
					setCell(r, c, currentPlayer);
				}
			}
		}

		System.out.println("Score: Player1=" + color_count[1] + " Player2=" + color_count[2]);
	}

	public static int distance(int startRow, int startCol, int targetRow, int targetCol) {
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

	public void switchPlayerTurn() {
		int other = otherPlayer;
		otherPlayer = currentPlayer;
		currentPlayer = other;
	}

	/*
	 * Game is over if either:
	 * - a player has 0 tiles: the other player wins
	 * - there is more empty cell: the player w/ the most tiles wins
	 */
	public int isGameOver() {
		if (color_count[2] == 0) { return 1; }
		if (color_count[1] == 0) { return 2; }

		if ( blockedOtherPlayer() ) {
			return currentPlayer;
		}

		if ( color_count[0] == 0) { // no empty cells left
			if (color_count[1] == color_count[2])     { return 0; } // tie
			else if (color_count[1] > color_count[2]) { return 1; }
			else                                      { return 2; }
		}
		else { return -1; }  // game is not over
	}

	/*
	for every tile of the grid:
		if tile belongs to the otherPlayer:
			for every tile around it w/i a distance of 2:
				if able to play (i.e. grid[r][c] == 0): return false
	return true
	 */
	private boolean blockedOtherPlayer() {
		for (int grid_r = 0; grid_r < grid.length; grid_r++) {
			for (int grid_c = 0; grid_c < grid[0].length; grid_c++) {

				if ( grid[grid_r][grid_c] == otherPlayer ) {

					for (int r = grid_r -2; r <= grid_r +2; r++) {
						for (int c = grid_c - 2; c <= grid_c + 2; c++) {

							if (isInGrid(r, c) && grid[r][c] == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public boolean isInGrid(int row, int col) {
		return row >=0 && row < grid.length && col >= 0 && col < grid[0].length;
	}

	public boolean isPlayerAt(int row, int col) {
		return isInGrid(row, col) && grid[row][col] == currentPlayer;
	}

	public boolean isCellEmpty(int row, int col) {
		return !isInGrid(row, col) || grid[row][col] == 0;
	}

	public void setCell(int row, int col, int new_color) {
		if ( isInGrid(row, col) ) {
			int cur_color = grid[row][col];
			if (new_color != cur_color) {
				grid[row][col] = new_color;
				color_count[new_color]++;
				color_count[cur_color]--;
			}
		}
	}
}