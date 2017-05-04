package ennumeration;

public enum EnumSave {
	JSON("json"),XML("xml");
	
	
	private String save;
	
	private EnumSave(String couleur) {
		this.save=couleur;
	}
	
	public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return save.equals(otherName);
    }

    public String toString() {
       return this.save;
    }
}
