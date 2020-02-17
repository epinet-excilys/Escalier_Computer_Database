package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.CompanyDAO;
import model.Company;

public final class CompanyDAOImpl {
	
private static volatile CompanyDAOImpl instance = null;
	
	
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
		int a = -1;

		try {
			a = CompanyDAO.getInstance().getNbRow();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if ((i <= a) && (a != -1)) {
			try {
				company = CompanyDAO.getInstance().find(i).get();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return Optional.ofNullable(company);
	}

	public Optional<List<Company>> getAllComput() {
		List<Company> list = null;
		try {

			list = CompanyDAO.getInstance().findAll().get();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Optional.ofNullable(list);

	}
	
	public int getNbRows() {
		int nbRow = -1;
		try {
			nbRow = CompanyDAO.getInstance().getNbRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nbRow;
	}

	
}
