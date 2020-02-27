package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.CompanyDAO;
import dao.ComputerDAO;
import model.Company;

public final class CompanyDAOService {

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	private static volatile CompanyDAOService instance = null;

	private CompanyDAOService() {
		super();
	}

	public final static CompanyDAOService getInstance() {
		if (CompanyDAOService.instance == null) {
			synchronized (CompanyDAOService.class) {
				if (CompanyDAOService.instance == null) {
					CompanyDAOService.instance = new CompanyDAOService();
				}
			}
		}
		return CompanyDAOService.instance;
	}

	public Optional<Company> findByID(int ID) {
		Company company = new Company.Builder().build();
		company = companyDAO.findByID(ID).get();
		return Optional.ofNullable(company);
	}

	public List<Company> getAllComput() {
		List<Company> listCompany = new ArrayList<>();
		listCompany = companyDAO.findAll();
		return listCompany;
	}

	public int getNbRows() {
		int nbRow = -1;
		nbRow = companyDAO.getNbRow();
		return nbRow;
	}

}
