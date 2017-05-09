package metier;

import ennumeration.EnumCouleur;

public abstract class Route {
	private int nbPion;
	private EnumCouleur couleur;
	private  Ville v1;
	private Ville v2;
	/**
	 * @param nbPion
	 * @param couleur
	 */

	public Route(int nbPion, EnumCouleur couleur, Ville v1, Ville v2) {
		super();
		this.nbPion = nbPion;
		this.couleur = couleur;
		this.v1=v1;
		this.v2=v2;
	}
	public int getNbPion() {
		return nbPion;
	}
	public EnumCouleur getCouleur() {
		return couleur;
	}

	public Ville getV1(){
		return v1;
	}
	
	public Ville getV2(){
		return v2;
	}
	
	public boolean containsVille(Ville v){
		if(v.getName()==v1.getName()){
			return true;
		}else if(v.getName()==v2.getName()){
			return true;
		}else{
			return false;
		}
	}
}
