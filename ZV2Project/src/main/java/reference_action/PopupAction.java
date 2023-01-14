package reference_action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_vo.LoginVO;
import reference_dao.CommentDAO;

/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/reference/popup.do") //�ҷ����� url ����
public class PopupAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
		HttpSession session = request.getSession();

		LoginVO DAO= (LoginVO)session.getAttribute("user");
		
		//�ڷ��ȣ�ޱ�
		int ref_seq =Integer.parseInt( request.getParameter("ref_seq"));
		//�Է������ޱ�
		int score =Integer.parseInt( request.getParameter("score"));
		//����Ʈ ���� �ޱ�
		String content = request.getParameter("content");
		if(DAO!=null) {//�α������� ������
			
			//id�ޱ�
			String id = DAO.getId();
			
			
			//dbó��
			CommentDAO.getInstance().insert_comment(ref_seq,content,id,score);
			
		}
			
		

		
		
		//���߿� ������������ ������
		RequestDispatcher disp = 
		request.getRequestDispatcher("reference_detail.do?ref_seq="+ ref_seq);
		disp.forward(request,response);
		
			
				
				
	}		
				
	

}
