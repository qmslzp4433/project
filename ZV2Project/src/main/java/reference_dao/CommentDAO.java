package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;


public class CommentDAO {
	// single-ton pattern:
	// ��ü1���������ؼ� ���������� ��������
	static CommentDAO single = null;

	public static CommentDAO getInstance() {
		// �������� �ʾ����� ����
		if (single == null)
			single = new CommentDAO();
		// ������ ��ü������ ��ȯ
		return single;
	}	
		
				public int insert_comment(int ref_seq, String content, String id, int score) {
					int res = 0;
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = "INSERT INTO reference_comment VALUES(COMMENT_SEQ.nextval,?,?,?,?,sysdate)";
					

					try {
						//1.Connectionȹ��
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü ȹ��
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, content);
						pstmt.setString(3, id);
						pstmt.setInt(4, score);

						//3.pstmt parameter ä���

						//4.DB�� ����(res:ó�������)
						res = pstmt.executeUpdate();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {

						try {
							if (pstmt != null)
								pstmt.close();
							if (conn != null)
								conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return res;
				}
				
				

			

				
				
}
