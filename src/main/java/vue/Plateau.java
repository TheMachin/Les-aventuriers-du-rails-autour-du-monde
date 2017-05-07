package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controlor.PlateauController;
import ennumeration.EnumCouleur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.MainMenu;
import metier.Boat;
import metier.Carte;
import metier.Destination;
import metier.Iteneraire;
import metier.Joueur;
import metier.RouteMartime;
import metier.RouteTerrestre;
import metier.Ville;
import metier.Wagon;

public class Plateau {

	
	@FXML 
	private Label lblDeckBoat, lblMsgGame;
	
	@FXML 
	private Label lblDeckWagon, lblSelectNbWagon, lblSelectNbBoat, lblPionBoat, lblPionWagon, lblScore, lblPionPort;
	
	@FXML 
	private Label lblDeckDestination, lblSelectDestination;

	@FXML 
	private HBox HboxMain, hboxDestination, hboxDestinationSelect;
	
	@FXML 
	private HBox HboxSelect;

	@FXML
	private Pane paneDestination, panePion;
	

	// villes
		Ville anchorage = new Ville("Anchorage", true);
		Ville cambridgeBay = new Ville("Cambridge Bay", true);
		Ville murmansk = new Ville("Murmansk", true);
		Ville tiksi = new Ville("Tiksi", true);
		Ville alqahira = new Ville("Al Qahira", true);
		Ville portMoresby = new Ville("Port Moresby", true);
		Ville alZahira = new Ville("Al Zahira", true);
		Ville sydney = new Ville("Sydney", true);
		Ville athina = new Ville("Athina", true);
		Ville manila = new Ville("Manila", true);
		Ville bangkok = new Ville("Bangkok", true);
		Ville tokyo = new Ville("Tokyo", true);
		Ville buenos = new Ville("Buenos Aires", true);
		Ville marseille = new Ville("Marseille", true);
		Ville capeTown = new Ville("Cape Town", true);
		Ville caracas = new Ville("Caracas", true);
		Ville casablanca = new Ville("Casablanca", true);
		Ville jakarta = new Ville("Jakarta", true);
		Ville honolulu = new Ville("Honolulu", true);
		Ville vakutsk = new Ville("Vakutsk", false);
		Ville darEsSalaam = new Ville("Dar Es Salaam", true);
		Ville djibouti = new Ville("Djibouti", false);
		Ville lahore = new Ville("Lahore", false);
		Ville edinburgh = new Ville("Edinburgh", true);
		Ville luanda = new Ville("Luanda", true);
		Ville hongkong = new Ville("Hong Kong", true);
		Ville hamburg = new Ville("Hamburg", true);
		Ville beijing = new Ville("Beijing", false);
		Ville lagos = new Ville("Lagos", true);
		Ville lima = new Ville("Lima", true);
		Ville tehran = new Ville("Tehran", false);
		Ville losAngeles = new Ville("Los Angeles", true);
		Ville rio = new Ville("Rio De Janeiro", true);
		Ville mexico = new Ville("Mexico", false);
		Ville christchurch = new Ville("Christchurch", true);
		Ville mumbai = new Ville("Mumbai", true);
		Ville ny = new Ville("New York", true);
		Ville miami = new Ville("Miami", true);
		Ville moskva = new Ville("Moskva", false);
		Ville petropavlovsk = new Ville("Petropavlovsk", true);
		Ville toamasina = new Ville("Toamasina", true);
		Ville novosibirsk = new Ville("Novosibirsk", true);
		Ville darwin = new Ville("Darwin", false);
		Ville reykjavik = new Ville("Reykjavik", true);
		Ville perth = new Ville("Perth", true);
		Ville valparaiso = new Ville("Valparaiso", true);
		Ville vancouver = new Ville("Vancouver", true);
		Ville winnipeg = new Ville("Winnipeg", false);
	
	

	private PlateauController plateauControlle = new PlateauController();
	


	private MainMenu main;
	
	private Stage stageScore = new Stage();
	private Score score = null;
	
	private Map<Label,Wagon> carteW = new HashMap<Label,Wagon>();
	private Map<Label,Boat> carteB = new HashMap<Label,Boat>();
	private Map<Label,Destination> carteD = new HashMap<Label,Destination>();
	private Map<Label,Iteneraire> carteI = new HashMap<Label,Iteneraire>();
	private List<Label> carteCSelect = new ArrayList<Label>();
	private String themeUrl = getClass().getResource("plateau.css").toExternalForm();
	
	public Plateau(PlateauController plateauController) {
		super();
		this.plateauControlle=plateauController;
		HboxMain = new HBox();
		HboxSelect = new HBox();
	}
	
