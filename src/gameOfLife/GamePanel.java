package gameOfLife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

// Authors: Wikcio and MŒ
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 4802230154979709652L;
	
	// Defines the height and width of single cell
	private int boxSize = 20;
	
	// If true cells are colored according to their lifetime
	private boolean colorCells = false;
	
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
	
	public void setColorCells(boolean b) {
		colorCells = b;
	}
	
	GamePanel() {
		super();
		
		// Used by Key Listener
		this.setFocusable(true);
		this.requestFocus();		
		
		
		/*****************************************************************************************************************************************
		 ** 			LISTENERS																												**
		 *****************************************************************************************************************************************/
		
		
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				// Before zooming we move top-left corner to mouse position
				minorX += xMousePos;
				minorY += yMousePos;
				fixOffsets();
				
				// Now we can safely zoom
				int notches = e.getWheelRotation();
				// if cases for quicker zoom for bigger box sizes
				int newBoxSize = boxSize - notches;
				if (boxSize > 20) newBoxSize -= notches*4;
				if (boxSize > 45) newBoxSize -= notches*5;
				if (boxSize > 75) newBoxSize -= notches*5;
				boxSize = newBoxSize;
				if (boxSize < 3) boxSize = 3;
				if (boxSize > 100) boxSize = 100;
				
				
				
				// Finally restore the top-left corner to its positions
				minorX -= xMousePos;
				minorY -= yMousePos;
				fixOffsets();
				
				//GamePanel.this.repaint();
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				lastMousePosX = e.getX();
				lastMousePosY = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (StructuresDialog.getStatus()) {
					drawStructure(StructuresDialog.getValue());
				}
				else {
					Cell cell = getCellAtMousePosition();
					if (cell.getLifetime() != 0)
						cell.kill();
					else
						cell.revive();
				}
				
				//repaint();
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				xMousePos = e.getX();
				yMousePos = e.getY();
				//repaint();
			}
			
			/* TODO - an idea: to draw new cells 
			 * by quickfire while holding a particular keyboard key?? */
			@Override
			public void mouseDragged(MouseEvent e) {
				xMousePos = e.getX();
				yMousePos = e.getY();
				
				// Determine offset from mouse drag
				minorX -= xMousePos - lastMousePosX;
				minorY -= yMousePos - lastMousePosY;
				lastMousePosX = xMousePos;
				lastMousePosY = yMousePos;
				
				fixOffsets();				
				repaint();
			}
		});
	
	this.addKeyListener(new KeyListener() {
		@Override
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_R)
				Structure.nextDirection();
			else if (e.getKeyCode() == KeyEvent.VK_T)
				Structure.reverseStructure();
				//repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	});
	
}
	/*****************************************************************************************************************************************
	 ** 			DRAWING DEVICES																												**
	 *****************************************************************************************************************************************/
	
	
	
	
	/*
	 * Recalculates major offsets, used after moving or zooming
	 */
	private void fixOffsets() {
		while (minorX >= boxSize) {
			minorX -= boxSize;
			majorX += 1;
		}
		while (minorX < 0) {
			minorX += boxSize;
			majorX -= 1;
		}
		while (minorY >= boxSize) {
			minorY -= boxSize;
			majorY += 1;
		}
		while (minorY < 0) {
			minorY += boxSize;
			majorY -= 1;
		}
	}
	
	
	/*
	 * Based on the minor and major offsets returns cell that
	 * lies on the screen at screen coords x, y
	 */
	public Cell getCellAtPosition(int x, int y) {
		final int _x = (x + minorX) / boxSize + majorX;
		final int _y = (y + minorY) / boxSize + majorY;
		return CellsHolder.getCell(_x, _y);
	}
		
	/*
	 * Returns cell under mouse position
	 */
	public Cell getCellAtMousePosition() {
		return getCellAtPosition(xMousePos, yMousePos);
	}
	
	
	
	public void drawGrid(Graphics g, int n) {
		final int width = this.getWidth();
		final int height = this.getHeight();
		
		// Drawing Horizontal lines
		for (int i = (boxSize - minorY) - (majorY % n)*boxSize; i < height; i += n*boxSize) {
			g.drawLine(0, i+1, width, i+1);
		}
		
		// Drawing Vertical lines
		for (int i = (boxSize - minorX) - (majorX % n)*boxSize; i < width; i += n*boxSize) {
			g.drawLine(i+1, 0, i+1, height);
		}
	}
	
	
	// For highlighting the structure to be placed
	public void highlightStructure(Graphics g) {
		g.setColor(Color.gray);
		/* Possibly-helpful-in-future formula for upper left pixel 
		 * to start drawing a box that is below the mouse */
		final int xRect = (int)Math.floor((xMousePos - boxSize + minorX)/boxSize)*boxSize + boxSize - minorX;
		final int yRect = (int)Math.floor((yMousePos - boxSize + minorY)/boxSize)*boxSize + boxSize - minorY;
		for (int i = 0; i < Structure.getStructuresMap().get(StructuresDialog.getValue()).getVX().length; i++)
			drawCell (xRect + (Structure.getRotationCoordinate(Structure.getDirection(), i))*boxSize, 
						yRect + (Structure.getRotationCoordinate(Structure.getDirection() - 1, i))*boxSize, g);
	}
	
	
	
	
	// For making the highlighted cells alive 
	public void drawStructure (String value) {
		for (int i = 0; i < Structure.getStructuresMap().get(StructuresDialog.getValue()).getVY().length; i++)
			CellsHolder.getCell(getCellAtMousePosition().getX() + Structure.getRotationCoordinate(Structure.getDirection(), i), 
								getCellAtMousePosition().getY() + Structure.getRotationCoordinate(Structure.getDirection() - 1, i)).revive();
		
		Structure.resetDirection();
		StructuresDialog.setSelectionStatus(false);
	}
	
	/*
	 * Draws cell at positions x, y on the given object.
	 * Decides if the cell needs to be wider and higher by 4 pixels based on boxSize.
	 */
	private void drawCell(int x, int y, Graphics g) {
		//TODO - maybe would look nicer if space changed based on zoom
		g.fillRect(x + 1, y + 1, boxSize - 1, boxSize - 1);
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Here we decide on minor and major grids, there are 3 setups base on boxSize
		
		if (boxSize > 6) {
			g.setColor(new Color(0xeeeeee));
			drawGrid(g, 1);
			g.setColor(new Color(0x888888));
			drawGrid(g, 10);
			g.setColor(new Color(0x000000));
			drawGrid(g, 100);
		}
		else {
			g.setColor(new Color(0x888888));
			drawGrid(g, 10);
			g.setColor(new Color(0x000000));
			drawGrid(g, 100);
		}

		
		final int width = this.getWidth();
		final int height = this.getHeight();
		
		for (int i = 0; i < width + boxSize; i += boxSize) {
			for (int j = 0; j < height + boxSize; j += boxSize) {
				Cell cell = getCellAtPosition(i, j);
				if (cell.getLifetime() != 0) {
					if (colorCells) {
						g.setColor(cell.getColor());
					}
					else {
						g.setColor(Color.black);
					}
					drawCell(i - minorX, j - minorY, g);
				}
			}
		}

		if (StructuresDialog.getStatus())
			highlightStructure(g);
	}

}