package gameOfLife;

import java.util.HashMap;
import java.util.Map;

public class Structure {
	
	// Currently not used dimensions that a structure fits in
	private int Width; 
	private int Height;
	// X and Y coordinates of a vector attached to the upper left cell pointing to a cell that should be alive
	private int VectorY[];
	private int VectorX[];
	
	// For rotation purposes
	// Zero is in some way north direction and the directions go clockwise. But see: getRotationCoordinate
	private static int direction = 0;
	private static boolean reverse = false;
	
	// Currently the only constructor
	public Structure (int Width, int Height, int VectorY[], int VectorX[]) {
		this.Height = Height;
		this.Width = Width;
		
		this.VectorY = new int[VectorY.length];
		for (int i = 0; i < VectorY.length; i++) 
			this.VectorY[i] = VectorY[i];
		
		this.VectorX = new int[VectorX.length];
		for (int i = 0; i < VectorX.length; i++) 
			this.VectorX[i] = VectorX[i];
	}
	
	public int getH () {
		return Height;
	}
	
	public int getW () {
		return Width;
	}
	
	public int[] getVX() {
		return VectorX;
	}
	
	public int[] getVY() {
		return VectorY;
	}
	
	/* After making separate switches for X and Y coordinate 
	 * one realizes that both X and Y coordinates of the drawing vector are found in here. 
	 * Y coordinates are trailing by 1 the X coordinates 
	 * See: highlight and drawStructure at GamePanel Class*/ 
	public static int getRotationCoordinate(int dir, int i) {
		switch ((4+dir)%4) // In java % operator returns negative numbers
		{
		case 0:
			if (!reverse)
				return +structuresMap.get(StructuresDialog.getValue()).getVX()[i];
			else
				return -structuresMap.get(StructuresDialog.getValue()).getVX()[i];
		case 1:
				return -structuresMap.get(StructuresDialog.getValue()).getVY()[i];
		case 2:
			if (!reverse)
				return -structuresMap.get(StructuresDialog.getValue()).getVX()[i];
			else
				return +structuresMap.get(StructuresDialog.getValue()).getVX()[i];
		case 3:
				return +structuresMap.get(StructuresDialog.getValue()).getVY()[i];
		default: 
				return 0;
		}
	}
	
	public static int getDirection() {
		return direction;
	}

	public static void resetDirection() {
		direction = 0;
		reverse = false;
	}

	public static void nextDirection () {
		direction++;
		direction %= 4;
	}

	public static void reverseStructure() {
		reverse = !reverse;
	}
	
	
	
	
	/*********************************************************
	 *	////////////||	STRUCTURES LIBRARY   || \\\\\\\\\\\\ *
	 *********************************************************/
	
	
	public static Map<String, Structure> getStructuresMap () {
		return structuresMap;
	}
	
