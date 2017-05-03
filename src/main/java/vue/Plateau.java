package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controlor.PlateauController;
import ennumeration.EnumCouleur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
	private Label lblDeckBoat;

	@FXML
	private Label lblDeckWagon, lblSelectNbWagon, lblSelectNbBoat, lblPionBoat, lblPionWagon, lblScore;

	@FXML
	private Label lblDeckDestination, lblSelectDestination;

	@FXML
	private HBox HboxMain, hboxDestination, hboxDestinationSelect;

	@FXML
	private HBox HboxSelect;

	@FXML
	private Pane paneDestination, panePion;

	// rectangles des routes maritimes
	@FXML
	private Rectangle vnyedin;
	@FXML
	private Rectangle rnyedin;
	@FXML
	private Rectangle nacb;
	@FXML
	private Rectangle bcbr;
	@FXML
	private Rectangle jnyr;
	@FXML
	private Rectangle bvt;
	@FXML
	private Rectangle nlat;
	@FXML
	private Rectangle vlat;
	@FXML
	private Rectangle jlah;
	@FXML
	private Rectangle glh;
	@FXML
	private Rectangle vls;
	@FXML
	private Rectangle nls;
	@FXML
	private Rectangle jvc;
	@FXML
	private Rectangle vvba;
	@FXML
	private Rectangle bmc;
	@FXML
	private Rectangle vmc;
	@FXML
	private Rectangle rcl;
	@FXML
	private Rectangle grdjl;
	@FXML
	private Rectangle nrdjct;
	@FXML
	private Rectangle brdjct;
	@FXML
	private Rectangle vbact;
	@FXML
	private Rectangle jbact;

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
	Ville capTown = new Ville("Cape Town", true);
	Ville caracas = new Ville("Caracas", true);
	Ville casablanca = new Ville("Casablanca", true);
	Ville jakarta = new Ville("Jakarta", true);
	Ville honolulu = new Ville("Honolulu", true);
	Ville vakutsk = new Ville("Vakutsk", false);
	Ville darEsSalaam = new Ville("Dar Es Salaam", true);
	Ville djibouti = new Ville("Djibouti", false);
	Ville lahore = new Ville("Lahore", false);
	Ville edimburg = new Ville("Edimburg", true);
	Ville luanda = new Ville("Luanda", true);
	Ville hongkong = new Ville("Hong Kong", true);
	Ville hamburg = new Ville("Hamburg", true);
	Ville beijing = new Ville("Beijing", false);
	Ville lagos = new Ville("Lagos", true);
	Ville lima = new Ville("Lima", true);
	Ville tehran = new Ville("Tehran", false);
	Ville losAngelos = new Ville("Los Angeles", true);
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

	private Map<Label, Wagon> carteW = new HashMap<Label, Wagon>();
	private Map<Label, Boat> carteB = new HashMap<Label, Boat>();
	private Map<Label, Destination> carteD = new HashMap<Label, Destination>();
	private Map<Label, Iteneraire> carteI = new HashMap<Label, Iteneraire>();
	private List<Label> carteCSelect = new ArrayList<Label>();
	private String themeUrl = getClass().getResource("plateau.css").toExternalForm();

	public Plateau(PlateauController plateauController) {
		super();
		this.plateauControlle = plateauController;
		HboxMain = new HBox();
		HboxSelect = new HBox();
	}

	public void setCardsWagonInMainOfPlayer(Wagon w) {
		Label lbl = new Label();
		System.out.println(w.getLienImage());
		Image image = new Image(getClass().getResourceAsStream(w.getLienImage()));
		lbl.setGraphic(new ImageView(image));
		lbl.setOnMouseClicked(this::selectionCard);
		this.HboxMain.getChildren().add(lbl);
		this.carteW.put(lbl, w);
		System.out.println(this.carteW.size());
	}

	public void setCardsBoatInMainOfPlayer(Boat b) {
		Label lbl = new Label();
		System.out.println(b.getLienImage());
		Image image = new Image(getClass().getResourceAsStream(b.getLienImage()));
		lbl.setGraphic(new ImageView(image));
		lbl.setOnMouseClicked(this::selectionCard);
		this.HboxMain.getChildren().add(lbl);
		this.carteB.put(lbl, b);
		System.out.println(this.carteB.size());
	}

	public void setCardsDestinationForChoice(Destination destination) {
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

	public void setCardsIteneraireForChoice(Iteneraire ite) {
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
	 * 
	 * @param e
	 */
	@FXML
	private void takeHideBoatCard(MouseEvent e) {
		System.out.println("clicked boat");
		plateauControlle.piocheCards("bateau");
	}

	/**
	 * Permet de piocher une carte train
	 * 
	 * @param e
	 */
	@FXML
	private void takeHideTrainCard(MouseEvent e) {
		plateauControlle.piocheCards("wagon");
	}

	@FXML
	private void takeHideDestinationCard(MouseEvent e) {
		// Appel fonction pioche dans metier
		plateauControlle.piocheCards("destination");
	}

	/**
	 * Permet de prendre la carte de la main du joueur pour pouvoir la jouer
	 * 
	 * @param e
	 */
	@FXML
	private void selectionCard(MouseEvent e) {
		Label lbl = (Label) e.getSource();
		HboxMain.getChildren().remove(lbl);
		lbl.setOnMouseClicked(this::deSelectionCard);
		HboxSelect.getChildren().add(lbl);

	}

	@FXML
	private void selectionCardDestination(MouseEvent e) {
		Label lbl = (Label) e.getSource();
		carteCSelect.add(lbl);
		lbl.setOnMouseClicked(this::deSelectionCardDestination);
		lbl.getStyleClass().clear();
		lbl.getStyleClass().add("lblCadre");
	}

	@FXML
	private void confirmeChoiceDestination(ActionEvent e) {
		List<Iteneraire> iteSelect = new ArrayList<Iteneraire>();
		List<Destination> desSelect = new ArrayList<Destination>();
		List<Iteneraire> iteNonSelect = new ArrayList<Iteneraire>();
		List<Destination> desNonSelect = new ArrayList<Destination>();
		Map<Label, Destination> carteD2 = carteD;
		Map<Label, Iteneraire> carteI2 = carteI;

		int i = 0;
		int j = 0;
		for (i = 0; i < carteCSelect.size(); i++) {
			if (carteD.containsKey(carteCSelect.get(i))) {
				Destination d = carteD.get(carteCSelect.get(i));
				desSelect.add(d);
				carteD2.remove(carteCSelect.get(i));
			} else if (carteI.containsKey(carteCSelect.get(i))) {
				Iteneraire ite = carteI.get(carteCSelect.get(i));
				iteSelect.add(ite);
				carteI2.remove(carteCSelect.get(i));
			}
		}
		for (i = 0; i < carteD2.size(); i++) {
			desNonSelect.add(carteD2.get(i));
		}
		for (i = 0; i < carteI2.size(); i++) {
			iteNonSelect.add(carteI2.get(i));
		}

		System.out.println("go controller " + desSelect.size() + " " + iteSelect.size());
		plateauControlle.takeCardsDestination(desSelect, desNonSelect, iteSelect, iteNonSelect);
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
	 * Remettre la carte dans la main du joueur à partir de la carte
	 * sélectionnée par la souris
	 */
	@FXML
	private void deSelectionCard(MouseEvent e) {
		Label lbl = (Label) e.getSource();
		deSelectionCard(lbl);
	}

	@FXML
	private void deSelectionCardDestination(MouseEvent e) {
		Label lbl = (Label) e.getSource();
		if (carteCSelect.contains(lbl)) {
			carteCSelect.remove(lbl);
		}
		lbl.setOnMouseClicked(this::selectionCardDestination);
		lbl.getStyleClass().clear();
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
		int i;
		for (i = nb; i >= 0; i--) {
			Label lbl = listLbl.get(i);
			HboxSelect.getChildren().remove(lbl);
			if (carteW.containsKey(lbl)) {
				Wagon b = carteW.get(lbl);
				// mettre carte wagon dans la défausse
				carteW.remove(lbl);
			} else if (carteB.containsKey(lbl)) {
				Boat b = carteB.get(lbl);
				// mettre carte bateau dans la défausse
				carteB.remove(lbl);
			}
		}
	}

	/**
	  * Prendre une route maritime. On vérifie si le joueur a mis assez de carte en jeu.
	  * Si c'est le cas, la route est prise sinon les cartes retournent dans la main du joueur
	  * @param e
	  */
	 @FXML
	 private void takeRoadBoatMap(MouseEvent e){
		 Rectangle rect = (Rectangle) e.getSource();
		 RouteMartime r = null;
		 switch (rect.getId())
		 {
		 case "vnyedin":   
			 r = new RouteMartime(7, EnumCouleur.VIOLET, ny, edimburg);
			 break;
		 case "rnyedin":   
			 r = new RouteMartime(7, EnumCouleur.ROUGE, ny, edimburg);
			 break;
		 case "nacb":  
			 r = new RouteMartime(6, EnumCouleur.NOIR, anchorage, cambridgeBay);
			 break;
		 case "bcbr":  
			 r = new RouteMartime(6, EnumCouleur.BLANC, cambridgeBay, reykjavik);
			 break;
		 case "jnyr":  
			 r = new RouteMartime(6, EnumCouleur.JAUNE, ny, reykjavik);
			 break;
		 case "vmc":  
			 r = new RouteMartime(7, EnumCouleur.VERT, miami, casablanca);
			 break;
		 case "bmc":  
			 r = new RouteMartime(2, EnumCouleur.BLANC, miami, caracas);
			 break;
		 case "rcl":  
			 r = new RouteMartime(7, EnumCouleur.ROUGE, caracas, lagos);
			 break;
		 case "grdjl":  
			 r = new RouteMartime(6, EnumCouleur.GRIS, rio, luanda);
			 break;
		 case "nrdjct":  
			 r = new RouteMartime(6, EnumCouleur.NOIR, rio, capTown);
			 break;
		 case "brdjct":  
			 r = new RouteMartime(6, EnumCouleur.BLANC, rio, capTown);
			 break;
		 case "vbact":  
			 r = new RouteMartime(7, EnumCouleur.VIOLET, buenos, capTown);
			 break;
		 case "jbact":  
			 r = new RouteMartime(7, EnumCouleur.JAUNE, buenos, capTown);
			 break;
		 
		 }
		  
		 
		 int carte=0;
		 int joker=0;
		 
		 List<Label> listLbl = new ArrayList<Label>();
		 
		 int i=0;
		 for(i=0;i<HboxSelect.getChildren().size();i++){
			 if(carteB.containsKey(HboxSelect.getChildren().get(i))){
				 Boat b = carteB.get(HboxSelect.getChildren().get(i));
				 
				 if(b.getCouleur()==r.getCouleur()){
					 
					 carte++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
					 if(b.isDoubleBoat()){
						 carte++;
					 }
					 
				 }
			 }else if(carteW.containsKey(HboxSelect.getChildren().get(i))){
				 Wagon c = carteW.get(HboxSelect.getChildren().get(i));
				 if(c.isJoker()){
					 joker++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
				 }
			 }
		 }
		 
		 if((joker+carte)>=r.getNbPion()){
			 mettreCarteDansDefausse(r.getNbPion()-1,listLbl);
			 rect.setOpacity(100);
			 
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
		RouteTerrestre r = new RouteTerrestre(7, EnumCouleur.VIOLET, null, null);
		int carte = 0;
		int joker = 0;

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

		if ((joker + carte) >= r.getNbPion()) {
			mettreCarteDansDefausse(r.getNbPion() - 1, listLbl);

		}
		// s'il reste des cartes en trop
		deSelectionAllCard();
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
		Ville v = new Ville("Marseille", true);
		int carteBoat = 0;
		int carteWagon = 0;
		int jokerBoat = 0;
		int jokerWagon = 0;

		List<Label> listLbl = new ArrayList<Label>();

		int i = 0;
		for (i = 0; i < HboxSelect.getChildren().size(); i++) {
			if (carteW.containsKey(HboxSelect.getChildren().get(i))) {
				Wagon c = carteW.get(HboxSelect.getChildren().get(i));

				if (c.isPort()) {

					carteWagon++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
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
					carteBoat++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
				}

			}
		}

		if ((carteBoat + carteWagon + jokerWagon + jokerBoat >= 4) && (carteWagon <= 2) && (carteBoat <= 2)
				&& (jokerWagon + jokerBoat <= 4)) {
			mettreCarteDansDefausse(4, listLbl);

		}
		// s'il reste des cartes en trop
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

	public void pionChoix() {
		panePion.toFront();
		lblSelectNbBoat.setText("35");
		lblSelectNbWagon.setText("25");
	}

	@FXML
	private void addPionWagon(ActionEvent e) {
		int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		int boat = Integer.parseInt(lblSelectNbBoat.getText());
		if (wagon >= 0 && boat > 0) {
			lblSelectNbWagon.setText(String.valueOf(wagon + 1));
			lblSelectNbBoat.setText(String.valueOf(boat - 1));
		}
	}

	@FXML
	private void removePionWagon(ActionEvent e) {
		int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		int boat = Integer.parseInt(lblSelectNbBoat.getText());
		if (wagon > 0 && boat >= 0) {
			lblSelectNbWagon.setText(String.valueOf(wagon - 1));
			lblSelectNbBoat.setText(String.valueOf(boat + 1));
		}
	}

	@FXML
	private void addPionWBoat(ActionEvent e) {
		int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		int boat = Integer.parseInt(lblSelectNbBoat.getText());
		if (wagon > 0 && boat >= 0) {
			lblSelectNbWagon.setText(String.valueOf(wagon - 1));
			lblSelectNbBoat.setText(String.valueOf(boat + 1));
		}
	}

	@FXML
	private void removePionBoat(ActionEvent e) {
		int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		int boat = Integer.parseInt(lblSelectNbBoat.getText());
		if (wagon >= 0 && boat > 0) {
			lblSelectNbWagon.setText(String.valueOf(wagon + 1));
			lblSelectNbBoat.setText(String.valueOf(boat - 1));
		}
	}

	@FXML
	private void confirmPion(ActionEvent e) {
		panePion.toBack();
		int wagon = Integer.parseInt(lblSelectNbWagon.getText());
		int boat = Integer.parseInt(lblSelectNbBoat.getText());

		plateauControlle.setPion(wagon, boat);
		lblPionWagon.setText(String.valueOf(wagon));
		lblPionBoat.setText(String.valueOf(boat));

	}

	public void printScore(int score) {
		lblScore.setText(String.valueOf(score));
	}

}
