package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vue.Menu;

public class MainMenu extends Application{
	
	
	private Stage primaryStage;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Les aventuriers du rails autour du monde");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.jpg")));
		Parent root = null;
		Menu menu = new Menu();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Menu.fxml"));
		loader.setController(menu);
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		menu.setMain(this);
        
		Scene scene = new Scene(root);
		
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		
	}
	
	public Stage getStage(){
		return this.primaryStage;
	}
	
	
}
