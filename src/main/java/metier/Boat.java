package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;

public class Boat extends Transport{

	private boolean doubleBoat;
	
	public Boat(EnumCouleur couleur, boolean port, boolean doubleBoat,String lienImage) {
		super(EnumCarte.BOAT, couleur, port, lienImage);
		// TODO Auto-generated constructor stub
		this.doubleBoat=doubleBoat;
	}

	public boolean isDoubleBoat() {
		return doubleBoat;
	}

}
