package jisik_action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_dao.JisikTableDAO;
import jisik_dao.JjimDAO;
import jisik_vo.JisikTableVO;
import jisik_vo.JjimVO;
import jisik_vo.LoginVO;

/**
 * Servlet implementation class PageListAction
 */
@WebServlet("/jisik/page_list.do")
public class PageListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("�Գ�");


		int page = Integer.parseInt(request.getParameter("page"));
		String param = request.getParameter("param");
		System.out.println(page);
		System.out.println("�Ķ����:" + param);


		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		List<JjimVO> jjimList = new ArrayList<JjimVO>();

		if(user != null) {
			String id = user.getId();
			jjimList = JjimDAO.getInstance().select_JjimIdx(id);

		}


		int start = (page-1)*15 + 1;
		int end = page*15;

		System.out.println("��ŸƮ:" + start);
		System.out.println("�ص�:" + end);
		List<JisikTableVO> list = new ArrayList<JisikTableVO>();

		if(param == "") {

			list = JisikTableDAO.getInstance().pageSelectList(start, end);

		}else {
			String real_param = param.substring(1);
			int index = real_param.indexOf('=');

			//�˻� ����
			String search_part = real_param.substring(0, index);

			//�Ķ���� ��
			String param_content = real_param.substring(index+1, real_param.length());

			if( search_part.equals("search_content") ) {
				list = JisikTableDAO.getInstance().page_searchTextselectList(param_content, start, end);

			}else if( search_part.equals("jjim") ) {

				String id = user.getId();
				list = JisikTableDAO.getInstance().page_searchJjimselectList(id, start, end);



			}else if( search_part.equals("mylist") ) {

				String id = user.getId();
				list = JisikTableDAO.getInstance().page_searchMyList(id, start, end);



			}else if( search_part.equals("subject") ) {

				list = JisikTableDAO.getInstance().page_searchSubejctselectList(param_content, start, end);
			}

		}



		for(int i = 0; i < list.size(); i++ ) {

			list.get(i).setLike_check(0);
		}//for


		for( int i = 0; i < list.size(); i++ ) {

			for(int j = 0; j < jjimList.size(); j++) {

				if( list.get(i).getIdx() == jjimList.get(j).getJjim_idx() ) {

					list.get(i).setLike_check(1);
					break;
				}

			}//inner

		}//outer


		request.setAttribute("list", list);
		System.out.println(list);

		RequestDispatcher disp = request.getRequestDispatcher("list_form.jsp");
		response.setCharacterEncoding("utf-8");
		disp.include(request, response);

	}

}
