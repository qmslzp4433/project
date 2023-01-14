package user_action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import user_dao.Join_teacherDAO;
import user_vo.Join_teacherVO;

/**
 * Servlet implementation class Join_teacherAction
 */
@WebServlet("/user/jointeacher.do")
public class Join_teacherAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//post����� ��� �ѱ��� ������!
		request.setCharacterEncoding("utf-8");
		
		String web_path = "/upload/";
		ServletContext application = request.getServletContext();
		
		//���� ���
		String path = application.getRealPath(web_path);
		System.out.println(path);
		// ������� �ּҼ���=======================================================================
		
		//�ִ� ũ�� ����
		int max_size = 1024 * 1024 * 200;
		
		// �����߿�!!
		// 'request' �� 'mr'�� �����ߴٴ� ��. �� ������ ���ʹ� 'request'�� ����� �� ����.
		// request ��� 'mr'�� �ؾ���.
		MultipartRequest mr = new MultipartRequest(request,
				path,
				max_size,
				"utf-8",
				new DefaultFileRenamePolicy());
		
		// USER_TOTAL ���̺� ����
		String id = mr.getParameter("id");
		String pw = mr.getParameter("upwd");
		String name = mr.getParameter("name");
		String nickname = mr.getParameter("nickname");
		String phone1 = mr.getParameter("phone1");
		String phone2 = mr.getParameter("phone2");
		String phone3 = mr.getParameter("phone3");
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		String emailaddr = mr.getParameter("email_addr");
		
// USER_TEACHER ���̺� ���� =======================================================
		//������ ���� ���ϸ� �ҷ�����
		String profile_photo = "";
		File profile_f = mr.getFile("photo_upload");
		System.out.println("������ ���� �޾ƿ�!  " + profile_f);
		if(profile_f != null) {
			profile_photo = profile_f.getName();
			
		}
		// DB�� ��°��� profile_photo �̴�.
		
		String idnumber = mr.getParameter("idnumber");
		String city = mr.getParameter("city");
		String country = mr.getParameter("country");
		String addr = city + " " + country;
		String gender = mr.getParameter("gender_yn");
		String mbti = mr.getParameter("mbti");
		String introduce = mr.getParameter("introduce");
		String video_yn = mr.getParameter("video_yn");
		String pre_yn = mr.getParameter("pre_yn");
		
		String area;
		String[] area_arr = mr.getParameterValues("area_country");
		String subject;
		String[] subject_arr =  mr.getParameterValues("subject");
		String hashtag;
		String[] hashtag_arr = mr.getParameterValues("hashtag_field");
		System.out.println("�Ķ���� �� �޾ƿ���.");
		//��������, ����, �ؽ��±״� �������� ���� �� �ֱ� ������ ���� 1���� �ۿ��� �ְ� ����. ( ��ǥ ó���� ����)
		area = area_arr[0];
		subject = subject_arr[0];
		hashtag = hashtag_arr[0];
		System.out.println("�迭�� �� �޾ƿ���.");
		for(int i = 1 ; i < area_arr.length ; i++) {
			area += ", " + area_arr[i]; }
		
		System.out.println(area);
		
		for(int i = 1 ; i < subject_arr.length ; i++) {
			subject += ", " + subject_arr[i]; }
		
		for(int i = 1 ; i < hashtag_arr.length ; i++) {
			hashtag += ", " + hashtag_arr[i]; }
		
		
		System.out.println(area);
		System.out.println(subject);
		System.out.println(hashtag);
		System.out.println(profile_photo);
		Join_teacherVO vo = new Join_teacherVO();
		
		vo.setId(id);
		vo.setPw(pw);
		vo.setName(name);
		vo.setNickname(nickname);
		vo.setPhone(phone);
		vo.setEmailaddr(emailaddr);
		
		vo.setProfile(profile_photo);
		vo.setIdnumber(idnumber);
		vo.setAddr(addr);
		vo.setArea(area);
		vo.setGender(gender);
		vo.setSubject(subject);
		vo.setMbti(mbti);
		vo.setHashtag(hashtag);
		vo.setIntroduce(introduce);
		vo.setVideo_yn(video_yn);
		vo.setPre_yn(pre_yn);
		
		Join_teacherDAO.getInstance().insert_user_total(vo);
		Join_teacherDAO.getInstance().insert_user_teacher(vo);
		
		//���ܿ���  ���ϸ� �ҷ�����
		for(int i = 0 ; i < 5 ; i++) {
			String insert_video_name = "";
			String video_name_wonbon = "video_upload" + i;
			File video_f = mr.getFile(video_name_wonbon);
			
			if(video_f != null) {
				insert_video_name = video_f.getName();
				Join_teacherDAO.getInstance().insert_teacher_video(id,insert_video_name);
				System.out.println(insert_video_name);
			}
		}
		// DB�� ��°��� profile_photo �̴�.
		
		System.out.println("ȸ������ ����");
		response.sendRedirect("../Login.jsp?path=/ZV2Project/Main_page.jsp");
	}
}
