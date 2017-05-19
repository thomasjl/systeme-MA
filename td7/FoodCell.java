package td7;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;

public class FoodCell implements sim.engine.Steppable{

	public int x;
	public int y;
	
	private int idFood;	
	private int FOOD_AVAILABLE;
	public Stoppable stoppable;
	
	
	public FoodCell(int i)
	{
		Constants c = new Constants();
		FOOD_AVAILABLE = c.MAX_FOOD;
		idFood=i;
	}
	
			
	public void consumeFood(SimState state)
	{
		Beings beings = (Beings) state;
		FOOD_AVAILABLE--;
		if(FOOD_AVAILABLE==0)
		{
			//le point de nourriture est epuisé. suppression de ce point 
			beings.yard.remove(this); //model est le SimState
			stoppable.stop();
			System.out.print("[food "+idFood+"]["+x+", "+y+"] Point de nourriture epuisé\n");
			
			//apparation d'un nouveau point de nourriture aléatoirement. (a faire)
			beings.currentNbFood++;
			FoodCell a = new FoodCell(beings.currentNbFood);
			Int2D location = beings.getFreeLocation();
			beings.yard.setObjectLocation(a, location);
			a.x = location.x;
			a.y = location.y;
			Stoppable stoppable= beings.schedule.scheduleRepeating(a);
			a.stoppable = stoppable;
					
		}
	}
	
	@Override
	public void step(SimState state) 
	{
			
		
		
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
