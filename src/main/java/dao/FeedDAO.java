package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.ConnectionPool;

public class FeedDAO {
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
}
