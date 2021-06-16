package dao;

import java.sql.*;
import util.*;
import javax.naming.*;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserDAO {
	public boolean signup(String uid, String userclass, String jsonstr) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			synchronized(this) {
				String sql = "SELECT no FROM user ORDER BY no DESC LIMIT 1";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				int max = (!rs.next()) ? 0 : rs.getInt("no");
				stmt.close();
				
				JSONObject jsonobj = (JSONObject) (new JSONParser()).parse(jsonstr);
				jsonobj.put("no", max + 1);
				
			String sql2 = "INSERT INTO user(no, id, userclass, jsonstr) VALUES(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1,  max + 1);
			stmt.setString(2, jsonobj.get("id").toString());
			stmt.setString(3, jsonobj.get("userclass").toString());
			stmt.setString(4, jsonobj.toJSONString());
			
			int count = stmt.executeUpdate();
			
			return (count == 1) ? true : false ;
			}
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean exists(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement("select id from user where id=?");
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			
			if(rs.next()) 
				return true;
			else
				return false;
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean withdraw(String uid) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from user where id = ?");
			stmt.setString(1, uid);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public boolean delete(String deleteno) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from user where no = ?");
			stmt.setString(1, deleteno);
			int count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	
	public int login(String uid, String upass) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM user WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			
			rs = stmt.executeQuery();
			if(!rs.next()) return 1;
			
			String jsonstr = rs.getString("jsonstr");
			JSONObject obj = (JSONObject) (new JSONParser()).parse(jsonstr);
			String pass = obj.get("password").toString();
			if(!upass.equals(pass)) return 2;
			return 0;			
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
			
			String sql = "SELECT jsonstr FROM user";
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
	
	public String getListsort(String which, String ascdesc) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM user ORDER BY " + which + " " + ascdesc;
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
	
	public String getSellerList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT jsonstr FROM user where userclass='판매자'";
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
	
	public String userSearch(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT jsonstr FROM user WHERE id = ?" ;
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
			String sql = "SELECT jsonstr FROM user WHERE no = ?" ;
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
	public boolean edit(String uid, String userclass, String jsonstr) throws NamingException, SQLException{
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("update user set userclass = ? where id = ?");
			stmt.setString(1, userclass);
			stmt.setString(2, uid);
			int count = stmt.executeUpdate();
			if(count == 0) return false;
			stmt.close();
			
			stmt = conn.prepareStatement("update user set jsonstr = ? where id = ?");
			stmt.setString(1, jsonstr);
			stmt.setString(2, uid);
			count = stmt.executeUpdate();
			return (count == 1) ? true : false; 
		}finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	}
	//내 정보
	public String myInfo(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.prepareStatement("select jsonstr from user where id = ?");
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
}