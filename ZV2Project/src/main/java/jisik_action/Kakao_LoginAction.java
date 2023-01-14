package jisik_action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_dao.LoginDAO;
import jisik_vo.LoginVO;

/**
 * Servlet implementation class Kakao_LoginAction
 */
@WebServlet("/kakaologin.do")
public class Kakao_LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String kakao_email = request.getParameter("kakao_email");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String path = request.getParameter("path");
		System.out.println("���̵�:"+id);
		System.out.println("īī���α���:"+path);
		
//īī�� �α��� ���ʽõ�=====================================================================================================================
		if(id.equals("try")) {
			LoginVO user = LoginDAO.getInstance().kakao_login(kakao_email);
			
			// LoginVO�� ������� �����Ű�, LoginDAO�� ���� getInstance ȣ���ؼ� selectOne �޼ҵ带 ȣ���Ͽ� StringŸ���� ID�� ������.
			// ���̵� ������. (���̵� ���� ��ȿ�� üũ)
			//��ȸ�� �� �ƴٸ� user���� ���ü� �ִ� ���� id�� pw 2�����̴�.
			//��ȸ�� �ȵƴٸ� user�� null���̴�.
			
			String resultStr = "";
			
			//user�� null���̸� �ְ����� ������ īī�� �̸����� �������� ������.
			if( user==null ) {
				resultStr = "[{'param' : 'no_account'}]";
				response.getWriter().print(resultStr);
				//�ٷ� resultStr�� login.jsp�� �ݹ�޼ҵ�(resultCheck)�� ������.
				return; //��й�ȣ �˻��� �ʿ䰡 ����. �ٷ� ����.
			}
					
			//�� if�� 2���� ���� ������ �ʾҴٸ� ����α�����.
			// �α��� �� ������ ���ǿ� ������ ����(���ε�)
			HttpSession session = request.getSession(); //request�� ���� session������ �Ҵ�޴� �۾�
			session.setMaxInactiveInterval(3600);	//���ǿ� ��� �� ���� �ð� (����:��)
			session.setAttribute("user", user); //user��� ��ü�� id,pw������ �������. �̰� ���ǿ� ����ִ°���.
			//������ ���������� ���������� ���ư��� �ȵǹǷ� RAM�޸𸮿� �����Ѵ�. ���� �ʿ��Ҷ��� ������Ѵ�.
			
			resultStr = String.format("[{'param' : 'success'}, {'p_path':'%s'}]", path);
			response.getWriter().print(resultStr);	//�α��� ������ ����ڿ��� �˷���.
		}
		
		
		
//īī�� �α��� ���� - ���ʵ�� �ʿ�==================================================================================================================
		else if(id.equals("no")) {
			request.setAttribute("kakao_email", kakao_email);
			request.setAttribute("path", path);
			RequestDispatcher disp = request.getRequestDispatcher("kakaologin.jsp");
			disp.forward(request, response);
		}
		
		

//īī�� �α��� ���ʵ���ϰ� �α���===================================================================================================================
		else {
			//�Ѱܹ��� ���̵���� ��ȸ
			LoginVO user = LoginDAO.getInstance().selectOne(id);
			// LoginVO�� ������� �����Ű�, LoginDAO�� ���� getInstance ȣ���ؼ� selectOne �޼ҵ带 ȣ���Ͽ� StringŸ���� ID�� ������.
			// ���̵� ������. (���̵� ���� ��ȿ�� üũ)
			//��ȸ�� �� �ƴٸ� user���� ���ü� �ִ� ���� id�� pw 2�����̴�.
			//��ȸ�� �ȵƴٸ� user�� null���̴�.
			
			String resultStr = "";
			
			//user�� null���̸� ���̵� ��ġ���� ������
			if( user==null ) {
				resultStr = "[{'param' : 'no_id'}]";
				response.getWriter().print(resultStr);
				//�ٷ� resultStr�� login.jsp�� �ݹ�޼ҵ�(resultCheck)�� ������.
				return; //��й�ȣ �˻��� �ʿ䰡 ����. �ٷ� ����.
			}
			
			//���� if���� ������ �ʾҴٸ� id���� �ִ°��̹Ƿ� pw�� �޾ƿ��� ���̴�.
			//DB���� �޾ƿ� ��й�ȣ�� �Ķ���ͷ� ���� ��й�ȣ�� ���Ѵ�.
			if( !user.getPw().equals(pw)) {	//��й�ȣ ����ġ
				resultStr = "[{'param' : 'no_pw'}]";
				response.getWriter().print(resultStr);
				//�ٷ� resultStr�� login.jsp�� �ݹ�޼ҵ�(resultCheck)�� ������.
				return; //��й�ȣ�� Ʋ�����Ƿ� �ٷ� ����.
			}
			
			//�� if�� 2���� ���� ������ �ʾҴٸ� ����α�����.
			// �α��� �� ������ ���ǿ� ������ ����(���ε�)
			
			//�� ���� īī�� �̸����� �ְ����� ������ ������Ʈ �Ѵ�. (�� �� �� ����ڴ� īī�� �̸��ϸ����� �α����� �����ϰ� ��)
			LoginVO kakao_update = new LoginVO();
			kakao_update.setId(id);
			kakao_update.setPw(pw);
			kakao_update.setKakao_email(kakao_email);
			LoginDAO.getInstance().kakao_email_update(kakao_update);
			
			
			HttpSession session = request.getSession(); //request�� ���� session������ �Ҵ�޴� �۾�
			session.setMaxInactiveInterval(3600);	//���ǿ� ��� �� ���� �ð� (����:��)
			session.setAttribute("user", user); //user��� ��ü�� id,pw������ �������. �̰� ���ǿ� ����ִ°���.
			//������ ���������� ���������� ���ư��� �ȵǹǷ� RAM�޸𸮿� �����Ѵ�. ���� �ʿ��Ҷ��� ������Ѵ�.
			
			resultStr = String.format("[{'param' : 'success'}, {'p_path':'%s'}]", path);
			response.getWriter().print(resultStr);	//�α��� ������ ����ڿ��� �˷���.
		}
		
		
	}
}
