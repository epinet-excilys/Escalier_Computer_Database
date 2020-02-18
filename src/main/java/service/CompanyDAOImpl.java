package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.CompanyDAO;
import exception.Logging;
import model.Company;

public final class CompanyDAOImpl {
	
private static volatile CompanyDAOImpl instance = null;
private static final String bddAccessLog = "Impossible de recuperer la Company";
	
	
	private CompanyDAOImpl() {
        super();
    }
	
	public final static CompanyDAOImpl getInstance() {
		
		if (CompanyDAOImpl.instance == null) {

	           synchronized(CompanyDAOImpl.class) {
	             if (CompanyDAOImpl.instance == null) {
	            	 CompanyDAOImpl.instance = new CompanyDAOImpl();
	             }
	           }
	        }
	        return CompanyDAOImpl.instance;
		
	}

	public Optional<Company> find(int i) {
		Company company = null;

			try {
				company = CompanyDAO.getInstance().find(i).get();
			} catch (SQLException e) {
				Logging.displayError(bddAccessLog);
			}

		return Optional.ofNullable(company);
	}

	public Optional<List<Company>> getAllComput() {
		List<Company> list = null;
		try {

			list = CompanyDAO.getInstance().findAll().get();

		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}

		return Optional.ofNullable(list);

	}
	
	public int getNbRows() {
		int nbRow = -1;
		try {
			nbRow = CompanyDAO.getInstance().getNbRow();
		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		}
		
		return nbRow;
	}

	
}
