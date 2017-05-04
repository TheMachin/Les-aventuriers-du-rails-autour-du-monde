package visitor;

import metier.Boat;
import metier.Destination;
import metier.Iteneraire;
import metier.Joueur;
import metier.Paquet;
import metier.Pions;
import metier.PlateauJeu;
import metier.RouteMartime;
import metier.RouteTerrestre;
import metier.Ville;
import metier.Wagon;

public interface Visitor {
	public void visit(PlateauJeu plateauJeu);
	public void visit(Boat boat);
	public void visit(Destination destination);
	public void visit(Iteneraire iteneraire);
	public void visit(Joueur joueur);
	public void visit(Paquet paquet);
	public void visit(Pions pions);
	public void visit(RouteMartime route);
	public void visit(RouteTerrestre route);
	public void visit(Ville ville);
	public void visit(Wagon wagon);
	public String getOutput();
}
