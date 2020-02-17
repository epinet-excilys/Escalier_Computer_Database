package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
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

	public void create(Computer computer) throws SQLException {

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

			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		}
	}

	public void delete(int idSuppression) throws SQLException {

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(deleteStatement);) {
			stmt.setInt(1, idSuppression);
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		}
	}

	public void update(Computer computer) throws SQLException {

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(updateStatement);) {

			stmt.setInt(5, computer.getId());

			stmt.setString(1, computer.getName());
			stmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroDate().atTime(LocalTime.MIDNIGHT)));
			stmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscoDate().atTime(LocalTime.MIDNIGHT)));
			stmt.setInt(4, computer.getCompany().getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		}

	}

	public Optional<Computer> find(int idSearch) throws SQLException {

		Computer computer = new Computer();

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getStatement);) {
			stmt.setInt(1, idSearch);
			result = stmt.executeQuery();
			
			
			if (result.first()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result);

			}

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		} finally {
			result.close();
		}

		return Optional.ofNullable(computer);
	}

	public Optional<ArrayList<Computer>> findAll() throws SQLException {

		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllStatement);) {

			result = stmt.executeQuery();
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result);
				list.add(computer);
			}

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		} finally {
			result.close();

		}

		return Optional.ofNullable(list);
	}

	public Optional<ArrayList<Computer>> findAllPaginate(int ligneDebutOffSet, int taillePage) throws SQLException {

		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer;

		try (Connection connect = ConnexionSQL.getConn();
				PreparedStatement stmt = connect.prepareStatement(getAllPaginateStatement);) {
			stmt.setInt(1, ligneDebutOffSet);
			stmt.setInt(2, taillePage);
			result = stmt.executeQuery();
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result);
				list.add(computer);
			}

		} catch (SQLException e) {
			// TODO REMPLIR AVEC LOG
		} finally {
			result.close();

		}

		return Optional.ofNullable(list);
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
			// TODO REMPLIR AVEC LOG
		} finally {
			result.close();
		}

		return nbRow;

	}

}
