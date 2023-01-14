package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jisik_vo.LoginVO;
import service.DBService;

public class MypageDAO {
	// single-ton pattern:
	// ��ü1���������ؼ� ���������� ��������
	static MypageDAO single = null;

	public static MypageDAO getInstance() {
		// �������� �ʾ����� ����
		if (single == null)
			single = new MypageDAO();
		// ������ ��ü������ ��ȯ
		return single;
	}

	// ��ĳ�� ���
	public List<LoginVO> showmycash(String id) {

		List<LoginVO> showmycash = new ArrayList<LoginVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT point FROM user_total"
				+ " WHERE id = ? ";
		
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
			// 2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 3.����� ó����ü ������
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				LoginVO vo = new LoginVO();
				// ���緹�ڵ尪=>Vo����
				vo.setPoint(rs.getInt("point"));
				
				// ArrayList�߰�
				showmycash.add(vo);
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

		return showmycash;
	}

}
