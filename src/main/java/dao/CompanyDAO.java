package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import exception.Logging;

import java.sql.PreparedStatement;

import mapper.CompanyMapper;
import model.*;

// A FAIRE
public final class CompanyDAO {

	private static volatile CompanyDAO instance = null;
	private final String getStatement = "SELECT company.id, company.name FROM company where id=?";
	private final String getAllStatement = "SELECT company.id, company.name FROM company";
	private final String getNbRowsStatement = "SELECT COUNT(*) as \"Rows\" FROM company;";

	private ResultSet result;
	
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

	public void create(Company obj) {
	}

	public void delete(Company obj) {
	}

	public void update(Company obj) {
	}

	public Optional<Company> find(int i) throws SQLException {

		Company company = new Company().builder.build();
		
		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getStatement);) {

			stmt.setInt(1, i);

			result = stmt.executeQuery();

			if (result.first()) {
				company = CompanyMapper.getInstance().getCompanyFromResultSet(result);
			}
		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		} finally {
			result.close();
		}

		return Optional.ofNullable(company);
	}

	public Optional<ArrayList<Company>> findAll() throws SQLException {

		ArrayList<Company> list = new ArrayList<Company>();
		Company company;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllStatement);) {

			result = stmt.executeQuery();

			while (result.next()) {
				company = CompanyMapper.getInstance().getCompanyFromResultSet(result);

				list.add(company);
			}

		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		} finally {
			result.close();
		}

		return Optional.ofNullable(list);
	}

	public int getNbRow() throws SQLException {
		int a = 0;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getNbRowsStatement);) {

			result = stmt.executeQuery();

			if (result.first()) {
				a = result.getInt("Rows");

			}

		} catch (SQLException e) {
			Logging.displayError(bddAccessLog);
		} finally {
			result.close();
		}

		return a;

	}

}
