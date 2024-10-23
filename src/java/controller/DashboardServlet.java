/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);  // Lấy session hiện tại, nhưng không tạo mới nếu chưa có
        if (session == null || session.getAttribute("account") == null) {
            // Nếu session không tồn tại hoặc chưa đăng nhập, chuyển hướng đến trang đăng nhập
            response.sendRedirect("adminLogin.jsp");
        } else {
            // Nếu đã đăng nhập, tiếp tục xử lý yêu cầu và hiển thị dashboard
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}
