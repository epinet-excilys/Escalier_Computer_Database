package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import exception.Logging;
import mapper.ComputerMapper;
import model.Computer;
import service.ComputerDAOImpl;

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet(name = "DashBoardServlet", urlPatterns = "/DashBoard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int pageIterator = 0;
	private int pageSize = 20;
	private int maxPage = 0;
	private int NbRowComputer = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		NbRowComputer = ComputerDAOImpl.getInstance().getNbRows();

		maxPage = NbRowComputer / pageSize;
		while (NbRowComputer % maxPage != 0) {
			maxPage++;
		}
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		ArrayList<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();

		if (request.getParameter("taillePage") != null) {
			pageSize = Integer.parseInt(request.getParameter("taillePage"));
		}
		if (request.getParameter("pageIterator") != null) {
			pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
			computerList = ComputerDAOImpl.getInstance().getAllPaginateComput(pageIterator * pageSize, pageSize);
		} else {
			pageIterator = 0;
			computerList = ComputerDAOImpl.getInstance().getAllPaginateComput(0, 20);
		}

		computerList.stream().forEach(
				computer -> computerDTOList.add(ComputerMapper.getInstance().fromComputerToComputerDTO(computer)));

		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageIterator", pageIterator);

		request.setAttribute("NbRowComputer", NbRowComputer);
		request.setAttribute("computerList", computerList);
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
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

}
