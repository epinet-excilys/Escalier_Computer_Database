package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.ComputerDAO;
import model.Computer;

public final class ComputerDAOService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();

	private static volatile ComputerDAOService instance = null;

	private ComputerDAOService() {
		super();
	}

	public final static ComputerDAOService getInstance() {
		if (ComputerDAOService.instance == null) {
			synchronized (ComputerDAOService.class) {
				if (ComputerDAOService.instance == null) {
					ComputerDAOService.instance = new ComputerDAOService();
				}
			}
		}
		return ComputerDAOService.instance;
	}

	public int update(Computer computer) {
		Computer computerToUpdate = new Computer.Builder().build();
		int nbOfRowUpdatedInDB = 0;
		computerToUpdate = computerDAO.findByID(computer.getId()).get();
		if (computerToUpdate != null) {
			ComputerDAO.getInstance().update(computer);
		}
		return nbOfRowUpdatedInDB;
	}

	public int add(Computer computer) {
		int nbOfRowAddedInDB = 0;
		nbOfRowAddedInDB = ComputerDAO.getInstance().create(computer);
		return nbOfRowAddedInDB;
	}

	public int delete(Computer computer) {
		int nbOfRowDeletedInDB = 0;
		nbOfRowDeletedInDB = ComputerDAO.getInstance().delete(computer.getId());
		return nbOfRowDeletedInDB;
	}

	//
	public Optional<Computer> findByID(int ID) {
		Optional<Computer> computer = null;
		computer = ComputerDAO.getInstance().findByID(ID);
		return computer;
	}

	public List<Computer> getAllComput() {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = ComputerDAO.getInstance().findAll();
		return listComputer;

	}

	public List<Computer> getAllPaginateComput(int ligneDebutOffSet, int taillePage) {
		List<Computer> listComputer = new ArrayList<>();
		listComputer = ComputerDAO.getInstance().findAllPaginate(ligneDebutOffSet, taillePage);
		return listComputer;

	}

	public int getNbRows() {
		int nbRow = -1;
		nbRow = ComputerDAO.getInstance().getNbRow();
		return nbRow;
	}

}
