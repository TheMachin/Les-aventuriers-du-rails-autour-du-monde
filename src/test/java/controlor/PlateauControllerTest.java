package controlor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ennumeration.EnumCouleur;
import metier.Joueur;
import metier.Paquet;
import metier.Pions;
import metier.PlateauJeu;
import metier.RouteTerrestre;
import metier.Wagon;
import vue.Plateau;

@RunWith(JUnitPlatform.class)
public class PlateauControllerTest {
	
	PlateauJeu plateau = new PlateauJeu(5);
	Plateau plateauView = new Plateau(null);
	
	@Test
	public void testTakeRoad(){
		
		Joueur j = new Joueur("", EnumCouleur.JAUNE, 0, 0, 0, true);
		
		Map<Integer,Joueur> joueurs = new HashMap<Integer, Joueur>();
		
		System.out.println("Verification si route train pris");
		RouteTerrestre r = new RouteTerrestre(1, EnumCouleur.JAUNE, null, null);
		Wagon w = new Wagon(EnumCouleur.JAUNE, true, false, null);
		ArrayList<Wagon> l = new ArrayList<Wagon>();
		l.add(w);
		PlateauController p = new PlateauController();
		plateauView.setPlateauController(p);
		p.setPlateauView(plateauView);
		Pions pion = new Pions(EnumCouleur.JAUNE, 10, 10);
		j.setPions(pion);
		joueurs.put(0, j);
		plateau.setListJoueur(joueurs);
		p.setPlateauJeu(plateau);
		assertEquals(false, p.checkTakeRoadWagon(r));
		assertEquals(true, p.takeRoadWagonOrBoatOrPort(r, null, null, null));
		assertEquals(true, p.checkTakeRoadWagon(r));
		
	}
	
	@Test
	public void testTakeRoadNotPion(){
		
		Joueur j = new Joueur("", EnumCouleur.JAUNE, 0, 0, 0, true);
		
		Map<Integer,Joueur> joueurs = new HashMap<Integer, Joueur>();
		
		System.out.println("Verification si route train pris");
		RouteTerrestre r = new RouteTerrestre(1, EnumCouleur.JAUNE, null, null);
		Wagon w = new Wagon(EnumCouleur.JAUNE, true, false, null);
		ArrayList<Wagon> l = new ArrayList<Wagon>();
		l.add(w);
		PlateauController p = new PlateauController();
		
		Pions pion = new Pions(EnumCouleur.JAUNE, 0, 0);
		j.setPions(pion);
		joueurs.put(0, j);
		plateau.setListJoueur(joueurs);
		p.setPlateauJeu(plateau);
		assertEquals(false, p.checkTakeRoadWagon(r));
		assertEquals(true, p.takeRoadWagonOrBoatOrPort(r, null, null, null));
		assertEquals(true, p.checkTakeRoadWagon(r));
		
	}
	
	
}
