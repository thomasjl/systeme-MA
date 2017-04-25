package td6;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class KbWaitMessage extends CyclicBehaviour{
	
	public KbWaitMessage()
	{
		
	}
	
	public void action()
	{
		ACLMessage message = myAgent.receive();
		
		if(message != null)
		{
					
			
		}
		else
		{
			block();
		}
	}

}
