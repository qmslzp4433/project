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
import reference_dao.MypageDAO;


@WebServlet("/reference/mycash.do")
public class MycashPageAction extends HttpServlet {
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
		
		
		
		//���߿� ������������ ������
				RequestDispatcher disp = 
				request.getRequestDispatcher("mycashpage.jsp");
				disp.forward(request,response);
				
					
	}

}
