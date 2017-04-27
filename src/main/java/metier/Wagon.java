package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;

public class Wagon extends Transport{

	private boolean joker;
	
	public Wagon(EnumCouleur couleur, boolean port, boolean joker, String lienImage) {
		super(EnumCarte.WAGON, couleur, port, lienImage);
		// TODO Auto-generated constructor stub

		this.joker = joker;
	}
	
	public boolean isJoker(){
		return this.joker;
	}

}
