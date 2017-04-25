package td6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class main {

	public static void test() {
		//String query = "./td6/s1.sparql"; // Q1
		//String query = "./td6/s2.sparql"; // Q2
		String query = "./td6/s3.sparql"; // Q3
		Model model = ModelFactory.createDefaultModel();
		try {
			//FileManager.get().readModel(model,"./td6/tBox.ttl");
			model.read(new FileInputStream("C:/Users/thomas/workspace/IA04/TD6/td6/src/td6/foaf.n3"),null, "TURTLE");
			runSelectQuery(query, model);
		} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
		
	}
	
	public static void test2() {
		
		//String q = "select distinct ?Concept " + 	"where {?Concept a <http://www.w3.org/2002/07/owl#Class>} LIMIT 100";
		//Query query = QueryFactory.create(q);
		
		Query query = QueryFactory.read( "./td6/s4.sparql"  );
		
		System.setProperty("http.proxyHost","proxyweb.utc.fr");
		System.setProperty("http.proxyPort","3128");
		System.out.println("Query sent");
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql",query );
		ResultSet concepts = qexec.execSelect();
		ResultSetFormatter.out(concepts);
		qexec.close();
		
		
	}
	
	public static void test3()
	{
		//etape 3
		//String query = "./td6/s5.sparql"; //etape3 Q1
		String query = "./td6/s6.sparql";  //etape3 Q2
		Model model = ModelFactory.createDefaultModel();
		
		//FileManager.get().readModel(model,"./td6/tBox.ttl");
			
		FileManager.get().readModel(model, "C:/Users/thomas/workspace/IA04/TD6/td6/src/td6/tBox.ttl");
			
		//model.read(new FileInputStream("C:/Users/thomas/workspace/IA04/TD6/td6/src/td6/tBox.ttl"),null, "TURTLE");
		runSelectQuery(query, model);
		
	}
		
	public static void runSelectQuery(String qfilename, Model model) {
		Query query = QueryFactory.read(qfilename);
		QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
		ResultSet r = queryExecution.execSelect();
		ResultSetFormatter.out(System.out,r);
		queryExecution.close();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test();
		//test2();
		test3();
		
		//reponse Q4 etape4 : on utilise KB pour retrouver le nom des pays. Et, on utilise GeoDataAgent pour réunir les capitales des pays. D'ou importance de agents.
	}

}
