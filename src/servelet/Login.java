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


import db.UserDBUtil;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Resource(name="jdbc/project")
    private DataSource datasource;
    private UserDBUtil userdb;
    ;
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			userdb = new UserDBUtil(datasource);
		}catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Login Servelet");
		
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
		String pass  = request.getParameter("pass");
		
		User tempUser = new User(email,pass);
		
		Boolean canLogin = tempUser.login(userdb);
		
		if(canLogin) {	
			session.setAttribute("user", tempUser);
			response.sendRedirect("Profile");
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			request.setAttribute("LoginError", true);
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
