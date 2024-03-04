

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class withdraw
 */
@WebServlet("/withdraw")
public class withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public withdraw() {
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
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("select * from details where ano='"+ano+"'");
			if(res.next()) {
				ResultSet res1 = stmt.executeQuery("select * from details where balance>='"+amt+"' and ano='"+ano+"'");
				if(res1.next()) {
				    int i = stmt.executeUpdate("update details set balance=balance-'"+amt+"' where ano='"+ano+"'");
					if(i>0) {
						out.println("Amount Successfully Withdrawn");
						String s1="withdraw";
						SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Timestamp timestamp=new Timestamp(System.currentTimeMillis());
						PreparedStatement pstmt=conn.prepareStatement("insert into bankStatement(ano,amt,transactionType,transactionTime) values (?,?,?,?)");
						pstmt.setString(1, ano);
						pstmt.setDouble(2,amt);
						pstmt.setString(3, s1);
						pstmt.setString(4, dateformat.format(timestamp));
						pstmt.execute();
						
					}else {
						out.println("Error debiting the amount");
					}
				}else {
					out.println("Insufficient Balance");
				}		
			}else {
				out.println("Account number not found");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
