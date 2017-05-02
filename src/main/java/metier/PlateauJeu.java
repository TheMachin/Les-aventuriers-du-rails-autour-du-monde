package metier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class PlateauJeu implements Visitable{
	private Paquet paquet= new Paquet();
	private int nbMaxJoueur=5;
	private int tour=0;
	
	private Map<Integer,Joueur> listJoueur=new HashMap<Integer,Joueur>();
	
	public PlateauJeu(int nbMaxJoueur) {
		super();
		this.nbMaxJoueur = nbMaxJoueur;
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

	public Map<Integer, Joueur> getListJoueur() {
		return listJoueur;
	}

	public boolean addJoueur(Joueur joueur, int id) {
		if(listJoueur.containsValue(joueur)||listJoueur.containsKey(id)){
			return false;
		}else{
			if(listJoueur.size()<nbMaxJoueur){
				listJoueur.put(id, joueur);
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Permet de vérifier si la partie peut être commencé
	 * Pour cela, on vérifie si il y a au moins 2 joueurs
	 * Si tous les joueurs sauf le joueur hébergeur a coché la case "Start" et a sélectionné une couleur
	 * On vérifie si le joueur qui héberge le jeu a bien sélectionné une couleur. 
	 * @return vrai si tous les conditions sont réunies, faux sinon.
	 */
	public boolean checkIfAllPlayerAreReady(){
		if(this.getListJoueur().size()<=1){
			return false;
		}
		Set cles = this.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next();
		   Joueur joueur = this.getListJoueur().get(cle);
		   if(cle!=0){
			   if(joueur.isStart()==false){
				   return false;
			   }
		   }
		   if(joueur.getCouleur()==null){
			   return false;
		   }
		}
		return true;
	}
	
	/**
	 * Permet de vérifier si le pseudo est unique
	 * @param pseudo : pseudo d'un joueur
	 * @return vrai s'il est unique et faux sinon.
	 */
	public boolean checkPseudoExists(String pseudo){
		Set cles = getListJoueur().keySet();
		Iterator it = cles.iterator();
		boolean exists=false ;
		while (it.hasNext()&&!exists){
		   int cle = (int) it.next();
		   Joueur joueur = getListJoueur().get(cle);
		   if(joueur.getName().equals(pseudo)){
			   return true;
		   }
		}
		return false;
	}	
	
	/**
	 * Vérifie la couleur n'a pas été déjà utilisé par un autre joueur
	 * @param color
	 * @return
	 */
	public boolean checkColorExists(EnumCouleur color){
		Set cles = getListJoueur().keySet();
		Iterator it = cles.iterator();
		boolean exists=false ;
		while (it.hasNext()&&!exists){
		   int cle = (int) it.next();
		   Joueur joueur = getListJoueur().get(cle);
		   if(joueur.getCouleur()!=null){
			   if(joueur.getCouleur().equals(color)){
				   return true;
			   }
		   }
		}
		return false;
	}
	
	public void setAllPlayerNotReady(){
		Set cles = this.getListJoueur().keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next();
		   this.getJoueur(cle).setStart(false);
		}
	}
	
	public void setListJoueur(Map<Integer, Joueur> listJoueur) {
		this.listJoueur = listJoueur;
	}

	public Joueur getJoueur(int id){
		if(listJoueur.containsKey(id)){
			return listJoueur.get(id);
		}else{
			return null;
		}
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
	public int whoIsNext(){
		return this.tour;
	}
	
	public void endOfPlayerTurn(){
		tour++;
		if(tour>=listJoueur.size()){
			tour=0;
		}
	}
	
}
