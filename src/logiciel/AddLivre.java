package logiciel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AddLivre {

	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	String login = "root";
	String password = "";
	Connection conn = null;
	String img = "";

	private Desktop desktop = Desktop.getDesktop();

	public GridPane pageAddLivre() {

		GridPane root = new GridPane();
		// Insets : up, right, down, left
		root.setPadding(new Insets(10, 50, 50, 50));
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
		// For blocking numbers of character of the TextField nombrePagesTF to 3 max
		// length
		Pattern pattern = Pattern.compile(".{0,3}");
		TextFormatter<Object> formatter = new TextFormatter<Object>((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		TextField nombrePagesTF = new TextField();
		// Force the user to use only numbers in the textfield nombrePagesTF
		nombrePagesTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}?")) {
					nombrePagesTF.setText(oldValue);
				}
			}
		});
		nombrePagesTF.setTextFormatter(formatter);
		root.add(nombrePagesL, 0, 3);
		root.add(nombrePagesTF, 1, 3);

		Label anneeL = new Label("Année d'édition : ");
		// For blocking numbers of character of the TextField anneelL to 4 max length
		Pattern pattern2 = Pattern.compile(".{0,4}");
		TextFormatter<Object> formatter2 = new TextFormatter<Object>((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern2.matcher(change.getControlNewText()).matches() ? change : null;
		});

		TextField anneeTF = new TextField();
		// Force the user to use only numbers in the textfield anneeTF
		anneeTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}?")) {
					anneeTF.setText(oldValue);
				}
			}
		});
		anneeTF.setTextFormatter(formatter2);
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

		final FileChooser fileChooser = new FileChooser();
		final Button openApic = new Button("Ouvrir une image...");

// VISUELS ----------------------------------------------------------------------------------->
		Visuals.visualLabelsRed(nomLivreL);
		Visuals.visualLabelsBlack(nomAuteurL);
		Visuals.visualLabelsRed(prenomAuteurL);
		Visuals.visualLabelsBlack(nombrePagesL);
		Visuals.visualLabelsRed(anneeL);
		Visuals.visualLabelsBlack(lieuL);
		Visuals.visualLabelsRed(commentaireL);
		Visuals.visualLabelsBlack(imageL);

		Visuals.visualAdminButtons(ajouterFinal);
		Visuals.visualAdminButtons(openApic);

// ACTIONS ----------------------------------------------------------------------------------->
		
		// Add a picture that the user wants for his book
		// Possibility to change the pic again with the button (which the name change
		// for "changer l'image" after add a first time)
		openApic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(null);
				if (file != null) {
					System.out.println("LIEN DE L'IMAGE : " + file);

					openApic.setText("Changer l'image");
					;
					// get the link of the pic, and add it in a string fileContenu
					// for using it and dispay it under the button openButton
					String fileContenu = "file:///" + file.getAbsolutePath();
					String newFileContenu = fileContenu.replace('\\', '/');
					img = newFileContenu;
					// changer ici les antislash en slash
					Image image2 = new Image(newFileContenu);
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
		
		// Button Ajouter Un Livre
		ajouterFinal.setOnMouseClicked((e1) -> {
			
			

			String nomLivre = nomLivreTF.getText(); // variable de récupération du nom du livre
			String nomAuteur = nomAuteurTF.getText(); // variable de récupération du nom de l'auteur
			String annee = anneeTF.getText(); // variable de récupération de l'année
			String lieu = lieuTF.getText(); // variable de récupération du lieu d'écriture de l'oeuvre
			String commentaire = commentaireTF.getText(); // variable de récupération du commentaire de l'utilisateur
			String prenomAuteur = prenomAuteurTF.getText(); // variable de récupération du prenom de l'auteur
			String nbPages = nombrePagesTF.getText();

			String nomLivreMaj = nomLivre.toUpperCase();

			// test AT LEAST if the name of the book and the author, and the number of pages
			// are completed
			// if not then the book isnt add to the BDD
			if (nomLivre == "" || nomAuteur == "" || nbPages == "" || img == "") {

				Alert dialog = new Alert(AlertType.INFORMATION);
				dialog.setTitle("Information d'ajout - ERREUR");
				dialog.setHeaderText("ERREUR\nAjout non effectué.");
				dialog.setContentText("Veuillez insérer au moins : " + "\n - Nom du livre" + "\n - Nom de l'auteur"
						+ "\n - Nombre de pages" + "\n - Image (png) de la couverture");
				dialog.showAndWait();

			}

			else {

				if (nbPages == "") {
					nbPages = "0";
				}
				int nombrePage = Integer.parseInt(nbPages); // variable de récupération du nombre de page qui sera
															// ensuite transformé en Int pour la base

				try {

					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection(url, login, password);

				}

				catch (ClassNotFoundException e) {
					System.err.println("Erreur de chargement");
					e.printStackTrace();
				}

				catch (SQLException e) {
					System.err.println("Erreur de chargement");
					e.printStackTrace();
				}
				try { // ajout des entrées qui sont les txtbox, dans la BDD a l'aide des requetes
						// INSERT INTO
					Statement stmt = conn.createStatement();
					conn.setAutoCommit(false);
					stmt.addBatch(
							"INSERT INTO `livre` (`idLivre`, `titre`, `lieux`, `nbPage`, `anneeEd`, `commentaire`, `linkImg`) VALUES (null,'"
									+ nomLivre + "','" + lieu + "'," + nombrePage + ",'" + annee + "','" + commentaire
									+ "','" + img + "')");
					stmt.addBatch("INSERT INTO `auteur` (`idAuteur`, `nom`, `prenom`) VALUES (null,'" + nomAuteur
							+ "','" + prenomAuteur + "')");
					int[] updateCounts = stmt.executeBatch();
					conn.commit();
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					System.err.println("Erreur de chargement");
					e.printStackTrace();
				}

				Alert dialog = new Alert(AlertType.INFORMATION);
				dialog.setTitle("Information d'ajout - REUSSI");
				dialog.setHeaderText("REUSSI\nAjout effectué.");
				// Add the name of the book in the message which had an UpperCase
				dialog.setContentText(
						"Votre livre " + nomLivreMaj + " a bien été ajouté a \nvotre bibliothéque d'ouvrages.");
				dialog.showAndWait();
		

			}

		});

		System.out.println(img);

		root.add(openApic, 2, 2);
		root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint);

		return root;

	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(FileChooserSample.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	


}