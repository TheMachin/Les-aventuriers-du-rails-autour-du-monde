package metier;

import java.util.HashMap;
import java.util.Map;

import ennumeration.EnumCouleur;

public class Plateau {
	private Paquet paquet;
	private int nbMaxJoueur=5;
	private Map<String,Joueur> listJoueur=new HashMap<String,Joueur>();
	
	/**
	 * @param paquet
	 * @param nbMaxJoueur
	 * @param joueur
	 */
	public Plateau(Paquet paquet, int nbMaxJoueur, Map<String, Joueur> joueur) {
		super();
		this.paquet = paquet;
		this.nbMaxJoueur = nbMaxJoueur;
		this.listJoueur = joueur;
	}

	public Paquet getPaquet() {
		return paquet;
	}

	public void setPaquet(Paquet paquet) {
		this.paquet = paquet;
	}

	public int getNbMaxJoueur() {
		return nbMaxJoueur;
	}

	public Map<String, Joueur> getListJoueur() {
		return listJoueur;
	}

	public boolean addJoueur(Joueur joueur) {
		if(listJoueur.containsValue(joueur)||listJoueur.containsKey(joueur.getCouleur().toString())){
			return false;
		}else{
			if(listJoueur.size()<nbMaxJoueur){
				listJoueur.put(joueur.getCouleur().toString(), joueur);
				return true;
			}else{
				return false;
			}
		}
	}
	
	public Joueur getJoueur(EnumCouleur c){
		if(listJoueur.containsKey(c)){
			return listJoueur.get(c);
		}else{
			return null;
		}
	}
	
	
	
}
