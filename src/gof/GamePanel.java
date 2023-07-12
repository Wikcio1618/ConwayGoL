package gof;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	//private static final long serialVersionUID = 4802230154979709652L;
	private GameOfLife game;
	
	private int boxSize = 16;
	
	// Offset for drawing the cells
	private int majorX = 0;
	private int majorY = 0;
	private int minorX = 0;
	private int minorY = 0;
	
	// For moving purpose
	private int lastMousePosX = 0;
	private int lastMousePosY = 0;
	
	// Current mouse position
	private int xMousePos = 0;
	private int yMousePos = 0;
	
	GamePanel(GameOfLife g) {
		super();
		game = g;
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				lastMousePosX = e.getX();
				lastMousePosY = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (StructuresDialog.getStatus()) 
					drawStructure(StructuresDialog.getValue());	
				// At the moment doesn't meet the usefulness' expectations. changes the state of a cell when clicked upon
				else if (!StructuresDialog.getStatus() && 
						!CellsHolder.getCell((int)Math.floor((xMousePos - minorX)/boxSize), (int)Math.floor((yMousePos - minorY)/boxSize)).isAlive()) {
					CellsHolder.getCell((int)Math.floor((xMousePos - minorX)/boxSize), (int)Math.floor((yMousePos - minorY)/boxSize)).setAlive(true);
				}
				else 
					CellsHolder.getCell((int)Math.floor((xMousePos - minorX)/boxSize), (int)Math.floor((yMousePos - minorY)/boxSize)).setAlive(false);
				repaint();
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				xMousePos = e.getX();
				yMousePos = e.getY();
				repaint();
			}
			
			/* TODO - an idea: to draw new cells 
			 * by a quickfire when holding a particular keyboard key?? */
			@Override
			public void mouseDragged(MouseEvent e) {
				 minorX += e.getX() - lastMousePosX;
				 minorY += e.getY() - lastMousePosY;
				 lastMousePosX = e.getX();
				 lastMousePosY = e.getY();
					xMousePos = e.getX();
					yMousePos = e.getY();
				 repaint();
			}
		});
	}
	
	public void drawGrid(Graphics g) {
		final int width = this.getWidth();
		final int height = this.getHeight();
		
		// Drawing Horizontal lines
		for (int i = minorY; i < width; i += boxSize) {
			g.fillRect(0, i-1, width, 2);
		}
		
		// Drawing Vertical lines
		for (int i = minorX; i < width; i += boxSize) {
			g.fillRect(i-1, 0, 2, height);
		}
	}
	// For highlighting the structure to be placed
	public void highlightStructure(Graphics g) {
		g.setColor(Color.gray);
		/* Possibly-helpful-in-future formula for upper left pixel 
		 * to start drawing a box that is below the mouse */
		final int xRect = (int)Math.floor((xMousePos - (int)minorX%boxSize - boxSize)/boxSize)*boxSize + (int)minorX%boxSize + boxSize;
		final int yRect = (int)Math.floor((yMousePos - (int)minorY%boxSize - boxSize)/boxSize)*boxSize + (int)minorY%boxSize + boxSize;
		
		switch (StructuresDialog.getValue()) {
		case "Pond":
			for (int i = 0; i < Structure.pond.getVY().length; i++)
				g.fillRect(xRect + (Structure.pond.getVX()[i] - 1)*boxSize + 2, yRect + (Structure.pond.getVY()[i] - 1)*boxSize + 2, boxSize - 4, boxSize - 4);
			break;
		case "Glider":
			for (int i = 0; i < Structure.glider.getVY().length; i++)
				g.fillRect(xRect + (Structure.glider.getVX()[i] - 2)*boxSize + 2, yRect + (Structure.glider.getVY()[i] - 2)*boxSize + 2, boxSize - 4, boxSize - 4);
			break;
		case "Glider Spawner":
			//-14 and -2 shifts the mouse in relation to the displayed structure by vector [14,2]. Elsewhere likewise.
			for (int i = 0; i < Structure.gliderSpawner.getVY().length; i++)
				g.fillRect(xRect + (Structure.gliderSpawner.getVX()[i] - 14)*boxSize + 2, yRect + (Structure.gliderSpawner.getVY()[i] - 2)*boxSize + 2, boxSize - 4, boxSize - 4);
			break;
		}
	}
	// For making the highlighted cells alive 
	public void drawStructure (String value) {
		// Possibly-helpful-in-future formula for the box that is below the mouse
		final int xBase = (int)Math.floor((xMousePos - minorX)/boxSize);
		final int yBase = (int)Math.floor((yMousePos - minorY)/boxSize);
		switch (value) {
		case "Pond":
			for (int i = 0; i < Structure.pond.getVY().length; i++)
				CellsHolder.getCell(xBase + Structure.pond.getVX()[i] - 1, yBase + Structure.pond.getVY()[i] - 1).setAlive(true);
			break;
		case "Glider":
			for (int i = 0; i < Structure.glider.getVY().length; i++)
				CellsHolder.getCell(xBase + Structure.glider.getVX()[i] - 2, yBase + Structure.glider.getVY()[i] - 2).setAlive(true);
			break;	
		case "Glider Spawner":
			for (int i = 0; i < Structure.gliderSpawner.getVY().length; i++)
				CellsHolder.getCell(xBase + Structure.gliderSpawner.getVX()[i] - 14, yBase + Structure.gliderSpawner.getVY()[i] - 2).setAlive(true);
			break;
		}
		StructuresDialog.setSelectionStatus(false);
	}
	
	// Returns a color based on black/colorful game mode
	public Color decideColor (int j, int i) {

		Color color = Color.white;
		if (game.getGameMgr().getColorfulCells() && CellsHolder.getCell(j, i).getLifetime() == 1)
			color = Color.blue;
		else if (game.getGameMgr().getColorfulCells() && CellsHolder.getCell(j, i).getLifetime() == 2)
			color = Color.orange;
		else if (game.getGameMgr().getColorfulCells() && CellsHolder.getCell(j, i).getLifetime() == 3)		
			color = Color.red;
		else if (game.getGameMgr().getColorfulCells() && CellsHolder.getCell(j, i).getLifetime() >= 4)
			color = Color.green;
		else if (!game.getGameMgr().getColorfulCells())
			color = Color.black;
		
		return color;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.lightGray);
		drawGrid(g);	
		
		final int width = this.getWidth() / boxSize + 1;
		final int height = this.getHeight() / boxSize + 1;
		
		for (int i = majorY; i < height; ++i) {
			for (int j = majorX; j < width; ++j) {
				if (CellsHolder.getCell(j, i).isAlive()) {
					g.setColor(decideColor (j, i));
					g.fillRect((j - majorX)*boxSize + 2 + minorX, (i - majorY)*boxSize + 2 + minorY, boxSize-4, boxSize-4);	
					
				}
			}
		}
		if (StructuresDialog.getStatus())
			highlightStructure(g);
	}

}