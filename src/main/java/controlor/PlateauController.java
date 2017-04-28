package controlor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import metier.Boat;
import metier.Joueur;
import metier.Paquet;
import metier.Wagon;
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
	
	
	
	public PlateauController() {
		// TODO Auto-generated constructor stub
	}

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
	
	public void sendFirstCards(){
		paquet = new Paquet();
		JSONObject json;
		JSONArray jsonA;
		Gson gson;
		int i=0;
		Set cles = listClientsServer.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next();
		   
		   json = new JSONObject();
		   jsonA = new JSONArray();
		   gson = new Gson();
			for(i=0;i<3;i++){
				jsonA.put(gson.toJson(paquet.piocheWagon()));
			}
			try {
				json.put("wagon", jsonA);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonA = new JSONArray();
			for(i=0;i<7;i++){
				jsonA.put(gson.toJson(paquet.piocheBoat()));
			}
			try {
				json.put("bateau", jsonA);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jsonA = new JSONArray();
			for(i=0;i<5;i++){
				jsonA.put(gson.toJson(paquet.piocheDesination()));
			}
			try {
				json.put("destination", jsonA);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				server.sendMessage(listClientsServer.get(cle), json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for(i=0;i<3;i++){
			plateauView.setCardsWagonInMainOfPlayer((paquet.piocheWagon()));
		}
		for(i=0;i<7;i++){
			plateauView.setCardsBoatInMainOfPlayer((paquet.piocheBoat()));
		}
		
	}
	
	/**
	 * fonction à supprimer
	 * @param json
	 * @throws JSONException
	 */
	public void getJSONFromServer(JSONObject json) throws JSONException{
		
	}
	
	public synchronized JSONObject getJSONFromClient(JSONObject json) throws IOException{
		
		
		
		return json;
	}
	
	public void test(){
		System.out.println("Une action a été délcenché");
	}
	
	public void waitServerMsg(){
		JSONObject json=null;
		JSONArray jsonA = null;
		Gson gson = new Gson();
		if(client==null){
			System.out.println("cette valeur est nulle");
		}
		try {
			json = client.receiveJSON();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(json!=null){
			if(json.has("wagon")){
				int i = 0;
				try {
					jsonA = json.getJSONArray("wagon");
					for(i=0;i<jsonA.length();i++){
						plateauView.setCardsWagonInMainOfPlayer(gson.fromJson(jsonA.get(i).toString(), Wagon.class));
					}
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(json.has("bateau")){
				int i = 0;
				try {
					jsonA = json.getJSONArray("bateau");
					for(i=0;i<jsonA.length();i++){
						plateauView.setCardsBoatInMainOfPlayer(gson.fromJson(jsonA.get(i).toString(), Boat.class));
					}
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
