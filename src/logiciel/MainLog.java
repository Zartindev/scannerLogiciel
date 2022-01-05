package logiciel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainLog extends Application {

	private int presenceAdminButton = 0;
	long lastRefreshTime = 0;

	
	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	String login = "root";
	String password = "";
	Connection conn = null;
	
	String imgPage = "";
	private Desktop desktop = Desktop.getDesktop();
	// String nomLivreSql = "";
	
	
	Map<Integer,String> nomLivreSql = new HashMap<Integer,String>();
	
	int i =0 ;
	

	public static void main(String[] args) {
		Application.launch(args);

	}

	/**
	 * start method of the main application
	 * 
	 * @param primaryStage
	 * 
	 *  The stage of the application
	 */

	public void start(Stage primaryStage) throws Exception {

// GRID ----------------------------------------------------------------------------------->
		GridPane root = new GridPane();

		// space between border and grid
		// up, right, down, left
		root.setPadding(new Insets(50));
		// space between each bloc of the grid, Horizontal and vertical
		root.setHgap(25);
		root.setVgap(15);

		// set visible the lign of the grid (remove at the end)
		// root.setGridLinesVisible(true);

		// set colum's size of the grid
		ColumnConstraints colConstraint = new ColumnConstraints();

		// set size Width of the grid, which ajust with the size of the scene
		colConstraint.setPercentWidth(50);
		root.setMaxWidth(Double.MAX_VALUE);
		colConstraint.setHalignment(HPos.LEFT);
		// [useless?] colConstraint.setHgrow(Priority.ALWAYS);

		RowConstraints rowConstraint = new RowConstraints();
		// set size Height of the grid, which ajust with the size of the scene
		rowConstraint.setPercentHeight(50);
		// [useless?] rowConstraint.setValignment(VPos.CENTER);
		// [useless?] rowConstraint.setVgrow(Priority.ALWAYS);

		// add constraints for columns and rows
		root.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint);
		root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint);

// COMPONENTS ----------------------------------------------------------------------------------->

		// add a pic for the buttons of the libraries
		Image image1 = new Image("logiciel/book_icon_158035.png");
		ImageView im1 = new ImageView();
		im1.setFitHeight(100);
		im1.setFitWidth(100);
		im1.setImage(image1);
		Image image2 = new Image("logiciel/pngegg.png");
		ImageView im2 = new ImageView();
		im2.setFitHeight(100);
		im2.setFitWidth(100);
		im2.setImage(image2);

		Button libraryBooks = new Button("Bibliothèque \n Ouvrages", im1);
		/** 
		 * 
		 *  {@link logiciel.Visuals#visualMainButtons()}
		 */
		Visuals.visualMainButtons(libraryBooks);

		Button libraryScans = new Button("Bibliothèque \n Analyses", im2);
		Visuals.visualMainButtons(libraryScans);

		Button addBook = new Button("Ajouter un livre");
		Visuals.visualMainButtons(addBook);

		Button refresh = new Button("ACTUALISER");
		Button admin = new Button("ADMIN");
		Visuals.visualAdminButtons(refresh);
		Visuals.visualAdminButtons(admin);
		
		final FileChooser fileChooser = new FileChooser();
		final Button openApic = new Button("Ouvrir une page a scanner...");
		Visuals.visualAdminButtons(openApic);
		
		
		Label textChoseBook = new Label("      Choississez le livre \ncorrespondant à la page :");
		Visuals.visualLabelsRed(textChoseBook);

		Button scannerPage = new Button("Scanné votre page");
		

		// PLACEMENT horizontals/verticals of the elements (center in grid box)
		GridPane.setHalignment(libraryBooks, HPos.CENTER);
		root.add(libraryBooks, 0, 0);
		GridPane.setHalignment(libraryScans, HPos.CENTER);
		GridPane.setValignment(libraryScans, VPos.BOTTOM);
		root.add(libraryScans, 0, 2);
		GridPane.setHalignment(addBook, HPos.CENTER);
		root.add(addBook, 1, 0);
		GridPane.setHalignment(refresh, HPos.LEFT);
		root.add(refresh, 2, 0);
		GridPane.setHalignment(admin, HPos.RIGHT);
		root.add(admin, 2, 0);
		
		GridPane.setHalignment(openApic, HPos.CENTER);
		root.add(openApic, 1, 1);		
		
		GridPane.setHalignment(textChoseBook, HPos.CENTER);
		GridPane.setValignment(textChoseBook, VPos.BOTTOM);
		root.add(textChoseBook, 1, 1);
		
		root.add(scannerPage, 1, 2);
		GridPane.setHalignment(scannerPage, HPos.CENTER);
		Visuals.visualAdminButtons(scannerPage);
		
		
