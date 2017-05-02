package metier;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Wagon extends Transport implements Visitable{

	private boolean joker;
	
	public Wagon(EnumCouleur couleur, boolean port, boolean joker, String lienImage) {
		super(EnumCarte.WAGON, couleur, port, lienImage);
		// TODO Auto-generated constructor stub

		this.joker = joker;
	}
	
	public boolean isJoker(){
		return this.joker;
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
