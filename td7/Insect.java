package td7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Int2D;

public class Insect implements sim.engine.Steppable,Stoppable{
	
	public int x;
	public int y;
	
	private int DISTANCE_DEPLACEMENT;
	private int DISTANCE_PERCEPTION;
	private int CHARGE_MAX;
	private int ENERGIE;
	private int CHARGE_PORTEE;
	private Constants constants;
	private int idFourmi;
	private int MAX_ENERGIE;
	
	public Stoppable stoppable;
	
	
	public Insect(int id)
	{
		Random rand = new Random();
		
		int deplacement=1;
		int perception=1;
		int chargeMax=1;
		
		int temp;
		constants = new Constants();
		for (int i=0;i<constants.CAPACITY;i++)
		{
			temp = rand.nextInt(3) + 1;
			if(temp==1)
			{
				deplacement++;
			}
			else if(temp==2)
			{
				perception++;
			}
			else if(temp==3)
			{
				chargeMax++;
			}
		}
		
		DISTANCE_DEPLACEMENT = deplacement;
		DISTANCE_PERCEPTION = perception;
		CHARGE_MAX = chargeMax;	
		ENERGIE = constants.MAX_ENERGY;
		MAX_ENERGIE=constants.MAX_ENERGY;
		CHARGE_PORTEE = Math.round(chargeMax/2);
		idFourmi=id;
		
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



	public int getDISTANCE_DEPLACEMENT() {
		return DISTANCE_DEPLACEMENT;
	}



	public void setDISTANCE_DEPLACEMENT(int dISTANCE_DEPLACEMENT) {
		DISTANCE_DEPLACEMENT = dISTANCE_DEPLACEMENT;
	}



	public int getDISTANCE_PERCEPTION() {
		return DISTANCE_PERCEPTION;
	}



	public void setDISTANCE_PERCEPTION(int dISTANCE_PERCEPTION) {
		DISTANCE_PERCEPTION = dISTANCE_PERCEPTION;
	}



	public int getCHARGE_MAX() {
		return CHARGE_MAX;
	}



	public void setCHARGE_MAX(int cHARGE_MAX) {
		CHARGE_MAX = cHARGE_MAX;
	}



	public int getENERGIE() {
		return ENERGIE;
	}



	public void setENERGIE(int eNERGIE) {
		ENERGIE = eNERGIE;
	}



	public int getCHARGE_PORTEE() {
		return CHARGE_PORTEE;
	}



	public void setCHARGE_PORTEE(int cHARGE_PORTEE) {
		CHARGE_PORTEE = cHARGE_PORTEE;
	}

	protected List<FoodCell> aroundFoodList(Beings beings,int l,int c, Boolean includeMyCell) {
		
		List<FoodCell> listOfFood = new ArrayList<>();
		for (int i = -1 ; i <= 1 ; i++) 
		{
			for (int j = -1 ; j <= 1 ; j++)
			{
				if(i==j && includeMyCell)
				{
					Int2D flocation = new Int2D(beings.yard.stx(l + i),
					beings.yard.sty(c + j));
					Bag ag = beings.yard.getObjectsAtLocation(flocation.x,flocation.y);
						
					List<FoodCell> tempFoodList = getListOfFoodInEmplacement(beings,flocation.x,flocation.y );
					listOfFood.addAll(tempFoodList);
					
				}
				
									
			}
		
		}
		return listOfFood;
	}
	
protected List<Int2D> getEmplacementList(Beings beings,int l,int c) {
		
		List<Int2D> listOfEmplacement = new ArrayList<>();
		for (int i = -DISTANCE_DEPLACEMENT ; i <= DISTANCE_DEPLACEMENT ; i++) 
		{
			for (int j = -DISTANCE_DEPLACEMENT ; j <= DISTANCE_DEPLACEMENT ; j++) {
				if (i != 0 || j != 0) 
				{
					Int2D flocation = new Int2D(beings.yard.stx(l + i),
					beings.yard.sty(c + j));
					//Bag ag = beings.yard.getObjectsAtLocation(flocation.x,flocation.y);
					listOfEmplacement.add(flocation);			
				}
			}
		}
		return listOfEmplacement;
	}
	
	protected List<FoodCell> getEmplacementFoodList(Beings beings,int l,int c) {
		
		List<FoodCell> listOfFood = new ArrayList<>();
		for (int i = -DISTANCE_PERCEPTION ; i <= DISTANCE_PERCEPTION ; i++) 
		{
			for (int j = -DISTANCE_PERCEPTION ; j <= DISTANCE_PERCEPTION ; j++) {
				if (i != 0 || j != 0) 
				{
					Int2D flocation = new Int2D(beings.yard.stx(l + i),
					beings.yard.sty(c + j));
					//Bag ag = beings.yard.getObjectsAtLocation(flocation.x,flocation.y);
					
					List<FoodCell> tempFoodList = getListOfFoodInEmplacement(beings,flocation.x,flocation.y );
					listOfFood.addAll(tempFoodList);					
				}
			}
		}
		return listOfFood;
	}

	
	public FoodCell getMaxFoodemplacement(List<FoodCell> listFoodCell)
	{
		int max = 0;
		FoodCell chosenFood=null;
		for(FoodCell f : listFoodCell )
		{
			if(f.getFOOD_AVAILABLE()>max)
			{
				chosenFood=f;
			}
		}
		return chosenFood;
	}
	
	public List<FoodCell> getListOfFoodInEmplacement(Beings beings, int l, int c)
	{
		Bag bag = beings.yard.getObjectsAtLocation(l,c);
		List<FoodCell> res = new ArrayList<>();
		
		if(bag!=null)
		{				
			//food (il peut y avoir plusieurs elemements "nourriture" par case)
			for(Object o : bag)
			{				
				if (o.getClass() != this.getClass() && ((FoodCell) o).getFOOD_AVAILABLE()>0)
				{	
					System.out.print("food en "+l+"-"+c+"\n");
					res.add(((FoodCell) o));//cast Object -> FoodCell
				}
			}	
			
		}
		return res;	
	}
	
	
	@Override
	public void step(SimState state) 
	{
		Beings beings = (Beings) state;
		
		//energie = 0
		if (ENERGIE==0)
		{
			beings.yard.remove(this); //model est le SimState
			stoppable.stop();
			System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi meurt\n");
		}
		//energie = 1 (doit manger nourriture)
		else if (ENERGIE==1)
		{			
			//detecte nourriture autour de lui
			List<FoodCell> listFoodCell = aroundFoodList(beings,x,y,true);//parametre true, pour prendre en compte la case sur laquelle est la fourmi
			if(listFoodCell!= null && listFoodCell.size()>0)
			{				
				//si au moins un element Food, on prend la nourriture de l'emplacement ou il y en a le plus
				FoodCell chosenFood = getMaxFoodemplacement(listFoodCell);				
				
				//mange f
				chosenFood.consumeFood();
				ENERGIE = ENERGIE + constants.FOOD_ENERGY ;
				System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi mange nourriture située en ["+chosenFood.x+", "+chosenFood.y+"]. Energie ("+ENERGIE+"/"+MAX_ENERGIE+"). Reserve ("+CHARGE_PORTEE+"/"+CHARGE_MAX+")\n");
							
				
			}
			//mange nourrriture qu'il transporte (pas de nourriture a proximite)
			else if (CHARGE_PORTEE > 0)
			{
				CHARGE_PORTEE --;	
				ENERGIE=ENERGIE + constants.FOOD_ENERGY;
				System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi mange nourriture qu'elle transporte. Energie ("+ENERGIE+"/"+MAX_ENERGIE+"). Reserve ("+CHARGE_PORTEE+"/"+CHARGE_MAX+")\n");
			}
			//si transporte rien + pas de reserve, fourmi va mourir
			else
			{
				System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi meurt\n");
				ENERGIE--;
			}			
			
		}
		//energie > 1
		else
		{
			//si la fourmi est au maximum de sa capacite, elle ne fait rien (mais perd quand meme un d'energie)
			if(CHARGE_PORTEE<CHARGE_MAX)
			{				
				//si de la nourriture est presente sur l'emplacement ou se trouve la fourmi
				if (getListOfFoodInEmplacement(beings,x,y).size()>0)
				{
					List<FoodCell> listOfFood = getListOfFoodInEmplacement(beings,x,y);
					
					//prend les ressources ou il y en a le plus
					FoodCell chosenFood = getMaxFoodemplacement(listOfFood);
					
					//au moins un element "nourriture" qui contient de la nourriture
					//fourmi stock la nourriture
					chosenFood.consumeFood();
					CHARGE_PORTEE++;
					int tempEnergie=ENERGIE;
					ENERGIE--;
					System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi stock nourriture située sur sur sa propre case. Energie ("+ENERGIE+"/"+MAX_ENERGIE+"). Reserve ("+CHARGE_PORTEE+"/"+CHARGE_MAX+")\n");
				}				
				//regarde nourriture autour de lui (ne peut pas chargée nourriture adjacente)
				else
				{
					List<FoodCell> listFarFoodCell = getEmplacementFoodList(beings,x,y);					
					
					if(listFarFoodCell!= null && listFarFoodCell.size()>0)
					{
						//se deplace vers celle ou il y a le plus de nourriture
						FoodCell chosenFood = getMaxFoodemplacement(listFarFoodCell);
						beings.yard.setObjectLocation(this,chosenFood.x ,chosenFood.y);
						int tempEnergie=ENERGIE;
						ENERGIE--;
						System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi se deplace vers nourriture située en ["+chosenFood.x+", "+chosenFood.y+"]. Energie ("+ENERGIE+"/"+MAX_ENERGIE+"). Reserve ("+CHARGE_PORTEE+"/"+CHARGE_MAX+")\n");
												
					}
					//sinon, se deplace au hasard (sur une case ou il n'y a pas deja une fourmi (amelioration : si nourriture critique, aller la ou il y a une fourmi => potentiel point de ressources))
					else
					{
						List<Int2D> listOfEmplacement = getEmplacementList(beings,x,y);
						Random rand = new Random();
						int  n = rand.nextInt(listOfEmplacement.size()) ;
						beings.yard.setObjectLocation(this,listOfEmplacement.get(n));
						int tempEnergie=ENERGIE;
						ENERGIE--;
						System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi se deplace au hasard en ["+listOfEmplacement.get(n).x+", "+listOfEmplacement.get(n).y+"]. Energie ("+ENERGIE+"/"+MAX_ENERGIE+"). Reserve ("+CHARGE_PORTEE+"/"+CHARGE_MAX+")\n");
											
					}
					
				}
					
				
					
			}
			else
			{
				int tempEnergie=ENERGIE;
				ENERGIE--;
				System.out.print("[fourmi "+idFourmi+"]["+x+", "+y+"] fourmi ne fait rien.  Energie passe de "+tempEnergie+" a "+ENERGIE+"sur "+MAX_ENERGIE+"\n");
				
			}
			
				
				
		}
			
	}

	
	@Override
	public void stop() {		
		
	}

}