// CHOICEBOX ----------------------------------------------------------------------------------->	
		
		ChoiceBox <String> choiceBox = new ChoiceBox<>();
		GridPane.setHalignment(choiceBox, HPos.CENTER);
		GridPane.setValignment(choiceBox, VPos.TOP);
		root.add(choiceBox, 1, 2);
		
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

		try {
			

			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(idLivre) AS total FROM Livre";
			ResultSet rs = stmt.executeQuery(sql);
			int total = 0;	
			
			while (rs.next()) {
				total = rs.getInt("total");
			}
			
			
			for ( i = 1; i < total+1; i++) {

				String sql2 = "SELECT titre FROM livre WHERE idLivre = "+ i +"";
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next()) {
					nomLivreSql.put(i,rs2.getString("titre"));
					choiceBox.getItems().add(nomLivreSql.get(i));
					
				}
				
				
				scannerPage.setOnMouseClicked((e1) -> {
					
					
					// This variable take the index of where the user click on the book's name 
					int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex() + 1;

					System.out.println(selectedIndex);
					
					
					
					//---------------------------
									
									// test if the name of the book and the pic is not empty
									// if not then the page isnt add to the BDD
									if ( imgPage == "") {

										Alert dialog = new Alert(AlertType.INFORMATION);
										dialog.setTitle("Information d'ajout - ERREUR");
										dialog.setHeaderText("ERREUR\nAjout non effectué.");
										dialog.setContentText(">>>>>>>>>>En travaux<<<<<<<<<<<");
										dialog.showAndWait();

									}

									else {

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
											Statement stmt2 = conn.createStatement();
											conn.setAutoCommit(false);
											stmt2.addBatch(
													"INSERT INTO `page` (`idPage`,`img`,`idLivre`) VALUES (null,'" + imgPage + "'," + selectedIndex + ")");
											stmt2.executeBatch();
											conn.commit();
											conn.setAutoCommit(true);
											
											// get the idPage for connected it with the measures in the DB
											String sql3 = "SELECT MAX(idPage) AS max FROM page";
											ResultSet rs3 = stmt2.executeQuery(sql3);
											int idPage=0;
											while (rs3.next()) {
												idPage = rs3.getInt("max");
											}
											System.out.println(idPage);
											//Creer un scan d'une image demande avec son lien d'image et l'id Page
											AddScan addScan = new AddScan(imgPage,idPage);
											
											Alert dialog = new Alert(AlertType.INFORMATION);
											dialog.setTitle("Information d'ajout - REUSSI");
											dialog.setHeaderText("REUSSI\nAjout effectué.");
											// Add the name of the book in the message which had an UpperCase
											dialog.setContentText(
													"Votre page lien : " + imgPage + " \na bien été ajouté a \nvotre bibliothéque d'analyses.");
											dialog.showAndWait();
										} catch (SQLException e) {
											System.err.println("Erreur de chargement");
											e.printStackTrace();
										}
								

									}

								});
				
			}
			
			

		} catch (SQLException e) {
			System.err.println("Erreur de chargement");
			e.printStackTrace();
		}
		
		
