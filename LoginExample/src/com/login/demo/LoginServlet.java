package com.login.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Uses Cookies for session tracking
 * 
 * @author Administrator
 *
 */
@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" }, initParams = {
		@WebInitParam(name = "user", value = "Ashish"),
		@WebInitParam(name = "password", value = "1729") })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		// we can create DB connection resource here and set it to Servlet
		// context
		if (getServletContext().getInitParameter("dbURL").equals(
				"jdbc:mysql://localhost:3306/emp_db")
				&& getServletContext().getInitParameter("dbUser")
						.equals("root")
				&& getServletContext().getInitParameter("dbUserPwd").equals(
						"1729"))
			getServletContext().setAttribute("DB_Success", "True");
		else
			throw new ServletException("DB Connection error");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");

		// get servlet config init params
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		// logging example
		log("User=" + user + "::password=" + pwd);

		// USING COOKIES FOR SESSION MANAGEMENT
		if (userID.equals(user) && password.equals(pwd)) {
			Cookie loginCookie = new Cookie("user", user);
			// setting cookie to expiry in 30 mins
			loginCookie.setMaxAge(30 * 60);
			response.addCookie(loginCookie);
			response.sendRedirect("LoginSuccess.jsp");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}

		// USING NO SESSION

		/*
		 * if (userID.equals(user) && password.equals(pwd)) {
		 * response.sendRedirect("LoginSuccess.jsp"); } else { RequestDispatcher
		 * rd = getServletContext().getRequestDispatcher( "/login.jsp");
		 * PrintWriter out = response.getWriter(); out.println(
		 * "<font color=red>Either user name or password is wrong.</font>");
		 * rd.include(request, response); }
		 */

	}

}