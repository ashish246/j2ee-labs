package com.datasource.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * @author Administrator
 *
 */
@WebServlet("/JDBCDataSourceExample")
public class JDBCDataSourceExample extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			ctx = new InitialContext();
			
			//for MYSQL
			/*DataSource ds = (DataSource) ctx
					.lookup("java:/comp/env/jdbc/emp_local_db");*/
			
			//For ORACLE
			DataSource ds = (DataSource) ctx
					.lookup("java:/comp/env/jdbc/LOCALTEST_DB");
			
			/*ctx = new InitialContext();
			Context initCtx  = (Context) ctx.lookup("java:/comp/env");
			DataSource ds = (DataSource) initCtx.lookup("jdbc/MyLocalDB");*/

			con = ds.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery("select empId from employee");

			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("<html><body><h2>Employee Details</h2>");
			out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
			out.print("<th>Employee ID</th>");

			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>" + rs.getInt("empid") + "</td>");
				out.print("</tr>");
			}
			out.print("</table></body><br/>");

			// lets print some DB information
			out.print("<h3>Database Details</h3>");
			out.print("Database Product: "
					+ con.getMetaData().getDatabaseProductName() + "<br/>");
			out.print("Database Driver: " + con.getMetaData().getDriverName());
			out.print("</html>");

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}

		}
	}

}