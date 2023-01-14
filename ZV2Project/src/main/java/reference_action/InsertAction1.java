package reference_action;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import reference_dao.CrudDAO;
import reference_vo.InsertVO;

/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/reference/insert1.do") //�ҷ����� url ����
public class InsertAction1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
		
		
	  //�ϴ� �� �޾ƿ���
	    // request.getRealPath("�����") �� ���� ������ ������ ���� ��θ� ���ؿ´�.
	    // �ü�� �� ������Ʈ�� ��ġ�� ȯ�濡 ���� ��ΰ� �ٸ��� ������ �Ʒ�ó�� ���ؿ��°� ����
	    String uploadPath = request.getRealPath("/upload");
	    //out.println("������ : " + uploadPath + "<br/>");
	     
	    int maxSize = 1024 * 1024 * 300; // �ѹ��� �ø� �� �ִ� ���� �뷮 : 300M�� ����
	     
	    String id = "";
	    String title = "";
	    int content_price=0;
	    String content_main = "";
	    String subject_middle ="";
	    String subject_bottom ="";
	     
	    String ref_route = ""; // �ߺ�ó���� �̸� (���� filename1�̾���)
	    String content = ""; // 
	    long fileSize = 0; // ���� ������
	 
	     
	   
	     
	   
	        // request,����������,�뷮,���ڵ�Ÿ��,�ߺ����ϸ� ���� �⺻ ��å
	    	MultipartRequest multi = new MultipartRequest(request,uploadPath,maxSize,"utf-8",new DefaultFileRenamePolicy());
	         
	        // form���� input name="name" �� �༮ value�� ������
	        id = multi.getParameter("id");
	        // name="title" �� �༮ value�� ������
	        title = multi.getParameter("title");
	        // name="price" �� �༮ value�� ������
	        content_price = Integer.parseInt(multi.getParameter("content_price"));
	        // name="content" �� �༮ value�� ������
	        content_main = multi.getParameter("content_main");
	        
	        //�ٹٲ�ó��
	        content_main = content_main.replaceAll("\n", "<br/>");
	        
	     // name="subject_middle" �� �༮ value�� ������
	        subject_middle = multi.getParameter("subject_middle");
	     // name="subject_bottom" �� �༮ value�� ������
	        subject_bottom = multi.getParameter("subject_bottom");
	         
	        
	        //�����
				File sumf = multi.getFile("ref_route");
				if(sumf != null) {
					ref_route = sumf.getName();
				}
	        
			//��������
				File contentf = multi.getFile("content");
				if(contentf != null) {
					content = contentf.getName();
					fileSize = contentf.length();
				}
				
				InsertVO vo= new InsertVO();
			
				vo.setTitle(title);
				vo.setId(id);
				
				//�ٹٲ�ó��

				vo.setContent_main(content_main);
				vo.setRef_route(ref_route);
				vo.setContent_price(content_price);
				vo.setSubject_middle(subject_middle);
				vo.setSubject_bottom(subject_bottom);
				vo.setContent(content);
				vo.setFileSize(fileSize);
				
	  			//dbó��
	  			CrudDAO.getInstance().insert(vo);

	  		
	  		//�ٽ� ������������ �;���
				RequestDispatcher disp = 
						request.getRequestDispatcher("reference.do");
				disp.forward(request,response);
		
		
		
		
	}
		
		
		
	

}