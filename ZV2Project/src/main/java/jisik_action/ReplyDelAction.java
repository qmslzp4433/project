package jisik_action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jisik_dao.JisikReplyDAO;
import jisik_dao.JisikTableDAO;
import jisik_vo.JisikReplyVO;

/**
 * Servlet implementation class ReplyDelAction
 */
@WebServlet("/jisik/reply_del.do")
public class ReplyDelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		int reply_idx = Integer.parseInt(request.getParameter("reply_idx"));
		
		//�� ��� seq���� ������ seq ��������!
		int idx = JisikReplyDAO.getInstance().selectForIdx_from_reply_idx(reply_idx);
		
		//�� ���� ��������!!!!
		JisikReplyVO vo = JisikReplyDAO.getInstance().selectOne(reply_idx);
		int ref = vo.getRef();
		String reOrder = vo.getReorder();
		String base_reOrder = reOrder.substring(0,reOrder.length()-1);
		
		//�θ����� ��� ���� ������Ʈ!!!!!
		JisikReplyDAO.getInstance().update_down_reply_num(ref, base_reOrder);
		
		int res = JisikReplyDAO.getInstance().delete_reply(reply_idx);
		
		
		System.out.println("idx:"+idx);
		
		String result = "no";
		
		if( res == 1 ) {
			
			result = "yes";
		}
		
		
		//������ ���� ��� ���� ������Ʈ
		JisikTableDAO.getInstance().deleteReplyInfo(idx);
		
		String resultStr = String.format("[{'result':'%s'}]", result);
		
		response.getWriter().print(resultStr);
		
	}

}
