package metier;

import ennumeration.EnumCarte;

public class Destination extends Carte{

	private int point;
	private Ville v1;
	private Ville v2;
	
	public Destination(EnumCarte name, String lienImage) {
		super(name, lienImage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param point
	 * @param v1
	 * @param v2
	 */
	public Destination(EnumCarte name, int point, Ville v1, Ville v2, String lienImage) {
		super(name, lienImage);
		this.point = point;
		this.v1 = v1;
		this.v2 = v2;
	}

	public int getPoint() {
		return point;
	}

	public Ville getV1() {
		return v1;
	}

	public Ville getV2() {
		return v2;
	}

	
	
}
