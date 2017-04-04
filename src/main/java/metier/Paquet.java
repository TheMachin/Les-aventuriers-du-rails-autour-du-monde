package metier;

import java.util.ArrayList;
import java.util.Random;

public class Paquet {
	private ArrayList<Wagon> pWagon = new ArrayList<Wagon>();
	private ArrayList<Boat> pBoat = new ArrayList<Boat>();
	private ArrayList<Wagon> defausseWagon = new ArrayList<Wagon>();
	private ArrayList<Boat> defausseBoat = new ArrayList<Boat>();
	
	private ArrayList<Carte> pDestination = new ArrayList<Carte>();
	private ArrayList<Carte> dDestination = new ArrayList<Carte>();

	/**
	 * @param pWagon
	 */
	public Paquet() {
		super();
	}
	
	public void addWagon(Wagon wagon){
		this.pWagon.add(wagon);
	}
	
	public void addBoat(Boat boat){
		this.pBoat.add(boat);
	}
	
	public void addWagonDefausse(Wagon wagon){
		this.defausseWagon.add(wagon);
	}
	
	public void addBoatDefausse(Boat boat){
		this.defausseBoat.add(boat);
	}
	
	public void addDestination(Carte t){
		this.pDestination.add(t);
	}
	
	public void addDestinationDefausse(Carte t){
		this.dDestination.add(t);
	}
	
	/**
	 * On pioche une carte wagon
	 * Si la pioche est vide, on récupère la défausse
	 * Si elle la pioche est toujours vide, on retourne un null
	 * @return Carte wagon
	 */
	public Wagon piocheWagon(){
		if(this.pWagon.isEmpty()){
			this.setpWagon(this.defausseWagon);
			this.defausseWagon=null;
		}
		if(this.pWagon.isEmpty()){
			return null;
		}
		int i= this.getRandomNumber(0, this.pWagon.size()-1);
		Wagon w = this.pWagon.get(i);
		this.pWagon.remove(i);
		return w;
	}
	
	/**
	 * On pioche une carte bateau s'il est vide on récupère sa pioche
	 * Si c'est encore vide, on retourne un null
	 * @return carte bateau
	 */
	public Boat piocheBoat(){
		if(this.pBoat.isEmpty()){
			this.setpBoat(this.defausseBoat);
			this.defausseBoat=null;
		}
		if(this.pBoat.isEmpty())
			return null;
		else{
			int i =this.getRandomNumber(0, this.pBoat.size()-1);
			Boat b = this.pBoat.get(i);
			this.pBoat.remove(i);
			return b;
		}
	}
	
	/**
	 * Piocher une carte transport
	 * @return une carte destination ou iténéraire
	 */
	public Carte piocheDesination(){
		if(this.pDestination.isEmpty()){
			this.pDestination=this.dDestination;
			this.dDestination=null;
		}
		if(this.pDestination.isEmpty())
			return null;
		else{
			int i = this.getRandomNumber(0, this.pDestination.size()-1);
			Carte c = this.pDestination.get(i);
			this.pDestination.remove(i);
			return c;
		}
	}
	
	/**
	 * Permet d'obtenir une valeur aléatoire entre le min et le max
	 * @param min
	 * @param max
	 * @return valeur aléatoire entre 0 et 1
	 */
	private int getRandomNumber(int min, int max){
		Random random = new Random();
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber;
	}

	public ArrayList<Wagon> getpWagon() {
		if(pWagon.isEmpty()){
			return null;
		}
		return pWagon;
	}

	public void setpWagon(ArrayList<Wagon> pWagon) {
		this.pWagon = pWagon;
	}

	public ArrayList<Boat> getpBoat() {
		if(pBoat.isEmpty()){
			return null;
		}
		return pBoat;
	}

	public void setpBoat(ArrayList<Boat> pBoat) {
		this.pBoat = pBoat;
	}
}
