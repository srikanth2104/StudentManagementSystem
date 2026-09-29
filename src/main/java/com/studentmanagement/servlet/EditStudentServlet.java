package com.studentmanagement.servlet;

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

@WebServlet("/edit")
public class EditStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:xe";

    private static final String USERNAME =
            "system";

    private static final String PASSWORD =
            "manager";

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        int sid = Integer.parseInt(
                request.getParameter("sid")
        );

        String sql =
                "SELECT SID, SNAME, SADDR, SPHONENUMBER " +
                "FROM STUDENT WHERE SID = ?";

        PrintWriter out = response.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<title>Edit Student</title>");
        out.println(
                "<link rel='stylesheet' href='style.css'>"
        );
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h1>Edit Student</h1>");

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, sid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                out.println(
                        "<form action='update' method='post'>"
                );

                // SID
                out.println("<label>Student ID</label>");

                out.println(
                        "<input type='number' " +
                        "name='sid' " +
                        "value='" + rs.getInt("SID") + "' " +
                        "readonly>"
                );

                // Name
                out.println("<label>Student Name</label>");

                out.println(
                        "<input type='text' " +
                        "name='sname' " +
                        "value='" +
                        rs.getString("SNAME") +
                        "' required>"
                );

                // Address
                out.println("<label>Student Address</label>");

                out.println(
                        "<input type='text' " +
                        "name='saddr' " +
                        "value='" +
                        rs.getString("SADDR") +
                        "' required>"
                );

                // Phone
                out.println("<label>Phone Number</label>");

                out.println(
                        "<input type='tel' " +
                        "name='sphone' " +
                        "value='" +
                        rs.getString("SPHONENUMBER") +
                        "' required>"
                );

                // Submit
                out.println(
                        "<button type='submit'>" +
                        "Update Student" +
                        "</button>"
                );

                out.println("</form>");

            } else {

                out.println(
                        "<h2>Student not found.</h2>"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            out.println(
                    "<h2>Error: " +
                    e.getMessage() +
                    "</h2>"
            );
        }

        out.println("<br>");

        out.println(
                "<a href='display'>" +
                "Back to Students" +
                "</a>"
        );

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }
}