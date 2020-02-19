package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.Logging;

public class ConnexionSQL {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	private static String user = "nom_utilisateur_choisi";
	private static String password = "mot_de_passe_solide";
	private static String driver = "com.mysql.jdbc.Driver";
	///////////////////////////////////////////////////////////////////////////////
	private static String urlTest = "jdbc:h2:mem:computer-database-db;INIT=RUNSCRIPT FROM 'src/test/resources/h2SQLgeneration.sql'";
	private static String userTest = "sa";
	private static String passwordTest = "";
	private static String driverh2 = "org.h2.Driver";
	///////////////////////////////////////////////////////////////////////////////
	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué";
	private static final String CLASS_NOT_FOUND_LOG = "La classe n'est pas trouver "; 
 
	public static Connection getConn() {
		
		
		if (isTestRunning(System.getProperty("testState"))) {
			Logging.displayInfo("Version test");
			try {
				Class.forName(driverh2);
				return DriverManager.getConnection(urlTest, userTest, passwordTest);
			} catch (SQLException e) {
				Logging.displayError(CONNECTION_LOG);
			} catch (ClassNotFoundException e) {
				Logging.displayError(CLASS_NOT_FOUND_LOG);
			}
			return null;
			
		} else {
			Logging.displayInfo("Version prod");
			try {
				Class.forName(driver);
				return DriverManager.getConnection(url, user, password);
			} catch (SQLException | ClassNotFoundException e) {
				Logging.displayError(CONNECTION_LOG);
			}
			return null;
			
		}
		
		
	}
	
	private static boolean isTestRunning(String testState) {
		if(testState == null) {
			return false;
		}else if(testState.contentEquals("Running")){
			return true;
		} else {
			return false;
		}
	}

}
