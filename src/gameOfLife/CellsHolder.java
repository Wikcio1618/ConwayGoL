package gameOfLife;

// Authors - Wikcio & MŒ
public class CellsHolder {
	
	static final int WIDTH = 1024;
	static final int HEIGHT = 1024;
	
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
		while (x < 0) {
			x += WIDTH;
		}
		while (y < 0) {
			y += HEIGHT;
		}
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
				if (cell.getLifetime() != 0) {
					cell.updateNeighbors();
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
				final int n = cell.getNeighborCount();
				
				if (cell.getLifetime() != 0) {
					if (n != 2 && n != 3) {
						cell.kill();
					}
					else {
						cell.incrementLifetime();
					}
				}
				else {
					if (n == 3) {
						cell.revive();
					}
				}
				
				cell.resetNeighbourCount();
			}	
		}
	}
	
	
	
	
	/*
	 * Sets every cell to dead state
	 */
	static void clearAll() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				cell.kill();
			}
		}
	}
	
	/*
	 * Clears the backup - kills all the cells in backup
	 */
	static void clearBackup() {
		for (Cell[] row : quickBackup) {
			for (Cell cell : row) {
				cell.kill();
			}
		}
	}
	
	static Cell[][] getCells() {
		return cells;
	}
	
	static Cell[][] getQuickBackup() {
		return quickBackup;
	}
	
	static void makeQuickBackup() {
		if (quickBackup == null) {
			quickBackup = new Cell[WIDTH][HEIGHT];
		}
		
		for (int i = 0; i != WIDTH; ++i) {
			for (int j = 0; j != HEIGHT; ++j) {
				quickBackup[i][j] = new Cell(cells[i][j]);
			}
		}
	}
	
	static void loadQuickBackup() {
		if (quickBackup != null) {
			for (int i = 0; i != WIDTH; ++i) {
				for (int j = 0; j != HEIGHT; ++j) {
					cells[i][j] = new Cell(quickBackup[i][j]);
				}
			}
		}
	}
	
}