package gof;

public class CellsHolder {
	
	static final int WIDTH = 512;
	static final int HEIGHT = 512;
	
	private static Cell[][] cells;
	private static Cell[][] quickBackup;
	
	/*
	 * Initializes all the cells in array and initializes backup information
	 */
	static void initCells() {
		cells = new Cell[HEIGHT][WIDTH];
		for (int i = 0; i != HEIGHT; ++i) {
			for (int j = 0; j != WIDTH; ++j) {
				cells[i][j] = new Cell(i, j);
			}
		}
		
		//Left as a sign of gratitude for serving for so long :-)
		/*
		cells[11][12].setAlive(true);
		cells[12][12].setAlive(true);
		cells[12][13].setAlive(true);
		cells[17][11].setAlive(true);
		cells[16][13].setAlive(true);
		cells[17][13].setAlive(true);
		cells[18][13].setAlive(true);
		
		cells[31][13].setAlive(true);
		cells[32][11].setAlive(true);
		cells[32][13].setAlive(true);
		cells[33][12].setAlive(true);
		cells[33][13].setAlive(true);
		*/
	}
	
	static void initQuickBackup () {
		quickBackup = new Cell[HEIGHT][WIDTH];
		for (int i = 0; i != HEIGHT; ++i) {
			for (int j = 0; j != WIDTH; ++j) {
				quickBackup[i][j] = new Cell(i, j);
			}
		}
	}
	/*
	 * Returns cell with given coordinates,
	 * while taking care of periodic boundary conditions
	 */
	static Cell getCell(int x, int y) {
		int _x = x % WIDTH;
		int _y = y % HEIGHT;
		return cells[_x][_y];
	}
	
	/*
	 * Iterates over every living cell,
	 * and increments its neighbors neigbourCount
	 */
	static void updateNeigboursCount() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.isAlive()) {
					cell.updateNeigbours();
				}
			}
		}
	}
	
	
	/*
	 * Decides if cells is meant to be alive or dead,
	 * resets the neighbor count for every cell to 0
	 */
	static void updateAliveStatus() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				final int n = cell.getNeigbourCount();
				if (cell.isAlive() && (n != 2 && n != 3)) {
					cell.setAlive(false);
					cell.setLifetime(0);
				}
				else if (cell.isAlive() == false && n == 3) {
					cell.setAlive(true);
				}
				else if (cell.isAlive() && (n == 2 || n == 3)) {
					cell.incrementLifetime();
				}
				cell.setNeighbourCount(0);
			}	
		}
	}
	
	/*
	 * Sets every cell to dead state
	 */
	static void clearAll() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				cell.setAlive(false);
			}
		}
	}
	
	static Cell[][] getCells() {
		return cells;
	}
	
	static Cell[][] getQuickBackup() {
		return quickBackup;
	}
	
	static void setQuickBackup (int x, int y, boolean b) {
		quickBackup[x][y].setAlive(b); 
	}
	
	// Probably unnecesary, see: Cell.setAlive()
	static void clearLifetimes () {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				cell.setLifetime(0);
			}
		}
	}
}