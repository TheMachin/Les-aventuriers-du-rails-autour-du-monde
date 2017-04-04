package vue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.Main;

public class DeckController {

	
	@FXML 
	private Label lblDeckBoat;
	
	@FXML 
	private Label lblDeckWagon;
	
	@FXML 
	private Label lblDeckDestination;

	@FXML 
	private HBox HboxMain;

	
	private Main main;
	
	/**
	 * 
	 */
	public DeckController() {
		super();
	}
	
	 
	 @FXML
	 private void takeHideBoatCard(MouseEvent e) {
		System.out.println("clicked boat");
		/*Button btn2 = new Button();
		
		btn2.setText("BoatMouse");
	    this.HboxMain.getChildren().add(btn2);*/
		
		Label lbl = new Label();
		Image image = new Image(getClass().getResourceAsStream("/images/wagonviolet.PNG"));
	    lbl.setGraphic(new ImageView(image));
	    this.HboxMain.getChildren().add(lbl);
		
	 }
	 
	 @FXML
	 private void takeHideTrainCard(MouseEvent e) {
		 System.out.println("clicked train");
		/*Button btn3 = new Button();
			
		btn3.setText("Wagon");
	    this.HboxMain.getChildren().add(btn3);*/
	    
	    Label lbl = new Label();
		Image image = new Image(getClass().getResourceAsStream("/images/wagonviolet.PNG"));
	    lbl.setGraphic(new ImageView(image));
	    this.HboxMain.getChildren().add(lbl);
	 }
	 
	 @FXML
	 private void takeHideDestinationCard(MouseEvent e) {
		 System.out.println("clicked destination");
	 }
	 
	 public void setMain(Main main){
		 this.main=main;
	 }
}
