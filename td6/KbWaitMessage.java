package td6;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class KbWaitMessage extends CyclicBehaviour{
	
	public KbWaitMessage()
	{
		
	}
	
	public AID getSparqlAgent()
	{		
		AID rec = null;
		DFAgentDescription template =
		new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Sparql");
		sd.setName("Sparql");
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
	
	
	public static void runSelectQuery(Query query, Model model) {
				
		QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
		ResultSet r = queryExecution.execSelect();
		ResultSetFormatter.out(System.out,r);
		queryExecution.close();
	}
	
	public void action()
	{
		ACLMessage message = myAgent.receive();
		
		if(message != null)
		{
			if ((message.getSender().equals(getSparqlAgent()) ))
			{
				//message provenant de l'agent sparql
				//System.out.print("log\n"+message.getContent()+"\n");
				//ParameterizedSparqlString pss = new ParameterizedSparqlString();
				//System.out.print("**************************\n");
				//pss.setCommandText(message.getContent());
				
				
				Model model = ModelFactory.createDefaultModel();
				try {
					model.read(new FileInputStream("C:/Users/thomas/workspace/IA04/TD6/td6/src/td6/tBox.ttl"),null, "TURTLE");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Query query = QueryFactory.create(message.getContent(), Syntax.syntaxARQ) ;
				
				QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
				ResultSet r = queryExecution.execSelect();
				
				//ResultSetFormatter.out(System.out,r);
				
				//String res = ResultSetFormatter.asText(r);
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				ResultSetFormatter.out(boas,r); 
				
				String res = boas.toString();			
				
				queryExecution.close();
								
				//envoie du resultat a l'agent Sparql pour affichage
				ACLMessage m = new ACLMessage(ACLMessage.INFORM);
				
				//recherche de l'agent environnemnt aupres du DF
				m.addReceiver(getSparqlAgent());					
							
				m.setContent(res);
				myAgent.send(m);
								
			}	
			
		}
		else
		{
			block();
		}
	}

}
