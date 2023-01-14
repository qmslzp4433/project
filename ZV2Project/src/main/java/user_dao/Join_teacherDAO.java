package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import service.DBService;
import user_vo.Join_teacherVO;

public class Join_teacherDAO {
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Join_teacherDAO single = null;

	public static Join_teacherDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Join_teacherDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	
	
	public int insert_user_total(Join_teacherVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO USER_TOTAL VALUES (?, ?, ?, ?, ?, ?, '������', 0,'')";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getNickname());
			pstmt.setString(5, vo.getPhone());
			pstmt.setString(6, vo.getEmailaddr());
			
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
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
//ȸ������ �� ������ ���̺� insert ==============================================================
	public int insert_user_teacher(Join_teacherVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO USER_TEACHER VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'NEW','','')";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getProfile());
			pstmt.setString(3, vo.getIdnumber());
			pstmt.setString(4, vo.getAddr());
			pstmt.setString(5, vo.getArea());
			pstmt.setString(6, vo.getGender());
			pstmt.setString(7, vo.getSubject());
			pstmt.setString(8, vo.getMbti());
			pstmt.setString(9, vo.getHashtag());
			pstmt.setString(10, vo.getIntroduce());
			pstmt.setString(11, vo.getVideo_yn());
			pstmt.setString(12, vo.getPre_yn());
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
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
	public int insert_teacher_video(String id, String video_name) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO TEACHER_VIDEO VALUES (TEACHER_VIDEO_SEQ.nextVal, ?, ?)";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, id);
			pstmt.setString(2, video_name);
			
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
				e.printStackTrace();
			}
		}
		return res;
	}

	
	
//������ ������������ ���� ������ update��==============================================================================================================
	public int update_user_total(Join_teacherVO vo) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE USER_TOTAL SET PW = ?, NAME = ?, PHONE = ? WHERE ID= ?";

		try {
			//1.Connectionȹ��
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ȹ��
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ä���
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getId());
			
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
				e.printStackTrace();
			}
		}
		return res;
	}
	
	//ȸ������ �� ������ ���̺� insert ==============================================================
		public int update_user_teacher(Join_teacherVO vo) {
			int res = 0;

			Connection conn = null;
			PreparedStatement pstmt = null;

			String sql = "UPDATE USER_TEACHER SET ADDR = ?, GENDER = ?, SUBJECT = ?, MBTI = ?, HASHTAG = ?, INTRODUCE = ?, VIDEO_YN = ?, PRE_YN = ? WHERE ID = ?";

			try {
				//1.Connectionȹ��
				conn = DBService.getInstance().getConnection();
				//2.���ó����ü ȹ��
				pstmt = conn.prepareStatement(sql);

				//3.pstmt parameter ä���
				pstmt.setString(1, vo.getAddr());
				pstmt.setString(2, vo.getGender());
				pstmt.setString(3, vo.getSubject());
				pstmt.setString(4, vo.getMbti());
				pstmt.setString(5, vo.getHashtag());
				pstmt.setString(6, vo.getIntroduce());
				pstmt.setString(7, vo.getVideo_yn());
				pstmt.setString(8, vo.getPre_yn());
				pstmt.setString(9, vo.getId());
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
					e.printStackTrace();
				}
			}
			return res;
		}
		
		
		public int update_teacher_profile(String id, String profile) {
			int res = 0;

			Connection conn = null;
			PreparedStatement pstmt = null;

			String sql = "UPDATE USER_TEACHER SET PROFILE = ? WHERE ID = ?";

			try {
				//1.Connectionȹ��
				conn = DBService.getInstance().getConnection();
				//2.���ó����ü ȹ��
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, profile);
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
					e.printStackTrace();
				}
			}
			return res;
		}
		
		
		public int update_teacher_area(String id, String area) {
			int res = 0;

			Connection conn = null;
			PreparedStatement pstmt = null;

			String sql = "UPDATE USER_TEACHER SET AREA = ? WHERE ID = ?";

			try {
				//1.Connectionȹ��
				conn = DBService.getInstance().getConnection();
				//2.���ó����ü ȹ��
				pstmt = conn.prepareStatement(sql);

				//3.pstmt parameter ä���
				pstmt.setString(1, area);
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
					e.printStackTrace();
				}
			}
			return res;
		}
}
