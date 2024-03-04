

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String psw = request.getParameter("psw");
		String dob = request.getParameter("dob");
		String ano=request.getParameter("ano");
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vikanth","root","Vikanth@msd7");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into details(username,email,psw,dob,ano) values ('"+username+"','"+email+"','"+psw+"','"+dob+"','"+ano+"')");
			conn.close();
			response.sendRedirect("Login.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
