package ennumeration;

public enum EnumCouleur {
	JAUNE("jaune"),ROUGE("rouge"),VERT("vert"),NOIR("noir"),BLEU("bleu"), VIOLET("violet"),
	BLANC("blanc"),GRIS("gris"), JOKER("joker");
	
	
	private String couleur;
	
	private EnumCouleur(String couleur) {
		this.couleur=couleur;
	}
}
