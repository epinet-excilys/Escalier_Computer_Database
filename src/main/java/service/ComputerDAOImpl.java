package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.ComputerDAO;
import model.Computer;

public final class ComputerDAOImpl {

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
			// TODO log
		}

		if (comp != null) {
			try {
				ComputerDAO.getInstance().update(obj);
			} catch (SQLException e) {
				// TODO log
			}
		}
	}

	public void add(Computer obj) {
		Computer comp = null;
		try {
			comp = ComputerDAO.getInstance().find(obj.getId()).get();
		} catch (SQLException e) {
			//TODO log
		}

		if (comp == null) {
			try {
				ComputerDAO.getInstance().create(obj);
			} catch (SQLException e) {
				// TODO log
			}
		}
	}

	public void delete(Computer obj) {
			try {
				ComputerDAO.getInstance().delete(obj.getId());
			} catch (SQLException e) {
				// TODO log
			}

	}

	//
	public Optional<Computer> find(int i) {
		Computer comp = null;

		try {
			comp = ComputerDAO.getInstance().find(i).get();
		} catch (SQLException e) {
			// TODO Log
		}

		return Optional.ofNullable(comp);
	}

	public ArrayList<Computer> getAllComput() {
		ArrayList<Computer> arrayList = null;
		try {

			arrayList = ComputerDAO.getInstance().findAll();

		} catch (SQLException e) {
			// TODO Log
		}

		return arrayList;

	}

	public ArrayList<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) {
		ArrayList<Computer> arrayList = null;
		try {

			arrayList = ComputerDAO.getInstance().findAllPaginate(ligneDebutOffSet, taillePage);

		} catch (SQLException e) {
			// TODO log
		}

		return arrayList;

	}

	public int getNbRows() {
		int nbRow = -1;
		try {
			nbRow = ComputerDAO.getInstance().getNbRow();
		} catch (SQLException e) {
			// TODO Log
		}

		return nbRow;
	}

}
