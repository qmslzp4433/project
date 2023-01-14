package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import service.DBService;

public class Find_areaDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Find_areaDAO single = null;

	public static Find_areaDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Find_areaDAO();
		//������ ��ü������ ��ȯ
		return single;
	}

	@SuppressWarnings("unchecked")
	public JSONArray selectList(String city) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT country FROM DATA_AREA WHERE CITY=? ORDER BY COUNTRY ASC";
		JSONArray countrylist = new JSONArray();

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			// ó���� �����̸� ó��
			pstmt.setString(1, city);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("country",rs.getString("country"));
				countrylist.add(obj);
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

		return countrylist;
	}
}
