package controlor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import metier.Joueur;
import metier.Paquet;
import server.Client;
import server.Server;
import server.Server.MyThreadHandler;
import vue.Plateau;

public class PlateauController {

	private Server server=null;
	private Client client=null;
	private Map<Integer,MyThreadHandler> listClientsServer = new HashMap<Integer,MyThreadHandler>();
	private Map<Integer,Joueur> joueurs = new HashMap<Integer,Joueur>();
	private int id;
	private Plateau plateauView;
	private Paquet paquet;
	
	
	
	public void setServer(Server server) {
		this.server = server;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setListClientsServer(Map<Integer, MyThreadHandler> listClientsServer) {
		this.listClientsServer = listClientsServer;
	}

	public void setJoueurs(Map<Integer, Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPlateauView(Plateau plateauView) {
		this.plateauView = plateauView;
	}

	public void initializePaquet(){
		paquet = new Paquet();
	}
	
	public void sendFirstCards(){
		
	}
	
	public void getJSONFromServer(JSONObject json) throws JSONException{
		
	}
	
	public synchronized JSONObject getJSONFromClient(JSONObject json) throws IOException{
		
		
		
		return json;
	}
	
	public void test(){
		System.out.println("Une action a été délcenché");
	}
}
