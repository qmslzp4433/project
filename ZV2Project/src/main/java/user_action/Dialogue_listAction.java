package user_action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user_dao.Dialogue_listDAO;
import user_vo.Dialogue_listVO;

/**
 * Servlet implementation class Dialogue_listAction
 */
@WebServlet("/user/dialogue_list.do")
public class Dialogue_listAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		Dialogue_listVO selected_id = Dialogue_listDAO.getInstance().selectOne(id);
		String gubun = selected_id.getGubun();
		
		Dialogue_listDAO dao = Dialogue_listDAO.getInstance();
		//DAO���� ������ ��ȭ���
		if (gubun.equals("������")) {
			System.out.println("list action���� dao�� ����Ʈ�Ϸ���!!(������ ��������)");
			List<Dialogue_listVO> list = dao.student_dial_info(id);
			request.setAttribute("content_list", list);
			//�����(���ε� ��) list�� � jsp���� ����ϰ� �Ұ��� ���� = ������
			RequestDispatcher disp = request.getRequestDispatcher("Dialogue_list_teacher.jsp");
			// Dialogue_list.jsp ������ �����س��� 'list'��ü�� elǥ������� ������ ����� �� �ִ�.
			disp.forward(request, response);
			// ��  Dialogue_list.jsp ���� ����Ҳ��ϱ� �ű���� �� list�� ������.
		}
		
		
		//�л��� ��� �ѹ��� ��ü �޽��� ����Ʈ�� ���� �޽��� ����Ʈ�� �Ѵ� �޾ư���.
		else if (gubun.equals("�л�")) {
			List<Dialogue_listVO> list = dao.teacher_dial_info(id);
			request.setAttribute("content_list", list);
			
			List<Dialogue_listVO> wish_list = dao.wish_teacher_dial_info(id);
			request.setAttribute("content_wish_list", wish_list);
			//�����(���ε� ��) list�� � jsp���� ����ϰ� �Ұ��� ���� = ������
			RequestDispatcher disp = request.getRequestDispatcher("Dialogue_list_student.jsp");
			// Dialogue_list.jsp ������ �����س��� 'list'��ü�� elǥ������� ������ ����� �� �ִ�.
			disp.forward(request, response);
			// ��  Dialogue_list.jsp ���� ����Ҳ��ϱ� �ű���� �� list�� ������.
		}
		
	
	}

}


