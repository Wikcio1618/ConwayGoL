package gof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameManager {
	
	private GameOfLife game;
	
	private boolean running;
	private int gameSpeed;
	private boolean colorfulCells = false;
	
	GameManager(GameOfLife g) {
		game = g;
		running = false;
		gameSpeed = 2;
	}
	
	void setRunning(boolean b) {
		running = b;
	}
	
	void setGameSpeed(int speed) {
		gameSpeed = speed;
	}
	
	public void setColorfulCells(boolean b) {
		colorfulCells = b;
	}
	
	public boolean getColorfulCells () {
		return colorfulCells;
	}

	public void saveToFile(File file) {
		System.out.println("Saving to file: " + file.getName());
		
		Charset charset = Charset.forName("US-ASCII");
		Path myPath = file.toPath();
		try (BufferedWriter writer = Files.newBufferedWriter(myPath, charset)) {
		    boolean byteWriteBoolean = false;
		    int byteWrite = 111; 	//meaningless number
		    for (int y = 0; y < CellsHolder.HEIGHT; y++)
		    	for (int x = 0; x < CellsHolder.WIDTH; x++) {
		    		
		    		byteWriteBoolean = CellsHolder.getCells()[x][y].isAlive();
		    		if (byteWriteBoolean == false) {
		    			byteWrite = 48;
		   			}
		   			else if (byteWriteBoolean == true) {
		   				byteWrite = 49;
		   			}
		    		
		    		writer.write(byteWrite);
		    		writer.write(CellsHolder.getCells()[x][y].getLifetime() + 48);
		    	}
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	public void loadFromFile(File file) {
		System.out.println("Loading from file: " + file.getName());
		
		Charset charset = Charset.forName("US-ASCII");
		Path myPath = file.toPath();
		try (BufferedReader reader = Files.newBufferedReader(myPath, charset)) {
		    int byteRead=111; 	//meaningless number
		    boolean byteReadBoolean = false;
		    CellsHolder.clearAll();
		    
		    for (int y = 0; y < CellsHolder.HEIGHT; y++)
		    	for (int x = 0; x < CellsHolder.WIDTH; x++) {
		    		if (byteRead != -1) {
		    			byteRead = reader.read();
		    			if (byteRead - 48 == 0) {
		    				byteReadBoolean = false;
		    			}
		    			else if (byteRead - 48 == 1) {
		    				byteReadBoolean = true;
		    			}
		    			CellsHolder.getCells()[x][y].setAlive(byteReadBoolean);
		    			CellsHolder.getCells()[x][y].setLifetime(reader.read() - 48);
		    		}
		    		else
		    			break;
		    	} 
		    
		   game.getGuiMgr().getGamePanel().repaint();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	public void makeQuickBackup() {
		System.out.println("Making quick backup");
		if (CellsHolder.getQuickBackup() == null) {
			CellsHolder.initQuickBackup();
			game.getGuiMgr().getMenuItem()[3].setEnabled(true);
		}
		
		for (int y = 0; y < CellsHolder.HEIGHT; y++)
	    	for (int x = 0; x < CellsHolder.WIDTH; x++) {
	    		CellsHolder.getQuickBackup()[x][y].setAlive(CellsHolder.getCells()[x][y].isAlive());
	    		CellsHolder.getQuickBackup()[x][y].setLifetime(CellsHolder.getCells()[x][y].getLifetime());
	    	}
	}

	public void loadQuickBackup() {
		System.out.println("Loading quick backup");
			CellsHolder.clearAll();
			for (int y = 0; y < CellsHolder.HEIGHT; y++)
				for (int x = 0; x < CellsHolder.WIDTH; x++) {
					CellsHolder.getCells()[x][y].setAlive(CellsHolder.getQuickBackup()[x][y].isAlive());
					CellsHolder.getCells()[x][y].setLifetime(CellsHolder.getQuickBackup()[x][y].getLifetime());
				}
			game.getGuiMgr().getGamePanel().repaint();
	}

	public void addStructure(String string) {
		System.out.println("Adding structure: " + string);
	}
}