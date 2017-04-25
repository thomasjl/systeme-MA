package td6;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;



public class PropagateSparqlAgent extends Agent{

	public AID getKbAgent()
	{
		//recherche de EnvironmentAgent auxpres du DF
		AID rec = null;
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Kb");
		sd.setName("Kb");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
		if (result.length > 0)
			rec = result[0].getName();		
			
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		
		return rec;
	}
	
	public void setup()
	{
		ACLMessage m = new ACLMessage(ACLMessage.INFORM);
		
		//recherche de l'agent environnemnt aupres du DF
		m.addReceiver(getKbAgent());		
		
					
		m.setContent("truc");
						
		send(m);
	}
	
}
