package td6;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SparqlWaitMessage extends CyclicBehaviour{

	public SparqlWaitMessage()
	{
	
	}
	
	public AID getKbAgent()
	{		
		AID rec = null;
		DFAgentDescription template =
		new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Kb");
		sd.setName("Kb");
		template.addServices(sd);
		try {
			DFAgentDescription[] result =
			DFService.search(myAgent, template);
		if (result.length > 0)
			rec = result[0].getName();		
			
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		
		return rec;
	}
	
	
	public void action()
	{
		ACLMessage message = myAgent.receive();
		
		if(message != null)
		{
			
			
			if ((message.getSender().equals(getKbAgent()) ))
			{
				//message provenant de l'agent KG
				System.out.print("Result :\n"+message.getContent()+"\n");
			}
			else
			{
				//message provenant de la console Jade
				ACLMessage m = new ACLMessage(ACLMessage.INFORM);
				
				//recherche de l'agent environnemnt aupres du DF
				m.addReceiver(getKbAgent());					
							
				m.setContent(message.getContent());
				myAgent.send(m);
			}
			
			
			
		}
		else
		{
			block();
		}
	}
	
}
