package user_action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user_dao.ID_checkDAO;
import user_vo.ID_checkVO;

/**
 * Servlet implementation class ID_checkAction
 */
@WebServlet("/user/idcheck.do")
public class ID_checkAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		ID_checkVO user = ID_checkDAO.getInstance().selectOne(id);
		String resultStr = "";
		
		if( user==null ) {
			resultStr = "[{'param' : 'can_use_id'}]";
			response.getWriter().print(resultStr);
			//�ٷ� resultStr�� login.jsp�� �ݹ�޼ҵ�(resultCheck)�� ������.
			return; //���̵� ��밡��! �ٷγ���.
		}
		
		//����Ʈ�� ����� �� ���� �̴�.
		resultStr = "[{'param' : 'cant_use_id'}]";
		response.getWriter().print(resultStr);	//���̵� ��� �Ұ� ����
		
	}

}
