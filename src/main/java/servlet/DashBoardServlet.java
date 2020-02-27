package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import mapper.ComputerMapper;
import model.Computer;
import service.ComputerDAOService;


@WebServlet(name = "DashBoardServlet", urlPatterns = "/DashBoard")
public class DashBoardServlet extends HttpServlet {
	
	private int pageIterator = 0;
	private int pageSize = 20;
	private int maxPage = 0;
	private int NbRowComputer = 0;
	
	private static final String DASHBOARD = "/WEB-INF/views/dashboard.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDTO> computerDTOList = new ArrayList<>();
		
		
		NbRowComputer = ComputerDAOService.getInstance().getNbRows();

		//TODO REVOIR CE PASSAGE POUR ARRONDIR LE NOMBRE
		maxPage = NbRowComputer / pageSize;
		
		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		if (request.getParameter("pageIterator") != null) {
			pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
			computerList = ComputerDAOService.getInstance().getAllPaginateComput(pageIterator * pageSize, pageSize);
		} else {
			pageIterator = 0;
			computerList = ComputerDAOService.getInstance().getAllPaginateComput(0, 20);
		}

//		computerList = paginate(request);

		computerList.stream().forEach(
				computer -> computerDTOList.add(ComputerMapper.getInstance().fromComputerToComputerDTO(computer)));

		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageIterator", pageIterator);
		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("computerList", computerList);
		request.getRequestDispatcher(DASHBOARD).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<Computer> paginate(HttpServletRequest request) {
		List<Computer> computerList = new ArrayList<>();
	
		
		return computerList;
		
	}

}
