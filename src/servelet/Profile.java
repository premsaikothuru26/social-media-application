package servelet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import db.PostDBUtil;
import model.User;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Resource(name="jdbc/project")
    private DataSource datasource;
    private PostDBUtil postdb
    ;
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			postdb = new PostDBUtil(datasource);
		}catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CREATE SESSION
		HttpSession session = request.getSession();
		
		// GET USER FROM SESSION
		User user = (User) session.getAttribute("user");
		
		try {
			postdb.getUserPosts(user);
			
			//UPDATE SESSION
			session.setAttribute("user", user);	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {		
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
