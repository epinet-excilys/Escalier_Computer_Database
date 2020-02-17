package model;

public class Company {
	
	private int id;
	private String name;
	
	private Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Company() {
	}
	


	public Company(int id) {
		this.id = id;
		
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
	
	public static class CompanyBuilder {
		private int idBuild;
		private String nameBuild;

		public CompanyBuilder() {
		}

		public CompanyBuilder setIdBuild(int idBDD) {
			this.idBuild = idBDD;
			return this;
		}

		public CompanyBuilder setNameBuild(String nameBDD) {
			this.nameBuild = nameBDD;
			return this;
		}

		public Company build() {
			return new Company(this);
		}
	}
	
	public Company(CompanyBuilder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Company [name=" + name + "]";
	}
	
	

}
