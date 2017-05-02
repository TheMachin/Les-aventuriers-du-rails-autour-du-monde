package controlor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ennumeration.EnumCarte;
import metier.Boat;
import metier.Carte;
import metier.Destination;
import metier.Iteneraire;
import metier.Joueur;
import metier.Paquet;
import metier.PlateauJeu;
import metier.RouteMartime;
import metier.RouteTerrestre;
import metier.Ville;
import metier.Wagon;
import server.Client;
import server.Server;
import server.Server.MyThreadHandler;
import visitor.SaveJsonVisitor;
import vue.Plateau;

public class PlateauController {

	private Server server=null;
	private Client client=null;
	private Map<Integer,MyThreadHandler> listClientsServer = new HashMap<Integer,MyThreadHandler>();
	private int id;
	private Plateau plateauView;
	private PlateauJeu plateauJeu = new PlateauJeu(5);
	
	private boolean initGame = true;
	private boolean tour=false;
	private int idTour=0;
	private int nbCartes=0;
	private boolean carteTransport=false;
	private boolean routePort=false;
	private boolean carteDestination=false;
	
	
	
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

	public void setId(int id) {
		this.id = id;
	}

	public void setPlateauView(Plateau plateauView) {
		this.plateauView = plateauView;
	}
	
	public PlateauJeu getPlateauJeu() {
		return plateauJeu;
	}

	public void setPlateauJeu(PlateauJeu plateauJeu) {
		this.plateauJeu = plateauJeu;
		this.plateauJeu.setAllPlayerNotReady();
	}

