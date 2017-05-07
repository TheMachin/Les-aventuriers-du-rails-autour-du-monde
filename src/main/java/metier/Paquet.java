package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;
import visitor.Visitable;
import visitor.Visitor;

public class Paquet implements Visitable{
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
		initializePaquet();
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
		if(this.pWagon.size()==0){
			this.setpWagon(this.defausseWagon);
			this.defausseWagon=null;
		}
		if(this.pWagon.size()==0){
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
		if(this.pBoat.size()==0){
			this.setpBoat(this.defausseBoat);
			this.defausseBoat=null;
		}
		if(this.pBoat.size()==0)
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
		if(this.pDestination.size()==0){
			this.pDestination=this.dDestination;
			this.dDestination=null;
		}
		if(this.pDestination.size()==0)
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
	
	private void initializePaquet(){
		int i=0;
		//cartes wagon et bateau siglé port
		for(i=0;i<4;i++){
			pWagon.add(new Wagon(EnumCouleur.BLANC, true, false,"/images/wagon/wagonsBlancPort.png"));
			pWagon.add(new Wagon(EnumCouleur.JAUNE, true, false,"/images/wagon/wagonsJaunePort.png"));
			pWagon.add(new Wagon(EnumCouleur.VIOLET, true, false,"/images/wagon/wagonsVioletPort.png"));
			pWagon.add(new Wagon(EnumCouleur.VERT, true, false,"/images/wagon/wagonsVertPort.png"));
			pWagon.add(new Wagon(EnumCouleur.ROUGE, true, false,"/images/wagon/wagonsRougePort.png"));
			pWagon.add(new Wagon(EnumCouleur.NOIR, true, false,"/images/wagon/wagonsNoirPort.png"));
			
			pBoat.add(new Boat(EnumCouleur.BLANC, true, false, "/images/bateau/simple/bateauBlanc.png"));
			pBoat.add(new Boat(EnumCouleur.JAUNE, true, false, "/images/bateau/simple/bateauJaune.png"));
			pBoat.add(new Boat(EnumCouleur.VIOLET, true, false, "/images/bateau/simple/bateauViolet.png"));
			pBoat.add(new Boat(EnumCouleur.VERT, true, false, "/images/bateau/simple/bateauVert.png"));
			pBoat.add(new Boat(EnumCouleur.ROUGE, true, false, "/images/bateau/simple/bateauRouge.png"));
			pBoat.add(new Boat(EnumCouleur.NOIR, true, false, "/images/bateau/simple/bateauNoir.png"));
		}
		
		//carte wagon non siglé port
		for(i=0;i<7;i++){
			pWagon.add(new Wagon(EnumCouleur.BLANC, false, false,"/images/wagon/wagonsBlanc.png"));
			pWagon.add(new Wagon(EnumCouleur.JAUNE, false, false,"/images/wagon/wagonsJaune.png"));
			pWagon.add(new Wagon(EnumCouleur.VIOLET, false, false,"/images/wagon/wagonsViolet.png"));
			pWagon.add(new Wagon(EnumCouleur.VERT, false, false,"/images/wagon/wagonsVert.png"));
			pWagon.add(new Wagon(EnumCouleur.ROUGE, false, false,"/images/wagon/wagonsRouge.png"));
			pWagon.add(new Wagon(EnumCouleur.NOIR, false, false,"/images/wagon/wagonsNoire.png"));
		}
		
		//joker
		for(i=0;i<14;i++){
			pWagon.add(new Wagon(EnumCouleur.JOKER, true, true,"/images/wagon/joker.png"));
		}
		
		//double bateau
		for(i=0;i<6;i++){
			
			pBoat.add(new Boat(EnumCouleur.BLANC, false, true, "/images/bateau/doubleBoat/doubleBlanc.png"));
			pBoat.add(new Boat(EnumCouleur.JAUNE, false, true, "/images/bateau/doubleBoat/doubleJaune.png"));
			pBoat.add(new Boat(EnumCouleur.VIOLET, false, true, "/images/bateau/doubleBoat/doubleViolet.png"));
			pBoat.add(new Boat(EnumCouleur.VERT, false, true, "/images/bateau/doubleBoat/doubleVert.png"));
			pBoat.add(new Boat(EnumCouleur.ROUGE, false, true, "/images/bateau/doubleBoat/doubleRouge.png"));
			pBoat.add(new Boat(EnumCouleur.NOIR, false, true, "/images/bateau/doubleBoat/doubleNoir.png"));
		}
		
		//destination iténéraire
		
		Ville alZahira = new Ville("Al Zahira", true);
		Ville sydney = new Ville("Sydney", true);
		Ville athina = new Ville("Athina", true);
		Ville manila = new Ville("Manila", true); 
		Ville bangkok = new Ville("Bangkok", true);
		Ville tokyo = new Ville("Tokyo", true);
		Ville buenos = new Ville("Buenos Aires", true);
		Ville marseille = new Ville("Marseille", true);
		Ville capTown = new Ville("Cape Town", true);
		Ville caracas = new Ville("Caracas", true);
		Ville casablanca = new Ville("Casablanca", true);
		Ville jakarta = new Ville("Jakarta", true);
		Ville honolulu = new Ville("Honolulu", true);
		Ville vakutsk = new Ville("Vakutsk", false);
		Ville darEsSalaam = new Ville("Dar Es Salaam", true);
		Ville djibouti = new Ville("Djibouti", false);
		Ville lahore = new Ville("Lahore", false);
		Ville edimburg = new Ville("Edimburg", true);
		Ville luanda = new Ville("Luanda", true);
		Ville hongkong = new Ville("Hong Kong", true);
		Ville hamburg = new Ville("Hamburg", true);
		Ville beijing = new Ville("Beijing", false);
		Ville lagos = new Ville("Lagos", true);
		Ville lima = new Ville("Lima", true);
		Ville tehran = new Ville("Tehran", false);
		Ville losAngelos = new Ville("Los Angeles", true);
		Ville rio = new Ville("Rio De Janeiro", true);
		Ville mexico = new Ville("Mexico", false);
		Ville christchurch = new Ville("Christchurch", true);
		Ville mumbai = new Ville("Mumbai", true);
		Ville ny = new Ville("New York", true);
		Ville miami = new Ville("Miami", true);
		Ville moskva = new Ville("Moskva", false);
		Ville petropavlovsk = new Ville("Petropavlovsk", true);
		Ville toamasina = new Ville("Toamasina", true);
		Ville novosibirsk = new Ville("Novosibirsk", true);
		Ville darwin = new Ville("Darwin", false);
		Ville reykjavik = new Ville("Reykjavik", true);
		Ville perth=  new Ville("Perth", true);
		Ville valparaiso = new Ville("Valparaiso", true);
		Ville vancouver = new Ville("Vancouver", true);
		Ville winnipeg = new Ville("Winnipeg", false);
		
		pDestination.add(new Destination(EnumCarte.DESTINATION, 19, alZahira, sydney, "/images/destination/AlZahiraSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, athina, manila, "/images/destination/AthinaManila.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 6, bangkok, tokyo, "/images/destination/BangkokTokyo.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 17, buenos, manila, "/images/destination/BuenosAiresManila.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 18, buenos, marseille, "/images/destination/BuenosAiresMarseille.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, buenos, sydney, "/images/destination/BuenosAiresSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, capTown, jakarta, "/images/destination/CapTownJakarta.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, caracas, alZahira, "/images/destination/CaracasAlZahira.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 12, caracas, athina, "/images/destination/CaracasAthina.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 16, casablanca, honolulu, "/images/destination/CasablancaHonolulu.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 16, casablanca, vakutsk, "/images/destination/CasanblancaVakutsk.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 15, darEsSalaam, tokyo, "/images/destination/DarEsSalaamTokyo.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 7, djibouti, lahore, "/images/destination/DjiboutiLahore.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 10, edimburg, luanda, "/images/destination/EdimburgLuanda.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 17, edimburg, hongkong, "/images/destination/EdinburgHongKong.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 25, edimburg, sydney, "/images/destination/EdinburgSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 22, edimburg, tokyo, "/images/destination/EdinburgTokyo.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, hamburg, beijing, "/images/destination/HamburgBeijing.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 8, hamburg, darEsSalaam, "/images/destination/HamburgDarEsSalaam.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 5, hongkong, jakarta, "/images/destination/HongKongJakarta.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 7, sydney, jakarta, "/images/destination/JakartaSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, lagos, hongkong, "/images/destination/LagosHongKong.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 10, lagos, tehran, "/images/destination/LagosTeheran.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, lima, jakarta, "/images/destination/LimaJakarta.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 17, losAngelos, darEsSalaam, "/images/destination/LosAngelesDarEsSalaam.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, losAngelos, hamburg, "/images/destination/LosAngelesHamburg.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 11, losAngelos, jakarta, "/images/destination/LosAngelesJakarta.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 15, losAngelos, rio, "/images/destination/LosAngelesRioDeJaneiro.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 5, marseille, alZahira, "/images/destination/MarseilleAlZahira.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, marseille, beijing, "/images/destination/MarseilleBeijing.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 23, marseille, christchurch, "/images/destination/MarseilleChristchurch.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 18, marseille, jakarta, "/images/destination/MarseilleJakarta.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, mexico, beijing, "/images/destination/MexicoBeijing.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 15, mexico, mumbai, "/images/destination/MexicoMumbai.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 11, mexico, ny, "/images/destination/MexicoNewYork.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 9, miami, buenos, "/images/destination/MiamiBuenesosAires.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, miami, moskva, "/images/destination/MiamiMoskva.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, hongkong, moskva, "/images/destination/MoskvaHongKong.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 15, petropavlovsk, moskva, "/images/destination/MoskvaPetropavlovsk.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 11, toamasina, moskva, "/images/destination/MoskvaToamasina.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 6, mumbai, beijing, "/images/destination/MumbaiBeijing.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 19, ny, capTown, "/images/destination/NewYorkCapTown.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 10, ny, marseille, "/images/destination/NewYorkMarseille.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 19, ny, mumbai, "/images/destination/NewYorkMumbai.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 17, ny, sydney, "/images/destination/NewYorkSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 15, ny, tokyo, "/images/destination/NewYorkTokyo.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, novosibirsk, darwin, "/images/destination/NovosibirskDarwin.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, reykjavik, mumbai, "/images/destination/ReykjavikMumbai.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 11, rio, darEsSalaam, "/images/destination/RioDeJaneiroDarEsSalaam.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 18, rio, hamburg, "/images/destination/RioDeJaneiroHamburg.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 17, rio, perth, "/images/destination/RioDeJaneiroPerth.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 20, rio, tokyo, "/images/destination/RioDeJaneiroTokyo.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 11, sydney, tokyo, "/images/destination/TokyoSydney.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 6, rio, valparaiso, "/images/destination/ValparaisoRioDeJaneiro.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 13, vancouver, edimburg, "/images/destination/VancouverEdinburg.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 9, vancouver, miami, "/images/destination/VancouverMiami.png"));
		pDestination.add(new Destination(EnumCarte.DESTINATION, 14, perth, winnipeg, "/images/destination/WinnipegPerth.png"));
		
		Ville anchorage = new Ville("Anchorage", true);
		Ville cambridgeBay = new Ville("Cambridge Bay", true);
		Ville murmansk = new Ville("Murmansk", true);
		Ville tiksi = new Ville("Tiksi",true);
		Ville alqahira = new Ville("Al Qahira", true);
		Ville portMoresby = new Ville("Port Moresby",true);
		
		Map<Integer,Ville> map = new HashMap<Integer,Ville>();
		map.put(0, anchorage);
		map.put(1, cambridgeBay);
		map.put(2, reykjavik);
		map.put(3, murmansk);
		map.put(4, tiksi);
		
		Iteneraire ite = new Iteneraire(EnumCarte.ITENERAIRE, 34, 23, 40, map, "/images/destination/AnchorageCambridgeRaykjavikMurmanskTiksi.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, anchorage);
		map.put(1, vancouver);
		map.put(2, winnipeg);
		map.put(3, cambridgeBay);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 18, 12, 24, map, "/images/destination/AnchorageVancouverWinnipegCambridgeBay.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, casablanca);
		map.put(1, alqahira);
		map.put(2, tehran);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 9, 6, 15, map, "/images/destination/CasablancaAlQahiraTehran.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, lagos);
		map.put(1, luanda);
		map.put(2, darEsSalaam);
		map.put(3, djibouti);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 9, 6, 15, map, "/images/destination/LagosLuandaDarEsSalaamDjibouti.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, manila);
		map.put(1, honolulu);
		map.put(2, portMoresby);
		map.put(3, darwin);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 13, 9, 19, map, "/images/destination/ManilaHonoluluPortMeresby.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, mexico);
		map.put(1, caracas);
		map.put(2, lima);
		map.put(3, valparaiso);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 15, 10, 21, map, "/images/destination/MexicoCaracasLimaValparaiso.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, murmansk);
		map.put(1, tiksi);
		map.put(2, novosibirsk);
		map.put(3, vakutsk);
		map.put(4, petropavlovsk);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 30, 20, 36, map, "/images/destination/MurmanskTiksiNovosibirskYakutskPetropavlovsk.png");
		
		pDestination.add(ite);
		
		map.clear();
		map.put(0, tehran);
		map.put(1, lahore);
		map.put(2, mumbai);
		map.put(3, bangkok);
		
		ite = new Iteneraire(EnumCarte.ITENERAIRE, 13, 9, 19, map, "/images/destination/TeheranLahoreMumbaiBangkok.png");
		
		pDestination.add(ite);
		
	}
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
}
