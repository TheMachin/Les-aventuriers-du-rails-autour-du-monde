package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;

public abstract class Transport extends Carte{

	private EnumCouleur couleur;
	private boolean port;
	
	/**
	 * @param name
	 * @param couleur : couleur de la carte
	 * @param port : specifie si la carte contient le single port
	 */
	public Transport(EnumCarte name, EnumCouleur couleur, boolean port) {
		super(name);
		this.couleur = couleur;
		this.port = port;
	}

	public Transport(EnumCarte name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public EnumCouleur getCouleur() {
		return couleur;
	}

	public boolean isPort() {
		return port;
	}
	
	

}
