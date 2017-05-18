package td7;

import java.util.Random;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;

public class Beings extends SimState {
	
	public static int GRID_SIZE;
	public static int NUM_A ;
	public static int NUM_B ;
	private Constants constants;
	
	public SparseGrid2D yard ;
	
	public Beings(long seed) {
		super(seed);
		constants = new Constants();
		NUM_A= constants.NUM_INSECT;
		NUM_B = constants.NUM_FOOD_CELL;
		GRID_SIZE = constants.GRID_SIZE;
		yard = new SparseGrid2D(GRID_SIZE,GRID_SIZE);
		
	}
	
	private Int2D getFreeLocation() {
		
		Int2D location = new Int2D(random.nextInt(yard.getWidth()),	random.nextInt(yard.getHeight()) );
		Object ag;
		while ((ag = yard.getObjectsAtLocation(location.x,location.y)) != null) {
			location = new Int2D(random.nextInt(yard.getWidth()),
			random.nextInt(yard.getHeight()) );
		}
		return location;
		}
	
	private void addAgentsA() {
		for(int i = 0; i < NUM_A; i++)
		{
			Insect a = new Insect(i);
			//Int2D location = getFreeLocation();
			
			Random rand = new Random();//random x
			int  x = rand.nextInt(constants.GRID_SIZE) ;
			int y = rand.nextInt(constants.GRID_SIZE) ;			
			
			yard.setObjectLocation(a,x,y);
			a.x = x;
			a.y = y;
			System.out.print("[fourmi "+i+"]["+x+", "+y +"]. Perception ("+a.getDISTANCE_PERCEPTION()+"). Déplacement ("+a.getDISTANCE_DEPLACEMENT()+"). Energie ("+a.getENERGIE()+"/"+constants.MAX_ENERGY+"). Reserve ("+a.getCHARGE_PORTEE()+"/"+a.getCHARGE_MAX()+").\n");
			schedule.scheduleRepeating(a);
			Stoppable stoppable = schedule.scheduleRepeating(a);
			a.stoppable = stoppable;
		}
	}
	
	private void addAgentsB() {
		for(int i = 0; i < NUM_B; i++)
		{
			FoodCell a = new FoodCell();
			Int2D location = getFreeLocation();
			yard.setObjectLocation(a,location.x,location.y);
			a.x = location.x;
			a.y = location.y;
			schedule.scheduleRepeating(a);
		}
	}
	
	public void start() {
		super.start(); 
		yard.clear();		
		addAgentsA();//insect
		addAgentsB();//food
	
	}
}