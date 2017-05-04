package metier;

import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class PionsTest {

	public void testTakeRoadOrPort(){
		Pions p = new Pions(null, 10, 40);
		
		RouteTerrestre rt = new RouteTerrestre(7, null, null, null);
		
		//on prend une route terrestre
		assertEquals(p.takeRoadOrPort(0, 7, rt, null, null), true);
		assertEquals(p.getNbWagon(),3);
		
		//on vérifie si la route ne peut pas etre prise en raison d'un manque de pion
		assertEquals(p.takeRoadOrPort(0, 7, rt, null, null), false);
		assertEquals(p.getNbWagon(),3);
		
		//on prend une route maritime
		RouteMartime rm = new RouteMartime(30, null, null, null);
		assertEquals(p.takeRoadOrPort(0, 30, null, rm, null), true);
		assertEquals(p.getNbBoat(),10);
		
		//on verifie quand il manque des pions
		assertEquals(p.takeRoadOrPort(0, 30, null, rm, null), false);
		assertEquals(p.getNbBoat(),10);
		
		//on prend un port
		Ville v = new Ville(null, true);
		assertEquals(p.takeRoadOrPort(2, 2, null, null, v), true);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),1);
		assertEquals(p.getNbPort(),3);
		
		assertEquals(p.takeRoadOrPort(2, 2, null, null, v), false);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),1);
		assertEquals(p.getNbPort(),3);
		
		p.setNbWagon(4);
		assertEquals(p.takeRoadOrPort(4, 0, null, null, v), true);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),0);
		assertEquals(p.getNbPort(),2);
		
		//on verifie s'il a plus de port
		p.setNbWagon(4);
		assertEquals(p.takeRoadOrPort(4, 0, null, null, v), true);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),0);
		assertEquals(p.getNbPort(),1);
		
		p.setNbWagon(4);
		assertEquals(p.takeRoadOrPort(4, 0, null, null, v), true);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),0);
		assertEquals(p.getNbPort(),0);
		
		p.setNbWagon(4);
		assertEquals(p.takeRoadOrPort(4, 0, null, null, v), false);
		assertEquals(p.getNbBoat(),8);
		assertEquals(p.getNbWagon(),0);
		assertEquals(p.getNbPort(),0);
	}
	
}
