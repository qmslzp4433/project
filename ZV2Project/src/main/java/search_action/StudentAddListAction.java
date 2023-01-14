package search_action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search_dao.Student_SearchDAO;
import search_vo.Student_SearchVO;

@WebServlet("/search/StudentAddList.do")
public class StudentAddListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");	
		
		Student_SearchDAO.getInstance().add();
		
		List<Student_SearchVO> list = Student_SearchDAO.getInstance().selectList();
		
		int length = list.size();
		
		boolean compare = Student_SearchDAO.getInstance().compare();
		
		request.setAttribute("list", list);
		request.setAttribute("compare", compare);
		request.setAttribute("length", length);
		
		RequestDispatcher rd = request.getRequestDispatcher("student_add.jsp");
		rd.include(request, response);
		
	}

}
