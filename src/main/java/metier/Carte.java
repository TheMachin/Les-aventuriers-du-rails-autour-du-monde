package metier;

import ennumeration.EnumCarte;

public abstract class Carte {
	private EnumCarte name;

	/**
	 * @param name
	 */
	public Carte(EnumCarte name) {
		super();
		this.name = name;
	}
	
}
