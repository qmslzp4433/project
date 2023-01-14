package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import user_vo.Dialogue_listVO;

public class Dialogue_listDAO {

	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Dialogue_listDAO single = null;

	public static Dialogue_listDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Dialogue_listDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	public Dialogue_listVO selectOne(String id) {

		Dialogue_listVO selected_id = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT gubun FROM USER_TOTAL WHERE ID=?";

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
				selected_id = new Dialogue_listVO();
				selected_id.setGubun(rs.getString("gubun"));
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

		return selected_id;
	}
	
	//�α��� �� ���̵� '�л�' �ΰ�� ������ ���� ���̺��� �޽��� ����� ������ �޾ƿ´�.
	public List<Dialogue_listVO> teacher_dial_info(String id) {
		List<Dialogue_listVO> list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.nickname, C.profile, C.addr FROM TALK_LIST A, USER_TOTAL B, USER_TEACHER C WHERE A.student_id = ? and B.id = A.teacher_id and c.id = A.teacher_id ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo = new Dialogue_listVO();
				//���緹�ڵ尪=>Vo����
				vo.setTalk_seq(rs.getInt("talk_seq"));
				vo.setTeacher_id(rs.getString("teacher_id"));
				vo.setStudent_id(rs.getString("student_id"));
				vo.setContent(rs.getString("content"));
				vo.setLast_send_date(rs.getString("last_send_date"));
				vo.setLast_send_time(rs.getString("last_send_time"));
				vo.setRead_info(rs.getInt("read_info"));
				vo.setLast_send_id(rs.getString("last_send_id"));
				vo.setProfile(rs.getString("profile"));
				vo.setAddr(rs.getString("addr"));
				vo.setNickname(rs.getString("nickname"));
				
				//ArrayList�߰�
				list.add(vo);
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
		return list;
	}
	
	
	public List<Dialogue_listVO> wish_teacher_dial_info(String id) {
		List<Dialogue_listVO> wish_list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT A.*, B.nickname, C.profile, C.addr, C.subject FROM TALK_LIST A, USER_TOTAL B, USER_TEACHER C, TEACHER_WISHLIST D WHERE A.student_id = ? and B.id = A.teacher_id and c.id = A.teacher_id and D.TEACHER_ID = A.TEACHER_ID ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo_w = new Dialogue_listVO();
				//���緹�ڵ尪=>Vo����
				vo_w.setTalk_seq(rs.getInt("talk_seq"));
				vo_w.setTeacher_id(rs.getString("teacher_id"));
				vo_w.setStudent_id(rs.getString("student_id"));
				vo_w.setContent(rs.getString("content"));
				vo_w.setLast_send_date(rs.getString("last_send_date"));
				vo_w.setLast_send_time(rs.getString("last_send_time"));
				vo_w.setRead_info(rs.getInt("read_info"));
				vo_w.setLast_send_id(rs.getString("last_send_id"));
				vo_w.setProfile(rs.getString("profile"));
				vo_w.setAddr(rs.getString("addr"));
				vo_w.setNickname(rs.getString("nickname"));
				vo_w.setSubject(rs.getString("subject"));
				
				//ArrayList�߰�
				wish_list.add(vo_w);
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
		return wish_list;
	}
	
	//�α��� �� ���̵� '������' �ΰ�� �л� ���� ���̺��� �޽��� ����� ������ �޾ƿ´�.
	public List<Dialogue_listVO> student_dial_info(String id) {
		List<Dialogue_listVO> list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.nickname, C.addr FROM TALK_LIST A, USER_TOTAL B, USER_STUDENT C WHERE A.teacher_id = ? and B.id = A.student_id and c.id = A.student_id ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo = new Dialogue_listVO();
				//���緹�ڵ尪=>Vo����
				vo.setTalk_seq(rs.getInt("talk_seq"));
				vo.setTeacher_id(rs.getString("teacher_id"));
				vo.setStudent_id(rs.getString("student_id"));
				vo.setContent(rs.getString("content"));
				vo.setLast_send_date(rs.getString("last_send_date"));
				vo.setLast_send_time(rs.getString("last_send_time"));
				vo.setRead_info(rs.getInt("read_info"));
				vo.setLast_send_id(rs.getString("last_send_id"));
				vo.setProfile("img/account.png");
				vo.setAddr(rs.getString("addr"));
				vo.setNickname(rs.getString("nickname"));
				
				//ArrayList�߰�
				list.add(vo);
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
		return list;
	}
	

	public int msg_delete(int talk_seq) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "{call TALK_DELETE_PROC(?) }";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setInt(1, talk_seq);

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
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
	
	
//������������ ���� ���� ���� �޾ƿ���
	public List<Dialogue_listVO> wish_teacher_info(String id) {
		List<Dialogue_listVO> wish_teacher_list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT B.id, B.nickname, C.profile, C.addr, C.subject FROM USER_TOTAL B, USER_TEACHER C, TEACHER_WISHLIST D WHERE D.student_id = ? and  D.TEACHER_ID = B.ID AND D.TEACHER_ID = C.ID";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo_w = new Dialogue_listVO();
				//���緹�ڵ尪=>Vo����
				vo_w.setId(rs.getString("id"));
				vo_w.setProfile(rs.getString("profile"));
				vo_w.setAddr(rs.getString("addr"));
				vo_w.setNickname(rs.getString("nickname"));
				vo_w.setSubject(rs.getString("subject"));
				
				//ArrayList�߰�
				wish_teacher_list.add(vo_w);
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
		return wish_teacher_list;
	}
}
