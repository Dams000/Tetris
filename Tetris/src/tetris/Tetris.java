package tetris;

import javax.swing.JOptionPane;

public class Tetris {

	private static GameForm gf;
	private static LeaderBoardForm lf;
	private static StartUpForm sf;
	
	private static AudioPlayer audio = new AudioPlayer();

	public static void start() {
		gf.setVisible(true);
		gf.startGame();
	}
	
	public static void showLeaderboard() {
		lf.setVisible(true);
	}
	
	public static void showStartUp() {
		sf.setVisible(true);
	}
	
	public static void gameOver(int score) {
		playGameover();
		
		String playerName = JOptionPane.showInputDialog("Game Over !\nPlease enter your name:");
		gf.setVisible(false);
		lf.addPlayer(playerName, score);
	}
	
	public static void playClear() {
		audio.playClearLine();
	}
	
	public static void playGameover() {
		audio.playGameoverSound();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				gf = new GameForm();
				lf = new LeaderBoardForm();
				sf = new StartUpForm();
				
				sf.setVisible(true);
				
			}
		});
		
	}

}
