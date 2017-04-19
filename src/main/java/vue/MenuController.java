package vue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.MainMenu;

public class MenuController {

	private MainMenu main;

    
	@FXML
	private Pane menu;
	
	@FXML
	private Pane joinsGame;
	
	@FXML
	private Pane creatGame;
	
	@FXML
	private StackPane stackPane;
	
	/**
	 * @param mainMenu
	 */
	public MenuController() {
		super();
	}


	@FXML
	private void handleButtonCreatGameFromMenu(ActionEvent e){
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
	
	public void setMain(MainMenu main){
		 this.main=main;
	 }

	
}
