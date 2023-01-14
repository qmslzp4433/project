package jisik_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jisik_vo.JisikTableVO;
import service.DBService;

public class JisikTableDAO {
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static JisikTableDAO single = null;

	public static JisikTableDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new JisikTableDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	//�������̺� ��ü ��ȸ!!
	public List<JisikTableVO> selectList() {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select u.nickname, j.*"
				+ " from user_total u, jisiktable j"
				+ " where u.id = j.id order by j.idx DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));
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
	
	
	
	
	//�������̺� �߰�!!!
	public int insert(JisikTableVO vo) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into jisiktable values(seq_jisik_idx.nextVal, ?, sysdate, ?, 0, 0, 0, 0, 0, ?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getSubject());
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
	
	
	//ocr�����͸� ���� idx ��������!!!!
	public int selectOne() {

		int idx = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select max(idx) max "
				+ "from jisiktable";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				idx = rs.getInt("max");

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
	
	
	
	
	
	
	//ocr������ �߰�!!!!
	public int insertOCR(JisikTableVO vo) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into ocr values(seq_ocr_idx.nextVal,?,?,?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, vo.getIdx());
			pstmt.setString(2, vo.getFilename());
			pstmt.setString(3, vo.getOcr_data());

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
	
	
	//view�� �ش��ϴ� ����Ʈ �̱�!!!!!
	public JisikTableVO selectOne(int idx) {

		JisikTableVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname, u.gubun "
				+ "from jisiktable j, user_total u "
				+ "where j.id = u.id and j.idx = "+idx;

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setContent(rs.getString("content"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setId(rs.getString("id"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setGubun(rs.getString("gubun"));
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
	
	
	//�並 ���� Ŭ���� ��ȸ�� ������Ʈ!!!!!!
	public int updateReadhit(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set readhit = readhit + 1 where idx=" + idx;

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
	
	
	//��� �޷����� ������ ��� ���� ������Ʈ!!!!!
	public int updateReplyInfo(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set reply_info = reply_info + 1 where idx="+idx;

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
	
	
	
	//�˻���� �˻��� ����Ʈ �Ѹ���!!!!
	public List<JisikTableVO> searchTextselectList( String search_content ) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id and (j.content like '%'||?||'%' or o.ocr_data like '%'||?||'%')"
				+ " order by j.idx DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, search_content);
			pstmt.setString(2, search_content);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//�̹��� �˻����� ���� ����Ʈ �Ѹ���!!!
	public List<JisikTableVO> searchImgselectList( String part1,String part2,String part3,String part4 ) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id"
				+ " and (o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%'"
				+ " or o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%')";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, part1);
			pstmt.setString(2, part2);
			pstmt.setString(3, part3);
			pstmt.setString(4, part4);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));
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
	
	
	//������ ����!!!!!
	public int jisik_update(JisikTableVO vo) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set content = ?, subject = ?, regidate = sysdate"
				+ " where idx=?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getContent());
			pstmt.setString(2, vo.getSubject());
			pstmt.setInt(3, vo.getIdx());
			

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
	
	
	//������ ������ ���� ocr������ ����!!!!!(������ ������ ��)
	public int ocr_delete(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from ocr where idx ="+idx;

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
	
	
	//������ ����!!!!
	public int jisik_delete(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from jisiktable where idx="+idx;

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
	
	
	
	//���ƿ� ���� +1 ������Ʈ!!!
	public int plusLikehit(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set likehit = likehit + 1 where idx = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, idx);

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
	
	
	
	//���ƿ� ���� -1������Ʈ!!!
	public int minusLikehit(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set likehit = likehit - 1 where idx = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, idx);

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
	
	
	
	
	//���ƿ� �� ajax�� ���� select!!!!!!!
	public int selectLikehit(int idx) {

//		int vo = null;
		int likehit = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select likehit from jisiktable where idx = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setInt(1, idx);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new int();
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
	
	
	//���� ��� �̾ƿ���!!!!!!!
	public List<JisikTableVO> searchJjimselectList(String id) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname"
				+ " from jisiktable j, user_total u, jjim_jisik jjim"
				+ " where j.id = u.id and j.idx = jjim.jjim_idx and jjim.jjim_id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//���� ���� ����!!!!!!!!
	public List<JisikTableVO> searchMyList( String id ) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id = u.id and j.id = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//���� �˻�!!!!!
	public List<JisikTableVO> searchSubejctselectList(String subject) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id = u.id and j.subject = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subject);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//������ó�� �� ������ �Ѹ���!!!!!
	public List<JisikTableVO> pageSelectList(int start, int end) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id=u.id) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//�˻��� ���� ������ ����!!!!!!!
	public int row_Num() {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id=u.id) ji )";

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
				row_num = rs.getInt("row");
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

		return row_num;
	}
	
	
	
	//������ ó���� �� �̹��� �˻�!!!!
	public List<JisikTableVO> page_searchImgselectList( String part1 , String part2, String part3, String part4,
														int start, int end) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id\r\n"
				+ " and (o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%'"
				+ " or o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%')) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, part1);
			pstmt.setString(2, part2);
			pstmt.setString(3, part3);
			pstmt.setString(4, part4);
			pstmt.setInt(5, start);
			pstmt.setInt(6, end);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//�̹��� �˻� ������ ó���� ���� �� ����!!!!!!
	public int row_Num_Img( String part1, String part2, String part3, String part4 ) {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id\r\n"
				+ " and (o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%'"
				+ " or o.ocr_data like '%'||?||'%' or o.ocr_data like '%'||?||'%')) ji )";
		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, part1);
			pstmt.setString(2, part2);
			pstmt.setString(3, part3);
			pstmt.setString(4, part4);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				row_num = rs.getInt("row");
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

		return row_num;
	}
	
	
	//������ ó���� �� text�˻� �Ѹ���!!!!!!!
	public List<JisikTableVO> page_searchTextselectList(String search_content, int start, int end) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id and (j.content like '%'||?||'%' or o.ocr_data like '%'||?||'%')) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search_content);
			pstmt.setString(2, search_content);
			pstmt.setInt(3, start);
			pstmt.setInt(4, end);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	//����¡ ó���� ���� Text�˻� �� ���� �Ѹ���!!!!
	public int row_Num_Text( String search_content ) {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, ocr o, user_total u"
				+ " where j.idx = o.idx and j.id = u.id"
				+ " and (j.content like '%'||?||'%' or o.ocr_data like '%'||?||'%')) ji )";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, search_content);
			pstmt.setString(2, search_content);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				row_num = rs.getInt("row");

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

		return row_num;
	}
	
	
	//������ ó���� �� �� ��� ��������!!!!!!!!
	public List<JisikTableVO> page_searchJjimselectList(String id, int start, int end) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u, jjim_jisik jjim"
				+ " where j.id = u.id and j.idx = jjim.jjim_idx and jjim.jjim_id = ?) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//����¡ ó���� ���� �� ���� �����!!!!
	public int row_Num_jjim(String id) {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u, jjim_jisik jjim"
				+ " where j.id = u.id and j.idx = jjim.jjim_idx and jjim.jjim_id = ?) ji )";

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
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				row_num = rs.getInt("row");

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

		return row_num;
	}
	
	
	//����¡ ó���� �� ���� ���� �Ѹ���!!!!!!!!
	public List<JisikTableVO> page_searchMyList(String id, int start, int end) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id = u.id and j.id = ?) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	//����¡ ó���� �� ���� ���� ���� ������!!!!
	public int row_Num_MyList(String id) {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id = u.id and j.id = ?) ji )";

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
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				row_num = rs.getInt("row");

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

		return row_num;
	}
	
	
	
	//����¡ ó���� �� ���� �˻� ����Ʈ �Ѹ���!!!!!!
	public List<JisikTableVO> page_searchSubejctselectList( String subject, int start, int end ) {

		List<JisikTableVO> list = new ArrayList<JisikTableVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from"
				+ " (select Rank() over( order by regidate DESC ) no, ji.*"
				+ " from (select j.*, u.nickname"
				+ " from jisiktable j, user_total u"
				+ " where j.id = u.id and j.subject = ?) ji )"
				+ " where no between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subject);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JisikTableVO vo = new JisikTableVO();
				//���緹�ڵ尪=>Vo����
				vo.setIdx(rs.getInt("idx"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadhit(rs.getInt("readhit"));
				vo.setLikehit(rs.getInt("likehit"));
				vo.setDel_info(rs.getInt("del_info"));
				vo.setSuccess_info(rs.getInt("success_info"));
				vo.setReply_info(rs.getInt("reply_info"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegidate(rs.getString("regidate"));
				vo.setId(rs.getString("id"));

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
	
	
	
	//����¡ ó���� ���� �˻� �� ������!!!!
	public int row_Num_Subject(String subject) {

//		int vo = null;
		int row_num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) \"row\" from"
				+" (select Rank() over( order by regidate DESC ) no, ji.*"
				+ "	from (select j.*, u.nickname"
				+ "	from jisiktable j, user_total u"
				+ "	where j.id = u.id and j.subject = ?) ji )";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����
			pstmt.setString(1, subject);

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
//				vo = new int();
				//���緹�ڵ尪=>Vo����
				row_num = rs.getInt("row");

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

		return row_num;
	}
	
	
	//��� ������ ������ ��� ���� ������Ʈ
	public int deleteReplyInfo(int idx) {
		// TODO Auto-generated method stub
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update jisiktable set reply_info = reply_info-1 where idx="+idx;

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

}
