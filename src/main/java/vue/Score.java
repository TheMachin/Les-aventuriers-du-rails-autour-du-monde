package vue;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ennumeration.EnumCouleur;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import metier.Joueur;

public class Score {

	private Map<Integer,Joueur> listJoueur;
	private Scene scene;
	
	
	public Score() {
		
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setListJoueur(Map<Integer, Joueur> listJoueur) {
		this.listJoueur = listJoueur;
		putScore();
	}



	private void putScore(){
		Set cles = this.listJoueur.keySet();
		Iterator it = cles.iterator();
		int i=1;
		while (it.hasNext()){
		   int cle = (int) it.next();
		   Label lbl = (Label) scene.lookup("#j"+i);
		   lbl.setText(listJoueur.get(cle).getName());
		   lbl = (Label) scene.lookup("#c"+i);
		   EnumCouleur c = listJoueur.get(cle).getCouleur();
		   lbl.setText(c.name());
		   lbl = (Label) scene.lookup("#s"+i);
		   lbl.setText(String.valueOf(listJoueur.get(cle).getScore()));
		   i++;
		}
		
	}
	
}
