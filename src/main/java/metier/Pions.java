package metier;

import java.util.ArrayList;
import java.util.List;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Pions implements Visitable{
	private EnumCouleur couleur;
	private int nbWagon;
	private int nbWagonRestant;
	private int nbBoat;
	private int nbBoatRestant;
	private int nbPort=3;
	private ArrayList<Ville> ports = new ArrayList<Ville>();
	private ArrayList<RouteTerrestre> routeTerreste = new ArrayList<RouteTerrestre>();
	private ArrayList<RouteMartime> routeMartime = new ArrayList<RouteMartime>();
	
	/**
	 * @param couleur
	 * @param nbWagon
	 * @param nbBoat
	 */
	public Pions(EnumCouleur couleur, int nbWagon, int nbBoat) {
		super();
		this.couleur = couleur;
		this.nbWagon = nbWagon;
		this.nbBoat = nbBoat;
		this.nbBoatRestant=Math.abs(nbBoat-50);
		this.nbWagonRestant=Math.abs(nbWagon-25);
	}
	
	
	public int getNbWagon() {
		return nbWagon;
	}
	public void setNbWagon(int nbWagon) {
		this.nbWagon = nbWagon;
	}
	public int getNbBoat() {
		return nbBoat;
	}
	public void setNbBoat(int nbBoat) {
		this.nbBoat = nbBoat;
	}
	public EnumCouleur getCouleur() {
		return couleur;
	}
	public int getNbPort() {
		return nbPort;
	}
	public int getNbWagonRestant() {
		return nbWagonRestant;
	}
	public void setNbWagonRestant(int nbWagonRestant) {
		this.nbWagonRestant = nbWagonRestant;
	}
	public int getNbBoatRestant() {
		return nbBoatRestant;
	}
	public void setNbBoatRestant(int nbBoatRestant) {
		this.nbBoatRestant = nbBoatRestant;
	}


	public ArrayList<Ville> getPorts() {
		return ports;
	}


	public void setPorts(ArrayList<Ville> ports) {
		this.ports = ports;
	}

	public void addPort(Ville v){
		this.ports.add(v);
		this.nbPort--;
	}

	public ArrayList<RouteTerrestre> getRouteTerreste() {
		return routeTerreste;
	}


	public void setRouteTerreste(ArrayList<RouteTerrestre> routeTerreste) {
		this.routeTerreste = routeTerreste;
	}

	public void addRouteTerrestre(RouteTerrestre r){
		this.routeTerreste.add(r);
	}

	public ArrayList<RouteMartime> getRouteMartime() {
		return routeMartime;
	}

	public void addRouteMaritime(RouteMartime r){
		this.routeMartime.add(r);
	}

	public void setRouteMartime(ArrayList<RouteMartime> routeMartime) {
		this.routeMartime = routeMartime;
	}
	
	/**
	 * Valide ou non le choix des pions
	 * @param wagon
	 * @param boat
	 * @return
	 */
	public String choicePion(int wagon,int boat){
		if(wagon>25){
			return "Le nombre de pion wagon est limité à 25";
		}else{
			this.nbWagon=wagon;
			this.nbWagonRestant=Math.abs(wagon-25);
		}
		if(boat>50){
			return "Le nombre de pion bateau est limité à 50";
		}else{
			this.nbBoat=boat;
			this.nbBoatRestant=Math.abs(boat-50);
		}
		return "ok";
	}
	
	/**
	 * Enregistre l'échange de pion et calcule le score
	 * @param wagon
	 * @param boat
	 * @param wR
	 * @param bR
	 * @return
	 */
	public int exchangePion(int wagon, int boat, int wR, int bR){
		int pointPerdu = Math.abs(this.nbWagon-wagon);
		this.nbWagon=wagon;
		this.nbWagonRestant=wR;
		this.nbBoat=boat;
		this.nbBoatRestant=bR;
		return -pointPerdu;
	}
	
	/**
	 * On vérifie s'il n'a pas déjà une route
	 * @param r
	 * @return
	 */
	public boolean checkIfRoadWagonIsTake(RouteTerrestre r){
		int i;
		for(i=0;i<routeTerreste.size();i++){
			RouteTerrestre r2 = routeTerreste.get(i);
			if(r2.getCouleur().equals(r.getCouleur())&&r2.getNbPion()==r.getNbPion()&&r2.getV1().getName().equals(r.getV1().getName())&&r2.getV2().getName().equals(r.getV2().getName())){
				return true;
			}
		}
		if(this.routeTerreste.contains(r)){
			return true;
		}
		return false;
	}
	
	public boolean checkIfRoadWagonDouble(RouteTerrestre r){
		int i;
		for(i=0;i<routeTerreste.size();i++){
			RouteTerrestre r2 = routeTerreste.get(i);
			if(r2.getV1().getName().equals(r.getV1().getName())&&r2.getV2().getName().equals(r.getV2().getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfRoadBoatIsTake(RouteMartime r){
		int i;
		for(i=0;i<routeMartime.size();i++){
			RouteMartime r2 = routeMartime.get(i);
			if(r2.getCouleur().equals(r.getCouleur())&&r2.getNbPion()==r.getNbPion()&&r2.getV1().getName().equals(r.getV1().getName())&&r2.getV2().getName().equals(r.getV2().getName())){
				return true;
			}
		}
		if(this.routeMartime.contains(r)){
			return true;
		}
		return false;
	}
	
	public boolean checkIfRoadBoatDouble(RouteMartime r){
		int i;
		for(i=0;i<routeMartime.size();i++){
			RouteMartime r2 = routeMartime.get(i);
			if(r2.getV1().getName().equals(r.getV1().getName())&&r2.getV2().getName().equals(r.getV2().getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfPortnIsTake(Ville v){
		int i;
		for(i=0;i<ports.size();i++){
			Ville v2 = ports.get(i);
			if(v.getName().equals(v2.getName())){
				return true;
			}
		}
		if(this.ports.contains(v)){
			return true;
		}
		return false;
	}
	
	
	public boolean checkIfEnoughPion(int wagon, int boat){
		if((this.nbBoat-boat)>=0&&(this.nbWagon-wagon)>=0){
			return true;
		}
		return false;
	}
	

	public void lessWagonBoat(int boat, int wagon){
		this.nbBoat=this.nbBoat-boat;
		this.nbWagon=this.nbWagon-wagon;
	}
	
	public boolean checkCityIsConnectedToRoad(Ville v){
		int i;
		for(i=0;i<routeMartime.size();i++){
			if(routeMartime.get(i).containsVille(v)){
				return true;
			}
		}
		for(i=0;i<routeTerreste.size();i++){
			if(routeTerreste.get(i).containsVille(v)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfRoadContainsTwoCity(Ville v1, Ville v2){
		int i;
		for(i=0;i<routeMartime.size();i++){
			if(routeMartime.get(i).containsVille(v1)&&routeMartime.get(i).containsVille(v2)){
				return true;
			}
		}
		for(i=0;i<routeTerreste.size();i++){
			if(routeTerreste.get(i).containsVille(v1)&&routeTerreste.get(i).containsVille(v2)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfTwoCityAreConnected(Ville v1, Ville v2){
		ArrayList<Route> route = new ArrayList<Route>();
		ArrayList<Route> r2 = new ArrayList<Route>();
		route.addAll(routeMartime);
		route.addAll(routeTerreste);
		if(explore(route, v1, r2, v2)){
			return true;
		}else{
			return false;
		}
	} 
	
	/**
	 * Parcours en profondeur pour vérifier si les deux villes sont reliés sur plusieurs routes
	 * @param r
	 * @param v
	 * @param rMarque
	 * @param dest
	 * @return
	 */
	public boolean explore(ArrayList<Route> r,Ville v, ArrayList<Route> rMarque,Ville dest){
		
		ArrayList<Route> r2 = new ArrayList<Route>();
		r2=getAllRoadOfCity(v, r);
		int i;
		for(i=0;i<r2.size();i++){
			if(!rMarque.contains(r2.get(i))){
				if(r2.get(i).containsVille(dest)){
					return true;
				}else{
					rMarque.add(r2.get(i));
					if(explore(r, r2.get(i).getV1(), rMarque, dest)){
						return true;
					}else if(explore(r, r2.get(i).getV2(), rMarque, dest)){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public ArrayList<Route> getAllRoadOfCity(Ville v, ArrayList<Route> route){
		ArrayList<Route> r2 = new ArrayList<Route>();
		int i;
		for(i=0;i<route.size();i++){
			if(route.get(i).containsVille(v)){
				r2.add(route.get(i));
			}
		}
		return r2;
	}
	
	public int countPion(){
		return this.nbBoat+this.nbWagon;
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
