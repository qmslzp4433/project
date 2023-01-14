package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.DBService;
import user_vo.JoinVO;

public class JoinDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static JoinDAO single = null;

	public static JoinDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new JoinDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
//ȸ������ �� total ���̺� insert=============================================================
	public int insert_user_total(JoinVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO USER_TOTAL VALUES (?, ?, ?, ?, ?, ?, '�л�', 0,'')";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getNickname());
			pstmt.setString(5, vo.getPhone());
			pstmt.setString(6, vo.getEmailaddr());
			
			//4.DB�� ����(res:ó�������)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
//ȸ������ �� �л� ���̺� insert ==============================================================
	public int insert_user_student(JoinVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO USER_STUDENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, '����')";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getHope_yn());
			pstmt.setString(3, vo.getGrade());
			pstmt.setString(4, vo.getAddr());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getHope_subject());
			pstmt.setString(7, vo.getHope_time());
			pstmt.setString(8, vo.getHashtag());
			pstmt.setString(9, vo.getIntroduce());
			//4.DB�� ����(res:ó�������)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
//�Ʒ����ʹ� �л� ���� ���������� ���Ǵ� class
	public int update_user_total(JoinVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE USER_TOTAL SET PW = ?, NAME = ?, PHONE = ? WHERE ID= ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getId());
			
			//4.DB�� ����(res:ó�������)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
//ȸ������ �� �л� ���̺� insert ==============================================================
	public int update_user_student(JoinVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE USER_STUDENT SET GRADE = ?, ADDR = ?, GENDER = ?, HOPE_SUBJECT = ?, HOPE_TIME = ?, HASHTAG = ?, INTRODUCE = ? WHERE ID = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getGrade());
			pstmt.setString(2, vo.getAddr());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getHope_subject());
			pstmt.setString(5, vo.getHope_time());
			pstmt.setString(6, vo.getHashtag());
			pstmt.setString(7, vo.getIntroduce());
			pstmt.setString(8, vo.getId());
			//4.DB�� ����(res:ó�������)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
}
