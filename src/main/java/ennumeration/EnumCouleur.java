package ennumeration;

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

    public String toString() {
       return this.couleur;
    }
}