	// vvvv If you add new structure make sure you added new position to the list in StructuresDialog vvv
	static private Map<String, Structure> structuresMap;
	static {
		structuresMap = new HashMap<>();
		
		//Oscillators
		structuresMap.put ("Pond", new Structure (4, 4, 
				new int[]{-1,-1,0,0,1,1,2,2},
				new int[]{0,1,-1,2,-1,2,0,1}));
		structuresMap.put ("Pulsar", new Structure (13, 13, 
				new int[]{6,6,6,4,4,3,3,2,2,1,1,1,-6,-6,-6,-4,-4,-3,-3,-2,-2,-1,-1,-1,-6,-6,-6,-4,-4,-3,-3,-2,-2,-1,-1,-1,6,6,6,4,4,3,3,2,2,1,1,1},
				new int[]{2,3,4,1,6,1,6,1,6,2,3,4,-2,-3,-4,-1,-6,-1,-6,-1,-6,-2,-3,-4,2,3,4,1,6,1,6,1,6,2,3,4,-2,-3,-4,-1,-6,-1,-6,-1,-6,-2,-3,-4}));
		structuresMap.put ("I-Column", new Structure (16, 3, 
				new int[]{-7,-6,-5,-5,-5,-2,-2,-2,-1,0,1,2,3,3,3,6,6,6,7,8},
				new int[]{0,0,-1,0,1,-1,0,1,0,0,0,0,-1,0,1,-1,0,1,0,0}));
		structuresMap.put ("Gabriel's p138", new Structure (15, 15, 
				new int[]{-7,-7,-7,-6,-6,-5,-5,-4,-4,-4,-4,-3,-3,-2,-2,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,2,2,2,2,3,3,4,4,4,4,5,5,6,6,7,7,7},
				new int[]{0,1,2,-1,2,0,4,-5,1,2,3,-4,2,-7,-6,-4,-3,-7,-4,6,-7,-5,5,7,-6,4,7,3,4,6,7,-2,4,-3,-2,-1,5,-4,0,-2,1,-2,-1,0}));
		
		//Long living
		structuresMap.put ("The R-pentomino", new Structure (3, 3,
				new int[] {-1,-1,0,0,1},
				new int[] {0,1,-1,0,0}));
		structuresMap.put ("Diehard", new Structure (3, 8,
				new int[] {-2,-1,-1,0,0,0,0},
				new int[] {1,-5,-4,-4,0,1,2}));
		structuresMap.put ("Acorn", new Structure (3, 7,
				new int[] {-1,0,1,1,1,1,1},
				new int[] {-2,0,-3,-2,1,2,3}));
		
		//Spaceships
		structuresMap.put ("Glider", new Structure (3, 3, 
				new int[] {-2,-1,0,0,0}, 
				new int[] {-1,0,-2,-1,0}));
		structuresMap.put("Light-weight spaceship", new Structure (4, 5,
				new int[] {-2,-2,-1,0,0,1,1,1,1},
				new int[] {-3,0,1,-3,1,-2,-1,0,1}));
		structuresMap.put("Middle-weight spaceship", new Structure (5, 6,
				new int[] {-4,-3,-3,-2,-1,-1,0,0,0,0,0}, 
				new int[] {0,-2,2,3,-2,3,-1,0,1,2,3}));
		structuresMap.put("Heavy-weight spaceship", new Structure (5, 7,
				new int[] {-3,-3,-2,-2,-1,0,0,1,1,1,1,1,1}, 
				new int[] {0,1,-2,3,4,-2,4,-1,0,1,2,3,4}));
		structuresMap.put("Glider synthesis", new Structure (79, 149,
				new int[] {-1,-1,0,0,1,18,18,19,19,20,-12,-12,-11,-11,-11,-10,-10,-10,-10,-10,-7,-7,-7,-6,-5,-1,0,0,1,1,-57,-56,-56,-55,-55,-49,-48,-48,-47,-47,-42,-41,-41,-40,-40,-36,-35,-35,-34,-34,-58,-58,-57,-57,-56,-40,-39,-39,-38,-38,38,38,38,38,38,39,39,39,40,40,47,47,48,48,49,16,17,17,18,18,47,47,48,48,49,54,54,55,55,56,-26,-26,-25,-25,-24,9,10,10,11,11,10,10,10,11,12},
				new int[] {-1,1,-1,0,0,-25,-24,-26,-24,-24,-29,-22,-28,-24,-23,-30,-29,-28,-23,-22,-33,-32,-31,-31,-32,-21,-23,-21,-22,-21,6,7,8,6,7,40,38,40,39,40,24,22,24,23,24,19,20,21,19,20,106,108,106,107,107,107,107,109,107,108,103,104,114,115,116,102,103,114,104,115,107,108,107,109,107,39,39,40,38,40,22,23,23,24,22,2,3,3,4,2,-79,-77,-78,-77,-78,-75,-75,-74,-76,-74,-38,-37,-36,-38,-37}));
		//Guns
		structuresMap.put ("Simkin glider gun", new Structure (21, 33, 
				new int[] {-10,-10,-10,-10,-9,-9,-9,-9,-7,-7,-6,-6,-1,-1,-1,-1,0,0,1,1,1,1,2,2,2,2,2,2,3,7,7,8,9,9,9,10}, 
				new int[] {-21,-20,-14,-13,-21,-20,-14,-13,-17,-16,-17,-16,1,2,4,5,0,6,0,7,10,11,0,1,2,6,10,11,5,-1,0,-1,0,1,2,2}));
		structuresMap.put ("Gosper glider gun", new Structure (9, 36, 
				new int[] {-2,-1,-1,0,0,0,0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3,3,3,4,4,4,5,5,6,6}, 
				new int[] {10,8,10,-2,-1,6,7,20,21,-3,1,6,7,20,21,-14,-13,-4,2,6,7,-14,-13,-4,0,2,3,8,10,-4,2,10,-3,1,-2,-1}));
	}
	
}