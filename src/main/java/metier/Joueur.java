package metier;

import ennumeration.EnumCouleur;

public class Joueur {
	private String name;
	private EnumCouleur couleur;
	private int score;
	private int bonus;
	private int malus;
	private Pions pions;
	
	/**
	 * @param name
	 * @param couleur
	 * @param score
	 * @param bonus
	 * @param malus
	 * @param nbWagon
	 * @param nbBoat
	 */
	public Joueur(String name, EnumCouleur couleur, int score, int bonus, int malus) {
		super();
		this.name = name;
		this.couleur = couleur;
		this.score = score;
		this.bonus = bonus;
		this.malus = malus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getMalus() {
		return malus;
	}
	public void setMalus(int malus) {
		this.malus = malus;
	}
	public EnumCouleur getCouleur() {
		return couleur;
	}
	public Pions getPions() {
		return pions;
	}
	public void setPions(Pions pions) {
		this.pions = pions;
	}
	
	
}
