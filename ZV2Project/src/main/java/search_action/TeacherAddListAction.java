package search_action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search_dao.Teacher_SearchDAO;
import search_vo.Teacher_SearchVO;

@WebServlet("/search/TeacherAddList.do")
public class TeacherAddListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");	
		
		Teacher_SearchDAO.getInstance().add();
		
		List<Teacher_SearchVO> list = Teacher_SearchDAO.getInstance().selectList();
		
		for(int i = 0; i < list.size(); i++) {
			String id = list.get(i).getId();
			String title = Teacher_SearchDAO.getInstance().video_selectOne(id);
			list.get(i).setTitle(title);
		}
		
		int length = list.size();
		
		boolean compare = Teacher_SearchDAO.getInstance().compare();
		
		request.setAttribute("list", list);
		request.setAttribute("compare", compare);
		request.setAttribute("length", length);
		
		RequestDispatcher rd = request.getRequestDispatcher("teacher_add.jsp");
		rd.include(request, response);
		
	}

}
