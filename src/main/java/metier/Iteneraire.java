package metier;

import java.util.Map;

import ennumeration.EnumCarte;
import visitor.Visitable;
import visitor.Visitor;

public class Iteneraire extends Carte implements Visitable{

	private int pointMax;
	private int point;
	private int malus;
	private Map<Integer,Ville> iteneraire; 
	
	public Iteneraire(EnumCarte name, String lienImage) {
		super(name, lienImage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param pointMax
	 * @param point
	 * @param malus
	 * @param iteneraire
	 */
	public Iteneraire(EnumCarte name, int pointMax, int point, int malus, Map<Integer, Ville> iteneraire, String lienImage) {
		super(name, lienImage);
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

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
