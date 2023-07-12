package gof;

public class Structure {
	
	// Currently not used dimensions that a structure fits in
	private int Width; 
	private int Height;
	// X and Y coordinates of a vector attached to the upper left cell pointing to a cell that should be alive
	private int VectorY[];
	private int VectorX[];
	
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
	
	// Structures Library that can and should be enlarged
	static Structure pond = new Structure (4, 4, new int[]{0,0,1,1,2,2,3,3}, new int[]{1,2,0,3,0,3,1,2});
	
	static Structure glider = new Structure (3, 3, new int[] {0,1,2,2,2}, new int[] {1,2,0,1,2});
	
	static Structure gliderSpawner = 
			new Structure (9, 36, 
			new int[] {0,1,1,2,2,2,2,2,2,3,3,3,3,3,3,4,4,4,4,4,4,5,5,5,5,5,5,5,5,6,6,6,7,7,8,8,}, 
			new int[] {24,22,24,12,13,20,21,34,35,11,15,20,21,34,35,0,1,10,16,20,21,0,1,10,14,16,17,22,24,10,16,24,11,15,12,13});
}
