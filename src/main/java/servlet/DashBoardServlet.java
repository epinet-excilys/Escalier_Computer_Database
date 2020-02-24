package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.Logging;
import model.Computer;
import service.ComputerDAOImpl;


/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet(name= "DashBoardServlet",  urlPatterns ="/DashBoard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setAttribute("NbRowComputer",100000);
		ArrayList<Computer> ComputerList = ComputerDAOImpl.getInstance().getAllPaginateComput(0,20);
		request.setAttribute("ComputerListDAO",ComputerList );
		for(Computer c : ComputerList){
			System.out.println(c);
		};

		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
