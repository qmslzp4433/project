package reference_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reference_vo.CommentVO;
import reference_vo.ContentVO;
import service.DBService;

public class ContentDAO {
	// single-ton pattern:
	// ��ü1���������ؼ� ���������� ��������
	static ContentDAO single = null;

	public static ContentDAO getInstance() {
		// �������� �ʾ����� ����
		if (single == null)
			single = new ContentDAO();
		// ������ ��ü������ ��ȯ
		return single;
	}

	// �ʱ���ȸ
	public List<ContentVO> selectList() {

		List<ContentVO> list_content = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
						
		String sql1 = "SELECT d.ref_seq,d.ref_route, d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
				+ " FROM reference_data d, user_total u" 
				+ " WHERE u.id=d.id";
		
		
				
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
		
			
			pstmt1 = conn.prepareStatement(sql1);
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

	// �ʱ���ȸ(1) + �ֽż� ����
	public List<ContentVO> selectList_align_latest1() {

		List<ContentVO> list_content = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
				+ " FROM reference_data d, user_total u" 
				+ " WHERE u.id=d.id"
				+ " ORDER BY d.ref_seq DESC";
		
				
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
		
			
			pstmt1 = conn.prepareStatement(sql1);
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

	// �ʱ���ȸ(1) + �ٿ�ε�� ����
	public List<ContentVO> selectList_align_download1() {

		List<ContentVO> list_content = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
				+ " FROM reference_data d, user_total u" 
				+ " WHERE u.id=d.id"
				+ " ORDER BY d.download_cnt DESC";
		
				
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
		
			
			pstmt1 = conn.prepareStatement(sql1);
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

	// �ʱ���ȸ(1) + ������
	public List<ContentVO> selectList_align_avgscore1() {

		List<ContentVO> list_content = new ArrayList<ContentVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, round(avg(c.regist_score),1) avg_score"
				+ " , d.content, d.filesize FROM reference_data d, reference_comment c, user_total u" 
				+ " WHERE c.ref_seq = d.ref_seq	AND u.id=d.id"
				+ " GROUP BY d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.ref_seq, d.ref_route, d.content, d.filesize" 
				+ " ORDER BY avg_score DESC";
		try {
			// 1.Connection���´�
			conn = DBService.getInstance().getConnection();
			// 2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			// 3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ContentVO vo = new ContentVO();
				// ���緹�ڵ尪=>Vo����
				
				vo.setRef_seq(rs.getInt("ref_seq"));
				vo.setRef_route(rs.getString("ref_route"));
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setTitle(rs.getString("title"));
				vo.setContent_price(rs.getInt("content_price"));
				vo.setDownload_cnt(rs.getInt("download_cnt"));
				vo.setAvg_score(rs.getDouble("avg_score"));
				vo.setContent(rs.getString("content"));
				vo.setFileSize(rs.getLong("fileSize"));
				// ArrayList�߰�
				list_content.add(vo);
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

		return list_content;
	}

	
	// �߱���ȸ(2) + �ֽż� ����
		public List<ContentVO> selectList_align_latest2(String subject_middle) {

			List<ContentVO> list_content = new ArrayList<ContentVO>();
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			
			String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
					+ " FROM reference_data d, user_total u" 
					+ " WHERE u.id=d.id AND d.subject_middle=?"
					+ " ORDER BY d.ref_seq DESC";
			
					
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

		// �߱���ȸ(2) + �ٿ�ε�� ����
				public List<ContentVO> selectList_align_download2(String subject_middle) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;
					
					String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
							+ " FROM reference_data d, user_total u" 
							+ " WHERE u.id=d.id AND d.subject_middle=?"
							+ " ORDER BY d.download_cnt DESC";
					
							
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
				// �߱���ȸ(2) + ������
				public List<ContentVO> selectList_align_avgscore2(String subject_middle) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT d.ref_seq,d.ref_route, d.id, u.nickname, d.title, d.content_price, d.download_cnt, round(avg(c.regist_score),1) avg_score"
							+ " , d.content, d.filesize FROM reference_data d, reference_comment c, user_total u" 
							+ " WHERE c.ref_seq = d.ref_seq	AND d.subject_middle = ? AND u.id=d.id"
							+ " GROUP BY d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.ref_seq, d.ref_route, d.content, d.filesize"
							+ " ORDER BY avg_score DESC";
					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
						// 2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, subject_middle);
						// 3.����� ó����ü ������
						rs = pstmt.executeQuery();

						while (rs.next()) {
							ContentVO vo = new ContentVO();
							// ���緹�ڵ尪=>Vo����
							
							vo.setRef_seq(rs.getInt("ref_seq"));
							vo.setRef_route(rs.getString("ref_route"));
							vo.setId(rs.getString("id"));
							vo.setNickname(rs.getString("nickname"));
							vo.setTitle(rs.getString("title"));
							vo.setContent_price(rs.getInt("content_price"));
							vo.setDownload_cnt(rs.getInt("download_cnt"));
							vo.setAvg_score(rs.getDouble("avg_score"));
							vo.setContent(rs.getString("content"));
							vo.setFileSize(rs.getLong("fileSize"));
							// ArrayList�߰�
							list_content.add(vo);
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

					return list_content;
				}
			
				
				// ������ȸ(3) + �ֽż� ����
				public List<ContentVO> selectList_align_latest3(String subject_bottom) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;
					
					String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
							+ " FROM reference_data d, reference_comment c, user_total u" 
							+ " WHERE u.id=d.id AND d.subject_bottom=?"
							+ " GROUP BY d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.ref_seq, d.ref_route, d.content, d.filesize"
							+ " ORDER BY d.ref_seq DESC";
					
							
					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
					
						
						pstmt1 = conn.prepareStatement(sql1);
						pstmt1.setString(1, subject_bottom);
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
	
				// ������ȸ(3) + �ٿ�ε�� ����
				public List<ContentVO> selectList_align_download3(String subject_bottom) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;
					
					String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.content, d.filesize"
							+ " FROM reference_data d, reference_comment c, user_total u" 
							+ " WHERE u.id=d.id AND d.subject_bottom=?"
							+ " GROUP BY d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.ref_seq, d.ref_route, d.content, d.filesize"
							+ " ORDER BY d.download_cnt DESC";
					
							
					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
					
						
						pstmt1 = conn.prepareStatement(sql1);
						pstmt1.setString(1, subject_bottom);
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
	
				
				// ������ȸ(3) + ������ ����
				public List<ContentVO> selectList_align_avgscore3(String subject_bottom) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql =  "SELECT d.ref_seq, d.ref_route, d.id, u.nickname, d.title, d.content_price, d.download_cnt, round(avg(c.regist_score),1) avg_score"
							+ " , d.content, d.filesize FROM reference_data d, reference_comment c, user_total u" 
							+ " WHERE c.ref_seq = d.ref_seq	AND d.subject_bottom = ? AND u.id=d.id"
							+ " GROUP BY d.id, u.nickname, d.title, d.content_price, d.download_cnt, d.ref_seq, d.ref_route, d.content, d.filesize" 
							+ " ORDER BY avg_score DESC";
					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
						// 2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, subject_bottom);
						// 3.����� ó����ü ������
						rs = pstmt.executeQuery();

						while (rs.next()) {
							ContentVO vo = new ContentVO();
							// ���緹�ڵ尪=>Vo����
							
							vo.setRef_seq(rs.getInt("ref_seq"));
							vo.setRef_route(rs.getString("ref_route"));
							vo.setId(rs.getString("id"));
							vo.setNickname(rs.getString("nickname"));
							vo.setTitle(rs.getString("title"));
							vo.setContent_price(rs.getInt("content_price"));
							vo.setDownload_cnt(rs.getInt("download_cnt"));
							vo.setAvg_score(rs.getDouble("avg_score"));
							vo.setContent(rs.getString("content"));
							vo.setFileSize(rs.getLong("fileSize"));
							// ArrayList�߰�
							list_content.add(vo);
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {

						try {
							if (rs != null)
								rs.close();
							if (pstmt != null)
							if (conn != null)
								conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					return list_content;
				}	

				
				//�������� content�� ���� 1���� �ҷ�����
				public List<ContentVO> selectList_detail(int ref_seq) {

					List<ContentVO> list_content = new ArrayList<ContentVO>();
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;
					
					String sql1 = "SELECT d.ref_seq,d.ref_route,d.id, u.nickname, d.title, d.content_price,d.content_main, d.download_cnt, d.content, d.filesize"
							+ " FROM reference_data d, user_total u" 
							+ " WHERE u.id=d.id AND d.ref_seq = ?";
							
							
					
							
					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
					
						
						pstmt1 = conn.prepareStatement(sql1);
						pstmt1.setInt(1, ref_seq);
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
							vo.setContent_main(rs1.getString("content_main"));
							vo.setContent(rs1.getString("content"));
							vo.setFileSize(rs1.getLong("fileSize"));
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
				
				
				//�������� ���ϸ� �����ͼ� �̸����� �Ѹ���
				public String filename(int ref_seq) {
					String res = "";
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT content FROM reference_data WHERE ref_seq=?";
					

					try {
						//1.Connectionȹ��
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü ȹ��
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
					

						//3.pstmt parameter ä���

						//4.����� ó����ü ������
						rs = pstmt.executeQuery();
						while (rs.next()) {
							res=rs.getString("content");
							
						}

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
				
				
				
				
				
				//���������� ��� ���� �ҷ����� ( ~�� ��� �� ��)
				public int showComment_cnt(int ref_seq) {
					int cnt=0;
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT COUNT(*) as cnt FROM reference_comment where ref_seq=?";
					
					try {
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);

						//3.pstmt parameter ����

						//4.����� ó����ü ������
						rs = pstmt.executeQuery();
						while (rs.next()) {
							cnt+=rs.getInt("cnt");
							
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
					return cnt;
				}
				
				
				
				//�������� content�� ��� �ҷ�����
				public List<CommentVO> selectList_comment(int ref_seq) {

					List<CommentVO> list_comment = new ArrayList<CommentVO>();
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT c.content, c.id, d.nickname, c.write_date, c.regist_score"
							+ " FROM reference_comment c, user_total d" 
							+ " WHERE ref_seq = ? AND c.id=d.id"
							+ " ORDER BY write_date DESC";
							

					try {
						// 1.Connection���´�
						conn = DBService.getInstance().getConnection();
						// 2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						// 3.����� ó����ü ������
						rs = pstmt.executeQuery();
						
						
				
						while (rs.next()) {
							CommentVO vo = new CommentVO();
							// ���緹�ڵ尪=>Vo����
						
							vo.setContent(rs.getString("content"));
							vo.setId(rs.getString("id"));
							vo.setNickname(rs.getString("nickname"));
							vo.setWrite_date(rs.getString("write_date"));
							vo.setRegist_score(rs.getInt("regist_score"));
							// ArrayList�߰�
							list_comment.add(vo);
					
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
					
					return list_comment;
				}
				
				
				
				//content���ϱ� ���
				public int w_check(int ref_seq, String id) {

					int check = 0;
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT * FROM reference_wishlist WHERE ref_seq = ? and id = ?";
					
					try {
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, id);

						//3.pstmt parameter ����

						//4.����� ó����ü ������
						rs = pstmt.executeQuery();
						
						if (rs.next()) {
							//���� �����ϸ� 1 ������ 0�λ��·� ��ȯ�ɰ�
							check = 1;
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
					return check;
				}
				
				public int w_insert(int ref_seq, String id) {
					// TODO Auto-generated method stub
					int res = 0;

					Connection conn = null;
					PreparedStatement pstmt = null;

					String sql = "INSERT INTO reference_wishlist VALUES (REFERENCE_WISHLIST_SEQ.nextval, ?, ?)";

					try {
						//1.Connectionȹ��
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü ȹ��
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, id);

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
				
				public int w_delete(int ref_seq, String id) {
					// TODO Auto-generated method stub
					int res = 0;

					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = "DELETE FROM reference_wishlist WHERE ref_seq = ? and id = ?";

					try {
						//1.Connectionȹ��
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü ȹ��
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, id);

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
				
				
				//content ���Ÿ�� �߰� ���
				public int b_check(int ref_seq, String id) {

					int check = 0;
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql = "SELECT * FROM reference_buylist WHERE ref_seq = ? and id = ?";
					
					try {
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, id);

						//3.pstmt parameter ����

						//4.����� ó����ü ������
						rs = pstmt.executeQuery();
						
						if (rs.next()) {
							//���� �����ϸ� 1 ������ 0�λ��·� ��ȯ�ɰ�
							check = 1;
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
					return check;
				}
			
				public int b_insert(int ref_seq, String id) {
					// TODO Auto-generated method stub
					int res = 0;

					Connection conn = null;
					PreparedStatement pstmt = null;

					String sql = "INSERT INTO reference_buylist VALUES (REFERENCE_BUYLIST_SEQ.nextval, ?, ?)";

					try {
						//1.Connectionȹ��
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü ȹ��
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, ref_seq);
						pstmt.setString(2, id);

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
				
				
				//���Ž� content_price��ŭ ����Ʈ ����
				public int bp_update(int ref_seq, String id) {
					int check=0;  //0�� �Ұ��� 1�� ����
					int content_price=0;
					int point = 0;
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					PreparedStatement pstmt2 = null;
					PreparedStatement pstmt3 = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;
					ResultSet rs3 = null;
					//����������
					String sql1 = "SELECT content_price FROM reference_data WHERE REF_SEQ=?";
					//���� ����Ʈ
					String sql2 = "SELECT point FROM user_total WHERE id=?";
					//���Ž� ����Ʈ����
					String sql3 = "UPDATE user_total SET point=(point)-? WHERE id =?"; 
					
					try {
						
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt1 = conn.prepareStatement(sql1);
						pstmt1.setInt(1, ref_seq);
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, id);
						
						
						
						
						//3.pstmt parameter ����

						//4.����� ó����ü ������
						rs1 = pstmt1.executeQuery();
						rs2 = pstmt2.executeQuery();
						 
						if (rs1.next()) {
							//�ش� ������ ���ݰ������
							content_price = rs1.getInt("content_price");
						}
						if (rs2.next()) {
							//�ش���̵� ��������Ʈ �������
							point = rs2.getInt("point");
						}
						
							pstmt3 = conn.prepareStatement(sql3);
							pstmt3.setInt(1, content_price);
							pstmt3.setString(2, id);
							pstmt3.executeUpdate(); // SQL�� ���


					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {

						try {
							if (rs1 != null)
								rs1.close();
							if (rs2 != null)
								rs2.close();
							if (pstmt1 != null)
								pstmt1.close();
							if (pstmt2 != null)
								pstmt2.close();
							if (pstmt3 != null)
								pstmt3.close();
							if (conn != null)
								conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return check;
				}
			
				
				
				//�ٿ�ε�� �ٿ�ε� ī��Ʈ ����.
				public int update_download_cnt(String fileName) {
					
					int download_cnt=0;
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					ResultSet rs1 = null;
				
					//�� �������� �ٿ�ε� �� �������
					String sql1 = "UPDATE reference_data SET download_cnt" + 
							" =(SELECT download_cnt+1 FROM reference_data WHERE content=?)" + 
							" WHERE content=?";
		
					
					try {
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt1 = conn.prepareStatement(sql1);				
						//3.pstmt parameter ����
						pstmt1.setString(1, fileName);
						pstmt1.setString(2, fileName);
						//4.����� ó����ü ������
						rs1 = pstmt1.executeQuery();
						
						 

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
					return download_cnt;
				}
				
				
			
				//���Ž� �Խñ� �����ο��� ����Ʈ ���� �ͼ� 
				public int get_point(int ref_seq) {
					int res=0;
					Connection conn = null;
					PreparedStatement pstmt1 = null;
					ResultSet rs1 = null;
				
					//���� ��������Ʈ�� �ݰ� ���ϱ� 
					String sql1 = "UPDATE user_total SET point = (SELECT point FROM user_total u, reference_data d WHERE d.id= u.id" + 
							"	AND d.ref_seq=?) " + 
							"	+ (SELECT (TRUNC(content_price/2, 0)) FROM reference_data WHERE ref_seq=?)" + 
							"	where id= (SELECT d.id FROM user_total u, reference_data d WHERE d.id= u.id AND " + 
							"	d.ref_seq=?)";
		
					
					try {
						//1.Connection���´�
						conn = DBService.getInstance().getConnection();
						//2.���ó����ü������ ������
						pstmt1 = conn.prepareStatement(sql1);				
						//3.pstmt parameter ����
						pstmt1.setInt(1, ref_seq);
						pstmt1.setInt(2, ref_seq);
						pstmt1.setInt(3, ref_seq);
						//4.����� ó����ü ������
						rs1 = pstmt1.executeQuery();
						
						 

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
					return res;
				}
				
				
}
