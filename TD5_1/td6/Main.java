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


public class Main {

	public static void main(String[] args) {
		
		Model model = ModelFactory.createDefaultModel();		
		
		FileManager.get().readModel(model,"./td6/tBox.ttl");
		
		String nstd5 = model.getNsPrefixURI("td5");		
		String nsrdf = model.getNsPrefixURI("foaf");
						
		Resource h = model.getResource(nstd5 + "p1");
		
		Property type = model.getProperty(nsrdf + "name");
		
		Selector selectTypes = new SimpleSelector(h,type,(Resource)null);
		//Selector selectTypes = new SimpleSelector((Resource)null,type,"Gaston");//identifiant, relation, valeur (on met a null ce que l'on recherche)
		StmtIterator iterator = model.listStatements(selectTypes);
		
		
		while (iterator.hasNext()) {
			Statement st = iterator.nextStatement();
			//get Object, pour afficher la valeur
			//get Subject pour afficher l'id
			RDFNode obj = st.getPredicate();
			System.out.println(obj.toString());
			System.out.println(obj.asResource().getLocalName());
			}
		

	}

}
