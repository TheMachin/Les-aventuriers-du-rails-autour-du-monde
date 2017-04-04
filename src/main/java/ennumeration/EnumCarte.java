package ennumeration;

public enum EnumCarte {
	
	WAGON("wagon"),BOAT("boat"),DESTINATION("destination"),ITENERAIRE("iténéraire");
	
	private String carte;
	
	private EnumCarte(String carte) {
		this.carte=carte;
	}
}
