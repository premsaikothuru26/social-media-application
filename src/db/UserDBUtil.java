package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import model.User;

public class UserDBUtil {

	private DataSource datasource;
	
	public UserDBUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	// find User
	public User findUser(String email) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		User foundUser = null;
	
		
		try {
			
			conn = this.datasource.getConnection();
			
			String sql = "SELECT * from user WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			res = pstmt.executeQuery();
			
			if(res.next()) {
				String tempFname = res.getString("FirstName").toString();
				String tempLname = res.getString("LastName").toString();
				String tempEmail = res.getString("Email").toString();
				String tempPass = res.getString("Password").toString();
				
				foundUser = new User(tempFname,tempLname,tempEmail,tempPass);
			}
			
			
		}finally {
			
			close(conn,smt,pstmt,res);
			
		}
	
		return foundUser;	
	}
	
	//register
	
	public void insertUser(User user) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String fname = user.getFname();
		String lname = user.getLname();
		String email = user.getEmail();
		String pass = user.getPass();
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = String.format("INSERT INTO user VALUES('%s','%s','%s','%s')",fname,lname,email,pass);
			smt = conn.createStatement();
			smt.executeUpdate(sql);
						
		}finally {
			
			close(conn,smt,pstmt,res);
			
		}	
	}
	
	
	
	
	
	// Closing the Connection
	
	private void close(Connection conn, Statement smt, PreparedStatement pstmt, ResultSet res) {
		try {
			
			if(conn != null) {
				conn.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(smt != null) {
				smt.close();
			}
			if(res != null) {
				res.close();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
