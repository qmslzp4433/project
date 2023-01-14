package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reference_vo.ContentVO;
import reference_vo.Subject_middleVO;
import service.DBService;


public class Subject_middleDAO {
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Subject_middleDAO single = null;
	
	public static Subject_middleDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Subject_middleDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	//�߰��� �̾ƿ���
	public List<Subject_middleVO> selectList() {

		List<Subject_middleVO> list_subject = new ArrayList<Subject_middleVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " SELECT subject_middle FROM subject_1"
					+" ORDER BY subject_middle DESC";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Subject_middleVO vo = new Subject_middleVO();
				//���緹�ڵ尪=>Vo����
				vo.setSubject_middle( rs.getString("subject_middle"));
				
				//ArrayList�߰�
				list_subject.add(vo);
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

		return list_subject;
	}
	
	
	//�Ұ��� �̾ƿ���
	public List<Subject_middleVO> selectList_subject_bottom( String subject_middle ) {

		List<Subject_middleVO> list_subject_bottom = new ArrayList<Subject_middleVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql_subject_bottom = "select subject_bottom from subject_2 where subject_middle =?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql_subject_bottom);
			pstmt.setString(1, subject_middle);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Subject_middleVO vo = new Subject_middleVO();
				//���緹�ڵ尪=>Vo����
				vo.setSubject_bottom(rs.getString("subject_bottom"));

				//ArrayList�߰�
				list_subject_bottom.add(vo);
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

		return list_subject_bottom;
		
	}	
	
	
	//�߰��� ������ �з�
	public List<ContentVO> selectList_subject_middle_content( String subject_middle ) {

		List<ContentVO> list_content = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
				+ " FROM reference_data d, user_total u" 
				+ " WHERE u.id=d.id AND d.subject_middle=?";
			
			
		
				
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
		
			
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, subject_middle);
			rs1 = pstmt1.executeQuery();
			
			
			while (rs1.next()) {
				ContentVO vo = new ContentVO();
				// ���緹�ڵ尪=>Vo����
				vo.setRef_seq(rs1.getInt("ref_seq"));
				vo.setRef_route(rs1.getString("ref_route"));
				vo.setId(rs1.getString("id"));
				vo.setNickname(rs1.getString("nickname"));
				vo.setTitle(rs1.getString("title"));
				vo.setContent_price(rs1.getInt("content_price"));
				vo.setDownload_cnt(rs1.getInt("download_cnt"));
				vo.setContent(rs1.getString("content"));
				vo.setFileSize(rs1.getLong("fileSize"));
				
				int ref_seq = rs1.getInt("ref_seq");
				//System.out.println(ref_seq);
				
				String sql2 =  "SELECT NVL( round(avg(regist_score),1) , 0 ) avg_score  FROM reference_comment where ref_seq = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, ref_seq);
				rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					//������� ���� �̾ƿ���
					vo.setAvg_score(rs2.getDouble("avg_score"));
				}
				rs2.close();
				pstmt2.close();
				// ArrayList�߰�
				list_content.add(vo);
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				if (rs1 != null)
					rs1.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list_content;
	}
	
	
	
	
	
	
}
