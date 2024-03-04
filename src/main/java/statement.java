

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class statement
 */
@WebServlet("/statement")
public class statement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public statement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ano = request.getParameter("ano");
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vikanth","root","Vikanth@msd7");
			PreparedStatement pstmt = conn.prepareStatement("select * from bankstatement where ano='"+ano+"'");
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				ResultSet res1 = pstmt.executeQuery("select * from bankstatement where ano='"+ano+"'");
				out.println("<html><head><title>Bank Statement</title></head>");
				out.println("<body><table><tr><th>Account number</th><th>Amount</th><th>Transaction Type</th><th>Transaction Time</th></tr>");
				while(res1.next()) {
					String ano1 = res1.getString("ano");
					Double amt = res1.getDouble("amt");
					String transactionType = res1.getString("transactionType");
					String transactionTime = res1.getString("transactionTime");
					out.println("<tr> <td>" +ano1+ "</td><td>" +amt+ "</td><td>" +transactionType+ "</td><td>" +transactionTime+ "</td></tr>");
				}
				out.println("</table></body>");
				out.println("</html>");
			}else {
				out.println("Invalid Account number");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
