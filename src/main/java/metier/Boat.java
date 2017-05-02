package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Boat extends Transport implements Visitable{

	private boolean doubleBoat;
	
	public Boat(EnumCouleur couleur, boolean port, boolean doubleBoat,String lienImage) {
		super(EnumCarte.BOAT, couleur, port, lienImage);
		// TODO Auto-generated constructor stub
		this.doubleBoat=doubleBoat;
	}

	public boolean isDoubleBoat() {
		return doubleBoat;
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
