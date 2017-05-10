package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Joueur implements Visitable{
	private String name;
	private EnumCouleur couleur;
	private int score;
	private int bonus;
	private int malus;
	private Pions pions;
	private boolean start;
	private List<Destination> cartesD = new ArrayList<Destination>();
	private List<Iteneraire> cartesI = new ArrayList<Iteneraire>();
	private List<Wagon> cartesW = new ArrayList<Wagon>();
	private List<Boat> cartesB = new ArrayList<Boat>();
 	
	/**
	 * @param name
	 * @param couleur
	 * @param score
	 * @param bonus
	 * @param malus
	 * @param nbWagon
	 * @param nbBoat
	 */
	public Joueur(String name, EnumCouleur couleur, int score, int bonus, int malus) {
		super();
		this.name = name;
		this.couleur = couleur;
		this.score = score;
		this.bonus = bonus;
		this.malus = malus;
		pions = new Pions(couleur, 0, 0);
	}
	
	public Joueur(String name, EnumCouleur couleur, int score, int bonus, int malus,boolean start) {
		super();
		this.name = name;
		this.couleur = couleur;
		this.score = score;
		this.bonus = bonus;
		this.malus = malus;
		this.start=start;
		pions = new Pions(couleur, 0, 0);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getMalus() {
		return malus;
	}
	public void setMalus(int malus) {
		this.malus = malus;
	}
	public EnumCouleur getCouleur() {
		return couleur;
	}
	public Pions getPions() {
		return pions;
	}
	public void setPions(Pions pions) {
		this.pions = pions;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setCouleur(EnumCouleur couleur) {
		this.couleur = couleur;
	}
	
	public void addDestination(Destination d){
		cartesD.add(d);
	}
	
	public void addIteneraire(Iteneraire i){
		cartesI.add(i);
	}
	
	public void addWagon(Wagon w){
		cartesW.add(w);
	}
	
	public void deleteWagon(Wagon w){
		if(cartesW.contains(w)){
			cartesW.remove(w);
		}
	}
	
	public void addBoat(Boat b){
		cartesB.add(b);
	}
	
	public void deleteBoat(Boat b){
		if(cartesB.contains(b)){
			cartesB.remove(b);
		}
	}

	@Override
	public String toString() {
		return "Joueur [name=" + name + ", couleur=" + couleur + ", score=" + score + ", bonus=" + bonus + ", malus="
				+ malus + ", pions=" + pions + ", start=" + start + "]";
	}
	
	public boolean checkIfHimTakeRoadWagon(RouteTerrestre r){
		return pions.checkIfRoadWagonIsTake(r);
	}
	
	public boolean checkIfHimTakeRoadBoat(RouteMartime r){
		return pions.checkIfRoadBoatIsTake(r);
	}
	
	public boolean checkIfHimTakePort(Ville v){
		return pions.checkIfPortnIsTake(v);
	}
	
	public void calculScoreTakeRoad(int value){
		if(value!=0){
			if(value<=2){
				this.score=this.score+value;
			}else if(value==3){
				this.score=this.score+4;
			}else if(value==4){
				this.score=this.score+7;
			}else if(value==5){
				this.score=this.score+10;
			}else if(value==6){
				this.score=this.score+15;
			}else if(value==7){
				this.score=this.score+18;
			}else if(value==8){
				this.score=this.score+21;
			}
		}
	}
	
	public int calculScore(){
		
		int i;
		for(i=0;i<cartesD.size();i++){
			if(getPions().checkCityIsConnectedToRoad(cartesD.get(i).getV1())&&getPions().checkCityIsConnectedToRoad(cartesD.get(i).getV2())){
				bonus=bonus+cartesD.get(i).getPoint();
			}
		}
		
		for(i=0;i<cartesI.size();i++){
			Map<Integer,Ville> villes = cartesI.get(i).getIteneraire();
			
		}
		
		this.score  = getScore()+getBonus()-getMalus();
		return this.score ;
	}

	public void deleteDestination(Destination d) {
		// TODO Auto-generated method stub
		if(cartesD.contains(d)){
			cartesD.remove(d);
		}
	}
	
	public void deleteIteneraire(Iteneraire d) {
		// TODO Auto-generated method stub
		if(cartesI.contains(d)){
			cartesI.remove(d);
		}
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
