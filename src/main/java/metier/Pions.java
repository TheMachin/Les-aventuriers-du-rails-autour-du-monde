package metier;

import java.util.ArrayList;

import ennumeration.EnumCouleur;

public class Pions {
	private EnumCouleur couleur;
	private int nbWagon;
	private int nbBoat;
	private int nbPort=4;
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


	public ArrayList<RouteTerrestre> getRouteTerreste() {
		return routeTerreste;
	}


	public void setRouteTerreste(ArrayList<RouteTerrestre> routeTerreste) {
		this.routeTerreste = routeTerreste;
	}


	public ArrayList<RouteMartime> getRouteMartime() {
		return routeMartime;
	}


	public void setRouteMartime(ArrayList<RouteMartime> routeMartime) {
		this.routeMartime = routeMartime;
	}
	
	
}
