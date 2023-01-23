package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class GameForm extends JFrame {

	private JPanel gameAreaPlaceholder = new JPanel();
	private JLabel scoreLabel = new JLabel();
	private JLabel levelLabel = new JLabel();


	private final JButton btnNewButton = new JButton("Main Menu");

	private GameArea ga;
	private GameThread gt;

	public GameForm() {
		setBackground(Color.LIGHT_GRAY);

		this.setSize(650, 657);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		gameAreaPlaceholder.setBounds(150, 10, 300, 600);
		gameAreaPlaceholder.setBorder(new LineBorder(Color.black));

		scoreLabel.setText("Score: 0");
		scoreLabel.setOpaque(true);
		scoreLabel.setBounds(460, 10, 166, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

		levelLabel.setText("Level: 1");
		levelLabel.setOpaque(true);
		levelLabel.setBounds(460, 50, 166, 30);
		levelLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

		this.getContentPane().setLayout(null);

		ga = new GameArea(gameAreaPlaceholder, 10);
		ga.setSize(300, 600);
		ga.setLocation(150, 10);

		getContentPane().add(scoreLabel);
		getContentPane().add(levelLabel);
		getContentPane().add(ga);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gt.interrupt();
				setVisible(false);
				Tetris.showStartUp();
			}
		});

		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton.setBounds(10, 10, 133, 30);
		btnNewButton.setFocusable(false);

		getContentPane().add(btnNewButton);
//		this.setVisible(false);
//		this.setUndecorated(true);

		initControls();

	}

	private void initControls() {
		InputMap im = this.getRootPane().getInputMap();
		ActionMap am = this.getRootPane().getActionMap();

		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");

		am.put("right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ga.moveBlockRight();
			}

		});
		am.put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ga.moveBlockLeft();
			}

		});
		am.put("up", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ga.rotateBlock();
			}

		});
		am.put("down", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ga.dropBlock();
			}

		});
	}

	public void startGame() {
		ga.initBackgroundArray();
		gt = new GameThread(ga, this);
		gt.start();
	}

	public void updateScore(int score) {
		scoreLabel.setText("Score: " + score);
	}

	public void updateLevel(int level) {
		levelLabel.setText("Level: " + level);
	}

	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				new GameForm().setVisible(true);

			}
		});

	}

}
