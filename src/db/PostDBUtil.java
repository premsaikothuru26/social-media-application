package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.Post;
import model.User;

public class PostDBUtil {
	private DataSource datasource;
	
	public PostDBUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	// GET ALL POSTS
	public ArrayList<Post> getAllPosts() throws Exception{
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		ArrayList<Post> tempPosts = new ArrayList<>();
		
		try {
			conn = this.datasource.getConnection();
			String sql = "select * from post";
			smt = conn.createStatement();
			res = smt.executeQuery(sql);
			
			while(res.next()) {
				String postId = Integer.toString(res.getInt("postID"));
				String email = res.getString("email").toString();
				String content = res.getString("content").toString();
				String image = res.getString("image").toString();
				String date = res.getString("date").toString();
				
				tempPosts.add(new Post(postId, email, content, image, date));	
			}
		}
		finally {
			close(conn,smt,pstmt,res);
		}
		return tempPosts;
	}

	
	// GET ALL USER POSTS
	public void getUserPosts(User user) throws Exception{
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		ArrayList<Post> tempPosts = new ArrayList<>();
		
		try {
			conn = this.datasource.getConnection();
			String sql = "select * from post WHERE email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String postId = Integer.toString(res.getInt("id"));
				String email = res.getString("email").toString();
				String content = res.getString("content").toString();
				String image = res.getString("image").toString();
				String date = res.getString("date").toString();
				
				tempPosts.add(new Post(postId, email, content, image, date));	
			}
			
			user.setPosts(tempPosts);
		}
		finally {
			close(conn,smt,pstmt,res);
		}
	}
	
	// INSERT ONE POST
	public void insertPost(Post tempPost) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
			
		String content = tempPost.getContent();
		String email = tempPost.getEmail();
		String image = tempPost.getImage();
		String date = tempPost.getDate();
		
		System.out.println(content + " " +email + " " + image + " " + date);
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = String.format("INSERT INTO posts (content, image, email, date) VALUES ('%s', '%s', '%s', '%s')",content,image,email,date);
			smt = conn.createStatement();
			smt.executeUpdate(sql);
						
		}finally {
			close(conn,smt,pstmt,res);
		}	
	}
	
	
//	DELETE POST
	public void deletePost(String id) throws Exception{
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = "DELETE FROM post WHERE id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		}
		finally {
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
