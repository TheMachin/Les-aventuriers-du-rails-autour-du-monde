package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.engine.execution.NamespaceAwareStore;

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
	Ville capeTown = new Ville("Cape Town", true);
	Ville caracas = new Ville("Caracas", true);
	Ville casablanca = new Ville("Casablanca", true);
	Ville jakarta = new Ville("Jakarta", true);
	Ville honolulu = new Ville("Honolulu", true);
	Ville yakutsk = new Ville("Yakutsk", false);
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
		switch (rect.getId()) {
		case "vnyedin":
			r = new RouteMartime(7, EnumCouleur.VIOLET, ny, edinburgh);
			break;
		case "rnyedin":
			r = new RouteMartime(7, EnumCouleur.ROUGE, ny, edinburgh);
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
			r = new RouteMartime(6, EnumCouleur.NOIR, rio, capeTown);
			break;
		case "brdjct":
			r = new RouteMartime(6, EnumCouleur.BLANC, rio, capeTown);
			break;
		case "vbact":
			r = new RouteMartime(7, EnumCouleur.VIOLET, buenos, capeTown);
			break;
		case "jbact":
			r = new RouteMartime(7, EnumCouleur.JAUNE, buenos, capeTown);
			break;
		case "bedinm":
			r = new RouteMartime(1, EnumCouleur.BLANC, edinburgh, marseille);
			break;
		case "vedinm":
			r = new RouteMartime(1, EnumCouleur.VERT, edinburgh, marseille);
			break;
		case "rma":
			r = new RouteMartime(2, EnumCouleur.ROUGE, marseille, athina);
			break;
		case "vrm":
			r = new RouteMartime(4, EnumCouleur.VERT, reykjavik, murmansk);
			break;
		case "rmt":
			r = new RouteMartime(7, EnumCouleur.ROUGE, murmansk, tiksi);
			break;
		case "jta1":
		case "jta2":
			r = new RouteMartime(8, EnumCouleur.JAUNE, tiksi, anchorage);
			break;
		case "ntp1":
		case "ntp2":
		case "ntp3":
			r = new RouteMartime(7, EnumCouleur.NOIR, tiksi, petropavlovsk);
			break;
		case "vpa":
			r = new RouteMartime(3, EnumCouleur.VIOLET, petropavlovsk, anchorage);
			break;
		case "gtp":
			r = new RouteMartime(2, EnumCouleur.GRIS, tokyo, petropavlovsk);
			break;
		case "gjm":
			r = new RouteMartime(2, EnumCouleur.GRIS, jakarta, manila);
			break;
		case "vaalq":
			r = new RouteMartime(1, EnumCouleur.VERT, athina, alqahira);
			break;
		case "rdpm":
			r = new RouteMartime(1, EnumCouleur.ROUGE, darwin, portMoresby);
			break;
		case "njd":
			r = new RouteMartime(2, EnumCouleur.NOIR, jakarta, darwin);
			break;
		case "videsj":
			r = new RouteMartime(7, EnumCouleur.VIOLET, darEsSalaam, jakarta);
			break;
		case "vedesj":
			r = new RouteMartime(7, EnumCouleur.VERT, darEsSalaam, jakarta);
			break;
		case "neh":
			r = new RouteMartime(1, EnumCouleur.NOIR, edinburgh, hamburg);
			break;
		case "jeh":
			r = new RouteMartime(1, EnumCouleur.JAUNE, edinburgh, hamburg);
			break;
		case "jmt":
			r = new RouteMartime(2, EnumCouleur.JAUNE, manila, tokyo);
			break;
		case "gre1":
		case "gre2":
			r = new RouteMartime(2, EnumCouleur.GRIS, reykjavik, edinburgh);
			break;
		case "bbj1":
		case "bbj2":
			r = new RouteMartime(2, EnumCouleur.BLANC, bangkok, jakarta);
			break;
		case "bdesm1":
		case "bdesm2":
			r = new RouteMartime(4, EnumCouleur.BLANC, darEsSalaam, mumbai);
			break;
		case "ghkt1":
		case "ghkt2":
			r = new RouteMartime(3, EnumCouleur.GRIS, hongkong, tokyo);
			break;
		case "bmh1":
		case "bmh2":
			r = new RouteMartime(5, EnumCouleur.BLANC, manila, honolulu);
			break;
		case "vpmh1":
		case "vpmh2":
			r = new RouteMartime(3, EnumCouleur.VERT, portMoresby, honolulu);
			break;
		case "gjp1":
		case "gjp2":
			r = new RouteMartime(3, EnumCouleur.GRIS, jakarta, perth);
			break;
		case "jpms1":
		case "jpms2":
			r = new RouteMartime(3, EnumCouleur.JAUNE, portMoresby, sydney);
			break;
		case "bsc":
			r = new RouteMartime(1, EnumCouleur.BLANC, sydney, christchurch);
			break;
		case "rsc":
			r = new RouteMartime(1, EnumCouleur.ROUGE, sydney, christchurch);
			break;
		case "rbm1":
		case "rbm2":
			r = new RouteMartime(2, EnumCouleur.ROUGE, bangkok, manila);
			break;
		case "jdest":
			r = new RouteMartime(1, EnumCouleur.JAUNE, darEsSalaam, toamasina);
			break;
		case "gctt":
			r = new RouteMartime(3, EnumCouleur.GRIS, capeTown, toamasina);
			break;
		case "vvba1":
		case "vvba2":
		case "vvba3":
			r = new RouteMartime(3, EnumCouleur.VERT, valparaiso, buenos);
			break;
		case "rctn":
			r = new RouteMartime(5, EnumCouleur.ROUGE, capeTown, null);
			break;
		case "vctn":
			r = new RouteMartime(5, EnumCouleur.VERT, capeTown, null);
			break;
		case "bnp":
			r = new RouteMartime(5, EnumCouleur.BLANC, null, perth);
			break;
		case "vnp":
			r = new RouteMartime(5, EnumCouleur.VIOLET, null, perth);
			break;
		case "rth1":
		case "rth2":
		case "rth3":
			r = new RouteMartime(5, EnumCouleur.ROUGE, tokyo, honolulu);
			break;
		case "jcv1":
		case "jcv2":
			r = new RouteMartime(7, EnumCouleur.JAUNE, christchurch, valparaiso);
			break;
		case "vsl1":
		case "vsl2":
			r = new RouteMartime(8, EnumCouleur.VIOLET, sydney, lima);
			break;
		case "nsl1":
		case "nsl2":
			r = new RouteMartime(8, EnumCouleur.NOIR, sydney, lima);
			break;
		case "ghl":
			r = new RouteMartime(6, EnumCouleur.GRIS, honolulu, lima);
			break;
		case "jhla1":
		case "jhla2":
			r = new RouteMartime(3, EnumCouleur.JAUNE, honolulu, losAngeles);
			break;
		case "vtla1":
		case "vtla2":
		case "vtla3":
		case "vtla4":
		case "vtla5":
		case "vtla6":
			r = new RouteMartime(7, EnumCouleur.VERT, tokyo, losAngeles);
			break;
		case "ntla1":
		case "ntla2":
		case "ntla3":
		case "ntla4":
		case "ntla5":
		case "ntla6":
			r = new RouteMartime(7, EnumCouleur.NOIR, tokyo, losAngeles);
			break;
		case "btv1":
		case "btv2":
		case "btv3":
			r = new RouteMartime(6, EnumCouleur.BLANC, tokyo, vancouver);
			break;
		}

		int carte = 0;
		int joker = 0;

		List<Label> listLbl = new ArrayList<Label>();

		int i = 0;
		for (i = 0; i < HboxSelect.getChildren().size(); i++) {
			if (carteB.containsKey(HboxSelect.getChildren().get(i))) {
				Boat b = carteB.get(HboxSelect.getChildren().get(i));

				if (b.getCouleur() == r.getCouleur()) {

					carte++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
					if (b.isDoubleBoat()) {
						carte++;
					}

				}
			} else if (carteW.containsKey(HboxSelect.getChildren().get(i))) {
				Wagon c = carteW.get(HboxSelect.getChildren().get(i));
				if (c.isJoker()) {
					joker++;
					listLbl.add((Label) HboxSelect.getChildren().get(i));
				}
			}
		}

		if ((joker + carte) >= r.getNbPion()) {
			mettreCarteDansDefausse(r.getNbPion() - 1, listLbl);
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
		Rectangle rect = (Rectangle) e.getSource();
		RouteTerrestre r = null;
		//RouteTerrestre r = new RouteTerrestre(7, EnumCouleur.VIOLET, null, null);
		int carte = 0;
		int joker = 0;

		switch (rect.getId()) {
			case "jvw":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, vancouver, winnipeg);
				break;
			case "vwny":
				r = new RouteTerrestre(2, EnumCouleur.VERT, winnipeg, ny);
				break;
			case "rvla":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, vancouver, losAngeles);
				break;
			case "vvla":
				r = new RouteTerrestre(1, EnumCouleur.VERT, vancouver, losAngeles);
				break;
			case "glaw":
				r = new RouteTerrestre(3, EnumCouleur.GRIS, losAngeles, winnipeg);
				break;
			case "rpd":
				r = new RouteTerrestre(2, EnumCouleur.ROUGE, perth, darwin);
				break;
			case "vds":
				r = new RouteTerrestre(2, EnumCouleur.VERT, darwin, sydney);
				break;
			case "bps":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, perth, sydney);
				break;
			case "jps":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, perth, sydney);
				break;
			case "blam":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, losAngeles, mexico);
				break;
			case "jlam":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, losAngeles, mexico);
				break;
			case "vlany":
				r = new RouteTerrestre(4, EnumCouleur.VIOLET, losAngeles, ny);
				break;
			case "nlany":
				r = new RouteTerrestre(4, EnumCouleur.NOIR, losAngeles, ny);
				break;
			case "vimc":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, mexico, caracas);
				break;
			case "rmc":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, mexico, caracas);
				break;
			case "bmny1":
			case "bmny2":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, miami, ny);
				break;
			case "jlc":
				r = new RouteTerrestre(2, EnumCouleur.JAUNE, lima, caracas);
				break;
			case "blc":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, lima, caracas);
				break;
			case "vcrdj1":
			case "vcrdj2":
			case "vcrdj3":
				r = new RouteTerrestre(4, EnumCouleur.VERT, caracas, rio);
				break;
			case "ncrdj1":
			case "ncrdj2":
			case "ncrdj3":
				r = new RouteTerrestre(4, EnumCouleur.NOIR, caracas, rio);
				break;
			case "glvg":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, lima, valparaiso);
				break;
			case "glvd":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, lima, valparaiso);
				break;
			case "bbardj":
				r = new RouteTerrestre(1, EnumCouleur.BLANC, buenos, rio);
				break;
			case "rbardj":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, buenos, rio);
				break;
			case "vectdes":
				r = new RouteTerrestre(3, EnumCouleur.VERT, capeTown, darEsSalaam);
				break;
			case "victdes":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, capeTown, darEsSalaam);
				break;
			case "glct":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, luanda, capeTown);
				break;
			case "jll":
				r = new RouteTerrestre(1, EnumCouleur.JAUNE, lagos, luanda);
				break;
			case "vll":
				r = new RouteTerrestre(1, EnumCouleur.VIOLET, lagos, luanda);
				break;
			case "gcl1":
			case "gcl2":
				r = new RouteTerrestre(4, EnumCouleur.GRIS, casablanca, lagos);
				break;
			case "gcaq":
				r = new RouteTerrestre(3, EnumCouleur.GRIS, casablanca, alqahira);
				break;
			case "raqd":
				r = new RouteTerrestre(2, EnumCouleur.ROUGE, alqahira, djibouti);
				break;
			case "baqd":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, alqahira, djibouti);
				break;
			case "rddes":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, djibouti, darEsSalaam);
				break;
			case "nddes":
				r = new RouteTerrestre(1, EnumCouleur.NOIR, djibouti, darEsSalaam);
				break;
			case "rmh":
				r = new RouteTerrestre(1, EnumCouleur.ROUGE, marseille, hamburg);
				break;
			case "vmh":
				r = new RouteTerrestre(1, EnumCouleur.VIOLET, marseille, hamburg);
				break;
			case "romt":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, moskva, tehran);
				break;
			case "vmm":
				r = new RouteTerrestre(2, EnumCouleur.VIOLET, murmansk, moskva);
				break;
			case "vha1":
			case "vha2":
				r = new RouteTerrestre(2, EnumCouleur.VERT, hamburg, athina);
				break;
			case "gat":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, athina, tehran);
				break;
			case "bhm":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, hamburg, moskva);
				break;
			case "nhm":
				r = new RouteTerrestre(2, EnumCouleur.NOIR, hamburg, moskva);
				break;
			case "vmn":
				r = new RouteTerrestre(4, EnumCouleur.VERT, moskva, novosibirsk);
				break;
			case "jmn":
				r = new RouteTerrestre(4, EnumCouleur.JAUNE, moskva, novosibirsk);
				break;
			case "rnb":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, novosibirsk, beijing);
				break;
			case "nnb":
				r = new RouteTerrestre(3, EnumCouleur.NOIR, novosibirsk, beijing);
				break;
			case "bbhk":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, beijing, hongkong);
				break;
			case "vbhk":
				r = new RouteTerrestre(2, EnumCouleur.VERT, beijing, hongkong);
				break;
			case "gnt":
				r = new RouteTerrestre(3, EnumCouleur.GRIS, novosibirsk, tiksi);
				break;
			case "vny":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, novosibirsk, yakutsk);
				break;
			case "vty":
				r = new RouteTerrestre(1, EnumCouleur.VERT, tiksi, yakutsk);
				break;
			case "bnl":
				r = new RouteTerrestre(2, EnumCouleur.BLANC, novosibirsk, lahore);
				break;
			case "jyb":
				r = new RouteTerrestre(3, EnumCouleur.JAUNE, yakutsk, beijing);
				break;
			case "byp":
				r = new RouteTerrestre(3, EnumCouleur.BLANC, yakutsk, petropavlovsk);
				break;
			case "nlm":
				r = new RouteTerrestre(1, EnumCouleur.NOIR, lahore, mumbai);
				break;
			case "vlm":
				r = new RouteTerrestre(1, EnumCouleur.VERT, lahore, mumbai);
				break;
			case "nwcb":
				r = new RouteTerrestre(4, EnumCouleur.NOIR, winnipeg, cambridgeBay);
				break;
			case "btm1":
			case "btm2":
			case "btm3":
				r = new RouteTerrestre(3, EnumCouleur.BLANC, tehran, mumbai);
				break;
			case "vtm1":
			case "vtm2":
			case "vtm3":
				r = new RouteTerrestre(3, EnumCouleur.VIOLET, tehran, mumbai);
				break;
			case "rmb1":
			case "rmb2":
			case "rmb3":
				r = new RouteTerrestre(3, EnumCouleur.ROUGE, mumbai, bangkok);
				break;
			case "jmb1":
			case "jmb2":
			case "jmb3":
				r = new RouteTerrestre(3, EnumCouleur.JAUNE, mumbai, bangkok);
				break;
			case "vhkb":
				r = new RouteTerrestre(1, EnumCouleur.VIOLET, hongkong, bangkok);
				break;
			case "nhkb":
				r = new RouteTerrestre(1, EnumCouleur.NOIR, hongkong, bangkok);
				break;
			case "dcm":
				r = new RouteTerrestre(2, EnumCouleur.GRIS, casablanca, marseille);
				break;
			case "dldes":
				r = new RouteTerrestre(4, EnumCouleur.GRIS, luanda, darEsSalaam);
				break;
			case "dtl":
				r = new RouteTerrestre(4, EnumCouleur.GRIS, tehran, lahore);
				break;
			case "dlb":
				r = new RouteTerrestre(6, EnumCouleur.GRIS, lahore, beijing);
				break;
			case "jaqt":
				r = new RouteTerrestre(1, EnumCouleur.JAUNE, alqahira, tehran);
				break;
			case "naqt":
				r = new RouteTerrestre(1, EnumCouleur.NOIR, alqahira, tehran);
				break;
			case "dva1":
			case "dva2":
				r = new RouteTerrestre(4, EnumCouleur.GRIS, vancouver, anchorage);
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
		//Ville v = new Ville("Marseille", true);
		Rectangle rect = (Rectangle) e.getSource();
		Ville v = null;

		switch (rect.getId()) {
			case "pmarseille":
				v = marseille;
				break;
			case "phamburg":
				v = hamburg;
				break;
			case "pvanc":
				v = vancouver;
				break;
			case "pla":
				v = losAngeles;
				break;
			case "pny":
				v = ny;
				break;
			case "pmiami":
				v = miami;
				break;
			case "pcaracas":
				v = caracas;
				break;
			case "plima":
				v = lima;
				break;
			case "pvalparaiso":
				v = valparaiso;
				break;
			case "pba":
				v = buenos;
				break;
			case "prdj":
				v = rio;
				break;
			case "pct":
				v = capeTown;
				break;
			case "pluanda":
				v = luanda;
				break;
			case "plagos":
				v = lagos;
				break;
			case "pcasablanca":
				v = casablanca;
				break;
			case "paq":
				v = alqahira;
				break;
			case "pdes":
				v = darEsSalaam;
				break;
			case "ptoamasina":
				v = toamasina;
				break;
			case "pathina":
				v = athina;
				break;
			case "pedinburgh":
				v = edinburgh;
				break;
			case "prey":
				v = reykjavik;
				break;
			case "pmurmansk":
				v = murmansk;
				break;
			case "pmumbai":
				v = mumbai;
				break;
			case "pbangkok":
				v = bangkok;
				break;
			case "ptiksi":
				v = tiksi;
				break;
			case "panchorage":
				v = anchorage;
				break;
			case "ppetropavlovsk":
				v = petropavlovsk;
				break;
			case "ptokyo":
				v = tokyo;
				break;
			case "phk":
				v = hongkong;
				break;
			case "pmanila":
				v = manila;
				break;
			case "pjakarta":
				v = jakarta;
				break;
			case "phonolulu":
				v = honolulu;
				break;
			case "ppm":
				v = portMoresby;
				break;
			case "pdarwin":
				v = darwin;
				break;
			case "pperth":
				v = perth;
				break;
			case "psydney":
				v = sydney;
				break;
			case "pchristchurch":
				v = christchurch;
				break;
			case "pcb":
				v = cambridgeBay;
				break;
		}


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
/* test */