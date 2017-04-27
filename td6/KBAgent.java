package td6;

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class KBAgent extends Agent{
	
	public KBAgent(){		
		
	}
	
	protected void setup() {
		
		//enregistrement auxpres du DF
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Kb");
		sd.setName("Kb");
		dfad.addServices(sd);
		try {
				DFService.register(this, dfad);
		}
		catch (FIPAException fe) {
						fe.printStackTrace();
		}
										
		addBehaviour(new KbWaitMessage());	
	}
}
