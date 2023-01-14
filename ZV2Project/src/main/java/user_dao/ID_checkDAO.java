package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBService;
import user_vo.ID_checkVO;

public class ID_checkDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static ID_checkDAO single = null;

	public static ID_checkDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new ID_checkDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	public ID_checkVO selectOne(String id) {

		ID_checkVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM USER_TOTAL WHERE ID=?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, id);
			
			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ID_checkVO();
				vo.setId(rs.getString("id"));
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
