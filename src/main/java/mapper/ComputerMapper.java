package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import dao.CompanyDAO;
import dto.CompanyDTO;
import dto.ComputerDTO;
import exception.Logging;
import model.Company;
import model.Computer;

public final class ComputerMapper {

	private static volatile ComputerMapper instance = null;
	private static final String bddAccessLog = "Impossible de recuperer le computer dans la BDD";
	private static final String ERREUR_DE_PARSING_DATE = "Impossible de Parser l'entrée en Date";

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

	public Optional<Computer> getComputerFromResultSet(ResultSet resultSet) throws SQLException {

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
		return Optional.ofNullable(computer);
	}

	public Computer fromStringToComput(String[] resultTab) throws ParseException, SQLException {
		int id = Integer.parseInt(resultTab[0]);
		String name = resultTab[1];
		LocalDate introDate = fromStringToLocalDate(resultTab[2]);
		LocalDate discoDate = fromStringToLocalDate(resultTab[3]);
		int idComp = Integer.parseInt(resultTab[4]);

		// TODO REVOIR CE PASSAGE
		Company company = null;
		company = CompanyDAO.getInstance().find(idComp).get();

		Computer computer = new Computer.ComputerBuilder().setIdBuild(id).setNameBuild(name)
				.setIntroDateBuild(introDate).setDiscoDateBuild(discoDate).setIdCompagnyBuild(company).build();

		return computer;
	}

	public LocalDate fromStringToLocalDate(String s) throws DateTimeParseException {

		if ((s != null )&& !s.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime;
			dateTime = LocalDate.parse(s, formatter);

			return dateTime;
		} else {
			return null;
		}
	}

	public ComputerDTO fromComputerToComputerDTO(Computer computer) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(computer.getCompany().getId());
		companyDTO.setName(computer.getCompany().getName());

		ComputerDTO computerDTO = new ComputerDTO( computer.getName(),
				computer.getIntroDate()==null?null:computer.getIntroDate().toString(),
				computer.getDiscoDate()==null?null:computer.getDiscoDate().toString(),companyDTO);
		computerDTO.setId(computer.getId());
		return computerDTO;
	}

}
