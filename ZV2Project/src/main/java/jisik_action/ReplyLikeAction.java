package jisik_action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_dao.JisikReplyDAO;
import jisik_dao.ReplyLikeDAO;
import jisik_vo.LoginVO;

/**
 * Servlet implementation class ReplyLikeAction
 */
@WebServlet("/jisik/reply_like.do")
public class ReplyLikeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int reply_idx = Integer.parseInt(request.getParameter("idx"));
		
		//�۾��� ���̵�
		String p_id = request.getParameter("id");
		
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		
		String id = user.getId();
		
		//����� ���ƿ� �� ����
		JisikReplyDAO.getInstance().update_replyLikehit(reply_idx);
		
		//like_reply ���̺� ������ �߰�!!
		ReplyLikeDAO.getInstance().insert_replyLike(reply_idx, id);
		
		
		//���ƿ並 ���� ����Ʈ ����
		ReplyLikeDAO.getInstance().update_point(p_id);
		
		//������ ���ƿ� �� ��������
		int likehit = JisikReplyDAO.getInstance().selectLikehit(reply_idx);
		
		String resultStr = String.format("[{'likehit':%d},{'idx':%d}]", likehit,reply_idx);
		response.getWriter().print(resultStr);
		
		
		
	}

}
