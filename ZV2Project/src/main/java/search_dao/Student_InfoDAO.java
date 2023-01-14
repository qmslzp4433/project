package search_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import search_vo.Student_SearchVO;
import service.DBService;

public class Student_InfoDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Student_InfoDAO single = null;

	public static Student_InfoDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Student_InfoDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	public List<Student_SearchVO> info(String id) {

		List<Student_SearchVO> list = new ArrayList<Student_SearchVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Student_Search where id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Student_SearchVO vo = new Student_SearchVO();
				//���緹�ڵ尪=>Vo����
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setIntroduce(rs.getString("introduce"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setAddr(rs.getString("addr"));
				vo.setGrade(rs.getString("grade"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setMyn("���� " + rs.getString("myn"));
				vo.setHyn(rs.getString("hyn"));

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
