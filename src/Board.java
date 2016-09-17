
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board implements KeyListener {

	//Variables declared 
	private static Board instance;
	Random rand = new Random();
	public int score = 0;
	public int winCount = 0;
	private Frame frahm;
	public String gameMode = "2048";
	static int loadScore;

	boolean randomMode = false;

	//The dimensions of the 2048 board can be modified here for varied gameplay
	public final int rows = 4;
	public final int cols = 4;

	public int board[][] = new int[rows][cols];

	boolean winTest = false;

	boolean computerMode = false;

	public boolean hasMoved = false;

	//instantiates Board and only allows one Board to be created
	public static Board getInstance()
	{
		if(instance == null)
			instance = new Board();
		return instance;
	}

	//Constructor sets values of 2d int array of board to all 0's
	private Board() {

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = 0;
			}
		}
		//	ultimateCheckers();


	}

	//prints the board in the console for debugging purposes
	public void printBoard() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	//converts from the integer value to a String value that is displayed on the buttons of the board.  
	//Enables different game modes including 11 which counts up from 1.
	public String intToString(int x) {
		if (gameMode.equals("2048") || gameMode.equals("2048 - Easy")) {
			if (x == 0)
				return "";
			else
				return String.valueOf(x);
		}

		if (gameMode.equals("11")) {
			if (x == 0)
				return "";
			
			else
				return String.valueOf((int)(Math.log(x)/Math.log(2)));
		}
		else {
			return "";
		}
	}

	//spawns a random 2 or 4 on a random open space on the board
	//10% chance of spawning a 4
	public void spawn() {

		int decide2or4 = rand.nextInt(10);
		if (decide2or4 == 0) {
			//randomizes the row and column coordinate separately
			int random4r = rand.nextInt(rows);
			int random4c = rand.nextInt(cols);
			//if space is open set it equal to 4
			if (board[random4r][random4c] == 0) {
				board[random4r][random4c] = 4;
			} else
				//recurse if it selected a space that is already taken
				this.spawn();
		} else {
			int random2r = rand.nextInt(rows);
			int random2c = rand.nextInt(cols);
			//if space is open set it equal to 2
			if (board[random2r][random2c] == 0) {
				board[random2r][random2c] = 2;
			} else
				//recurse if it selected a space that is already taken
				this.spawn();
		}
	}

	//Slides all the tiles on the board to the right and combines them accordingly with matching tiles
	//Starts from the right side of the board and works its way to the left sliding the tiles over
	public void moveRight() {

		//used to determine if the board has changed after a move has been performed.
		boolean somethingHappened = false;

		//used to determine if the tile at the coordinate in question has already been combined with another tile or not
		boolean[][] beenCombined = new boolean[rows][cols];

		//sets all values of the beenCombined to false
		for (int r = 0; r < rows; r++) {
			for (int c = 1; c <= cols - 1; c++) {
				beenCombined[r][c] = false;
			}
		}
		//2 for loops to cycle through all spaces that can be moved to the right in the array (the left three columns)
		for (int r = 0; r < rows; r++) {
			for (int c = cols - 2; c >= 0; c--) {

				//creates a temporary variable that allows for incrementing the tile at r,c as far as it can go to the right
				int d = c;

				//if space to the right is open,  
				if (board[r][d + 1] == 0 && board[r][d] != 0) {

					//while it is within the bounds of the array
					while (d < cols - 1) {

						if (board[r][d + 1] == 0) {

							//move it to the right
							board[r][d + 1] = board[r][d];

							//set old space to 0
							board[r][d] = 0;

							//somethingHappened is now equal to true since the board has changed
							somethingHappened = true;

						} 
						//if the space isn't open, break out of the loop
						else {
							break;
						}

						//increments d so that it can move it another space to the right if possible
						d++;
					}

				}

				//if the tile is equal to the tile to the right of it, and it has not already been combined
				//it combines them into one tile of double the value
				if (d < cols - 1) {
					if (board[r][d + 1] == board[r][d] && board[r][d] != 0 && beenCombined[r][d+1] == false) {
						board[r][d + 1] *= 2;

						board[r][d] = 0;

						//it has been combined
						beenCombined[r][d+1] = true;

						//something has happened
						somethingHappened = true;

						//adds the combined number to the player's score
						score += board[r][d + 1];
					}
				}
			}
		}

		//only spawns another number if the board has changed
		if (somethingHappened) {
			spawn();
		}
		if (!somethingHappened) {
			System.out.println("Move Results in No Change to the Board.");
		}
	}

	//same code as the moveRight() method except that it cycles through the array in a different order 
	//since it is moving the tiles to the left and not the right
	public void moveLeft() {
		boolean somethingHappened = false;
		boolean[][] beenCombined = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 1; c <= cols - 1; c++) {
				beenCombined[r][c] = false;
			}
		}
		for (int r = 0; r < rows; r++) {
			for (int c = 1; c <= cols - 1; c++) {

				int d = c;

				if (board[r][d - 1] == 0 && board[r][d] != 0) {
					while (d > 0) {

						if (board[r][d - 1] == 0) {
							board[r][d - 1] = board[r][d];

							board[r][d] = 0;

							somethingHappened = true;
						}

						else {
							break;

						}
						d--;
					}

				}

				if (d > 0) {
					if (board[r][d - 1] == board[r][d] && board[r][d] != 0 && beenCombined[r][d-1] == false) {
						board[r][d - 1] *= 2;

						board[r][d] = 0;
						beenCombined[r][d-1] = true;
						somethingHappened = true;

						score += board[r][d - 1];

					}
				}
			}
		}
		if (somethingHappened) {
			spawn();
		}
		if (!somethingHappened) {
			System.out.println("That move results in no change to the Board.");
		}
	}

	//same code as the moveRight() method except that it cycles through the array in a different order 
	//since it is moving the tiles to the up and not the right
	public void moveUp() {

		boolean somethingHappened = false;
		boolean[][] beenCombined = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 1; c <= cols - 1; c++) {
				beenCombined[r][c] = false;
			}
		}
		for (int c = 0; c < cols; c++) {
			for (int r = 1; r <= rows - 1; r++) {
				int d = r;

				if (board[d - 1][c] == 0 && board[d][c] != 0) {

					while (d > 0) {

						if (board[d - 1][c] == 0) {

							board[d - 1][c] = board[d][c];

							board[d][c] = 0;
							somethingHappened = true;
						} else {
							break;
						}
						d--;
					}
				}

				if (d > 0) {
					if (board[d - 1][c] == board[d][c] && board[d][c] != 0 && beenCombined[d-1][c] == false) {
						board[d - 1][c] *= 2;

						board[d][c] = 0;

						beenCombined[d-1][c] = true;
						somethingHappened = true;

						score += board[d - 1][c];

					}
				}
			}
		}
		if (somethingHappened) {
			spawn();
		}
		if (!somethingHappened) {
			System.out.println("Can't move that way silly :P");
		}

	}

	//same code as the moveRight() method except that it cycles through the array in a different order 
	//since it is moving the tiles to the down and not the right
	public void moveDown() {

		boolean somethingHappened = false;
		boolean[][] beenCombined = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 1; c <= cols - 1; c++) {
				beenCombined[r][c] = false;
			}
		}
		for (int c = 0; c < cols; c++) {
			for (int r = rows - 2; r >= 0; r--) {

				int d = r;

				if (board[d + 1][c] == 0 && board[d][c] != 0) {

					while (d < rows - 1) {

						if (board[d + 1][c] == 0) {

							board[d + 1][c] = board[d][c];

							board[d][c] = 0;
							somethingHappened = true;
						} else {
							break;
						}

						d++;
					}
				}

				if (d < rows - 1) {
					if (board[d + 1][c] == board[d][c] && board[d][c] != 0 && beenCombined[d+1][c] == false) {
						board[d + 1][c] *= 2;

						board[d][c] = 0;
						beenCombined[d+1][c] = true;
						somethingHappened = true;

						score += board[d + 1][c];

					}
				}
			}
		}
		if (somethingHappened) {
			spawn();
		}
		if (!somethingHappened) {
			System.out.println("Can't move that way silly :P");
		}
	}

	//determines if the player has lost by checking if there are any possible moves for the player to make
	public boolean hasLost() {

		boolean somethingCanHappen = false;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board[r][c] == 0) {
					somethingCanHappen = true;
				}
				if (r != 0) {
					if (board[r][c] == board[r - 1][c]) {
						somethingCanHappen = true;
					}
				}
				if (r != rows - 1) {
					if (board[r][c] == board[r + 1][c]) {
						somethingCanHappen = true;
					}
				}
				if (c != 0) {
					if (board[r][c] == board[r][c - 1]) {
						somethingCanHappen = true;
					}
				}
				if (c != cols - 1) {
					if (board[r][c] == board[r][c + 1]) {
						somethingCanHappen = true;
					}
				}
			}
		}
		return !somethingCanHappen;
	}

	//determines if the player has won by checking to see if there is a 2048 tile on the board
	public boolean hasWon() {

		boolean hasWon = false;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board[r][c] == 2048) {
					hasWon = true;
					winCount++;
				}
			}
		}
		if (hasLost()) hasWon = false;
		return hasWon;
	}
	@Override

	//listens for the keys pressed on the keyboard by the player
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed");
		switch (e.getKeyCode()) {

		//performs the move method in the direction that the player presses
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			moveLeft();
			printBoard();
			if(frahm.winMode) frahm.winMode = false;
			hasMoved = true;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			moveRight();
			printBoard();
			if(frahm.winMode) frahm.winMode = false;
			hasMoved = true;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			moveUp();
			printBoard();
			if(frahm.winMode) frahm.winMode = false;
			hasMoved = true;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			moveDown();
			printBoard();
			if(frahm.winMode) frahm.winMode = false;
			hasMoved = true;
			break;

		//Cheat is used for debugging: places 1024 tile, commented out in final version
		/*
		 	case KeyEvent.VK_C:
			board[0][0] = 1024;
			break;
		*/
			//restarts the game when R is pressed
		case KeyEvent.VK_R:
			reStart();
			if(frahm.winMode) frahm.winMode = false;
			break;

			//number keys change the game mode
			
			//1 changes the game to powers of 2 instead of multiples of 2: 11 as opposed to 2048
		case KeyEvent.VK_1:
			if (gameMode == "2048") gameMode = "11";
			else gameMode = "2048";
			if(frahm.winMode) frahm.winMode = false;
			break;
			
			//2 changes the game to have nice rainbow colors that are aesthetically pleasing
		case KeyEvent.VK_2:
			if (!frahm.rainbow2) frahm.rainbow2 = true;
			else frahm.rainbow2 = false;
			if(frahm.winMode) frahm.winMode = false;
			if (frahm.colorPulse) frahm.colorPulse = false;
			break;
			
			//3 changes the game to have a pulsing rainbow, matching values have matching colors at a single instant
		case KeyEvent.VK_3:
			if (!frahm.colorPulse) frahm.colorPulse = true;
			else frahm.colorPulse = false;
			if (frahm.rainbow2) frahm.rainbow2 = false;
			break;
			
			//4 turns off the numbers. When combined with 3, it becomes very challenging
		case KeyEvent.VK_4:
			if (frahm.textOn) frahm.textOn = false;
			else frahm.textOn = true;
			break;
		}


		if (hasLost()) {
			System.out.println("Game Over.");
			printBoard();
		}

		if (hasWon()) {
			System.out.println("You Win!");
			printBoard();
		}

		//redraws the frame constantly
		frahm.reDraw();
		if (randomMode) {
		randomAttack();
		}
	}

	//KeyListener methods declared here
	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	//sets all score, winCount, and all values of the board to 0, also spawns two new tiles
	public void reStart() {

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = 0;
			}
		}
		score = 0;
		winCount = 0;
		winTest = false;
		frahm.winMode = false;

		spawn();
		spawn();

		frahm.initialTime = System.currentTimeMillis();
	}

	//sets the frame to the one in this class so there can only be one frame
	public void setFrame(Frame frame){
		this.frahm = frame;
	}

	//Unused method that made it possible to randomly spawn higher number tiles than 2 and 4
	public void randomAttack() {

		int val = 0;

		int oneInTwenty = rand.nextInt(20);
		int r = rand.nextInt(rows);
		int c = rand.nextInt(cols);

		int randVal = rand.nextInt(100);

		if (oneInTwenty == 0) {
			if (randVal <= 99) {
				val = 128;
			}

			if (randVal <= 50) {
				val = 256;
			}

			if (randVal <= 25) {
				val = 512;
			}

			if (randVal <= 10) {
				val = 1024;
			}

			if (randVal <= 2) {
				val = 2048;
			}
			board[r][c] = val;
		}

	}
	
	//initial attempt to save high score below, but was not completed
	/*
	public static void Save(int score) throws IOException{
		FileWriter f = new FileWriter(new File("e12k439.txt"));
		f.write(score + "\n");
		f.close();
	}

	public static void Load() throws Exception{
		int x;

		BufferedReader saveFile = new BufferedReader(new FileReader("e12k439.txt"));

		x = Integer.parseInt(saveFile.readLine());



		saveFile.close();
		loadScore = x;
	}
	 */

	//method used for performing massive amounts of moves on a larger grid
	//performs thousands of moves every second
	//redraws every 4000 moves to decrease lag
	public void computerGo() {

		computerMode = true;
		int counter = 0;
		while (!hasLost()) {
			moveUp();

			moveLeft();

			moveRight();

			moveDown();

			if (counter >= 1000) {
				frahm.reDraw();
				counter = 0;
			}
			counter++;
		}
	}
	//An unused method that lays out tiles in an interesting pattern
	//Mostly created for educational purposes
	public void ultimateCheckers() {
		int two = 2;

		for (int i = 0; i < cols; i++) {
			board[0][i] = two;
			two*=2;
		}
		for (int i = 0; i < rows; i++) {
			board[i][cols-1] = two;
			two*=2;
		}
		for (int r = 1; r < rows; r++) {
			for (int c = 0; c < cols - 1; c++) {
				board[r][c] = board[r-1][c+1];
			}
		}
	}
}