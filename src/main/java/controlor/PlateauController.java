package controlor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.google.gson.reflect.TypeToken;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.scene.control.Label;
import metier.Boat;
import metier.Carte;
import metier.Destination;
import metier.Iteneraire;
import metier.PlateauJeu;
import metier.RouteMartime;
import metier.RouteTerrestre;
import metier.Ville;
import metier.Wagon;
import server.Client;
import server.Server;
import server.Server.MyThreadHandler;
import vue.Plateau;

public class PlateauController extends Thread {

	private Server server = null;
	private Client client = null;
	private Map<Integer, MyThreadHandler> listClientsServer = new HashMap<Integer, MyThreadHandler>();
	private int id;
	private Plateau plateauView;
	private PlateauJeu plateauJeu = new PlateauJeu(5);

	private boolean initGame = true;
	private boolean tour = false;
	private int nbCartes = 0;
	private boolean carteTransport = false;
	private boolean routePort = false;
	private boolean carteDestination = false;
	private boolean choixCarteDestination = false;

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

	public int getIdPlayer() {
		return this.id;
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

	public void sendFirstCards() {
		JSONObject json;
		JSONArray jsonA;
		Gson gson;
		int i = 0;
		Set cles = listClientsServer.keySet();
		Iterator it = cles.iterator();

		ArrayList<Wagon> pWagon = new ArrayList<Wagon>();
		ArrayList<Boat> pBoat = new ArrayList<Boat>();
		Wagon w1 = plateauJeu.getPaquet().piocheWagon();
		Wagon w2 = plateauJeu.getPaquet().piocheWagon();
		Wagon w3 = plateauJeu.getPaquet().piocheWagon();
		pWagon.add(w1);
		pWagon.add(w2);
		pWagon.add(w3);
		plateauView.setCardWagonInWagonDiscover1(w1);
		plateauView.setCardWagonInWagonDiscover2(w2);
		plateauView.setCardWagonInWagonDiscover3(w3);

		Boat b1 = plateauJeu.getPaquet().piocheBoat();
		Boat b2 = plateauJeu.getPaquet().piocheBoat();
		Boat b3 = plateauJeu.getPaquet().piocheBoat();
		pBoat.add(b1);
		pBoat.add(b2);
		pBoat.add(b3);
		plateauView.setCardBoatInBoatDiscover1(b1);
		plateauView.setCardBoatInBoatDiscover2(b2);
		plateauView.setCardBoatInBoatDiscover3(b3);

		while (it.hasNext()) {
			int cle = (int) it.next();

			json = new JSONObject();
			jsonA = new JSONArray();
			gson = new Gson();
			try {
				json.put("visibleWagon", gson.toJson(pWagon));
				json.put("visibleBoat", gson.toJson(pBoat));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (i = 0; i < 3; i++) {
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
			for (i = 0; i < 7; i++) {
				Boat b = plateauJeu.getPaquet().piocheBoat();
				System.out.println(plateauJeu.getPaquet().getpBoat().size());
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
			for (i = 0; i < 5; i++) {
				Object o = plateauJeu.getPaquet().piocheDesination();
				Carte c = (Carte) o;
				if (c.getName().equals(EnumCarte.DESTINATION)) {
					Destination d = (Destination) o;
					jsonAD.put(gson.toJson(d));
				} else {
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
		for (i = 0; i < 3; i++) {
			plateauView.setCardsWagonInMainOfPlayer((plateauJeu.getPaquet().piocheWagon()));
		}
		for (i = 0; i < 7; i++) {
			plateauView.setCardsBoatInMainOfPlayer((plateauJeu.getPaquet().piocheBoat()));
			System.out.println(plateauJeu.getPaquet().getpBoat().size());
		}
		plateauView.printMsgDestination("Veuillez choisir au moins 3 cartes");
		for (i = 0; i < 5; i++) {
			Object o = plateauJeu.getPaquet().piocheDesination();
			Carte c = (Carte) o;
			if (c.getName().equals(EnumCarte.DESTINATION)) {
				Destination d = (Destination) o;
				plateauView.setCardsDestinationForChoice(d);
			} else {
				Iteneraire ite = (Iteneraire) o;
				plateauView.setCardsIteneraireForChoice(ite);
			}

		}

		server.setPlateauControllerAtThread(listClientsServer, this);

	}
	
	public boolean allowJoker(){
		if (nbCartes >=1){
			plateauView.printNotification("Vous avez déjà pris une carte !");
			return false;
		}
		return true;
	}

	public void deleteCardWagon1(Wagon w) {
		if (client != null) {
			JSONObject json = new JSONObject();
			JSONArray jsonA = new JSONArray();
			plateauView.setCardsWagonInMainOfPlayer(w);
			plateauJeu.getJoueur(id).addWagon(w);
			try {
				json.put("id", id);
				json.put("wagonDelete1", 1);
				try {
					client.sendJSON(json);
					getJsonFromServer(client.receiveJSON());
					//plateauView.deleteCardWagon1();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(w.isJoker() == true){
				choixCartes("joker");
			} else {
				choixCartes("wagon");
			}
		} else {
			JSONObject json = new JSONObject();
			JSONArray jsonA = new JSONArray();
			plateauView.setCardsWagonInMainOfPlayer(w);
			plateauJeu.getJoueur(id).addWagon(w);
			plateauView.deleteCardWagon1();
			try {
				json.put("wagonDelete1", 1);
				server.broadcast(listClientsServer, json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(w.isJoker() == true){
				choixCartes("joker");
			} else {
				choixCartes("wagon");
			}
		}
		if (checkEndOfTurn()) {
			endTurn();
		}
	}

	public void cardWagon1() {
		JSONObject json;
		JSONArray jsonA;
		Gson gson;
		if (client != null) {
			json = new JSONObject();
			jsonA = new JSONArray();
			gson = new Gson();
			ArrayList<Wagon> pWagon = new ArrayList<Wagon>();
			Wagon w1 = plateauJeu.getPaquet().piocheWagon();
			pWagon.add(w1);
			// plateauView.setCardWagonInWagonDiscover1(w1);
			try {
				json.put("visibleWagon1", gson.toJson(pWagon));
				json.put("id", id);
				try {
					client.sendJSON(json);
					getJsonFromServer(client.receiveJSON());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			json = new JSONObject();
			jsonA = new JSONArray();
			gson = new Gson();
			ArrayList<Wagon> pWagon = new ArrayList<Wagon>();
			Wagon w1 = plateauJeu.getPaquet().piocheWagon();
			pWagon.add(w1);
			plateauView.setCardWagonInWagonDiscover1(w1);
			try {
				json.put("visibleWagon1", gson.toJson(pWagon));
				try {
					server.broadcast(listClientsServer, json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void clientDeconnecter(int no) {
		if (server != null) {
			plateauJeu.getJoueur(no).setStart(false);
			if (plateauJeu.getNbPlayerActif() <= 1) {
				server.closeServer();
			}
		} else {
			client.deconnection();
		}
	}

	/**
	 * On appelle cette fonction quand le serveur reçoit les paquets du client
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public synchronized JSONObject getJSONFromClient(JSONObject json) throws IOException {
		Gson gson = new Gson();
		JSONArray jsonA = null;
		if (json == null) {

		} else if (json.has("checkPionRoad")) {
			try {
				int no = json.getInt("id");
				int wagon = json.getInt("wagon");
				int boat = json.getInt("boat");
				json = new JSONObject();
				json.put("checkPionRoad", plateauJeu.getJoueur(no).getPions().checkIfEnoughPion(wagon, boat));
				return json;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (json.has("pioche")) {
			try {
				int no = json.getInt("id");
				String pioche = json.getString("pioche");
				json = new JSONObject();
				JSONObject json2 = new JSONObject();
				String msg = "";
				jsonA = new JSONArray();
				switch (pioche) {
				case "bateau":
					Boat b = plateauJeu.getPaquet().piocheBoat();
					if (b != null) {
						plateauJeu.getJoueur(no).addBoat(b);
						json.put("bateau", jsonA.put(gson.toJson(b)));
						json.put("msg", "Vous avez piocher une carte bateau");
						msg = "Le joueur " + plateauJeu.getJoueur(no).getName() + " a pioché une carte bateau";
					} else {
						json.put("error", "La pioche bateau est vide");
					}
					break;
				case "wagon":
					Wagon w = plateauJeu.getPaquet().piocheWagon();
					if (w != null) {
						plateauJeu.getJoueur(no).addWagon(w);
						json.put("wagon", jsonA.put(gson.toJson(w)));
						json.put("msg", "Vous avez piocher une carte wagon");
						msg = "Le joueur " + plateauJeu.getJoueur(no).getName() + " a pioché une carte wagon";
					} else {
						json.put("error", "La pioche wagon est vide");
					}
					break;
				case "destination":
					JSONArray jsonAD = new JSONArray();
					JSONArray jsonAI = new JSONArray();
					int i;
					for (i = 0; i < 4; i++) {
						Object o = plateauJeu.getPaquet().piocheDesination();
						Carte c = (Carte) o;
						if (c != null) {
							if (c.getName().equals(EnumCarte.DESTINATION)) {
								Destination d = (Destination) o;
								jsonAD.put(gson.toJson(d));
							} else {
								Iteneraire ite = (Iteneraire) o;
								jsonAI.put(gson.toJson(ite));
							}
						} else {
							json.put("error", "La pioche destination est vide");
						}
					}
					json.put("msg", "Vous avez piocher une carte bateau");
					msg = "Le joueur " + plateauJeu.getJoueur(no).getName() + " a pioché une carte destination";
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

				json2.put("msg", msg);
				plateauView.printNotification(msg);
				server.broadcastExceptOne(listClientsServer, json2, no);

				return json;
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
		} else if (json.has("route")) {
			try {
				RouteTerrestre rt = null;
				RouteMartime rm = null;
				Ville port = null;
				if (json.has("routeTerrestre")) {
					rt = gson.fromJson((String) json.get("routeTerrestre"), RouteTerrestre.class);
				} else if (json.has("routeMaritime")) {
					rm = gson.fromJson((String) json.get("routeMaritime"), RouteMartime.class);
				} else if (json.has("port")) {
					port = gson.fromJson((String) json.get("port"), Ville.class);
				} else {
					json = new JSONObject();
					json.put("error", "Pas de route saisie");
					return json;
				}
				int no = json.getInt("id");
				String fxId = json.getString("fxId");
				json = new JSONObject();

				int longueurRoad = 0;
				String msg = "";
				if (rt != null) {
					if (checkTakeRoadWagon(rt, no)) {
						json.put("error", "La route a déjà été prise");
						return json;
					}
					msg = "La route " + rt.getV1().getName() + "-" + rt.getV2().getName() + " est prise par "
							+ plateauJeu.getJoueur(no).getName();
					plateauJeu.getJoueur(no).getPions().addRouteTerrestre(rt);
					longueurRoad = rt.getNbPion();
				} else if (rm != null) {
					if (checkTakeRoadBoat(rm, no)) {
						json.put("error", "La route a déjà été prise");
						return json;
					}
					msg = "La route " + rm.getV1().getName() + "-" + rm.getV2().getName() + " est prise par "
							+ plateauJeu.getJoueur(no).getName();
					plateauJeu.getJoueur(no).getPions().addRouteMaritime(rm);
					longueurRoad = rm.getNbPion();
				} else if (port != null) {
					if (checkTakePort(port)) {
						json.put("error", "Le port a déjà été pris");
						return json;
					}
					if (!plateauJeu.getJoueur(no).getPions().checkCityIsConnectedToRoad(port)) {
						json.put("error", "La ville n'est pas connectée à une de vos route");
						return json;
					}
					msg = "Le port de " + port.getName() + " est pris par " + plateauJeu.getJoueur(no).getName();
					plateauJeu.getJoueur(no).getPions().addPort(port);
				} else {
					json.put("error", "Erreur de traitement. Veuillez recommencer");
					return json;
				}
				jsonA = new JSONArray();
				json.put("routePrise", true);
				json.put("msg", msg);
				json.put("id", no);
				json.put("couleur", gson.toJson(plateauJeu.getJoueur(no).getCouleur()));
				plateauJeu.getJoueur(no).calculScoreTakeRoad(longueurRoad);
				int score = plateauJeu.getJoueur(no).getScore();
				json.put("score", score);
				json.put("fxId", fxId);
				// envoyer à tout le monde sauf à celui qui a pris la route
				server.broadcastExceptOne(listClientsServer, json, no);
				plateauView.printNotification(msg);
				List<String> listFxID = gson.fromJson(fxId, ArrayList.class);
				plateauView.colorRoadOrPort(plateauJeu.getJoueur(no).getCouleur(), listFxID);
				plateauView.setListJoueurAtScoreView(plateauJeu.getListJoueur());
				return json;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (json.has("defausse")) {
			int i;
			int no;

			if (json.has("defausseDestination")) {
				try {
					jsonA = json.getJSONArray("defausseDestination");
					for (i = 0; i < jsonA.length(); i++) {
						Destination d = gson.fromJson(jsonA.get(i).toString(), Destination.class);
						plateauJeu.getPaquet().addDestinationDefausse((d));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (json.has("defausseIteneraire")) {
				try {
					jsonA = json.getJSONArray("defausseIteneraire");
					for (i = 0; i < jsonA.length(); i++) {
						Iteneraire ite = gson.fromJson(jsonA.get(i).toString(), Iteneraire.class);
						plateauJeu.getPaquet().addDestinationDefausse((ite));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("selectionDestination")) {
				try {
					jsonA = json.getJSONArray("selectionDestination");
					no = json.getInt("id");
					for (i = 0; i < jsonA.length(); i++) {
						plateauJeu.getListJoueur().get(no)
								.addDestination((gson.fromJson(jsonA.get(i).toString(), Destination.class)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("selectionIteneraire")) {
				try {
					jsonA = json.getJSONArray("selectionDestination");
					no = json.getInt("id");
					for (i = 0; i < jsonA.length(); i++) {
						plateauJeu.getListJoueur().get(no)
								.addIteneraire((gson.fromJson(jsonA.get(i).toString(), Iteneraire.class)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (json.has("defausseWagon") || json.has("defausseBoat")) {
				List<Wagon> listW = null;
				List<Boat> listB = null;
				try {
					if (json.has("defausseWagon")) {
						String jsonTxt = (String) json.get("defausseWagon");
						Wagon[] mcArray = gson.fromJson(jsonTxt, Wagon[].class);
						listW = new ArrayList<Wagon>(Arrays.asList(mcArray));
					}
					if (json.has("defausseBoat")) {
						String jsonTxt = (String) json.get("defausseBoat");
						Boat[] mcArray = gson.fromJson(jsonTxt, Boat[].class);
						listB = new ArrayList<Boat>(Arrays.asList(mcArray));
					}

					no = json.getInt("id");

					traitementDiscardingWagonBoat(listW, listB, no);

					int wagon = plateauJeu.getJoueur(no).getPions().getNbWagon();
					int boat = plateauJeu.getJoueur(no).getPions().getNbBoat();
					int port = plateauJeu.getJoueur(no).getPions().getNbPort();
					json = new JSONObject();
					json.put("pion", true);
					json.put("Pwagon", wagon);
					json.put("Pboat", boat);
					json.put("Pport", port);
					return json;
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

		} else if (json.has("pionBateau") || json.has("pionWagon")) {
			try {
				int no = json.getInt("id");
				int boat = json.getInt("pionBateau");
				int wagon = json.getInt("pionWagon");
				plateauJeu.getListJoueur().get(no).getPions().setNbBoat(boat);
				plateauJeu.getListJoueur().get(no).getPions().setNbWagon(wagon);
				json = new JSONObject();
				json.put("pion", true);
				plateauJeu.getListJoueur().get(no).setStart(true);
				if (plateauJeu.checkIfAllPlayerAreReady()) {
					this.endTurn();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (json.has("finTour")) {
			endTurn();
			return null;
		} else if (json.has("wagonDelete1")) {
			try {
				int no = json.getInt("id");
				json = new JSONObject();
				json.put("wagonDelete1", 1);
				plateauView.deleteCardWagon1();
				server.broadcastExceptOne(listClientsServer, json, no);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (json.has("visibleWagon1")) {
			try {
				int no = json.getInt("id");
				gson = new Gson();
				String visibleWagon1 = json.getString("visibleWagon1");
				Type type = new TypeToken<List<Wagon>>() {
				}.getType();
				List<Wagon> listFxId = gson.fromJson(visibleWagon1, type);
				plateauView.setCardWagonInWagonDiscover1(listFxId.get(0));
				// json = new JSONObject();
				// json.put("visibleWagon1", listFxId.get(0));
				server.broadcastExceptOne(listClientsServer, json, no);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return json;
	}

	/**
	 * Vérifie si le joueur peut effectuer une action
	 * 
	 * @param card
	 *            : nom d'une carte, la valeur peut être nulle
	 * @return true si il peut continuer, false sinon.
	 */
	public boolean checkAction(String card) {
		if (card == null) {
			card = "";
		}
		System.out.println(tour);
		System.out.println(carteDestination);
		System.out.println(carteTransport);
		if (!tour) {
			plateauView.printNotification("Ce n'est pas votre tour");
			return false;
		}
		if (carteDestination) {
			plateauView.printNotification("Vous avez déjà piochée une carte destination");
			return false;
		}
		if (carteTransport) {
			if (card.equals("wagon") || card.equals("boat") || card.equals("bateau")) {
				if (nbCartes >= 2) {
					plateauView.printNotification("Vous avez déjà piochée deux cartes de transport");
					return false;
				} else {
					return true;
				}
			} else {
				plateauView.printNotification("Vous avez déjà piochée une carte transport");
				return false;
			}

		}
		if (routePort) {
			plateauView.printNotification("Vous avez déjà pris une route");
			return false;
		}
		return true;
	}

	public void piocheCards(String card) {
		if (checkAction(card)) {

			if (server == null) {

				JSONObject json = new JSONObject();
				try {
					json.put("pioche", card);
					json.put("id", id);
					client.sendJSON(json);
					waitServerMsg();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				choixCartes(card);
				String msg = "";
				switch (card) {
				case "wagon":
					Wagon w = plateauJeu.getPaquet().piocheWagon();
					if (w != null) {
						plateauView.setCardsWagonInMainOfPlayer(w);
						plateauJeu.getJoueur(id).addWagon(w);
						plateauView.printNotification("Vous avez pris une carte wagon");
						msg = "Le joueur " + plateauJeu.getJoueur(id).getName() + " a pris une carte wagon";
					} else {
						plateauView.printNotification("La pioche wagon est vide");
					}
					break;
				case "bateau":
					Boat b = plateauJeu.getPaquet().piocheBoat();
					if (b != null) {
						plateauView.setCardsBoatInMainOfPlayer(b);
						plateauJeu.getJoueur(id).addBoat(b);
						plateauView.printNotification("Vous avez pris une carte bateau");
						msg = "Le joueur " + plateauJeu.getJoueur(id).getName() + " a pris une carte bateau";
					} else {
						plateauView.printNotification("La pioche bateau est vide");
					}
					break;
				case "destination":
					int i;
					for (i = 0; i < 4; i++) {
						Object o = plateauJeu.getPaquet().piocheDesination();
						Carte c = (Carte) o;
						if (c != null) {
							if (c.getName().equals(EnumCarte.DESTINATION)) {
								Destination d = (Destination) o;
								plateauView.setCardsDestinationForChoice(d);
							} else {
								Iteneraire ite = (Iteneraire) o;
								plateauView.setCardsIteneraireForChoice(ite);
							}
							plateauView.printNotification(
									"Vous avez choisi de prendre une ou plusieurs cartes destination");
							msg = "Le joueur " + plateauJeu.getJoueur(id).getName() + " a pris une carte destination";
						} else {
							plateauView.printNotification("La pioche destination est vide");
						}

					}
					break;
				default:
					plateauView.printNotification("Auncune carte demandé. Veuillez recommencer");
					break;
				}
				if (!msg.equals("")) {
					JSONObject json = new JSONObject();
					try {
						json.put("msg", msg);
						server.broadcast(listClientsServer, json);
					} catch (JSONException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			if (checkEndOfTurn()) {
				endTurn();
			}
		} else {
			if (checkEndOfTurn()) {
				endTurn();
			}
		}

	}

	public void setPion(int wagon, int boat) {
		if (server == null) {
			JSONObject json = new JSONObject();
			try {
				json.put("pionBateau", boat);
				json.put("pionWagon", wagon);
				json.put("id", id);
				client.sendJSON(json);
				json = client.receiveJSON();
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waitStartGame();
		} else {
			plateauJeu.getListJoueur().get(id).getPions().setNbBoat(boat);
			plateauJeu.getListJoueur().get(id).getPions().setNbWagon(wagon);
			plateauJeu.getListJoueur().get(id).setStart(true);

			waitStartGame();

			/*
			 * SaveJsonVisitor sv = new SaveJsonVisitor();
			 * plateauJeu.accept(sv);
			 */

		}

	}

	public void waitStartGame() {
		initGame = false;
		if (server != null) {

		} else {
			client.timer();
		}

	}

	public void beginTurn() {
		tour = true;
		carteDestination = false;
		carteTransport = false;
		choixCarteDestination = false;
		nbCartes = 0;
		routePort = false;
		plateauView.printMsgGame("C'est à votre tour");
	}

	public boolean checkEndOfTurn() {
		if (choixCarteDestination || routePort || (carteTransport && nbCartes >= 2)) {
			tour = false;
			return true;
		}
		return false;
	}

	public void endTurn() {
		plateauView.printMsgGame("Votre tour est terminé");
		if (server != null) {
			plateauJeu.endOfPlayerTurn();
			int no = plateauJeu.whoIsNext();
			String notification = "";
			boolean endGame = false;
			/*
			 * if(plateauJeu.checkIfGameWillBeEnd()){ if(!plateauJeu.endGame()){
			 * notification="Il reste moins de 2 tours"; }else{
			 * notification="Le jeu est terminé"; endGame=true; }
			 * plateauView.printNotification(notification); }
			 */
			if (endGame) {
				JSONObject json = new JSONObject();
				try {
					json.put("fin", true);
					try {
						server.broadcast(listClientsServer, json);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (no == id) {
					beginTurn();
				} else {
					plateauView.printMsgGame("C'est au tour du joueur " + plateauJeu.getJoueur(no).getName() + " !");
				}
				JSONObject json = new JSONObject();
				try {
					json.put("tour", no);
					if (!notification.equals("")) {
						json.put("msg", notification);
					}
					Platform.runLater(() -> {
						try {
							server.broadcast(listClientsServer, json);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			JSONObject json = new JSONObject();
			try {
				json.put("finTour", true);
				System.out.println("client " + json.toString());
				client.sendJSON(json);

			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.timer();
		}
	}

	public void choixCartes(String card) {
		if (!carteTransport && !carteDestination) {
			switch (card) {
			case "wagon":
				carteTransport = true;
				break;
			case "boat":
			case "bateau":
				carteTransport = true;
				break;
			case "destination":
				carteDestination = true;
				break;
			case "joker":
				nbCartes = nbCartes + 2;
				carteTransport = true;
			}
		}
		if (carteTransport) {
			nbCartes++;
		}
	}

	public void traitementDiscardingWagonBoat(List<Wagon> listW, List<Boat> listB, int no) {
		int i;
		int doble = 0;
		for (i = 0; i < listW.size(); i++) {
			plateauJeu.getPaquet().addWagonDefausse(listW.get(i));
			plateauJeu.getJoueur(no).deleteWagon(listW.get(i));
		}
		for (i = 0; i < listB.size(); i++) {
			plateauJeu.getPaquet().addBoatDefausse(listB.get(i));
			plateauJeu.getJoueur(no).deleteBoat(listB.get(i));
			if (listB.get(i).isDoubleBoat()) {
				doble++;
			}
		}
		plateauJeu.getJoueur(no).getPions().lessWagonBoat(listB.size() + doble, listW.size());
	}

	public void discardingWagonBoat(List<Wagon> listW, List<Boat> listB) {
		if (server != null) {
			int i;
			int doble = 0;
			if (server != null) {
				traitementDiscardingWagonBoat(listW, listB, id);

				plateauView.printPion(plateauJeu.getJoueur(id).getPions().getNbWagon(),
						plateauJeu.getJoueur(id).getPions().getNbBoat(),
						plateauJeu.getJoueur(id).getPions().getNbPort());
			}
		} else {
			JSONObject json = new JSONObject();
			Gson gson = new Gson();
			try {
				json.put("id", id);
				json.put("defausse", true);
				if (listW != null) {
					json.put("defausseWagon", gson.toJson(listW));
				}
				if (listB != null) {
					json.put("defausseBoat", gson.toJson(listB));
				}
				System.out.println(json.toString());
				client.sendJSON(json);
				json = client.receiveJSON();
				System.out.println(json.toString());
				if (json.has("pion")) {
					try {
						int wagon = json.getInt("Pwagon");
						int boat = json.getInt("Pboat");
						int port = json.getInt("Pport");

						plateauView.printPion(wagon, boat, port);
						routePort = true;

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (json.has("error")) {
					try {
						String msgError = json.getString("error");
						plateauView.printNotification(msgError);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (checkEndOfTurn()) {
			endTurn();
		}

	}

	/**
	 * Prendre une route ou construire un port
	 * 
	 * @param rt
	 * @param rm
	 * @param port
	 * @param listFxId
	 * @return
	 */
	public boolean takeRoadWagonOrBoatOrPort(RouteTerrestre rt, RouteMartime rm, Ville port, List<String> listFxId) {
		if (!routePort && !carteDestination && !carteTransport && !choixCarteDestination) {
			if (client != null) {
				JSONObject json = new JSONObject();
				Gson gson = new Gson();
				try {
					json.put("route", true);
					if (rt != null) {
						json.put("routeTerrestre", gson.toJson(rt));
					} else if (rm != null) {
						json.put("routeMaritime", gson.toJson(rm));
					} else if (port != null) {
						json.put("port", gson.toJson(port));
					} else {
						return false;
					}
					json.put("id", id);
					json.put("fxId", gson.toJson(listFxId));
					client.sendJSON(json);
					json = client.receiveJSON();
					if (json.has("routePrise")) {
						int score = json.getInt("score");
						routePort = true;
						plateauJeu.getJoueur(id).setScore(score);
						plateauView.printScore(score);
						plateauView.printNotification("Vous avez pris la route");
						plateauView.colorRoadOrPort(plateauJeu.getJoueur(id).getCouleur(), listFxId);
						plateauView.setListJoueurAtScoreView(plateauJeu.getListJoueur());
						return true;
					} else {
						plateauView.printNotification(json.getString("error"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					int longueurRoad = 0;
					String msg = "";
					if (rt != null) {
						if (checkTakeRoadWagon(rt, id)) {
							plateauView.printNotification("La route a déjà été prise");
							return false;
						}
						msg = "La route " + rt.getV1().getName() + "-" + rt.getV2().getName() + " est prise par "
								+ plateauJeu.getJoueur(id).getName();
						plateauJeu.getJoueur(0).getPions().addRouteTerrestre(rt);
						longueurRoad = rt.getNbPion();
					} else if (rm != null) {
						if (checkTakeRoadBoat(rm, id)) {
							plateauView.printNotification("La route a déjà été prise");
							return false;
						}
						msg = "La route " + rm.getV1().getName() + "-" + rm.getV2().getName() + " est prise par "
								+ plateauJeu.getJoueur(id).getName();
						plateauJeu.getJoueur(0).getPions().addRouteMaritime(rm);
						longueurRoad = rm.getNbPion();
					} else if (port != null) {
						if (checkTakePort(port)) {
							plateauView.printNotification("Le port a déjà été pris");
							return false;
						}
						if (!plateauJeu.getJoueur(id).getPions().checkCityIsConnectedToRoad(port)) {
							plateauView.printNotification("La ville n'est pas connectée à une de vos route");
							return false;
						}
						msg = "Le port de " + port.getName() + " est pris par " + plateauJeu.getJoueur(id).getName();
						plateauJeu.getJoueur(0).getPions().addPort(port);
					} else {
						plateauView.printNotification("Erreur de traitement. Veuillez recommencer");
						return false;
					}
					JSONObject json = new JSONObject();
					JSONArray jsonA = new JSONArray();
					Gson gson = new Gson();
					json.put("routePrise", true);
					json.put("msg", msg);
					json.put("id", id);
					json.put("couleur", gson.toJson(plateauJeu.getJoueur(id).getCouleur()));
					plateauJeu.getJoueur(id).calculScoreTakeRoad(longueurRoad);
					int score = plateauJeu.getJoueur(id).getScore();
					plateauView.printScore(score);
					json.put("score", score);
					json.put("fxId", gson.toJson(listFxId));
					server.broadcast(listClientsServer, json);
					plateauView.printNotification("Vous avez pris la route");
					plateauView.colorRoadOrPort(plateauJeu.getJoueur(id).getCouleur(), listFxId);
					plateauView.setListJoueurAtScoreView(plateauJeu.getListJoueur());
					routePort = true;
					return true;
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			endTurn();
		}
		return false;
	}

	/**
	 * On vérifie si le joueur a assez de pion pour prendre uen route ou
	 * construire un port
	 * 
	 * @param wagon
	 * @param boat
	 * @param no
	 *            : id du joueur
	 * @return
	 */
	public boolean checkIfEnoughPion(int wagon, int boat, int no) {
		if (tour && !routePort) {
			if (server != null) {
				return plateauJeu.getJoueur(no).getPions().checkIfEnoughPion(wagon, boat);
			} else {
				JSONObject json = new JSONObject();
				try {
					json.put("checkPionRoad", true);
					json.put("wagon", wagon);
					json.put("boat", boat);
					json.put("id", no);
					client.sendJSON(json);
					json = client.receiveJSON();
					if (json.has("checkPionRoad")) {
						if (json.getBoolean("checkPionRoad")) {
							return true;
						}
					}
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return false;
	}

	/**
	 * On vérifie si la route n'a pas déjà été prise
	 * 
	 * @param r
	 * @return
	 */
	public boolean checkTakeRoadWagon(RouteTerrestre r, int no) {
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()) {
			int cle = (int) it.next();
			if (plateauJeu.getListJoueur().get(cle).checkIfHimTakeRoadWagon(r)) {
				return true;
			}
		}
		if (plateauJeu.getJoueur(no).getPions().checkIfRoadWagonDouble(r)) {
			return true;
		}
		return false;
	}

	/**
	 * On vérifie si la route n'a pas déjà été prise
	 * 
	 * @param r
	 * @return
	 */
	public boolean checkTakeRoadBoat(RouteMartime r, int no) {
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()) {
			int cle = (int) it.next();
			if (plateauJeu.getListJoueur().get(cle).checkIfHimTakeRoadBoat(r)) {
				return true;
			}
		}
		if (plateauJeu.getJoueur(no).getPions().checkIfRoadBoatDouble(r)) {
			return true;
		}
		return false;
	}

	/**
	 * On vérifie si le port n'a pas déjà été construit
	 * 
	 * @param v
	 * @return
	 */
	public boolean checkTakePort(Ville v) {
		Set cles = plateauJeu.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()) {
			int cle = (int) it.next();
			if (plateauJeu.getListJoueur().get(cle).checkIfHimTakePort(v)) {
				return true;
			}
		}
		return false;
	}

	public void takeCardsDestination(List<Destination> destSelect, List<Destination> destNoSelect,
			List<Iteneraire> iteSelectm, List<Iteneraire> iteNoSelect) {
		if (initGame) {
			if ((destSelect.size() + iteSelectm.size()) >= 3) {
				traitementCartesDestination(destSelect, destNoSelect, iteSelectm, iteNoSelect);
			} else {
				// dire qu'il y a pas assez de cartes selectionné
				plateauView.printMsgDestination("Veuillez sélectionner au moins 3 carte");
			}
		} else if ((destSelect.size() + iteSelectm.size()) >= 1) {
			traitementCartesDestination(destSelect, destNoSelect, iteSelectm, iteNoSelect);
			choixCarteDestination = true;
			if (checkEndOfTurn()) {
				endTurn();
			}
		} else {
			plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
		}
	}

	private void traitementCartesDestination(List<Destination> destSelect, List<Destination> destNoSelect,
			List<Iteneraire> iteSelectm, List<Iteneraire> iteNoSelect) {
		int i;
		if (server == null) {
			JSONArray jsonAD = new JSONArray();
			JSONArray jsonAI = new JSONArray();
			JSONObject json = new JSONObject();
			Gson gson = new Gson();
			for (i = 0; i < destNoSelect.size(); i++) {
				jsonAD.put(gson.toJson(destNoSelect.get(i)));
			}
			for (i = 0; i < iteNoSelect.size(); i++) {
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
			for (i = 0; i < destSelect.size(); i++) {
				jsonAD.put(gson.toJson(destSelect.get(i)));
			}
			for (i = 0; i < iteSelectm.size(); i++) {
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
		} else {
			for (i = 0; i < destNoSelect.size(); i++) {
				plateauJeu.getPaquet().addDestinationDefausse(destNoSelect.get(i));
			}
			for (i = 0; i < iteNoSelect.size(); i++) {
				plateauJeu.getPaquet().addDestinationDefausse(iteNoSelect.get(i));
			}
			for (i = 0; i < destSelect.size(); i++) {
				plateauJeu.getListJoueur().get(id).addDestination(destSelect.get(i));
			}
			for (i = 0; i < iteSelectm.size(); i++) {
				plateauJeu.getListJoueur().get(id).addIteneraire(iteSelectm.get(i));
			}
		}

		plateauView.putDestinationInMainOfPlayer(destSelect, iteSelectm);
		plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
		if (initGame) {
			plateauView.pionChoix();
		}
	}

	/**
	 * Donnée reçu par le client depuis le serveur
	 * 
	 * @param json
	 */
	public void getJsonFromServer(JSONObject json) {
		if (json.has("tour")) {
			int no = 0;
			try {
				no = json.getInt("tour");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (no == id) {
				beginTurn();
				client.setTurn(true);
				plateauView.printMsgGame("C'est à votre tour !");
			} else {
				client.setTurn(false);
				plateauView.printMsgGame("C'est au tour du joueur " + plateauJeu.getJoueur(no).getName() + " !");
			}
		}
		if (json.has("routePrise")) {
			int no;
			Gson gson = new Gson();
			try {
				no = json.getInt("id");
				String couleur = json.getString("couleur");
				EnumCouleur color = gson.fromJson(couleur, EnumCouleur.class);
				int score = json.getInt("score");
				plateauJeu.getJoueur(no).setScore(score);
				String list = (String) json.get("fxId");
				List<String> listFxId = gson.fromJson(list, ArrayList.class);

				plateauView.colorRoadOrPort(color, listFxId);
				plateauView.setListJoueurAtScoreView(plateauJeu.getListJoueur());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (json.has("msg")) {
			try {
				String msg = json.getString("msg");
				plateauView.printNotification(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (json.has("wagonDelete1")) {
			plateauView.deleteCardWagon1();
		}
		if (json.has("visibleWagon1")) {
			try {
				Gson gson = new Gson();
				String visibleWagon1 = json.getString("visibleWagon1");
				Type type = new TypeToken<List<Wagon>>() {
				}.getType();
				List<Wagon> listFxId = gson.fromJson(visibleWagon1, type);
				plateauView.setCardWagonInWagonDiscover1(listFxId.get(0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("poubelle " + json.toString());
	}

	public void waitServerMsg() {
		JSONObject json = null;
		JSONArray jsonA = null;
		Gson gson = new Gson();
		if (client == null) {
			System.out.println("cette valeur est nulle");
		}
		try {
			json = client.receiveJSON();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json != null) {
			if (json.has("wagon")) {
				int i = 0;
				try {
					jsonA = json.getJSONArray("wagon");
					for (i = 0; i < jsonA.length(); i++) {
						plateauView.setCardsWagonInMainOfPlayer(gson.fromJson(jsonA.get(i).toString(), Wagon.class));
					}
					choixCartes("wagon");
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("bateau")) {
				int i = 0;
				try {
					jsonA = json.getJSONArray("bateau");
					for (i = 0; i < jsonA.length(); i++) {
						plateauView.setCardsBoatInMainOfPlayer(gson.fromJson(jsonA.get(i).toString(), Boat.class));
					}
					choixCartes("boat");
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("destination") || json.has("iteneraire")) {
				int i = 0;
				if (initGame) {
					plateauView.printMsgDestination("Veuillez sélectionner au moins 3 carte");
				} else {
					plateauView.printMsgDestination("Veuillez sélectionner au moins 1 carte");
					choixCartes("destination");
				}
				try {
					if (json.has("destination")) {
						jsonA = json.getJSONArray("destination");
						System.out.println("destinationRecuCarte");
						for (i = 0; i < jsonA.length(); i++) {
							plateauView.setCardsDestinationForChoice(
									gson.fromJson(jsonA.get(i).toString(), Destination.class));
						}
					}
					if (json.has("iteneraire")) {
						jsonA = json.getJSONArray("iteneraire");
						for (i = 0; i < jsonA.length(); i++) {
							plateauView.setCardsIteneraireForChoice(
									gson.fromJson(jsonA.get(i).toString(), Iteneraire.class));
						}
					}
				} catch (JsonSyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("error")) {
				try {
					String msgError = json.getString("error");
					plateauView.printNotification(msgError);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("msg")) {
				try {
					String msg = json.getString("msg");
					plateauView.printNotification(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("visibleWagon")) {
				try {
					String visibleWagon = json.getString("visibleWagon");
					Type type = new TypeToken<List<Wagon>>() {
					}.getType();
					List<Wagon> listFxId = gson.fromJson(visibleWagon, type);
					int i;
					plateauView.setCardWagonInWagonDiscover1(listFxId.get(0));
					plateauView.setCardWagonInWagonDiscover2(listFxId.get(1));
					plateauView.setCardWagonInWagonDiscover3(listFxId.get(2));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (json.has("visibleBoat")) {
				try {
					String visibleBoat = json.getString("visibleBoat");
					Type type = new TypeToken<List<Boat>>() {
					}.getType();
					List<Boat> listFxId = gson.fromJson(visibleBoat, type);
					int i;
					plateauView.setCardBoatInBoatDiscover1(listFxId.get(0));
					plateauView.setCardBoatInBoatDiscover2(listFxId.get(1));
					plateauView.setCardBoatInBoatDiscover3(listFxId.get(2));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
