package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBService;
import user_vo.NICKNAME_checkVO;

public class NICKNAME_checkDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static NICKNAME_checkDAO single = null;

	public static NICKNAME_checkDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new NICKNAME_checkDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	public NICKNAME_checkVO selectOne(String nickname) {

		NICKNAME_checkVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT nickname FROM USER_TOTAL WHERE nickname=?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, nickname);
			
			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new NICKNAME_checkVO();
				vo.setNickname(rs.getString("nickname"));
				//���緹�ڵ尪=>Vo����

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
				e.printStackTrace();
			}
		}

		return vo;
	}

}
