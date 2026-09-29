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

@WebServlet("/delete")
public class DeleteStudentServlet extends HttpServlet {

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

        int sid = Integer.parseInt(
                request.getParameter("sid")
        );

        String sql =
                "DELETE FROM STUDENT WHERE SID = ?";

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

            ps.executeUpdate();

            ps.close();
            con.close();

            // Return to display page
            response.sendRedirect("display");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h2>Delete Error: " +
                    e.getMessage() +
                    "</h2>"
            );
        }
    }
}