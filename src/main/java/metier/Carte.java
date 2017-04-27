package metier;

import ennumeration.EnumCarte;

public abstract class Carte {
	private EnumCarte name;
	private String lienImage;

	/**
	 * @param name
	 */
	public Carte(EnumCarte name, String lienImage) {
		super();
		this.name = name;
		this.lienImage=lienImage;
	}

	public EnumCarte getName() {
		return name;
	}

	public String getLienImage() {
		return lienImage;
	}
	
	
	
}
