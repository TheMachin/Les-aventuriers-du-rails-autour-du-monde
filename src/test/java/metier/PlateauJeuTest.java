package metier;

import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class PlateauJeuTest {

	PlateauJeu plateauJeu = new PlateauJeu(5);
	
	public void testIfAloneButNot(){
		Joueur j = new Joueur(null, null, 0, 0, 0, false);
		Joueur j2 = new Joueur(null, null, 0, 0, 0, true);
		
		plateauJeu.addJoueur(j, 0);
		plateauJeu.addJoueur(j2, 1);
		plateauJeu.addJoueur(j2, 2);
		assertEquals(plateauJeu.ifAlone(),false);
	}
	
	public void testIfAloneBecauseAlone(){
		Joueur j = new Joueur(null, null, 0, 0, 0, false);
		Joueur j2 = new Joueur(null, null, 0, 0, 0, true);
		
		plateauJeu.addJoueur(j2, 2);
		assertEquals(plateauJeu.ifAlone(),true);
	}
	
	public void testIfAloneBecauseOtherPlayerRageQuit(){
		Joueur j = new Joueur(null, null, 0, 0, 0, false);
		Joueur j2 = new Joueur(null, null, 0, 0, 0, true);
		
		plateauJeu.addJoueur(j, 0);
		plateauJeu.addJoueur(j, 1);
		plateauJeu.addJoueur(j2, 2);
		assertEquals(plateauJeu.ifAlone(),true);
	}
	
	public void testIfGameWillBeEnd(){
		Pions p = new Pions(null, 4, 1);
		Joueur j = new Joueur(null, null, 0, 0, 0, false);
		j.setPions(p);
		Joueur j2 = new Joueur(null, null, 0, 0, 0, true);
		j2.setPions(p);
		plateauJeu.addJoueur(j, 0);
		plateauJeu.addJoueur(j, 1);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),true);
		assertEquals(plateauJeu.endGame(),false);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),true);
		assertEquals(plateauJeu.endGame(),false);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),true);
		assertEquals(plateauJeu.endGame(),false);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),true);
		assertEquals(plateauJeu.endGame(),false);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),true);
		assertEquals(plateauJeu.endGame(),true);
	}
	
	public void testIfGameNotWillBeEnd(){
		Pions p = new Pions(null, 20, 10);
		Joueur j = new Joueur(null, null, 0, 0, 0, false);
		j.setPions(p);
		Joueur j2 = new Joueur(null, null, 0, 0, 0, true);
		j2.setPions(p);
		plateauJeu.addJoueur(j, 0);
		plateauJeu.addJoueur(j, 1);
		plateauJeu.addJoueur(j2, 2);
		assertEquals(plateauJeu.checkIfGameWillBeEnd(),false);
	}
	
}
