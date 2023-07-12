package gof;

public class Cell {
	private static final int XPOS_NEIGHBOURS[] = {1, 1, 1, 0, -1, -1, -1, 0};
	private static final int YPOS_NEIGHBOURS[] = {1, 0, -1, -1, -1, 0, 1, 1};
	
	private int xPos;
	private int yPos;
	
	private boolean alive = false;
	private int lifetime = 0;        //meaning that it is the n-th turn the cell takes part in
	private int neigbourCount = 0;
	
	
	// The cell by default is dead
	Cell(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	/*
	 * Returns neighbouring cell that is in position
	 * (dx, dy) with respect to this cell
	 */
	Cell getNeigbour(int dx, int dy) {
		return null;
	}
	
	
	/*
	 * Takes every neigbour of this cell
	 * and increments its neighbour count by 1
	 */
	void updateNeigbours() {
		for (int i = 0; i != 8; ++i) {
			final int nX = xPos + XPOS_NEIGHBOURS[i];
			final int nY = yPos + YPOS_NEIGHBOURS[i];
			CellsHolder.getCell(nX, nY).neigbourCount++;
		}
	}
	
	
	/*
	 * Tells if the cell is alive
	 */
	boolean isAlive() {
		return alive;
	}
	
	void setAlive(boolean b) {
		alive = b;
		if (b)
			lifetime++;
		else
			setLifetime (0);
	}

	public int getNeigbourCount() {
		return neigbourCount;
	}
	
	public void setNeighbourCount(int n) {
		neigbourCount = n;
	}
	
	public void incrementLifetime () {
		lifetime++;
	}
	
	public int getLifetime () {
		return lifetime;
	}
	
	public void setLifetime (int lifetime) {
		this.lifetime = lifetime;
	}
}