package logiciel;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

public class BibliothequeLivres {

	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	String login = "root";
	String password = "";
	Connection conn = null;
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
			int x = -1;
			int y = 0;
			
			root.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint);
			root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint);
			
			
			while (rs.next()) {
			total = rs.getInt("total");
			}
			
			
			for (int i = 1; i < total+1; i++) {

				String sql2 = "SELECT linkImg FROM livre WHERE idLivre = "+ i +"";
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next()) {
					imgf = rs2.getString("linkImg");
				}
				System.out.println(imgf);
				Image image2 = new Image(imgf);
				ImageView im2 = new ImageView();
				im2.setFitHeight(100);
				im2.setFitWidth(100);
				im2.setImage(image2);
				
				
				if (i%4 == 0) {
					y++;
					x = 0;
				}
				else {
				x++;
				}

				// Use a button to show the pic that the user took
				Button afficheImage = new Button("", im2);
				System.out.println(x);
				System.out.println(y);
				root.add(afficheImage, x, y);

			}
			

		} catch (SQLException e) {
			System.err.println("Erreur de chargement");
			e.printStackTrace();
		}

		return root;
	}

}
