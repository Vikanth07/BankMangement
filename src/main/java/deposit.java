

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deposit
 */
@WebServlet("/deposit")
public class deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deposit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ano = request.getParameter("ano");
		Double amt = Double.parseDouble(request.getParameter("amt"));
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vikanth","root","Vikanth@msd7");
			PreparedStatement pstmt = conn.prepareStatement("select * from details where ano=?");
			pstmt.setString(1, ano);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				int val = pstmt.executeUpdate("update details set balance=balance+'"+amt+"' where ano='"+ano+"'");
				if(val>0) {
					out.println("Amount Successfully deposited "+amt);
					SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Timestamp timestamp=new Timestamp(System.currentTimeMillis());
					pstmt.execute("insert into bankstatement values ('"+ano+"','"+amt+"','deposit','"+dateformat.format(timestamp)+"')");
				}else {
					out.println("Error occured while depositing");
				}
			}else {
				out.println("Account number not available");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