// ACTIONS ----------------------------------------------------------------------------------->

		libraryBooks.setOnMouseClicked((e) -> {
			BibliothequeLivres biblio = new BibliothequeLivres();

			/**
			 * {@link logiciel.BibliothequeLivres#pageBiblioLivres()} Create a new scene
			 * when libraryBooks is clicked, and open the class BibliothequeLivres
			 */
			Scene sceneBiblio = new Scene(biblio.pageBiblioLivres(), 800, 500, Color.BEIGE);
			Stage stageBiblio = new Stage();
			stageBiblio.setTitle("BIBLIOTHEQUE DES LIVRES");
			stageBiblio.setScene(sceneBiblio);
			stageBiblio.show();

		});

		libraryScans.setOnMouseClicked((e) -> {
			BibliothequeScans biblioS = new BibliothequeScans();

			/**
			 * {@link logiciel.BibliothequeLivres#pageBiblioLivres()} Create a new scene
			 * when libraryBooks is clicked, and open the class BibliothequeLivres
			 */
			Scene sceneBiblioS = new Scene(biblioS.pageBiblioScans(), 800, 500, Color.BEIGE);
			Stage stageBiblioS = new Stage();
			stageBiblioS.setTitle("BIBLIOTHEQUE DES SCANS");
			stageBiblioS.setScene(sceneBiblioS);
			stageBiblioS.show();

		});

		addBook.setOnMouseClicked((e) -> {
			AddLivre addLivre = new AddLivre();

			/**
			 * {@link logiciel.BibliothequeLivres#pageBiblioLivres()} 
			 * Create a new scene
			 * when libraryBooks is clicked, and open the class BibliothequeLivres
			 */
			Scene sceneLivre = new Scene(addLivre.pageAddLivre(), 700, 500, Color.BEIGE);
			Stage stageLivre = new Stage();
			stageLivre.setTitle("AJOUTER UN NOUVEAU LIVRE");
			stageLivre.setScene(sceneLivre);
			stageLivre.setResizable(false);
			stageLivre.show();

		});

		Button adminbutton = new Button("adminbutton");
		Visuals.visualAdminButtons(adminbutton);

		admin.setOnMouseClicked((e) -> {
			// System.exit(0);

			if (presenceAdminButton == 0) {
				GridPane.setHalignment(adminbutton, HPos.CENTER);
				root.add(adminbutton, 2, 1);
				presenceAdminButton = 1;
			}
		

		});
			
				
		
		refresh.setOnMouseClicked((e) -> {
			// System.exit(0);

			root.getChildren().remove(adminbutton);
			
			 System.out.println( "Restarting app!" );
			  primaryStage.close();
			  Platform.runLater( () -> {
				try {
					new MainLog().start( new Stage() );
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} );

		
		});
		
		
		
		// Add a picture that the user wants to scan after
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
							String fileContenu = ""+ file.getAbsolutePath();
							String newFileContenu = fileContenu.replace('\\', '/');
							imgPage = newFileContenu;
							// changer ici les antislash en slash
							Image image2 = new Image(newFileContenu);
							ImageView im2 = new ImageView();
							im2.setFitHeight(200);
							im2.setFitWidth(150);
							im2.setImage(image2);

							// Use a button to show the pic that the user took
							Button afficheImage = new Button("", im2);
							root.add(afficheImage, 2, 1);
							GridPane.setHalignment(afficheImage, HPos.CENTER);
							GridPane.setValignment(afficheImage, VPos.TOP);

							// Show the pic who has been chosen bigger
							afficheImage.setOnMouseClicked((exception) -> {

								openFile(file);
						
							});

						}
					}
				});
				
				
				
				

// SCENE ----------------------------------------------------------------------------------->
		Scene scene = new Scene(root, 1000, 600, Color.BEIGE);
		primaryStage.setTitle("SCANNER LOGICIEL TEXTES ANCIENS");
		primaryStage.setScene(scene);
		

		// Set max resize possible for width
		primaryStage.widthProperty().addListener((o, oldValue, newValue) -> {
			if (newValue.intValue() < 600) {
				primaryStage.setResizable(false);
				primaryStage.setWidth(600);
				primaryStage.setResizable(true);
			}
		});
		// Set max resize possible for height
		primaryStage.heightProperty().addListener((o, oldValue, newValue) -> {
			if (newValue.intValue() < 480) {
				primaryStage.setResizable(false);
				primaryStage.setHeight(480);
				primaryStage.setResizable(true);
			}
		});

		primaryStage.show();

	}
	
// OTHER METHODS ----------------------------------------------------------------------------------->	
	
	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(FileChooserSample.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