	public void setCardsWagonInMainOfPlayer(Wagon w){
		Label lbl = new Label();
		System.out.println(w.getLienImage());
		Image image = new Image(getClass().getResourceAsStream(w.getLienImage()));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCard);
	    this.HboxMain.getChildren().add(lbl);
	    this.carteW.put(lbl, w);
	    System.out.println(this.carteW.size());
	}
	
	public void setCardsBoatInMainOfPlayer(Boat b){
		Label lbl = new Label();
		System.out.println(b.getLienImage());
		Image image = new Image(getClass().getResourceAsStream(b.getLienImage()));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCard);
	    this.HboxMain.getChildren().add(lbl);
	    this.carteB.put(lbl, b);
	    System.out.println(this.carteB.size());
	}
	
	public void setCardsDestinationForChoice(Destination destination){
		paneDestination.toFront();
		System.out.println("destination");
		Label lbl = new Label();
		lbl.getStylesheets().add(themeUrl);
		Image image = new Image(getClass().getResourceAsStream(destination.getLienImage()));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCardDestination);
	    carteD.put(lbl, destination);
		hboxDestinationSelect.getChildren().add(lbl);
		
	}
	
	public void setCardsIteneraireForChoice(Iteneraire ite){
		paneDestination.toFront();
		System.out.println("iteneraire");
		Label lbl = new Label();
		lbl.getStylesheets().add(themeUrl);
		Image image = new Image(getClass().getResourceAsStream(ite.getLienImage()));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCardDestination);
	    carteI.put(lbl, ite);
		hboxDestinationSelect.getChildren().add(lbl);
		
	}
	
	
	 /**
	  * Permet de piocher une carte bateau
	  * @param e
	  */
	 @FXML
	 private void takeHideBoatCard(MouseEvent e) {
		System.out.println("clicked boat");
		plateauControlle.piocheCards("bateau");		
	 }
	 
	 
	 /**
	  * Permet de piocher une carte train
	  * @param e
	  */
	 @FXML
	 private void takeHideTrainCard(MouseEvent e) {
		 plateauControlle.piocheCards("wagon");		
	 }
	 
	 
	 @FXML
	 private void takeHideDestinationCard(MouseEvent e) {
		//Appel fonction pioche dans metier
		 plateauControlle.piocheCards("destination");		
	 }
	
	 /**
	  * Permet de prendre la carte de la main du joueur pour pouvoir la jouer
	  * @param e
	  */
	 @FXML
	 private void selectionCard(MouseEvent e){
		 Label lbl = (Label) e.getSource();
		 HboxMain.getChildren().remove(lbl);
		 lbl.setOnMouseClicked(this::deSelectionCard);
		 HboxSelect.getChildren().add(lbl);
		 
	 }
	 
	 @FXML
	 private void selectionCardDestination(MouseEvent e){
		 Label lbl = (Label) e.getSource();
		 carteCSelect.add(lbl);
		 lbl.setOnMouseClicked(this::deSelectionCardDestination);
		 lbl.getStyleClass().clear();
		 lbl.getStyleClass().add("lblCadre");
	 }
	 
	 @FXML
	 private void confirmeChoiceDestination(ActionEvent e){
		 List<Iteneraire> iteSelect = new ArrayList<Iteneraire>();
		 List<Destination> desSelect = new ArrayList<Destination>();
		 List<Iteneraire> iteNonSelect = new ArrayList<Iteneraire>();
		 List<Destination> desNonSelect = new ArrayList<Destination>();
		 Map<Label,Destination> carteD2 = carteD;
		 Map<Label,Iteneraire> carteI2 = carteI;
		 
		 int i=0;
		 int j=0;
		 for(i=0;i<carteCSelect.size();i++){
			 if(carteD.containsKey(carteCSelect.get(i))){
				 Destination d = carteD.get(carteCSelect.get(i));
				 desSelect.add(d);
				 carteD2.remove(carteCSelect.get(i));
			 }else if(carteI.containsKey(carteCSelect.get(i))){
				 Iteneraire ite = carteI.get(carteCSelect.get(i));
				 iteSelect.add(ite);
				 carteI2.remove(carteCSelect.get(i));
			 }
		 }
		 for(i=0;i<carteD2.size();i++){
			 desNonSelect.add(carteD2.get(i));
		 }
		 for(i=0;i<carteI2.size();i++){
			 iteNonSelect.add(carteI2.get(i));
		 }
		 
		 
		 System.out.println("go controller "+desSelect.size()+" "+iteSelect.size());
		 plateauControlle.takeCardsDestination(desSelect, desNonSelect, iteSelect, iteNonSelect);
	 }
	 
	 
	 /**
	  * Remettre la carte dans la main du joueur à partir de la carte sélectionnée par la souris
	  */
	 @FXML
	 private void deSelectionCard(MouseEvent e){
		 Label lbl = (Label) e.getSource();
		 deSelectionCard(lbl);
	 }
	 
	 @FXML
	 private void deSelectionCardDestination(MouseEvent e){
		 Label lbl = (Label) e.getSource();
		 if(carteCSelect.contains(lbl)){
			 carteCSelect.remove(lbl);
		 }
		 lbl.setOnMouseClicked(this::selectionCardDestination);
		 lbl.getStyleClass().clear();
	 }

	public void putDestinationInMainOfPlayer(List<Destination> listDes, List<Iteneraire> listIte) {
		int i;
		Label lbl;
		Image image;
		for (i = 0; i < listDes.size(); i++) {
			lbl = new Label();
			image = new Image(getClass().getResourceAsStream(listDes.get(i).getLienImage()));
			lbl.setGraphic(new ImageView(image));
			hboxDestination.getChildren().add(lbl);
		}
		for (i = 0; i < listIte.size(); i++) {
			lbl = new Label();
			image = new Image(getClass().getResourceAsStream(listIte.get(i).getLienImage()));
			lbl.setGraphic(new ImageView(image));
			hboxDestination.getChildren().add(lbl);
		}
		hboxDestinationSelect.getChildren().clear();
		carteI.clear();
		carteD.clear();
		carteCSelect.clear();
		paneDestination.toBack();
	}

	public void printMsgDestination(String msg) {
		lblSelectDestination.setText(msg);
	}

	/**
	 * Action pour remettre la carte dans la main du joueur
	 * 
	 * @param lbl
	 */
	private void deSelectionCard(Label lbl) {
		HboxSelect.getChildren().remove(lbl);
		lbl.setOnMouseClicked(this::selectionCard);
		HboxMain.getChildren().add(lbl);
	}

	public void deSelectionAllCard() {
		int i = 0;
		for (i = HboxSelect.getChildren().size() - 1; i >= 0; i--) {
			deSelectionCard((Label) HboxSelect.getChildren().get(i));
		}
	}

	public void mettreCarteDansDefausse(int nb, List<Label> listLbl) {
		int i=0;
		List<Wagon> listW = new ArrayList<Wagon>();
		List<Boat> listB = new ArrayList<Boat>();
		System.out.println("label "+nb+ " taille"+listLbl.size());
		for(i=0;i<nb;i++) {
			if(listLbl.get(i)!=null){
				Label lbl = listLbl.get(i);
				HboxSelect.getChildren().remove(lbl);
				if (carteW.containsKey(lbl)) {
					Wagon b = carteW.get(lbl);
					// mettre carte wagon dans la défausse
					listW.add(b);
					carteW.remove(lbl);
				} else if (carteB.containsKey(lbl)) {
					Boat b = carteB.get(lbl);
					listB.add(b);
					if(b.isDoubleBoat()){
						nb--;
					}
					// mettre carte bateau dans la défausse
					carteB.remove(lbl);
				}
			}
		}
		plateauControlle.discardingWagonBoat(listW, listB);
	}

	/**
	 * Prendre une route maritime. On vérifie si le joueur a mis assez de carte
	 * en jeu. Si c'est le cas, la route est prise sinon les cartes retournent
	 * dans la main du joueur
	 * 
	 * @param e
	 */
	@FXML
	private void takeRoadBoatMap(MouseEvent e) {
		Rectangle rect = (Rectangle) e.getSource();
		RouteMartime r = null;
		List<String> listFxId = new ArrayList<String>();
		switch (rect.getId()) {
		case "vnyedin":
			r = new RouteMartime(7, EnumCouleur.VIOLET, ny, edinburgh);
			listFxId.add("vnyedin");
			break;
		case "rnyedin":
			r = new RouteMartime(7, EnumCouleur.ROUGE, ny, edinburgh);
			listFxId.add("rnyedin");
			break;
		case "nacb":
			r = new RouteMartime(6, EnumCouleur.NOIR, anchorage, cambridgeBay);
			listFxId.add("nacb");
			break;
		case "bcbr":
			r = new RouteMartime(6, EnumCouleur.BLANC, cambridgeBay, reykjavik);
			listFxId.add("bcbr");
			break;
		case "jnyr":
			r = new RouteMartime(6, EnumCouleur.JAUNE, ny, reykjavik);
			listFxId.add("jnyr");
			break;
		case "vmc":
			r = new RouteMartime(7, EnumCouleur.VERT, miami, casablanca);
			listFxId.add("vmc");
			break;
		case "bmc":
			r = new RouteMartime(2, EnumCouleur.BLANC, miami, caracas);
			listFxId.add("bmc");
			break;
		case "rcl":
			r = new RouteMartime(7, EnumCouleur.ROUGE, caracas, lagos);
			listFxId.add("rcl");
			break;
		case "grdjl":
			r = new RouteMartime(6, EnumCouleur.GRIS, rio, luanda);
			listFxId.add("grdjl");
			break;
		case "nrdjct":
			r = new RouteMartime(6, EnumCouleur.NOIR, rio, capeTown);
			listFxId.add("nrdjct");
			break;
		case "brdjct":
			r = new RouteMartime(6, EnumCouleur.BLANC, rio, capeTown);
			listFxId.add("brdjct");
			break;
		case "vbact":
			r = new RouteMartime(7, EnumCouleur.VIOLET, buenos, capeTown);
			listFxId.add("vbact");
			break;
		case "jbact":
			r = new RouteMartime(7, EnumCouleur.JAUNE, buenos, capeTown);
			listFxId.add("jbact");
			break;
		case "bedinm":
			r = new RouteMartime(1, EnumCouleur.BLANC, edinburgh, marseille);
			listFxId.add("bedinm");
			break;
		case "vedinm":
			r = new RouteMartime(1, EnumCouleur.VERT, edinburgh, marseille);
			listFxId.add("vedinm");
			break;
		case "rma":
			r = new RouteMartime(2, EnumCouleur.ROUGE, marseille, athina);
			listFxId.add("rma");
			break;
		case "vrm":
			r = new RouteMartime(4, EnumCouleur.VERT, reykjavik, murmansk);
			listFxId.add("vrm");
			break;
		case "rmt":
			r = new RouteMartime(7, EnumCouleur.ROUGE, murmansk, tiksi);
			listFxId.add("rmt");
			break;
		case "jta1":
		case "jta2":
			r = new RouteMartime(8, EnumCouleur.JAUNE, tiksi, anchorage);
			listFxId.add("jta1");
			listFxId.add("jta2");
			break;
		case "ntp1":
		case "ntp2":
		case "ntp3":
			r = new RouteMartime(7, EnumCouleur.NOIR, tiksi, petropavlovsk);
			
			listFxId.add("ntp1");
			listFxId.add("ntp2");
			listFxId.add("ntp3");
			break;
		case "vpa":
			r = new RouteMartime(3, EnumCouleur.VIOLET, petropavlovsk, anchorage);
			listFxId.add("vpa");
			break;
		case "gtp":
			r = new RouteMartime(2, EnumCouleur.GRIS, tokyo, petropavlovsk);
			listFxId.add("gtp");
			break;
		case "gjm":
			r = new RouteMartime(2, EnumCouleur.GRIS, jakarta, manila);
			listFxId.add("gjm");
			break;
		case "vaalq":
			r = new RouteMartime(1, EnumCouleur.VERT, athina, alqahira);
			listFxId.add("vaalq");
			break;
		case "rdpm":
			r = new RouteMartime(1, EnumCouleur.ROUGE, darwin, portMoresby);
			listFxId.add("rdpm");
			break;
		case "njd":
			r = new RouteMartime(2, EnumCouleur.NOIR, jakarta, darwin);
			listFxId.add("njd");
			break;
		case "videsj":
			r = new RouteMartime(7, EnumCouleur.VIOLET, darEsSalaam, jakarta);
			listFxId.add("videsj");
			break;
		case "vedesj":
			r = new RouteMartime(7, EnumCouleur.VERT, darEsSalaam, jakarta);
			listFxId.add("vedesj");
			break;
		case "neh":
			r = new RouteMartime(1, EnumCouleur.NOIR, edinburgh, hamburg);
			listFxId.add("neh");
			break;
		case "jeh":
			r = new RouteMartime(1, EnumCouleur.JAUNE, edinburgh, hamburg);
			listFxId.add("jeh");
			break;
		case "jmt":
			r = new RouteMartime(2, EnumCouleur.JAUNE, manila, tokyo);
			listFxId.add("jmt");
			break;
		case "gre1":
		case "gre2":
			r = new RouteMartime(2, EnumCouleur.GRIS, reykjavik, edinburgh);
			listFxId.add("gre1");
			listFxId.add("gre2");
			break;
		case "bbj1":
		case "bbj2":
			r = new RouteMartime(2, EnumCouleur.BLANC, bangkok, jakarta);
			listFxId.add("bbj1");
			listFxId.add("bbj2");
			break;
		case "bdesm1":
		case "bdesm2":
			r = new RouteMartime(4, EnumCouleur.BLANC, darEsSalaam, mumbai);
			listFxId.add("bdesm1");
			listFxId.add("bdesm2");
			break;
		case "ghkt1":
		case "ghkt2":
			r = new RouteMartime(3, EnumCouleur.GRIS, hongkong, tokyo);
			listFxId.add("ghkt1");
			listFxId.add("ghkt2");
			break;
		case "bmh1":
		case "bmh2":
			r = new RouteMartime(5, EnumCouleur.BLANC, manila, honolulu);
			listFxId.add("bmh1");
			listFxId.add("bmh2");
			break;
		case "vpmh1":
		case "vpmh2":
			r = new RouteMartime(3, EnumCouleur.VERT, portMoresby, honolulu);
			listFxId.add("vpmh1");
			listFxId.add("vpmh2");
			break;
		case "gjp1":
		case "gjp2":
			r = new RouteMartime(3, EnumCouleur.GRIS, jakarta, perth);
			listFxId.add("gjp1");
			listFxId.add("gjp2");
			break;
		case "jpms1":
		case "jpms2":
			r = new RouteMartime(3, EnumCouleur.JAUNE, portMoresby, sydney);
			listFxId.add("jpms1");
			listFxId.add("jpms2");
			break;
		case "bsc":
			r = new RouteMartime(1, EnumCouleur.BLANC, sydney, christchurch);
			listFxId.add("bsc");
			break;
		case "rsc":
			r = new RouteMartime(1, EnumCouleur.ROUGE, sydney, christchurch);
			listFxId.add("rsc");
			break;
		case "rbm1":
		case "rbm2":
			r = new RouteMartime(2, EnumCouleur.ROUGE, bangkok, manila);
			listFxId.add("rbm1");
			listFxId.add("rbm2");
			break;
		case "jdest":
			r = new RouteMartime(1, EnumCouleur.JAUNE, darEsSalaam, toamasina);
			listFxId.add("jdest");
			break;
		case "gctt":
			r = new RouteMartime(3, EnumCouleur.GRIS, capeTown, toamasina);
			listFxId.add("gctt");
			break;
		case "vvba1":
		case "vvba2":
		case "vvba3":
			r = new RouteMartime(3, EnumCouleur.VERT, valparaiso, buenos);
			listFxId.add("vvba1");
			listFxId.add("vvba2");
			listFxId.add("vvba3");
			break;
		case "rctn":
			r = new RouteMartime(5, EnumCouleur.ROUGE, capeTown, null);
			listFxId.add("rctn");
			break;
		case "vctn":
			r = new RouteMartime(5, EnumCouleur.VERT, capeTown, null);
			listFxId.add("vctn");
			break;
		case "bnp":
			r = new RouteMartime(5, EnumCouleur.BLANC, null, perth);
			listFxId.add("bnp");
			break;
		case "vnp":
			r = new RouteMartime(5, EnumCouleur.VIOLET, null, perth);
			listFxId.add("vnp");
			break;
		case "rth1":
		case "rth2":
		case "rth3":
			r = new RouteMartime(5, EnumCouleur.ROUGE, tokyo, honolulu);
			listFxId.add("rth1");
			listFxId.add("rth2");
			listFxId.add("rth3");
			break;
		case "jcv1":
		case "jcv2":
			r = new RouteMartime(7, EnumCouleur.JAUNE, christchurch, valparaiso);
			listFxId.add("jcv1");
			listFxId.add("jcv2");
			break;
		case "vsl1":
		case "vsl2":
			r = new RouteMartime(8, EnumCouleur.VIOLET, sydney, lima);
			listFxId.add("vsl2");
			listFxId.add("vsl2");
			break;
		case "nsl1":
		case "nsl2":
			r = new RouteMartime(8, EnumCouleur.NOIR, sydney, lima);
			listFxId.add("nsl2");
			listFxId.add("nsl2");
			break;
		case "ghl":
			r = new RouteMartime(6, EnumCouleur.GRIS, honolulu, lima);
			listFxId.add("ghl");
			break;
		case "jhla1":
		case "jhla2":
			r = new RouteMartime(3, EnumCouleur.JAUNE, honolulu, losAngeles);
			listFxId.add("jhla1");
			listFxId.add("jhla2");
			break;
		case "vtla1":
		case "vtla2":
		case "vtla3":
		case "vtla4":
		case "vtla5":
		case "vtla6":
			r = new RouteMartime(7, EnumCouleur.VERT, tokyo, losAngeles);
			listFxId.add("vtla1");
			listFxId.add("vtla2");
			listFxId.add("vtla3");
			listFxId.add("vtla4");
			listFxId.add("vtla5");
			listFxId.add("vtla6");
			break;
		case "ntla1":
		case "ntla2":
		case "ntla3":
		case "ntla4":
		case "ntla5":
		case "ntla6":
			r = new RouteMartime(7, EnumCouleur.NOIR, tokyo, losAngeles);
			listFxId.add("ntla1");
			listFxId.add("ntla2");
			listFxId.add("ntla3");
			listFxId.add("ntla4");
			listFxId.add("ntla5");
			listFxId.add("ntla6");
			break;
		case "btv1":
		case "btv2":
		case "btv3":
			r = new RouteMartime(6, EnumCouleur.BLANC, tokyo, vancouver);
			listFxId.add("btv1");
			listFxId.add("btv2");
			listFxId.add("btv3");
			break;
		}

		
		int carte = 0;
		int doubleBoat = 0 ;
		int joker = 0;
		EnumCouleur color=null;
		RouteMartime routeM = r;
		List<Label> listLbl = new ArrayList<Label>();
		List<Wagon> listW = new ArrayList<Wagon>();
		List<Boat> listB = new ArrayList<Boat>();
		int i = 0;
		for (i = 0; i < HboxSelect.getChildren().size(); i++) {
			if (carteB.containsKey(HboxSelect.getChildren().get(i))) {
				Boat b = carteB.get(HboxSelect.getChildren().get(i));
				if(r.getCouleur()==EnumCouleur.GRIS){
					if(color==null){
						color=b.getCouleur();
					}
				}
				if (b.getCouleur() == routeM.getCouleur() || b.getCouleur() == color) {

					carte++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
					if (b.isDoubleBoat()) {
						doubleBoat++;
					}
					listB.add(b);

				}
			} else if (carteW.containsKey(HboxSelect.getChildren().get(i))) {
				Wagon c = carteW.get(HboxSelect.getChildren().get(i));
				if (c.isJoker()) {
					joker++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
					listW.add(c);
				}
			}
		}

		if ((joker + carte + doubleBoat) >= r.getNbPion() && plateauControlle.checkIfEnoughPion(joker, carte+doubleBoat, plateauControlle.getIdPlayer())) {
			if(plateauControlle.takeRoadWagonOrBoatOrPort(null, r, null, listFxId)){
				System.out.println("route prise");
				mettreCarteDansDefausse(r.getNbPion(), listLbl);
			}

		}
		// s'il reste des cartes en trop
		deSelectionAllCard();
	}

	/**
	 * Prendre une route terreste. On vérifie si le joueur a mis assez de carte
	 * en jeu. Si c'est le cas, la route est prise sinon les cartes retournent
	 * dans la main du joueur
	 * 
	 * @param e
	 */
	@FXML
	private void takeRoadWagonMap(MouseEvent e) {
		Rectangle rect = (Rectangle) e.getSource();
		List<String> listFxId = new ArrayList<String>();
		RouteTerrestre r = null;
		//RouteTerrestre r = new RouteTerrestre(7, EnumCouleur.VIOLET, null, null);
		int carte = 0;
		int joker = 0;

		switch (rect.getId()) {
			case "jvw":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, vancouver, winnipeg);
				listFxId.add("jvw");
				break;
			case "vwny":
				r = new RouteTerrestre(2, EnumCouleur.VERT, winnipeg, ny);
				listFxId.add("vwny");
				break;
			case "rvla":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, vancouver, losAngeles);
				listFxId.add("rvla");
				break;
			case "vvla":
				r = new RouteTerrestre(1, EnumCouleur.VERT, vancouver, losAngeles);
				listFxId.add("vvla");
				break;
			case "glaw":
				r = new RouteTerrestre(3, EnumCouleur.GRIS, losAngeles, winnipeg);
				listFxId.add("glaw");
				break;
			case "rpd":
				r = new RouteTerrestre(2, EnumCouleur.ROUGE, perth, darwin);
				listFxId.add("rpd");
				break;
			case "vds":
				r = new RouteTerrestre(2, EnumCouleur.VERT, darwin, sydney);
				listFxId.add("vds");
				break;
			case "bps":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, perth, sydney);
				listFxId.add("bps");
				break;
			case "jps":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, perth, sydney);
				listFxId.add("jps");
				break;
			case "blam":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, losAngeles, mexico);
				listFxId.add("blam");
				break;
			case "jlam":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, losAngeles, mexico);
				listFxId.add("jlam");
				break;
			case "vlany":
				r = new RouteTerrestre(4, EnumCouleur.VIOLET, losAngeles, ny);
				listFxId.add("vlany");
				break;
			case "nlany":
				r = new RouteTerrestre(4, EnumCouleur.NOIR, losAngeles, ny);
				listFxId.add("nlany");
				break;
			case "vimc":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, mexico, caracas);
				listFxId.add("vimc");
				break;
			case "rmc":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, mexico, caracas);
				listFxId.add("rmc");
				break;
			case "bmny1":
			case "bmny2":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, miami, ny);
				listFxId.add("bmny1");
				listFxId.add("bmny2");
				break;
			case "jlc":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, lima, caracas);
				listFxId.add("jlc");
				break;
			case "blc":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, lima, caracas);
				listFxId.add("blc");
				break;
			case "vcrdj1":
			case "vcrdj2":
			case "vcrdj3":
				r = new RouteTerrestre(4, EnumCouleur.VERT, caracas, rio);
				listFxId.add("vcrdj1");
				listFxId.add("vcrdj2");
				listFxId.add("vcrdj3");
				break;
			case "ncrdj1":
			case "ncrdj2":
			case "ncrdj3":
				r = new RouteTerrestre(4, EnumCouleur.NOIR, caracas, rio);
				listFxId.add("ncrdj1");
				listFxId.add("ncrdj2");
				listFxId.add("ncrdj3");
				break;
			case "glvg":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, lima, valparaiso);
				listFxId.add("glvg");
				break;
			case "glvd":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, lima, valparaiso);
				listFxId.add("glvd");
				break;
			case "bbardj":
				r = new RouteTerrestre(1, EnumCouleur.BLANC, buenos, rio);
				listFxId.add("bbardj");
				break;
			case "rbardj":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, buenos, rio);
				listFxId.add("rbardj");
				break;
			case "vectdes":
				r = new RouteTerrestre(3, EnumCouleur.VERT, capeTown, darEsSalaam);
				listFxId.add("vectdes");
				break;
			case "victdes":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, capeTown, darEsSalaam);
				listFxId.add("victdes");
				break;
			case "glct":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, luanda, capeTown);
				listFxId.add("glct");
				break;
			case "jll":
				r = new RouteTerrestre(1, EnumCouleur.JAUNE, lagos, luanda);
				listFxId.add("jll");
				break;
			case "vll":
				r = new RouteTerrestre(1, EnumCouleur.VIOLET, lagos, luanda);
				listFxId.add("vll");
				break;
			case "gcl1":
			case "gcl2":
				r = new RouteTerrestre(4, EnumCouleur.GRIS, casablanca, lagos);
				listFxId.add("gcl1");
				listFxId.add("gcl2");
				break;
			case "gcaq":
				r = new RouteTerrestre(3, EnumCouleur.GRIS, casablanca, alqahira);
				listFxId.add("gcaq");
				break;
			case "raqd":
				r = new RouteTerrestre(2, EnumCouleur.ROUGE, alqahira, djibouti);
				listFxId.add("raqd");
				break;
			case "baqd":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, alqahira, djibouti);
				listFxId.add("baqd");
				break;
			case "rddes":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, djibouti, darEsSalaam);
				listFxId.add("rddes");
				break;
			case "nddes":
				r = new RouteTerrestre(1, EnumCouleur.NOIR, djibouti, darEsSalaam);
				listFxId.add("nddes");
				break;
			case "rmh":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, marseille, hamburg);
				listFxId.add("rmh");
				break;
			case "vmh":
				r = new RouteTerrestre(1, EnumCouleur.VIOLET, marseille, hamburg);
				listFxId.add("vmh");
				break;
			case "romt":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, moskva, tehran);
				listFxId.add("romt");
				break;
			case "vmm":
				r = new RouteTerrestre(2, EnumCouleur.VIOLET, murmansk, moskva);
				listFxId.add("vmm");
				break;
			case "vha1":
			case "vha2":
				r = new RouteTerrestre(2, EnumCouleur.VERT, hamburg, athina);
				listFxId.add("vha1");
				listFxId.add("vha2");
				break;
			case "gat":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, athina, tehran);
				listFxId.add("gat");
				break;
			
		}

		List<Label> listLbl = new ArrayList<Label>();

		int i = 0;
		for (i = 0; i < HboxSelect.getChildren().size(); i++) {
			if (carteW.containsKey(HboxSelect.getChildren().get(i))) {
				Wagon c = carteW.get(HboxSelect.getChildren().get(i));

				if (c.getCouleur() == r.getCouleur()) {
					carte++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
				} else if (c.isJoker()) {
					joker++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
				}
			}
		}

		if ((joker + carte) >= r.getNbPion() && plateauControlle.checkIfEnoughPion(carte+joker, 0, plateauControlle.getIdPlayer())) {
			if(plateauControlle.takeRoadWagonOrBoatOrPort(r, null, null, listFxId)){
				System.out.println("route prise");
				mettreCarteDansDefausse(r.getNbPion(), listLbl);
			}

		}
		// s'il reste des cartes en trop
		deSelectionAllCard();
	}

	public void colorRoadOrPort(EnumCouleur color, List<String> listFxId){
		Scene scene = lblScore.getScene();
		int i;
		for(i=0;i<listFxId.size();i++){
			Rectangle rectangle = (Rectangle) scene.lookup("#"+listFxId.get(i));
			rectangle.setOpacity(100);
			rectangle.setFill(color.getColor(color));
		}
	}
	/**
	 * Construction d'un port Il faut 2 cartes bateau et 2 cartes wagon avec le
	 * sigle port Une ou plusieurs cartes joker peuvent remplacer une carte
	 * bateau ou wagon (ou plusieurs)
	 * 
	 * @param e
	 */
	@FXML
	private void buildPort(MouseEvent e) {
		//Ville v = new Ville("Marseille", true);
		Rectangle rect = (Rectangle) e.getSource();
		List<String> listFxId = new ArrayList<String>();
		Ville v = null;

		switch (rect.getId()) {
			case "pm":
				v = new Ville("Marseille", true);
				break;
			case "ph":
				v = new Ville("Hamburg", true);
				break;
		}
		listFxId.add(rect.getId());

		int carteBoat = 0;
		int carteWagon = 0;
		int jokerWagon = 0;
		EnumCouleur color = null;

		List<Label> listLbl = new ArrayList<Label>();

		int i = 0;
		for (i = 0; i < HboxSelect.getChildren().size(); i++) {
			if (carteW.containsKey(HboxSelect.getChildren().get(i))) {
				Wagon c = carteW.get(HboxSelect.getChildren().get(i));

				if (c.isPort()&&!c.isJoker()) {
					if(color==null){
						color=c.getCouleur();
					}
					if(color.name()==c.getCouleur().name()){
						carteWagon++;
						listLbl.add((Label) HboxSelect.getChildren().get(i));
					}
				} else if (c.isJoker()) {
					jokerWagon++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
				}
			} else if (carteB.containsKey(HboxSelect.getChildren().get(i))) {
				Boat b = carteB.get(HboxSelect.getChildren().get(i));

				/**
				 * Pas de double bateau pour construire un port La carte doit
				 * être siglé port
				 */
				if (!b.isDoubleBoat() && b.isPort()) {
					if(color==null){
						color=b.getCouleur();
					}
					if(color.name()==b.getCouleur().name()){
						carteBoat++;
						listLbl.add((Label) HboxSelect.getChildren().get(i));
					}
					
				}

			}
			
		}
		System.out.println(carteBoat+" "+carteWagon+" "+jokerWagon);
		if ((carteBoat + carteWagon + jokerWagon >= 4) && (carteWagon <= 2) && (carteBoat <= 2) && (jokerWagon <= 4) && plateauControlle.checkIfEnoughPion(carteWagon+jokerWagon, carteBoat, plateauControlle.getIdPlayer())) {
			if(plateauControlle.takeRoadWagonOrBoatOrPort(null, null, v, listFxId)){
				System.out.println("route prise");
				mettreCarteDansDefausse(4, listLbl);
			}
		}else{
			printMsgGame("Pas assez de cartes pour construire un port");
		}
		deSelectionAllCard();
	}

	public void setMain(MainMenu main) {
		this.main = main;
	}

	public void setPlateauController(PlateauController plateauControlle) {
		this.plateauControlle = plateauControlle;
	 }
	 
	 public PlateauController getPlateauControlle() {
			return plateauControlle;
	 }
	 
	 public void pionChoix(){
		 panePion.toFront();
		 lblSelectNbBoat.setText("35");
		 lblSelectNbWagon.setText("25");
	 }
	 
	 @FXML
	 private void addPionWagon(ActionEvent e){
		 int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		 int boat = Integer.parseInt(lblSelectNbBoat.getText());
		 if(wagon>=0&&boat>0){
			 lblSelectNbWagon.setText(String.valueOf(wagon+1));
			 lblSelectNbBoat.setText(String.valueOf(boat-1));
		 }
	 }
	 
	 @FXML
	 private void removePionWagon(ActionEvent e){
		 int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		 int boat = Integer.parseInt(lblSelectNbBoat.getText());
		 if(wagon>0&&boat>=0){
			 lblSelectNbWagon.setText(String.valueOf(wagon-1));
			 lblSelectNbBoat.setText(String.valueOf(boat+1));
		 }
	 }
	 
	 @FXML
	 private void addPionWBoat(ActionEvent e){
		 int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		 int boat = Integer.parseInt(lblSelectNbBoat.getText());
		 if(wagon>0&&boat>=0){
			 lblSelectNbWagon.setText(String.valueOf(wagon-1));
			 lblSelectNbBoat.setText(String.valueOf(boat+1));
		 }
	 }
	 
	 @FXML
	 private void removePionBoat(ActionEvent e){
		 int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		 int boat = Integer.parseInt(lblSelectNbBoat.getText());
		 if(wagon>=0&&boat>0){
			 lblSelectNbWagon.setText(String.valueOf(wagon+1));
			 lblSelectNbBoat.setText(String.valueOf(boat-1));
		 }
	 }
	 
	 @FXML
	 private void confirmPion(ActionEvent e){
		 panePion.toBack();
		 int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		 int boat = Integer.parseInt(lblSelectNbBoat.getText());
		 
		 plateauControlle.setPion(wagon, boat);
		 printPion(wagon, boat, 3);
		 
		
	 }
	 
	 public void printPion(int wagon, int boat, int port){
		 lblPionWagon.setText(String.valueOf(wagon));
		 lblPionBoat.setText(String.valueOf(boat));
		 lblPionPort.setText(String.valueOf(port));
	 }
	 
	 public void printScore(int score){
		 lblScore.setText(String.valueOf(score));
	 }
	 
	 public void printMsgGame(String msg){
		 Platform.runLater(() -> {
			 lblMsgGame.setText(msg);
			});
	 }
	 
	 @FXML
	 private void printAllScore(ActionEvent e){
		 
		 
		 if(stageScore.isShowing()){
			 stageScore.close();
		 }
		 Platform.runLater(() -> {
			 windowsScore(plateauControlle.getPlateauJeu().getListJoueur());
		 });
	 }
	 
	 public void setListJoueurAtScoreView(Map<Integer,Joueur> listJoueurs){
		 Platform.runLater(() -> {
			 if(score!=null){
				 score.setListJoueur(listJoueurs);
			 }else{
				 windowsScore(listJoueurs);
			 }
		 });
	 }
	 
	 private void windowsScore(Map<Integer,Joueur> listJoueurs){
			
			Parent root = null;
			score = new Score();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Score.fxml"));
			loader.setController(score);
			try {
				root = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root);
			score.setScene(scene);
			score.setListJoueur(listJoueurs);
			
			stageScore.setScene(scene);
			stageScore.setTitle("Score des joueurs");
			stageScore.showAndWait();
			stageScore.toFront();
	 }

}
