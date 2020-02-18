package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.Logging;

public class ConnexionSQL {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";

	private static String user = "nom_utilisateur_choisi";

	private static String password = "mot_de_passe_solide";

	private static String urlTest = "jdbc:h2:~/computer-database-db;";

	private static String userTest = "nom_utilisateur_choisi";

	private static String passwordTest = "mot_de_passe_solide";

	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué"; 

	public static Connection getConn() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			Logging.displayError(CONNECTION_LOG);
		}
		return null;
	}

	public static Connection getConnTest() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO log
		}
		return null;
	}

}
