package visitor;

import com.google.gson.Gson;

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

public class SaveJsonVisitor implements Visitor{

	private String outputString;
	
	@Override
	public void visit(PlateauJeu plateauJeu) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(plateauJeu);
	}

	@Override
	public void visit(Boat boat) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(boat);
	}

	@Override
	public void visit(Destination destination) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(destination);
	}

	@Override
	public void visit(Iteneraire iteneraire) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(iteneraire);
	}

	@Override
	public void visit(Joueur joueur) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(joueur);
		
	}

	@Override
	public void visit(Paquet paquet) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(paquet);
	}

	@Override
	public void visit(Pions pions) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(pions);
	}

	@Override
	public void visit(RouteMartime route) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(route);
	}

	@Override
	public void visit(RouteTerrestre route) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(route);
	}

	@Override
	public void visit(Ville ville) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(ville);
	}

	@Override
	public void visit(Wagon wagon) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		outputString+=gson.toJson(wagon);
	}

	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return this.outputString;
	}

}
