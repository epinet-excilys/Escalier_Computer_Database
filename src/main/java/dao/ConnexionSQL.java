package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.Logging;

public class ConnexionSQL {

	private static Properties conProp;
	private static final String CONFIGURATION_LOCATION = "database.propreties";
	private static String toWhichDatabaseAreWeConnected;
	private static String url;
	private static String user;
	private static String password;
	private static String driver;

	private static final String IOE_LOG = "Le chargement des proprieté";
	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué";
	private static final String CLASS_NOT_FOUND_LOG = "La classe n'est pas trouver ";

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);
	
	public static Connection getConn() {

		conProp = new Properties();
		System.out.println("Mabite");

		try {
			conProp.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("database.properties"));
			
			driver = conProp.getProperty("driver");
			url = conProp.getProperty("url");
			user = conProp.getProperty("user");
			password = conProp.getProperty("password");
			toWhichDatabaseAreWeConnected = conProp.getProperty("stateOfBDD");
			
			
			System.out.println(driver);
			System.out.println(url);
			
			Logging.getLog().info((toWhichDatabaseAreWeConnected));
			
			
			Class.forName(driver);

			return DriverManager.getConnection(url, user, password);

		} catch (IOException e1) {
			LOGGER.error(IOE_LOG + e1.getMessage());
		} catch (SQLException e2) {
			LOGGER.error(CONNECTION_LOG + e2.getMessage());
		} catch (ClassNotFoundException e3) {
			LOGGER.error(CLASS_NOT_FOUND_LOG + e3.getMessage());
		}

		System.exit(0);
		return null;
	}

}
