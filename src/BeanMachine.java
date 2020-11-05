import processing.core.PApplet;

public class BeanMachine extends PApplet {
	int gridHeight = 30; // odd numbers preferable
	int gridWidth = 20;
	int gridType = 0;
	int blockSize = 20;

	int frameCounter;

	Grid grid;
	boolean pause;

	public static void main(String[] args) {
		PApplet.main("BeanMachine");
	}

	public void keyPressed() {
		if (key == ' ') {
			pause = !pause;
		}
	}

	public void settings() {
		pause = false;
		
		grid = new Grid(gridHeight, gridWidth, gridType);
		grid.initPachinko();

		int windowWidth = gridWidth * blockSize;
		int windowHeight = gridHeight * blockSize;
		
		size(windowWidth, windowHeight);

		frameCounter = 0;
	}

	public void draw(){
		if(frameCounter <= 0) {
			if(!pause)
				grid.update();
			frameCounter=10;
		}

		frameCounter--;
		
		if (mousePressed) {
			int x = mouseX / blockSize;
			int y = mouseY / blockSize;
			int windowWidth = gridWidth * blockSize;	
			int windowHeight = gridHeight * blockSize;

			if (mouseX > 0 && mouseX < windowWidth && mouseY > 0 && mouseY < windowHeight)
			{
				if (!pause)
					grid.dropBall(x, y);
			}
		}

		for(int i = 0; i < grid.height; i++){
			for(int j = 0; j < grid.width; j++){
				int x = blockSize * j; //accounts for fact that height and width are opposite in Grid class
				int y = blockSize * i;

				if(grid.isEmpty(i, j))
				{
					fill(255, 255, 255);
					stroke(255, 255, 255);
				} 
				else if (grid.isPin(i, j))
				{
					fill(0, 0, 0);
					stroke(0, 0, 0);
				} 
				else if (grid.isBall(i, j))  // teal
				{
					fill(0, 255, 130);
					stroke(0, 255, 130);
				}
				else if (grid.isCollector(i, j)) // pink
				{
					fill(255, grid.inverseBallCount(j, i), 255);
					stroke(255, grid.inverseBallCount(j, i), 255);
				}
						
				rect(x, y, blockSize, blockSize);
			}
		}

	}
}