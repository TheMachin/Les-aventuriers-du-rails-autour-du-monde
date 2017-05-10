package ennumeration;

import javafx.scene.paint.Color;

public enum EnumCouleur {
	JAUNE("jaune"),ROUGE("rouge"),VERT("vert"),NOIR("noir"),BLEU("bleu"), VIOLET("violet"),
	BLANC("blanc"),GRIS("gris"), JOKER("joker");
	
	
	private String couleur;
	
	private EnumCouleur(String couleur) {
		this.couleur=couleur;
	}
	
	public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return couleur.equals(otherName);
    }

	public Color getColor(EnumCouleur c){
		switch(c){
			case JAUNE: 
				return Color.YELLOW;
			case ROUGE:
				return Color.RED;
			case VERT:
				return Color.GREEN;
			case NOIR:
				return Color.BLACK;
			case BLEU:
				return Color.BLUE;
			default:
				return Color.GREY;
		}
		
	}
	
    public String toString() {
       return this.couleur;
    }
}
