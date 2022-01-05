package logiciel;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.*;

public class AddScan {
	
	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	String login = "root";
	String password = "";
	Connection conn = null;
	String img = "";
	

	
	
	public AddScan(String fileContenu,int idPage) {
		
		//start the analyse of the image
		MeasuresList List_Insert = null;
		System.out.println(idPage);
		ImagesToProcessList ipl = new ImagesToProcessList();
		ipl.addImageName(fileContenu);
		CCLabeler counter = new CCLabeler();
		for (Object o : ipl) {
			// processes the image and counts the particles
			String imagename_to_process = (String) o;
			counter.process(imagename_to_process,idPage);
		}
		
		// get back the measure and convert it to a String without "[" and "]" char
		List_Insert = counter.getMeasures();
		String newList_Insert = (""+List_Insert);
		newList_Insert=newList_Insert.substring(1);
		newList_Insert = newList_Insert.substring(0, newList_Insert.length()-1);
		System.out.println(newList_Insert);
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, login, password);
			System.out.println("test connexion BD oui");

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
			stmt.addBatch("INSERT INTO `mesure_caractere` (`idMesure`, `aire`, `centreX`, `centreY`, `Xstart`, `Ystart`, `width`,`height`,`idPage`) VALUES "+newList_Insert);
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("Erreur de chargement");
			e.printStackTrace();
		}

	}
	
    
}
