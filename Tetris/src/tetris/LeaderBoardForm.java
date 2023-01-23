package tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Dimension;

public class LeaderBoardForm extends JFrame {

	private JPanel contentPane;
	private JTable leaderboard;

	private DefaultTableModel tm;

	private String leaderboardFile = "leaderboard";
	
	private TableRowSorter<TableModel> sorter;

	private final String[] columnNames = { "Names", "Score" };
	private final Object[][] data = { {} };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeaderBoardForm frame = new LeaderBoardForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LeaderBoardForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 657);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Main Menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Tetris.showStartUp();
			}
		});
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(10, 10, 133, 30);
		contentPane.add(btnNewButton);

		leaderboard = new JTable(data, columnNames);
		leaderboard.setSelectionBackground(Color.WHITE);
		leaderboard.setCellSelectionEnabled(true);
		leaderboard.setColumnSelectionAllowed(true);
		leaderboard.setFocusable(false);
		leaderboard.setGridColor(Color.DARK_GRAY);
		leaderboard.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Score"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		leaderboard.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		leaderboard.setBounds(10, 50, 616, 560);
		contentPane.add(leaderboard);

		initTableData();
		initTableSorter();
	}

	@SuppressWarnings("unchecked")
	private void initTableData() {
		Vector<String> ci = new Vector<String>();
		ci.add("Player");
		ci.add("Score");

		tm = (DefaultTableModel) leaderboard.getModel();

		try {
			FileInputStream fs = new FileInputStream(leaderboardFile);
			ObjectInputStream os = new ObjectInputStream(fs);

			tm.setDataVector((Vector<Vector>) os.readObject(), ci);

			os.close();
			fs.close();
		} catch (Exception e) {
		}
	}
	
	private void initTableSorter() {
		sorter = new TableRowSorter<>(tm);
		leaderboard.setRowSorter(sorter);
		
		ArrayList<SortKey> keys = new ArrayList<>();
		keys.add(new SortKey(1, SortOrder.DESCENDING));
		
		sorter.setSortKeys(keys);
	}

	private void saveLeaderboard() {
		try {
			FileOutputStream fs = new FileOutputStream(leaderboardFile);
			ObjectOutputStream os = new ObjectOutputStream(fs);

			os.writeObject(tm.getDataVector());

			os.close();
		} catch (Exception e) {
		}

		tm.getDataVector();
	}

	public void addPlayer(String playerName, int score) {
		tm.addRow(new Object[] { playerName, score });
		sorter.sort();
		saveLeaderboard();

		this.setVisible(true);
	}
}
