package user_action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user_dao.NICKNAME_checkDAO;
import user_vo.NICKNAME_checkVO;


/**
 * Servlet implementation class ID_checkAction
 */
@WebServlet("/user/nickcheck.do")
public class NICKNAME_checkAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nickname = request.getParameter("nickname");
		NICKNAME_checkVO user = NICKNAME_checkDAO.getInstance().selectOne(nickname);
		String resultStr = "";
		
		if( user==null ) {
			resultStr = "[{'param' : 'can_use_nickname'}]";
			response.getWriter().print(resultStr);
			//�ٷ� resultStr�� login.jsp�� �ݹ�޼ҵ�(resultCheck)�� ������.
			return; //���̵� ��밡��! �ٷγ���.
		}
		
		//����Ʈ�� ����� �� ���� �̴�.
		resultStr = "[{'param' : 'cant_use_nickname'}]";
		response.getWriter().print(resultStr);	//���̵� ��� �Ұ� ����
		
	}

}
