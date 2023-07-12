package gameOfLife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class GameManager {
	
	private GameOfLife game;
	
	GameManager(GameOfLife g) {
		game = g;
	}
	
	public void saveToFile(File file) {
		System.out.println("Saving to file: " + file.getName());

		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
		    for (int y = 0; y < CellsHolder.HEIGHT; y++)
		    	for (int x = 0; x < CellsHolder.WIDTH; x++) {
		    		writer.write(Integer.toString(CellsHolder.getCells()[x][y].getLifetime()));
		    		writer.newLine();
		    	}
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	public void loadFromFile(File file) {
		System.out.println("Loading from file: " + file.getName());

		try (BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"))) {
		    CellsHolder.clearAll();
		    String lineRead = reader.readLine();
		    for (int y = 0; y < CellsHolder.HEIGHT; y++)
		    	for (int x = 0; x < CellsHolder.WIDTH; x++) 	
		    		if (lineRead != null) {
		    			CellsHolder.getCells()[x][y].setLifetime(Integer.parseInt(lineRead)); 
		    			lineRead = reader.readLine();
		    	}
		   game.getGuiMgr().getGamePanel().repaint();   
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	/*
	 * Makes quick backup/quicksave of current cells state
	 */
	public void makeQuickBackup() {
		System.out.println("Making quick backup");		
		CellsHolder.makeQuickBackup();
	}

	public void loadQuickBackup() {
		System.out.println("Loading quick backup");
		CellsHolder.loadQuickBackup();
		game.getGuiMgr().getGamePanel().repaint();
	}

	public void addStructure(String string) {
		System.out.println("Adding structure: " + string);
	}
}