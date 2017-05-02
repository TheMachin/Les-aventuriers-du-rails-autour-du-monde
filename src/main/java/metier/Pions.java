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
	
	public boolean checkIfRoadWagonIsTake(RouteTerrestre r){
		if(this.routeTerreste.contains(r)){
			return true;
		}
		return false;
	}
	
	public boolean checkIfRoadBoatIsTake(RouteMartime r){
		if(this.routeMartime.contains(r)){
			return true;
		}
		return false;
	}
	
	public boolean checkIfPortnIsTake(Ville v){
		if(this.ports.contains(v)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * On vérifie s'il y a assez de pion pour prendre une route ou un port
	 * Si c'est ok, on enleve les pions qu'il a utilisé
	 * @param boat
	 * @param wagon
	 * @param rt
	 * @param rm
	 * @param v
	 * @return
	 */
	public boolean takeRoadOrPort(int boat, int wagon, RouteTerrestre rt, RouteMartime rm, Ville v){
		if((this.nbBoat-boat)>=0&&(this.nbWagon-wagon)>=0){
			if(rt!=null){
				this.addRouteTerrestre(rt);
				this.nbWagon=this.nbWagon-wagon;
				return true;
			}else if(rm!=null){
				this.addRouteMaritime(rm);
				this.nbBoat=this.nbBoat-boat;
				return true;
			}else if(v!=null){
				if(v.isPort()&&nbPort>0){
					this.addPort(v);
					this.nbBoat=this.nbBoat-boat;
					this.nbWagon=this.nbWagon-wagon;
					this.nbPort--;
					return true;
				}
				return false;
			}else{
				return false;
			}
		}
		return false;
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
