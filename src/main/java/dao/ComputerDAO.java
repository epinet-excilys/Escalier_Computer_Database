package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.Logging;
import mapper.ComputerMapper;
import model.Computer;

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

	private static final String BDD_ACCESS_LOG = "Impossible de se connecter à la  BDD niveau DAO";
	private static final String BDD_NULL_OBJECT_LOG = "Tentative de manipulation d'un objet null";

	private static ConnexionSQL connection = ConnexionSQL.getInstance();

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

	public int create(Computer computer) {
		int nbOfRowInsertedInDB = 0;

		if (computer != null) {
			try (Connection connect = connection.getConn();
					PreparedStatement stmt = connect.prepareStatement(createStatement);) {
				stmt.setString(1, computer.getName());
				stmt.setTimestamp(2,
						computer.getIntroducedDate() != null
								? Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT))
								: null);
				stmt.setTimestamp(3,
						computer.getDiscontinuedDate() != null
								? Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT))
								: null);
				stmt.setInt(4, computer.getCompany().getId());

				nbOfRowInsertedInDB = stmt.executeUpdate();

			} catch (SQLException e1) {
				Logging.getLog().error(BDD_ACCESS_LOG + e1.getMessage());
			} catch (NullPointerException e2) {
				Logging.getLog().error(BDD_NULL_OBJECT_LOG + e2.getMessage());
			}
		}
		return nbOfRowInsertedInDB;

	}

	public int delete(int idSuppression) {
		int nbOfDeletedRowsinDB = 0;
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(deleteStatement);) {
			stmt.setInt(1, idSuppression);
			nbOfDeletedRowsinDB = stmt.executeUpdate();

		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		}
		return nbOfDeletedRowsinDB;
	}

	public int update(Computer computer){
		int nbOfUpdatedRowsinDB = 0;

		if (computer != null) {
			try (Connection connect = connection.getConn();
					PreparedStatement stmt = connect.prepareStatement(updateStatement);) {

				stmt.setInt(5, computer.getId());

				stmt.setString(1, computer.getName());
				stmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT)));
				stmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT)));
				stmt.setInt(4, computer.getCompany().getId());

				nbOfUpdatedRowsinDB = stmt.executeUpdate();

			} catch (SQLException e1) {
				Logging.getLog().error(BDD_ACCESS_LOG + e1.getMessage());
			} catch (NullPointerException e2) {
				Logging.getLog().error(BDD_NULL_OBJECT_LOG + e2.getMessage());
			}
		}
		return nbOfUpdatedRowsinDB;

	}

	public Optional<Computer> findByID(int idSearch) {
		Optional<Computer> computer = Optional.empty();
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getStatement);) {
			stmt.setInt(1, idSearch);

			try (ResultSet result = stmt.executeQuery()) {

				if (result.first()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result);

				}
			}

		} catch (SQLException e1) {
			Logging.getLog().error(BDD_ACCESS_LOG + e1.getMessage());
		}
		return computer;
	}

	public List<Computer> findAll() {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllStatement);) {
			try (ResultSet result = stmt.executeQuery();) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		}
		return computerList;
	}

	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllPaginateStatement);) {
			stmt.setInt(1, ligneDebutOffSet);
			stmt.setInt(2, taillePage);
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		}
		return computerList;
	}

	public int getNbRow() {
		int nbRow = -1;
		try (Connection connect = connection.getConn();
				PreparedStatement stmt = connect.prepareStatement(getNbRowsStatement);) {
			try (ResultSet result = stmt.executeQuery()) {
				if (result.first()) {
					nbRow = result.getInt("Rows");
				}
			}
		} catch (SQLException e) {
			Logging.getLog().error(BDD_ACCESS_LOG);
		}
		return nbRow;
	}

}
