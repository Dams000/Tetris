package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import tetrisblocks.*;

public class GameArea extends JPanel {

	private int rows;
	private int columns;
	private int cellSize;

	private Color previousColor;
	private Color[][] background;

	private TetrisBlock block;
	private TetrisBlock previousBlock;

	private TetrisBlock[] blocks;

	public GameArea(JPanel placeholder, int columns) {

//		placeholder.setVisible(false);
		this.setBounds(placeholder.getBounds());
		this.setBackground(placeholder.getBackground());
		this.setBorder(placeholder.getBorder());

		this.columns = columns;
		cellSize = (this.getBounds().width / this.columns);
		rows = this.getBounds().height / cellSize;

		blocks = new TetrisBlock[] { new IShape(), new JShape(), new LShape(), new OShape(), new SShape(), new TShape(),
				new ZShape() };

	}

	public void initBackgroundArray() {
		background = new Color[rows][columns];
	}

	public void spawnBlock() {

		Random r = new Random();

		block = blocks[r.nextInt(blocks.length)];
		block.spawn(columns);

		if (previousColor != null)
			while (block.getColor() == previousColor)
				block.spawn(columns);

//		if (previousBlock != null)
//			while (block.getShape() == previousBlock.getShape())
//				block.spawn(columns);

		previousColor = block.getColor();
		previousBlock = block;
	}

	public boolean isBlockOutOfBounds() {
		if (block.getY() < 0) {
			block = null;
			return true;
		}
		return false;
	}

	public boolean moveBlockDown() {
		if (!checkBottom()) {
			return false;
		}
		block.moveDown();
		repaint();

		return true;
	}

	public void moveBlockRight() {
		if (block == null)
			return;
		if (!checkRight())
			return;
		block.moveRight();
		repaint();
	}

	public void moveBlockLeft() {
		if (block == null)
			return;
		if (!checkLeft())
			return;
		block.moveLeft();
		repaint();
	}

	public void rotateBlock() {
		if (block == null)
			return;
		block.rotate();

		if (block.getLeftEdge() < 0)
			block.setX(0);
		if (block.getRightEdge() >= columns)
			block.setX(columns - block.getWidth());
		if (block.getBottomEdge() >= rows)
			block.setY(rows - block.getHeight());

		if (checkBackground())
			block.unRotate();

		repaint();
	}

	public void dropBlock() {
		if (block == null)
			return;
		while (checkBottom())
			block.moveDown();
		repaint();
	}

	private boolean checkBackground() {

		int shape[][] = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();

		for (int c = 0; c < w; c++) {
			for (int r = 0; r < h; r++) {
				if (shape[r][c] != 0) {
					int x = c + block.getX();
					int y = r + block.getY();

					if (y < 0)
						break;

					if (background[y][x] != null)
						return true;
//					break;
				}
			}
		}
		return false;

	}

	private boolean checkBottom() {
		if (block.getBottomEdge() == rows)
			return false;

		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();

		for (int c = 0; c < w; c++) {
			for (int r = h - 1; r >= 0; r--) {
				if (shape[r][c] != 0) {
					int x = c + block.getX();
					int y = r + block.getY() + 1;
					if (y < 0)
						break;
					if (background[y][x] != null)
						return false;
					break;
				}

			}
		}
		return true;
	}

	private boolean checkRight() {
		if (block.getRightEdge() == columns)
			return false;

		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();

		for (int r = 0; r < h; r++) {
			for (int c = w - 1; c >= 0; c--) {
				if (shape[r][c] != 0) {
					int x = c + block.getX() + 1;
					int y = r + block.getY();
					if (y < 0)
						break;
					if (background[y][x] != null)
						return false;
					break;
				}

			}
		}
		return true;
	}

	private boolean checkLeft() {
		if (block.getLeftEdge() == 0)
			return false;

		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();

		for (int r = 0; r < h; r++) {
			for (int c = 0; c < w; c++) {
				if (shape[r][c] != 0) {
					int x = c + block.getX() - 1;
					int y = r + block.getY();
					if (y < 0)
						break;
					if (background[y][x] != null)
						return false;
					break;
				}

			}
		}
		return true;
	}

	public int clearLines() {

		boolean lineFilled;
		int linesCleared = 0;

		for (int r = rows - 1; r >= 0; r--) {
			lineFilled = true;
			for (int c = 0; c < columns; c++) {
				if (background[r][c] == null) {
					lineFilled = false;
					break;
				}
			}
			if (lineFilled) {
				linesCleared++;
				clearLine(r);
				shiftDown(r);
				clearLine(0);

				r++;

				repaint();
			}
		}
		if (linesCleared > 0)
			Tetris.playClear();

		return linesCleared;
	}

	private void clearLine(int row) {
		for (int i = 0; i < columns; i++)
			background[row][i] = null;
	}

	private void shiftDown(int r) {
		for (int row = r; row > 0; row--) {
			for (int col = 0; col < columns; col++) {
				background[row][col] = background[row - 1][col];
			}
		}
	}

	public void moveBlockToBackground() {
		int[][] shape = block.getShape();
		int h = block.getHeight();
		int w = block.getWidth();

		int xPos = block.getX();
		int yPos = block.getY();

		for (int r = 0; r < h; r++) {
			for (int c = 0; c < w; c++) {
				if (shape[r][c] == 1)
					background[r + yPos][c + xPos] = block.getColor();
			}
		}
	}

	private void drawBlock(Graphics g) {

		int h = block.getHeight();
		int w = block.getWidth();
		int[][] shape = block.getShape();
		Color c = block.getColor();

		for (int row = 0; row < h; row++) {
			for (int column = 0; column < w; column++) {
				if (shape[row][column] == 1) {

					int x = (block.getX() + column) * cellSize;
					int y = (block.getY() + row) * cellSize;

					drawSquare(g, c, x, y);
				}
			}
		}

	}

	private void drawBackground(Graphics g) {

		Color color;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {

				color = background[r][c];

				if (color != null) {
					int x = c * cellSize;
					int y = r * cellSize;

					drawSquare(g, color, x, y);
				}

			}
		}

	}

	private void drawSquare(Graphics g, Color color, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, cellSize, cellSize);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, cellSize, cellSize);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		drawBackground(g);
		drawBlock(g);

	}

}
