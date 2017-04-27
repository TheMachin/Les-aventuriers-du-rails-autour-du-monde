package metier;

import java.io.Serializable;

import ennumeration.EnumCouleur;

public class Joueur implements Serializable{
	private String name;
	private EnumCouleur couleur;
	private int score;
	private int bonus;
	private int malus;
	private Pions pions;
	private boolean start;
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
	
	public Joueur(String name, EnumCouleur couleur, int score, int bonus, int malus,boolean start) {
		super();
		this.name = name;
		this.couleur = couleur;
		this.score = score;
		this.bonus = bonus;
		this.malus = malus;
		this.start=start;
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

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setCouleur(EnumCouleur couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return "Joueur [name=" + name + ", couleur=" + couleur + ", score=" + score + ", bonus=" + bonus + ", malus="
				+ malus + ", pions=" + pions + ", start=" + start + "]";
	}
	
	
}
