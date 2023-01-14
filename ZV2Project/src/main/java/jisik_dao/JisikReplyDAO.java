package jisik_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jisik_vo.JisikReplyVO;
import service.DBService;

public class JisikReplyDAO {
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static JisikReplyDAO single = null;

	public static JisikReplyDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new JisikReplyDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	//��� �߰��ϱ�!!!!
	public int insertReply(JisikReplyVO vo) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql =
			"insert into jisikreply values(seq_reply_idx.nextval,"
			+ "?,"
			+ "sysdate,"
			+ "?,"
			+ "0,"
			+ "0,"
			+ "seq_reply_idx.currVal,"
			+ "'a',"
			+ "0,"
			+ "?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, vo.getIdx());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());

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
	
	
	//view�� �����ۿ� �ش��ϴ� ��۵� �Ѹ���!!!
	public List<JisikReplyVO> selectList(int idx) {

		List<JisikReplyVO> list = new ArrayList<JisikReplyVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname, u.gubun"
				+ " from jisikreply j, user_total u"
				+ " where j.id = u.id and j.idx=" + idx
				+ " order by j.ref ASC, j.reorder ASC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikReplyVO vo = new JisikReplyVO();
				//���緹�ڵ尪=>Vo����
				vo.setReply_idx(rs.getInt("reply_idx"));
				vo.setIdx(rs.getInt("idx"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setContent(rs.getString("content"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setRef(rs.getInt("ref"));
				vo.setReorder(rs.getString("reorder"));
				vo.setReplynum(rs.getInt("replynum"));
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setGubun(rs.getString("gubun"));
				

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
	
	
	//���� ����� ���� ������!!
	public JisikReplyVO selectOne(int reply_idx) {

		JisikReplyVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from jisikreply where reply_idx="+reply_idx;

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new JisikReplyVO();
				//���緹�ڵ尪=>Vo����
				vo.setRef(rs.getInt("ref"));
				vo.setReorder(rs.getString("reorder"));
				vo.setIdx(rs.getInt("idx"));
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

		return vo;
	}
	
	
	//�迭�� �ִ� ã��!!
	public String selectOne(JisikReplyVO vo) {

		//argType vo = null;
		String max = "";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select nvl(max(reorder), 'empty') max from jisikreply where ref = ? and  reorder like ?||'_'";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setInt(1, vo.getRef());
			pstmt.setString(2, vo.getReorder());

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				max = rs.getString("max");

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

		return max;
	}
	
	
	//�θ����� replynum ������Ʈ!!!!
	public int updateReplyNum(int reply_idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisikreply set replynum = replynum + 1 where reply_idx="+reply_idx;

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���

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
	
	
	//���� �߰�
	public int insert_reReply(JisikReplyVO vo) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into jisikreply values(seq_reply_idx.nextval,"
				+ "?,"
				+ "sysdate,"
				+ "?,"
				+ "0,"
				+ "0,"
				+ "?,"
				+ "?,"
				+ "0,"
				+ "?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, vo.getIdx());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getRef());
			pstmt.setString(4, vo.getReorder());
			pstmt.setString(5, vo.getId());

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
	
	
	//�θ� nickname ��������!!
	public String selectBaseNickname(int myref, String baseOrder) {

//		String vo = null;
		String baseNickname = "";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select u.nickname "
				+ "from jisikreply j, user_total u "
				+ "where j.id = u.id and ref= ? and reorder =?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setInt(1, myref);
			pstmt.setString(2, baseOrder);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new String();
				//���緹�ڵ尪=>Vo����
				baseNickname = rs.getString("nickname");
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

		return baseNickname;
	}
	
	
	//��� ����!!!!!
	public int update_reply(int reply_idx, String content) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisikreply set content = ? , regidate = sysdate where reply_idx=?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, content);
			pstmt.setInt(2, reply_idx);

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
	
	//��� ����!!!!!
	public int delete_reply(int reply_idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from jisikreply where reply_idx = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, reply_idx);

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
	
	
	//����� ���ƿ�� ������Ʈ!!!!!
	public int update_replyLikehit(int reply_idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisikreply set likehit = likehit + 1"
				+ " where reply_idx = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, reply_idx);

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
	
	
	//����� ���ƿ� �� ��������
	public int selectLikehit(int reply_idx) {

		/*int vo = null;*/
		int likehit = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select likehit from jisikreply where reply_idx=?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setInt(1, reply_idx);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				/* vo = new int(); */
				//���緹�ڵ尪=>Vo����
				likehit = rs.getInt("likehit");
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

		return likehit;
	}
	
	
	//����� ���� idx��������!!
	public int selectForIdx_from_reply_idx(int reply_idx) {

//		int vo = null;
		int idx = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select idx from jisikreply where reply_idx="+reply_idx;

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				idx = rs.getInt("idx");

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

		return idx;
	}
	
	
	//���� ������ ���� ����� replynum update!!!!!!!1111
	public int update_down_reply_num(int ref, String baseOrder) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisikreply set replynum = replynum-1"
				+ " where ref=? and reorder =?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, ref);
			pstmt.setString(2, baseOrder);

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
	
	
	//id�� ���� �г� �޾ƿ���!!!!!!!
	public String selectGrade(String id) {

//		String vo = null;
		String grade = "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select grade from user_student where id=?";

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
//				vo = new String();
				//���緹�ڵ尪=>Vo����
				grade = rs.getString("grade");
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

		return grade;
	}

}
