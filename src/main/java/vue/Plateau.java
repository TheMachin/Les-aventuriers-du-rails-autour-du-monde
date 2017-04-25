package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ennumeration.EnumCouleur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.Main;
import metier.Boat;
import metier.RouteMartime;
import metier.RouteTerrestre;
import metier.Ville;
import metier.Wagon;

public class Plateau implements InterfacePlateau{

	
	@FXML 
	private Label lblDeckBoat;
	
	@FXML 
	private Label lblDeckWagon;
	
	@FXML 
	private Label lblDeckDestination;

	@FXML 
	private HBox HboxMain;
	
	@FXML 
	private HBox HboxSelect;

	
	private Main main;
	
	private Map<Label,Wagon> carteW = new HashMap<Label,Wagon>();
	private Map<Label,Boat> carteB = new HashMap<Label,Boat>();
	/**
	 * 
	 */
	public Plateau() {
		super();
	}
	
	 /**
	  * Permet de piocher une carte bateau
	  * @param e
	  */
	 @FXML
	 private void takeHideBoatCard(MouseEvent e) {
		System.out.println("clicked boat");
		//Appel fonction pioche dans metier
		Label lbl = new Label();
		Image image = new Image(getClass().getResourceAsStream("/images/wagonviolet.PNG"));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCard);
	    this.HboxMain.getChildren().add(lbl);
	    this.carteB.put(lbl, new Boat(EnumCouleur.VIOLET, false, false));
	    System.out.println(this.carteB.size());
		
	 }
	 
	 /**
	  * Permet de piocher une carte train
	  * @param e
	  */
	 @FXML
	 private void takeHideTrainCard(MouseEvent e) {
		 System.out.println("clicked train");
		//Appel fonction pioche dans metier
	    Label lbl = new Label();
		Image image = new Image(getClass().getResourceAsStream("/images/wagonviolet.PNG"));
	    lbl.setGraphic(new ImageView(image));
	    lbl.setOnMouseClicked(this::selectionCard);
	    this.HboxMain.getChildren().add(lbl);
	    this.carteW.put(lbl, new Wagon(EnumCouleur.VIOLET, false, false));
	    System.out.println(this.carteW.size());
	 }
	 
	 
	 @FXML
	 private void takeHideDestinationCard(MouseEvent e) {
		//Appel fonction pioche dans metier
		 System.out.println("clicked destination");
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
	 
	 /**
	  * Remettre la carte dans la main du joueur à partir de la carte sélectionnée par la souris
	  */
	 @FXML
	 private void deSelectionCard(MouseEvent e){
		 
		 Label lbl = (Label) e.getSource();
		 deSelectionCard(lbl);
	 }
	 
	 /**
	  * Action pour remettre la carte dans la main du joueur
	  * @param lbl
	  */
	 private void deSelectionCard(Label lbl){
		 HboxSelect.getChildren().remove(lbl);
		 lbl.setOnMouseClicked(this::selectionCard);
		 HboxMain.getChildren().add(lbl);
	 }
	 
	 
	 public void deSelectionAllCard(){
		 int i =0;
		 for(i=HboxSelect.getChildren().size()-1;i>=0;i--){
			 deSelectionCard((Label) HboxSelect.getChildren().get(i));
		 }
	 }
	 
	 public void mettreCarteDansDefausse(int nb,List<Label> listLbl){
		 int i;
		 for(i=nb;i>=0;i--){
			 Label lbl = listLbl.get(i);
			 HboxSelect.getChildren().remove(lbl);
			 if(carteW.containsKey(lbl)){
				 Wagon b = carteW.get(lbl);
			 	//mettre carte wagon dans la défausse
				 carteW.remove(lbl);
			 }else if(carteB.containsKey(lbl)){
				 Boat b  = carteB.get(lbl);
				 //mettre carte bateau dans la défausse
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
		 RouteMartime r = new RouteMartime(7, EnumCouleur.VIOLET, null, null);
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
			 /*for(i=r.getNbPion()-1;i>=0;i--){
				 Label lbl = listLbl.get(i);
				 HboxSelect.getChildren().remove(lbl);
				 if(carteW.containsKey(lbl)){
					 Wagon b = carteW.get(lbl);
				 	//mettre carte wagon dans la défausse
					 carteW.remove(lbl);
				 }else if(carteB.containsKey(lbl)){
					 Boat b  = carteB.get(lbl);
					 //mettre carte bateau dans la défausse
					 carteB.remove(lbl);
				 }
			 }*/
			 mettreCarteDansDefausse(r.getNbPion()-1,listLbl);
			 
		 }
		 // s'il reste des cartes en trop
		 deSelectionAllCard(); 
	 }
	 
	 /**
	  * Prendre une route terreste. On vérifie si le joueur a mis assez de carte en jeu.
	  * Si c'est le cas, la route est prise sinon les cartes retournent dans la main du joueur
	  * @param e
	  */
	 @FXML
	 private void takeRoadWagonMap(MouseEvent e){
		 RouteTerrestre r = new RouteTerrestre(7, EnumCouleur.VIOLET, null, null);
		 int carte=0;
		 int joker=0;
		 
		 List<Label> listLbl = new ArrayList<Label>();
		 
		 int i=0;
		 for(i=0;i<HboxSelect.getChildren().size();i++){
			 if(carteW.containsKey(HboxSelect.getChildren().get(i))){
				 Wagon c = carteW.get(HboxSelect.getChildren().get(i));
				 
				 if(c.getCouleur()==r.getCouleur()){
					 carte++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
				 }else if(c.isJoker()){
					 joker++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
				 }
			 }
		 }
		 
		 if((joker+carte)>=r.getNbPion()){
			 /*for(i=r.getNbPion()-1;i>=0;i--){
				 Label lbl = listLbl.get(i);
				 HboxSelect.getChildren().remove(lbl);
				 Wagon c = carteW.get(lbl);
				 //mettre carte wagon dans la défausse
				 carteW.remove(lbl);
			 }*/
			 mettreCarteDansDefausse(r.getNbPion()-1,listLbl);
			 
		 }
		 // s'il reste des cartes en trop
		 deSelectionAllCard(); 
	 }
	 
	 /**
	  * Construction d'un port
	  * Il faut 2 cartes bateau et 2 cartes wagon avec le sigle port
	  * Une ou plusieurs cartes joker peuvent remplacer une carte bateau ou wagon (ou plusieurs)
	  * @param e
	  */
	 @FXML
	 private void buildPort(MouseEvent e){
		 Ville v = new Ville("Marseille", true);
		 int carteBoat=0;
		 int carteWagon=0;
		 int jokerBoat=0;
		 int jokerWagon=0;
		 
		 List<Label> listLbl = new ArrayList<Label>();
		 
		 int i=0;
		 for(i=0;i<HboxSelect.getChildren().size();i++){
			 if(carteW.containsKey(HboxSelect.getChildren().get(i))){
				 Wagon c = carteW.get(HboxSelect.getChildren().get(i));
				 
				 if(c.isPort()){
					 
					 carteWagon++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
				 }else if(c.isJoker()){
					 jokerWagon++;
					 listLbl.add((Label) HboxSelect.getChildren().get(i));
				 }
			 }else if(carteB.containsKey(HboxSelect.getChildren().get(i))){
				 	Boat b = carteB.get(HboxSelect.getChildren().get(i));
				 
				 	/**
				 	 * Pas de double bateau pour construire un port
				 	 * La carte doit être siglé port
				 	 */
				 	if(!b.isDoubleBoat()&&b.isPort()){
				 		carteBoat++;
				 		listLbl.add((Label) HboxSelect.getChildren().get(i));
					 }
				 	
			 }
		 }
		 
		 if((carteBoat+carteWagon+jokerWagon+jokerBoat>=4)&&(carteWagon<=2)&&(carteBoat<=2)&&(jokerWagon+jokerBoat<=4)){
			 /*for(i=4;i>=0;i--){
				 Label lbl = listLbl.get(i);
				 HboxSelect.getChildren().remove(lbl);
				 
				 if(carteW.containsKey(lbl)){
					 Wagon b = carteW.get(lbl);
				 	//mettre carte wagon dans la défausse
					 carteW.remove(lbl);
				 }else if(carteB.containsKey(lbl)){
					 Boat b  = carteB.get(lbl);
					 
					 carteB.remove(lbl);
				 }
			 }*/
		 	mettreCarteDansDefausse(4,listLbl);
			 
		 }
		 // s'il reste des cartes en trop
		 deSelectionAllCard(); 
	 }
	 
	 public void setMain(Main main){
		 this.main=main;
	 }
}
