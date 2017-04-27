package vue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

import controlor.MenuController;
import controlor.PlateauController;
import ennumeration.EnumCouleur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.MainMenu;
import metier.Joueur;
import server.Client;
import server.Server;
import server.Server.MyThreadHandler;

public class Menu{

	private MainMenu main;
	private MenuController menuController = new MenuController(this);
    
	@FXML
	private Label lblMsgError, lblMsgIp;
	
	@FXML
	private Pane menu;
	
	@FXML
	private Pane joinsGame;
	
	@FXML
	private Pane creatGame;
	
	@FXML
	private Pane panePseudo;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private TextField txtAdressIP;
	
	@FXML
	private TextField txtJ1, txtJ11;
	
	@FXML
	private TextField txtJ2, txtJ21;
	
	@FXML
	private TextField txtJ3, txtJ31;
	
	@FXML
	private TextField txtJ4, txtJ41;
	
	@FXML
	private TextField txtJ5, txtJ51;
	
	@FXML
	private TextField txtFieldPseudo;
	
	@FXML
	private Label lblMsgPseudo;
	
	@FXML
	private ComboBox<String> cbColor1;
	
	@FXML
	private ComboBox<String> cbColor2;
	
	@FXML
	private ComboBox<String> cbColor3;
	
	@FXML
	private ComboBox<String> cbColor4;
	
	@FXML
	private ComboBox<String> cbColor5;
	
	@FXML
	private CheckBox checkReady;
	
	@FXML
	private Button btnStart;
	
	public Menu() {
		super();
	}
	
	public void setMsgError(String msg){
		Platform.runLater(() -> {
			lblMsgError.setText(msg);
		});
	}
	
	public void setTxtInTextField(int id, String value){
		if(id==0){
			txtJ1.setText(value);
		}else if(id==1){
			txtJ2.setText(value);
		}else if(id==2){
			txtJ3.setText(value);
		}else if(id==3){
			txtJ4.setText(value);
		}else if(id==4){
			txtJ5.setText(value);
		}
	}
	
	
	
	public void setCombobox(int id){
		
		cbColor1.getItems().addAll(
				EnumCouleur.NOIR.name(),
				EnumCouleur.BLEU.name(),
				EnumCouleur.JAUNE.name(),
				EnumCouleur.VERT.name(),
				EnumCouleur.ROUGE.name()
			);
		
		cbColor2.setItems(cbColor1.getItems());
		cbColor3.setItems(cbColor1.getItems());
		cbColor4.setItems(cbColor1.getItems());
		cbColor5.setItems(cbColor1.getItems());
		
		if(id==0){
			cbColor1.setDisable(false);
			txtJ21.setVisible(true);
			cbColor2.setVisible(false);
			txtJ31.setVisible(true);
			cbColor3.setVisible(false);
			txtJ41.setVisible(true);
			cbColor4.setVisible(false);
			txtJ51.setVisible(true);
			cbColor5.setVisible(false);
			checkReady.setDisable(true);
			checkReady.setVisible(false);
		}else if(id==1){
			cbColor2.setDisable(false);
			txtJ11.setVisible(true);
			cbColor1.setVisible(false);
			txtJ31.setVisible(true);
			cbColor3.setVisible(false);
			txtJ41.setVisible(true);
			cbColor4.setVisible(false);
			txtJ51.setVisible(true);
			cbColor5.setVisible(false);
			btnStart.setVisible(false);
		}else if(id==2){
			cbColor3.setDisable(false);
			txtJ11.setVisible(true);
			cbColor1.setVisible(false);
			txtJ21.setVisible(true);
			cbColor2.setVisible(false);
			txtJ41.setVisible(true);
			cbColor4.setVisible(false);
			txtJ51.setVisible(true);
			cbColor5.setVisible(false);
			btnStart.setVisible(false);
		}else if(id==3){
			cbColor4.setDisable(false);
			txtJ11.setVisible(true);
			cbColor1.setVisible(false);
			txtJ31.setVisible(true);
			cbColor3.setVisible(false);
			txtJ21.setVisible(true);
			cbColor2.setVisible(false);
			txtJ51.setVisible(true);
			cbColor5.setVisible(false);
			btnStart.setVisible(false);
		}else if(id==4){
			cbColor5.setDisable(false);
			txtJ11.setVisible(true);
			cbColor1.setVisible(false);
			txtJ31.setVisible(true);
			cbColor3.setVisible(false);
			txtJ41.setVisible(true);
			cbColor4.setVisible(false);
			txtJ21.setVisible(true);
			cbColor2.setVisible(false);
			btnStart.setVisible(false);
		}
	}
	
