package search_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import search_vo.Teacher_SearchVO;
import service.DBService;

public class Teacher_SearchDAO {
	
	static String finalSql = "";
	static String max_list = "";
	
	static ArrayList<String> subjectSelect = new ArrayList<String>();	// ����
	static ArrayList<String> areaSelect = new ArrayList<String>();		// ����
	static ArrayList<String> meetSelect = new ArrayList<String>();		// ���鰡�ɿ���
	static ArrayList<String> genderSelect = new ArrayList<String>();	// ����
	static ArrayList<String> mbtiSelect = new ArrayList<String>();		// MBTI
	static ArrayList<String> hashTagSelect = new ArrayList<String>();	// �ؽ��±�
	
	static int p_list = 0;
	
	static String subjects = "";
	static String areas = "";
	static String myns = "";
	static String genders = "";
	static String mbtis = "";
	static String hashtags = "";
	
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Teacher_SearchDAO single = null;

	public static Teacher_SearchDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Teacher_SearchDAO();
		//������ ��ü������ ��ȯ
		return single;
	}
	
	

	
	public ArrayList<String> subject(ArrayList<String> list) {		
		subjectSelect = list;
		return subjectSelect;
	}
	
	public ArrayList<String> area(ArrayList<String> list) {		
		areaSelect = list;
		return areaSelect;
	}
		
	public ArrayList<String> myn(ArrayList<String> list) {		
		meetSelect = list;
		return meetSelect;
	}
	
	public ArrayList<String> gender(ArrayList<String> list) {		
		genderSelect = list;
		return genderSelect;
	}
	
	public ArrayList<String> mbti(ArrayList<String> list) {		
		mbtiSelect = list;
		return mbtiSelect;
	}
	
	public ArrayList<String> hashtag(ArrayList<String> list) {		
		hashTagSelect = list;
		return hashTagSelect;
	}
	
	public String subjects(String subject) {
		subjects = "";
		subjects += subject;
		return subjects;
	}
	
	public String areas(String area) {
		areas = "";
		areas += area;
		return areas;
	}
	
	public String myns(String myn) {
		myns = "";
		myns += myn;
		return myns;
	}
	
	public String genders(String gender) {
		genders = "";
		genders += gender;
		return genders;
	}
	
	public String mbtis(String mbti) {
		mbtis = "";
		mbtis += mbti;
		return mbtis;
	}
	
	public String hashtags(String hashtag) {
		hashtags = "";
		hashtags += hashtag;
		return hashtags;
	}
	
	public String subject() {
		return subjects;
	}
	
	public String area() {
		return areas;
	}
	
	public String myn() {
		return myns;
	}
	
	public String gender() {
		return genders;
	}
	
	public String mbti() {
		return mbtis;
	}
	
	public String hashtag() {
		return hashtags;
	}
	
	public List<Teacher_SearchVO> selectList() {

		List<Teacher_SearchVO> list = new ArrayList<Teacher_SearchVO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder finalQ = new StringBuilder();
		StringBuilder max = new StringBuilder();
		finalQ.append("select * from (select rownum rnum, ts.* from ");
		finalQ.append("(select * from TEACHER_SEARCH WHERE ");

		if(!subjectSelect.isEmpty()) { // ArrayList�� ������� �ʴٸ�
			finalQ.append("(");
			for(int i = 0; i < subjectSelect.size(); i++) {
				finalQ.append("subject LIKE '%").append(subjectSelect.get(i)).append("%' OR "); // ���񳢸��� OR �߰�
				if(i == subjectSelect.size()-1) {	// ������ �����̶��
					finalQ.delete(finalQ.length()-3, finalQ.length());	// OR ���� ����	
				}
			}
		}
		else {	// ArrayList�� ���������
			finalQ.append("(subject LIKE '%%'");	// �� �ֱ�
		}

		finalQ.append(") AND ");	// ���� �������� �Ѿ ���� AND �߰�


		if(!areaSelect.isEmpty()) {
			finalQ.append("(");
			for(int i = 0; i < areaSelect.size(); i++) {
				finalQ.append("area LIKE '%").append(areaSelect.get(i)).append("%' OR ");
				if(i == areaSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(area LIKE '%%'");
		}

		finalQ.append(") AND ");

		if(!meetSelect.isEmpty()) {
			finalQ.append("(");
			for(int i = 0; i < meetSelect.size(); i++) {
				finalQ.append("myn = '").append(meetSelect.get(i)).append("' OR ");
				if(i == meetSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(myn LIKE '%%'");
		}

		finalQ.append(") AND ");

		if(!genderSelect.isEmpty()) {
			finalQ.append("(");
			for(int i = 0; i < genderSelect.size(); i++) {
				finalQ.append("gender = '").append(genderSelect.get(i)).append("' OR ");
				if(i == genderSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(gender LIKE '%%'");
		}

		finalQ.append(") AND ");

		if(!mbtiSelect.isEmpty()) {
			finalQ.append("(");
			for(int i = 0; i < mbtiSelect.size(); i++) {
				finalQ.append("MBTI = '").append(mbtiSelect.get(i)).append("' OR ");
				if(i == mbtiSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(MBTI LIKE '%%'");
		}

		finalQ.append(") AND ");

		if(!hashTagSelect.isEmpty()) {
			finalQ.append("(");
			for(int i = 0; i < hashTagSelect.size(); i++) {
				finalQ.append("hashtag LIKE '%").append(hashTagSelect.get(i)).append("%' OR ");
				if(i == hashTagSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(hashtag LIKE '%%'");
		}

		finalQ.append(")) ts) b");
		max.append(finalQ);
		max = max.replace(7, 8, "max(b.rnum)");
		max_list = max.toString();
		
		finalQ.append(" where b.rnum between " + (p_list+1) + " and " + (p_list+10));
		finalSql = finalQ.toString();
		
		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
						
			pstmt = conn.prepareStatement(finalSql);
			
			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Teacher_SearchVO vo = new Teacher_SearchVO();
				//���緹�ڵ尪=>Vo����
				vo.setId(rs.getString("id"));
				vo.setBadge1(rs.getString("badge1"));
				vo.setBadge2(rs.getString("badge2"));
				vo.setBadge3(rs.getString("badge3"));
				vo.setNickname(rs.getString("nickname"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setArea(rs.getString("area"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setMbti(rs.getString("mbti"));
				vo.setMyn("���� " + rs.getString("myn"));
				vo.setProfile(rs.getString("profile"));
				
//				System.out.println(rs.getString("id"));
//				String title = video_selectOne(rs.getString("id"));
//				vo.setTitle(title);

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
	
	public void add() {
		p_list = p_list + 10;
	}
	
	public void reset() {
		p_list = 0;
		max_list = "";
	}
	
	public boolean compare() {
		if(max_list() != 0) {
			return p_list+10 < max_list();
		}
		else {
			return true;
		}
	}
	
	public int max_list() {

		int max = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = max_list;
		
		if( sql.equals("")) {
			return max;
		}

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter ����

			//4.����� ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				max = rs.getInt("max(b.rnum)");

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
	
	public List<Teacher_SearchVO> first() {
		
		subjectSelect.clear(); 
		areaSelect.clear(); 
		meetSelect.clear(); 
		genderSelect.clear(); 
		mbtiSelect.clear(); 
		hashTagSelect.clear(); 
		
		subjects = "";
		areas = "";
		myns = "";
		genders = "";
		mbtis = "";
		hashtags = "";

		List<Teacher_SearchVO> list = new ArrayList<Teacher_SearchVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select rownum rnum, ts.* from (select * from TEACHER_SEARCH) ts) b where b.rnum between ? and ?";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (p_list+1));
			pstmt.setInt(2, (p_list+10));

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Teacher_SearchVO vo = new Teacher_SearchVO();
				//���緹�ڵ尪=>Vo����
				vo.setId(rs.getString("id"));
				vo.setBadge1(rs.getString("badge1"));
				vo.setBadge2(rs.getString("badge2"));
				vo.setBadge3(rs.getString("badge3"));
				vo.setNickname(rs.getString("nickname"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setArea(rs.getString("area"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setMbti(rs.getString("mbti"));
				vo.setMyn("���� " + rs.getString("myn"));
//				System.out.println(rs.getString("id"));
//				String title = video_selectOne(rs.getString("id"));
//				vo.setTitle(title);
//				System.out.println(title);

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
	
	
	
	public String video_selectOne(String id) {

//		String vo = null;
		String title="";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select title"
				+ " from teacher_video"
				+ " where video_seq = (select min(video_seq) from teacher_video where id=?)";

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
				title = rs.getString("title");
				
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

		return title;
	}
	
	public ArrayList<String> id_check() {
		ArrayList<String> id_check = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from TEACHER_SEARCH";

		try {
			//1.Connection���´�
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü������ ������
			pstmt = conn.prepareStatement(sql);

			//3.����� ó����ü ������
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id_check.add(rs.getString("id"));
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
	
		return id_check;
	}
	
	
	
}
