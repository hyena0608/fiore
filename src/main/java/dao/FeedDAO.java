package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.ConnectionPool;

public class FeedDAO {
	// onedaycalss
	public boolean insert(String jsonstr) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			synchronized(this) { // 상호배제 동작하게 만들기
				// no 값 받아오기
				String sql = "SELECT no FROM onedaypost ORDER BY no DESC LIMIT 1";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				int max = (!rs.next()) ? 0 : rs.getInt("no");
				stmt.close();
				
				JSONObject jsonobj = (JSONObject) (new JSONParser()).parse(jsonstr);
				jsonobj.put("no", max + 1);
				
				
				stmt = conn.prepareStatement("insert into onedaypost(no, id, jsonstr) values (?, ?, ?)");
				stmt.setInt(1,  max + 1);
				stmt.setString(2, jsonobj.get("id").toString());
				stmt.setString(3, jsonobj.toJSONString());
				
				int count = stmt.executeUpdate();
				
				return (count == 1) ? true : false ;
			}
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String getList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.prepareStatement("select jsonstr from onedaypost ORDER BY no DESC");
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt ++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String myFeedList(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.prepareStatement("select jsonstr from onedaypost where id = ?");
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt ++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String myCommentList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.prepareStatement("select jsonstr from comment");
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt ++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String getGroup(String maxNo) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			String sql = "select jsonstr from onedaypost";
			if(maxNo != null) {
				sql += " where no < " + maxNo;
			}
			sql += " order by NO desc limit 3";
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt ++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	// reservation
	public boolean insertComment(String jsonstr) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			synchronized(this) { // 상호배제 동작하게 만들기
				// no 값 받아오기
				String sql = "SELECT no FROM comment ORDER BY no DESC LIMIT 1";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				int max = (!rs.next()) ? 0 : rs.getInt("no");
				stmt.close();
				
				JSONObject jsonobj = (JSONObject) (new JSONParser()).parse(jsonstr);
				jsonobj.put("no", max + 1);
				
				
				stmt = conn.prepareStatement("insert into comment(no, id, jsonstr) values (?, ?, ?)");
				stmt.setInt(1,  max + 1);
				stmt.setString(2, jsonobj.get("id").toString());
				stmt.setString(3, jsonobj.toJSONString());
				
				int count = stmt.executeUpdate();
				
				return (count == 1) ? true : false ;
			}
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String getGroupComment(String maxNo) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			String sql = "select jsonstr from comment";
			if(maxNo != null) {
				sql += " where no < " + maxNo;
			}
			sql += " order by NO desc limit 3";
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt ++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String getFeedsort(String which, String ascdesc) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM onedaypost ORDER BY " + which + " " + ascdesc;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String getCommentsort(String which, String ascdesc) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM comment ORDER BY " + which + " " + ascdesc;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}

	public String feedUserSearch(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM onedaypost WHERE id = ?" ;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String commentUserSearch(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM commnet WHERE id = ?" ;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String editlist(String editno) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM onedaypost WHERE no = ?" ;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, editno);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public String editCommentlist(String editid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM comment WHERE no = ?" ;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, editid);
			rs = stmt.executeQuery();
			
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
				if(cnt++>0) str +=",";
				str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	public boolean edit(String no, String jsonstr) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("update onedaypost set jsonstr = ? where no = ?");
			stmt.setString(1, jsonstr);
			stmt.setString(2, no);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean editComment(String jsonstr) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			JSONObject jsonobj = (JSONObject) (new JSONParser()).parse(jsonstr);
			stmt = conn.prepareStatement("update comment set jsonstr = ? where id = ?");
			stmt.setString(1, jsonobj.toJSONString());
			stmt.setString(2, jsonobj.get("no").toString());
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean MeditComment(String no, String jsonstr) throws NamingException, SQLException, ParseException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("update comment set jsonstr = ? where no = ?");
			stmt.setString(1, jsonstr);
			stmt.setString(2, no);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean feedDelete(String deleteno) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from onedaypost where no = ?");
			stmt.setString(1, deleteno);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean commentDelete(String deleteno) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from comment where no = ?");
			stmt.setString(1, deleteno);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
}
