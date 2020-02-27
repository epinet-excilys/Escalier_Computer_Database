package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.CompanyDAO;
import model.Company;

public final class CompanyDAOService {

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
		company = CompanyDAO.getInstance().findByID(ID).get();
		return Optional.ofNullable(company);
	}

	public List<Company> getAllComput() {
		List<Company> listCompany = new ArrayList<>();
		listCompany = CompanyDAO.getInstance().findAll();
		return listCompany;

	}

	public int getNbRows() {
		int nbRow = -1;
		nbRow = CompanyDAO.getInstance().getNbRow();
		return nbRow;
	}

}
