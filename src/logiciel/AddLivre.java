package logiciel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddLivre {
	
	private Desktop desktop = Desktop.getDesktop();

	public GridPane pageAddLivre() {

		GridPane root = new GridPane();
		// Insets : up, right, down, left
		root.setPadding(new Insets(10,50,50,50));
		// space between each bloc of the grid, Horizontal and vertical
		root.setHgap(25);
		root.setVgap(15);
		
		root.setGridLinesVisible(false);
		
		ColumnConstraints colConstraint = new ColumnConstraints();
		colConstraint.setPercentWidth(50);
		root.setMaxWidth(Double.MAX_VALUE);
		colConstraint.setHalignment(HPos.CENTER);
		
		RowConstraints rowConstraint = new RowConstraints();
		rowConstraint.setPercentHeight(20);
		
		root.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint);
		root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint);
		
// COMPONENTS ----------------------------------------------------------------------------------->		
		
		Label nomLivreL = new Label("Nom de l'ouvrage : ");
		TextField nomLivreTF = new TextField();
		root.add(nomLivreL, 0, 0);
		root.add(nomLivreTF, 1, 0);
		Label nomAuteurL = new Label("Nom de l'auteur : ");
		TextField nomAuteurTF = new TextField();
		root.add(nomAuteurL, 0, 1);
		root.add(nomAuteurTF, 1, 1);
		Label prenomAuteurL = new Label("Prenom de l'auteur : ");
		TextField prenomAuteurTF = new TextField();
		root.add(prenomAuteurL, 0, 2);
		root.add(prenomAuteurTF, 1, 2);
		Label nombrePagesL = new Label("Nombre de pages : ");
		TextField nombrePagesTF = new TextField();
		root.add(nombrePagesL, 0, 3);
		root.add(nombrePagesTF, 1, 3);
		Label anneeL = new Label("Année d'édition : ");
		TextField anneeTF = new TextField();
		root.add(anneeL, 0, 4);
		root.add(anneeTF, 1, 4);
		Label lieuL = new Label("               Lieu \n(ou a été écrit l'oeuvre) : ");
		TextField lieuTF = new TextField();
		root.add(lieuL, 0, 5);
		root.add(lieuTF, 1, 5);
		Label commentaireL = new Label("Commentaire : ");
		
		TextArea commentaireTF = new TextArea();

		root.add(commentaireL, 0, 6);
		root.add(commentaireTF, 1, 6);
		
		
		
		Label imageL = new Label("Image : ");
		root.add(imageL, 2, 1);
		Button ajouterFinal = new Button("AJOUTER LE LIVRE");
		root.add(ajouterFinal, 2, 6);
		Visuals.visualAdminButtons(ajouterFinal);

	
		
		 
			final FileChooser fileChooser = new FileChooser();
	        final Button openButton = new Button("Ouvrir une image...");
			Visuals.visualAdminButtons(openButton);
 
// ACTIONS ----------------------------------------------------------------------------------->
	        
	        // Add a picture that the user wants for his book
	        // Possibility to change the pic again with the button (which the name change for "changer l'image" after add a first time)
	        openButton.setOnAction(
	            new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(final ActionEvent e) {
	                    File file = fileChooser.showOpenDialog(null);
	                    if (file != null) {
	                    	System.out.println("LIEN DE L'IMAGE : " + file);
	                    	
	                    	openButton.setText("Changer l'image"); ;
	                    	// get the link of the pic, and add it in a string fileContenu 
	                    	// for using it and dispay it under the button openButton
	                    	String fileContenu = "file:///" + file.getAbsolutePath();
	                    	Image image2 = new Image(fileContenu);
	                		ImageView im2 = new ImageView();
	                		im2.setFitHeight(200);
	                		im2.setFitWidth(150);
	                		im2.setImage(image2);
	                		
	                		// Use a button to show the pic that the user took
	                		Button afficheImage = new Button("", im2);
	                		root.add(afficheImage, 2, 4);
	                		
	                		// Show the pic who has been chosen bigger
	                		afficheImage.setOnMouseClicked((exception) -> {
	                			
	                			openFile(file);
	                		});
	                		
	                    }
	                }
	            });
		
	    root.add(openButton, 2, 2);
		root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint);
		
		

		return root;
	}
	
	 private void openFile(File file) {
	        try {
	            desktop.open(file);
	        } catch (IOException ex) {
	            Logger.getLogger(
	                FileChooserSample.class.getName()).log(
	                    Level.SEVERE, null, ex
	                );
	        }
	    }


}