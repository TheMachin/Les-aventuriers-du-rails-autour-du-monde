package controlor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import ennumeration.EnumCouleur;
import metier.Joueur;
import server.Client;
import server.Server;
import server.Server.MyThreadHandler;
import vue.Menu;
import vue.Plateau;

public class MenuController {

	private Menu menuView;
	private Server server=null;
	private Client client=null;
	private Map<Integer,MyThreadHandler> listClientsServer = new HashMap<Integer,MyThreadHandler>();
	private Map<Integer,Joueur> joueurs = new HashMap<Integer,Joueur>();
	private int id;
	private Thread t;
	final String IP_ADDRESS_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	

	
	
	public MenuController(Menu menuView) {
		super();
		this.menuView = menuView;
	}

	public Menu getMenuView() {
		return menuView;
	}

	public void setMenuView(Menu menuView) {
		this.menuView = menuView;
	}

	/**
	 * Un joueur a créé la partie, on créé un serveur
	 */
	public void createServer(){
		server = new Server(7777,this);
		System.out.println("Lancement");
		t = new Thread(server);
		t.start();
		System.out.println("Lancé");
		id=0;
		joueurs.put(id, new Joueur(String.valueOf(id), null, 0, 0, 0, false));
		menuView.setPanePseudo();
		menuView.setCombobox(id);
	}
	
	public void clientDeconnecter(int no){
		if(joueurs.containsKey(no)){
			joueurs.remove(no);
			listClientsServer.remove(no);
		}
	}
	
