package vue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import controlor.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Main;
import main.MainMenu;

public class Menu implements InterfaceMenu{

	private MainMenu main;
	private MenuController menuController = new MenuController();
    
	@FXML
	private Pane menu;
	
	@FXML
	private Pane joinsGame;
	
	@FXML
	private Pane creatGame;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private TextField txtAdressIP;
	
	
	public Menu() {
		super();
		//this.menuController = menuController;
	}

	@FXML
	private void handleButtonCreatGameFromMenu(ActionEvent e){
		this.menuController.createServer();
		creatGame.toFront();
	}
	
	@FXML
	private void handleButtonJoinGameFromMenu(ActionEvent e){
		
		String ip = txtAdressIP.getText();
		this.menuController.clientJoinServer(ip);
		creatGame.toFront();
		
	}
	
	@FXML
	private void handleButtonJoinsGameFromMenu(ActionEvent e){
		joinsGame.toFront();
	}
	
	@FXML
	private void handleButtonRules(ActionEvent e){
		//creatGame.toFront();
		File file = new File("src//main//java//pdf//rs_world_rules_2016_fr.pdf");
		if(file.exists()){
			if (Desktop.isDesktopSupported()) {
			    try {
			        Desktop.getDesktop().open(file.getAbsoluteFile());
			    } catch (IOException ex) {
			        // no application registered for PDFs
			    }
			}
		}
        
	}


	@FXML
	private void handleButtonMenu(ActionEvent e){
		menu.toFront();
	}
	
	@FXML
	private void handleButtonStartGame(ActionEvent e) throws IOException{
		
		Button btn=(Button) e.getSource();
		Stage stage = (Stage) btn.getScene().getWindow();
		
		Parent root = FXMLLoader.load(getClass().getResource("plateau.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Plateau interfaceController = new Plateau();
		//interfaceController.setMain(this);
	}
	
	public void setMain(MainMenu main){
		 this.main=main;
	 }
	
	public void setMenuController(MenuController main){
		 this.menuController=main;
	 }

	
}
