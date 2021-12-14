package logiciel;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class BibliothequeScans {

	public GridPane pageBiblioScans() {

		GridPane root = new GridPane();
		
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
		root.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint);
		root.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint);

		return root;
	}


}