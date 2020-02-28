package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CompanyDAO;
import dto.CompanyDTO;
import mapper.CompanyMapper;
import model.Company;
import model.Computer;
import service.CompanyDAOService;
import service.ComputerDAOService;

@WebServlet(name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ADDCOMPUTER = "/WEB-INF/views/addComputer.jsp";
	private static final LocalDate DATE_LIMITE = LocalDate.of(1971, 01, 01);


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CompanyMapper companyMapper = CompanyMapper.getInstance();
		CompanyDAOService companyDAOService = CompanyDAOService.getInstance();
		
		List<Company> companyList = companyDAOService.getAllCompany();
		List<CompanyDTO> companyDTOList = companyList.stream()
				.map(company -> companyMapper.fromCompanyToCompanyDTO(company)).collect(Collectors.toList());

		request.setAttribute("companyDTOList", companyDTOList);
		this.getServletContext().getRequestDispatcher(ADDCOMPUTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
//		ComputerDAOService computerDAOService = ComputerDAOService.getInstance();
//		CompanyDAOService companyDAOService = CompanyDAOService.getInstance();
//		
//		String computerName = request.getParameter("computerName");
//		LocalDate introducedDate = request.getParameter("introduced").isEmpty() ? null
//				: LocalDate.parse(request.getParameter("introduced"));
//		LocalDate discontinuedDate = request.getParameter("discontinued").isEmpty() ? null
//				: LocalDate.parse(request.getParameter("discontinued"));
//		int companyID = Integer.parseInt(request.getParameter("companyId"));
		
		//Company company = companyDAOService.findByID(companyID).get();
		
		Company company = new Company.Builder().setIdBuild(1).build();
		Computer computer = new Computer.Builder().setNameBuild("Nom")
				.setIntroducedDateBuild(LocalDate.now().minusYears(5))
				.setDiscontinuedDateBuild(LocalDate.now().minusYears(1)).setIdCompagnyBuild(company).build();

		
//		if (isDateOk(introducedDate, discontinuedDate)) {
//			
//			
//			Computer computer = new Computer.Builder()
//					.setNameBuild(computerName)
//					.setIntroducedDateBuild(introducedDate)
//					.setDiscontinuedDateBuild(discontinuedDate)
//					.setIdCompagnyBuild(company).build();
//			
//			
//					
//					
			System.out.println(computer);
//			
//		}

	}

	private boolean isDateOk(LocalDate introducedDate, LocalDate discontinuedDate) {
		boolean bothAreNull = ((introducedDate == null) && (discontinuedDate == null));
		boolean introducedIsOk = ((introducedDate != null) && (introducedDate.isAfter(DATE_LIMITE)));
		boolean discontinuedIsOk = ((discontinuedDate != null) && (discontinuedDate.isAfter(DATE_LIMITE)));

		return (introducedIsOk && discontinuedIsOk && (discontinuedDate.isAfter(introducedDate)))
				|| ((introducedIsOk) ^ (discontinuedIsOk))
				|| (bothAreNull);

	}

}
