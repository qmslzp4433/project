package user_action;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/email_auth.do")
public class Email_authAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static String user = "best__teacher@naver.com"; //�߽����� �̸��� ���̵� �Է�
	final static String password = "Asdf!234";         //�߽��� �̸����� �н����带 �Է�
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String resultStr = "";
		
		int auth_num = 0;
		String id = request.getParameter("id");

		
		Random rd = new Random();
		rd.setSeed(System.currentTimeMillis());
		
		auth_num = 0;
		while(auth_num < 99999 || auth_num > 1000000) {
			auth_num = rd.nextInt(900000)+100000;
		}
		
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.naver.com"); 
		prop.put("mail.smtp.port", 465); 
		prop.put("mail.smtp.auth", "true"); 
		prop.put("mail.smtp.ssl.enable", "true"); 
		prop.put("mail.smtp.ssl.trust", "smtp.naver.com");
		
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication() {
	        	   return new PasswordAuthentication(user, password);
	           }
	    });
		
		try {
	           MimeMessage message = new MimeMessage(session);
	           message.setFrom(new InternetAddress(user));
	            //�����ڸ����ּ�
	           message.addRecipient(Message.RecipientType.TO, new InternetAddress(id)); 
	            // Subject
	           message.setSubject("�ְ����� �̸��� ������ȣ"); //���� ������ �Է�
	            // Text
	           message.setText("�ְ������� �����ϱ� ���ؼ� �Ʒ��� ������ȣ�� Ȩ������ �Է¶��� �Է����ֽʽÿ�.\n\n\n ������ȣ : " + auth_num);    //���� ������ �Է�
	            // send the message
	           Transport.send(message); ////����
	           System.out.println("message sent successfully...");
	           resultStr = "[{'param' : '" + auth_num + "'}]";
            
        } catch (AddressException e) {
            e.printStackTrace();
            resultStr = "[{'param' : 'send_fail'}]";
            return;
            
        } catch (MessagingException e) {
            e.printStackTrace();
            resultStr = "[{'param' : 'send_fail'}]";
            return;
        }
		
		
//		Email_authVO vo = new Email_authVO();
//		vo.setId(id);
//		vo.setAuth_num(auth_num);
//		vo.setDatetime(datetime);
//		
//		int res = Email_authDAO.getInstance().insert(vo);
		
//		if(res != 1) {
//			resultStr = "[{'param' : '" + auth_num + "'}]";
//		}
		
		System.out.println(resultStr);
		//response.sendRedirect("join_student.jsp");
		response.getWriter().print(resultStr);
		
	}
}

