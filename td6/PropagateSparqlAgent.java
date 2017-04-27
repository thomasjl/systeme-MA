package td6;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;



public class PropagateSparqlAgent extends Agent{
	
	public void setup()
	{
		//enregistrement auxpres du DF
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Sparql");
		sd.setName("Sparql");
		dfad.addServices(sd);
		try {
				DFService.register(this, dfad);
		}
		catch (FIPAException fe) {
						fe.printStackTrace();
		}
				
		addBehaviour(new SparqlWaitMessage());					
		
	}
	
}
