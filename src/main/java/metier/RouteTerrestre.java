package metier;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class RouteTerrestre extends Route implements Visitable{

	private boolean pair=false;
	
	public RouteTerrestre(int nbPion, EnumCouleur couleur, Ville v1, Ville v2) {
		super(nbPion, couleur, v1, v2);
		// TODO Auto-generated constructor stub
	}
	
	public RouteTerrestre(int nbPion, EnumCouleur couleur, Ville v1, Ville v2,boolean pair) {
		super(nbPion, couleur, v1, v2);
		this.pair=pair;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isPair(){
		return this.pair;
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
