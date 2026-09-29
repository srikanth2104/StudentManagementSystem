package com.studentmanagement.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Oracle database connection details
    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:xe";

    private static final String USERNAME =
            "system";

    private static final String PASSWORD =
            "manager";

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Get data from HTML form
        String sid = request.getParameter("sid");
        String sname = request.getParameter("sname");
        String saddr = request.getParameter("saddr");
        String sphone = request.getParameter("sphone");

        String sql =
                "INSERT INTO STUDENT " +
                "(SID, SNAME, SADDR, SPHONENUMBER) " +
                "VALUES (?, ?, ?, ?)";

        try {

            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish database connection
            Connection con = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement ps = con.prepareStatement(sql);

            // Set values
            ps.setInt(1, Integer.parseInt(sid));
            ps.setString(2, sname);
            ps.setString(3, saddr);
            ps.setString(4, sphone);

            // Execute INSERT
            int rows = ps.executeUpdate();

            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registration Result</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");

            out.println("<body>");
            out.println("<div class='container'>");

            if (rows > 0) {

                out.println("<h1>Student Registered Successfully</h1>");

                out.println(
                        "<p class='message'>" +
                        "Student " + sname +
                        " has been successfully registered." +
                        "</p>"
                );

                out.println(
                        "<a href='register.html' class='btn'>" +
                        "Register Another Student" +
                        "</a>"
                );

                out.println("<br><br>");

                out.println(
                        "<a href='display' class='btn'>" +
                        "Display Students" +
                        "</a>"
                );

            } else {

                out.println(
                        "<h1>Registration Failed</h1>"
                );
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            // Close resources
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Database Error:</h2>" +
                    "<p>" + e.getMessage() + "</p>"
            );
        }
    }
}