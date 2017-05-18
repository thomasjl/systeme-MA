package td7;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;

public class AgentType implements Steppable {
	public int x, y;
	@Override
	
	public void step(SimState state) {
		Beings beings = (Beings) state;
		if (friendsNum(beings) * 3 < 8) 
		{
			move(beings);
		}
		
	}
	protected int friendsNum(Beings beings) {
	
		return friendsNum(beings,x,y);
	}

	protected int friendsNum(Beings beings,int l,int c) {
		int nb = 0;
		for (int i = -1 ; i <= 1 ; i++) {
		for (int j = -1 ; j <= 1 ; j++) {
		if (i != 0 || j != 0) 
		{
			Int2D flocation = new Int2D(beings.yard.stx(l + i),
			beings.yard.sty(c + j));
			Object ag = beings.yard.getObjectsAtLocation(flocation.x,flocation.y);
			if (ag != null && ag.getClass() == this.getClass())
			nb++;
		}
		}
		}
		return nb;
	}
	
	public void move(Beings beings) {
		int n = beings.random.nextInt(8);
		switch(n) {
		case 0:
		//if (beings.free(x-1, y)) {
		if(beings.yard.numObjectsAtLocation(x-1, y)==0)
		{
		beings.yard.setObjectLocation(null,x, y);
		beings.yard.setObjectLocation(this,beings.yard.stx(x-1), y);
		x = beings.yard.stx(x-1);
		}
		break;
		
		}
	}
}