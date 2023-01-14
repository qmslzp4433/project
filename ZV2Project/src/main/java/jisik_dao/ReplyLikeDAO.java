package jisik_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jisik_vo.ReplyLikeVO;
import service.DBService;

public class ReplyLikeDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static ReplyLikeDAO single = null;

	public static ReplyLikeDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new ReplyLikeDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	//��� ���ƿ� ���� �߰�!!!!!!
	public int insert_replyLike(int reply_idx, String id) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into like_reply values(seq_like_num.nextVal, ?, ?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, reply_idx);
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
	
	
	//���ƿ並 ���� ����Ʈ ����!!!!
	public int update_point(String id) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update user_total set point = point + 50 where id=?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, id);

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
	
	
	//���ƿ� Ȯ���� ���� ����Ʈ ��������
	public List<ReplyLikeVO> selectLikeList(String id) {

		List<ReplyLikeVO> list = new ArrayList<ReplyLikeVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from like_reply where like_id=?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReplyLikeVO vo = new ReplyLikeVO();
				//���緹�ڵ尪=>Vo����
				vo.setLike_id(rs.getString("like_id"));
				vo.setLike_idx(rs.getInt("like_idx"));
				vo.setLike_num(rs.getInt("like_num"));

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
