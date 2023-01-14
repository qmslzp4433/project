package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reference_vo.ContentVO;
import service.DBService;


public class SearchDAO {
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static SearchDAO single = null;
	
	public static SearchDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new SearchDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	//id�˻���� �����
	public List<ContentVO> searchid(String id) {
		
		List<ContentVO> searchid = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		
		
		String sql1 = "SELECT d.ref_seq,d.ref_route, d.id, d.content_main, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize" +									
				 " FROM reference_data d, user_total u											 	 " +
				 " WHERE u.id=d.id AND (d.content_main LIKE ? OR d.title LIKE ?)				   		 	 " ;
				
				
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
			pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setString(1, "%"+id +"%");
			pstmt1.setString(2, "%"+id +"%");
			rs1 = pstmt1.executeQuery();
			
			
			while (rs1.next()) {
				ContentVO vo = new ContentVO();
				// ���緹�ڵ尪=>Vo����
				vo.setRef_seq(rs1.getInt("ref_seq"));
				vo.setRef_route(rs1.getString("ref_route"));
				vo.setId(rs1.getString("id"));
				vo.setNickname(rs1.getString("nickname"));
				vo.setContent_main(rs1.getString("content_main"));
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
				searchid.add(vo);
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

		return searchid;
	}
	
	
	
	
	
	
	
}
