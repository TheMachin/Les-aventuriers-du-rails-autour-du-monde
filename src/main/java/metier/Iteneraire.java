package metier;

import java.util.Map;

import ennumeration.EnumCarte;

public class Iteneraire extends Carte{

	private int pointMax;
	private int point;
	private int malus;
	private Map<Integer,Ville> iteneraire; 
	
	public Iteneraire(EnumCarte name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param pointMax
	 * @param point
	 * @param malus
	 * @param iteneraire
	 */
	public Iteneraire(EnumCarte name, int pointMax, int point, int malus, Map<Integer, Ville> iteneraire) {
		super(name);
		this.pointMax = pointMax;
		this.point = point;
		this.malus = malus;
		this.iteneraire = iteneraire;
	}

	
	
	
	public int getPointMax() {
		return pointMax;
	}

	public int getPoint() {
		return point;
	}

	public int getMalus() {
		return malus;
	}

	public Map<Integer, Ville> getIteneraire() {
		return iteneraire;
	}

	public boolean addCity(Ville v){
		if(this.iteneraire.containsValue(v)){
			return false;
		}else{
			this.iteneraire.put(this.iteneraire.size()+1, v);
			return true;
		}
	}

	
	
}
