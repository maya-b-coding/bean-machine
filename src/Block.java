public class Block {
	
	public int direction = 0; //0 = neutral, 1 = up, 2 down, 3 left, 4 right
	public int type = 0; //empty, pin, ball, collector
	public boolean hasUpdated = false;
	public int ballCount = 0; // used only by collector type
	
	Block(int x, int y)
	{	
		if (y >= 0 && y < 6) 
			type = y;
		
		if (x >= 0 && x < 5 && type == 2) //prevents non-accepted directions from being input
			direction = x;
		
	}
	
	Block()
	{
		direction = 0;
		type = 0;
	}
	
	void setDir(int newDir)
	{
		direction = newDir;
	}
	
	void setType(int newType)
	{
		type = newType;
		if (type != 2)
			direction = 4;
	}
	
	void updateStatus(boolean newStatus)
	{
		hasUpdated = newStatus;
	}
	
	void incrementBallCount()
	{
		ballCount++;
	}
}
