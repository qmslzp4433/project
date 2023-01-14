package jisik_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jisik_vo.JjimVO;
import service.DBService;

public class JjimDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static JjimDAO single = null;

	public static JjimDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new JjimDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	//jjim �߰�!!!
	public int insert_jjim(int idx, String id) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into jjim_jisik values(seq_jjim_num.nextval, ?, ?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, idx);
			pstmt.setString(2, id);

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
	
	
	//jjim ����!!!!!
	public int delete_jjim(int idx, String id) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from jjim_jisik where jjim_idx=? and jjim_id=?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, idx);
			pstmt.setString(2, id);

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
	
	
	//��ǥ�ø� ���� idx ��������!!!!!
	public List<JjimVO> select_JjimIdx(String id) {

		List<JjimVO> list = new ArrayList<JjimVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from jjim_jisik where jjim_id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JjimVO vo = new JjimVO();
				//���緹�ڵ尪=>Vo����
				vo.setJjim_id(rs.getString("jjim_id"));
				vo.setJjim_idx(rs.getInt("jjim_idx"));
				vo.setJjim_num(rs.getInt("jjim_num"));

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
