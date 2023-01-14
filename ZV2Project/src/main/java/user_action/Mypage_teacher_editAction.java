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
 * Servlet implementation class Mypage_teacher_editAction
 */
@WebServlet("/user/teacher_edit.do")
public class Mypage_teacher_editAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//post����� ��� �ѱ��� ������!
				request.setCharacterEncoding("utf-8");
				int profile_edit_yn = 0; //������ ���濩�� �Ǵ�.
				int area_edit_yn = 0; //���� ���濩�� �Ǵ�.
				
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
				System.out.println("id : " + id);
				
				String pw = mr.getParameter("upwd");
				System.out.println("pw : " + pw);
				
				String name = mr.getParameter("name");
				System.out.println("name : " + name);
				
				String phone1 = mr.getParameter("phone1");
				String phone2 = mr.getParameter("phone2");
				String phone3 = mr.getParameter("phone3");
				String phone = phone1 + "-" + phone2 + "-" + phone3;
				System.out.println("phone : " + phone);
				
		// USER_TEACHER ���̺� ���� =======================================================
				//������ ���� ���ϸ� �ҷ�����
				String profile_photo = "";
				File profile_f = mr.getFile("photo_upload");
				System.out.println("������ ���� �޾ƿ�!  " + profile_f);
				if(profile_f != null) {
					profile_photo = profile_f.getName();
				}
				else {
					profile_edit_yn = 1;	//������ ���濩�� �Ǵ�. 1�̸� ���� ���Ѱ�.
				}
				
				// DB�� ��°��� profile_photo �̴�.
				
				String city = mr.getParameter("city");
				String country = mr.getParameter("country");
				String addr = city + " " + country;
				System.out.println("addr : " + addr);
				
				String gender = mr.getParameter("gender_yn");
				System.out.println("gender : " + gender);
				
				String mbti = mr.getParameter("mbti");
				System.out.println("mbti : " + mbti);
				
				String introduce = mr.getParameter("introduce");
				System.out.println("�ڱ�Ұ� : " + introduce);
				
				String video_yn = mr.getParameter("video_yn");
				System.out.println(video_yn);
				String pre_yn = mr.getParameter("pre_yn");
				System.out.println(pre_yn);
				
				String area = "";
				String[] area_arr = mr.getParameterValues("area_country");
				
				if( area_arr != null) {
					area = area_arr[0];
					for(int i = 1 ; i < area_arr.length ; i++) {
						area += ", " + area_arr[i]; }
					
					System.out.println(area);
				}
				else
					area_edit_yn = 1; //���� ���濩�� �Ǵ�. 1�̸� ���� ���Ѱ�.
				
				String subject;
				String[] subject_arr =  mr.getParameterValues("subject");
				String hashtag;
				String[] hashtag_arr = mr.getParameterValues("hashtag_field");
				System.out.println("�Ķ���� �� �޾ƿ���.");
				//��������, ����, �ؽ��±״� �������� ���� �� �ֱ� ������ ���� 1���� �ۿ��� �ְ� ����. ( ��ǥ ó���� ����)
				
				subject = subject_arr[0];
				hashtag = hashtag_arr[0];
				System.out.println("�迭�� �� �޾ƿ���.");
				
				
				for(int i = 1 ; i < subject_arr.length ; i++) {
					subject += ", " + subject_arr[i]; }
				
				for(int i = 1 ; i < hashtag_arr.length ; i++) {
					hashtag += ", " + hashtag_arr[i]; }
				
				
				System.out.println("������ ���� : " + profile_photo);
				Join_teacherVO vo = new Join_teacherVO();
				
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				vo.setPhone(phone);
				
				vo.setAddr(addr);
				vo.setGender(gender);
				vo.setSubject(subject);
				vo.setMbti(mbti);
				vo.setHashtag(hashtag);
				vo.setIntroduce(introduce);
				vo.setVideo_yn(video_yn);
				vo.setPre_yn(pre_yn);
				
				
				
				
				
				
				Join_teacherDAO.getInstance().update_user_total(vo);
				Join_teacherDAO.getInstance().update_user_teacher(vo);
				if(profile_edit_yn == 0)
					Join_teacherDAO.getInstance().update_teacher_profile(id, profile_photo);
				if(area_edit_yn == 0)
					Join_teacherDAO.getInstance().update_teacher_area(id, area);
				
				// DB�� ��°��� profile_photo �̴�.
				
				System.out.println("���� ��������");
				response.sendRedirect("mypage.do?id=" + id);
			}
	
}
