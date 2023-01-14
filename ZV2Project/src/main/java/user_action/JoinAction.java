package user_action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user_dao.JoinDAO;
import user_vo.JoinVO;

/**
 * Servlet implementation class JoinAction
 */
@WebServlet("/user/join.do")
public class JoinAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//post����� ��� �ѱ��� ������!
		request.setCharacterEncoding("utf-8");
		
		// USER_TOTAL ���̺� ����
		String id = request.getParameter("id");
		String pw = request.getParameter("upwd");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		String emailaddr = request.getParameter("email_addr");
		
		// USER_STUDENT ���̺� ���� 
		String hope_yn = request.getParameter("find_yn");
		String grade = request.getParameter("grade");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String addr = city + " " + country;
		String gender = request.getParameter("gender_yn");
		String hashtag;
		String introduce = request.getParameter("introduce");
		String[] subject_arr = request.getParameterValues("subject");
		String hope_subject;
		String hope_time = request.getParameter("time");
		String[] hashtag_arr = request.getParameterValues("hashtag_field");
		
		//����� �ؽ��±״� �������� ���� �� �ֱ� ������ ���� 1���� �ۿ��� �ְ� ����. ( ��ǥ ó���� ����)
		hope_subject = subject_arr[0];
		hashtag = hashtag_arr[0];
		
		for(int i = 1 ; i < subject_arr.length ; i++) {
			hope_subject += ", " + subject_arr[i];
		}
		
		
		for(int i = 1 ; i < hashtag_arr.length ; i++) {
			hashtag += ", " + hashtag_arr[i];
		}
		
		
		JoinVO vo = new JoinVO();
		vo.setId(id);
		vo.setPw(pw);
		vo.setName(name);
		vo.setNickname(nickname);
		vo.setPhone(phone);
		vo.setEmailaddr(emailaddr);
		vo.setHope_yn(hope_yn);
		vo.setGrade(grade);
		vo.setAddr(addr);
		vo.setGender(gender);
		vo.setHope_subject(hope_subject);
		vo.setHope_time(hope_time);
		vo.setHashtag(hashtag);
		vo.setIntroduce(introduce);
		
		
		JoinDAO.getInstance().insert_user_total(vo);
		JoinDAO.getInstance().insert_user_student(vo);
		
		System.out.println("ȸ������ ����");
		response.sendRedirect("../Login.jsp?path=/ZV2Project/Main_page.jsp");
	}

}
