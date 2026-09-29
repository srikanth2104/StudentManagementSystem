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

@WebServlet("/display")
public class DisplayStudentServlet extends HttpServlet {

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

        String sql =
                "SELECT SID, SNAME, SADDR, SPHONENUMBER " +
                "FROM STUDENT ORDER BY SID";

        PrintWriter out = response.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<title>Student Details</title>");
        out.println(
                "<link rel='stylesheet' href='style.css'>"
        );
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h1>Student Details</h1>");

        out.println("<table border='1'>");

        // Table header
        out.println("<tr>");

        out.println("<th>SID</th>");
        out.println("<th>SNAME</th>");
        out.println("<th>SADDR</th>");
        out.println("<th>SPHONENUMBER</th>");
        out.println("<th>EDIT</th>");
        out.println("<th>UPDATE</th>");
        out.println("<th>DELETE</th>");

        out.println("</tr>");

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int sid = rs.getInt("SID");

                String sname =
                        rs.getString("SNAME");

                String saddr =
                        rs.getString("SADDR");

                String sphone =
                        rs.getString("SPHONENUMBER");

                out.println("<tr>");

                out.println(
                        "<td>" + sid + "</td>"
                );

                out.println(
                        "<td>" + sname + "</td>"
                );

                out.println(
                        "<td>" + saddr + "</td>"
                );

                out.println(
                        "<td>" + sphone + "</td>"
                );

                // Edit
                out.println(
                        "<td>" +
                        "<a class='action edit' " +
                        "href='edit?sid=" + sid + "'>" +
                        "Edit" +
                        "</a>" +
                        "</td>"
                );

                // Update
                out.println(
                        "<td>" +
                        "<a class='action update' " +
                        "href='edit?sid=" + sid + "'>" +
                        "Update" +
                        "</a>" +
                        "</td>"
                );

                // Delete
                out.println(
                        "<td>" +
                        "<a class='action delete' " +
                        "href='delete?sid=" + sid + "' " +
                        "onclick=\"return confirm('Are you sure you want to delete this student?');\">" +
                        "Delete" +
                        "</a>" +
                        "</td>"
                );

                out.println("</tr>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<tr>");

            out.println(
                    "<td colspan='7'>" +
                    "Database Error: " +
                    e.getMessage() +
                    "</td>"
            );

            out.println("</tr>");
        }

        out.println("</table>");

        out.println("<br>");

        out.println(
                "<a href='register.html' class='btn'>" +
                "Register New Student" +
                "</a>"
        );

        out.println("<br><br>");

        out.println(
                "<a href='index.html'>" +
                "Back to Home" +
                "</a>"
        );

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }
}