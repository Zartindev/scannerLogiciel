package logiciel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
		String login = "root";
		String password = "";
		Connection conn = null;

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
			while (rs.next()) {
				int total = rs.getInt("total");
				System.out.println(total);
			}
		} catch (SQLException e) {
			System.err.println("Erreur de chargement");
			e.printStackTrace();
		}

	}

}
