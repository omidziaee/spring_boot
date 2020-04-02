package webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Browser sends Http Request to Web Server
 * 
 * Code in Web Server => Input:HttpRequest, Output: HttpResponse
 * JEE with Servlets
 * 
 * Web Server responds with Http Response
 */

//Java Platform, Enterprise Edition (Java EE) JEE6

//Servlet is a Java programming language class 
//used to extend the capabilities of servers 
//that host applications accessed by means of 
//a request-response programming model.

//1. extends javax.servlet.http.HttpServlet
//2. @WebServlet(urlPatterns = "/login.do")
//3. doGet(HttpServletRequest request, HttpServletResponse response)
//4. How is the response created?

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
	UserValidationService userValidation = new UserValidationService();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		PrintWriter out = response.getWriter();
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<title>Yahoo Omid Joon!!!!!!!!</title>");
//		out.println("</head>");
//		out.println("<body>");
//		out.println("This is the first server Omid creates!");
//		out.println("</body>");
//		out.println("</html>")
		String name = request.getParameter("name");
		request.setAttribute("name", name);
		request.setAttribute("password", request.getParameter("password"));
		request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("password"));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		request.setAttribute("password", password);
		String username = request.getParameter("username");
		request.setAttribute("username", password);
		if(userValidation.isValid(username, password)) {
			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
		}else {
			// Whenever you want to send something to jsp it should be a set request
			request.setAttribute("errorMsg", "Invalid credentials!");
			request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
		}
		
		
	}

}