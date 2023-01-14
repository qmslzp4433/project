package reference_action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import reference_dao.ContentDAO;

/**
 * Servlet implementation class DownloadAction
 */
@WebServlet("/reference/download.do")
public class DownloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		 // ���ϴٿ�ε�
		
		 // a�±��� href�� fileDown1.jsp?file_name="<%=fileName1 �� ���� ������ 
	    // �ߺ� ���� ó���� ���ϸ� ���� �����´�.
	    String fileName = request.getParameter("content");
	     	
	    // ���ε��� ������ ��ġ�� ���ε� ������ �̸��� �˾ƾ� �Ѵ�.
	    String savePath = "upload"; 
	    // ���� ������ ������̰� ������ ������ ��¥ ��θ� ���ؿ;��Ѵ�.
	    String sDownPath = request.getRealPath(savePath);
	    
	    System.out.println("�ٿ�ε� ���� ���� ��� ��ġ : " + sDownPath);
	    //System.out.println("fileName1 : " + fileName);
	     
	    // ����Ǿ� �ִ� �������/����� ���ϸ� ���� Ǯ path�� ������ش�.
	        // �ڹٿ����� \�� ǥ���ϱ� ���ؼ��� \�� �ѹ� �� �ٿ��ֱ� ������ \\�� ���ش�.
	    String sFilePath = sDownPath + "\\" + fileName; // ex)c:\\uploadPath\\image.jpg
	    //System.out.println("sFilePath : " + sFilePath);
	    // Ǯ path�� ���Ѱ� ���� ��ü�� �νĽ�Ų��.
	    File outputFile = new File(sFilePath);
	    // ����� ������ �о�� ������ ���۸� �ӽ÷� ����� ������ �뷮�� ������ �ѹ��� ���ε��� �� �ִ� ����ũ��� �����Ѵ�.
	    byte[] temp = new byte[1024*1024*300]; // 300M
	     
	    // ������ �о�;� ������ inputStream�� ����.(Ǯ�н��� ������ ���� ��ü�� �̿��� input��Ʈ���� �����Ѵ�.)
	    FileInputStream in = new FileInputStream(outputFile);
	     
	    // ���� Ȯ�� : �о�� ����� ������ ���� -> ������ ������ �� Ÿ���� �����ؾ� �Ѵ�.
	    String sMimeType = getServletContext().getMimeType(sFilePath);
	     
	    //System.out.println("���� : " + sMimeType);
	     
	    // �������� ���� ���� ����ó��
	    if ( sMimeType == null ){
	        // �������� ǥ��
	        sMimeType = "application.octec-stream"; // �Ϸõ� 8bit ��Ʈ�� ����
	        // ������ �˷����� ���� ���Ͽ� ���� �б� ���� ����
	    }
	     
	    // ���� �ٿ�ε� ����
	    // ������ ������ �ش�.
	    response.setContentType(sMimeType); // ������ �������� text/html;charset=utf-8��
	    // ���� mime Ÿ������ ������ �ش�.
	     
	    // ���ε� ������ ������ ���� �� �����Ƿ� ���ڵ��� ���ش�.
	    String sEncoding = new String(fileName.getBytes("euc-kr"),"8859_1");
	    //String B = "utf-8";
	    //String sEncoding = URLEncoder.encode(A,B);
	     
	    // ��Ÿ ������ ����� �÷��� �Ѵ�.
	    // ��Ÿ ������ ���� ���������� �ٿ�ε� �� ȭ�鿡 ��½��� �ش�.
	    String AA = "Content-Disposition";
	    String BB = "attachment;filename="+sEncoding;
	    response.setHeader(AA,BB);
	     
	    // �������� ����
	    ServletOutputStream out2 = response.getOutputStream();
	     
	    int numRead = 0;
	     
	    // ����Ʈ �迭 temp�� 0������ numRead������ �������� ���
	        // ������ ��ġ�� ���� ����� inputStream���� �е� ��(-1) ������ while�� ����.
	    while((numRead = in.read(temp,0,temp.length)) != -1){ // temp �迭�� �о�ðǵ� 0��° �ε������� �ѹ��� �ִ� temp.length ��ŭ �о�´�.
	        // �о�ð� ���̻� ������ -1�� �����ϸ鼭 while���� ��������
	         
	        // �������� ��� : �ٴ� header ������ attachment�� �س��������� �ٿ�ε尡 �ȴ�.
	        out2.write(temp,0,numRead); // temp�迭�� �ִ� �������� 0��°���� �ִ� numRead��ŭ ����Ѵ�.
	    }
	    // �ڿ� ����
	    out2.flush();
	    out2.close();
	    in.close();
		
	    
	  //download_cnt ���� ������ ( �ٿ�ε�� ���� ���� 1�� �߰��Ǿ� ����)	
	    //System.out.println(fileName);
		ContentDAO.getInstance().update_download_cnt(fileName);
		
	 
        
	//���߿� ������������ ������
	//RequestDispatcher disp = 
	//request.getRequestDispatcher("reference_detail.jsp");
	//disp.forward(request,response);
	
		
		
		
		
		
		
		
	}

}
