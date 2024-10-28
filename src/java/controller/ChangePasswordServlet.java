/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author PC
 */
public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại của admin
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra nếu admin chưa đăng nhập
        if (account == null) {
            response.sendRedirect("login.jsp");  // Chuyển hướng về trang đăng nhập
            return;
        }

        // Lấy dữ liệu từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!account.getPassword().equals(currentPassword)) {
            // Mật khẩu hiện tại không đúng
            request.setAttribute("errorMessage", "Current password is incorrect.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Mật khẩu hợp lệ, cập nhật mật khẩu mới
        AccountDAO accountDAO = new AccountDAO();
        boolean updateSuccess = false;

        try {
            updateSuccess = accountDAO.updatePassword(account.getUsername(), newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your password. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

// Kiểm tra xem cập nhật có thành công không
        if (updateSuccess) {
            // Cập nhật mật khẩu mới vào đối tượng account trong session
            account.setPassword(newPassword); // Cập nhật đối tượng Account với mật khẩu mới
            session.setAttribute("account", account); // Lưu lại account đã cập nhật vào session

            // Thông báo thành công và chuyển hướng
            session.setAttribute("successMessage", "Password changed successfully!");
            response.sendRedirect("userProfile.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to change password. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
        }

    }
}
