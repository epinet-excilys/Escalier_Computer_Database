package client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import model.Company;
import model.Computer;
import service.ComputerDAOImpl;
import service.CompanyDAOImpl;
import mapper.ComputerMapper;
import dao.ComputerDAO;
import exception.Logging;;

public class CLI {

	private Scanner scanner;
	private String[] tabRep = { "", "", "", "", "" };
	private boolean flagContinue;
	private final int TAILLE_PAGE = 20;
	private final String BDD_ADD_LOG = "Impossible d'ajouter le computer en BDD";
	private final String BDD_MOD_LOG = "Impossible de modifier le computer en BDD";
	private final String PARSE_LOG = "Impossible de Parser la date fournit";
	private final String SQL_LOG = "SQL erreur";

	public CLI() {
		scanner = new Scanner(System.in);

	}

	public void demonstration() {
		flagContinue = true;
		int commande = 0;
		afficher("Version t+10 de CDB - acces console");

		while (flagContinue) {
			afficher("Saisir : 0 pour afficher la liste Computer");
			afficher("         1 pour afficher un Computer");
			afficher("         2 pour ajouter un Computer");
			afficher("         3 pour supprimer un Computer");
			afficher("         4 pour modifier un Computer");
			afficher("         5 pour afficher la liste Company");
			afficher("         6 pour quitter");

			commande = scannerQuestion(0, 6);

			switch (EnumMenu.valueOf(commande)) {

			case DISPLAYALLCOMPUTER:
				afficher("liste comput");
				affiAllPaginateComput();
				break;
			case DISPLAYCOMPUTER:
				afficher("un comput");
				affiComput();
				break;
			case CREATE:
				afficher("ajout comput");
				addComput();
				break;
			case DELETE:
				afficher("suppr comput");
				deletComput();
				break;
			case UPDATE:
				afficher("modif comput");
				modifComput();
				break;
			case DISPLAYCOMPANY:
				afficher("affich company");
				affiAllCompan();
				break;
			case EXIT:
				afficher("Quitter le prog");
				flagContinue = false;
				break;

			}

		}

	}

	public void addComput() {

		Computer computer = ;

		afficher("Vous allez saisir les valeurs champs par champs");

		int i = (ComputerDAOImpl.getInstance().getNbRows() + 1);
		String passage_1 = "" + i + "";

		tabRep[0] = (passage_1);

		afficher("Saisir le nom");
		tabRep[1] = (scanner.nextLine());

		afficher("Saisir la Date d'introduction sur le marché (AAAA-MM-dd)");
		tabRep[2] = (scanner.nextLine());

		afficher("Saisir la Date de retrait du le marché (AAAA-MM-dd)");
		tabRep[3] = (scanner.nextLine());

		afficher("Saisir l'id de la companie ");
		tabRep[4] = String.valueOf(scannerIdCompan("ajoutez"));

		Computer computer = null;
		try {
			computer = ComputerMapper.getInstance().fromStringToComput(tabRep);
		} catch (ParseException e1) {
			Logging.getLog().error(PARSE_LOG + e1.getMessage());
		} catch (SQLException e2) {
			Logging.getLog().error(SQL_LOG + e2.getMessage());
		}
		tabRep = null;

		afficher(computer);

		try {
			ComputerDAO.getInstance().create(computer);
		} catch (SQLException e) {
			Logging.displayError(BDD_ADD_LOG);
		}
	}

	public void modifComput() {
		int commandeId = scannerIdComput("afficher");

		if (commandeId != -1) {
			Optional<Computer> comp = ComputerDAOImpl.getInstance().find(commandeId);
			if (comp.isPresent()) {

				tabRep[0] = String.valueOf(comp.get().getId());

				afficher("Nom Actuel : " + comp.get().getName());
				afficher("Saisir le nouveau nom");
				tabRep[1] = (scanner.nextLine());

				afficher("Intro Date Actuel : " + comp.get().getIntroDate());
				afficher("Saisir la Date d'introduction sur le marché (AAAA-MM-dd)");
				tabRep[2] = (scanner.nextLine());

				afficher("Disco Date Actuel : " + comp.get().getDiscoDate());
				afficher("Saisir la Date de retrait du le marché (AAAA-MM-dd)");
				tabRep[3] = (scanner.nextLine());

				afficher("Id companie Actuel : " + comp.get().getCompany().getId());
				afficher("Saisir l'id de la companie ");
				tabRep[4] = String.valueOf(scannerIdCompan("ajoutez"));

				Computer computer = null;
				try {
					computer = ComputerMapper.getInstance().fromStringToComput(tabRep);
				} catch (ParseException parseEx) {
					Logging.displayError(PARSE_LOG);
				} catch (SQLException sqlEx) {
					Logging.displayError(SQL_LOG);
				}
				tabRep = null;

				afficher(computer);

				try {
					ComputerDAO.getInstance().update(computer);
				} catch (SQLException e) {
					Logging.displayError(BDD_MOD_LOG);
				}

			} else {
				afficher("Pas de Correspondance en Base");
			}

		}

	}

