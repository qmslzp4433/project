package search_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import search_vo.AreaVO;
import service.DBService;

public class AreaDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static AreaDAO single = null;

	public static AreaDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new AreaDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	public List<AreaVO> selectList(String city) {

		List<AreaVO> list = new ArrayList<AreaVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from DATA_AREA where CITY = ?";
		
		AreaVO v = new AreaVO();
		v.setCity(city);
		v.setCountry("��ü");
		list.add(v);

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, city);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AreaVO vo = new AreaVO();
				//���緹�ڵ尪=>Vo����
				vo.setCity(rs.getString("city"));
				vo.setCountry(rs.getString("country"));

				//ArrayList�߰�
				list.add(vo);
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

		return list;
	}
}
