

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class changepin
 */
@WebServlet("/changepin")
public class changepin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changepin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String cpsw = request.getParameter("cpsw");
		String npsw = request.getParameter("npsw");
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vikanth","root","Vikanth@msd7");
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("select * from details where username='"+username+"' and psw='"+cpsw+"'");
			if(res.next()) {
				int i=stmt.executeUpdate("update details set psw='"+npsw+"' where username='"+username+"' and psw='"+cpsw+"'");
				if(i==1) {
					out.println("Successfully Changed the Password");
				}else {
					out.println("Error while changing the Password");
				}
			}else {
				out.println("Invalid Username or Current Password");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
