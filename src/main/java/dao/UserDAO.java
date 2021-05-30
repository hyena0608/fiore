package dao;

import java.sql.*;
import util.*;
import javax.naming.*;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserDAO {
	public boolean insert(String uid, String jsonstr) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO user(id, jsonstr) VALUES(?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, jsonstr);
			
			int count = stmt.executeUpdate();
			
			return (count == 1) ? true : false ;
		}finally {
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
	public boolean delete(String uid) throws NamingException, SQLException{
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

			/*
			ArrayList<UserObj> users = new ArrayList<UserObj>();
			while(rs.next()) {
				UserObj userobj = new UserObj(rs.getString("id"), rs.getString("name"), rs.getString("ts"));
				users.add(userobj);
			}
			return users;
			*/
			/*
			JSONArray users = new JSONArray();
			while(rs.next()) {
				JSONObject usrobj = new JSONObject();
				usrobj.put("id", rs.getString("id"));
				usrobj.put("name", rs.getString("name"));
				usrobj.put("ts", rs.getString("ts"));
				users.add(usrobj);
			}
			return users.toJSONString(); // 스트링 형태로 인코딩해서 반환
			*/
			
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
	
	public int signup(String uid, String upass, String uname) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO user(id, password, username) VALUES(?, ?, ?)";
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, upass);
			stmt.setString(3, uname);
			int count = stmt.executeUpdate();

			return count;
			
			
		} finally {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
	} 
}
