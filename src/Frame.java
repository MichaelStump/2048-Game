
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame {


	public JButton[][] buttonList;

	Random rand = new Random();

	public Font font1 = new Font("Dialog", Font.BOLD, 65);
	public Font font2 = new Font("Dialog", Font.BOLD, 55);

	private Board board;

	//declarations of all the variables needed for timing
	public double initialTime;
	public double currentTime;
	public double timeMillis;
	public double timeS;
	public int minutes;
	public int seconds;
	public int tenthSeconds;
	public int winMin;
	public int winSec;
	public int winTenthSec;

	//declarations of board mode booleans
	public boolean rainbow = false;
	public boolean rainbow2 = false;
	public boolean partyMode = false;
	public boolean colorPulse = false;
	public boolean textOn = true;
	
//	public int re = 255;
//	public int g = 0;
//	public int b = 0;
	
	public int[] re = new int[14];
	public int[] g = new int[14];
	public int[] b = new int[14];

	//Constructor of the Frame sets the board, gives values to the JButton array, and constructs the JFrame
	public Frame(Board board) {
		this.board = board;
		this.board.setFrame(this);

		buttonList = new JButton[board.rows][board.cols];
		for (int r = 0; r < board.rows; r++) {
			for (int c = 0; c < board.cols; c++) {

				buttonList[r][c] = new JButton(board.intToString(board.board[r][c]));
				buttonList[r][c].setFocusable(false);
			}
		}

		initColorPulse();
		
		setsFont();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("2048");
		setSize(200 * board.cols, 200 * board.rows);

		GridLayout grid = new GridLayout(board.rows,board.cols);
		setLayout(grid);

		this.reDraw();
		setVisible(true);
		setResizable(false);
		initialTime = System.currentTimeMillis();
	}

	public void initColorPulse() {
		re[0] = 0;
		g[0] = 0;
		b[0] = 0;
		
		re[1] = 255;
		g[1] = 0;
		b[1] = 0;
		
		re[2] = 255;
		g[2] = 0;
		b[2] = 127;
		
		re[3] = 255;
		g[3] = 0;
		b[3] = 255;
		
		re[4] = 127;
		g[4] = 0;
		b[4] = 255;
		
		re[5] = 0;
		g[5] = 0;
		b[5] = 255;
		
		re[6] = 0;
		g[6] = 127;
		b[6] = 255;
		
		re[7] = 0;
		g[7] = 255;
		b[7] = 255;

		re[8] = 0;
		g[8] = 255;
		b[8] = 127;
		
		re[9] = 0;
		g[9] = 255;
		b[9] = 0;
		
		re[10] = 127;
		g[10] = 255;
		b[10] = 0;
		
		re[11] = 255;
		g[11] = 255;
		b[11] = 0;
		
		re[12] = 255;
		g[12] = 127;
		b[12] = 0;
		
		re[13] = 255;
		g[13] = 0;
		b[13] = 0;
	}
	
	//Sets all the fonts to the one created
	public void setsFont() {
		for (int r = 0; r < board.rows; r++) {
			for (int c = 0; c < board.cols; c++) {
				if (board.board[r][c] <= 9999) buttonList[r][c].setFont(font1);

				else {
					
					if (board.computerMode) {
						int numDigits = (int)(Math.log10(board.board[r][c])+1);
//						System.out.println(numDigits);
						buttonList[r][c].setFont(new Font("Dialog", Font.BOLD, (int) (50 - (((numDigits)-3)*(8*1/(Math.log10(numDigits)))))));
					}
					else { 
						buttonList[r][c].setFont(font2);
					}
				}

			}
		} 	
	}

	//Redraws the board buttons to have proper text and color
	//Redraws the timer
	//Redraws the title if the user has won/lost
	public void reDraw() {

		for (int r = 0; r < board.rows; r++) {
			for (int c = 0; c < board.cols; c++) {

				setTileColor(buttonList[r][c], r, c);
				setsFont();
				if (textOn) {
				buttonList[r][c].setText(board.intToString(board.board[r][c]));
				}
				else {
					buttonList[r][c].setText(board.intToString(0));
				}
				add(buttonList[r][c]);

			}
		}

		if (board.hasLost()) {
			this.setTitle("2048                         GAME OVER!            Press R to Restart                      Score: " + String.valueOf(this.board.score));
		}

		else if (board.winCount == 1) {
			this.setTitle("2048         YOU WIN!       Keep Playing to Keep Going      " + "Timer: " + winMin +  ":" + winSec + "." + winTenthSec +"    Score:  " + String.valueOf(this.board.score));
		}
		else if (board.winCount >=1) {

			this.setTitle("2048   " + "Timer: " + winMin +  ":" + winSec + "." + winTenthSec + "    Score: " + String.valueOf(this.board.score));
		}
		else {
			this.setTitle("2048   " + "Timer: " + minutes +  ":" + seconds + "." + tenthSeconds + "    Score: " + String.valueOf(this.board.score));
		}
		setVisible(true);
//		try {
//			Thread.sleep(0);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	//Sets the tile color based on what mode the user is in
	public void setTileColor(JButton button, int r, int c) {

		if (partyMode) {
			int randCol = rand.nextInt(11);
			if (randCol == 0) buttonList[r][c].setBackground(new Color(204, 192, 179));
			if (randCol == 1) buttonList[r][c].setBackground(new Color(255, 0, 0));
			if (randCol == 2) buttonList[r][c].setBackground(new Color(255, 115, 0));
			if (randCol == 3) buttonList[r][c].setBackground(new Color(255, 179, 0));
			if (randCol == 4) buttonList[r][c].setBackground(new Color(255, 225, 0));
			if (randCol == 5) buttonList[r][c].setBackground(new Color(255, 255, 0));
			if (randCol == 6) buttonList[r][c].setBackground(new Color(0, 255, 0));
			if (randCol == 7) buttonList[r][c].setBackground(new Color(0, 255, 170));
			if (randCol == 8) buttonList[r][c].setBackground(new Color(0, 255, 255));
			if (randCol == 9) buttonList[r][c].setBackground(new Color(0, 157, 255));
			if (randCol == 10) buttonList[r][c].setBackground(new Color(0, 0, 255));
			if (randCol == 11) buttonList[r][c].setBackground(new Color(188, 19, 254));
			if (randCol <= 5) buttonList[r][c].setForeground(Color.WHITE);
			if (randCol > 5) buttonList[r][c].setForeground(Color.BLACK);


		}
		else if (rainbow) {
			if (board.board[r][c] == 0) buttonList[r][c].setBackground(new Color(204, 192, 179));
			if (board.board[r][c] == 2) buttonList[r][c].setBackground(new Color(255, 0, 0));
			if (board.board[r][c] == 4) buttonList[r][c].setBackground(new Color(255, 115, 0));
			if (board.board[r][c] == 8) buttonList[r][c].setBackground(new Color(255, 179, 0));
			if (board.board[r][c] == 16) buttonList[r][c].setBackground(new Color(255, 225, 0));
			if (board.board[r][c] == 32) buttonList[r][c].setBackground(new Color(255, 255, 0));
			if (board.board[r][c] == 64) buttonList[r][c].setBackground(new Color(0, 255, 0));
			if (board.board[r][c] == 128) buttonList[r][c].setBackground(new Color(0, 255, 170));
			if (board.board[r][c] == 256) buttonList[r][c].setBackground(new Color(0, 255, 255));
			if (board.board[r][c] == 512) buttonList[r][c].setBackground(new Color(0, 157, 255));
			if (board.board[r][c] == 1024) buttonList[r][c].setBackground(new Color(0, 0, 255));
			if (board.board[r][c] == 2048) buttonList[r][c].setBackground(new Color(188, 19, 254));
			if (board.board[r][c] == 4096) buttonList[r][c].setBackground(new Color(58, 59, 51));
			if (board.board[r][c] >= 8192) buttonList[r][c].setBackground(new Color(58, 59, 51));

			buttonList[r][c].setForeground(Color.WHITE);
		}
		else if (rainbow2) {
			if (board.board[r][c] == 0) buttonList[r][c].setBackground(new Color(204, 192, 179));
			if (board.board[r][c] == 2048) buttonList[r][c].setBackground(new Color(255, 0, 0));
			if (board.board[r][c] == 1024) buttonList[r][c].setBackground(new Color(255, 115, 0));
			if (board.board[r][c] == 512) buttonList[r][c].setBackground(new Color(255, 179, 0));
			if (board.board[r][c] == 256) buttonList[r][c].setBackground(new Color(255, 225, 0));
			if (board.board[r][c] == 128) buttonList[r][c].setBackground(new Color(255, 255, 0));
			if (board.board[r][c] == 64) buttonList[r][c].setBackground(new Color(0, 255, 0));
			if (board.board[r][c] == 32) buttonList[r][c].setBackground(new Color(0, 255, 170));
			if (board.board[r][c] == 16) buttonList[r][c].setBackground(new Color(0, 255, 255));
			if (board.board[r][c] == 8) buttonList[r][c].setBackground(new Color(0, 157, 255));
			if (board.board[r][c] == 4) buttonList[r][c].setBackground(new Color(0, 0, 255));
			if (board.board[r][c] == 2) buttonList[r][c].setBackground(new Color(188, 19, 254));
			if (board.board[r][c] == 4096) buttonList[r][c].setBackground(new Color(58, 59, 51));
			if (board.board[r][c] >= 8192) buttonList[r][c].setBackground(new Color(58, 59, 51));

			buttonList[r][c].setForeground(Color.WHITE);
		}
		else if (colorPulse) {
			for (int i = 1; i < 14; i++) {
				colorPulse2(i);
				if (board.board[r][c] == 0) buttonList[r][c].setBackground(new Color(204, 192, 179));
				if (board.board[r][c] == 2) {
					buttonList[r][c].setBackground(new Color(re[1], g[1], b[1]));
					buttonList[r][c].setForeground(new Color(g[1], b[1], re[1]));
				}
				if (board.board[r][c] == 4) {
					buttonList[r][c].setBackground(new Color(re[2], g[2], b[2]));
					buttonList[r][c].setForeground(new Color(g[2], b[2], re[2]));
				}
				
				if (board.board[r][c] == 8) {
					buttonList[r][c].setBackground(new Color(re[3], g[3], b[3]));
					buttonList[r][c].setForeground(new Color(g[3], b[3], re[3]));
				}
				if (board.board[r][c] == 16) {
					buttonList[r][c].setBackground(new Color(re[4], g[4], b[4]));
					buttonList[r][c].setForeground(new Color(g[4], b[4], re[4]));
				}
				if (board.board[r][c] == 32) {
					buttonList[r][c].setBackground(new Color(re[5], g[5], b[5]));
					buttonList[r][c].setForeground(new Color(g[5], b[5], re[5]));
				}
				if (board.board[r][c] == 64) {
					buttonList[r][c].setBackground(new Color(re[6], g[6], b[6]));
					buttonList[r][c].setForeground(new Color(g[6], b[6], re[6]));
				}
				if (board.board[r][c] == 128) {
					buttonList[r][c].setBackground(new Color(re[7], g[7], b[7]));
					buttonList[r][c].setForeground(new Color(g[7], b[7], re[7]));
				}
				if (board.board[r][c] == 256) {
					buttonList[r][c].setBackground(new Color(re[8], g[8], b[8]));
					buttonList[r][c].setForeground(new Color(g[8], b[8], re[8]));
				}
				if (board.board[r][c] == 512) {
					buttonList[r][c].setBackground(new Color(re[9], g[9], b[9]));
					buttonList[r][c].setForeground(new Color(g[9], b[9], re[9]));
				}
				if (board.board[r][c] == 1024) {
					buttonList[r][c].setBackground(new Color(re[10], g[10], b[10]));
					buttonList[r][c].setForeground(new Color(g[10], b[10], re[10]));
				}
				if (board.board[r][c] == 2048) {
					buttonList[r][c].setBackground(new Color(re[11], g[11], b[11]));
					buttonList[r][c].setForeground(new Color(g[11], b[11], re[11]));
				}
				if (board.board[r][c] == 4096) {
					buttonList[r][c].setBackground(new Color(re[12], g[12], b[12]));
					buttonList[r][c].setForeground(new Color(g[12], b[12], re[12]));
				}
				if (board.board[r][c] == 8192) {
					buttonList[r][c].setBackground(new Color(re[13], g[13], b[13]));
					buttonList[r][c].setForeground(new Color(g[13], b[13], re[13]));
				}

			}

		}
		else {
			if (board.board[r][c] == 0) buttonList[r][c].setBackground(new Color(204, 192, 179));
			if (board.board[r][c] == 2) buttonList[r][c].setBackground(new Color(238, 228, 218));
			if (board.board[r][c] == 4) buttonList[r][c].setBackground(new Color(237, 224, 200));
			if (board.board[r][c] == 8) buttonList[r][c].setBackground(new Color(242, 177, 121));
			if (board.board[r][c] == 16) buttonList[r][c].setBackground(new Color(245, 149, 99));
			if (board.board[r][c] == 32) buttonList[r][c].setBackground(new Color(246, 124, 95));
			if (board.board[r][c] == 64) buttonList[r][c].setBackground(new Color(246, 94, 59));
			if (board.board[r][c] == 128) buttonList[r][c].setBackground(new Color(237, 207, 114));
			if (board.board[r][c] == 256) buttonList[r][c].setBackground(new Color(237, 204, 97));
			if (board.board[r][c] == 512) buttonList[r][c].setBackground(new Color(237, 200, 80));
			if (board.board[r][c] == 1024) buttonList[r][c].setBackground(new Color(237, 197, 63));
			if (board.board[r][c] == 2048) buttonList[r][c].setBackground(new Color(237, 194, 46));
			if (board.board[r][c] == 4096) buttonList[r][c].setBackground(new Color(58, 59, 51));
			if (board.board[r][c] >= 8192) buttonList[r][c].setBackground(new Color(58, 59, 51));

			if (board.board[r][c] >= 8) buttonList[r][c].setForeground(Color.WHITE);
			else buttonList[r][c].setForeground(new Color(119, 110, 101));
		}

	}

//	public void colorPulse(JButton[][] buttonList, int r, int c) {
//		
//		if (re == 255 && g < 255 && b == 0) {
//			g++;
//		}
//		
//		else if (g == 255 && re > 0 && b == 0) {
//			re--;
//		}
//		
//		else if (g == 255 && re == 0 && b < 255) {
//			b++;
//		}
//		
//		else if (b == 255 && g > 0 && re == 0) {
//			g--;
//		}
//		
//		else if (b == 255 && g == 0 && re < 255) {
//			re++;
//		}
//		
//		else if (re == 255 && b > 0 && g == 0) {
//			b--;
//		}
//		buttonList[r][c].setBackground(new Color(re, g, b));
//		buttonList[r][c].setForeground(new Color(b, re, g));
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
public void colorPulse2(int i) {
		
		if (re[i] == 255 && g[i] < 255 && b[i] == 0) {
			g[i]++;
		}
		
		else if (g[i] == 255 && re[i] > 0 && b[i] == 0) {
			re[i]--;
		}
		
		else if (g[i] == 255 && re[i] == 0 && b[i] < 255) {
			b[i]++;
		}
		
		else if (b[i] == 255 && g[i] > 0 && re[i] == 0) {
			g[i]--;
		}
		
		else if (b[i] == 255 && g[i] == 0 && re[i] < 255) {
			re[i]++;
		}
		
		else if (re[i] == 255 && b[i] > 0 && g[i] == 0) {
			b[i]--;
		}
		
//		buttonList[r][c].setBackground(new Color(re, g, b));
//		buttonList[r][c].setForeground(new Color(b, re, g));

	}
	//runs the timer and converts milliseconds to comprehensive time
	public void time() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentTime = System.currentTimeMillis();
			timeMillis = currentTime - initialTime;
			timeS = (int) timeMillis/1000;

			minutes = (int) timeS / 60;
			seconds = (int) timeS - (minutes * 60);
			tenthSeconds = (int) (timeMillis - (timeS * 1000))/100;
			//	System.out.println(timeS + "    " + minutes + ":" + seconds);
			reDraw();

			if ((board.winCount == 1 && board.winTest == false)) {
				board.winTest=true;
				partyMode = true;
				break;
			}
		}
		winMin = minutes;
		winSec = seconds;
		winTenthSec = tenthSeconds;
		time();
	}

}