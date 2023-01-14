package reference_action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_vo.LoginVO;
import reference_dao.ContentDAO;

/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/reference/buylist.do") //�ҷ����� url ����
public class BuylistAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
		
		//���ǿ��� �α��� id�޾ƿ�
		HttpSession session = request.getSession();
		LoginVO DAO= (LoginVO)session.getAttribute("user");
		if(DAO!=null) {
				
				
			String id = DAO.getId();			
			//�����ϱ�
			String resultStr = "";
			int ref_seq =Integer.parseInt(request.getParameter("ref_seq"));
			
			//������ �����߾����� ���ſ��� �Ǵ� /0�̿��� ���� �Ȼ�Ŵϱ� 0�϶��� �����ϱ� ����
			int checkbuy = ContentDAO.getInstance().b_check(ref_seq, id);
			//System.out.println(checkbuy);  0 �߳���
			
			 //bp_check�� ����Ʈ�� �����������ϴµ� �˳����� ��ȿ�� �˻��ؼ� 1�̸� ���� 0�̸� �Ұ��� ����ϴ� ��. 
			 //-> dbó����dao�ܰ迡�� �� �Ǿ �Ѿ���°� ���� int checkpoint =
			
			 
			 if( checkbuy == 0 ) {
			 ContentDAO.getInstance().b_insert(ref_seq, id); 
			 ContentDAO.getInstance().bp_update(ref_seq, id); 
			 
			 //�ڷ��Ǹ��� ����Ʈ������Ű��
			ContentDAO.getInstance().get_point(ref_seq); 
			 }
			 

			resultStr = "[{'param':'"+ ref_seq +"'}]";
			
			response.getWriter().print(resultStr);
		
		}
		
		
		
	}

}