package jisik_action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Servlet implementation class LogoutAction
 */
@WebServlet("/logout.do")
public class LogoutAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		/* String path = URLEncoder.encode(request.getParameter("path"),"utf-8"); */
				
		String path = request.getParameter("path");
		int han_index = path.indexOf('=');
		System.out.println("han_idx:" + han_index);
		String real_path = "";
		
		if( han_index != -1 ) {
			String han = path.substring(han_index+1);
			System.out.println("�Ķ����:"+han);
			String sub_path = path.substring(0,han_index+1);
			System.out.println("�ּ�:" + sub_path);
			
			real_path = sub_path + URLEncoder.encode(han,"utf-8");
					
			System.out.println("�α׾ƿ�:"+path);
			System.out.println("��¥�α׾ƿ�:"+real_path);
			
		}else {
			real_path = path;
		}
		
		session.removeAttribute("user");
		response.setCharacterEncoding("utf-8");
		response.sendRedirect(real_path);
	}

}
