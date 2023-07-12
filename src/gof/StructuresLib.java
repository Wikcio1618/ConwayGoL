package gof;

public class StructuresLib {
	static Boolean[][] pond = new Boolean[4][4]; {
	for (int i = 0; i != 4; ++i) 
		for (int j = 0; j != 4; ++j)
			pond[i][j] = new Boolean (false);
	
		pond[0][1] = true;
		pond[0][2] = true;
		pond[1][0] = true;
		pond[2][0] = true;
		pond[3][1] = true;
		pond[3][2] = true;
		pond[1][3] = true;
		pond[2][3] = true;
	}
	
	static Boolean[][] glider = new Boolean[3][3]; 	{
		for (int i = 0; i != 3; ++i) 
			for (int j = 0; j != 3; ++j)
				pond[i][j] = new Boolean (false);
		
			glider[0][1] = true;
			glider[1][2] = true;
			glider[2][0] = true;
			glider[2][1] = true;
			glider[2][2] = true;
		}
	
	static Boolean[][] Dakota = new Boolean[4][5]; {
		for (int i = 0; i != 4; ++i) 
			for (int j = 0; j != 5; ++j)
				pond[i][j] = new Boolean (false);
		
			glider[0][1] = true;
			glider[0][4] = true;
			glider[1][0] = true;
			glider[2][0] = true;
			glider[2][4] = true;
			glider[3][0] = true;
			glider[3][1] = true;
			glider[3][2] = true;                                            
			glider[3][3] = true;
		}
	
	
}