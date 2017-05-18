package td7;

import sim.engine.SimState;

public class FoodCell implements sim.engine.Steppable{

	public int x;
	public int y;
	
	private int FOOD_AVAILABLE;
	
	public FoodCell()
	{
		Constants c = new Constants();
		FOOD_AVAILABLE = c.MAX_FOOD;
	}
	
		
	public void consumeFood()
	{
		FOOD_AVAILABLE--;
	}
	
	@Override
	public void step(SimState arg0) {
		
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getFOOD_AVAILABLE() {
		return FOOD_AVAILABLE;
	}

	public void setFOOD_AVAILABLE(int fOOD_AVAILABLE) {
		FOOD_AVAILABLE = fOOD_AVAILABLE;
	}

}
