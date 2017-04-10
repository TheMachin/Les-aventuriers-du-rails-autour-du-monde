package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vue.InterfaceController;

public class Main extends Application{

	
	private Stage primaryStage;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Les aventuriers du rails autour du monde");
		
		
        Parent root = FXMLLoader.load(getClass().getResource("../vue/plateau.fxml"));
		Scene scene = new Scene(root);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		
		InterfaceController interfaceController = new InterfaceController();
		interfaceController.setMain(this);
		
	}
	
	public Stage getStage(){
		return this.primaryStage;
	}

}
