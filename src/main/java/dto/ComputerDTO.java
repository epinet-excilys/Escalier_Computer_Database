package dto;


public class ComputerDTO {
	
	private int id;
	private String name;
	private String introDate;
	private String discoDate;
	private CompanyDTO company;
	
	
	
	
	
	public ComputerDTO(String name, String introDateString, String discoDateString, CompanyDTO companyDTO) {
		this.name = name;
		this.introDate = introDateString;
		this.discoDate = discoDateString;
		this.company = companyDTO;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroDateString() {
		return introDate;
	}
	public void setIntroDateString(String introDateString) {
		this.introDate = introDateString;
	}
	public String getDiscoDateString() {
		return discoDate;
	}
	public void setDiscoDateString(String discoDateString) {
		this.discoDate = discoDateString;
	}
	public CompanyDTO getCompanyDTO() {
		return company;
	}
	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.company = companyDTO;
	}


	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introDate=" + introDate
				+ ", discoDate=" + discoDate + ", companyDTO=" + company + "]";
	}
	
	

		

}
