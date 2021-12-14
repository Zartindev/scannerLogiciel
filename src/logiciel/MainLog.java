package logiciel;

import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainLog extends Application {

	private int presenceAdminButton = 0;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		root.setGridLinesVisible(true);

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

		Button user = new Button("USER");
		Button admin = new Button("ADMIN");
		Visuals.visualAdminButtons(user);
		Visuals.visualAdminButtons(admin);

		Label text = new Label();

		// PLACEMENT horizontals/verticals of the elements (center in grid box)
		GridPane.setHalignment(libraryBooks, HPos.CENTER);
		root.add(libraryBooks, 0, 1);
		GridPane.setHalignment(libraryScans, HPos.CENTER);
		root.add(libraryScans, 0, 2);
		GridPane.setHalignment(addBook, HPos.CENTER);
		root.add(addBook, 1, 0);
		GridPane.setHalignment(user, HPos.LEFT);
		root.add(user, 2, 0);
		GridPane.setHalignment(admin, HPos.RIGHT);
		root.add(admin, 2, 0);
		GridPane.setHalignment(text, HPos.CENTER);
		root.add(text, 2, 1);

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

//			if(text.getText().isEmpty() || text.getText().contains("USER MOD")) {
//				text.setText("ADMIN MOD");
//			} else
//				text.setText("");
//			

		});
		user.setOnMouseClicked((e) -> {
			// System.exit(0);

			presenceAdminButton = 0;

			root.getChildren().remove(adminbutton);

//			if(text.getText().isEmpty() || text.getText().contains("ADMIN MOD")) {
//				text.setText("USER MOD");
//			} else
//				text.setText("");
//				
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

}