	public void setSelectedElementCombobox(int id, String value){
		System.out.println("valeur retourne "+value);
		if(id==0){
			txtJ11.setText(value);
		}else if(id==1){
			txtJ21.setText(value);
		}else if(id==2){
			txtJ31.setText(value);
		}else if(id==3){
			txtJ41.setText(value);
		}else if(id==4){
			txtJ51.setText(value);
		}
	}
	
	public void setPanePseudo(){
		panePseudo.toFront();
	}
	
	public void setPaneCreatGame(){
		creatGame.toFront();
	}
	
	@FXML
	private void handleButtonCreatGameFromMenu(ActionEvent e){
		this.menuController.createServer();
	}
	
	@FXML
	private void handleButtonJoinGameFromMenu(ActionEvent e){
		
		String ip = txtAdressIP.getText();
		lblMsgIp.setText("");
		if(ip!=""){
			this.menuController.clientJoinServer(ip);
		}else{
			lblMsgIp.setText("Veuillez saisir une adresse IP.");
		}
	}
	
	public void setMsgAdressIp(String msg){
		lblMsgIp.setText(msg);
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
	private void handleTxtFieldPseudoAction(ActionEvent event){
		TextField txt=(TextField) event.getSource();
		if(txt.getText()!=""){
			try {
				menuController.createPseudo(txt.getText());
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				lblMsgPseudo.setText("La connexion avec le serveur a été perdu. Veuillez relancer le jeu.");
			}
		}else{
			lblMsgPseudo.setText("Veuillez saisir un pseudo.");
		}
	}
	
	@FXML 
	private void handleButtonPseudoAction(ActionEvent event){
		if(txtFieldPseudo.getText()!=""){
			try {
				menuController.createPseudo(txtFieldPseudo.getText());
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				lblMsgPseudo.setText("La connexion avec le serveur a été perdu. Veuillez relancer le jeu.");
			}
		}else{
			lblMsgPseudo.setText("Veuillez saisir un pseudo.");
		}
	}
	
	public void setMsgLblPseudo(String msg){
		lblMsgPseudo.setText(msg);
	}
	
	public void setVisibleButtunStartGame(){
		Platform.runLater(() -> {
			btnStart.setVisible(true);
			btnStart.setDisable(false);
		});
	}
	
	@FXML
	private void handleButtonStartGame(ActionEvent e) throws IOException{
		
		menuController.commencerLaPartie();
		
		/*Button btn=(Button) e.getSource();
		Stage stage = (Stage) btn.getScene().getWindow();
		
		Parent root = FXMLLoader.load(getClass().getResource("plateau.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();*/
		
		//Plateau interfaceController = new Plateau();
		//interfaceController.setMain(this);
	}
	
	public Plateau changerPlateau(){//(Server server, Client client, Map<Integer, MyThreadHandler> listClientsServer, Map<Integer, Joueur> joueurs, int id){
		
		Stage stage = (Stage) lblMsgError.getScene().getWindow();
		
		Parent root;
		try {
			
			Plateau plateau = new Plateau();
			plateau.setMain(main);
			root = FXMLLoader.load(getClass().getResource("plateau.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			return plateau;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//couleur combobox
	@FXML
	private void handleComboBoxColor(ActionEvent e){
		lblMsgError.setText("");
		ComboBox<String> co=(ComboBox<String>) e.getSource();
		String color = co.getValue();
		try {
			menuController.selectColorPlayer(color);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void handleCheckBoxReady(ActionEvent e){
		boolean selected = checkReady.isSelected();
		System.out.println(selected);
		lblMsgError.setText("");
		menuController.setReadyGame(selected);
	}
	
	public void setSelectedCheckBox(boolean selected){
		Platform.runLater(() -> {
			checkReady.setSelected(selected);
		});
	}
	
	public void setButtonStart(boolean selected){
		Platform.runLater(() -> {
			btnStart.setDisable(selected);
		});
	}
	
	
	public void setMain(MainMenu main){
		 this.main=main;
	 }
	
	public void setMenuController(MenuController main){
		 this.menuController=main;
	 }

}
