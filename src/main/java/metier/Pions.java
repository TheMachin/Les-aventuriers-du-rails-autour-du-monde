package metier;

import java.util.ArrayList;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Pions implements Visitable{
	private EnumCouleur couleur;
	private int nbWagon;
	private int nbBoat;
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
			System.out.println(r2.getV1().getName()+" "+r.getV1().getName()+" "+r2.getV2().getName()+" "+r.getV2().getName());
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
		System.out.println("boat "+nbBoat+" wagon "+nbWagon);
	}
	
	public boolean checkPortIsConnectedToRoad(Ville v){
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
	
	public int countPion(){
		return this.nbBoat+this.nbWagon;
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
