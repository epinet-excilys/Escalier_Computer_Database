package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import exception.Logging;

import java.sql.Date;
import java.sql.PreparedStatement;

import mapper.ComputerMapper;
import model.*;

public final class ComputerDAO {

	private static volatile ComputerDAO instance = null;
	private final String createStatement = "INSERT INTO computer(name, introduced, discontinued, company_id) "
			+ "VALUES(?, ?, ?, ?);";
	private final String updateStatement = "UPDATE computer set name=?, introduced=? , discontinued=?, company_id=? where id=?;";
	private final String deleteStatement = "DELETE from computer where id=?;";
	//
	private final String getStatement = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer  LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;";
	private final String getAllStatement = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id ;";
	private final String getAllPaginateStatement = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id LIMIT ?, ?;";
	private final String getNbRowsStatement = "SELECT COUNT(*) as \"Rows\" FROM computer;";

	private ResultSet result;

	private static final String BDD_ACCESS_LOG = "Impossible de se connecter à la  BDD niveau DAO";
	private static final String BDD_NULL_OBJECT_LOG = "Tentative de manipulation d'un objet null";

	Connection conection;
	
	private ComputerDAO() {
		super();

	}

	public final static ComputerDAO getInstance() {

		if (ComputerDAO.instance == null) {

			synchronized (ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
				}
			}
		}

		return ComputerDAO.instance;
	}

	public int create(Computer computer) throws SQLException {
		int valueOfPreparedStatement = 0;

		if (computer != null) {
			try (Connection connect = ConnexionSQL.getConn();
					PreparedStatement stmt = connect.prepareStatement(createStatement);) {
				stmt.setString(1, computer.getName());
				stmt.setTimestamp(2,
						computer.getIntroDate() != null
								? Timestamp.valueOf(computer.getIntroDate().atTime(LocalTime.MIDNIGHT))
								: null);
				stmt.setTimestamp(3,
						computer.getDiscoDate() != null
								? Timestamp.valueOf(computer.getDiscoDate().atTime(LocalTime.MIDNIGHT))
								: null);
				stmt.setInt(4, computer.getCompany().getId());

				valueOfPreparedStatement = stmt.executeUpdate();

			} catch (SQLException e) {
				Logging.getLog().error(BDD_ACCESS_LOG);
			} catch (NullPointerException e) {
				Logging.getLog().error(BDD_NULL_OBJECT_LOG);
			}
		}
		return valueOfPreparedStatement;

	}

	public int delete(int idSuppression) throws SQLException {
		int valueOfPreparedStatement = 0;
		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(deleteStatement);) {
			stmt.setInt(1, idSuppression);
			valueOfPreparedStatement = stmt.executeUpdate();

		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		}
		return valueOfPreparedStatement;
	}

	public int update(Computer computer) throws SQLException {
		int valueOfPreparedStatement = 0;

		if (computer != null) {
			try (Connection connect = ConnexionSQL.getConn();
					PreparedStatement stmt = connect.prepareStatement(updateStatement);) {

				stmt.setInt(5, computer.getId());

				stmt.setString(1, computer.getName());
				stmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroDate().atTime(LocalTime.MIDNIGHT)));
				stmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscoDate().atTime(LocalTime.MIDNIGHT)));
				stmt.setInt(4, computer.getCompany().getId());

				valueOfPreparedStatement = stmt.executeUpdate();

			} catch (SQLException e) {
				Logging.getLog().error(BDD_ACCESS_LOG);
			} catch (NullPointerException e) {
				Logging.getLog().error(BDD_NULL_OBJECT_LOG);
			}
		}
		return valueOfPreparedStatement;

	}

	public Optional<Computer> find(int idSearch) throws SQLException, NoSuchElementException {

		Optional<Computer> computer = Optional.empty();

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getStatement);) {
			stmt.setInt(1, idSearch);
			result = stmt.executeQuery();

			if (result.first()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result);

			}

		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		} finally {
			result.close();
		}

		return computer;
	}

	public ArrayList<Computer> findAll() throws SQLException {

		ArrayList<Computer> arrayList = new ArrayList<Computer>();
		Computer computer;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllStatement);) {

			result = stmt.executeQuery();
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				arrayList.add(computer);
			}

		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		} finally {
			result.close();

		}

		return arrayList;
	}

	public ArrayList<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) throws SQLException {

		ArrayList<Computer> arrayList = new ArrayList<Computer>();
		Computer computer;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllPaginateStatement);) {
			stmt.setInt(1, ligneDebutOffSet);
			stmt.setInt(2, taillePage);
			
			result = stmt.executeQuery();
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				arrayList.add(computer);
			}

		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		} finally {
			result.close();

		}

		return arrayList;
	}

	public int getNbRow() throws SQLException {
		int nbRow = -1;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getNbRowsStatement);) {
			result = stmt.executeQuery();

			if (result.first()) {
				nbRow = result.getInt("Rows");

			}

		} catch (SQLException e) {

			Logging.getLog().error(BDD_ACCESS_LOG);
		} finally {
			result.close();
		}

		return nbRow;

	}

}