	/**
	 * Le client se connecte au serveur
	 * @param ip
	 * @throws IOException
	 */
	public void clientJoinServer(String ip){
		Matcher matcher = Pattern.compile(IP_ADDRESS_PATTERN).matcher(ip);
		if(matcher.find()||ip.equals(new String("localhost"))){
			client = new Client(ip, 7777,this);
			t = new Thread(client);
			t.start();
			if(client.connexion()){
				System.out.println("Client connecté !");
				
				//le premier json qu'il reçoit est : id joueur
				JSONObject json = null;
				try {
					json = client.receiveJSON();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					menuView.setMsgAdressIp("Problème de connexion avec le serveur.");
				}
				if(json!=null){
					try {
						id=json.getInt("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						menuView.setMsgAdressIp("Le transfert de donnée a échouée");
					}
					System.out.println(json.toString());
					menuView.setPanePseudo();
					menuView.setCombobox(id);
				}else{
					
				}
			}else{
				menuView.setMsgAdressIp("La connexion au serveur a échouée");
			}
		}else{
			menuView.setMsgAdressIp("Veuillez saisir une adresse IP valide.");
		}
	}
	
	
	public void commencerLaPartie(){
		if(server==null){
			playerGoGame();
		}else{
			serverGoGame();
		}
	}
	
	public void serverGoGame(){
		
		Plateau plateau = menuView.changerPlateau();
		
		PlateauController plateauController = plateau.getPlateauControlle();
		plateauController.setId(id);
		plateauController.setJoueurs(joueurs);
		plateauController.setListClientsServer(listClientsServer);
		plateauController.setServer(server);
		
		
		plateauController.setPlateauView(plateau);
		
		JSONObject json = new JSONObject();
		try {
			json.put("plateau", true);
			server.Broadcast(listClientsServer, json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playerGoGame(){
		
		Plateau plateau = menuView.changerPlateau();
		
		PlateauController plateauController = plateau.getPlateauControlle();
		plateauController.setId(id);
		plateauController.setJoueurs(joueurs);
		plateauController.setListClientsServer(listClientsServer);
		plateauController.setClient(client);
		plateauController.setPlateauView(plateau);
		
		client.setPlateauController(plateauController);
	}
	
	/**
	 * On ajoute un nouveau joueur dans la partie
	 * @param nb
	 * @param t
	 */
	public void addNewPlayer(int nb, MyThreadHandler t){
		listClientsServer.put(nb, t);
		joueurs.put(nb, new Joueur(String.valueOf(nb), null, 0, 0, 0, false));
		menuView.setButtonStart(true);
	}
	
	/**
	 * Fonction exécutée par le client quand il reçoit des données du serveur
	 * @param json
	 * @throws JSONException
	 */
	public void getJSONFromServer(JSONObject json) throws JSONException{
		if(json.has("error")){
			jsonGetMsgError(json);
			System.out.println(json.getString("error"));
		}else if(json.has("plateau")){
			client.cancelTimer();
			menuView.setVisibleButtunStartGame();
		}else{
			jsonInformationGame(json);
		}
	}
	
	/**
	 * Fonction exécutée par le serveur quand il reçoit des données du client
	 * @param json
	 * @return un json au client
	 * @throws IOException
	 */
	public synchronized JSONObject getJSONFromClient(JSONObject json) throws IOException{
		//le client a envoyé son pseudo pour l'enregistrement
		if(json.has("pseudo")){
			try {
				int no = json.getInt("id");
				String pseudo = json.getString("pseudo");
				
	    		if(checkPseudoExists(pseudo)){
	    			System.out.println("existe");
	    			json=new JSONObject();
	    			json.put("error", "Le pseudo existe déjà, veuillez saisir un autre.");
	    			return json;
	    		}else{
	    			System.out.println("existe pas");
	    			joueurs.replace(no, new Joueur(pseudo, null, 0, 0, 0,false));
	    			json = getInformationGame();
	    			putAllPseudoInView();
	    			server.Broadcast(listClientsServer, json);
	    			System.out.println("fin broascast");
	    			return json; 
	    		}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//le client a sélectionné une couleur
		}else if(json.has("color")){
				try {
					int no = json.getInt("id");
					String color = json.getString("color");
					EnumCouleur colorEnum = EnumCouleur.valueOf(color);
					if(checkColorExists(colorEnum)){
						json=new JSONObject();
		    			json.put("error", "La couleur a déjà été choisi.");
		    			return json;
					}else{
						System.out.println("existe pas");
						Joueur joueur = joueurs.get(no);
						joueur.setCouleur(colorEnum);
		    			joueurs.replace(no, joueur);
		    			json = getInformationGame();
		    			putAllPseudoInView();
		    			server.Broadcast(listClientsServer, json);
		    			System.out.println("fin broascast");
		    			return json; 
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//le client a indiqué qu'il est prêt à commencer le jeu
		}else if(json.has("start")){
			try {
				int no = json.getInt("id");
				boolean selected = json.getBoolean("start");
				Joueur joueur = joueurs.get(no);
				
				if(joueur==null){
					json=new JSONObject();
	    			json.put("error", "Une erreur est survenue, Veuillez relancer le jeu.");
	    			return json;
				}
				
				if(joueur.getCouleur()==null&&selected==true){
					json=new JSONObject();
	    			json.put("error", "La couleur n'a pas été choisie.");
	    			json.put("start", false);
	    			return json;
				}else{
					joueur.setStart(selected);
	    			joueurs.replace(no, joueur);
	    			json = getInformationGame();
	    			putAllPseudoInView();
	    			server.Broadcast(listClientsServer, json);
	    			System.out.println("fin broascast");
	    			if(checkIfAllPLayerAreReady()){
	    				menuView.setButtonStart(false);
	    			}else{
	    				menuView.setButtonStart(true);
	    			}
	    			return json; 
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		json = new JSONObject();
		try {
			json.put("error", "Le serveur n'a pas saisi les données qui ont été envoyé.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * Permet de vérifier si la partie peut être commencé
	 * Pour cela, on vérifie si il y a au moins 2 joueurs
	 * Si tous les joueurs sauf le joueur hébergeur a coché la case "Start" et a sélectionné une couleur
	 * On vérifie si le joueur qui héberge le jeu a bien sélectionné une couleur. 
	 * @return vrai si tous les conditions sont réunies, faux sinon.
	 */
	public boolean checkIfAllPLayerAreReady(){
		if(joueurs.size()<=1){
			return false;
		}
		Set cles = joueurs.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next();
		   Joueur joueur = joueurs.get(cle);
		   if(cle!=0){
			   if(joueur.isStart()==false){
				   return false;
			   }
		   }
		   if(joueur.getCouleur()==null){
			   return false;
		   }
		}
		return true;
	}
	
	/**
	 * Permet de vérifier si le pseudo est unique
	 * @param pseudo : pseudo d'un joueur
	 * @return vrai s'il est unique et faux sinon.
	 */
	public boolean checkPseudoExists(String pseudo){
		Set cles = joueurs.keySet();
		Iterator it = cles.iterator();
		boolean exists=false ;
		while (it.hasNext()&&!exists){
		   int cle = (int) it.next();
		   Joueur joueur = joueurs.get(cle);
		   if(joueur.getName().equals(pseudo)){
			   return true;
		   }
		}
		return false;
	}	
	
	
	/**
	 * Permet de créer le pseudo d'un joueur
	 * @param pseudo
	 * @throws JSONException
	 * @throws IOException
	 */
	public void createPseudo(String pseudo) throws JSONException, IOException{
		
		if(server==null){
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("pseudo", pseudo);
			client.sendJSON(json);
			json = client.receiveJSON();
			if(json.has("error")){
				menuView.setMsgLblPseudo(json.getString("error"));
			}else{
				menuView.setMsgLblPseudo("success");
				menuView.setPaneCreatGame();
				client.timer();
			}
		}else{
			if(checkPseudoExists(pseudo)){
				menuView.setMsgLblPseudo("error");
			}else{
				joueurs.replace(id, new Joueur(pseudo, null, 0, 0, 0,false));
				menuView.setMsgLblPseudo("success");
				menuView.setPaneCreatGame();
				putAllPseudoInView();
			}
		}
	}
	
	/**
	 * Vérifie la couleur n'a pas été déjà utilisé par un autre joueur
	 * @param color
	 * @return
	 */
	public boolean checkColorExists(EnumCouleur color){
		Set cles = joueurs.keySet();
		Iterator it = cles.iterator();
		boolean exists=false ;
		while (it.hasNext()&&!exists){
		   int cle = (int) it.next();
		   Joueur joueur = joueurs.get(cle);
		   if(joueur.getCouleur()!=null){
			   if(joueur.getCouleur().equals(color)){
				   return true;
			   }
		   }
		}
		return false;
	}
	
	/**
	 * Permet de sélectionner une couleur par un joueur
	 * @param color
	 * @throws IOException
	 */
	public void selectColorPlayer(String color) throws IOException{
		if(server==null){
			JSONObject json = new JSONObject();
			
			try {
				json.put("id", id);
				json.put("color", color);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//on envoie au serveur
			client.sendJSON(json);
			
		}else{
			if(checkColorExists(EnumCouleur.valueOf(color))){
				menuView.setMsgError("La couleur a déjà été sélectionné par un autre joueur.");
			}else{
				//Si la couleur n'a pas été utilisé, on affecte la couleur au joueur concerné et on met à jour la liste des joueurs
				Joueur joueur = joueurs.get(id);
				joueur.setCouleur(EnumCouleur.valueOf(color));
				joueurs.replace(id, joueur);
				
				server.Broadcast(listClientsServer, getInformationGame());
				putAllPseudoInView();
				if(checkIfAllPLayerAreReady()){
    				menuView.setButtonStart(false);
    			}else{
    				menuView.setButtonStart(true);
    			}
    			System.out.println("fin broascast");
			}
		}
	}
	
	/**
	 * Permet d'indiquer qu'un joueur est prêt ou non
	 * On vérifie si il a bien sélectionné une couleur, s'il est prêt
	 * @param selected : valeur du checkbox de sélection
	 */
	public void setReadyGame(boolean selected){
		if(server==null){
			JSONObject json = new JSONObject();
			try {
				json.put("start", selected);
				json.put("id", id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.sendJSON(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				menuView.setMsgError("Echec d'envoie de données au serveur");
			}
		}else{
			
		}
	}
	
	/**
	 * Permet de construire un JSON avec toutes les informations de la partie
	 * @return un json
	 */
	public synchronized JSONObject getInformationGame(){
		JSONObject json = new JSONObject();
		JSONArray listJoueur=new JSONArray();
		JSONArray listId=new JSONArray();
		//permet de sérializer une classe
		Gson gson;
        try {
        	Set cles = joueurs.keySet();
    		Iterator it = cles.iterator();
    		while (it.hasNext()){
    		   int cle = (int) it.next();
    		   gson = new Gson();
    		   listId.put(cle);
    		   //sérialization
    		   listJoueur.put(gson.toJson(joueurs.get(cle)));
    		}
    		json.put("ids", listId);
			json.put("joueurs", listJoueur);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(json.toString());
        return json;
	}
	
	/**
	 * On récupère le message d'erreur et l'envoie vers la vue
	 * @param json
	 */
	public void jsonGetMsgError(JSONObject json){
		try {
			menuView.setMsgError(json.getString("error"));
			if(json.has("start")){
				menuView.setSelectedCheckBox(false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Récupération du json avec toutes les informations (pseudo, couleur)
	 * on met à jour la vue
	 * @param json
	 */
	public void jsonInformationGame(JSONObject json){
		try {
			System.out.println("jsoninformationgame");
			JSONArray jsonArrayJ = json.getJSONArray("joueurs");
			JSONArray jsonArrayI = json.getJSONArray("ids");
			Gson gson = new Gson();
			joueurs = new HashMap<Integer,Joueur>();
			int i = 0;
			for(i=0;i<jsonArrayI.length();i++){
				joueurs.put(jsonArrayI.getInt(i), gson.fromJson(jsonArrayJ.get(i).toString(), Joueur.class));
			}
			putAllPseudoInView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Mettre à jour la vue
	 */
	public void putAllPseudoInView(){
		Set cles = joueurs.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next();
		   menuView.setTxtInTextField(cle, joueurs.get(cle).getName());
		   if((joueurs.get(cle).getCouleur()!=null)&&(cle!=id)){
			   menuView.setSelectedElementCombobox(cle, joueurs.get(cle).getCouleur().name());
		   }
		   if(cle!=0){
			   System.out.println("cle : "+cle+ " "+joueurs.get(cle).isStart());
			   if(cle==id){
				   menuView.setSelectedCheckBox(joueurs.get(cle).isStart());
			   }
		   }
		}
	}
	
}
