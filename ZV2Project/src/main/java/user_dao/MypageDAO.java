package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import user_vo.Mypage_studentVO;
import user_vo.Mypage_teacherVO;

public class MypageDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static MypageDAO single = null;

	public static MypageDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new MypageDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	public List<Mypage_teacherVO> mypage_teacher_info(String id) {

		List<Mypage_teacherVO> teacher_list = new ArrayList<Mypage_teacherVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.* FROM USER_TOTAL A, USER_TEACHER B WHERE A.ID = ? AND B.ID = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, id);
			
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Mypage_teacherVO vo = new Mypage_teacherVO();
				//���緹�ڵ尪=>Vo����
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmailaddr(rs.getString("emailaddr"));
				vo.setGubun(rs.getString("gubun"));
				vo.setPoint(rs.getInt("point"));
				
				vo.setProfile(rs.getString("profile"));
				vo.setIdnumber(rs.getString("idnumber"));
				vo.setAddr(rs.getString("addr"));
				vo.setArea(rs.getString("area"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setMbti(rs.getString("mbti"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setIntroduce(rs.getString("introduce"));
				vo.setVideo_yn(rs.getString("video_yn"));
				vo.setPre_yn(rs.getString("pre_yn"));
				vo.setBadge1(rs.getString("badge1"));
				vo.setBadge2(rs.getString("badge2"));
				vo.setBadge3(rs.getString("badge3"));
				
				
				//ArrayList�߰�
				teacher_list.add(vo);
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
		return teacher_list;
	}

	
	
	public List<Mypage_teacherVO> teacher_video_select(String id) {

		List<Mypage_teacherVO> video_list = new ArrayList<Mypage_teacherVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM TEACHER_VIDEO WHERE ID = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Mypage_teacherVO vo = new Mypage_teacherVO();
				//���緹�ڵ尪=>Vo����
				vo.setVideo_seq(rs.getInt("video_seq"));
				vo.setTitle(rs.getString("title"));
				
				//ArrayList�߰�
				video_list.add(vo);
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
		return video_list;
	}
	
	
	
	public List<Mypage_studentVO> mypage_student_info(String login_id) {
		List<Mypage_studentVO> student_list = new ArrayList<Mypage_studentVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.* FROM USER_TOTAL A, USER_STUDENT B WHERE A.ID = ? AND B.ID = ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, login_id);
			pstmt.setString(2, login_id);
			
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Mypage_studentVO vo = new Mypage_studentVO();
				//���緹�ڵ尪=>Vo����
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmailaddr(rs.getString("emailaddr"));
				vo.setGubun(rs.getString("gubun"));
				vo.setPoint(rs.getInt("point"));
				
				vo.setHope_yn(rs.getString("hope_yn"));
				vo.setGrade(rs.getString("grade"));
				vo.setAddr(rs.getString("addr"));
				vo.setGender(rs.getString("gender"));
				vo.setHope_subject(rs.getString("hope_subject"));
				vo.setHope_time(rs.getString("hope_time"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setIntroduce(rs.getString("introduce"));
				
				
				
				//ArrayList�߰�
				student_list.add(vo);
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
		return student_list;
	}
	
	
	public int hope_update(String id, String hope_result) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE USER_STUDENT SET HOPE_YN = ? WHERE ID = ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			
			pstmt.setString(1, hope_result);
			pstmt.setString(2, id);
			//4.DB�� ����(res:ó�������)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
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
