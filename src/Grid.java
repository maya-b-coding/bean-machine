import java.util.Random;

public class Grid {
	
	int height = 3;
	int width = 2;
	int type = 1;
	Block[][] blockArray;
	
	Random rand = new Random();
	
	Grid(int x, int y, int t)
	{
		if(x >= 3) //height must be at least 3 blocks to account for drop slots.
			height = x;
		if(y >= 2)
			width = y;
		type = t;
		
		blockArray = new Block[height][width];
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				blockArray[i][j] = new Block();
			}
		}
	}
	
	void initEmpty()
	{
		for (int i = 0; i < height - 1; i++)
		{
			for (int j = 0; j < width; j++)
			{
				blockArray[i][j].setType(0);
			}
		}
		
		for (int j = 0; j < width; j++) //Fills last row with collectors
		{
			blockArray[height - 1][j].setType(3);
		}
	}
	
	void initPachinko()
	{	
		for (int j = 0; j < width; j++)
		{
			blockArray[0][j].setType(0); //set as drop slots
		}
		
		for (int j = 0; j < width; j++)
		{
			blockArray[1][j].setType(0); //set as locked empty slots
		}
		
		for (int i = 2; i < height - 1; i++)
		{
			
			if(i % 4 == 2) //alternates between filled and empty blocks for first row
			{
				int newType = 0;
				for (int j = 0; j < width; j++) 
				{
					blockArray[i][j].setType(newType);
					if (newType == 0)
						newType = 1;
					else
						newType = 0;
				}
			}
			
			if (i % 4 == 1 || i % 4 == 3) //fills row with empty space
			{
				for(int j = 0; j < width; j++)
				{
					blockArray[i][j].setType(0);
				}
			}
			
			if (i % 4 == 0) //alternates between filled and empty blocks for first row (starts with filled this time)
			{
				int newType = 1;
				for (int j = 0; j < width; j++) 
				{
					blockArray[i][j].setType(newType);
					if (newType == 0)
						newType = 1;
					else
						newType = 0;
				}
			}
		}	
		
			for (int j = 0; j < width; j++) //Fills last row with collectors
			{
				blockArray[height - 1][j].setType(3);
			}
	}
	
	void dropBall(int j, int i)
	{
		if (blockArray[i][j].type == 0)
		{
			blockArray[i][j].setType(2);
			blockArray[i][j].setDir(2);
		}
	}
	
	int inverseBallCount(int j, int i)
	{
		int curValue = 245 - (blockArray[i][j].ballCount * 2); //multiplier allows it to change color faster
		if (curValue < 0)
			curValue = 0;
		return curValue;
	}
	
	void update()
	{
		for (int i = height - 1; i >= 0; i--)
		{
			for (int j = 0; j < width; j++)
			{
				if(blockArray[i][j].hasUpdated == true)
					continue;
				if(blockArray[i][j].type == 2) //if a ball is present
				{
					ballDown(i, j);
				}
			}
		}
		for (int i = height - 1; i >= 0; i--)
			for (int j = 0; j < width; j++)
			{
				blockArray[i][j].updateStatus(false);
			}
	}
	
	void ballDown(int i, int j)
	{
		if (i >= height)
			return;
		if (blockArray[i + 1][j].type == 0) // if block below is empty, drop down one block
		{
			blockArray[i + 1][j].setType(2);
			blockArray[i + 1][j].setDir(2);
			blockArray[i][j].setType(0);
			blockArray[i][j].setDir(0);
		}
		else if (blockArray[i + 1][j].type == 3)
		{
			blockArray[i + 1][j].incrementBallCount();
			blockArray[i][j].setType(0);
			blockArray[i][j].setDir(0);
		}
		else  //if a block is present, 50% chance go left 50% chance go right
		{
			if (blockArray[i][j].direction != 3 || blockArray[i][j].direction != 4)
				blockArray[i][j].setDir(randomLeftOrRight());
			
			if (blockArray[i][j].direction == 3)
			{
				ballLeft(i, j);
			} else
			{
				ballRight(i, j);
			}
		}
	}
	
	void ballLeft(int i, int j)
	{	
		if(j > 0 && blockArray[i][j - 1].type == 0) // move left if possible
		{
			blockArray[i][j - 1].setType(2);
			blockArray[i][j - 1].setDir(4);
			blockArray[i][j].setType(0);
			blockArray[i][j].setDir(0);
		} else if(j < width - 1) //move right if left is not possible
		{
			if (blockArray[i][j + 1].type == 0)
			{
				blockArray[i][j + 1].setType(2);
				blockArray[i][j + 1].setDir(4);
				blockArray[i][j].setType(0);
				blockArray[i][j].setDir(0);
				blockArray[i][j + 1].updateStatus(true);
			}
		}
		else 
		{
			blockArray[i][j].setType(0); // vanish
		}
		
	}
	
	void ballRight(int i, int j)
	{
		if(j < width - 1 && blockArray[i][j + 1].type == 0) {
			blockArray[i][j + 1].setType(2);
			blockArray[i][j + 1].setDir(4);
			blockArray[i][j].setType(0);
			blockArray[i][j].setDir(0);
			blockArray[i][j + 1].updateStatus(true);
		} else if(j > 0 && blockArray[i][j - 1].type == 0)
		{
			blockArray[i][j - 1].setType(2);
			blockArray[i][j - 1].setDir(4);
			blockArray[i][j].setType(0);
			blockArray[i][j].setDir(0);
		}
		else 
		{
			blockArray[i][j].setType(0);
		}
	}
	
	boolean isEmpty(int i, int j)
	{
		if (blockArray[i][j].type == 0 || blockArray[i][j].type == 4 || blockArray[i][j].type == 5)
			return true;
		else return false;
	}
	
	boolean isPin(int i, int j)
	{
		if (blockArray[i][j].type == 1)
			return true;
		else return false;
	}
	
	boolean isBall(int i, int j)
	{
		if (blockArray[i][j].type == 2)
			return true;
		else return false;
	}
	
	boolean isCollector(int i, int j)
	{
		if (blockArray[i][j].type == 3)
			return true;
		else return false;
	}

	
	int randomLeftOrRight()
	{
		int n = rand.nextInt(2);
		return n + 3;
	}
}
