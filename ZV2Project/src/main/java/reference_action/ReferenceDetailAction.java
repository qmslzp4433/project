package reference_action;

import java.io.IOException;
import java.net.URLEncoder;
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
import reference_vo.CommentVO;
import reference_vo.ContentVO;


/**
 * Servlet implementation class UserListAction
 */
@WebServlet("/reference/reference_detail.do") //�ҷ����� url ����
public class ReferenceDetailAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
	
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
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
			
		
		
		
		//������ �������� �ҷ�����
		int ref_seq =Integer.parseInt( request.getParameter("ref_seq"));
		ContentDAO dao = ContentDAO.getInstance();
		List<ContentVO> list_content = dao.selectList_detail(ref_seq);
		request.setAttribute("list_content", list_content);
		
		//���ϸ�Ἥ �̸����� �ҷ�����
		String filename = ContentDAO.getInstance().filename(ref_seq);
		//filename = new String(filename.getBytes("8859_1"),"utf-8");
		
		//filename = URLEncoder.encode(filename,"utf-8");
		String savePath = "../upload";
	    String sFilePath = savePath + "\\" + filename;
	    System.out.println(sFilePath);
		request.setAttribute("filename", sFilePath);
		
		//��۰��� �ҷ�����
		int cnt = ContentDAO.getInstance().showComment_cnt(ref_seq);
		request.setAttribute("comment_cnt", cnt);
		//System.out.println(cnt);
		
		//���ϱ� (�α��� �ÿ��� ����)
		if(DAO!=null) {//�α��ν�
			String id = DAO.getId();
			int check = ContentDAO.getInstance().w_check(ref_seq, id);
			request.setAttribute("check", check);
		}
		
		
		
		//�ش� ��������ȣ ��� ��ü �ҷ�����(int ref_seq ��Ȱ��)
		ContentDAO commentdao = ContentDAO.getInstance();
		List<CommentVO> list_comment = commentdao.selectList_comment(ref_seq);
		request.setAttribute("list_comment", list_comment);
		
		
		//���ű�ɰ��� check������
				if(DAO!=null) {//�α��ν�
					String id = DAO.getId();
					
					//�����ߴ��� ���� ( 0:���� �̱��� / 1:���ſϷ���� )
					int check = ContentDAO.getInstance().b_check(ref_seq, id);
					request.setAttribute("checkbuy", check);
					
					  //int checkpoint = ContentDAO.getInstance().bp_check(ref_seq, id);
					  //request.setAttribute("checkpoint", checkpoint);
					 
				}
				        
		        
		//���߿� ������������ ������
		RequestDispatcher disp = 
		request.getRequestDispatcher("reference_detail.jsp?filename="+filename);
		disp.forward(request,response);
		
			
				
				
	}		
				
	
	
}
