package td5;

import java.io.File;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainContainer {

	public static void main(String[] args) {
		
		String MAIN_PROPERTIES_FILE = "profile.cfg";
		Runtime rt = Runtime.instance();
		Profile p = null;
		
		try{
			
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);			
			
			AgentContainer mc = rt.createMainContainer(p);			
				
			AgentController interm = mc.createNewAgent("interm", "td5.IntermAgent",null);
			interm.start();
			
			AgentController KB = mc.createNewAgent("kb","td5.KBAgent", null);
			KB.start();
						
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	
		
	

	}

}