package tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartUpForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartUpForm frame = new StartUpForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartUpForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 657);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Tetris.start();
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton.setBounds(118, 291, 400, 40);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFocusable(false);
		contentPane.add(btnNewButton);
		
		JButton btnLeaderboard = new JButton("LeaderBoard");
		btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Tetris.showLeaderboard();
			}
		});
		btnLeaderboard.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnLeaderboard.setBackground(Color.LIGHT_GRAY);
		btnLeaderboard.setBounds(118, 341, 400, 40);
		btnLeaderboard.setFocusable(false);
		contentPane.add(btnLeaderboard);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnQuit.setBackground(Color.LIGHT_GRAY);
		btnQuit.setBounds(118, 391, 400, 40);
		btnQuit.setFocusable(false);
		contentPane.add(btnQuit);
	}
}
