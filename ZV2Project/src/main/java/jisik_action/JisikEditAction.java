package jisik_action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jisik_dao.JisikTableDAO;
import jisik_vo.JisikTableVO;
import ocr.OcrReturn;

/**
 * Servlet implementation class JisikEditAction
 */
@WebServlet("/jisik/jisik_edit.do")
public class JisikEditAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String web_path = "/upload/";
		
		ServletContext application = request.getServletContext();
		
		String path = application.getRealPath(web_path);
		System.out.println(path);
		
		int max_size = 1024 * 1024 * 100;
		
		MultipartRequest mr = new MultipartRequest(request,
												   path,
												   max_size,
												   "utf-8",
												   new DefaultFileRenamePolicy());
		
		
		int length = Integer.parseInt(mr.getParameter("length"));
		String content = mr.getParameter("content");
		String subject = mr.getParameter("subject");
		int idx = Integer.parseInt(mr.getParameter("idx"));
		
		
		String param = "file";

		
		String ocrResult = "";

		List<String> filenameArr = new ArrayList<String>();
		List<String> ocrArr = new ArrayList<String>();

//		System.out.println("���� ������1 : " + filenameArr.size());
		for(int i = 0; i < length; i++) {
			param = param + i;

			File f = mr.getFile(param);
			if( f != null ) {
				String filename = f.getName();
				filenameArr.add(filename);
				System.out.println(filename);
				OcrReturn ocr = new OcrReturn();
				
					try {
						ocrResult = ocr.ocrtest(path, filename);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				ocrArr.add(ocrResult);
				filename ="";
				param = "file";
				
			}//if
		

		}//for
		
		
		JisikTableVO vo = new JisikTableVO();
		vo.setIdx(idx);
		vo.setContent(content);
		vo.setSubject(subject);
		
		
		//�������� update
		int res = JisikTableDAO.getInstance().jisik_update(vo);
		
		//ocr������ ������ ���� �����ϱ�
		int del_res = JisikTableDAO.getInstance().ocr_delete(idx);
		
		
		
		if( filenameArr.size() > 0) {
			for(int i = 0; i < filenameArr.size(); i++) {
				JisikTableVO vo_ocr = new JisikTableVO();
				vo_ocr.setIdx(idx);
				vo_ocr.setFilename(filenameArr.get(i));
				vo_ocr.setOcr_data(ocrArr.get(i));
				
				JisikTableDAO.getInstance().insertOCR(vo_ocr);
				
			}
			
		}//if
		
		
		
	}//service

}
