package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.CompanyDAO;
import model.Company;
import model.Computer;

public final class ComputerMapper {

	private static volatile ComputerMapper instance = null;

	private ComputerMapper() {
		super();
	}

	public final static ComputerMapper getInstance() {

		if (ComputerMapper.instance == null) {

			synchronized (ComputerMapper.class) {
				if (ComputerMapper.instance == null) {
					ComputerMapper.instance = new ComputerMapper();
				}
			}
		}
		return ComputerMapper.instance;

	}

	public Computer getComputerFromResultSet(ResultSet resultSet) throws SQLException {
		
		
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		//
		LocalDate introDate = (resultSet.getTimestamp("introduced") != null
				? resultSet.getTimestamp("introduced").toLocalDateTime().toLocalDate()
				: null);
		LocalDate discoDate = (resultSet.getTimestamp("discontinued") != null
				? resultSet.getTimestamp("discontinued").toLocalDateTime().toLocalDate()
				: null);

		
		int idComp = (resultSet.getInt("company_id"));
		String nameComp = (resultSet.getString("company.name"));
		
		Company company = new Company.CompanyBuilder().setIdBuild(idComp).setNameBuild(nameComp).build();
		
	
		
		Computer computer = new Computer.ComputerBuilder().setIdBuild(id).setNameBuild(name)
				.setIntroDateBuild(introDate).setDiscoDateBuild(discoDate).setIdCompagnyBuild(company).build();
		return computer;
	}

	public Computer fromStringToComput(String[] resultTab) {
		int id = Integer.parseInt(resultTab[0]);
		String name = resultTab[1];
		LocalDate introDate = fromStringToLocalDate(resultTab[2]);
		LocalDate discoDate = fromStringToLocalDate(resultTab[3]);
		int idComp = Integer.parseInt(resultTab[4]);
		
		//TODO REVOIR CE PASSAGE
		Company company= null;
		try {
			company = CompanyDAO.getInstance().find(idComp).get();
		} catch (SQLException e) {
			// TODO log
		}
		
		Computer computer = new Computer.ComputerBuilder().setIdBuild(id).setNameBuild(name)
				.setIntroDateBuild(introDate).setDiscoDateBuild(discoDate).setIdCompagnyBuild(company).build();

		return computer;
	}

	// TODO : Ne marche pas

	public LocalDate fromStringToLocalDate(String s) {

		if (!s.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime = LocalDate.parse(s, formatter);
			return dateTime;
		} else {
			return null;
		}
	}

}