	public void deletComput() {

		int commandeId = scannerIdComput("supprimer");

		if (commandeId != -1) {

			Computer comp = ComputerDAOImpl.getInstance().find(commandeId).get();
			afficher(comp);

			ComputerDAOImpl.getInstance().delete(comp);
		} else {
			afficher("Pas de Correspondance en Base");
		}

	}

	public void affiComput() {

		int commandeId = scannerIdComput("afficher");

		if (commandeId != -1) {
			Optional<Computer> comp = ComputerDAOImpl.getInstance().find(commandeId);
			if (comp.isPresent()) {
				afficher(comp);
			} else {
				afficher("Pas de Correspondance en Base");
			}

		}

	}

	public void affiAllComput() {
		List<Computer> list = ComputerDAOImpl.getInstance().getAllComput();

		for (Computer c : list) {
			afficher(c);
		}

	}

	public void affiAllPaginateComput() {
		int nbTotalRows = ComputerDAOImpl.getInstance().getNbRows();
		int currentRow = 0;

		do {
			List<Computer> list = ComputerDAOImpl.getInstance().getAllPaginateComput(currentRow, TAILLE_PAGE);
			for (Computer c : list) {
				afficher(c);
			}
			afficher("appuyer sur [Entrée]");
			scanner.nextLine();
			afficher("----------------------------------");
			currentRow += 20;

		} while (currentRow < nbTotalRows);

	}

	// Methode
	// Compan-----------------------------------------------------------------------------------

	public void affiAllCompan() {
		List<Company> list = CompanyDAOImpl.getInstance().getAllComput().get();

		for (Company c : list) {
			afficher(c);
		}
	}

	// Methode
	// Console-----------------------------------------------------------

	public void afficher(Object s) {
		System.out.println(s);
	}

	public int scannerQuestion(int premier_possib, int deuxiem_possib) {

		String rep;
		int repEnInt = -1;

		afficher("Entrer votre Choix : [" + premier_possib + ":" + deuxiem_possib + "]");

		do {

			if (repEnInt != -1 && (repEnInt < premier_possib || repEnInt > deuxiem_possib)) {
				repEnInt = -1;
				afficher("Entrer votre Choix : [" + premier_possib + ":" + deuxiem_possib + "]");
			}

			try {
				rep = scanner.nextLine();

				repEnInt = Integer.parseInt(rep);

			} catch (Exception e) {
				afficher("Veuillez entrer une valeur compréhensible pour le programme");
				afficher("Entrer votre Choix : [" + premier_possib + ":" + deuxiem_possib + "]");
				repEnInt = -1;
			}

		} while (repEnInt == -1 || (repEnInt < premier_possib || repEnInt > deuxiem_possib));

		return repEnInt;

	}

	// TODO Modifier
	public int scannerIdComput(String personnalisation) {
		int valMaxId = ComputerDAOImpl.getInstance().getNbRows();
		int repEnInt = -1;
		String rep = "";
		if (valMaxId != -1) {
			afficher("Entrez l'ID de la machine que vous voulez " + personnalisation);
			do {

				try {
					rep = scanner.nextLine();

					repEnInt = Integer.parseInt(rep);

				} catch (Exception e) {
					afficher("Veuillez entrer une valeurs compréhensive pour le programme");
					repEnInt = -1;
				}

			} while (repEnInt == -1);
		}

		return repEnInt;

	}

	// TODO MODFIER
	public int scannerIdCompan(String personnalisation) {
		int valMaxId = CompanyDAOImpl.getInstance().getNbRows();
		int repEnInt = -1;
		String rep = "";
		if (valMaxId != -1) {
			do {

				try {
					rep = scanner.nextLine();

					repEnInt = Integer.parseInt(rep);

				} catch (Exception e) {
					afficher("Veuillez entrer une valeurs compréhensive pour le programme");
					repEnInt = -1;
				}

			} while (repEnInt == -1);
		}

		return repEnInt;

	}

}
