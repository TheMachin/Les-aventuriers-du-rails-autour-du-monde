package metier;

import ennumeration.EnumCouleur;

public abstract class Route {
	private int nbPion;
	private EnumCouleur couleur;
	/**
	 * @param nbPion
	 * @param couleur
	 */
	public Route(int nbPion, EnumCouleur couleur) {
		super();
		this.nbPion = nbPion;
		this.couleur = couleur;
	}
	public int getNbPion() {
		return nbPion;
	}
	public EnumCouleur getCouleur() {
		return couleur;
	}
	
	
}
