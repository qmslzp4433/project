package jisik_action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import jisik_dao.JisikReplyDAO;
import jisik_dao.JisikTableDAO;
import jisik_vo.JisikReplyVO;
import jisik_vo.LoginVO;

/**
 * Servlet implementation class JisikReReplyAction
 */
@WebServlet("/jisik/reReply.do")
public class JisikReReplyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String web_path = "/upload/";
		
		ServletContext application = request.getServletContext();

		String path = application.getRealPath(web_path);
		int max_size = 1024 * 1024 * 100;
		
		MultipartRequest mr = new MultipartRequest(request, path, max_size);
		
		
		String p_content = mr.getParameter("content");
		String content = URLDecoder.decode(p_content, "utf-8");

		int reply_idx = Integer.parseInt( mr.getParameter("reply_idx") );
		
		//���� ����� ���� ������
		JisikReplyVO baseVO = JisikReplyDAO.getInstance().selectOne(reply_idx);
		String baseOrder = baseVO.getReorder();
		int baseRef = baseVO.getRef();
		int idx = baseVO.getIdx();
		
		JisikReplyVO matchVO = new JisikReplyVO();
		matchVO.setRef(baseRef);
		matchVO.setReorder(baseOrder);
		
		//���ǿ� ����� ���̵� ã��
		HttpSession session = request.getSession();
		LoginVO userVO = (LoginVO) session.getAttribute("user");
		String id = userVO.getId();

		//�迭�� �ִ� ã��
		String maxOrder = JisikReplyDAO.getInstance().selectOne(matchVO);
		
		
		//reorder ���ϱ�
		String newOrder = "";
		
		if( maxOrder.equals("empty") ) {
			
			newOrder = baseOrder + "a";
			
		}else {
			
			int length = baseOrder.length();
			char order = maxOrder.charAt(length);
			order++;
			newOrder = baseOrder + order; 
			
		}
		
		
		//insert ���� VO ����
		JisikReplyVO vo = new JisikReplyVO();
		vo.setIdx(idx);
		vo.setRef(baseRef);
		vo.setContent(content);
		vo.setReorder(newOrder);
		vo.setId(id);
		
		
		
		//�θ��� ������Ʈ
		JisikReplyDAO.getInstance().updateReplyNum(reply_idx);
		
		//������ ������Ʈ
		JisikTableDAO.getInstance().updateReplyInfo(idx);
		
		
		//���� �߰�
		JisikReplyDAO.getInstance().insert_reReply(vo);
		
		
		//��� �̵�
		response.sendRedirect("view.do?idx="+idx);
		
	}

}
