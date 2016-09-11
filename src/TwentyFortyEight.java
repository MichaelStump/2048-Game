public class TwentyFortyEight {

	public static void main(String[] args) {
		//Instantiates Board object.  Only allows one board object to be created
		Board board = Board.getInstance();
		//creates a new frame with the created Board object
		Frame frame = new Frame(board);
		//spawns two numbers on the board
		board.spawn();
		board.spawn();
		//redraws the board to show the spawned number
		frame.reDraw();
		//adds a KeyListener to the program
		frame.addKeyListener(board);

		//Commented out line below has the computer run random moves until it loses
		//board.computerGo();
		
		//starts the timer
		frame.time();
		
	}
}
