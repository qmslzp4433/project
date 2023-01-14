package search_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import search_vo.Student_SearchVO;
import service.DBService;

public class Student_SearchDAO {
	
	static String finalSql = "";
	static String max_list = "";
	
	static ArrayList<String> subjectSelect = new ArrayList<String>();	// ����
	static ArrayList<String> areaSelect = new ArrayList<String>();		// ����
	static ArrayList<String> meetSelect = new ArrayList<String>();		// ���鰡�ɿ���
	static ArrayList<String> genderSelect = new ArrayList<String>();	// ����
	
	static int p_list = 0;
	
	static String subjects = "";
	static String areas = "";
	static String myns = "";
	static String genders = "";
	
	// single-ton pattern: 
	// ��ü1���������ؼ� ���������� ��������
	static Student_SearchDAO single = null;

	public static Student_SearchDAO getInstance() {
		//�������� �ʾ����� ����
		if (single == null)
			single = new Student_SearchDAO();
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
	
	public List<Student_SearchVO> selectList() {

		List<Student_SearchVO> list = new ArrayList<Student_SearchVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder finalQ = new StringBuilder();
		StringBuilder max = new StringBuilder();
		finalQ.append("select * from (select rownum rnum, ss.* from ");
		finalQ.append("(select * from STUDENT_SEARCH WHERE ");

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
				finalQ.append("addr = '").append(areaSelect.get(i)).append("' OR ");
				if(i == areaSelect.size()-1) {
					finalQ.delete(finalQ.length()-3, finalQ.length());
				}
			}
		}
		else {
			finalQ.append("(addr LIKE '%%'");
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

		finalQ.append(")) ss) b");
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
				Student_SearchVO vo = new Student_SearchVO();
				//���緹�ڵ尪=>Vo����
				
				vo.setId(rs.getString("id"));
				vo.setHyn(rs.getString("hyn"));
				vo.setNickname(rs.getString("nickname"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setAddr(rs.getString("addr"));
				vo.setGrade(rs.getString("grade"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setMyn("���� " + rs.getString("myn"));

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
	
	public List<Student_SearchVO> first() {
		
		subjectSelect.clear();
		areaSelect.clear();
		meetSelect.clear();
		genderSelect.clear();
		
		subjects = "";
		areas = "";
		myns = "";
		genders = "";

		List<Student_SearchVO> list = new ArrayList<Student_SearchVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select rownum rnum, ss.* from (select * from STUDENT_SEARCH) ss) b where b.rnum between ? and ?";

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
				Student_SearchVO vo = new Student_SearchVO();
				//���緹�ڵ尪=>Vo����
				
				vo.setId(rs.getString("id"));
				vo.setHyn(rs.getString("hyn"));
				vo.setNickname(rs.getString("nickname"));
				vo.setGender(rs.getString("gender"));
				vo.setSubject(rs.getString("subject"));
				vo.setAddr(rs.getString("addr"));
				vo.setGrade(rs.getString("grade"));
				vo.setHashtag(rs.getString("hashtag"));
				vo.setMyn("���� " + rs.getString("myn"));

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
	
	public ArrayList<String> id_check() {
		ArrayList<String> id_check = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from STUDENT_SEARCH";

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
