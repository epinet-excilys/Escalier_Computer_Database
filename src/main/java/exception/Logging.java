package exception;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging 
{
	private static final Logger logCons =LoggerFactory.getLogger(Logging.class);
	private static final Logger logFile =LoggerFactory.getLogger(Logging.class);
	
	
	public static void displayError(String personnalisation)
	{
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jConsole.properties"));
		logCons.error(personnalisation);
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));
		logFile.error(personnalisation);
	}
	public static void displayInfo(String personnalisation)
	{
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jConsole.properties"));
		logCons.info(personnalisation);
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));
		logFile.info(personnalisation);
	}
	
}
