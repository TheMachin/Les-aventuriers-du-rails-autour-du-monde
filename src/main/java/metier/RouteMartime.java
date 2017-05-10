package metier;

import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class RouteMartime extends Route implements Visitable{

	public RouteMartime(int nbPion, EnumCouleur couleur, Ville v1, Ville v2) {
		super(nbPion, couleur, v1, v2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
