package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.CompanyDTO;
import model.Company;

public final class CompanyMapper {

	private static volatile CompanyMapper instance = null;

	private CompanyMapper() {
		super();
	}

	public final static CompanyMapper getInstance() {

		if (CompanyMapper.instance == null) {

			synchronized (CompanyMapper.class) {
				if (CompanyMapper.instance == null) {
					CompanyMapper.instance = new CompanyMapper();
				}
			}
		}
		return CompanyMapper.instance;

	}
	
	public Company getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		
		int idComp = resultSet.getInt("id");
		String nameComp = resultSet.getString("name");
		
		Company company = new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
		
		return company;
		
		
	}

	public CompanyDTO fromCompanyToCompanyDTO(Company company) {
		return new CompanyDTO(company.getId(),company.getName());
	}
	

}
