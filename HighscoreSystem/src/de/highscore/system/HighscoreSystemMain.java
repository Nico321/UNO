package de.highscore.system;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.highscore.common.HighscoreOnlineService;

public class HighscoreSystemMain {
		
		private static Logger jlog =  Logger.getLogger(HighscoreSystemMain.class.getPackage().getName());
			
		/**
		 * Diese Main-Methode startet den Server-Teil des OnlineBankingSystems.
		 * 
		 * 
		 * Beim Start m�ssen der JVM des Servers die folgenden Systemproperties mitgegeben werden:
		 * 
		 * -Djava.rmi.server.codebase=file:${workspace_loc:/OnlineBankingServer/bin}/
		 * Dieser Parameter wird von der RMI Registry an den Client weitergegeben, damit dieser wei�, von wo
		 * er die vom Server �bermittelten Klassen, die der Client selbst nicht kennt (KundeImpl und KontoImpl), laden kann.
		 * 
		 * -Djava.security.policy=.\bin\security.policy
		 * Dieser Parameter gibt an, wo die f�r den Server zu verwendende Security-Policy hinterlegt ist.
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
			
			//erzeuge ein paar Beispieldaten zu Kunden und Konten; die verwendeten Konstruktoren registrieren die erzeugten Objekte in 
			//zentralen Registries, sodass sie bei spaeteren Client-Requests wiedergefunden werden koennen.
			HighscoreOnlineServiceImpl hs = new HighscoreOnlineServiceImpl();
			String joe = "Joe";
			String emma = "Emma";
			try{
			hs.addNewUser(joe, 50);
			hs.addNewUser(emma, 100);
			hs.addPointsToUser(joe, 70);
			}
			catch (Exception e)
			{
			
			}
		    
			//In der folgenden Zeile wird ein SecurityManager gesetzt. Das ist erforderlich, wenn Klassendefinitionen
		    //vom Server geladen werden sollen. In unserem Beispiel sind das die Klassen KundeImpl und KontoImpl.
		    System.setSecurityManager(new RMISecurityManager());

		    try {
		    	//starte die Java RMI Registry:
		    	Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		    	jlog.log(Level.FINE, "Registry gestartet: "+ registry);
	            
				//lass Java zum Remote-Objekt OnlineBankingSystemImpl die fuer die Netzwerkkommunikation erforderlichen Stub und Skeleton generieren:
		    	HighscoreOnlineService highscoreOnlineServiceStub = (HighscoreOnlineService) UnicastRemoteObject.exportObject(new HighscoreOnlineServiceImpl(),0);
	           
	            //veroeffentliche das vorbereitete Remote-Objekt und einer eindeutigen ID in der Registry:
	            registry.rebind(HighscoreOnlineService.INTERFACE_ID, highscoreOnlineServiceStub);
	            jlog.log(Level.FINE, "Interface in Registry angemeldet. Warte auf Requests...");
			}
			catch (RemoteException ex) {
			    jlog.log(Level.SEVERE, ex.getMessage(), ex);
			}		
		}
}
