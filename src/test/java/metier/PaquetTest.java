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
		
		Wagon wagon = new Wagon(EnumCouleur.BLEU, false, false);
		Boat boat = new Boat(EnumCouleur.BLEU, false, false, false);
		
		//p.addBoat(boat);
		p.addWagon(wagon);
		
		assertEquals(p.getpWagon().get(0),wagon);
		//assertEquals(p.pWagon.get(1),wagon);
		
	}
	
	@Test
	public void testAddBoat(){
		System.out.println("Verification si contient bateau");
		
		Boat boat = new Boat(EnumCouleur.BLEU, false, false, false);
		
		p.addBoat(boat);
		
		assertEquals(p.getpBoat().get(0),boat);
		
	}
	
	@Test
	public void testPiocheWagon(){
		System.out.println("Verification si on pioche wagon");
		
		Wagon wagon = new Wagon(EnumCouleur.BLEU, false, false);
		
		p.addWagon(wagon);
		
		assertEquals(p.piocheWagon(),wagon);
		assertEquals(p.piocheWagon(), null);
		assertEquals(p.getpWagon(),null);
	}
	
	@Test
	public void testPiocheBoat(){
		System.out.println("Verification si on pioche bateau");
		
		Boat boat = new Boat(EnumCouleur.BLEU, false, false, false);
		
		p.addBoat(boat);
		
		assertEquals(p.piocheBoat(),boat);
		//assertEquals(p.pWagon.get(1),wagon);
	}
	
	@Test
	public void testPiocheDestination(){
		System.out.println("Verification si on pioche destination");
		
		Destination d = new Destination(EnumCarte.DESTINATION, 4, new Ville("Marseille", true), new Ville("Miami", true));
		
		p.addDestination(d);
		
		assertEquals(p.piocheDesination(),d);
		//assertEquals(p.pWagon.get(1),wagon);
	}
}
