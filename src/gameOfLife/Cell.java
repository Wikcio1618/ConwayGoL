package gameOfLife;

import java.awt.Color;

public class Cell {
	private static final int XPOS_NEIGHBOURS[] = {1, 1, 1, 0, -1, -1, -1, 0};
	private static final int YPOS_NEIGHBOURS[] = {1, 0, -1, -1, -1, 0, 1, 1};
	
	private int xPos;
	private int yPos;
	
	/*
	 * If the cell is dead, it's lifetime is zero.
	 * Otherwise lifetime indicates for how many cell was alive,
	 * starting counting from the turn it revived on.
	 */
	private int lifetime = 0;
	private int neighborCount = 0;
	
	
	/*
	 * The cell by default is dead
	 */
	Cell(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	/*
	 * Copy constructor, for making backups
	 */
	Cell(Cell cell) {
		this.xPos = cell.xPos;
		this.yPos = cell.yPos;
		this.lifetime = cell.lifetime;
		this.neighborCount = cell.neighborCount;
	}
	
	/*
	 * Returns neighboring cell that is in position
	 * (dx, dy) with respect to this cell
	 */
	Cell getNeighbor(int dx, int dy) {
		return CellsHolder.getCell(xPos + dx, yPos + dy);
	}
	

	/*
	 * Takes every neighbor of this cell
	 * and increments its neighbor count by 1
	 */
	void updateNeighbors() {
		for (int i = 0; i != 8; ++i) {
			getNeighbor(XPOS_NEIGHBOURS[i], YPOS_NEIGHBOURS[i]).incrementNeighborCount();
		}
	}
	
	void kill() {
		lifetime = 0;
	}
	
	void revive() {
		lifetime = 1;
	}
	
	private void incrementNeighborCount() {
		++neighborCount;
	}
	
	public int getNeighborCount() {
		return neighborCount;
	}
	
	public void incrementLifetime() {
		++lifetime;
	}
	
	public int getLifetime() {
		return lifetime;
	}
	
	public void setLifetime (int l) {
		lifetime = l;
	}



	public Color getColor() {
		switch (lifetime) {
		case 0: return Color.blue; // Blue color should never be visible - the cell is dead
		case 1: return new Color(0x660000);
		case 2: return new Color(0xe66700);
		case 3: return new Color(0xe09600);
		case 4: return new Color(0xbbe000);
		default: return new Color(0x3ffc00);
		}
	}

	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}



	public void resetNeighbourCount() {
		neighborCount = 0;
	}
}