	public void sendFirstCards(){
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
				Wagon w = plateauJeu.getPaquet().piocheWagon();
				jsonA.put(gson.toJson(w));
				plateauJeu.getJoueur(cle).addWagon(w);
			}
			try {
				json.put("wagon", jsonA);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonA = new JSONArray();
			for(i=0;i<7;i++){
				Boat b = plateauJeu.getPaquet().piocheBoat();
				jsonA.put(gson.toJson(b));
				plateauJeu.getJoueur(cle).addBoat(b);
			}
			try {
				json.put("bateau", jsonA);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray jsonAD = new JSONArray();
			JSONArray jsonAI = new JSONArray();
			for(i=0;i<5;i++){
				Object o = plateauJeu.getPaquet().piocheDesination();
				Carte c = (Carte) o;
				if(c.getName().equals(EnumCarte.DESTINATION)){
					Destination d = (Destination) o;
					jsonAD.put(gson.toJson(d));
				}else{
					Iteneraire ite = (Iteneraire) o;
					jsonAI.put(gson.toJson(ite));
				}
			}
			try {
				json.put("destination", jsonAD);
				json.put("iteneraire", jsonAI);
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
			plateauView.setCardsWagonInMainOfPlayer((plateauJeu.getPaquet().piocheWagon()));
		}
		for(i=0;i<7;i++){
			plateauView.setCardsBoatInMainOfPlayer((plateauJeu.getPaquet().piocheBoat()));
		}
		plateauView.printMsgDestination("Veuillez choisir au moins 3 cartes");
		for(i=0;i<5;i++){
			Object o = plateauJeu.getPaquet().piocheDesination();
			Carte c = (Carte) o;
			if(c.getName().equals(EnumCarte.DESTINATION)){
				Destination d = (Destination) o;
				plateauView.setCardsDestinationForChoice(d);
			}else{
				Iteneraire ite = (Iteneraire) o;
				plateauView.setCardsIteneraireForChoice(ite);
			}
			
		}
		
		server.setPlateauControllerAtThread(listClientsServer, this);
		
	}
	
	
	/**
	 * On appelle cette fonction quand le serveur reçoit les paquets du client
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public synchronized JSONObject getJSONFromClient(JSONObject json) throws IOException{
		Gson gson = new Gson();
		JSONArray jsonA = null;
		if(json==null){
			
		}else if(json.has("pioche")){
			try {
				int no = json.getInt("id");
				String pioche = json.getString("pioche");
				json = new JSONObject();
				jsonA = new JSONArray();
				switch(pioche){
					case "bateau" :
						Boat b = plateauJeu.getPaquet().piocheBoat();
						plateauJeu.getJoueur(no).addBoat(b);
						json.put("bateau", jsonA.put(gson.toJson(b)));
						break;
					case "wagon":
						Wagon w = plateauJeu.getPaquet().piocheWagon();
						plateauJeu.getJoueur(no).addWagon(w);
						json.put("wagon", jsonA.put(gson.toJson(w)));
						break;
					case "destination":
						JSONArray jsonAD = new JSONArray();
						JSONArray jsonAI = new JSONArray();
						int i;
						for(i=0;i<4;i++){
							Object o = plateauJeu.getPaquet().piocheDesination();
							Carte c = (Carte) o;
							if(c.getName().equals(EnumCarte.DESTINATION)){
								Destination d = (Destination) o;
								jsonAD.put(gson.toJson(d));
							}else{
								Iteneraire ite = (Iteneraire) o;
								jsonAI.put(gson.toJson(ite));
							}
						}
						try {
							json.put("destination", jsonAD);
							json.put("iteneraire", jsonAI);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					default:
						json.put("error", "Erreur de traitement");
						break;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					json.put("error", "Erreur de traitement");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}else if(json.has("route")){
			if(json.has("routeTerrestre")){
				
				List<Wagon> listW = new ArrayList<Wagon>();
				int i;
				int no=-1;
				RouteTerrestre r=null;
				try {
					no = json.getInt("id");
					r = gson.fromJson(json.getString("routeTerrestre"), RouteTerrestre.class);
					
					jsonA = new JSONArray();
					jsonA = json.getJSONArray("cartesWagon");
					
					for(i=0;i<jsonA.length();i++){
						listW.add(gson.fromJson(jsonA.getString(i), Wagon.class));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				json = new JSONObject();
				
				//on verifie si la route n'a pas déjá été prise
				if(checkTakeRoadWagon(r)&&no>0){
					//on vérifie si le nombre de pion est suffisant
					if(plateauJeu.getListJoueur().get(no).getPions().takeRoadOrPort(0, listW.size(), r, null, null)){
						for(i=0;i<listW.size();i++){
							plateauJeu.getPaquet().addWagonDefausse(listW.get(i));
							plateauJeu.getJoueur(no).deleteWagon(listW.get(i));
						}
						plateauJeu.getListJoueur().get(no).calculScoreAfterTakeRoad(0, listW.size());
						try {
							json.put("route", true);
							json.put("score", plateauJeu.getListJoueur().get(no).getScore());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							json.put("route", false);
							json.put("error", "Nombre de pions insuffisant");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					try {
						json.put("route", false);
						if(no<=0){
							json.put("error", "Erreur de traitement");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return json;
			}
			
		}else if(json.has("defausse")){
			int i;
			int no;
			
			if(json.has("defausseDestination")){
				try {
					jsonA = json.getJSONArray("defausseDestination");
					for(i=0;i<jsonA.length();i++){
						Destination d = gson.fromJson(jsonA.get(i).toString(), Destination.class);
						plateauJeu.getPaquet().addDestinationDefausse((d));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(json.has("defausseIteneraire")){
				try {
					jsonA = json.getJSONArray("defausseIteneraire");
					for(i=0;i<jsonA.length();i++){
						Iteneraire ite = gson.fromJson(jsonA.get(i).toString(), Iteneraire.class);
						plateauJeu.getPaquet().addDestinationDefausse((ite));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(json.has("selectionDestination")){
				try {
					jsonA = json.getJSONArray("selectionDestination");
					no = json.getInt("id");
					for(i=0;i<jsonA.length();i++){
						plateauJeu.getListJoueur().get(no).addDestination((gson.fromJson(jsonA.get(i).toString(), Destination.class)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(json.has("selectionIteneraire")){
				try {
					jsonA = json.getJSONArray("selectionDestination");
					no = json.getInt("id");
					for(i=0;i<jsonA.length();i++){
						plateauJeu.getListJoueur().get(no).addIteneraire((gson.fromJson(jsonA.get(i).toString(), Iteneraire.class)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			json = new JSONObject();
			try {
				json.put("defausse", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(json.has("pionBateau")||json.has("pionWagon")){
			try {
				int no = json.getInt("id");
				int boat = json.getInt("pionBateau");
				int wagon = json.getInt("pionWagon");
				plateauJeu.getListJoueur().get(no).getPions().setNbBoat(boat);
				plateauJeu.getListJoueur().get(no).getPions().setNbWagon(wagon);
				json = new JSONObject();
				json.put("pion", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	public void piocheCards(String card){
		if(tour){
			choixCartes(card);
			JSONObject json = new JSONObject();
			try {
				json.put("pioche", card);
				json.put("id", id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(server==null){
				try {
					client.sendJSON(json);
					waitServerMsg();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				switch(card){
					case "wagon":
						Wagon w = plateauJeu.getPaquet().piocheWagon();
						plateauView.setCardsWagonInMainOfPlayer(w);
						plateauJeu.getJoueur(id).addWagon(w);
						break;
					case "bateau":
						Boat b = plateauJeu.getPaquet().piocheBoat();
						plateauView.setCardsBoatInMainOfPlayer(b);
						plateauJeu.getJoueur(id).addBoat(b);
						break;
					case "destination":
						int i;
						for(i=0;i<4;i++){
							Object o = plateauJeu.getPaquet().piocheDesination();
							Carte c = (Carte) o;
							if(c.getName().equals(EnumCarte.DESTINATION)){
								Destination d = (Destination) o;
								plateauView.setCardsDestinationForChoice(d);
								plateauJeu.getJoueur(id).addDestination(d);
							}else{
								Iteneraire ite = (Iteneraire) o;
								plateauView.setCardsIteneraireForChoice(ite);
								plateauJeu.getJoueur(id).addIteneraire(ite);
							}
							
						}
						break;
					default:
						
						break;
				}
			}
		}else{
			// ce n'est pas votre tour
		}
		
	}
	
	public void setPion(int wagon, int boat){
		if(server==null){
			JSONObject json = new JSONObject();
			try {
				json.put("pionBateau", boat);
				json.put("pionWagon", wagon);
				json.put("id", id);
				client.sendJSON(json);
				json = client.receiveJSON();
				/*plateauJeu.getListJoueur().get(id).getPions().setNbBoat(boat);
				plateauJeu.getListJoueur().get(id).getPions().setNbWagon(wagon);*/
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			plateauJeu.getListJoueur().get(id).getPions().setNbBoat(boat);
			plateauJeu.getListJoueur().get(id).getPions().setNbWagon(wagon);
			
			SaveJsonVisitor sv = new SaveJsonVisitor();
			plateauJeu.accept(sv);
			
		}
		
	}
	
	public void choixCartes(String card){
		if(!carteTransport&&!carteDestination){
			switch(card){
				case "wagon":
					carteTransport=true;
					break;
				case "bateau":
					carteTransport=true;
					break;
				case "destination":
					carteDestination=true;
					break;
			}
		}else{
			if(carteTransport){
				nbCartes++;
			}
		}
	}
	
	/**
	 * Fonction pour prendre une route
	 * @param r
	 * @param listW
	 * @return
	 */
	public boolean takeRoadWagon(RouteTerrestre r, ArrayList<Wagon> listW){
		if(client!=null){
			JSONObject json = new JSONObject();
			JSONArray jsonA = new JSONArray();
			Gson gson = new Gson();
			try {
				json.put("route", true);
				json.put("routeTerrestre", gson.toJson(r));
				json.put("id", id);
				int i;
				for(i=0;i<listW.size();i++){
					jsonA.put(gson.toJson(listW.get(i)));
				}
				json.put("cartesWagon", jsonA);
				client.sendJSON(json);
				json = client.receiveJSON();
				
				boolean route = json.getBoolean("route");
				
				if(route){
					
					int score = json.getInt("score");
					plateauJeu.getListJoueur().get(id).setScore(score);
					plateauJeu.getListJoueur().get(id).getPions().addRouteTerrestre(r);
					plateauView.printScore(score);
					return true;
				}else{
					return false;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(checkTakeRoadWagon(r)){
				if(plateauJeu.getListJoueur().get(0).getPions().takeRoadOrPort(0, listW.size(), r, null, null)){
					int i;
					for(i=0;i<listW.size();i++){
						plateauJeu.getPaquet().addWagonDefausse(listW.get(i));
						plateauJeu.getJoueur(id).deleteWagon(listW.get(i));
					}
					plateauJeu.getListJoueur().get(0).calculScoreAfterTakeRoad(0, listW.size());
					plateauView.printScore(plateauJeu.getListJoueur().get(0).getScore());
					return true;
				}else{
					//pas assez de pions
					return false;
				}
			}else{
				return false;
			}
		}
		return false;
	}
	
	public boolean checkTakeRoadWagon(RouteTerrestre r){
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
			int cle = (int) it.next();
			if(plateauJeu.getListJoueur().get(cle).checkIfHimTakeRoadWagon(r)){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkTakeRoadBoat(RouteMartime r){
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
			int cle = (int) it.next();
			if(plateauJeu.getListJoueur().get(cle).checkIfHimTakeRoadBoat(r)){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkTakePort(Ville v){
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
			int cle = (int) it.next();
			if(plateauJeu.getListJoueur().get(cle).checkIfHimTakePort(v)){
				return false;
			}
		}
		return true;
	}
	
	public void takeCardsDestination(List<Destination> destSelect, List<Destination> destNoSelect, List<Iteneraire> iteSelectm, List<Iteneraire> iteNoSelect){
		System.out.println("here");
		if(initGame){
			if((destSelect.size()+iteSelectm.size())>=3){
				traitementCartesDestination(destSelect, destNoSelect, iteSelectm, iteNoSelect);
			}else{
				//dire qu'il y a pas assez de cartes selectionné
				plateauView.printMsgDestination("Veuillez sélectionner au moins 3 carte");
			}
		}else if((destSelect.size()+iteSelectm.size())>=1){
			traitementCartesDestination(destSelect, destNoSelect, iteSelectm, iteNoSelect);
		}else{
			plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
		}
	}
	
	public void traitementCartesDestination(List<Destination> destSelect, List<Destination> destNoSelect, List<Iteneraire> iteSelectm, List<Iteneraire> iteNoSelect){
		int i;
		if(server==null){
			JSONArray jsonAD = new JSONArray();
			JSONArray jsonAI = new JSONArray();
			JSONObject json = new JSONObject();
			Gson gson = new Gson();
			for(i=0;i<destNoSelect.size();i++){
				jsonAD.put(gson.toJson(destNoSelect.get(i)));
			}
			for(i=0;i<iteNoSelect.size();i++){
				jsonAI.put(gson.toJson(iteNoSelect.get(i)));
			}
			try {
				json.put("defausse", true);
				json.put("defausseDestination", jsonAD);
				json.put("defausseIteneraire", jsonAI);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonAD = new JSONArray();
			jsonAI = new JSONArray();
			for(i=0;i<destSelect.size();i++){
				jsonAD.put(gson.toJson(destSelect.get(i)));
			}
			for(i=0;i<iteSelectm.size();i++){
				jsonAI.put(gson.toJson(iteSelectm.get(i)));
			}
			try {
				json.put("id", id);
				json.put("selectionDestination", jsonAD);
				json.put("selectionIteneraire", jsonAI);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.sendJSON(json);
				json = client.receiveJSON();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initGame=false;
		}else{
			for(i=0;i<destNoSelect.size();i++){
				plateauJeu.getPaquet().addDestinationDefausse(destNoSelect.get(i));
			}
			for(i=0;i<iteNoSelect.size();i++){
				plateauJeu.getPaquet().addDestinationDefausse(iteNoSelect.get(i));
			}
			for(i=0;i<destSelect.size();i++){
				plateauJeu.getListJoueur().get(id).addDestination(destSelect.get(i));
			}
			for(i=0;i<iteSelectm.size();i++){
				plateauJeu.getListJoueur().get(id).addIteneraire(iteSelectm.get(i));
			}
		}
		
		plateauView.putDestinationInMainOfPlayer(destSelect, iteSelectm);
		plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
		plateauView.pionChoix();
		
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
			if(json.has("destination")||json.has("iteneraire")){
				int i = 0;
				if(initGame){
					plateauView.printMsgDestination("Veuillez sélectionner au moins 3 carte");
				}else{
					plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
				}
				try {
					if(json.has("destination")){
						jsonA = json.getJSONArray("destination");
						for(i=0;i<jsonA.length();i++){
							plateauView.setCardsDestinationForChoice(gson.fromJson(jsonA.get(i).toString(), Destination.class));
						}
					}
					if(json.has("iteneraire")){
						jsonA = json.getJSONArray("iteneraire");
						for(i=0;i<jsonA.length();i++){
							plateauView.setCardsIteneraireForChoice(gson.fromJson(jsonA.get(i).toString(), Iteneraire.class));
						}
					}
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
