package tetris;

import java.awt.Color;
import java.util.Random;

public class TetrisBlock {

	private int[][] shape;
	private Color color;
	private int x, y;
	private int[][][] shapes;
	private int currentRotation;

	private Color[] availableColors = { Color.orange, Color.blue, Color.green, Color.yellow, Color.magenta };

	public TetrisBlock(int[][] shape) {

		this.shape = shape;

		initShapes();

	}

	private void initShapes() {
		shapes = new int[4][][];

		for (int i = 0; i < 4; i++) {
			int r = shape[0].length;
			int c = shape.length;

			shapes[i] = new int[r][c];

			for (int y = 0; y < r; y++) {
				for (int x = 0; x < c; x++) {
					shapes[i][y][x] = shape[c - x - 1][y];
				}
			}

			shape = shapes[i];
		}
	}

	public void spawn(int gridWidth) {
		
		Random r = new Random();

		currentRotation = r.nextInt(4);
		shape = shapes[currentRotation];

		y = -getHeight();
		x = r.nextInt(gridWidth - getWidth());

		color = availableColors[r.nextInt(availableColors.length)];

	}

	public int[][] getShape() {
		return shape;
	}

	public Color getColor() {
		return color;
	}

	public int getHeight() {
		return shape.length;
	}

	public int getWidth() {
		return shape[0].length;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void moveDown() {
		y++;
	}

	public void moveLeft() {
		x--;
	}

	public void moveRight() {
		x++;
	}

	public void rotate() {
		currentRotation++;
		if (currentRotation > 3)
			currentRotation = 0;
		shape = shapes[currentRotation];
	}

	public void unRotate() {
		currentRotation--;
		if (currentRotation < 0)
			currentRotation = 3;
		shape = shapes[currentRotation];
	}

	public int getBottomEdge() {
		return y + getHeight();
	}

	public int getRightEdge() {
		return x + getWidth();
	}

	public int getLeftEdge() {
		return x;
	}

}
