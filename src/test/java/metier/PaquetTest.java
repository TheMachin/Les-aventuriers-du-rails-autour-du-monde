package metier;

import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ennumeration.EnumCarte;
import ennumeration.EnumCouleur;

@RunWith(JUnitPlatform.class)
public class PaquetTest {

	Paquet p = new Paquet();
	
	@Test
	public void testAddWagon(){
		System.out.println("Verification si contient train");
		p = new Paquet();
		Wagon wagon = new Wagon(EnumCouleur.BLEU, false, false, null);
		
		//p.addBoat(boat);
		p.addWagon(wagon);
		assertEquals(p.getpWagon().contains(wagon),true);
		//assertEquals(p.pWagon.get(1),wagon);
		
	}
	
	@Test
	public void testAddBoat(){
		System.out.println("Verification si contient bateau");
		p = new Paquet();
		Boat boat = new Boat(EnumCouleur.BLEU, false, false, null);
		
		p.addBoat(boat);
		
		assertEquals(p.getpBoat().contains(boat),true);
		
	}
	
	@Test
	public void testPiocheWagon(){
		System.out.println("Verification si on pioche wagon");
		p = new Paquet();
		int nbWagon=p.getpWagon().size();
		p.piocheWagon();
		assertEquals(nbWagon-1,p.getpWagon().size());
	}
	
	@Test
	public void testPiocheBoat(){
		System.out.println("Verification si on pioche bateau");
		p = new Paquet();
		int nbBoat = p.getpBoat().size();
		p.piocheBoat();
		
		assertEquals(p.getpBoat().size(),nbBoat-1);
		//assertEquals(p.pWagon.get(1),wagon);
	}
}
