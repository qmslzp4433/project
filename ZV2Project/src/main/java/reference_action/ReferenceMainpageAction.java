package reference_action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jisik_vo.LoginVO;
import reference_dao.ContentDAO;
import reference_dao.MypageDAO;
import reference_dao.Subject_bottomDAO;
import reference_dao.Subject_middleDAO;
import reference_vo.ContentVO;
import reference_vo.Subject_middleVO;

/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/reference/reference.do") //�ҷ����� url ����
public class ReferenceMainpageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
	
		//��ĳ����Ȳ �󸶳��Ҵ����ҷ�����
		HttpSession session = request.getSession();

		LoginVO DAO= (LoginVO)session.getAttribute("user");
		if(DAO!=null) {
		String id = DAO.getId();
		
		
		MypageDAO cash = MypageDAO.getInstance(); 
		List<LoginVO> showmycash = cash.showmycash(id); 
		request.setAttribute("showmycash", showmycash);
		//System.out.println(showmycash.get(0).getPoint());
		}
		
		
		
		
		
		
		   		  
		//dao���� ������ �߰��� ��� //ó������ ��ü ����Ʈ ����
		Subject_middleDAO dao = Subject_middleDAO.getInstance();
		List<Subject_middleVO> list_subject_middle = dao.selectList();
		
		//list�� request������ ���ε� ( �����صдٴ� ��)
		request.setAttribute("list_subject_middle", list_subject_middle);
			
				
		
		String subject_middle="";
		String subject_bottom="";
		String align="";
				
	
		subject_middle += request.getParameter("subject_middle");
		subject_bottom += request.getParameter("subject_bottom");
				//���ı�� 	
		align += request.getParameter("align");
				/* System.out.println(align); */
		
		
	
		
	
		//�ʱ�ȭ��
		if( subject_middle.equals("null") && subject_bottom.equals("null") && align.equals("null")) {
					//dao���� ������ ����Ʈ ���
		ContentDAO dao1 = ContentDAO.getInstance();
		List<ContentVO> list_content = dao1.selectList();
		request.setAttribute("list_content", list_content);
		
		
		} 
		
		
		 //System.out.println(subject_middle); 
		 //System.out.println(subject_bottom);
		 //System.out.println(align);
		
		//�ʱ�ȭ�� + ���ı��
		if(subject_middle.equals("null") && subject_bottom.equals("null") && !align.equals("null")) {	
		
			//System.out.println("aaa");
					//�ֽż�����
					if(align.equals("latest")) {
						ContentDAO dao1 = ContentDAO.getInstance();
						List<ContentVO> list_content = dao1.selectList_align_latest1();
						request.setAttribute("list_content", list_content);
						
					//�ٿ�ε�� ����
					} if(align.equals("download")) {
						ContentDAO dao1 = ContentDAO.getInstance();
						List<ContentVO> list_content = dao1.selectList_align_download1();
						request.setAttribute("list_content", list_content);
						
					//������ ����
					} if(align.equals("avgscore")) {
						ContentDAO dao1 = ContentDAO.getInstance();
						List<ContentVO> list_content = dao1.selectList_align_avgscore1();
						request.setAttribute("list_content", list_content);
						
					}
		}	
		
		
					//�߰��� ���� 
		if(!subject_middle.equals("null") && subject_bottom.equals("null") && align.equals("null")){
					//�ش� �߰��� �ش��ϴ� �Ұ��� ���
					Subject_middleDAO dao1 = Subject_middleDAO.getInstance();
					List<Subject_middleVO> subject_bottom_list = 
					dao1.selectList_subject_bottom(subject_middle);
					request.setAttribute("list_subject_bottom", subject_bottom_list); 
					
							
					//�ش� �߰��� �ش��ϴ� ����Ʈ ���
					List<ContentVO> list_content = 
					dao1.selectList_subject_middle_content(subject_middle);
					//list�� request������ ���ε� (�����صдٴ� ��)
					request.setAttribute("list_content", list_content);
					
					
		}		
					//�߰��� ���� + ����
		if(!subject_middle.equals("null") && subject_bottom.equals("null") && !align.equals("null")) {
			

					//�ش� �߰��� �ش��ϴ� �Ұ��� ���
				Subject_middleDAO dao1 = Subject_middleDAO.getInstance();
				List<Subject_middleVO> subject_bottom_list = 
				dao1.selectList_subject_bottom(subject_middle);
				request.setAttribute("list_subject_bottom", subject_bottom_list); 
						
				
				//�ֽż� ����
				if(align.equals("latest")) {
					ContentDAO dao2 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao2.selectList_align_latest2(subject_middle);
					request.setAttribute("list_content", list_content);
					
				//�ٿ�ε�� ����
				} if(align.equals("download")) {
					ContentDAO dao2 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao2.selectList_align_download2(subject_middle);
					request.setAttribute("list_content", list_content);
					
				//������ ����
				} if(align.equals("avgscore")) {
					ContentDAO dao2 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao2.selectList_align_avgscore2(subject_middle);
					request.setAttribute("list_content", list_content);
					
				}
			
				
		 }
			 
			
			//�Ұ��� ����Ʈ ���
			if(subject_middle.equals("null") && !subject_bottom.equals("null") && align.equals("null")) {
			
				
					//�ش� �Ұ���  �ش��ϴ� ����Ʈ ���
					Subject_bottomDAO dao2 = Subject_bottomDAO.getInstance();
					List<ContentVO> list_content = 
					dao2.selectList_subject_bottom_content(subject_bottom);
					request.setAttribute("list_content", list_content);
						
			}		
				//�Ұ��� ����Ʈ ��� + ����	
		
			if(subject_middle.equals("null") && !subject_bottom.equals("null") && !align.equals("null")) {
				
				
				//�ֽż� ����
				if(align.equals("latest")) {
					ContentDAO dao3 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao3.selectList_align_latest3(subject_bottom);
					request.setAttribute("list_content", list_content);
					
				//�ٿ�ε�� ����
				} if(align.equals("download")) {
					ContentDAO dao3 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao3.selectList_align_download3(subject_bottom);
					request.setAttribute("list_content", list_content);
					
				//������ ����
				} if(align.equals("avgscore")) {
					ContentDAO dao3 = ContentDAO.getInstance();
					List<ContentVO> list_content = dao3.selectList_align_avgscore3(subject_bottom);
					request.setAttribute("list_content", list_content);
					
				}
						
				
		}	
				
			
			//���߿� ������������ �;���
				RequestDispatcher disp = 
						request.getRequestDispatcher("reference.jsp");
				disp.forward(request,response);
		
			
				
				
				
	}		
				
	

}
