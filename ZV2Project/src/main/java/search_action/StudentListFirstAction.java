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

/**
 * Servlet implementation class StudentListFirstAction
 */
@WebServlet("/search/StudentListFirst.do")
public class StudentListFirstAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Student_SearchDAO.getInstance().reset();
		
		List<Student_SearchVO> list = Student_SearchDAO.getInstance().first();
		
		int length = list.size();
		
		boolean compare = Student_SearchDAO.getInstance().compare();
		
		String subject = Student_SearchDAO.getInstance().subject();		
		String area = Student_SearchDAO.getInstance().area();		
		String myn = Student_SearchDAO.getInstance().myn();		
		String gender = Student_SearchDAO.getInstance().gender();	
		
		if(subject == "") {
			subject += "null";
		}
		
		if(area == "") {
			area += "null";
		}
		
		if(myn == "") {
			myn += "null";
		}
		
		if(gender == "") {
			gender += "null";
		}

		request.setAttribute("list", list);
		request.setAttribute("subject", subject);		
		request.setAttribute("area", area);		
		request.setAttribute("myn", myn);		
		request.setAttribute("gender", gender);
		request.setAttribute("length", length);
		request.setAttribute("compare", compare);
		
		RequestDispatcher disp = request.getRequestDispatcher("student_list.jsp");
		disp.forward(request, response);
	}

}
