package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import reference_vo.InsertVO;
import service.DBService;

public class CrudDAO {
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static CrudDAO single = null;
	
	public static CrudDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new CrudDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
		// �۾��� (insert)
		public int insert(InsertVO vo) {
			// TODO Auto-generated method stub
			int res = 0;

			Connection conn = null;
			PreparedStatement pstmt = null;

			String sql = "INSERT INTO reference_data"
					+ " VALUES (ref_seq.nextval, ?, ?, ?, ?, 0, ?, 0, ?, ?, ?, ? )";
			try {
				//1.Connectionȹ��
				conn = DBService.getInstance().getConnection();
				//2.���ó����ü ȹ��
				pstmt = conn.prepareStatement(sql);

				//3.pstmt parameter ä���
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getId());
				pstmt.setString(3, vo.getContent_main());
				pstmt.setString(4, vo.getRef_route());
				pstmt.setInt(5, vo.getContent_price());
				pstmt.setString(6, vo.getSubject_middle());
				pstmt.setString(7, vo.getSubject_bottom());
				pstmt.setString(8, vo.getContent());
				pstmt.setLong(9, vo.getFileSize());
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
		
		
		
		
		//delete
		public int delete(int ref_seq) {
			// TODO Auto-generated method stub
			int res = 0;

			Connection conn = null;
			PreparedStatement pstmt = null;

			String sql = "DELETE FROM reference_data WHERE ref_seq=?";

			try {
				//1.Connectionȹ��
				conn = DBService.getInstance().getConnection();
				//2.���ó����ü ȹ��
				pstmt = conn.prepareStatement(sql);
				//3.pstmt parameter ä���
				pstmt.setInt(1, ref_seq);
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
