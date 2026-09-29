package com.studentmanagement.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        int sid = Integer.parseInt(
                request.getParameter("sid")
        );

        String sname =
                request.getParameter("sname");

        String saddr =
                request.getParameter("saddr");

        String sphone =
                request.getParameter("sphone");

        String sql =
                "UPDATE STUDENT " +
                "SET SNAME = ?, " +
                "SADDR = ?, " +
                "SPHONENUMBER = ? " +
                "WHERE SID = ?";

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, sname);
            ps.setString(2, saddr);
            ps.setString(3, sphone);
            ps.setInt(4, sid);

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            if (rows > 0) {

                // Go back to student display
                response.sendRedirect("display");

            } else {

                response.getWriter().println(
                        "<h2>Student not found.</h2>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Update Error: " +
                    e.getMessage() +
                    "</h2>"
            );
        }
    }
}