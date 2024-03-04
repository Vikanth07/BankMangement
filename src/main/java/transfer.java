

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class transfer
 */
@WebServlet("/transfer")
public class transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sano = request.getParameter("sano");
		String dano = request.getParameter("dano");
		Double amt = Double.parseDouble(request.getParameter("amt"));
		PrintWriter out = response.getWriter();
		if(sano.equals(dano)) {
			out.println("Source and destination account number cannot be same");
			return;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vikanth","root","Vikanth@msd7");
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("select * from details where ano='"+sano+"'");
			if(res.next()) {
				ResultSet res1 = stmt.executeQuery("select * from details where ano='"+dano+"'");
				if(res1.next()) {
					ResultSet res2 = stmt.executeQuery("select * from details where ano='"+sano+"' and balance>='"+amt+"'");
					if(res2.next()) {
						int i = stmt.executeUpdate("update details set balance=balance-'"+amt+"' where ano='"+sano+"'");
						int j = stmt.executeUpdate("update details set balance=balance+'"+amt+"' where ano='"+dano+"'");
						if(i==1 && j==1) {
							out.println("Amount Successfully Transfered");
							SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Timestamp timestamp=new Timestamp(System.currentTimeMillis());
							stmt.execute("insert into bankstatement values ('"+sano+"','"+amt+"','debit','"+dateformat.format(timestamp)+"')");
							stmt.execute("insert into bankstatement values ('"+dano+"','"+amt+"','credit','"+dateformat.format(timestamp)+"')");
						}else {
							out.println("Error transfering the amount");
						}
					}else {
						out.println("Insufficient Balance to Transfer");
					}
				}else {
					out.println("Invalid Destination account number");
				}
			}else {
				out.println("Invalid Source account number");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Exception is "+e);
		}
	}

}
