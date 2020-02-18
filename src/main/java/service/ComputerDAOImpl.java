package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.ComputerDAO;
import exception.Logging;
import model.Computer;

public final class ComputerDAOImpl {

	private static final String bddAccessLog = "Impossible de recuperer le computer dans la BDD";
	private static final String bddAddLog = "Impossible d'ajouter le computer en BDD";
	private static final String bddModLog = "Impossible de modifier le computer en BDD";
	private static final String bddSupprLog = "Impossible de supprimer le computer en BDD";
	private static final String bddNotFoundLog = "Il n'existe pas de Computer en BDD correspondant";
	
	private static volatile ComputerDAOImpl instance = null;

	private ComputerDAOImpl() {
		super();
	}

	public final static ComputerDAOImpl getInstance() {

		if (ComputerDAOImpl.instance == null) {

			synchronized (ComputerDAOImpl.class) {
				if (ComputerDAOImpl.instance == null) {
					ComputerDAOImpl.instance = new ComputerDAOImpl();
				}
			}
		}
		return ComputerDAOImpl.instance;
	}

	// TODO A MODIFIER
	public void update(Computer obj) {
		Computer comp = null;
		try {
			comp = ComputerDAO.getInstance().find(obj.getId()).get();
		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}

		if (comp != null) {
			try {
				ComputerDAO.getInstance().update(obj);
			} catch (SQLException e) {
				Logging.displayError(bddModLog);
			}
		}
	}

	public void add(Computer obj) {
		Computer comp = null;
		try {
			comp = ComputerDAO.getInstance().find(obj.getId()).get();
		} catch (SQLException e) {
			Logging.displayInfo(bddNotFoundLog);
		}

		if (comp == null) {
			try {
				ComputerDAO.getInstance().create(obj);
			} catch (SQLException e) {
				Logging.displayError(bddAddLog);
			}
		}
	}

	public void delete(Computer obj) {
			try {
				ComputerDAO.getInstance().delete(obj.getId());
			} catch (SQLException e) {
				Logging.displayError(bddSupprLog);
			}

	}

	//
	public Optional<Computer> find(int i) {
		Computer comp = null;

		try {
			comp = ComputerDAO.getInstance().find(i).get();
		} catch (SQLException e) {
			Logging.displayError(bddNotFoundLog);
		}

		return Optional.ofNullable(comp);
	}

	public ArrayList<Computer> getAllComput() {
		ArrayList<Computer> arrayList = null;
		try {

			arrayList = ComputerDAO.getInstance().findAll();

		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}

		return arrayList;

	}

	public ArrayList<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) {
		ArrayList<Computer> arrayList = null;
		try {

			arrayList = ComputerDAO.getInstance().findAllPaginate(ligneDebutOffSet, taillePage);

		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}

		return arrayList;

	}

	public int getNbRows() {
		int nbRow = -1;
		try {
			nbRow = ComputerDAO.getInstance().getNbRow();
		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}

		return nbRow;
	}

}
