package search_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBService;

public class Talk_CheckDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Talk_CheckDAO single = null;

	public static Talk_CheckDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Talk_CheckDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	public int talk_check(String t_id, String s_id) {

		int check = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from TALK_LIST where teacher_id = ? and student_id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, t_id);
			pstmt.setString(2, s_id);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = 1;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return check;
	}
	
	public int insert_talk(String t_id, String s_id) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into TALK_LIST(talk_seq, teacher_id, student_id) values(TALK_LIST_SEQ.nextval, ?, ?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, t_id);
			pstmt.setString(2, s_id);

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
	
	public int talk_seq(String t_id, String s_id) {

		int seq = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select TALK_SEQ from TALK_LIST where teacher_id = ? and student_id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, t_id);
			pstmt.setString(2, s_id);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getInt("TALK_SEQ");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return seq;
	}
	

}
