<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="tag" %> 

<%@ page import="model.*" %>  


<% 
	User user = (User) session.getAttribute("user"); 
	if(user == null){
		response.sendRedirect("index.jsp");
	}
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
<link rel="stylesheet" href="./css/index.css">
</head>
<body>
	<header>
		<h1 class="main-heading">Profile Page</h1>
		<a href="index.jsp">Logout</a>
	</header>
	<nav>
		<a class="nav-link" href="Home">Home</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="nav-link" href="#">Settings</a>
	</nav>
	<br><br>
	<form action="CreatePost" method="POST">
		<h2 class="sub-heading">Create Post</h2>
		<br>
		<textarea name="postContent" placeholder="Whats on your mind ?"></textarea><br><br>
		<input type="file" name="postImage"><br><br>
		
		<button type="submit" name="createPost">Create</button>	
		<button type="reset">Clear</button>	
		<br>
		<br>
		<tag:if test="${created}">
			<span class="error">Post sucessfully created</span>
		</tag:if>
		<tag:if test="${notCreated}">
 		 	<span class="error">Post cannot be created</span>
		</tag:if>		
	</form>
	<br><br>
	<tag:if test="${user.getPosts().size() > 0}">
		<h1 class="sub-heading">All Posts</h1>
	</tag:if>
	<tag:if test="${user.getPosts().size() <= 0}">
		<h1 class="sub-heading">No Posts Available</h1>
	</tag:if>
	<br>
	<div class="posts">
		<tag:forEach var="post" items="${user.getPosts()}">
			<div class="post">
				<span>${post.getDate()}<br></span>
				<p>${post.getContent()}<br></p>
				<img src="${post.getImage()}" alt=""/>
				<form action="postOperation" method="GET">
					<button type="submit" value="${post.getPostId()}" name="edit">Edit</button>
					<button type="submit" value="${post.getPostId()}" name="like">Like</button>
					<button type="submit" value="${post.getPostId()}" name="del">Delete</button>
				</form>
			</div>
		</tag:forEach>
	</div>
	
	
	

</body>
</html>