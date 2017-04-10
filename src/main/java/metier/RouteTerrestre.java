package metier;

import ennumeration.EnumCouleur;

public class RouteTerrestre extends Route{

	private boolean pair;
	
	public RouteTerrestre(int nbPion, EnumCouleur couleur, Ville v1, Ville v2) {
		super(nbPion, couleur, v1, v1);
		// TODO Auto-generated constructor stub
	}

}
