/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author ndcpr
 */
public class StaffProfileServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại nếu đã có

        if (session == null || session.getAttribute("account") == null) {
            // Chuyển hướng đến trang đăng nhập nếu không tìm thấy session hoặc tài khoản
            response.sendRedirect("adminLogin");
            return;
        }

        // Lấy account từ session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 3) {
            // Nếu RoleID là 3 (nhân viên), chuyển tiếp đến trang staffProfile.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("Frontend_Staff/staffProfile.jsp");
            dispatcher.forward(request, response);
        } else {
            // Nếu không có quyền, đặt thông báo lỗi và chuyển đến trang lỗi
            request.setAttribute("errorMessage", "You do not have the required permissions to access the profile page.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Để xử lý POST giống với GET
    }
}
