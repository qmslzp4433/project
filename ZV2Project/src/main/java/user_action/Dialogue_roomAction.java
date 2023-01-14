package user_action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user_dao.Dialogue_roomDAO;
import user_vo.Dialogue_roomVO;

/**
 * Servlet implementation class Dialogue_roomAction
 */
@WebServlet("/user/dialogue_room.do")
public class Dialogue_roomAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int talk_seq = Integer.parseInt(request.getParameter("talk_seq"));
		String id = request.getParameter("login_id");
		
		//���� �л� ������ ���� select
		Dialogue_roomVO login_id = Dialogue_roomDAO.getInstance().login_id_select(id);
		
		Dialogue_roomDAO.getInstance().msg_update(talk_seq, id);
		
		String gubun = login_id.getGubun();
		System.out.println("roomdao�� ���� : " + gubun);
		
		Dialogue_roomDAO dao = Dialogue_roomDAO.getInstance();
		
		
		
		if (gubun.equals("������")) {
			Dialogue_roomVO opponent_info = dao.student_info(talk_seq);
			request.setAttribute("msg_list", opponent_info);
			
			List<Dialogue_roomVO> list = dao.teacher_msg_select(talk_seq);
			request.setAttribute("msg_content_list", list);
		}
		
		if (gubun.equals("�л�")) {
			Dialogue_roomVO opponent_info = dao.teacher_info(talk_seq);
			request.setAttribute("msg_list", opponent_info);
			
			List<Dialogue_roomVO> list = dao.student_msg_select(talk_seq);
			request.setAttribute("msg_content_list", list);
		}
		
		//�����(���ε� ��) list�� � jsp���� ����ϰ� �Ұ��� ���� = ������
		RequestDispatcher disp = request.getRequestDispatcher("Dialogue_room.jsp");
		// Dialogue_list.jsp ������ �����س��� 'list'��ü�� elǥ������� ������ ����� �� �ִ�.
		disp.forward(request, response);
		// ��  Dialogue_list.jsp ���� ����Ҳ��ϱ� �ű���� �� list�� ������.
		
		
	}

}
