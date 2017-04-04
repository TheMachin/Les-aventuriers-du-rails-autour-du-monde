package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;

public abstract class Transport extends Carte{

	private EnumCouleur couleur;
	private boolean port;
	private boolean joker;
	
	/**
	 * @param name
	 * @param couleur : couleur de la carte
	 * @param port : specifie si la carte contient le single port
	 * @param joker : specifie si la cartr est un joker ou pas
	 */
	public Transport(EnumCarte name, EnumCouleur couleur, boolean port, boolean joker) {
		super(name);
		this.couleur = couleur;
		this.port = port;
		this.joker = joker;
		if(this.joker){
			this.port=true;
		}
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

	public boolean isJoker() {
		return joker;
	}
	
	

}
