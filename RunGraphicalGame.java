import processing.core.*;

public class RunGraphicalGame extends PApplet {
	GameBoard game;
	Display display;
	Location startLocation, targetLocation;
	int clickCount = 1;

	public void settings() {
		size(640, 550);
	}

	public void setup() {
		// Create a game object
		game = new GameBoard(7,7);

		// Create the display
		// parameters: (10,10) is upper left of display
		// (400, 400) is the width and height
		display = new Display(this, 55, 10, 530, 530);

		display.setColor(0, color(200, 200, 200));  // empty (grey)
		display.setColor(1, color(255, 0, 0));      // player 1 (red)
		display.setColor(2, color(0, 255, 0));      // player 2 (green)

		// You can use images instead if you'd like.
		// display.setImage(1, "assets/on.png");
		// display.setImage(2, "assets/off.png");

		display.initializeWithGame(game);
	}

	@Override
	public void draw() {
		background(200);
		display.drawGrid(game.getGrid()); // display the game

		int winner = game.isGameOver();
		if ( winner != -1 ) {
			if (winner == 0) {
				System.out.println("GAME OVER. It's a tie!");
			} else {
				System.out.println("Player " + winner + " wins!");
			}
			super.stop();
		}
	}

	public void mouseReleased() {
		if (clickCount == 1) {
			startLocation = display.gridLocationAt(mouseX, mouseY);
			System.out.println("[DEBUGGING INFO] You clicked at START " + startLocation.getRow() + ", " + startLocation.getCol());

			// check location
			if ( !game.isPlayerAt(startLocation.getRow(), startLocation.getCol() )) {
				System.out.println("[WARNING] Invalid START cell, try again");
			} else {
				clickCount++;
			}

		} else if (clickCount == 2) {
			targetLocation = display.gridLocationAt(mouseX, mouseY);
			System.out.println("[DEBUGGING INFO] You clicked at TARGET " + targetLocation.getRow() + ", " + targetLocation.getCol());

			// check location
			if (targetLocation.equals( startLocation )) { // pass my turn
				System.out.println("Passing your turn. Other's player turn.");
				game.switchPlayerTurn();
				clickCount = 1;
			}
			else if ( !game.isCellEmpty(targetLocation.getRow(), targetLocation.getCol() )) {
				System.out.println("[WARNING] Invalid TARGET cell, try again");
			} else if ( !game.move( startLocation.getRow(), startLocation.getCol(), targetLocation.getRow(), targetLocation.getCol() )) {
				System.out.println("[WARNING] Invalid TARGET move, try again");
			} else {
				game.infectOpponent( targetLocation.getRow(), targetLocation.getCol() );
				clickCount = 1;
			}
		}
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}