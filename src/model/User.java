package model;

import java.util.ArrayList;

import db.PostDBUtil;
import db.UserDBUtil;

public class User {
	private String fname;
	private String lname;
	private String email;
	private String pass;
	
	private ArrayList<User> friends  = new ArrayList<>();
	private ArrayList<Post> posts  = new ArrayList<>();
	
	public User(String fname, String lname, String email, String pass) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pass = pass;
	}
	
	public User(String email, String pass) {
		// TODO Auto-generated constructor stub
		this.email = email;
		this.pass = pass;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public ArrayList<User> getFriends() {
		return this.friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}
	

	public ArrayList<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	
	public boolean delPost(String id, PostDBUtil postdb) {
		
		try {
			postdb.deletePost(id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean likePost(String id, PostDBUtil postdb) {
		return true;
	}
	
	public boolean editPost(String id, PostDBUtil postdb) {
		return true;
	}
	
	
	// Custom Methods
	
	public boolean login(UserDBUtil userdb) {

		try {
			User tempUser = userdb.findUser(this.email);
	
			if(tempUser != null) {
				
				if(this.pass.equals(tempUser.pass)) {
					this.fname = tempUser.fname;
					this.lname = tempUser.lname;
					this.email = tempUser.email;
					this.pass = null;
					return true;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(UserDBUtil userdb) {
		
		try {
			userdb.insertUser(this);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean createPost(String content, String image, PostDBUtil postdb) {
		
		
		Post tempPost = new Post(this.email, content,image);
		
		try {
			postdb.insertPost(tempPost);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	

}
