package metier;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class JoueurTest {
	
	
	Joueur j = new Joueur("", null, 0, 0, 0);
	
	@Test
	public void testCalculScoreDestination(){
		
		Ville v1 = new Ville("a", true);
		Ville v2 = new Ville("b", true);
		Ville v3 = new Ville("c", true);
		Ville v4 = new Ville("d", true);
		Ville v5 = new Ville("e", true);
		Ville v6 = new Ville("f", true);
		Ville v7 = new Ville("g", true);
		Ville v8 = new Ville("h", true);
		RouteTerrestre r = new RouteTerrestre(7, null, v1, v2);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(2, null, v2, v3);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(5, null, v3, v4);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v5, v6);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v7, v8);
		j.getPions().addRouteTerrestre(r);
		
		Destination d = new Destination(null, 50, v1, v4, null);
		j.addDestination(d);
		d = new Destination(null, 50, v5, v1, null);
		j.addDestination(d);
		j.calculScore();
		System.out.println("le score est de : "+j.getBonus());
		assertEquals(j.getBonus(), 50);
		
	}
	
	@Test
	public void testCalculScoreDestinationWithPort(){
		
		Ville v1 = new Ville("a", true);
		Ville v2 = new Ville("b", true);
		Ville v3 = new Ville("c", true);
		Ville v4 = new Ville("d", true);
		Ville v5 = new Ville("e", true);
		Ville v6 = new Ville("f", true);
		Ville v7 = new Ville("g", true);
		Ville v8 = new Ville("h", true);
		RouteTerrestre r = new RouteTerrestre(7, null, v1, v2);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(2, null, v2, v3);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(5, null, v3, v4);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v5, v6);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v7, v8);
		j.getPions().addRouteTerrestre(r);
		
		Destination d = new Destination(null, 50, v1, v4, null);
		j.addDestination(d);
		j.getPions().addPort(v1);
		j.getPions().addPort(v2);
		d = new Destination(null, 50, v5, v1, null);
		j.addDestination(d);
		j.calculScore();
		System.out.println("le score est de : "+j.getBonus());
		assertEquals(j.getBonus(), 66);
		
	}
	
	@Test
	public void testCalculScoreDestinationPort2(){
		
		Ville v1 = new Ville("a", true);
		Ville v2 = new Ville("b", true);
		Ville v3 = new Ville("c", true);
		Ville v4 = new Ville("d", true);
		Ville v5 = new Ville("e", true);
		Ville v6 = new Ville("f", true);
		Ville v7 = new Ville("g", true);
		Ville v8 = new Ville("h", true);
		RouteTerrestre r = new RouteTerrestre(7, null, v1, v2);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(2, null, v2, v3);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(5, null, v3, v4);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v5, v6);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v7, v8);
		j.getPions().addRouteTerrestre(r);
		
		Destination d = new Destination(null, 50, v1, v4, null);
		j.addDestination(d);
		j.getPions().addPort(v1);
		j.getPions().addPort(v4);
		d = new Destination(null, 50, v3, v4, null);
		j.addDestination(d);
		j.calculScore();
		System.out.println("le score est de : "+j.getBonus());
		assertEquals(j.getBonus(), 100+20+30);
		
	}
	
	@Test
	public void testCalculScoreIteneraire(){
		
		Ville v1 = new Ville("a", true);
		Ville v2 = new Ville("b", true);
		Ville v3 = new Ville("c", true);
		Ville v4 = new Ville("d", true);
		Ville v5 = new Ville("e", true);
		Ville v6 = new Ville("f", true);
		Ville v7 = new Ville("g", true);
		Ville v8 = new Ville("h", true);
		RouteTerrestre r = new RouteTerrestre(7, null, v1, v2);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(2, null, v2, v3);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(5, null, v3, v4);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v5, v6);
		j.getPions().addRouteTerrestre(r);
		r = new RouteTerrestre(7, null, v7, v8);
		j.getPions().addRouteTerrestre(r);
		Map<Integer,Ville> iteneraire = new HashMap<Integer,Ville>();
		iteneraire.put(0, v1);
		iteneraire.put(1, v2);
		iteneraire.put(2, v3);
		Iteneraire i = new Iteneraire(null, 50, 25, 90, iteneraire, null);
		j.addIteneraire(i);
		iteneraire = new HashMap<Integer,Ville>();
		iteneraire.put(0, v1);
		iteneraire.put(1, v2);
		iteneraire.put(2, v8);
		i = new Iteneraire(null, 50, 25, 900, iteneraire, null);
		j.addIteneraire(i);
		iteneraire = new HashMap<Integer,Ville>();
		iteneraire.put(0, v1);
		iteneraire.put(1, v3);
		iteneraire.put(2, v2);
		i = new Iteneraire(null, 50, 25, 900, iteneraire, null);
		j.addIteneraire(i);
		j.calculScore();
		System.out.println("le score est de : "+j.getBonus());
		assertEquals(j.getBonus(), 75);
		assertEquals(j.getMalus(), 900);
		
	}
	
	
	
}
