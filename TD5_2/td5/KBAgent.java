package td5;

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

public class KBAgent extends Agent{
	
	public KBAgent(){		
		
	}
	
	protected void setup() {
		try{
			
			Model model = ModelFactory.createDefaultModel();			
			FileManager.get().readModel(model,"./td5/tBox.ttl");
			String nstd5 = model.getNsPrefixURI("td5");
			Resource h = model.getResource(nstd5 + "p1");
			
			Selector selectTypes = new SimpleSelector(h,(Property)null,(Resource)null);
			
			StmtIterator iterator = model.listStatements(selectTypes);
			
			
			while (iterator.hasNext()) {
				Statement st = iterator.nextStatement();
				//get Object, pour afficher la valeur
				//get Subject pour afficher l'id
				RDFNode obj2 = st.getPredicate();
				System.out.print(obj2.toString()+" ");
				
				RDFNode obj = st.getObject();
				System.out.print(obj.toString()+" \n");			
				
				
				
				
				//System.out.println(obj.asResource().getLocalName());
				}
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		}
}
