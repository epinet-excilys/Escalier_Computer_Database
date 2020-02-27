package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.Logging;

import java.sql.PreparedStatement;

import mapper.CompanyMapper;
import model.Company;

public final class CompanyDAO {

	private static volatile CompanyDAO instance = null;
	private final String getStatement = "SELECT company.id, company.name FROM company where id=?";
	private final String getAllStatement = "SELECT company.id, company.name FROM company";
	private final String getNbRowsStatement = "SELECT COUNT(*) as \"Rows\" FROM company;";

	private static ConnexionSQL connection = ConnexionSQL.getInstance();

	private final static String bddAccessLog = "Impossible d'acceder à la BDD niveau DAO";

	private CompanyDAO() {
		super();
	}

	public final static CompanyDAO getInstance() {
		if (CompanyDAO.instance == null) {
			synchronized (CompanyDAO.class) {
				if (CompanyDAO.instance == null) {
					CompanyDAO.instance = new CompanyDAO();
				}
			}
		}
		return CompanyDAO.instance;
	}

	public Optional<Company> findByID(int idSearch) {
		Company company = new Company.Builder().build();
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getStatement);) {
			stmt.setInt(1, idSearch);
			try (ResultSet result = stmt.executeQuery()) {
				if (result.first()) {
					company = CompanyMapper.getInstance().getCompanyFromResultSet(result);
				}
			}
		} catch (SQLException e1) {
			Logging.getLog().error(bddAccessLog + e1.getMessage());
		}
		return Optional.ofNullable(company);
	}

	public List<Company> findAll() {
		List<Company> listCompany = new ArrayList<>();
		Company company = new Company.Builder().build();
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllStatement);) {
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					company = CompanyMapper.getInstance().getCompanyFromResultSet(result);

					listCompany.add(company);
				}
			}
		} catch (SQLException e1) {
			Logging.getLog().error(bddAccessLog + e1.getMessage());
		}
		return listCompany;
	}

	public int getNbRow() {
		int nbRow = 0;
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getNbRowsStatement);) {
			
			try (ResultSet result = stmt.executeQuery()) {

				if (result.first()) {
					nbRow = result.getInt("Rows");

				}
			}

		} catch (SQLException e1) {
			Logging.getLog().error(bddAccessLog + e1.getMessage());
		}

		return nbRow;

	}

}
