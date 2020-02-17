package client;

import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;

import model.Computer;
import service.ComputerDAOImpl;
import service.CompanyDAOImpl;
import mapper.ComputerMapper;
import dao.ComputerDAO;;

public class CLI {

	private Scanner sc;
	private String[] tabRep = { "", "", "", "", "" };
	private boolean flagContinue;
	private final int TAILLE_PAGE = 20;

	public CLI() {
		sc = new Scanner(System.in);

	}

	public void demonstration() {
		flagContinue = true;
		int commande = 0;
		afficher("Version t+3 de la BDD - acces console");

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
				break;
			case DISPLAYCOMPANY:
				afficher("affich company");
				break;
			case EXIT:
				afficher("Quitter le prog");
				flagContinue = false;
				break;

			}

		}

	}

	// TODO A REFAIRE
	// Methode Comput
	public void addComput() {

		Computer comp = null;

		afficher("Vous allez saisir les valeurs champs par champs");

		int i = (ComputerDAOImpl.getInstance().getNbRows() + 1);
		String passage_1 = "" + i + "";
		
		tabRep[0] = (passage_1);

		afficher("Saisir le nom");
		tabRep[1] = "Nom";// (sc.nextLine());

		afficher("Saisir la Date d'introduction sur le marché (AAAA-MM-dd)");
		tabRep[2] = (sc.nextLine());

		afficher("Saisir la Date de retrait du le marché (AAAA-MM-dd)");
		tabRep[3] = (sc.nextLine());

		afficher("Saisir l'id de la companie ");
		tabRep[4] = String.valueOf(scannerIdCompan("ajoutez"));
		
		Computer computer = ComputerMapper.getInstance().fromStringToComput(tabRep);
		
		afficher(computer);
		
		
		try {
			ComputerDAO.getInstance().create(computer);
		} catch (SQLException e) {
			// TODO Log
		}
	}

	public void modifComput() {

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
		afficher(commandeId);
		if (commandeId != -1) {
			Computer comp = ComputerDAOImpl.getInstance().find(commandeId).get();
			afficher(comp);
		} else {
			afficher("Pas de Correspondance en Base");
		}

	}

	public void affiAllComput() {
		List <Computer> list = ComputerDAOImpl.getInstance().getAllComput().get();

		for (Computer c : list) {
			afficher(c);
		}

	}

	public void affiAllPaginateComput() {
		int nbTotalRows = ComputerDAOImpl.getInstance().getNbRows();
		int currentRow = 0;

		do {
			List<Computer> list = ComputerDAOImpl.getInstance().getAllPaginateComput(currentRow, TAILLE_PAGE).get();
			for (Computer c : list) {
				afficher(c);
			}
			afficher("----------------");
			currentRow += 20;

		} while (currentRow < nbTotalRows);

	}

	// Methode
	// Compan-----------------------------------------------------------------------------------

	public void affiCompan() {

	}

	public void affiAllCompan() {

	}

	//--------------- Methode Console-----------------------------------------------------------

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
				rep = sc.nextLine();

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
			afficher("Entrez l'ID de la machine que vous voulez " + personnalisation );
			do {

				try {
					rep = sc.nextLine();

					repEnInt = Integer.parseInt(rep);

				} catch (Exception e) {
					afficher("Veuillez entrer une valeurs compréhensive pour le programme");
					repEnInt = -1;
				}

			} while (repEnInt == -1);
		}

		return repEnInt;

	}

	public String scannerString(String personnalisation) {
		String rep = "non_valable";

		return rep;
	}
	
	
	// TODO REFAIRE CETTE FONCTION
	/*
	 * public boolean scannerSur() {
	 * 
	 * String oui = "OUI"; String non = "non"; String rep = "vide";
	 * 
	 * do { afficher("Etes vous sur ? [" + oui + ":" + non + "]");
	 * 
	 * try { rep = sc.nextLine();
	 * 
	 * } catch (Exception e) {
	 * afficher("Veuillez entrer une valeurs compréhensive pour le programme"); }
	 * 
	 * } while (!rep.contentEquals(oui) || !rep.contentEquals(non));
	 * 
	 * if (rep.contentEquals(oui)) { return true; } else if (rep.contentEquals(non))
	 * { return false; } else { afficher("Erreur de saisi"); return false; }
	 * 
	 * }
	 */


	//TODO MODFIER
	public int scannerIdCompan(String personnalisation) {
		int valMaxId = CompanyDAOImpl.getInstance().getNbRows();
		int repEnInt = -1;
		String rep = "";
		if (valMaxId != -1) {
			do {

				try {
					rep = sc.nextLine();

					repEnInt = Integer.parseInt(rep);

				} catch (Exception e) {
					afficher("Veuillez entrer une valeurs compréhensive pour le programme");
					repEnInt = -1;
				}

			} while (repEnInt == -1 );
		}

		return repEnInt;

	}

}
