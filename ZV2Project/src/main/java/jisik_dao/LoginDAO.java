package jisik_dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jisik_vo.LoginVO;
import service.DBService;

public class LoginDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static LoginDAO single = null;

	public static LoginDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new LoginDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	public LoginVO selectOne(String id) {

		LoginVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, pw, nickname FROM USER_TOTAL WHERE ID = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, id);
			
			//4.����� ó����ü ������
			rs = pstmt.executeQuery();
			
			//���̵� ��� �ƹ��͵� ��ȸ�� ���� �ʴ´ٸ� vo�� null���̴�.
			if (rs.next()) {
				vo = new LoginVO(); //���� �ִٸ� vo�޸𸮰� ������.
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw")); //���� �ִٸ� ������� pw�� ������ �� �ִ�.
				vo.setNickname(rs.getString("nickname"));
				//��� : ��ȸ�� �� �ƴٸ� vo�� null�� �ƴϸ鼭 id,pw,nickname �� 3���� ���´�.
				//��� : ��ȸ�� �ȵƴٸ� vo���� null �̴�.
			}
			

		} catch (Exception e) {
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

		return vo;
	}
	
	
	public LoginVO kakao_login(String email_addr) {

		LoginVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, pw, nickname FROM USER_TOTAL WHERE kakao_email = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, email_addr);
			
			//4.����� ó����ü ������
			rs = pstmt.executeQuery();
			
			//���̵� ��� �ƹ��͵� ��ȸ�� ���� �ʴ´ٸ� vo�� null���̴�.
			if (rs.next()) {
				vo = new LoginVO(); //���� �ִٸ� vo�޸𸮰� ������.
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw")); //���� �ִٸ� ������� pw�� ������ �� �ִ�.
				vo.setNickname(rs.getString("nickname"));
				//��� : ��ȸ�� �� �ƴٸ� vo�� null�� �ƴϸ鼭 id,pw,nickname �� 3���� ���´�.
				//��� : ��ȸ�� �ȵƴٸ� vo���� null �̴�.
			}
			

		} catch (Exception e) {
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

		return vo;
	}
	
	
	public int kakao_email_update(LoginVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE USER_TOTAL SET kakao_email = ? WHERE id= ? and pw = ? ";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getKakao_email());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getPw());
			
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
