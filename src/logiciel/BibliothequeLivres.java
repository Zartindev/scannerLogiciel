package logiciel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BibliothequeLivres {

	//Database connection
	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	String login = "root";
	String password = "";
	Connection conn = null;
	private Label label;
	int i = 0;
	//Map for sql query
	Map<Integer,String> titre = new HashMap<Integer,String>();
	Map<Integer,String> lieux = new HashMap<Integer,String>();
	Map<Integer,Integer> nbPage = new HashMap<Integer,Integer>();
	Map<Integer,Integer> anneeEd = new HashMap<Integer,Integer>();
	Map<Integer,String> commentaire = new HashMap<Integer,String>();
	String imgf ="";

	public GridPane pageBiblioLivres() {

		GridPane root = new GridPane();

		root.setGridLinesVisible(true);

//		AtomicInteger rowCount = new AtomicInteger();
//		Button addRow = new Button("Add Row");
//		addRow.setOnAction( e-> root.addRow(rowCount.getAndIncrement(),
//		        new Label("Row" + (rowCount.get()-1) + " Col1"),
//		        new Label("Row" + (rowCount.get()-1) + " Col2")));
//		
//		GridPane.setHalignment(addRow, HPos.CENTER);
//		root.add(addRow, 0, 0);

		// space between border and grid
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
		
		

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//try catch for database connection
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
			
			//count number of book query
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(idLivre) AS total FROM Livre";
			ResultSet rs = stmt.executeQuery(sql);
			//location for root img
			int x = -1;
			int y = 0;
			//query result put in variable
			int total = 0;
			
			while (rs.next()) {
				total = rs.getInt("total");
			}
			
			//Creation Column and Row Constraints
			root.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint);
			root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint);
			
			//"for" loop which create all image button needed
			for (i = 1; i < total+1; i++) {
				//SQL query which research book in terms of idLivre
				String sql2 = "SELECT titre, lieux, nbPage, anneeEd, commentaire, linkImg FROM livre WHERE idLivre = "+ i +"";
				//put result in a ResultSet
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next()) {
					titre.put(i, rs2.getString("titre"));
					lieux.put(i, rs2.getString("lieux"));
					nbPage.put(i, rs2.getInt("nbPage"));
					anneeEd.put(i, rs2.getInt("anneeEd"));
					commentaire.put(i, rs2.getString("commentaire"));
					imgf = rs2.getString("linkImg");
				}
				//properties for every image
				Image image2 = new Image(imgf);
				ImageView im2 = new ImageView();
				im2.setFitHeight(100);
				im2.setFitWidth(100);
				im2.setImage(image2);
				
				//an if which give x and y location for every image like this : 0 0 / 1 0 / 2 0 / 0 1 / 1 1 / 1 2 / etc...
				if (i%4 == 0) {
					y++;
					x = 0;
				}
				else {
				x++;
				}

				// Use a button to show the pic that the user took
				Button afficheImage = new Button("", im2);
				root.add(afficheImage, x, y);
				//dialog pop up creation
				Alert dialog = new Alert(AlertType.CONFIRMATION);
				dialog.setTitle(titre.get(i));
				dialog.setHeaderText("Information sur le livre: "+ titre.get(i) +".");
				dialog.setContentText("Titre de l'oeuvre: " + titre.get(i) + "\n Lieux de création: " + lieux.get(i) + "\n Nombre de pages: " + nbPage.get(i) + "\n Année d'édition: " + anneeEd.get(i) + "\n Commentaire: " + commentaire.get(i) + ".");
				//show pop up on click
				afficheImage.setOnMouseClicked((e) -> {
					ButtonType ok = new ButtonType("ok");
					ButtonType Supprimer = new ButtonType("Supprimer");
					ButtonType Annuler = new ButtonType("Annuler");
					
					// Remove default ButtonTypes
					dialog.getButtonTypes().clear();
					
					dialog.getButtonTypes().addAll(ok, Supprimer, Annuler);
					
					// option != null.
					Optional<ButtonType> option = dialog.showAndWait();
					
					if (option.get() == null) {
					} else if (option.get() == ok) {
					} else if (option.get() == Supprimer) {
						//String sql3 = "DELETE FROM `livre` WHERE `livre`.`idLivre` = 1";
						//ResultSet rs3 = stmt.executeQuery(sql3);
					} else if (option.get() == Annuler) {
					} else {
						this.label.setText("-");
					}
				});
			}
			

		} catch (SQLException e) {
			System.err.println("Erreur de chargement");
			e.printStackTrace();
		}

		return root;
	}

}
